package io.toolisticon.keycloak.gdpr.api;

import lombok.*;

/**
 * DTO for a request to /encrypt
 * DTO for a response from /decrypt
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DecryptedData {

    private String userId;

    private String data;

    @Override
    public String toString() {
        return "DecryptedData{" +
                "userId='" + userId + '\'' +
                ", data=..." +
                '}';
    }
}
