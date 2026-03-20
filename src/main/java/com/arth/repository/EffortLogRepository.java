package com.arth.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.arth.entity.EffortLogEntity;

public interface EffortLogRepository extends JpaRepository<EffortLogEntity, Integer> {

	@Query("select e.logDate, sum(e.utilizedMinutes) from EffortLogEntity e where e.userId = :userId and e.logDate between :startDate and :endDate group by e.logDate order by e.logDate")
	List<Object[]> findDailyMinutesByUserIdAndDateRange(Integer userId, LocalDate startDate, LocalDate endDate);

	List<EffortLogEntity> findByTaskIdAndUserIdOrderByLogDateDescLogTimeDesc(Integer taskId, Integer userId);
}
