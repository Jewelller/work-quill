package com.qwizery.work_quill.server.base.controller;

import com.qwizery.work_quill.component.controller.BaseController;
import com.qwizery.work_quill.server.base.auth.verify.VerifyAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizedController extends BaseController {
    protected boolean hasRole(String role) {
        var authentication = (VerifyAuthentication) SecurityContextHolder.getContext().getAuthentication();
        var userInfo = authentication.getUserInfo();
        return userInfo.roles().stream().anyMatch((r) -> r.equals(role));
    }
}
