package com.qwizery.work_quill.server.base.exception;

import org.springframework.http.HttpStatus;

public class NoSuchElementException extends BaseException {
    private static final String DEFAULT_ERROR_CODE = "missing_token";
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public NoSuchElementException(String msg, HttpStatus httpStatus, String code) {
        super(new java.util.NoSuchElementException(), msg, httpStatus, code);
    }

    public NoSuchElementException(String msg, String code) {
        this(msg, DEFAULT_HTTP_STATUS, code);
    }

    public NoSuchElementException(String msg) {
        this(msg, DEFAULT_ERROR_CODE);
    }
}
