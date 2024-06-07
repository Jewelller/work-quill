package com.qwizery.work_quill.server.base.auth.verify;

import com.qwizery.work_quill.server.base.auth.record.UserLoginRecord;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * SpringSecurity传输登录认证的数据的载体，相当一个Dto
 * 必须是 {@link Authentication} 实现类
 * 这里选择extends{@link AbstractAuthenticationToken}，而不是直接implements Authentication,
 * 是为了少些写代码。因为{@link Authentication}定义了很多接口，我们用不上。
 */
@Getter
@Setter
@Slf4j
public class VerifyAuthentication extends AbstractAuthenticationToken {

    private String jwtToken; // 前端传过来
    private String jwtRefreshToken; // 前端传过来
    private UserLoginRecord currentUser; // 认证成功后，后台从数据库获取信息
    private boolean needSendNewToken = false;

    public VerifyAuthentication() {
        // 权限，用不上，直接null
        super(null);
    }

    @Override
    public Object getCredentials() {
        // log.error("Don't call getCredentials since this instance carry 2 verify credentials");
        return null;
    }

    @Override
    public Object getPrincipal() {
        // log.error("Don't call getPrincipal since this instance does not directly carry principal");
        return null;
    }

}
