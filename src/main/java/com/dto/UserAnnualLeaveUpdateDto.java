package com.dto;

import com.enums.AnnualLeaveStatus;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Locale;

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
    private Locale locale;
}
