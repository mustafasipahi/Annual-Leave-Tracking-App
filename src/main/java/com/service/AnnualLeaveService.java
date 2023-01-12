package com.service;

import com.dto.UserAnnualLeaveResponse;
import com.dto.UserAnnualLeaveUpdateDto;
import com.entity.AnnualLeaveEntity;

public interface AnnualLeaveService {

    void save(AnnualLeaveEntity entity);
    void update(UserAnnualLeaveUpdateDto dto);
    UserAnnualLeaveResponse list(Long userId);
    int getTotalUsedDayCount(Long userId);
}
