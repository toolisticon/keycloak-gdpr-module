package io.toolisticon.keycloak.gdpr.api;

import io.toolisticon.keycloak.gdpr.crypto.EncryptionService;
import io.toolisticon.keycloak.gdpr.crypto.KeyService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.models.KeycloakSession;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GdprEndpointTest {
    private static KeycloakSession kcSession;
    private static GdprEndpoint endpoint;

    @BeforeAll
    static void setup() throws Exception {
        endpoint = new GdprEndpoint(kcSession, new EncryptionService(new KeyService()));
    }

    @Test
    void shouldEncryptData() {
        DecryptedData data = new DecryptedData();
        data.setData("lorem ipsum");
        data.setUserId("1");
        EncryptedData result = endpoint.encrypt(data);
        assertNotNull(result);
        assertEquals(data.getUserId(), result.getUserId());
        assertNotEquals(data.getData(), result.toString());
        assertNotEquals(data.getData(), result.getCipherText());
    }

    @Test
    void shouldDecryptData() {
        DecryptedData data = new DecryptedData();
        data.setData("lorem ipsum");
        data.setUserId("1");
        DecryptedData result = endpoint.decrypt(endpoint.encrypt(data));
        assertNotNull(result);
        assertTrue(data.equals(result));
        assertEquals(data.toString(), result.toString());
    }
}
