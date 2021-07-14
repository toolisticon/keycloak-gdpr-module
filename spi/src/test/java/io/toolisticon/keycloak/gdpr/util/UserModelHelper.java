package io.toolisticon.keycloak.gdpr.util;

import org.keycloak.models.UserModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserModelHelper {

    public static UserModel buildUser(String userId) {
        UserModel user = mock(UserModel.class);
        Map<String, List<String>> userAttrinutes = new HashMap<>();
        when(user.getAttributes()).thenReturn(userAttrinutes);
        when(user.getId()).thenReturn(userId);
        return user;
    }

    public static UserModel buildUser() {
        return buildUser(UUID.randomUUID().toString());
    }

}
