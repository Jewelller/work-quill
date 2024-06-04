package com.qwizery.workquillserverbase.config;

import com.qwizery.workquillserverbase.auth.exception.CustomAuthenticationExceptionHandler;
import com.qwizery.workquillserverbase.auth.exception.CustomAuthorizationExceptionHandler;
import com.qwizery.workquillserverbase.auth.exception.CustomSecurityExceptionHandler;
import com.qwizery.workquillserverbase.filter.TestFilter;
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;

import javax.sql.DataSource;

@Slf4j
@RefreshScope
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationEntryPoint authenticationExceptionHandler = new CustomAuthenticationExceptionHandler();
    private final AccessDeniedHandler authorizationExceptionHandler = new CustomAuthorizationExceptionHandler();
    private final Filter globalSpringSecurityExceptionHandler = new CustomSecurityExceptionHandler();

    /**
     * 禁用不必要的默认 Filter, 处理异常相应内容
     **/
    private void commonHttpSetting(HttpSecurity http) throws Exception {
//        禁用SpringSecurity默认filter。这些filter都是非前后端分离项目的产物，用不上.
//                yml配置文件将日志设置DEBUG模式，就能看到加载了哪些filter
//        logging:
//          level:
//              org.springframework.security: DEBUG
//        表单登录 / 登出、session管理、csrf防护等默认配置，如果不disable。会默认创建默认filter
        http.formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                // requestCache用于重定向，前后端分析项目无需重定向，requestCache也用不上
                .requestCache(cache -> cache
                        .requestCache(new NullRequestCache())
                )
                // 无需给用户一个匿名身份
                .anonymous(AbstractHttpConfigurer::disable);

        // 处理 SpringSecurity 异常响应结果。响应数据的结构，改成业务统一的JSON结构。不要框架默认的响应结构
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        // 认证失败异常
                        .authenticationEntryPoint(authenticationExceptionHandler)
                        // 鉴权失败异常
                        .accessDeniedHandler(authorizationExceptionHandler)
        );
        // 其他未知异常. 尽量提前加载。
        http.addFilterBefore(globalSpringSecurityExceptionHandler, SecurityContextHolderFilter.class);
    }


    /* 不鉴权的Api */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        commonHttpSetting(http); // 干掉不要的filter
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());

        http.addFilterBefore(new TestFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    UserDetailsManager users(DataSource dataSource) {

        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER", "ADMIN")
                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        if (!users.userExists("user")) users.createUser(user);
        if (!users.userExists("admin")) users.createUser(admin);

        return users;
    }

}
