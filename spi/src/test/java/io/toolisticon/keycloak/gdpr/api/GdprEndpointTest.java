package io.toolisticon.keycloak.gdpr.api;

import io.toolisticon.keycloak.gdpr.crypto.EncryptionService;
import io.toolisticon.keycloak.gdpr.crypto.KeyService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.keycloak.common.ClientConnection;
import org.keycloak.models.*;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;
import java.util.HashMap;

import static io.toolisticon.keycloak.gdpr.util.UserModelHelper.buildUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GdprEndpointTest {

    static GdprEndpoint endpoint;
    static KeycloakSession session;
    static EncryptionService encryptionService;
    static RealmModel realmModel;

    @BeforeAll
    static void setup() throws Exception {
        session = mock(KeycloakSession.class);
        encryptionService = new EncryptionService(new KeyService());
        KeycloakContext ctx = mock(KeycloakContext.class);
        HttpHeaders httpHeaders = mock(HttpHeaders.class);
        KeycloakUriInfo urInfo = mock(KeycloakUriInfo.class);
        realmModel = mock(RealmModel.class);
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
        void shouldFailDecryptDueToAccessProblems() {
            EncryptedData data = new EncryptedData();
            data.setCipherText("lorem ipsum");
            data.setUserId("1");
            assertThrows(NotAuthorizedException.class, () -> {
                endpoint.decrypt(data);
            });
        }

        @Test
        void shouldFailEncryptDueToAccessProblems() {
            DecryptedData data = new DecryptedData();
            data.setData("lorem ipsum");
            data.setUserId("1");
            assertThrows(NotAuthorizedException.class, () -> {
                endpoint.encrypt(data);
            });
        }


    }

    @Nested
    class Crypto {
        UserModel user;

        @BeforeEach
        void setup() throws Exception {
            user = buildUser();
            UserProvider userProvider = mock(UserProvider.class);
            when(userProvider.getUserById(realmModel, user.getId())).thenReturn(user);
            when(session.users()).thenReturn(userProvider);
            endpoint = new GdprEndpoint(session, new EncryptionService(new KeyService())) {
                @Override
                protected void checkAccessRights() {
                    // ignore checks
                }

                @Override
                protected RealmModel getRealmModel() {
                    return realmModel;
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
            DecryptedData data = new DecryptedData(user.getId(), "lorem ipsum");
            EncryptedData result = endpoint.encrypt(data);
            EncryptedData result2 = endpoint.encrypt(data);
            assertNotNull(result);
            assertEquals(result2, result);
            assertEquals(result2.hashCode(), result.hashCode());
            assertEquals(data.getUserId(), result.getUserId());
            assertNotEquals(data.getData(), result.toString());
            assertNotEquals(data.getData(), result.getCipherText());
        }

        @Test
        void shouldEncryptDataInBatch() {
            DecryptedBatchData data = new DecryptedBatchData(user.getId(), new HashMap<String, String>() {
                {
                    put("text", "lorem ipsum");
                }
            });
            EncryptedBatchData result = endpoint.encrypt(data);
            EncryptedBatchData result2 = endpoint.encrypt(data);
            assertNotNull(result);
            assertEquals(result2, result);
            assertEquals(result2.hashCode(), result.hashCode());
            assertEquals(data.getUserId(), result.getUserId());
            assertNotEquals(data.getData().toString(), result.toString());
            assertNotEquals(data.getData().get("text"), result.getCipherTextEntries().get("text"));
        }

        @Test
        void shouldNotEncryptWithUnknownUser() {
            DecryptedData data =  new DecryptedData(buildUser().getId(),"lorem ipsum");
            assertThrows(BadRequestException.class, () -> {
                endpoint.encrypt(data);
            });
        }

        @Test
        void shouldDecryptData() {
            DecryptedData data1 = new DecryptedData(user.getId(), "Neque porro quisquam est");
            DecryptedData data2 = new DecryptedData(user.getId(),"lorem ipsum");
            DecryptedData result1 = endpoint.decrypt(endpoint.encrypt(data1));
            DecryptedData result2 = endpoint.decrypt(endpoint.encrypt(data2));
            assertNotNull(result1);
            assertNotNull(result2);
            assertEquals(data1, result1);
            assertEquals(data1.hashCode(), result1.hashCode());
            assertEquals(data1.toString(), result1.toString());
            assertNotEquals(data1, result2);
            assertNotEquals(data1.hashCode(), result2.hashCode());
            assertEquals(data1.toString(), result2.toString());
            assertEquals(data2, result2);
            assertEquals(data2.hashCode(), result2.hashCode());
            assertEquals(data2.toString(), result2.toString());
            assertNotEquals(data2, result1);
            assertNotEquals(data2.hashCode(), result1.hashCode());
            assertEquals(data2.toString(), result1.toString());
        }

        @Test
        void shouldDecryptDataInBatch() {
            DecryptedBatchData data = new DecryptedBatchData(user.getId(), new HashMap<String, String>() {
                {
                    put("text", "lorem ipsum");
                }
            });
            data.setUserId(user.getId());
            DecryptedBatchData result = endpoint.decrypt(endpoint.encrypt(data));
            assertNotNull(result);
            assertEquals(data, result);
            assertEquals(data.hashCode(), result.hashCode());
            assertEquals(data.toString(), result.toString());
        }

        @Test
        void shouldNotDecryptInvalid() {
            EncryptedData data = new EncryptedData(user.getId(), "lorem ipsum");
            assertThrows(BadRequestException.class, () -> {
                endpoint.decrypt(data);
            });
        }
    }

}
