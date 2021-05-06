package io.toolisticon.keycloak.gdpr;

import io.toolisticon.keycloak.gdpr.api.GdprEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

@Slf4j
public class GdprEndpointProvider implements RealmResourceProvider {

    private KeycloakSession session;

    public GdprEndpointProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new GdprEndpoint(session);
    }

    @Override
    public void close() {
        // Nothing to close
    }
}
