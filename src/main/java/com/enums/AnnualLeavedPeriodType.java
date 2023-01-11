package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AnnualLeavedPeriodType {

    ONE_TO_FIVE(15),
    FIVE_TO_TEN(18),
    TEN_PLUS(24);

    private int period;
}
