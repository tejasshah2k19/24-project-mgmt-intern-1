package com.arth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arth.entity.ModuleEntity;
import com.arth.entity.ProjectEntity;
import com.arth.entity.TaskEntity;
import com.arth.entity.TaskUserEntity;
import com.arth.entity.UserEntity;
import com.arth.repository.ModuleRepository;
import com.arth.repository.ProjectRepository;
import com.arth.repository.TaskRepository;
import com.arth.repository.TaskUserRepository;
import com.arth.repository.UserRepository;

@Controller
public class TaskUserController {

	@Autowired
	TaskUserRepository taskUserRepo;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	ProjectRepository projectRepository;

	@GetMapping("/taskusers")
	public String taskUsers(@RequestParam("taskId") Integer taskId, Model model,
			@RequestParam(value = "msg", required = false) String msg) {
		TaskEntity task = taskRepository.findById(taskId).orElse(null);
		if (task == null) {
			return "redirect:/login";
		}

		ModuleEntity module = moduleRepository.findById(task.getModuleId()).orElse(null);
		ProjectEntity project = projectRepository.findById(task.getProjectId()).orElse(null);

		model.addAttribute("task", task);
		model.addAttribute("module", module);
		model.addAttribute("project", project);
		model.addAttribute("users", getUsersByTaskAndStatus(taskId, 1));
		model.addAttribute("usersHold", getUsersByTaskAndStatus(taskId, 3));
		model.addAttribute("usersRevoke", getUsersByTaskAndStatus(taskId, 2));
		model.addAttribute("msg", msg);

		return "TaskUsers";
	}

	@GetMapping("/assigntask")
	public String newAssignTask(@RequestParam("taskId") Integer taskId, Model model,
			@RequestParam(value = "msg", required = false) String msg) {
		TaskEntity task = taskRepository.findById(taskId).orElse(null);
		if (task == null) {
			return "redirect:/login";
		}

		ModuleEntity module = moduleRepository.findById(task.getModuleId()).orElse(null);
		ProjectEntity project = projectRepository.findById(task.getProjectId()).orElse(null);

		List<UserEntity> users = userRepository.findAll();
		List<Integer> assignedUserIds = taskUserRepo.findUserIdsByTaskId(taskId);
		if (!assignedUserIds.isEmpty()) {
			users.removeIf(user -> assignedUserIds.contains(user.getUserId()));
		}

		model.addAttribute("users", users);
		model.addAttribute("task", task);
		model.addAttribute("module", module);
		model.addAttribute("project", project);
		model.addAttribute("msg", msg);
		return "AssignTask";
	}

	@PostMapping("/assigntask")
	public String assignTask(TaskUserEntity taskUser, RedirectAttributes redirectAttributes) {
		if (taskUser.getTaskId() == null || taskUser.getUserId() == null || taskUser.getTaskId() <= 0
				|| taskUser.getUserId() <= 0) {
			redirectAttributes.addAttribute("msg", "invalid");
			redirectAttributes.addAttribute("taskId", taskUser.getTaskId());
			return "redirect:/assigntask";
		}

		TaskUserEntity existingTaskUser = taskUserRepo.findByTaskIdAndUserId(taskUser.getTaskId(), taskUser.getUserId());
		if (existingTaskUser != null) {
			if (existingTaskUser.getAssignStatus() != null && existingTaskUser.getAssignStatus() == 1) {
				redirectAttributes.addAttribute("msg", "alreadyAssigned");
				redirectAttributes.addAttribute("taskId", taskUser.getTaskId());
				return "redirect:/assigntask";
			}

			existingTaskUser.setAssignStatus(1);
			taskUserRepo.save(existingTaskUser);
			redirectAttributes.addAttribute("msg", "reAssigned");
			redirectAttributes.addAttribute("taskId", taskUser.getTaskId());
			return "redirect:/taskusers";
		}

		taskUser.setAssignStatus(1);
		taskUserRepo.save(taskUser);
		redirectAttributes.addAttribute("msg", "assigned");
		redirectAttributes.addAttribute("taskId", taskUser.getTaskId());
		return "redirect:/taskusers";
	}

	@GetMapping("/taskrevoke")
	public String taskRevoke(@RequestParam("taskId") Integer taskId, @RequestParam("userId") Integer userId,
			@RequestParam("status") Integer status) {
		TaskUserEntity taskUser = taskUserRepo.findByTaskIdAndUserId(taskId, userId);
		if (taskUser != null) {
			taskUser.setAssignStatus(status);
			taskUserRepo.save(taskUser);
		}
		return "redirect:/taskusers?taskId=" + taskId;
	}

	private List<UserEntity> getUsersByTaskAndStatus(Integer taskId, Integer status) {
		List<TaskUserEntity> taskUsers = taskUserRepo.findByTaskIdAndAssignStatus(taskId, status);
		List<UserEntity> users = new ArrayList<>();
		for (TaskUserEntity taskUser : taskUsers) {
			UserEntity user = userRepository.findById(taskUser.getUserId()).orElse(null);
			if (user != null) {
				users.add(user);
			}
		}
		return users;
	}
}
