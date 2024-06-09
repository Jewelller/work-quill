package com.qwizery.work_quill.server.base.config;

import com.qwizery.work_quill.component.i18n.LanguageTool;
import com.qwizery.work_quill.server.base.util.JwtTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

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
