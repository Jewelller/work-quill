package com.qwizery.work_quill.server.base.auth;

import com.qwizery.work_quill.server.base.auth.verify.VerifyAuthenticationProvider;
import com.qwizery.work_quill.server.base.model.Result;
import com.qwizery.work_quill.server.base.util.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class VerifyFailureHandler implements AuthenticationFailureHandler {
    /**
     * 这里的 {@link AuthenticationException} 一般会是从 {@link VerifyAuthenticationProvider} 抛出
     **/
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.debug("Authentication failed, returning error message");
        String errorMessage = exception.getMessage();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        Result responseData = Result.error(errorMessage);
        writer.print(JSON.stringify(responseData));
        writer.flush();
        writer.close();
    }
}
