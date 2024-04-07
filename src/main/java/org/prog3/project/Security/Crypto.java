package org.prog3.project.Security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Crypto {
    public static String applySHA256(String input) {    // SHA256 hash algorithm
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(); // This will contain hash as hexadecimal
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: sign and verify a message - functions 2
}
