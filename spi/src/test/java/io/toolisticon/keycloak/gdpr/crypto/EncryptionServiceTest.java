package io.toolisticon.keycloak.gdpr.crypto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.keycloak.models.UserModel;

import static io.toolisticon.keycloak.gdpr.util.UserModelHelper.buildUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EncryptionServiceTest {

    private static EncryptionService encryptionService;
    private static UserModel user;

    @BeforeAll
    static void setup() throws Exception {
        user = buildUser();
        encryptionService = new EncryptionService(new KeyService());
    }

    @Test
    void shouldDecryptAndEncrypt() {
        String sampleText = "just sample text";
        byte[] cypherText = encryptionService.encrypt(user, sampleText.getBytes());
        String result = new String(encryptionService.decrypt(user, cypherText));
        assertEquals(sampleText, result);
    }

    @Test
    void shouldNotDecryptForUnkownUser() {
        UserModel user2 = buildUser();
        String sampleText = "just sample text";
        byte[] cypherText = encryptionService.encrypt(user, sampleText.getBytes());
        assertThrows(KeyNotFoundException.class, () -> {
            new String(encryptionService.decrypt(user2, cypherText));
        });
    }

    @Test
    void shouldNotDecryptFalsyCipher() {
        String sampleText = "just sample text";
        encryptionService.encrypt(user, sampleText.getBytes());
        assertThrows(DecryptionFailedException.class, () -> {
            new String(encryptionService.decrypt(user, sampleText.getBytes()));
        });
    }

}
