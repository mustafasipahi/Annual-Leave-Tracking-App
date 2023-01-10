package com.repository;

import com.entity.AnnualLeaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnualLeaveRepository extends JpaRepository<AnnualLeaveEntity, Long> {
}
