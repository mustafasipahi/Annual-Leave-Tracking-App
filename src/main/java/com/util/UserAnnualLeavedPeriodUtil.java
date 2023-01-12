package com.util;

import com.enums.AnnualLeavedPeriodType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAnnualLeavedPeriodUtil {

    public static AnnualLeavedPeriodType getPeriod(Date userCreatedDate) {
        return Optional.ofNullable(userCreatedDate)
            .map(date -> {
                long diffInMill = Math.abs(DateUtil.nowAsDate().getTime() - userCreatedDate.getTime());
                long diffDays = TimeUnit.DAYS.convert(diffInMill, TimeUnit.MILLISECONDS);
                if (diffDays <= 5) {
                    return AnnualLeavedPeriodType.ONE_TO_FIVE;
                } else if (diffDays <= 10) {
                    return AnnualLeavedPeriodType.FIVE_TO_TEN;
                } else {
                    return AnnualLeavedPeriodType.TEN_PLUS;
                }
            }).orElse(AnnualLeavedPeriodType.ONE_TO_FIVE);
    }
}
