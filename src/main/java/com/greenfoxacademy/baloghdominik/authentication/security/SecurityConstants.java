package com.greenfoxacademy.baloghdominik.authentication.security;

/**
 * Created by baloghdominik on 2018. 04. 25..
 */

public class SecurityConstants {
    public static final String SECRET = "secret";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 1_800_000L; //half an hour
}