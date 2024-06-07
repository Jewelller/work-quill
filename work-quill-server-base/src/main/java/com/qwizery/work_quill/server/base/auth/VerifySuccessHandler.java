package com.qwizery.work_quill.server.base.auth;

import com.qwizery.work_quill.server.base.util.ExceptionTool;
import com.qwizery.work_quill.server.base.util.JSON;
import com.qwizery.work_quill.server.base.auth.verify.VerifyAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 认证成功/登录成功 事件处理器
 */
@Slf4j
@Component
public class VerifySuccessHandler extends
        AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationSuccessHandler {

    public VerifySuccessHandler() {
        this.setRedirectStrategy((request, response, url) -> {
            // 更改重定向策略，前后端分离项目，后端使用RestFul风格，无需做重定向
            // Do nothing, no redirects in REST
        });
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        if (!(authentication instanceof VerifyAuthentication)) {
            log.error("authentication must be an object of VerifyAuthentication");
            ExceptionTool.throwException("auth.verify.internal_error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.debug("Authentication success");

        var responseWrapper = (ContentCachingResponseWrapper) response;
        responseWrapper.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE); // This warning is intended just for Postman

        if (authentication.isAuthenticated() && ((VerifyAuthentication) authentication).isNeedSendNewToken()) {
            log.debug("Need to append new token to client");

            // Get the cached response body
            byte[] contentAsByteArray = responseWrapper.getContentAsByteArray();
            String responseBody = new String(contentAsByteArray, responseWrapper.getCharacterEncoding());

            // Modify the response body
            String modifiedResponseBody = attachNewToken(responseBody, (VerifyAuthentication) authentication);

            // Write the modified response body back to the response
            responseWrapper.resetBuffer();
            responseWrapper.getWriter().write(modifiedResponseBody);
            responseWrapper.copyBodyToResponse();
        } else {
            responseWrapper.copyBodyToResponse();
        }
    }

    @SuppressWarnings("unchecked")
    private String attachNewToken(String responseBody, VerifyAuthentication authentication) {
        // Appending new token to response data
        log.debug("Modifying response body: {}", responseBody);

        var resp = JSON.parseToMap(responseBody);
        var dataOpt = Optional.ofNullable(resp.get("data"));

        Map<String, Object> data = null;
        if (dataOpt.isPresent()) {
            try {
                data = (Map<String, Object>) dataOpt.get(); // Unchecked cast warning suppressed
            } catch (ClassCastException e) {
                log.error("Cannot cast data({}) to Map<String, Object>", dataOpt.get().getClass().getName());
            }
        } else {
            data = new HashMap<>();
        }

        data.put("token", authentication.getJwtToken());
        data.put("refreshToken", authentication.getJwtRefreshToken());

        resp.put("data", data);

        return JSON.stringify(resp);
    }
}