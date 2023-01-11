package com.dto;

import com.enums.AnnualLeaveStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAnnualLeaveDto implements Serializable {

    private static final long serialVersionUID = 2616432353085933212L;

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int count;
    private AnnualLeaveStatus status;
    private Date createdDate;
}
