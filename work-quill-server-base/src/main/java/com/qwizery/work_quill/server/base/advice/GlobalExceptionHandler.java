package com.qwizery.work_quill.server.base.advice;

import com.qwizery.work_quill.component.model.Result;
import com.qwizery.work_quill.server.base.exception.BaseException;
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
        return Result.error("error.internal_error");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public Result noResourceFoundException(HttpServletResponse resp, NoResourceFoundException e) {
        log.warn("Resource not found: {}", e.getMessage());
        return Result.error("error.resource_not_found");
    }

    @ExceptionHandler(BaseException.class)
    public Result baseException(HttpServletResponse resp, BaseException e) {
        log.warn("Handling base exception({}): {}", e.getClass().getName(), e.getMessage());
        resp.setStatus(e.getHttpStatus().value());
        return new Result.Builder()
                .code(e.getCode() == null ? Result.ERROR_CODE : e.getCode())
                .msg(e.getMessage())
                .build();
    }

}
