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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.constant.ErrorCodes.INVALID_RULE;
import static com.enums.AnnualLeavedPeriodType.ONE_TO_FIVE;
import static com.enums.AnnualLeavedPeriodType.TEN_PLUS;
import static com.service.annual.leave.rule.rules.AvailableAnnualLeaveCountRule.MESSAGE_KEY;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
class AvailableAnnualLeaveCountRuleTest {

    @InjectMocks
    private AvailableAnnualLeaveCountRule availableAnnualLeaveCountRule;

    @Test
    void shouldThrowAnnualLeaveRuleExceptionVerifyWhenRequestSizeLessThan1() {

        final UserEntity user = UserEntity.builder()
            .createdDate(DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(200)))
            .build();

        final UserAnnualLeaveRuleDto dto = UserAnnualLeaveRuleDto.builder()
            .user(user)
            .requestedAnnualLeaveDays(Collections.emptyList())
            .build();

        final AnnualLeaveRuleException exception = assertThrows(
            AnnualLeaveRuleException.class,
            () -> availableAnnualLeaveCountRule.verify(dto)
        );

        assertEquals(INVALID_RULE, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals(MESSAGE_KEY, exception.getMessage());
    }

    @Test
    void shouldThrowAnnualLeaveRuleExceptionVerifyWhenTotalAnnualLeaveDayLessThanPeriod() {

        final UserEntity user = UserEntity.builder()
            .createdDate(DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(200)))
            .build();

        final UserAnnualLeaveRuleDto dto = UserAnnualLeaveRuleDto.builder()
            .user(user)
            .requestedAnnualLeaveDays(Collections.singletonList(LocalDate.now()))
            .totalUsedDayCount(70)
            .build();

        final AnnualLeaveRuleException exception = assertThrows(
            AnnualLeaveRuleException.class,
            () -> availableAnnualLeaveCountRule.verify(dto)
        );

        assertEquals(INVALID_RULE, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals(MESSAGE_KEY, exception.getMessage());
    }

    @Test
    void shouldThrowAnnualLeaveRuleExceptionVerifyWhenRequestSizeMoreThanPeriod() {

        final List<LocalDate> requestedAnnualLeaveDays = Stream.iterate(LocalDate.now(), date -> date.plusDays(1))
            .limit(TEN_PLUS.getPeriod() + 5)
            .collect(Collectors.toList());

        final UserEntity user = UserEntity.builder()
            .createdDate(DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(200)))
            .build();

        final UserAnnualLeaveRuleDto dto = UserAnnualLeaveRuleDto.builder()
            .user(user)
            .requestedAnnualLeaveDays(requestedAnnualLeaveDays)
            .totalUsedDayCount(10)
            .build();

        final AnnualLeaveRuleException exception = assertThrows(
            AnnualLeaveRuleException.class,
            () -> availableAnnualLeaveCountRule.verify(dto)
        );

        assertEquals(INVALID_RULE, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals(MESSAGE_KEY, exception.getMessage());
    }

    @Test
    void shouldThrowAnnualLeaveRuleExceptionVerifyWhenTotalRequestMoreThanPeriod() {

        final List<LocalDate> requestedAnnualLeaveDays = Stream.iterate(LocalDate.now(), date -> date.plusDays(1))
            .limit(TEN_PLUS.getPeriod() - 1)
            .collect(Collectors.toList());

        final UserEntity user = UserEntity.builder()
            .createdDate(DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(200)))
            .build();

        final UserAnnualLeaveRuleDto dto = UserAnnualLeaveRuleDto.builder()
            .user(user)
            .requestedAnnualLeaveDays(requestedAnnualLeaveDays)
            .totalUsedDayCount(10)
            .build();

        final AnnualLeaveRuleException exception = assertThrows(
            AnnualLeaveRuleException.class,
            () -> availableAnnualLeaveCountRule.verify(dto)
        );

        assertEquals(INVALID_RULE, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals(MESSAGE_KEY, exception.getMessage());
    }

    @Test
    void shouldVerify() {

        final List<LocalDate> requestedAnnualLeaveDays = Stream.iterate(LocalDate.now(), date -> date.plusDays(1))
            .limit(ONE_TO_FIVE.getPeriod() - 1)
            .collect(Collectors.toList());

        final UserEntity user = UserEntity.builder()
            .createdDate(DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(200)))
            .build();

        final UserAnnualLeaveRuleDto dto = UserAnnualLeaveRuleDto.builder()
            .user(user)
            .requestedAnnualLeaveDays(requestedAnnualLeaveDays)
            .totalUsedDayCount(ONE_TO_FIVE.getPeriod() - requestedAnnualLeaveDays.size())
            .build();

        UserAnnualLeaveRuleResponse verify = availableAnnualLeaveCountRule.verify(dto);
        assertTrue(verify.isContinueRule());
    }
}