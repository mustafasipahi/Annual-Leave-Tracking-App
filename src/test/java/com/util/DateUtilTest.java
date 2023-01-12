package com.util;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void shouldGetTimeInMillis() {
        assertEquals(System.currentTimeMillis(), DateUtil.getTimeInMillis());
    }

    @Test
    void shouldGetNowAsDate() {
        assertEquals(new Date(System.currentTimeMillis()), DateUtil.nowAsDate());
    }

    @Test
    void shouldAdd() {
        final Date now = DateUtil.nowAsDate();
        final Duration duration = Duration.ofDays(1);
        assertEquals(new Date(now.getTime() + duration.toMillis()), DateUtil.add(now, duration));
    }

    @Test
    void shouldSub() {
        final Date now = DateUtil.nowAsDate();
        final Duration duration = Duration.ofDays(1);
        assertEquals(new Date(now.getTime() - duration.toMillis()), DateUtil.sub(now, duration));
    }
}