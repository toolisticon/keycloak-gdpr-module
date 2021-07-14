package io.toolisticon.keycloak.gdpr.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for a request to /decrypt
 * DTO for a response from /encrypt
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncryptedData {

    private String userId;
    private String cipherText;
}
