package com.service.impl;

import com.dto.UserAnnualLeaveCreateDto;
import com.dto.UserAnnualLeaveDto;
import com.dto.UserAnnualLeaveRuleDto;
import com.dto.UserAnnualLeaveUpdateDto;
import com.entity.AnnualLeaveEntity;
import com.enums.AnnualLeaveStatus;
import com.holder.AppContextHolder;
import com.repository.AnnualLeaveRepository;
import com.service.UserAnnualLeaveService;
import com.service.UserService;
import com.service.annual.leave.rule.UserAnnualLeaveRule;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.constant.CacheConstants.USER_TOTAL_ANNUAL_LEAVE_CACHE;

@Service
@AllArgsConstructor
public class UserAnnualLeaveServiceImpl implements UserAnnualLeaveService {

    private List<UserAnnualLeaveRule> rules;
    private AnnualLeaveRepository annualLeaveRepository;
    private UserService userService;

    @Override
    public Long create(UserAnnualLeaveCreateDto dto) {
        final UserAnnualLeaveRuleDto ruleDto = convertToRuleDto(dto);
        rules.forEach(rule -> rule.verify(ruleDto));

        return save(null);
    }

    @Override
    public Long update(UserAnnualLeaveUpdateDto dto) {

        return null;
    }

    @Override
    @CacheEvict(value = USER_TOTAL_ANNUAL_LEAVE_CACHE, key = "#userId", allEntries = true)
    public List<UserAnnualLeaveDto> list(Long userId) {
        return null;
    }

    @CacheEvict(value = USER_TOTAL_ANNUAL_LEAVE_CACHE, key = "#userId", allEntries = true)
    public int getUserTotalAnnualLeave(Long userId) {
        return annualLeaveRepository.findAllByUserIdAndStatus(userId, AnnualLeaveStatus.APPROVED)
            .stream()
            .map(AnnualLeaveEntity::getCount)
            .reduce(Integer::sum)
            .orElse(0);
    }

    private UserAnnualLeaveRuleDto convertToRuleDto(UserAnnualLeaveCreateDto dto) {
        return UserAnnualLeaveRuleDto.builder()
            .locale(dto.getLocale())
            .user(userService.detail(dto.getUserId(), dto.getLocale()))
            .totalAnnualLeave(AppContextHolder.getBean(UserAnnualLeaveServiceImpl.class).getUserTotalAnnualLeave(dto.getUserId()))
            .build();
    }

    private Long save(AnnualLeaveEntity entity) {
        return annualLeaveRepository.save(entity).getId();
    }
}
