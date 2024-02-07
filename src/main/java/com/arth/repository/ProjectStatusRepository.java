package com.arth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arth.entity.ProjectStatusEntity;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatusEntity, Integer> {

}
