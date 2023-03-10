package com.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAnnualLeaveCreateDto {

    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
}
