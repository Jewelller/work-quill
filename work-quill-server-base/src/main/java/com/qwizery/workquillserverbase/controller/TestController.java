package com.qwizery.workquillserverbase.controller;

import com.qwizery.workquillserverbase.model.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.base.api.prefix}")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/vip")
    public String getVipIndex() {
        return "Vip index";
    }

    @GetMapping("/vip/about")
    public String getVipAbout() {
        return "Vip about";
    }

    @GetMapping("/resources")
    public String getResourcesIndex() {
        return "resources";
    }

    @GetMapping("/resources/about")
    public String getResourcesAbout() {
        return "resources about";
    }

    @GetMapping("/test/i18n")
    public Result getI18nIndex(HttpServletRequest request, HttpServletResponse response) {
        log.info("Locale from request: {}", request.getHeader("Accept-Language"));
        return Result.success("text.message.test");
    }
}
