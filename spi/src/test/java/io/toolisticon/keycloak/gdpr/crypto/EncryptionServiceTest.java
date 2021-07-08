package io.toolisticon.keycloak.gdpr.crypto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EncryptionServiceTest {

    private EncryptionService encryptionService;

    @BeforeEach
    void setup() throws Exception {
        encryptionService = new EncryptionService(new KeyService());
    }

    @Test
    void shouldDecryptAndEncrypt() {
        String user = "1";
        String sampleText = "just sample text";
        byte[] cypherText = encryptionService.encrypt(user, sampleText.getBytes());
        String result = new String(encryptionService.decrypt(user,cypherText));
        assertEquals(sampleText, result);
    }

}
