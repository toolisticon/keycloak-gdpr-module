package io.toolisticon.keycloak.gdpr.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * DTO for a request to /decrypt
 * DTO for a response from /encrypt
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncryptedBatchData {

    private String userId;

    private Map<String, String> cipherTextEntries = new HashMap<>();
}
