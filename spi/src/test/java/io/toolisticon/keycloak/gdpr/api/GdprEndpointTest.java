package io.toolisticon.keycloak.gdpr.api;

import io.toolisticon.keycloak.gdpr.crypto.EncryptionService;
import io.toolisticon.keycloak.gdpr.crypto.KeyService;
import org.bouncycastle.util.encoders.DecoderException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.keycloak.models.KeycloakSession;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class GdprEndpointTest {

    private static GdprEndpoint endpoint;

    @BeforeAll
    static void setup() throws Exception {
        endpoint = new GdprEndpoint(Mockito.mock(KeycloakSession.class), new EncryptionService(new KeyService()));
    }

    @Test
    void shouldInit() {
        assertNotNull(endpoint.getAdminApiRoot());
        assertNotNull(endpoint.getKeycloakSession());
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
        assertEquals(data, result);
        assertEquals(data.toString(), result.toString());
    }

    @Test
    void shouldNotDecryptInvalid() {
        EncryptedData data = new EncryptedData();
        data.setCipherText("lorem ipsum");
        data.setUserId("1");
        assertThrows(DecoderException.class, () -> {
            endpoint.decrypt(data);
        });
    }
}
