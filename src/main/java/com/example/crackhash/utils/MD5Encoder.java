package com.example.crackhash.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encoder {
    public static String getMD5Hash(String message) {
        return DigestUtils.md5DigestAsHex(message.getBytes(StandardCharsets.UTF_8));
    }
}
