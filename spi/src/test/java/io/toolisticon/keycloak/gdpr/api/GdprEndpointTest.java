package io.toolisticon.keycloak.gdpr.api;

import io.toolisticon.keycloak.gdpr.crypto.EncryptionService;
import io.toolisticon.keycloak.gdpr.crypto.KeyService;
import org.bouncycastle.util.encoders.DecoderException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.keycloak.common.ClientConnection;
import org.keycloak.models.KeycloakContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakUriInfo;
import org.keycloak.models.RealmModel;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GdprEndpointTest {

    static GdprEndpoint endpoint;
    static KeycloakSession session;
    static EncryptionService encryptionService;


    @BeforeAll
    static void setup() throws Exception {
        session = mock(KeycloakSession.class);
        encryptionService = new EncryptionService(new KeyService());
        KeycloakContext ctx = mock(KeycloakContext.class);
        HttpHeaders httpHeaders = mock(HttpHeaders.class);
        KeycloakUriInfo urInfo = mock(KeycloakUriInfo.class);
        RealmModel realmModel = mock(RealmModel.class);
        MultivaluedMap<String, String> requestHeaders = new MultivaluedHashMap<>();
        requestHeaders.putSingle(HttpHeaders.AUTHORIZATION, "BEARER 124555");
        when(session.getContext()).thenReturn(ctx);
        when(ctx.getRealm()).thenReturn(realmModel);
        when(ctx.getUri()).thenReturn(urInfo);
        when(ctx.getConnection()).thenReturn(mock(ClientConnection.class));
        when(ctx.getRequestHeaders()).thenReturn(httpHeaders);
        when(realmModel.getName()).thenReturn("test");
        when(urInfo.getBaseUri()).thenReturn(URI.create("http://localhost:8080/auth"));
        when(httpHeaders.getRequestHeaders()).thenReturn(requestHeaders);
    }

    @Nested
    class Access_Management {

        @BeforeEach
        void setup() {
            endpoint = new GdprEndpoint(session, encryptionService);
        }

        @Test
        void shouldInit() {
            assertNotNull(endpoint.getAdminApiRoot());
            assertNotNull(endpoint.getKeycloakSession());
        }

        @Test
        void shouldFailDueToAccessProblems() {
            EncryptedData data = new EncryptedData();
            data.setCipherText("lorem ipsum");
            data.setUserId("1");
            assertThrows(NotAuthorizedException.class, () -> {
                endpoint.decrypt(data);
            });
        }

    }

    @Nested
    class Crypto {

        @BeforeEach
        void setup() throws Exception {
            endpoint = new GdprEndpoint(session, new EncryptionService(new KeyService())) {
                @Override
                protected void checkAccessRights() {
                    // ignore checks
                }
            };
        }

        @Test
        void shouldInit() {
            assertNotNull(endpoint.getAdminApiRoot());
            assertNotNull(endpoint.getKeycloakSession());
        }

        @Test
        void shouldEncryptData() {
            DecryptedData data = new DecryptedData();
            data.setData("lorem ipsum");
            data.setUserId("1");
            EncryptedData result = endpoint.encrypt(data);
            assertNotNull(result);
            assertEquals(data.getUserId(), result.getUserId());
            assertNotEquals(data.getData(), result.toString());
            assertNotEquals(data.getData(), result.getCipherText());
        }

        @Test
        void shouldDecryptData() {
            DecryptedData data = new DecryptedData();
            data.setData("lorem ipsum");
            data.setUserId("1");
            DecryptedData result = endpoint.decrypt(endpoint.encrypt(data));
            assertNotNull(result);
            assertEquals(data, result);
            assertEquals(data.toString(), result.toString());
        }

        @Test
        void shouldNotDecryptInvalid() {
            EncryptedData data = new EncryptedData();
            data.setCipherText("lorem ipsum");
            data.setUserId("1");
            assertThrows(DecoderException.class, () -> {
                endpoint.decrypt(data);
            });
        }
    }

}
