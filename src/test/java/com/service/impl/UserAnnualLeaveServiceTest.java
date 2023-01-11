package com.service.impl;

import com.dto.UserAnnualLeaveCreateDto;
import com.entity.AnnualLeaveEntity;
import com.entity.UserEntity;
import com.enums.AnnualLeaveStatus;
import com.service.AnnualLeaveService;
import com.service.UserService;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
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

    void shouldCreate() {

        final UserAnnualLeaveCreateDto dto = UserAnnualLeaveCreateDto.builder()
            .userId(RandomUtils.nextLong())
            .startDate(LocalDate.now().minusDays(1))
            .endDate(LocalDate.now().plusDays(1))
            .build();

        final Locale locale = new Locale("tr");

        final UserEntity user = UserEntity.builder()
            .id(dto.getUserId())
            .build();

        int totalUsedDayCount = RandomUtils.nextInt();

        when(userService.detail(dto.getUserId(), locale)).thenReturn(user);
        when(annualLeaveService.getTotalUsedDayCount(dto.getUserId())).thenReturn(totalUsedDayCount);
        userAnnualLeaveService.create(dto, locale);

        verify(userService).detail(dto.getUserId(), locale);
        verify(annualLeaveService).getTotalUsedDayCount(dto.getUserId());

        ArgumentCaptor<AnnualLeaveEntity> captor = ArgumentCaptor.forClass(AnnualLeaveEntity.class);
        verify(annualLeaveService).save(captor.capture());
        AnnualLeaveEntity captorValue = captor.getValue();

        assertEquals(user.getId(), captorValue.getUser().getId());
        assertEquals(dto.getStartDate(), captorValue.getStartDate());
        assertEquals(dto.getEndDate(), captorValue.getEndDate());
        assertEquals(totalUsedDayCount, captorValue.getCount());
        assertEquals(AnnualLeaveStatus.WAITING_APPROVE, captorValue.getStatus());
    }
}