package io.toolisticon.keycloak.gdpr.crypto;

public class DecryptionFailedException extends RuntimeException {

    public DecryptionFailedException(Throwable cause) {
        super(cause);
    }
}
