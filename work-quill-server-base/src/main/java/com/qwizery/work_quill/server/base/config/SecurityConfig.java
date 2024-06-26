package com.qwizery.work_quill.server.base.config;

import com.qwizery.work_quill.server.base.auth.LoginFailureHandler;
import com.qwizery.work_quill.server.base.auth.LoginSuccessHandler;
import com.qwizery.work_quill.server.base.auth.VerifyFailureHandler;
import com.qwizery.work_quill.server.base.auth.VerifySuccessHandler;
import com.qwizery.work_quill.server.base.auth.exception.AuthenticationFailedExceptionHandler;
import com.qwizery.work_quill.server.base.auth.exception.AuthorizationAccessDeniedHandler;
import com.qwizery.work_quill.server.base.auth.exception.SecurityExceptionHandler;
import com.qwizery.work_quill.server.base.auth.username.UsernameAuthenticationFilter;
import com.qwizery.work_quill.server.base.auth.username.UsernameAuthenticationProvider;
import com.qwizery.work_quill.server.base.auth.verify.VerifyAuthenticationFilter;
import com.qwizery.work_quill.server.base.auth.verify.VerifyAuthenticationProvider;
import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Slf4j
@RefreshScope
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ApplicationContext applicationContext;

    public SecurityConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Value("${app.base.api.prefix}${app.base.api.username-login-path}")
    private String usernameLoginPath;

    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Resource
    private VerifySuccessHandler verifySuccessHandler;

    @Resource
    private VerifyFailureHandler verifyFailureHandler;

    private final AuthenticationEntryPoint authenticationExceptionHandler = new AuthenticationFailedExceptionHandler();
    private final AccessDeniedHandler authorizationExceptionHandler = new AuthorizationAccessDeniedHandler();
    private final Filter globalSpringSecurityExceptionHandler = new SecurityExceptionHandler();

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
        commonHttpSetting(http);

        var usernameAuthenticationProvider = applicationContext.getBean(UsernameAuthenticationProvider.class);

        // 使用 securityMatcher限定当前配置起作用的路径
        http.securityMatcher("/**")
                .authorizeHttpRequests(request -> request.anyRequest().authenticated());

        // 加一个登录方式: 用户名、密码登录
        UsernameAuthenticationFilter usernameLoginFilter = new UsernameAuthenticationFilter(
                new AntPathRequestMatcher(usernameLoginPath, HttpMethod.POST.name()),
                new ProviderManager(
                        List.of(usernameAuthenticationProvider)),
                loginSuccessHandler,
                loginFailureHandler
        );
        http.addFilterBefore(usernameLoginFilter, UsernamePasswordAuthenticationFilter.class);

        // 其他登录方式加在这里
        // ...

        // 验证登录
        // verify login
        VerifyAuthenticationProvider authProvider = applicationContext.getBean(VerifyAuthenticationProvider.class);
        VerifyAuthenticationFilter authFilter = new VerifyAuthenticationFilter(
                usernameLoginPath,
                AntPathRequestMatcher.antMatcher("/**"),
                new ProviderManager(List.of(authProvider)),
                verifySuccessHandler,
                verifyFailureHandler
        );
        http.addFilterBefore(authFilter, UsernameAuthenticationFilter.class);

        return http.build();
    }

}
