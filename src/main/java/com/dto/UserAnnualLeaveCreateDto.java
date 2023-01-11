package com.dto;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAnnualLeaveCreateDto {

    private Long userId;
    private Locale locale;
}
