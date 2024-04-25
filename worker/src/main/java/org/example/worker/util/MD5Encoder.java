package org.example.worker.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class MD5Encoder {
    public static String getMD5Hash(String message) {
        return DigestUtils.md5DigestAsHex(message.getBytes(StandardCharsets.UTF_8));
    }
}
