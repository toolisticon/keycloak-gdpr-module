package io.toolisticon.keycloak.gdpr.crypto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.models.UserModel;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static io.toolisticon.keycloak.gdpr.util.UserModelHelper.buildUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EncryptionServiceTest {

    private EncryptionService encryptionService;
    private UserModel user;

    @BeforeEach
    void setup() throws Exception {
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

    @Test
    void shouldNotEncryptWithInvalidSecretKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        String sampleText = "just sample text";
        SecretKey key = mock(SecretKey.class);
        KeyService keyservice = new KeyService() {
            @Override
            public SecretKey getOrCreate(UserModel user) {
                return key;
            }
        };
        encryptionService = new EncryptionService(keyservice);
        when(key.getFormat()).thenReturn(null);
        assertThrows(EncryptionFailedException.class, () -> {
            encryptionService.encrypt(user, sampleText.getBytes());
        });
    }

}
