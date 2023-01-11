package com.repository;

import com.entity.AnnualLeaveEntity;
import com.enums.AnnualLeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnualLeaveRepository extends JpaRepository<AnnualLeaveEntity, Long> {

    List<AnnualLeaveEntity> findAllByUserIdAndStatus(Long userId, AnnualLeaveStatus status);
}
