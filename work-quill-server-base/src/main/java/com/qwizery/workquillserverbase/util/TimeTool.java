package com.qwizery.workquillserverbase.util;

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
}
