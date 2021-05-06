package io.toolisticon.keycloak.gdpr;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

/**
 * This class will setup the scim resource provider as a rest provider in the keycloak server
 *
 * @see META-INF/services/org.keycloak.services.resource.RealmResourceProviderFactory
 */
@Slf4j
public class GdprEndpointProviderFactory  implements RealmResourceProviderFactory {

    /**
     * this ID identifies the rest provider and is used as base context path for this module
     */
    public static final String ID = ContextPaths.GDPR_ENDPOINT_PATH;

    /**
     * @return the ID of this module that is used as base context path
     */
    @Override
    public String getId()
    {
        return ID;
    }

    @Override
    public RealmResourceProvider create(KeycloakSession session) {
        return new GdprEndpointProvider(session);
    }

    @Override
    public void init(Config.Scope scope) {
        // Nothing to initialize
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // Nothing to do
    }

    @Override
    public void close() {
        // Nothing to do
    }
}
