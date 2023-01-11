package com.dto;

import com.entity.UserEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAnnualLeaveRuleDto {

    private Locale locale;
    private UserEntity user;
    private List<LocalDate> requestedAnnualLeaveDays;
    private int totalUsedDayCount;
    private int approvedDaysCount;
}
