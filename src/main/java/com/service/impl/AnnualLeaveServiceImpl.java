package com.service.impl;

import com.dto.UserAnnualLeaveDto;
import com.dto.UserAnnualLeaveUpdateDto;
import com.entity.AnnualLeaveEntity;
import com.exception.AnnualLeaveException;
import com.repository.AnnualLeaveRepository;
import com.service.AnnualLeaveService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.constant.CacheConstants.USER_ANNUAL_LEAVE_LIST_CACHE;
import static com.constant.CacheConstants.USER_TOTAL_ANNUAL_LEAVE_CACHE;
import static com.enums.AnnualLeaveStatus.APPROVED;
import static com.enums.AnnualLeaveStatus.WAITING_APPROVE;

@Service
@AllArgsConstructor
public class AnnualLeaveServiceImpl implements AnnualLeaveService {

    private final AnnualLeaveRepository annualLeaveRepository;

    @Override
    public void save(AnnualLeaveEntity annualLeaveEntity) {
        annualLeaveRepository.save(annualLeaveEntity);
    }

    @Override
    public void update(UserAnnualLeaveUpdateDto dto) {
        final AnnualLeaveEntity annualLeaveEntity = annualLeaveRepository.findById(dto.getAnnualLeaveId())
            .orElseThrow(() -> new AnnualLeaveException(dto.getLocale()));

        annualLeaveEntity.setStatus(dto.getStatus());
        annualLeaveRepository.save(annualLeaveEntity);
    }

    @Override
    @Cacheable(value = USER_ANNUAL_LEAVE_LIST_CACHE, key = "#userId")
    public List<UserAnnualLeaveDto> list(Long userId) {
        return annualLeaveRepository.findAll()
            .stream()
            .map(entity -> UserAnnualLeaveDto.builder()
                .id(entity.getId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .count(entity.getCount())
                .status(entity.getStatus())
                .createdDate(entity.getCreatedDate())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = USER_TOTAL_ANNUAL_LEAVE_CACHE, key = "#userId")
    public int getTotalUsedDayCount(Long userId) {
        return annualLeaveRepository.findAllByUserIdAndStatusIn(userId, Arrays.asList(APPROVED, WAITING_APPROVE))
            .stream()
            .map(AnnualLeaveEntity::getCount)
            .reduce(Integer::sum)
            .orElse(0);
    }
}
