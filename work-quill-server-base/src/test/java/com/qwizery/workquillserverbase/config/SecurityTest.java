package com.qwizery.workquillserverbase.config;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SecurityTest {

    private static final Logger log = LoggerFactory.getLogger(SecurityTest.class);

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void encodeTest() {
        var result = passwordEncoder.encode("password");
        log.info(result);

        // The encoded password doesn't have a prefix {bcrypt}
        Assertions.assertFalse(result.startsWith("{bcrypt}"));

        var result2 = passwordEncoder.encode("password");
        Assertions.assertTrue(passwordEncoder.matches("password", result));
        Assertions.assertTrue(passwordEncoder.matches("password", result2));
    }
}