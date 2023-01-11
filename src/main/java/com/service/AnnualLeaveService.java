package com.service;

import com.dto.UserAnnualLeaveDto;
import com.dto.UserAnnualLeaveUpdateDto;
import com.entity.AnnualLeaveEntity;

import java.util.List;

public interface AnnualLeaveService {

    void save(AnnualLeaveEntity entity);
    Long update(UserAnnualLeaveUpdateDto dto);
    List<UserAnnualLeaveDto> list(Long userId);
    int getTotalUsedDayCount(Long userId);
}
