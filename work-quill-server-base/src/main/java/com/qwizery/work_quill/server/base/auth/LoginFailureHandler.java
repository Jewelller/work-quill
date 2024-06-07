package com.qwizery.work_quill.server.base.auth;

import com.qwizery.work_quill.server.base.auth.username.UsernameAuthenticationProvider;
import com.qwizery.work_quill.server.base.model.Result;
import com.qwizery.work_quill.server.base.util.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * AbstractAuthenticationProcessingFilter抛出AuthenticationException异常后，会跑到这里来
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    /**
     * 这里的 {@link AuthenticationException} 一般会是从 {@link UsernameAuthenticationProvider} 抛出
     **/
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String errorMessage = exception.getMessage();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        Result responseData = Result.error(errorMessage);
        writer.print(JSON.stringify(responseData));
        writer.flush();
        writer.close();
    }
}
