package com.arth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.arth.entity.TaskUserEntity;

public interface TaskUserRepository extends JpaRepository<TaskUserEntity, Integer>{

	TaskUserEntity findByTaskIdAndUserId(Integer taskId, Integer userId);

	List<TaskUserEntity> findByTaskIdAndAssignStatus(Integer taskId, Integer assignStatus);

	@Query(value = "select user_id from taskuser where task_id = :taskId", nativeQuery = true)
	List<Integer> findUserIdsByTaskId(Integer taskId);

}
