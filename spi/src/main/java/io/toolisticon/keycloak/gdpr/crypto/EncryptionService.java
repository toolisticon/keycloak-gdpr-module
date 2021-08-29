package io.toolisticon.keycloak.gdpr.crypto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Arrays;
import org.keycloak.models.UserModel;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.*;

import static io.toolisticon.keycloak.gdpr.GdprEndpointProviderFactory.JCE_PROVIDER;

@Slf4j
@RequiredArgsConstructor
public class EncryptionService {

    public static final String CIPHER = "AES/GCM/NoPadding";

    private final KeyService keyService;
    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * Encrypt bytes of data.
     *
     * @param user User to which the data belongs
     * @param data data bytes to encrypt
     * @return cipher text with IV prepended
     */
    public byte[] encrypt(UserModel user, byte[] data) {
        try {
            final SecretKey key = keyService.getOrCreate(user);
            byte[] iv = generateIv();
            final Cipher cipher = encryptCipher(key, iv);
            byte[] cipherText = cipher.doFinal(data);
            return Arrays.concatenate(iv, cipherText);
        } catch (Exception e) {
            throw new EncryptionFailedException();
        }
    }

    /**
     * Decrypt bytes of data.
     *
     * @param user User to which the data belongs
     * @param cipherText cipher text with IV prepended
     * @return decrypted data bytes
     */
    public byte[] decrypt(UserModel user, byte[] cipherText) {
        final SecretKey key = keyService.get(user)
                .orElseThrow(() -> new KeyNotFoundException("No encryption key found"));
        try {
            final byte[] iv = Arrays.copyOfRange(cipherText, 0, 16);
            final byte[] data = Arrays.copyOfRange(cipherText, 16, cipherText.length);
            final Cipher cipher = decryptCipher(key, iv);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new DecryptionFailedException(e);
        }
    }

    private Cipher encryptCipher(SecretKey key, byte[] iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(CIPHER, JCE_PROVIDER);
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));
        return cipher;
    }

    private Cipher decryptCipher(SecretKey key, byte[] iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(CIPHER, JCE_PROVIDER);
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));
        return cipher;
    }

    private byte[] generateIv() {
        final byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return iv;
    }
}
