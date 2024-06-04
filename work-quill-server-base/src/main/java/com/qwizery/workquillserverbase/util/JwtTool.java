package com.qwizery.workquillserverbase.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JwtTool {

    public static String privateKey = "no-private-key";
    public static String publicKey = "no-public-key";

    public static Long tokenExpireDuration = TimeUnit.MINUTES.toMillis(10);
    public static Long tokenRefreshDuration = TimeUnit.MINUTES.toMillis(30);

    public static void setPublicKey(String publicKey) {
        JwtTool.publicKey = publicKey.trim();
    }
    public static void setPrivateKey(String privateKey) {
        JwtTool.privateKey = privateKey.trim();
    }

    public static void setTokenRefreshDuration(Long tokenRefreshDuration) {
        JwtTool.tokenRefreshDuration = tokenRefreshDuration;
    }

    public static void setTokenExpireDuration(Long tokenExpireDuration) {
        JwtTool.tokenExpireDuration = tokenExpireDuration;
    }

    private static PrivateKey getPrivateKey() {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    private static PublicKey getPublicKey() {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static JwtBuilder createBuilder(Object payload, Date from, Long duration) {
        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .subject("work-quill")
                .claims()
                .add(JSON.parseToMap(JSON.stringify(payload)))
                .expiration(new Date(from.getTime() + duration))
                .and()
                .signWith(getPrivateKey());
    }

    public static String createJws(Object payload, Date from) {
        return createBuilder(payload, from, tokenExpireDuration).compact();
    }

    public static String createRefreshJws(Object payload, Date from) {
        return createBuilder(payload, from, tokenRefreshDuration).compact();
    }

    public static boolean isJwsValid(String jws) {
        return Jwts.parser().verifyWith(getPublicKey()).build().isSigned(jws);
    }
}
