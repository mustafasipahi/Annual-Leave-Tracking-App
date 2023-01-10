package com.service;

import com.dto.UserAnnualLeaveCreateDto;
import com.dto.UserAnnualLeaveDto;
import com.dto.UserAnnualLeaveUpdateDto;

import java.util.List;

public interface UserAnnualLeaveService {

    Long create(UserAnnualLeaveCreateDto dto);
    Long update(UserAnnualLeaveUpdateDto dto);
    List<UserAnnualLeaveDto> list(Long userId);
}
