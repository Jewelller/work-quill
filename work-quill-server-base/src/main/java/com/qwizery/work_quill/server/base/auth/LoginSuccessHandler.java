package com.qwizery.work_quill.server.base.auth;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qwizery.work_quill.server.base.mapper.RoleMapper;
import com.qwizery.work_quill.server.base.model.Result;
import com.qwizery.work_quill.server.base.util.JSON;
import com.qwizery.work_quill.server.base.auth.record.UserLoginRecord;
import com.qwizery.work_quill.server.base.entity.Role;
import com.qwizery.work_quill.server.base.util.ExceptionTool;
import com.qwizery.work_quill.server.base.util.JwtTool;
import com.qwizery.work_quill.server.base.util.TimeTool;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证成功/登录成功 事件处理器
 */
@Component
public class LoginSuccessHandler extends
        AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationSuccessHandler {

    @Resource
    private RoleMapper roleMapper;

    public LoginSuccessHandler() {
        this.setRedirectStrategy((request, response, url) -> {
            // 更改重定向策略，前后端分离项目，后端使用RestFul风格，无需做重定向
            // Do nothing, no redirects in REST
        });
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserLoginRecord)) {
            ExceptionTool.throwException(
                    "登陆认证成功后，authentication.getPrincipal()返回的Object对象必须是：UserLoginInfo！");
        }
        UserLoginRecord currentUser = (UserLoginRecord) principal;

        List<String> roleList = roleMapper.selectList(
                Wrappers.<Role>query().eq("user_id", currentUser.userId())
        ).stream()
                .map(Role::getRole)
                .toList();

        Date now = TimeTool.getNowDate();

        var finalInfo = currentUser.getUserLoginInfoRecord(roleList, now.getTime() + JwtTool.tokenExpireDuration);
        // 生成token和refreshToken
        Map<String, Object> responseData = new LinkedHashMap<>();
        responseData.put("token", JwtTool.createJws(finalInfo, now));
        responseData.put("refreshToken", JwtTool.createRefreshJws(finalInfo, now));

        // 虽然APPLICATION_JSON_UTF8_VALUE过时了，但也要用。因为Postman工具不声明utf-8编码就会出现乱码
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        writer.print(JSON.stringify(Result.success("auth.login.success", responseData)));
        writer.flush();
        writer.close();
    }

}
