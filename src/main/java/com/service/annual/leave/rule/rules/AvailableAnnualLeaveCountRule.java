package com.service.annual.leave.rule.rules;

import com.dto.UserAnnualLeaveRuleDto;
import com.enums.AnnualLeavedPeriodType;
import com.exception.AnnualLeaveRuleException;
import com.service.annual.leave.rule.UserAnnualLeaveRule;
import com.util.UserAnnualLeavedPeriodUtil;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(200)
@Service
@AllArgsConstructor
public class AvailableAnnualLeaveCountRule implements UserAnnualLeaveRule {

    @Override
    public void verify(UserAnnualLeaveRuleDto dto) {

        int availableAnnualLeaveCount = availableAnnualLeaveCount(dto);

        if (dto.getRequestedAnnualLeave() > availableAnnualLeaveCount) {
            throw new AnnualLeaveRuleException("unavailable.annual.leave.count", dto.getLocale());
        }
    }

    private int availableAnnualLeaveCount(UserAnnualLeaveRuleDto dto) {
        final AnnualLeavedPeriodType period = UserAnnualLeavedPeriodUtil.getPeriod(dto.getUser().getCreatedDate());
        return Math.subtractExact(period.getPeriod(), dto.getTotalAnnualLeave());
    }
}
