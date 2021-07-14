package io.toolisticon.keycloak.gdpr.api;

import io.toolisticon.keycloak.gdpr.crypto.DecryptionFailedException;
import io.toolisticon.keycloak.gdpr.crypto.EncryptionFailedException;
import io.toolisticon.keycloak.gdpr.crypto.EncryptionService;
import io.toolisticon.keycloak.gdpr.crypto.KeyNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager.AuthResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.nio.charset.StandardCharsets;

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
        } catch (EncryptionFailedException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    @Path("decrypt")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DecryptedData decrypt(EncryptedData data) {
        this.checkAccessRights();
        final byte[] cipherText = Base64.decode(data.getCipherText());
        try {
            final byte[] decryptedBytes = encryptionService.decrypt(getUserModel(data.getUserId()), cipherText);
            final String decryptedData = new String(decryptedBytes, StandardCharsets.UTF_8);
            return new DecryptedData(data.getUserId(), decryptedData);
        } catch (DecryptionFailedException | KeyNotFoundException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    protected void checkAccessRights() {
        if (this.auth == null) {
            throw new NotAuthorizedException("Bearer");
        } else if (this.auth.getToken().getRealmAccess() == null) {
            throw new ForbiddenException("Don't have realm access");
        }
    }

    protected RealmModel getRealmModel() {
        return this.auth.getSession().getRealm();
    }

    private UserModel getUserModel(String userId) {
        return this.keycloakSession.users().getUserById(getRealmModel(), userId);
    }
}
