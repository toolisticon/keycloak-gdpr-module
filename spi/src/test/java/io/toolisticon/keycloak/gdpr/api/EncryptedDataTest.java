package io.toolisticon.keycloak.gdpr.api;

import org.junit.jupiter.api.Test;
import org.keycloak.models.UserModel;

import static io.toolisticon.keycloak.gdpr.util.UserModelHelper.buildUser;
import static org.junit.jupiter.api.Assertions.*;

class EncryptedDataTest {

    @Test
    void shouldCompareForDifferentUsers() {
        EncryptedData data1 = new EncryptedData(buildUser().getId(), "Neque porro quisquam est");
        EncryptedData data2 = new EncryptedData(buildUser().getId(), "Neque porro quisquam est");
        assertNotEquals(data1.toString(), data2.toString());
    }
    @Test
    void shouldCompareForDifferentCipher() {
        UserModel user = buildUser();
        EncryptedData data1 = new EncryptedData(user.getId(), "Neque porro quisquam est");
        EncryptedData data2 = new EncryptedData(user.getId(),"lorem ipsum");
        assertNotEquals(data1, data2);
        assertNotEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    void shouldHideSensitiveInformationInToString() {
        UserModel user = buildUser();
        EncryptedData data1 = new EncryptedData(user.getId(), "Neque porro quisquam est");
        EncryptedData data2 = new EncryptedData(user.getId(),"lorem ipsum");
        EncryptedData data3 = new EncryptedData(buildUser().getId(), "Neque porro quisquam est");
        EncryptedData data4 = new EncryptedData(buildUser().getId(), "Neque porro quisquam est");
        assertEquals(data1.toString(), data2.toString());
        assertNotEquals(data1.toString(), data3.toString());
        assertNotEquals(data1.toString(), data4.toString());
    }
}
