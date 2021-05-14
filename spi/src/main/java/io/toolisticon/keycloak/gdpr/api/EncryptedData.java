package io.toolisticon.keycloak.gdpr.api;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for a request to /decrypt
 * DTO for a response from /encrypt
 */
@Data
@AllArgsConstructor
public class EncryptedData {

    private String userId;
    private String cipherText;
}
