package com.qwizery.work_quill.component.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Slf4j
public class I18nMessageTool {
    private static final ReloadableResourceBundleMessageSource localMessageSource;

    static {
        localMessageSource = new ReloadableResourceBundleMessageSource();
        localMessageSource.setBasename("classpath:i18n/messages");
        localMessageSource.setDefaultEncoding("UTF-8");
        localMessageSource.setCacheSeconds(3600);
        localMessageSource.setAlwaysUseMessageFormat(false);
        localMessageSource.setFallbackToSystemLocale(true);
    }

    public static String translate(String key, Object... args) {
        if (localMessageSource == null) { // 本地国际化文件不存在
            return key;
        }

        try {
            var requestLocale = LanguageTool.getRequestLanguage();
            return localMessageSource.getMessage(key, args, requestLocale);
        } catch (NoSuchMessageException e) {
            log.error(e.getMessage(), e);
        }
        return key;
    }
}
