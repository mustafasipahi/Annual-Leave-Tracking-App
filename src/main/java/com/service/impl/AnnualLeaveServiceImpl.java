package com.service.impl;

import com.client.RedisCacheClient;
import com.dto.UserAnnualLeaveDto;
import com.dto.UserAnnualLeaveUpdateDto;
import com.entity.AnnualLeaveEntity;
import com.exception.AnnualLeaveException;
import com.repository.AnnualLeaveRepository;
import com.service.AnnualLeaveService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.constant.CacheConstants.USER_ANNUAL_LEAVE_LIST_CACHE;
import static com.constant.CacheConstants.USER_TOTAL_ANNUAL_LEAVE_CACHE;
import static com.enums.AnnualLeaveStatus.APPROVED;
import static com.enums.AnnualLeaveStatus.WAITING_APPROVE;

@Service
@AllArgsConstructor
public class AnnualLeaveServiceImpl implements AnnualLeaveService {

    private final AnnualLeaveRepository annualLeaveRepository;
    private final RedisCacheClient redisCacheClient;

    @Override
    public void save(AnnualLeaveEntity annualLeaveEntity) {
        evictCache(annualLeaveEntity.getUser().getId());
        annualLeaveRepository.save(annualLeaveEntity);
    }

    @Override
    public Long update(UserAnnualLeaveUpdateDto dto) {
        final AnnualLeaveEntity annualLeaveEntity = annualLeaveRepository.findById(dto.getAnnualLeaveId())
            .orElseThrow(() -> new AnnualLeaveException(dto.getLocale()));

        annualLeaveEntity.setStatus(dto.getStatus());
        evictCache(annualLeaveEntity.getUser().getId());
        return null;
    }

    @Override
    @Cacheable(value = USER_ANNUAL_LEAVE_LIST_CACHE, key = "#userId")
    public List<UserAnnualLeaveDto> list(Long userId) {
        return null;
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

    private void evictCache(Long userId) {
        redisCacheClient.delete(USER_ANNUAL_LEAVE_LIST_CACHE + userId);
        redisCacheClient.delete(USER_TOTAL_ANNUAL_LEAVE_CACHE + userId);
    }
}
