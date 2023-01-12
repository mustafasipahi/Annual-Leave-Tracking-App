package com.service.impl;

import com.dto.UserAnnualLeaveCreateDto;
import com.dto.UserAnnualLeaveRuleDto;
import com.dto.UserAnnualLeaveRuleResponse;
import com.entity.AnnualLeaveEntity;
import com.entity.UserEntity;
import com.enums.AnnualLeaveStatus;
import com.service.AnnualLeaveService;
import com.service.UserService;
import com.service.annual.leave.rule.UserAnnualLeaveRule;
import com.service.annual.leave.rule.rules.AdvanceFirstYearRule;
import com.service.annual.leave.rule.rules.WorkedForLeastOneYearRule;
import com.calculator.AvailableDaysCalculator;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAnnualLeaveServiceTest {

    @InjectMocks
    private UserAnnualLeaveService userAnnualLeaveService;

    @Mock
    private UserService userService;
    @Mock
    private AnnualLeaveService annualLeaveService;
    @Mock
    private WorkedForLeastOneYearRule workedForLeastOneYearRule;
    @Mock
    private AdvanceFirstYearRule advanceFirstYearRule;

    @Spy
    private List<UserAnnualLeaveRule> rules = new ArrayList<>();

    @BeforeEach
    public void setup() {
        rules.add(workedForLeastOneYearRule);
        rules.add(advanceFirstYearRule);
    }

    @Test
    void shouldCreate() {

        final UserAnnualLeaveCreateDto dto = UserAnnualLeaveCreateDto.builder()
            .userId(RandomUtils.nextLong())
            .startDate(LocalDate.now().minusDays(1))
            .endDate(LocalDate.now().plusDays(1))
            .build();

        final UserEntity user = UserEntity.builder()
            .id(dto.getUserId())
            .build();

        UserAnnualLeaveRuleResponse ruleResponse1 = UserAnnualLeaveRuleResponse.builder()
            .continueRule(true)
            .build();

        UserAnnualLeaveRuleResponse ruleResponse2 = UserAnnualLeaveRuleResponse.builder()
            .continueRule(false)
            .build();

        when(userService.detail(dto.getUserId())).thenReturn(user);
        when(workedForLeastOneYearRule.verify(any(UserAnnualLeaveRuleDto.class))).thenReturn(ruleResponse1);
        when(advanceFirstYearRule.verify(any(UserAnnualLeaveRuleDto.class))).thenReturn(ruleResponse2);
        userAnnualLeaveService.create(dto);

        verify(userService).detail(dto.getUserId());
        verify(annualLeaveService).getTotalUsedDayCount(dto.getUserId());

        ArgumentCaptor<AnnualLeaveEntity> captor = ArgumentCaptor.forClass(AnnualLeaveEntity.class);
        verify(annualLeaveService).save(captor.capture());
        AnnualLeaveEntity captorValue = captor.getValue();

        int totalUsedDayCount = AvailableDaysCalculator.getAvailableDays(dto.getStartDate(), dto.getEndDate()).size();

        assertEquals(user.getId(), captorValue.getUser().getId());
        assertEquals(dto.getStartDate(), captorValue.getStartDate());
        assertEquals(dto.getEndDate(), captorValue.getEndDate());
        assertEquals(totalUsedDayCount, captorValue.getCount());
        assertEquals(AnnualLeaveStatus.WAITING_APPROVE, captorValue.getStatus());
    }
}