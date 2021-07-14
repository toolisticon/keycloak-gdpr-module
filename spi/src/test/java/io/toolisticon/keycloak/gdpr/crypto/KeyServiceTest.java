package io.toolisticon.keycloak.gdpr.crypto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class KeyServiceTest {

    private static KeyService keyService;

    @BeforeAll
    static void setup() throws Exception {
        keyService = new KeyService();
    }

    @Test
    void shouldGetOrCreateSecretKeyForUser() {
        String user = UUID.randomUUID().toString();
        SecretKey key = keyService.getOrCreate(user);
        assertNotNull(key);
    }

    @Test
    void shouldNotGetSecretKeyForUnkownUser() {
        String user = UUID.randomUUID().toString();
        assertTrue(!keyService.get(user).isPresent());
    }

    @Test
    void shouldGetSecretKeyForUser() {
        String user = UUID.randomUUID().toString();
        keyService.getOrCreate(user);
        assertNotNull(keyService.get(user));
    }

}
