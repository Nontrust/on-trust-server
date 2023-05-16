package com.ontrustserver.global.config;

import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class TimeUtil {
    private final static String KTC = "Asia/Seoul";
    public static DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss zzzz");
    }

    /**
     * @return ZonedDateTime At UTC
     */
    public static ZonedDateTime nowUtcDate(){
        return ZonedDateTime.from(Instant.now().atZone(ZoneOffset.UTC).toInstant());
    }

    public static ZonedDateTime convertUtcToKtc(Date utcDate){
        ZoneId utcZoneId = ZoneId.of(KTC);
        ZonedDateTime utcZonedDateTime = utcDate.toInstant().atZone(utcZoneId);

        return utcZonedDateTime.withZoneSameInstant(utcZoneId);
    }
}
