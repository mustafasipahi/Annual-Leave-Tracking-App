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

@Order(100)
@Service
@AllArgsConstructor
public class AdvanceFirstYearRule implements UserAnnualLeaveRule {

    private static final int ADVANCE_ANNUAL_LEAVE_COUNT = 5;
    private static final String MESSAGE_KEY = "advance.first.year";

    @Override
    public UserAnnualLeaveRuleResponse verify(UserAnnualLeaveRuleDto dto) {

        if (isUserInFirstYear(dto.getUser().getCreatedDate())) {

            int totalAnnualLeaveDay = dto.getTotalUsedDayCount();
            int requestSize = dto.getRequestedAnnualLeaveDays().size();

            if (totalAnnualLeaveDay >= ADVANCE_ANNUAL_LEAVE_COUNT) {
                throw new AnnualLeaveRuleException(MESSAGE_KEY, dto.getLocale());
            }

            if (requestSize > ADVANCE_ANNUAL_LEAVE_COUNT) {
                throw new AnnualLeaveRuleException(MESSAGE_KEY, dto.getLocale());
            }

            if (Math.addExact(requestSize, totalAnnualLeaveDay) > ADVANCE_ANNUAL_LEAVE_COUNT) {
                throw new AnnualLeaveRuleException(MESSAGE_KEY, dto.getLocale());
            }

            return UserAnnualLeaveRuleResponse.builder()
                .continueRule(false)
                .build();
        }

        return UserAnnualLeaveRuleResponse.builder()
            .continueRule(true)
            .build();
    }

    private boolean isUserInFirstYear(Date userCreateDate) {
        final Date now = DateUtil.nowAsDate();
        final Date oneYearLater = DateUtil.add(userCreateDate, Duration.ofDays(365));
        return oneYearLater.compareTo(now) <= 0;
    }
}
