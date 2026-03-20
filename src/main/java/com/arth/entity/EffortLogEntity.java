package com.arth.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "effort_log")
public class EffortLogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer logId;

	private Integer projectId;
	private Integer moduleId;
	private Integer userId;
	private Integer taskId;
	private Integer utilizedMinutes;
	private LocalDate logDate;
	private LocalTime logTime;
	@Column(columnDefinition = "TEXT")
	private String comments;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
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

	public Integer getUtilizedMinutes() {
		return utilizedMinutes;
	}

	public void setUtilizedMinutes(Integer utilizedMinutes) {
		this.utilizedMinutes = utilizedMinutes;
	}

	public LocalDate getLogDate() {
		return logDate;
	}

	public void setLogDate(LocalDate logDate) {
		this.logDate = logDate;
	}

	public LocalTime getLogTime() {
		return logTime;
	}

	public void setLogTime(LocalTime logTime) {
		this.logTime = logTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
