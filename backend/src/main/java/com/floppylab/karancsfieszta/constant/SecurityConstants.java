package com.floppylab.karancsfieszta.constant;

public final class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/api/authenticate";

    // Signing key for HS512 algorithm
    public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYf3s6v9y$B&E(H+MbQeTtWmZq4t7w!z%C*F-J@NcRg";

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";

    private SecurityConstants() { }
}