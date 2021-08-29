package io.toolisticon.keycloak.gdpr.crypto;

public class DecryptionFailedException extends RuntimeException {

    public DecryptionFailedException() {
    }

    public DecryptionFailedException(Throwable cause) {
        super(cause);
    }
}
