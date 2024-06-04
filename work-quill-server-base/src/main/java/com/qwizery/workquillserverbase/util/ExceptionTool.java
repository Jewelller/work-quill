package com.qwizery.workquillserverbase.util;

import com.qwizery.workquillserverbase.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ExceptionTool {
    private static final HttpStatus defaultHttpStatus = HttpStatus.BAD_REQUEST;

    public static void throwException(String message, HttpStatus httpStatus, String errorCode) {
        BaseException baseException = new BaseException(message, httpStatus);
        baseException.setCode(errorCode);
        throw baseException;
    }

    public static void throwException(String message) {
        throw new BaseException(message, defaultHttpStatus);
    }

    public static void throwException(boolean throwException, String message) {
        if (throwException) {
            throw new BaseException(message, defaultHttpStatus);
        }
    }

    public static void throwException(HttpStatus httpStatus) {
        throw new BaseException(httpStatus);
    }


    public static void throwException(String message, String code) {
        throwException(message, defaultHttpStatus, code);
    }

    public static void throwException(String message, HttpStatus httpStatus) {
        throw new BaseException(message, httpStatus);
    }

    public static void throwException(String message, Throwable e) {
        throw new BaseException(message, e, defaultHttpStatus);
    }


}
