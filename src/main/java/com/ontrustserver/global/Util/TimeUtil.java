package com.ontrustserver.global.Util;

import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TimeUtil {
    public static DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss zzzz");
    }

    /**
     * @return ZonedDateTime At UTC
     */
    public static ZonedDateTime nowUtcDate(){
        return ZonedDateTime.now(ZoneOffset.UTC);
    }
}
