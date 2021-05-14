package io.toolisticon.keycloak.gdpr.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.services.managers.RealmManager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;



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

    // can be read via curl localhost:8888/auth/realms/master/gdpr/encrypt/
    // TODO adding auth
    @Path("encrypt")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EncryptedData encrypt(DecryptedData data) {
        return new EncryptedData(data.getUserId(), "encrypted data");
    }

    @Path("decrypt")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DecryptedData decrypt(EncryptedData data) {
        return new DecryptedData(data.getUserId(), "42");
    }
}
