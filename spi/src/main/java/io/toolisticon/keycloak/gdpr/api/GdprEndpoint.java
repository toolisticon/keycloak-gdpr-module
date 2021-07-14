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

    public GdprEndpoint(KeycloakSession keycloakSession, EncryptionService encryptionService) {
        this.keycloakSession = keycloakSession;
        this.encryptionService = encryptionService;
    }

    /**
     * @return The admin API
     */
    @Path("admin")
    public Object getAdminApiRoot() {
        return new AdminEndPoint(this.keycloakSession);
    }

    // can be read via curl localhost:8888/auth/realms/master/gdpr/encrypt/
    // TODO adding auth
    @Path("encrypt")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EncryptedData encrypt(DecryptedData data) {
        try {
            final byte[] dataBytes = data.getData().getBytes(StandardCharsets.UTF_8);
            final byte[] encryptedData = encryptionService.encrypt(data.getUserId(), dataBytes);

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
        final byte[] cipherText = Base64.decode(data.getCipherText());

        try {
            final byte[] decryptedBytes = encryptionService.decrypt(data.getUserId(), cipherText);
            final String decryptedData = new String(decryptedBytes, StandardCharsets.UTF_8);
            return new DecryptedData(data.getUserId(), decryptedData);
        } catch (DecryptionFailedException | KeyNotFoundException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }
}
