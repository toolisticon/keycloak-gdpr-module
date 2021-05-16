package io.toolisticon.keycloak.gdpr;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

import io.toolisticon.keycloak.gdpr.api.GdprEndpoint;
import io.toolisticon.keycloak.gdpr.crypto.EncryptionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GdprEndpointProvider implements RealmResourceProvider {

    private KeycloakSession session;
    private final EncryptionService encryptionService;

    public GdprEndpointProvider(KeycloakSession session, EncryptionService encryptionService) {
        this.session = session;
        this.encryptionService = encryptionService;
    }

    @Override
    public Object getResource() {
        return new GdprEndpoint(session, encryptionService);
    }

    @Override
    public void close() {
        // Nothing to close
    }
}
