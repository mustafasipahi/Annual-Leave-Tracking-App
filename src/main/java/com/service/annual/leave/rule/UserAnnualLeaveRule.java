package com.service.annual.leave.rule;

import com.dto.UserAnnualLeaveRuleDto;

public interface UserAnnualLeaveRule {

    void verify(UserAnnualLeaveRuleDto dto);
}
