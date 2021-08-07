package io.toolisticon.keycloak.gdpr.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * DTO for a request to /encrypt
 * DTO for a response from /decrypt
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecryptedBatchData {

    private String userId;

    private Map<String, String> data = new HashMap<>();
}
