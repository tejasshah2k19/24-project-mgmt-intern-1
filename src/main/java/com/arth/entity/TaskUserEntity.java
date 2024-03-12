package com.arth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "taskuser")
public class TaskUserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer taskUserId;
	Integer userId;
	Integer taskId;
	Integer assignStatus;// 1 assign 2 revoke 3 hold

	public Integer getTaskUserId() {
		return taskUserId;
	}

	public void setTaskUserId(Integer taskUserId) {
		this.taskUserId = taskUserId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getAssignStatus() {
		return assignStatus;
	}

	public void setAssignStatus(Integer assignStatus) {
		this.assignStatus = assignStatus;
	}

}
