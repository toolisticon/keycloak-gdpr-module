package io.toolisticon.keycloak.gdpr.api;

import lombok.*;

/**
 * DTO for a request to /decrypt
 * DTO for a response from /encrypt
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EncryptedData {

    private String userId;

    private String cipherText;

    @Override
    public String toString() {
        return "EncryptedData{" +
                "userId='" + userId + '\'' +
                ", cipherText=..." +
                '}';
    }
}
