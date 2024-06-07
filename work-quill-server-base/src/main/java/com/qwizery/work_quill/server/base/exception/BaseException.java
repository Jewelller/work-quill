package com.qwizery.work_quill.server.base.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseException extends RuntimeException {

    public static final String DEFAULT_ERROR_CODE = "error";
    public static final String DEFAULT_ERROR_MESSAGE = "error.unknown_error";

    private HttpStatus httpStatus;
    private String code;

    /**
     * A null cause indicates that the cause might be unknown or not exist, so fill it if you can
     **/
    protected BaseException(Throwable cause, String message, HttpStatus httpStatus, String code) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.code = code;
    }

    protected BaseException(String message, HttpStatus httpStatus, String code) {
        this(null, message, httpStatus, code);
    }

    protected BaseException(Throwable cause, String message) {
        this(cause, message, HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_ERROR_CODE);
    }

    protected BaseException() {
        this(DEFAULT_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_ERROR_CODE);
    }

    public static class Builder {
        private final BaseException baseException;

        private Builder(Throwable cause, String msg) {
            this.baseException = new BaseException(cause, msg);
        }

        private Builder() {
            this.baseException = new BaseException();
        }

        public static Builder ofUnknown() {
            return new Builder();
        }

        public static Builder of(Throwable cause, String msg) {
            return new Builder(cause, msg);
        }

        public Builder code(String code) {
            this.baseException.code = code;
            return this;
        }

        public Builder status(HttpStatus httpStatus) {
            this.baseException.httpStatus = httpStatus;
            return this;
        }

        public BaseException build() {
            return baseException;
        }
    }
}
