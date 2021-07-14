package io.toolisticon.keycloak.gdpr.crypto;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.keycloak.models.UserModel;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.*;

import static io.toolisticon.keycloak.gdpr.GdprEndpointProviderFactory.JCE_PROVIDER;

@Slf4j
public class KeyService {

    public static final String USER_ATTR_PRIV_KEY = "GDPR_PRIV_KEY";
    public static final String ENCRYPTION_ALGORITHM = "AES";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private final HashMap<String, SecretKey> keys = new HashMap<>();
    private final KeyGenerator keyGenerator;

    public KeyService() throws NoSuchAlgorithmException, NoSuchProviderException {
        keyGenerator = KeyGenerator.getInstance("AES", JCE_PROVIDER);
        keyGenerator.init(256);
    }

    public SecretKey getOrCreate(UserModel user) {
        return get(user).orElseGet(() -> createKey(user));
    }

    private SecretKey createKey(UserModel user) {
        final SecretKey secretKey = keyGenerator.generateKey();
        // get base64 encoded version of the key
        final String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        user.getAttributes().put(USER_ATTR_PRIV_KEY, Collections.singletonList(encodedKey));
        // add to local map, too
        keys.put(user.getId(), secretKey);
        log.debug("Created key for userId {}", user.getId());
        return secretKey;
    }

    public Optional<SecretKey> get(UserModel user) {
        // check if existing key already added on User Model
        final List<String> userKeyAttributes = user.getAttributes().get(USER_ATTR_PRIV_KEY);
        if (userKeyAttributes != null && userKeyAttributes.size() > 0) {
            final String key = userKeyAttributes.get(0);
            log.debug("Found existing key for userId {}", user.getId());
            byte[] decodedKey = Base64.getDecoder().decode(key);
            SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, ENCRYPTION_ALGORITHM);
            // add to local map, too
            keys.put(user.getId(), secretKey);
        }
        return Optional.ofNullable(keys.get(user.getId()));
    }

    public void delete(UserModel user) {
        user.getAttributes().remove(USER_ATTR_PRIV_KEY);
        keys.remove(user.getId());
    }
}
