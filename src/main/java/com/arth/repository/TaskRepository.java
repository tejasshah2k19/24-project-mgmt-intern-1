package com.arth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.arth.entity.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer>{

	List<TaskEntity> findByModuleId(Integer moduleId);

	@Query(value = "select distinct t.* from task t, taskuser tu where t.task_id = tu.task_id and tu.user_id = :userId and tu.assign_status = 1", nativeQuery = true)
	List<TaskEntity> findAssignedTasksByUserId(Integer userId);

	
}
