package org.prog3.project.kriptotry;

import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;

@Getter
@Setter
public class Block {
    private int id;
    private int nonce;
    private long timeStamp;
    private String hash;
    private String prevHash;
    private String data;

    public Block(int id, String transaction, String prevHash) {
        this.id = id;
        this.data = transaction;
        this.prevHash = prevHash;
        this.timeStamp = new Date().getTime();
        this.hash = generateHash();
    }

    public String generateHash() {
        try {
            String data = prevHash + timeStamp + nonce + this.data;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexadecimalString = new StringBuilder();
            for (byte b : hash) {
                String hexadecimal = Integer.toHexString(0xff & b);
                if (hexadecimal.length() == 1) hexadecimalString.append('0');
                hexadecimalString.append(hexadecimal);
            }
            return hexadecimalString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void incrementNonce() {
        this.nonce++;
    }
}
