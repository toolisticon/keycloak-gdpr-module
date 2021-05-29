package io.toolisticon.keycloak.gdpr.crypto;

import static io.toolisticon.keycloak.gdpr.GdprEndpointProviderFactory.JCE_PROVIDER;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Optional;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class KeyService {

    @PersistenceContext
    protected EntityManager em;

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
        return secretKey;
    }

    public Optional<SecretKey> get(String userId) {
        return Optional.ofNullable(keys.get(userId));
    }

    public void delete(String userId) {
        keys.remove(userId);
    }
}
