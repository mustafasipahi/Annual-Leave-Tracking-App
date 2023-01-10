package com.service.impl;

import com.dto.UserAnnualLeaveCreateDto;
import com.dto.UserAnnualLeaveDto;
import com.dto.UserAnnualLeaveUpdateDto;
import com.entity.AnnualLeaveEntity;
import com.repository.AnnualLeaveRepository;
import com.service.UserAnnualLeaveService;
import com.service.rule.UserAnnualLeaveRule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserAnnualLeaveServiceImpl implements UserAnnualLeaveService {

    private List<UserAnnualLeaveRule> rules;
    private AnnualLeaveRepository annualLeaveRepository;

    @Override
    public Long create(UserAnnualLeaveCreateDto dto) {
        rules.forEach(rule -> rule.verify(dto));

        return save(null);
    }

    @Override
    public Long update(UserAnnualLeaveUpdateDto dto) {

        return null;
    }

    @Override
    public List<UserAnnualLeaveDto> list(Long userId) {
        return null;
    }

    private Long save(AnnualLeaveEntity entity) {
        return annualLeaveRepository.save(entity).getId();
    }
}
