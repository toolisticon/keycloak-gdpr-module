package io.toolisticon.keycloak.gdpr;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ContextPaths
{

    /**
     * the base path for all GDPR end points
     */
    public static final String GDPR_BASE_PATH = "gdpr";

    /**
     * the path to the {@link io.toolisticon.keycloak.gdpr.api.GdprEndpoint} class <br/>
     * note that by default the base {@link #GDPR_BASE_PATH} is prepended to this path since it has been used as
     * provider factory id
     */
    public static final String GDPR_ENDPOINT_PATH = "/v1";

    /**
     * context path to the GDPR administration
     */
    public static final String ADMIN = "/admin";
}
