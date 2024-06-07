package com.qwizery.work_quill.server.base.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeTool {

    public static Date getNowDate() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static long getNowMilli() {
        return System.currentTimeMillis();
    }

    public static Long getExpireTime(Date from, long duration) {
        return from.getTime() + duration;
    }
}
