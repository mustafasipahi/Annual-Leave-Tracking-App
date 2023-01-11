package com.service.impl;

import com.dto.UserAnnualLeaveCreateDto;
import com.dto.UserAnnualLeaveRuleDto;
import com.dto.UserAnnualLeaveRuleResponse;
import com.entity.AnnualLeaveEntity;
import com.enums.AnnualLeaveStatus;
import com.service.AnnualLeaveService;
import com.service.UserService;
import com.service.annual.leave.rule.UserAnnualLeaveRule;
import com.util.AvailableDaysCalculator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class UserAnnualLeaveService {

    private final List<UserAnnualLeaveRule> rules;
    private final UserService userService;
    private final AnnualLeaveService annualLeaveService;

    public void create(UserAnnualLeaveCreateDto dto, Locale locale) {
        final UserAnnualLeaveRuleDto ruleDto = convertToRuleDto(dto, locale);

        for (UserAnnualLeaveRule rule : rules) {
            UserAnnualLeaveRuleResponse verify = rule.verify(ruleDto);
            if (!verify.isContinueRule()) {
                break;
            }
        }

        annualLeaveService.save(AnnualLeaveEntity.builder()
            .user(ruleDto.getUser())
            .startDate(dto.getStartDate())
            .endDate(dto.getEndDate())
            .count(ruleDto.getRequestedAnnualLeaveDays().size())
            .status(AnnualLeaveStatus.WAITING_APPROVE)
            .build());
    }

    private UserAnnualLeaveRuleDto convertToRuleDto(UserAnnualLeaveCreateDto dto, Locale locale) {
        return UserAnnualLeaveRuleDto.builder()
            .locale(locale)
            .user(userService.detail(dto.getUserId(), locale))
            .totalUsedDayCount(annualLeaveService.getTotalUsedDayCount(dto.getUserId()))
            .requestedAnnualLeaveDays(AvailableDaysCalculator.getAvailableDays(dto.getStartDate(), dto.getEndDate(), locale))
            .build();
    }
}
