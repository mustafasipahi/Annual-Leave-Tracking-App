package com.service.annual.leave.rule.rules;

import com.dto.UserAnnualLeaveRuleDto;
import com.service.annual.leave.rule.UserAnnualLeaveRule;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(200)
@Service
@AllArgsConstructor
public class B implements UserAnnualLeaveRule {

    @Override
    public void verify(UserAnnualLeaveRuleDto dto) {

    }
}
