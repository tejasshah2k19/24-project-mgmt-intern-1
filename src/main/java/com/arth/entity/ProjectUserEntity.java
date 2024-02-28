package com.arth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="projectuser")
public class ProjectUserEntity {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer projectUserId;
	Integer userId;
	Integer projectId;
	Integer assignStatus;//1 assign 2 revoke 3 hold 
	public Integer getProjectUserId() {
		return projectUserId;
	}
	public void setProjectUserId(Integer projectUserId) {
		this.projectUserId = projectUserId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getAssignStatus() {
		return assignStatus;
	}
	public void setAssignStatus(Integer assignStatus) {
		this.assignStatus = assignStatus;
	}

	
}
