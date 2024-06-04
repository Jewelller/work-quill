package com.qwizery.workquillserverbase.util;

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

@Slf4j
public class JwtTool {

    public static String privateKey = "no-private-key";
    public static String publicKey = "no-public-key";

    public static void setPublicKey(String publicKey) {
        JwtTool.publicKey = publicKey.trim();
    }

    public static void setPrivateKey(String privateKey) {
        JwtTool.privateKey = privateKey.trim();
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

    public static String CreateJws(Object payload, Date expireTime) {
        return Jwts.builder()
                .header()
                .add("my-head", "head")
                .and()
                .subject("work-quill")
                .claims()
                .add("claim-key", "claim-value")
                .expiration(expireTime)
                .and()
                .signWith(getPrivateKey())
                .compact();
    }

    public static boolean isJwsValid(String jws) {
        return Jwts.parser().verifyWith(getPublicKey()).build().isSigned(jws);
    }
}
