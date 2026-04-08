package com.example.loginapi.util;

public class TokenUtil {

    public static Long getUserIdFromAuthHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7).trim();
        if (!token.startsWith("token-")) {
            return null;
        }
        try {
            return Long.parseLong(token.substring(6));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
