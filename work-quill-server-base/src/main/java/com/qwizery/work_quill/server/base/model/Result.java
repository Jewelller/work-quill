package com.qwizery.work_quill.server.base.model;

import com.qwizery.work_quill.server.base.util.I18nMessageTool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    private String code;
    private String msg;
    private Object data;

    public static final String SUCCESS_CODE = "success";
    public static final String ERROR_CODE = "error";

    public static <T> Result success(String code, String msg, T data) {
        return new Builder().code(code).msg(msg).data(data).build();
    }

    public static <T> Result success(String msg, T data) {
        return success(SUCCESS_CODE, msg, data);
    }

    public static Result success(String msg) {
        return success(msg, null);
    }

    public static Result success() {
        return success(SUCCESS_CODE, null, null);
    }

    public static <T> Result error(String code, String msg, T data) {
        return new Builder().code(ERROR_CODE).msg(msg).data(data).build();
    }

    public static <T> Result error(String msg, T data) {
        return error(ERROR_CODE, msg, data);
    }

    public static Result error(String msg) {
        return error(msg, null);
    }

    public static Result error() {
        return error(ERROR_CODE, null, null);
    }

    public static class Builder {
        private final Result result;

        public Builder() {
            result = new Result();
        }

        public Builder code(String code) {
            result.code = code;
            return this;
        }

        public Builder msg(String msg) {
            // Build the message with i18n
            result.msg = I18nMessageTool.translate(msg);
            return this;
        }

        public <T> Builder data(T data) {
            result.data = data;
            return this;
        }

        public Result build() {
            return result;
        }
    }
}
