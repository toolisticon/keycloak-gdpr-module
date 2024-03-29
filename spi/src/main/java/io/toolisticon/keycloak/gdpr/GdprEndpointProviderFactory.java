package io.toolisticon.keycloak.gdpr;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

import io.toolisticon.keycloak.gdpr.crypto.EncryptionService;
import io.toolisticon.keycloak.gdpr.crypto.KeyService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * This class will setup the scim resource provider as a rest provider in the keycloak server
 *
 * @see META-INF/services/org.keycloak.services.resource.RealmResourceProviderFactory
 */
@Slf4j
public class GdprEndpointProviderFactory  implements RealmResourceProviderFactory {

    private EncryptionService encryptionService = null;

    public static final String JCE_PROVIDER = "BC";

    /**
     * @return the ID of this module that is used as base context path
     */
    @Override
    public String getId()
    {
        /**
         * this ID identifies the rest provider and is used as base context path for this module
         */
        return ContextPaths.GDPR_ENDPOINT_PATH;
    }

    @Override
    public RealmResourceProvider create(KeycloakSession session) {
        return new GdprEndpointProvider(session, encryptionService);
    }

    @SneakyThrows
    @Override
    public void init(Config.Scope scope) {
        final KeyService keyService = new KeyService();
        encryptionService = new EncryptionService(keyService);
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
