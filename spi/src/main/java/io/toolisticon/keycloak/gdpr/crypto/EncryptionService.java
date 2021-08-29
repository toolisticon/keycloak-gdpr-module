package io.toolisticon.keycloak.gdpr.crypto;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.keycloak.models.UserModel;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static io.toolisticon.keycloak.gdpr.GdprEndpointProviderFactory.JCE_PROVIDER;

@Slf4j
public class EncryptionService {

    public static final String CIPHER = "AES/GCM/NoPadding";

    // TODO: The Initialization Vector should never be fixed in a real application. Simplified for the demo.
    private final byte[] ivParam = Hex.decode("000102030405060708090a0b");

    private final KeyService keyService;

    public EncryptionService(KeyService keyService) {
        this.keyService = keyService;
    }

    public byte[] encrypt(UserModel user, byte[] data) {
        try {
            final SecretKey key = keyService.getOrCreate(user);
            final Cipher cipher = encryptCipher(key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new EncryptionFailedException();
        }
    }

    public byte[] decrypt(UserModel user, byte[] cipherText) {
        final SecretKey key = keyService.get(user)
                .orElseThrow(() -> new KeyNotFoundException("No encryption key found"));
        try {
            final Cipher cipher = decryptCipher(key);
            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            throw new DecryptionFailedException(e);
        }
    }

    private Cipher encryptCipher(SecretKey key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(CIPHER, JCE_PROVIDER);
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, ivParam));
        return cipher;
    }

    private Cipher decryptCipher(SecretKey key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(CIPHER, JCE_PROVIDER);
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, ivParam));
        return cipher;
    }
}
