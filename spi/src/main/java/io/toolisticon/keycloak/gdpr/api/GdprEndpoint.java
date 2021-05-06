package io.toolisticon.keycloak.gdpr.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.services.managers.RealmManager;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;

@Slf4j
public class GdprEndpoint {

    /**
     * the current request context
     */
    @Getter(AccessLevel.PROTECTED)
    private final KeycloakSession keycloakSession;

    public GdprEndpoint(KeycloakSession keycloakSession) {
        this.keycloakSession = keycloakSession;
    }

    /**
     * @return The admin API
     */
    @Path("admin")
    public Object getAdminApiRoot() {
        return new AdminEndPoint(this.keycloakSession);
    }

    private RealmModel init(String realmName) {
        RealmManager realmManager = new RealmManager(this.keycloakSession);
        RealmModel realm = realmManager.getRealmByName(realmName);
        if (realm == null) {
            throw new NotFoundException("Realm does not exist");
        }
        this.keycloakSession.getContext().setRealm(realm);
        return realm;
    }
}
