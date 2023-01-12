package com.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAnnualLeaveResponse implements Serializable {

    List<UserAnnualLeaveDto> annualLeaveList;
}
