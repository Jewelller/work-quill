package com.qwizery.work_quill.server.base.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;
import java.util.Optional;

@Slf4j
public class LanguageTool {

    public static String defaultLanguage = "en_US";

    public static Locale getRequestLanguage() {
        var requestAttributes = Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (requestAttributes.isPresent()) {
            var language = requestAttributes.get().getRequest().getHeader("Accept-Language");
            return Locale.of(
                    language == null || language.isEmpty() ? defaultLanguage : language);
        }
        return Locale.of(defaultLanguage);
    }
}
