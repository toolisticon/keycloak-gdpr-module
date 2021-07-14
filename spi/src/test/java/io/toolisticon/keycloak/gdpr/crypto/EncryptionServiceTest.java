package io.toolisticon.keycloak.gdpr.crypto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionServiceTest {

    private static EncryptionService encryptionService;

    @BeforeAll
    static void setup() throws Exception {
        encryptionService = new EncryptionService(new KeyService());
    }

    @Test
    void shouldDecryptAndEncrypt() {
        String user = "1";
        String sampleText = "just sample text";
        byte[] cypherText = encryptionService.encrypt(user, sampleText.getBytes());
        String result = new String(encryptionService.decrypt(user, cypherText));
        assertEquals(sampleText, result);
    }

    @Test
    void shouldNotDecryptForUnkownUser() {
        String user1 = "1";
        String user2 = "2";
        String sampleText = "just sample text";
        byte[] cypherText = encryptionService.encrypt(user1, sampleText.getBytes());
        assertThrows(KeyNotFoundException.class, () -> {
            new String(encryptionService.decrypt(user2, cypherText));
        });
    }

    @Test
    void shouldNotDecryptFalsyCipher() {
        String user = "1";
        String sampleText = "just sample text";
        encryptionService.encrypt(user, sampleText.getBytes());
        assertThrows(DecryptionFailedException.class, () -> {
            new String(encryptionService.decrypt(user, sampleText.getBytes()));
        });
    }

}
