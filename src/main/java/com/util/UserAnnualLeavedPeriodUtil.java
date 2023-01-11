package com.util;

import com.enums.AnnualLeavedPeriodType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAnnualLeavedPeriodUtil {

    public static AnnualLeavedPeriodType getPeriod(Date userCreatedDate) {
        return Optional.ofNullable(userCreatedDate)
            .map(date -> {
                long a = Math.abs(DateUtil.nowAsDate().getTime() - userCreatedDate.getTime());
                if (a <= 5) {
                    return AnnualLeavedPeriodType.ONE_TO_FIVE;
                } else if (a <= 10) {
                    return AnnualLeavedPeriodType.FIVE_TO_TEN;
                } else {
                    return AnnualLeavedPeriodType.TEN_PLUS;
                }
            }).orElse(AnnualLeavedPeriodType.ONE_TO_FIVE);
    }
}
