package com.service.annual.leave.rule;

import com.dto.UserAnnualLeaveRuleDto;
import com.dto.UserAnnualLeaveRuleResponse;

public interface UserAnnualLeaveRule {

    UserAnnualLeaveRuleResponse verify(UserAnnualLeaveRuleDto dto);
}
