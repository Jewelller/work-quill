package com.qwizery.workquillserverbase.advice;

import com.qwizery.workquillserverbase.exception.BaseException;
import com.qwizery.workquillserverbase.model.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result exception(HttpServletResponse resp, Exception e) {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        log.error("Server Internal Error: {}", e.getMessage(), e);
        return Result.error("${error.internal_error}");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public void noResourceFoundException(HttpServletResponse resp, NoResourceFoundException e) {
        log.warn("Resource not found: {}", e.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    public Result baseException(HttpServletResponse resp, BaseException e) {
        resp.setStatus(e.getHttpStatus().value());
        return new Result.Builder()
                .code(e.getCode() == null ? Result.ERROR_CODE : e.getCode())
                .msg(e.getMessage())
                .build();
    }
}
