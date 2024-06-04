package com.qwizery.workquillserverbase.config;

import com.qwizery.workquillserverbase.util.JwtTool;
import com.qwizery.workquillserverbase.util.LanguageTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${app.default-language}")
    private String defaultLanguage;

    @Value("${app.jwt.private-key}")
    private String jwtPrivateKey;

    @Value("${app.jwt.public-key}")
    private String jwtPublicKey;

    @Bean
    public int initStatic() {
        LanguageTool.defaultLanguage = this.defaultLanguage;
        
        JwtTool.privateKey = jwtPrivateKey;
        JwtTool.publicKey = jwtPublicKey;
        return 0;
    }
}
