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

import static com.constant.ErrorCodes.INVALID_RULE;
import static com.service.annual.leave.rule.rules.WorkedForLeastOneYearRule.MESSAGE_KEY;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
class WorkedForLeastOneYearRuleTest {

    @InjectMocks
    private WorkedForLeastOneYearRule workedForLeastOneYearRule;

    @Test
    void shouldThrowAnnualLeaveRuleExceptionVerifyWhenUserCreatedDateLessThanNow() {

        final UserEntity user = UserEntity.builder()
            .createdDate(DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(200)))
            .build();

        final UserAnnualLeaveRuleDto dto = UserAnnualLeaveRuleDto.builder()
            .user(user)
            .build();

        final AnnualLeaveRuleException exception = assertThrows(
            AnnualLeaveRuleException.class,
            () -> workedForLeastOneYearRule.verify(dto)
        );
        assertEquals(INVALID_RULE, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals(MESSAGE_KEY, exception.getMessage());
    }

    @Test
    void shouldVerify() {

        final UserEntity user = UserEntity.builder()
            .createdDate(DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(370)))
            .build();

        final UserAnnualLeaveRuleDto dto = UserAnnualLeaveRuleDto.builder()
            .user(user)
            .build();

        UserAnnualLeaveRuleResponse verify = workedForLeastOneYearRule.verify(dto);
        assertTrue(verify.isContinueRule());
    }
}