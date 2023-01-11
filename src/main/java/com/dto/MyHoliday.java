package com.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyHoliday {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
