package com.dto;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAnnualLeaveRuleDto {

    private Locale locale;
    private UserDto user;
    private int totalAnnualLeave;
}
