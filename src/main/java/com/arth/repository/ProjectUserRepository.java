package com.arth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.arth.entity.ProjectUserEntity;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUserEntity, Integer> {

	ProjectUserEntity findByProjectIdAndUserId(Integer projectId, Integer userId);

	@Query(value = "select user_id from projectuser where project_id = :projectId", nativeQuery = true)
	java.util.List<Integer> findUserIdsByProjectId(Integer projectId);

}
