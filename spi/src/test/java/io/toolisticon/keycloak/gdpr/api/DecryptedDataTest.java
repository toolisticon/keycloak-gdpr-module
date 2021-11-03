package io.toolisticon.keycloak.gdpr.api;

import org.junit.jupiter.api.Test;
import org.keycloak.models.UserModel;

import static io.toolisticon.keycloak.gdpr.util.UserModelHelper.buildUser;
import static org.junit.jupiter.api.Assertions.*;

class DecryptedDataTest {

    @Test
    void shouldCompareForDifferentUsers() {
        DecryptedData data1 = new DecryptedData(buildUser().getId(), "Neque porro quisquam est");
        DecryptedData data2 = new DecryptedData(buildUser().getId(), "Neque porro quisquam est");
        assertNotEquals(data1.toString(), data2.toString());
    }

    @Test
    void shouldCompareForDifferentData() {
        UserModel user = buildUser();
        DecryptedData data1 = new DecryptedData(user.getId(), "Neque porro quisquam est");
        DecryptedData data2 = new DecryptedData(user.getId(),"lorem ipsum");
        assertNotEquals(data1, data2);
        assertNotEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    void shouldHideSensitiveInformationInToString() {
        UserModel user = buildUser();
        DecryptedData data1 = new DecryptedData(user.getId(), "Neque porro quisquam est");
        DecryptedData data2 = new DecryptedData(user.getId(),"lorem ipsum");
        DecryptedData data3 = new DecryptedData(buildUser().getId(), "Neque porro quisquam est");
        DecryptedData data4 = new DecryptedData(buildUser().getId(), "Neque porro quisquam est");
        assertEquals(data1.toString(), data2.toString());
        assertNotEquals(data1.toString(), data3.toString());
        assertNotEquals(data1.toString(), data4.toString());
    }
}
