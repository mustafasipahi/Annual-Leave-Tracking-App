package com.service.impl;

import com.dto.UserAnnualLeaveDto;
import com.dto.UserAnnualLeaveResponse;
import com.dto.UserAnnualLeaveUpdateDto;
import com.entity.AnnualLeaveEntity;
import com.enums.AnnualLeaveStatus;
import com.exception.AnnualLeaveException;
import com.repository.AnnualLeaveRepository;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.constant.ErrorCodes.AVAILABLE_NOT_FOUND;
import static com.enums.AnnualLeaveStatus.APPROVED;
import static com.enums.AnnualLeaveStatus.WAITING_APPROVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
class AnnualLeaveServiceImplTest {

    @InjectMocks
    private AnnualLeaveServiceImpl annualLeaveService;
    @Mock
    private AnnualLeaveRepository annualLeaveRepository;

    @Test
    void shouldSave() {
        final AnnualLeaveEntity entity = AnnualLeaveEntity.builder()
            .id(RandomUtils.nextLong())
            .build();
        annualLeaveService.save(entity);
        verify(annualLeaveRepository).save(entity);
    }

    @Test
    void shouldThrowAnnualLeaveExceptionUpdateWhenAnnualLeaveEntityNotFound() {

        final UserAnnualLeaveUpdateDto dto = UserAnnualLeaveUpdateDto.builder()
            .status(AnnualLeaveStatus.APPROVED)
            .build();

        final AnnualLeaveException exception = assertThrows(
            AnnualLeaveException.class,
            () -> annualLeaveService.update(dto)
        );
        assertEquals(AVAILABLE_NOT_FOUND, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals("annual.leave.not.found", exception.getMessageKey());
    }

    @Test
    void shouldUpdate() {

        final UserAnnualLeaveUpdateDto dto = UserAnnualLeaveUpdateDto.builder()
            .status(AnnualLeaveStatus.APPROVED)
            .build();

        when(annualLeaveRepository.findById(dto.getAnnualLeaveId())).thenReturn(Optional.of(new AnnualLeaveEntity()));
        annualLeaveService.update(dto);

        ArgumentCaptor<AnnualLeaveEntity> captor = ArgumentCaptor.forClass(AnnualLeaveEntity.class);
        verify(annualLeaveRepository).save(captor.capture());
        AnnualLeaveEntity captorValue = captor.getValue();

        assertEquals(dto.getStatus(), captorValue.getStatus());
    }

    @Test
    void shouldList() {
        final Long userId = RandomUtils.nextLong();

        final AnnualLeaveEntity entity1 = AnnualLeaveEntity.builder()
            .id(RandomUtils.nextLong())
            .build();

        final AnnualLeaveEntity entity2 = AnnualLeaveEntity.builder()
            .id(RandomUtils.nextLong())
            .build();

        when(annualLeaveRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));
        UserAnnualLeaveResponse response = annualLeaveService.list(userId);
        List<UserAnnualLeaveDto> list = response.getAnnualLeaveList();

        assertEquals(2, list.size());
        assertEquals(entity1.getId(), list.get(0).getId());
        assertEquals(entity2.getId(), list.get(1).getId());
    }

    @Test
    void shouldGetTotalUsedDayCount() {
        final Long userId = RandomUtils.nextLong();

        final AnnualLeaveEntity entity1 = AnnualLeaveEntity.builder()
            .id(RandomUtils.nextLong())
            .count(RandomUtils.nextInt())
            .build();

        final AnnualLeaveEntity entity2 = AnnualLeaveEntity.builder()
            .id(RandomUtils.nextLong())
            .count(RandomUtils.nextInt())
            .build();

        when(annualLeaveRepository.findAllByUserIdAndStatusIn(userId, Arrays.asList(APPROVED, WAITING_APPROVE)))
            .thenReturn(Arrays.asList(entity1, entity2));

        int totalUsedDayCount = annualLeaveService.getTotalUsedDayCount(userId);
        assertEquals(entity1.getCount() + entity2.getCount(), totalUsedDayCount);
    }
}