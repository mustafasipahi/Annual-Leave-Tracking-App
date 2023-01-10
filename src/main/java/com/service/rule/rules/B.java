package com.service.rule.rules;

import com.dto.UserAnnualLeaveCreateDto;
import com.service.rule.UserAnnualLeaveRule;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(200)
@Service
@AllArgsConstructor
public class B implements UserAnnualLeaveRule {

    @Override
    public void verify(UserAnnualLeaveCreateDto dto) {

    }
}
