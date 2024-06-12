package com.qwizery.work_quill.server.base.controller;

import com.qwizery.work_quill.component.controller.BaseController;
import com.qwizery.work_quill.component.model.Result;
import com.qwizery.work_quill.server.base.util.JSON;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@RestController
@RefreshScope
@RequestMapping("${app.base.api.prefix}")
public class ForwardSecuredController extends BaseController {

    @Resource
    private RestTemplate rest;

    @Value("${app.service.protocol}")
    private String serviceProtocol;

    @Value("${app.service.name}")
    private String serviceName;

    @RequestMapping("/**")
    public Result getRedirect(HttpServletRequest request) {
        log.debug("[{}] Redirecting to {}://{}{}", request.getMethod(), serviceProtocol, serviceName, request.getRequestURI());

        Map<String, Object> requestBody = JSON.parseToMap((String) request.getAttribute("requestBody"));
        switch (request.getMethod()) {
            case "GET" -> {
                return rest.getForObject(serviceProtocol + "://" + serviceName + request.getRequestURI(), Result.class);
            }
            case "POST" -> {
                return rest.postForObject(serviceProtocol + "://" + serviceName + request.getRequestURI(), requestBody, Result.class);
            }
            case "DELETE" -> {
                RequestCallback requestCallback = rest.httpEntityCallback(requestBody, Result.class);
                Object[] uriVariables = request.getParameterMap().keySet().toArray();
                HttpMessageConverterExtractor<Result> responseExtractor = new HttpMessageConverterExtractor<>(Result.class, rest.getMessageConverters());
                return rest.execute(serviceProtocol + "://" + serviceName + request.getRequestURI(), HttpMethod.DELETE, requestCallback, responseExtractor, uriVariables);
            }
        }

        // This shouldn't reach
        return Result.error();
    }
}
