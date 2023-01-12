package com.dto;

import com.entity.UserEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAnnualLeaveRuleDto {

    private UserEntity user;
    private List<LocalDate> requestedAnnualLeaveDays;
    private int totalUsedDayCount;
    private int approvedDaysCount;
}
