package com.qwizery.workquillserverbase.auth.username;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qwizery.workquillserverbase.entity.User;
import com.qwizery.workquillserverbase.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 帐号密码登录认证
 */
@Component
public class UsernameAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    public UsernameAuthenticationProvider() {
        super();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 用户提交的用户名 + 密码：
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        var userOpt = Optional.ofNullable(userMapper.selectOne(Wrappers.<User>query().eq("username", username)));
        if (userOpt.isEmpty() || !passwordEncoder.matches(password, userOpt.get().getPassword())) {
            // 用户不存在或密码错误，直接抛异常
            // 根据SpringSecurity框架的代码逻辑，认证失败时，应该抛这个异常：org.springframework.security.core.AuthenticationException
            // BadCredentialsException就是这个异常的子类
            // 抛出异常后后，AuthenticationFailureHandler的实现类会处理这个异常。
            throw new BadCredentialsException("auth.login.failed.bad_credential");
        }

        UsernameAuthentication token = new UsernameAuthentication();
        token.setCurrentUser(userOpt.get().getUserLoginRecord());
        token.setAuthenticated(true); // 认证通过，这里一定要设成true
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernameAuthentication.class);
    }
}

