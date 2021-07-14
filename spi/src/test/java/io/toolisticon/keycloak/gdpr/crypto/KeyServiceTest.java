package io.toolisticon.keycloak.gdpr.crypto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.keycloak.models.UserModel;

import javax.crypto.SecretKey;

import static io.toolisticon.keycloak.gdpr.util.UserModelHelper.buildUser;
import static org.junit.jupiter.api.Assertions.*;

class KeyServiceTest {

    private static KeyService keyService;
    private static UserModel user;

    @BeforeAll
    static void setup() throws Exception {
        user = buildUser();
        keyService = new KeyService();
    }

    @Test
    void shouldBeTheSameKeyForTheSameUser() {
        SecretKey key = keyService.getOrCreate(user);
        SecretKey keyAgain = keyService.get(user).get();
        assertEquals(key, keyAgain);
    }

    @Test
    void shouldGetOrCreateSecretKeyForUser() {
        SecretKey key = keyService.getOrCreate(user);
        assertNotNull(key);
    }


    @Test
    void shouldNotGetSecretKeyForUnkownUser() {
        UserModel user2 = buildUser();
        assertTrue(!keyService.get(user2).isPresent());
    }

    @Test
    void shouldGetSecretKeyForUser() {
        keyService.getOrCreate(user);
        assertNotNull(keyService.get(user));
    }

}
