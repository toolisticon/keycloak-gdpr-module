package io.toolisticon.keycloak.gdpr;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ContextPaths
{

    /**
     * the base path for all GDPR end points
     */
    public static final String GDPR_ENDPOINT_PATH = "gdpr";

    /**
     * context path to the GDPR administration
     */
    public static final String ADMIN = "/admin";
}
