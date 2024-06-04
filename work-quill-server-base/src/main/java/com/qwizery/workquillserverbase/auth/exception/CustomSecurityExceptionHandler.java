package com.qwizery.workquillserverbase.auth.exception;

import com.qwizery.workquillserverbase.exception.BaseException;
import com.qwizery.workquillserverbase.model.Result;
import com.qwizery.workquillserverbase.util.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 捕捉Spring security filter chain 中抛出的未知异常
 */
public class CustomSecurityExceptionHandler extends OncePerRequestFilter {

    public static final Logger logger = LoggerFactory.getLogger(
            CustomSecurityExceptionHandler.class);

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                 @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BaseException e) {
            // 自定义异常
            Result result = new Result.Builder()
                    .code(e.getCode())
                    .msg(e.getMessage())
                    .build();

            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(e.getHttpStatus().value());
            PrintWriter writer = response.getWriter();

            writer.write(JSON.stringify(result));
            writer.flush();
            writer.close();

        } catch (AuthenticationException | AccessDeniedException e) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            PrintWriter writer = response.getWriter();
            writer.print(JSON.stringify(Result.error(e.getMessage())));
            writer.flush();
            writer.close();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 未知异常
            Result result = Result.error("error.internal_error");

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            PrintWriter writer = response.getWriter();
            writer.write(JSON.stringify(result));
            writer.flush();
            writer.close();
        }
    }
}
