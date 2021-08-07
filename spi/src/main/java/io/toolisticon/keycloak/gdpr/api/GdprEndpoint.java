package io.toolisticon.keycloak.gdpr.api;

import io.toolisticon.keycloak.gdpr.crypto.DecryptionFailedException;
import io.toolisticon.keycloak.gdpr.crypto.EncryptionFailedException;
import io.toolisticon.keycloak.gdpr.crypto.EncryptionService;
import io.toolisticon.keycloak.gdpr.crypto.KeyNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.DecoderException;
import org.bouncycastle.util.encoders.EncoderException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager.AuthResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class GdprEndpoint {

    /**
     * the current request context
     */
    @Getter(AccessLevel.PROTECTED)
    private final KeycloakSession keycloakSession;
    private final EncryptionService encryptionService;
    private final AuthResult auth;

    public GdprEndpoint(KeycloakSession keycloakSession, EncryptionService encryptionService) {
        this.keycloakSession = keycloakSession;
        this.encryptionService = encryptionService;
        this.auth = new AppAuthManager.BearerTokenAuthenticator(keycloakSession).authenticate();
    }

    /**
     * @return The admin API
     */
    @Path("admin")
    public Object getAdminApiRoot() {
        return new AdminEndPoint(this.keycloakSession);
    }

    // can be read via curl localhost:8888/auth/realms/master/gdpr/encrypt/
    @Path("encrypt")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EncryptedData encrypt(DecryptedData data) {
        this.checkAccessRights();
        try {
            final byte[] dataBytes = data.getData().getBytes(StandardCharsets.UTF_8);
            final byte[] encryptedData = encryptionService.encrypt(getUserModel(data.getUserId()), dataBytes);

            final String encodedCipherText = Base64.toBase64String(encryptedData);
            return new EncryptedData(data.getUserId(), encodedCipherText);
        } catch (EncoderException | EncryptionFailedException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    @Path("decrypt")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DecryptedData decrypt(EncryptedData data) {
        this.checkAccessRights();
        try {
            final byte[] cipherText = Base64.decode(data.getCipherText());
            final byte[] decryptedBytes = encryptionService.decrypt(getUserModel(data.getUserId()), cipherText);
            final String decryptedData = new String(decryptedBytes, StandardCharsets.UTF_8);
            return new DecryptedData(data.getUserId(), decryptedData);
        } catch (DecoderException | DecryptionFailedException | KeyNotFoundException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }
    // BATCH OPERATIONS

    @Path("encrypt/batch")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EncryptedBatchData encrypt(DecryptedBatchData data) {
        this.checkAccessRights();
        try {
            final EncryptedBatchData result = new EncryptedBatchData();
            result.setUserId(data.getUserId());
            final UserModel user = getUserModel(data.getUserId());
            for (Map.Entry<String, String> entry : data.getData().entrySet()) {
                final byte[] dataBytes = entry.getValue().getBytes(StandardCharsets.UTF_8);
                final byte[] encryptedData = encryptionService.encrypt(user, dataBytes);
                result.getCipherTextEntries().put(entry.getKey(), Base64.toBase64String(encryptedData));
            }
            log.debug("Encryption done {}", result);
            return result;
        } catch (EncryptionFailedException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    @Path("decrypt/batch")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DecryptedBatchData decrypt(EncryptedBatchData data) {
        this.checkAccessRights();
        try {
            final DecryptedBatchData result = new DecryptedBatchData();
            result.setUserId(data.getUserId());
            for (Map.Entry<String, String> entry : data.getCipherTextEntries().entrySet()) {
                final byte[] cipherText = Base64.decode(entry.getValue());
                final byte[] decryptedBytes = encryptionService.decrypt(getUserModel(data.getUserId()), cipherText);
                final String decryptedData = new String(decryptedBytes, StandardCharsets.UTF_8);
                result.getData().put(entry.getKey(), decryptedData);
            }
            log.debug("Decryption done {}", result);
            return result;
        } catch (DecryptionFailedException | KeyNotFoundException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    protected void checkAccessRights() {
        if (this.auth == null) {
            log.error("Empty authentication details");
            throw new NotAuthorizedException("Bearer");
        } else if (this.auth.getToken().getRealmAccess() == null) {
            log.error("No access to realm");
            throw new ForbiddenException("Don't have realm access");
        }
        log.debug("Got user with id {}", this.auth.getUser().getId());
    }

    protected RealmModel getRealmModel() {
        return this.auth.getSession().getRealm();
    }

    private UserModel getUserModel(String userId) {
        return this.keycloakSession.users().getUserById(getRealmModel(), userId);
    }
}
