package io.toolisticon.keycloak.gdpr.crypto;

import static io.toolisticon.keycloak.gdpr.GdprEndpointProviderFactory.JCE_PROVIDER;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.HashMap;
import java.util.Optional;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

@Slf4j
public class KeyService {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private final HashMap<String, SecretKey> keys = new HashMap<>();
    private final KeyGenerator keyGenerator;

    public KeyService() throws NoSuchAlgorithmException, NoSuchProviderException {
        keyGenerator = KeyGenerator.getInstance("AES", JCE_PROVIDER);
        keyGenerator.init(256);
    }

    public SecretKey getOrCreate(String userId) {
        return get(userId).orElseGet(() -> createKey(userId));
    }

    private SecretKey createKey(String userId) {
        final SecretKey secretKey = keyGenerator.generateKey();
        keys.put(userId, secretKey);
        log.info("created key for userId {}", userId);
        return secretKey;
    }

    public Optional<SecretKey> get(String userId) {
        return Optional.ofNullable(keys.get(userId));
    }

    public void delete(String userId) {
        keys.remove(userId);
    }
}
