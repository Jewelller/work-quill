package com.qwizery.work_quill.server.base.config;

import com.qwizery.work_quill.server.base.util.JwtTool;
import com.qwizery.work_quill.server.base.util.LanguageTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {

    @Value("${app.default-language}")
    private String defaultLanguage;

    /* Jwt Configuration */
    @Value("${app.jwt.expire-time}")
    private long jwtExpireTime;

    @Value("${app.jwt.refresh-time}")
    private long jwtRefreshTime;

    @Value("${app.jwt.private-key}")
    private String jwtPrivateKey;

    @Value("${app.jwt.public-key}")
    private String jwtPublicKey;

    @Bean
    public int initStatic() {
        LanguageTool.defaultLanguage = this.defaultLanguage;

        // Jwt Configuration
        JwtTool.setTokenExpireDuration(TimeUnit.MINUTES.toMillis(jwtExpireTime));
        JwtTool.setTokenRefreshDuration(TimeUnit.MINUTES.toMillis(jwtRefreshTime));
        JwtTool.setPrivateKey(jwtPrivateKey);
        JwtTool.setPublicKey(jwtPublicKey);
        return 0;
    }
}
