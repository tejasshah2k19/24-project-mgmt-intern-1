package com.arth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.arth.entity.ProjectEntity;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer>{

	List<ProjectEntity> findByProjectStatusId(Integer projectStatusId);

	@Query(value ="select * from projects where project_id in ( select project_id from projectuser where user_id = :userId )",nativeQuery = true)
	List<ProjectEntity> myProjects(Integer userId);
	
}
