package io.toolisticon.keycloak.gdpr.crypto;

public class KeyNotFoundException extends RuntimeException {

    public KeyNotFoundException(String message) {
        super(message);
    }
}
