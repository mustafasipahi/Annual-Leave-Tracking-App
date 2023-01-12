package com.util;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;

import static com.enums.AnnualLeavedPeriodType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserAnnualLeavedPeriodUtilTest {

    @Test
    void shouldReturnONE_TO_FIVEWhenUserCreatedDateNull() {
        assertEquals(ONE_TO_FIVE, UserAnnualLeavedPeriodUtil.getPeriod(null));
    }

    @Test
    void shouldReturnONE_TO_FIVEWhenUserCreatedDateLessThan5() {
        final Date userCreatedDate = DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(4));
        assertEquals(ONE_TO_FIVE, UserAnnualLeavedPeriodUtil.getPeriod(userCreatedDate));
    }

    @Test
    void shouldReturnFIVE_TO_TENWhenUserCreatedDateLessThan10() {
        final Date userCreatedDate = DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(9));
        assertEquals(FIVE_TO_TEN, UserAnnualLeavedPeriodUtil.getPeriod(userCreatedDate));
    }

    @Test
    void shouldReturnTEN_PLUSWhenUserCreatedDateLessMoreThan10() {
        final Date userCreatedDate = DateUtil.add(DateUtil.nowAsDate(), Duration.ofDays(11));
        assertEquals(TEN_PLUS, UserAnnualLeavedPeriodUtil.getPeriod(userCreatedDate));
    }
}