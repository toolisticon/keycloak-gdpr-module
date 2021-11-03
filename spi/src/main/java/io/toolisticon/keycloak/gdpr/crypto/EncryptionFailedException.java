package io.toolisticon.keycloak.gdpr.crypto;

public class EncryptionFailedException extends RuntimeException {

    public EncryptionFailedException(Throwable cause) {
        super(cause);
    }
}
