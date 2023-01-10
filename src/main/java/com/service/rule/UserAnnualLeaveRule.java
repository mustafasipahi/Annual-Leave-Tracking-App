package com.service.rule;

import com.dto.UserAnnualLeaveCreateDto;

public interface UserAnnualLeaveRule {

    void verify(UserAnnualLeaveCreateDto dto);
}
