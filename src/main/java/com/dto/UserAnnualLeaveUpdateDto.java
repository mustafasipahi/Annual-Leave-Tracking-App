package com.dto;

import com.enums.AnnualLeaveStatus;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAnnualLeaveUpdateDto {

    @NotNull
    private Long annualLeaveId;
    @NotNull
    private AnnualLeaveStatus status;
}
