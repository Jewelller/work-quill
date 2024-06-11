package com.qwizery.work_quill.server.base.auth.verify;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qwizery.work_quill.server.base.auth.record.UserLoginInfoRecord;
import com.qwizery.work_quill.server.base.entity.Authority;
import com.qwizery.work_quill.server.base.entity.User;
import com.qwizery.work_quill.server.base.exception.NoSuchElementException;
import com.qwizery.work_quill.server.base.mapper.AuthorityMapper;
import com.qwizery.work_quill.server.base.mapper.UserMapper;
import com.qwizery.work_quill.server.base.util.ExceptionTool;
import com.qwizery.work_quill.server.base.util.JwtTool;
import com.qwizery.work_quill.server.base.util.TimeTool;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 帐号密码登录认证
 */
@Slf4j
@Component
public class VerifyAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AuthorityMapper authorityMapper;

    public VerifyAuthenticationProvider() {
        super();
    }

    // 检测jwt内容是否合法
    private UserLoginInfoRecord verify(Date nowDate, String token) {
        JwtParser parser = JwtTool.getJwtParser();
        Claims payload = parser.parseSignedClaims(token).getPayload();

        var userIdOpt = Optional.ofNullable((String) payload.get("userId"));
        var usernameOpt = Optional.ofNullable((String) payload.get("username"));

        Long userId = Long.valueOf(userIdOpt.orElseThrow(() -> new NoSuchElementException("auth.verify.invalid_token")));
        String username = usernameOpt.orElseThrow(() -> new NoSuchElementException("auth.verify.invalid_token"));

        var userOpt = Optional.ofNullable(userMapper.selectOne(Wrappers.lambdaQuery(User.of(userId, username))));
        User user = userOpt.orElseThrow(() -> new NoSuchElementException("auth.verify.invalid_token"));

        var userRolesOpt = Optional.ofNullable(authorityMapper.selectList(Wrappers.lambdaQuery(Authority.ofId(user.getUserId()))));
        List<String> userRoles = userRolesOpt
                .orElseThrow(() -> new NoSuchElementException("auth.verify.invalid_token"))
                .stream()
                .map(Authority::getRole)
                .toList();


        return user.getUserLoginRecord().getUserLoginInfoRecord(userRoles, TimeTool.getExpireTime(nowDate, JwtTool.tokenExpireDuration));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof VerifyAuthentication)) {
            log.error("VerifyAuthenticationProvider requires a VerifyAuthentication instance");
            ExceptionTool.throwException("auth.verify.error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        var verifyAuth = (VerifyAuthentication) authentication;

        // 用户提交的 JWT token：
        String token = verifyAuth.getJwtToken();
        String refreshToken = verifyAuth.getJwtRefreshToken();

        // 查看 Jwt token 是否合法
        if (JwtTool.isJwsValid(token)) {
            log.debug("Token valid");
            // 都合法：不需要补充新的token
            var userInfo = verify(TimeTool.getNowDate(), token);

            verifyAuth.setJwtToken(token);
            verifyAuth.setJwtRefreshToken(refreshToken);
            verifyAuth.setUserInfo(userInfo);
            verifyAuth.setAuthenticated(true);

        } else if (JwtTool.isJwsValid(refreshToken)) {
            log.debug("Refresh token valid");
            // 仅refreshToken合法：重新给予两个新的token
            var nowDate = TimeTool.getNowDate();
            var userInfo = verify(nowDate, refreshToken);

            verifyAuth.setJwtToken(JwtTool.createJws(userInfo, nowDate));
            verifyAuth.setJwtRefreshToken(JwtTool.createJws(refreshToken, nowDate));
            verifyAuth.setAuthenticated(true);
            verifyAuth.setUserInfo(userInfo);
            verifyAuth.setNeedSendNewToken(true);
        } else {
            // 两个token都失效，不返回任何token给 VerifyAuthenticationFilter
            log.debug("all token invalid");
            verifyAuth.setAuthenticated(false);

            ExceptionTool.throwException("auth.verify.invalid_token");
        }

        return verifyAuth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(VerifyAuthentication.class);
    }
}

