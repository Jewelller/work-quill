package com.qwizery.work_quill.server.base.util;

import com.qwizery.work_quill.server.base.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ExceptionTool {
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String DEFAULT_ERROR_CODE = "error";

    public static void throwException(Throwable cause, String message, HttpStatus httpStatus, String code) {
        throw BaseException.Builder.of(cause, message).status(httpStatus).code(code).build();
    }

    public static void throwException(String message, HttpStatus httpStatus, String code) {
        throwException(null, message, httpStatus, code);
    }

    public static void throwException(String message, HttpStatus httpStatus) {
        throwException(message, httpStatus, DEFAULT_ERROR_CODE);
    }

    public static void throwException(String message) {
        throwException(message, DEFAULT_HTTP_STATUS, DEFAULT_ERROR_CODE);
    }

}
