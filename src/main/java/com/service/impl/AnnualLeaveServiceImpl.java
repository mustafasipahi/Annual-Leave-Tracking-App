package com.service.impl;

import com.dto.UserAnnualLeaveDto;
import com.dto.UserAnnualLeaveUpdateDto;
import com.entity.AnnualLeaveEntity;
import com.repository.AnnualLeaveRepository;
import com.service.AnnualLeaveService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.constant.CacheConstants.USER_LIST_LEAVE_CACHE;
import static com.constant.CacheConstants.USER_TOTAL_ANNUAL_LEAVE_CACHE;
import static com.enums.AnnualLeaveStatus.APPROVED;
import static com.enums.AnnualLeaveStatus.WAITING_APPROVE;

@Service
@AllArgsConstructor
public class AnnualLeaveServiceImpl implements AnnualLeaveService {

    private AnnualLeaveRepository annualLeaveRepository;

    @Override
    @Caching(evict = {
        @CacheEvict(value = USER_LIST_LEAVE_CACHE, key = "#annualLeaveEntity.user.id"),
        @CacheEvict(value = USER_TOTAL_ANNUAL_LEAVE_CACHE, key = "#annualLeaveEntity.user.id")
    })
    public void save(AnnualLeaveEntity annualLeaveEntity) {
        annualLeaveRepository.save(annualLeaveEntity);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = USER_LIST_LEAVE_CACHE, key = "#userId"),
        @CacheEvict(value = USER_LIST_LEAVE_CACHE, key = "#userId")
    })
    public Long update(UserAnnualLeaveUpdateDto dto) {

        return null;
    }

    @Override
    @Cacheable(value = USER_LIST_LEAVE_CACHE, key = "#userId")
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
}
