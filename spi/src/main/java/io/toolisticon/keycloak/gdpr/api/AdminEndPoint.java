package io.toolisticon.keycloak.gdpr.api;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resources.admin.AdminRoot;

@Slf4j
public class AdminEndPoint extends AdminRoot {
    
    public AdminEndPoint(KeycloakSession session) {
        this.session = session;
    }
}
