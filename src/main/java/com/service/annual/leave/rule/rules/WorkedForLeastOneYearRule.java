package com.service.annual.leave.rule.rules;

import com.dto.UserAnnualLeaveRuleDto;
import com.dto.UserAnnualLeaveRuleResponse;
import com.exception.AnnualLeaveRuleException;
import com.service.annual.leave.rule.UserAnnualLeaveRule;
import com.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Order(200)
@Service
@AllArgsConstructor
public class WorkedForLeastOneYearRule implements UserAnnualLeaveRule {

    private static final String MESSAGE_KEY = "worked.for.least.one.year";

    @Override
    public UserAnnualLeaveRuleResponse verify(UserAnnualLeaveRuleDto dto) {

        final Date userCreatedDate = DateUtil.add(dto.getUser().getCreatedDate(), Duration.ofDays(365));
        final Date now = DateUtil.nowAsDate();

        if (userCreatedDate.compareTo(now) > 0) {
            throw new AnnualLeaveRuleException(MESSAGE_KEY);
        }

        return UserAnnualLeaveRuleResponse.builder()
            .continueRule(true)
            .build();
    }
}
