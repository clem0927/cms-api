package com.min.cms.security.jwt;

public class JwtUtil {

    public static String resolveToken(String bearer) {
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}