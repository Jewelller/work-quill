package com.qwizery.work_quill.server.base.controller;

import com.qwizery.work_quill.component.controller.BaseController;
import com.qwizery.work_quill.component.model.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
@RequestMapping("${app.base.api.prefix}")
public class SecuredController extends BaseController {

    @Resource
    private RestTemplate rest;

    @Value("${app.service.protocol}")
    private String serviceProtocol;

    @Value("${app.service.name}")
    private String serviceName;

    @RequestMapping("/**")
    public Result getRedirect(HttpServletRequest request) {
        return rest.getForObject(serviceProtocol + "://" + serviceName + request.getRequestURI(), Result.class);
    }
}
