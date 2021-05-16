package io.toolisticon.keycloak.gdpr.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for a request to /encrypt
 * DTO for a response from /decrypt
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecryptedData {

    private String userId;
    private String data;
}
