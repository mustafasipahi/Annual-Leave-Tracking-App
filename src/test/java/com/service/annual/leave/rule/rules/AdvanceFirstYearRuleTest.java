package com.service.annual.leave.rule.rules;

import com.dto.UserAnnualLeaveRuleDto;
import com.dto.UserAnnualLeaveRuleResponse;
import com.entity.UserEntity;
import com.exception.AnnualLeaveRuleException;
import com.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.constant.ErrorCodes.INVALID_RULE;
import static com.service.annual.leave.rule.rules.AdvanceFirstYearRule.ADVANCE_ANNUAL_LEAVE_COUNT;
import static com.service.annual.leave.rule.rules.AdvanceFirstYearRule.MESSAGE_KEY;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
class AdvanceFirstYearRuleTest {

    @InjectMocks
    private AdvanceFirstYearRule advanceFirstYearRule;

    @Test
    void shouldVerifyWhenIsUserInFirstYearFalse() {

        final UserEntity user = UserEntity.builder()
            .createdDate(DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(370)))
            .build();

        final UserAnnualLeaveRuleDto dto = UserAnnualLeaveRuleDto.builder()
            .user(user)
            .build();

        UserAnnualLeaveRuleResponse verify = advanceFirstYearRule.verify(dto);
        assertTrue(verify.isContinueRule());
    }

    @Test
    void shouldThrowAnnualLeaveRuleExceptionWhenTotalUsedDayCountMoreThan5() {

        final List<LocalDate> requestedAnnualLeaveDays = Stream.iterate(LocalDate.now(), date -> date.plusDays(1))
            .limit(3)
            .collect(Collectors.toList());

        final UserEntity user = UserEntity.builder()
            .createdDate(DateUtil.nowAsDate())
            .build();

        final UserAnnualLeaveRuleDto dto = UserAnnualLeaveRuleDto.builder()
            .user(user)
            .totalUsedDayCount(ADVANCE_ANNUAL_LEAVE_COUNT + 1)
            .requestedAnnualLeaveDays(requestedAnnualLeaveDays)
            .build();

        final AnnualLeaveRuleException exception = assertThrows(
            AnnualLeaveRuleException.class,
            () -> advanceFirstYearRule.verify(dto)
        );
        assertEquals(INVALID_RULE, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals(MESSAGE_KEY, exception.getMessage());
    }

    @Test
    void shouldThrowAnnualLeaveRuleExceptionWhenRequestSizeMoreThan5() {

        final List<LocalDate> requestedAnnualLeaveDays = Stream.iterate(LocalDate.now(), date -> date.plusDays(1))
            .limit(ADVANCE_ANNUAL_LEAVE_COUNT + 1)
            .collect(Collectors.toList());

        final UserEntity user = UserEntity.builder()
            .createdDate(DateUtil.nowAsDate())
            .build();

        final UserAnnualLeaveRuleDto dto = UserAnnualLeaveRuleDto.builder()
            .user(user)
            .totalUsedDayCount(ADVANCE_ANNUAL_LEAVE_COUNT - 1)
            .requestedAnnualLeaveDays(requestedAnnualLeaveDays)
            .build();

        final AnnualLeaveRuleException exception = assertThrows(
            AnnualLeaveRuleException.class,
            () -> advanceFirstYearRule.verify(dto)
        );
        assertEquals(INVALID_RULE, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals(MESSAGE_KEY, exception.getMessage());
    }

    @Test
    void shouldThrowAnnualLeaveRuleExceptionWhenTotalDaysCountMoreThan5() {

        final List<LocalDate> requestedAnnualLeaveDays = Stream.iterate(LocalDate.now(), date -> date.plusDays(1))
            .limit(3)
            .collect(Collectors.toList());

        final UserEntity user = UserEntity.builder()
            .createdDate(DateUtil.nowAsDate())
            .build();

        final UserAnnualLeaveRuleDto dto = UserAnnualLeaveRuleDto.builder()
            .user(user)
            .totalUsedDayCount(ADVANCE_ANNUAL_LEAVE_COUNT - 2)
            .requestedAnnualLeaveDays(requestedAnnualLeaveDays)
            .build();

        final AnnualLeaveRuleException exception = assertThrows(
            AnnualLeaveRuleException.class,
            () -> advanceFirstYearRule.verify(dto)
        );
        assertEquals(INVALID_RULE, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals(MESSAGE_KEY, exception.getMessage());
    }

    @Test
    void shouldVerify() {

        final List<LocalDate> requestedAnnualLeaveDays = Stream.iterate(LocalDate.now(), date -> date.plusDays(1))
            .limit(1)
            .collect(Collectors.toList());

        final UserEntity user = UserEntity.builder()
            .createdDate(DateUtil.nowAsDate())
            .build();

        final UserAnnualLeaveRuleDto dto = UserAnnualLeaveRuleDto.builder()
            .user(user)
            .totalUsedDayCount(ADVANCE_ANNUAL_LEAVE_COUNT - 2)
            .requestedAnnualLeaveDays(requestedAnnualLeaveDays)
            .build();

        final UserAnnualLeaveRuleResponse verify = advanceFirstYearRule.verify(dto);
        assertFalse(verify.isContinueRule());
    }
}