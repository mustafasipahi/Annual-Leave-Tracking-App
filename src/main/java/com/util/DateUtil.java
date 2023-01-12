package com.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {

    public static Long getTimeInMillis() {
        return System.currentTimeMillis();
    }

    public static Date nowAsDate() {
        return new Date(getTimeInMillis());
    }

    public static Date add(Date date, Duration duration) {
        return new Date(date.getTime() + duration.toMillis());
    }

    public static Date sub(Date date, Duration duration) {
        return new Date(date.getTime() - duration.toMillis());
    }
}
