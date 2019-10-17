package com.crawler.line.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAEncoder {
    public static String encode(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());

        StringBuilder builder = new StringBuilder();
        for (byte b : md.digest()) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
