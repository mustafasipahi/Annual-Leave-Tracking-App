package com.service.annual.leave.rule.rules;

import com.dto.UserAnnualLeaveRuleDto;
import com.dto.UserAnnualLeaveRuleResponse;
import com.enums.AnnualLeavedPeriodType;
import com.exception.AnnualLeaveRuleException;
import com.service.annual.leave.rule.UserAnnualLeaveRule;
import com.util.UserAnnualLeavedPeriodUtil;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(300)
@Service
@AllArgsConstructor
public class AvailableAnnualLeaveCountRule implements UserAnnualLeaveRule {

    private static final String MESSAGE_KEY = "unavailable.annual.leave.count";

    @Override
    public UserAnnualLeaveRuleResponse verify(UserAnnualLeaveRuleDto dto) {

        int totalAnnualLeaveDay = dto.getTotalUsedDayCount();
        int requestSize = dto.getRequestedAnnualLeaveDays().size();
        final AnnualLeavedPeriodType period = UserAnnualLeavedPeriodUtil.getPeriod(dto.getUser().getCreatedDate());

        if (requestSize < 1) {
            throw new AnnualLeaveRuleException(MESSAGE_KEY, dto.getLocale());
        }

        if (totalAnnualLeaveDay >= period.getPeriod()) {
            throw new AnnualLeaveRuleException(MESSAGE_KEY, dto.getLocale());
        }

        if (requestSize > period.getPeriod()) {
            throw new AnnualLeaveRuleException(MESSAGE_KEY, dto.getLocale());
        }

        if (Math.addExact(requestSize, totalAnnualLeaveDay) > period.getPeriod()) {
            throw new AnnualLeaveRuleException(MESSAGE_KEY, dto.getLocale());
        }

        return UserAnnualLeaveRuleResponse.builder()
            .continueRule(true)
            .build();
    }
}
