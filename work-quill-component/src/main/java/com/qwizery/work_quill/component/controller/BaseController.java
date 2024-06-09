package com.qwizery.work_quill.component.controller;

import com.qwizery.work_quill.component.model.Result;

public abstract class BaseController {

    protected <T> Result success(String code, String msg, T data) {
        return Result.success(code, msg, data);
    }

    protected <T> Result success(String code, String msg) {
        return Result.success(code, msg);
    }

    protected <T> Result success(String msg, T data) {
        return Result.success(msg, data);
    }

}
