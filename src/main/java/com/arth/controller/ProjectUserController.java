package com.arth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arth.entity.ProjectEntity;
import com.arth.entity.ProjectUserEntity;
import com.arth.entity.UserEntity;
import com.arth.repository.ProjectRepository;
import com.arth.repository.ProjectUserRepository;
import com.arth.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProjectUserController {

	
	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	UserRepository userRepo;

	@Autowired
	ProjectUserRepository projectUserRepo;

	@GetMapping("/assignproject")
	public String assignProject(@RequestParam(value = "projectId", required = false) Integer projectId, Model model,
			@RequestParam(value = "msg", required = false) String msg) {

		List<UserEntity> users = userRepo.findAll();
		if (projectId != null && projectId > 0) {
			List<Integer> assignedUserIds = projectUserRepo.findUserIdsByProjectId(projectId);
			if (!assignedUserIds.isEmpty()) {
				users.removeIf(user -> assignedUserIds.contains(user.getUserId()));
			}
		}

		model.addAttribute("selectedProjectId", projectId);
		model.addAttribute("users", users);
		model.addAttribute("projects", projectRepo.findAll());
		model.addAttribute("msg", msg);

		return "AssignProject";
	}

	@GetMapping("/pmassignproject")
	public String pmAssignProject(@RequestParam(value = "projectId", required = false) Integer projectId, Model model,
			@RequestParam(value = "msg", required = false) String msg, HttpSession session) {
		UserEntity pm = (UserEntity) session.getAttribute("user");
		if (pm == null) {
			return "redirect:/login";
		}

		List<ProjectEntity> pmProjects = projectRepo.myProjects(pm.getUserId());
		if (projectId != null && projectId > 0 && !hasProjectAccess(pmProjects, projectId)) {
			projectId = null;
			msg = "unauthorized";
		}

		List<UserEntity> users = userRepo.findAll();
		if (projectId != null && projectId > 0) {
			List<Integer> assignedUserIds = projectUserRepo.findUserIdsByProjectId(projectId);
			if (!assignedUserIds.isEmpty()) {
				users.removeIf(user -> assignedUserIds.contains(user.getUserId()));
			}
		}

		model.addAttribute("selectedProjectId", projectId);
		model.addAttribute("users", users);
		model.addAttribute("projects", pmProjects);
		model.addAttribute("msg", msg);
		return "PmAssignProject";
	}

	@PostMapping("/assignproject")
	public String assignProjectToUser(ProjectUserEntity pu, RedirectAttributes redirectAttributes) {
		if (pu.getProjectId() == null || pu.getUserId() == null || pu.getProjectId() <= 0 || pu.getUserId() <= 0) {
			redirectAttributes.addAttribute("msg", "invalid");
			return "redirect:/assignproject";
		}

		ProjectUserEntity existingAssignment = projectUserRepo.findByProjectIdAndUserId(pu.getProjectId(), pu.getUserId());
		if (existingAssignment != null) {
			if (existingAssignment.getAssignStatus() != null && existingAssignment.getAssignStatus() == 1) {
				redirectAttributes.addAttribute("projectId", pu.getProjectId());
				redirectAttributes.addAttribute("msg", "alreadyAssigned");
				return "redirect:/assignproject";
			}

			existingAssignment.setAssignStatus(1);
			projectUserRepo.save(existingAssignment);
			redirectAttributes.addAttribute("projectId", pu.getProjectId());
			redirectAttributes.addAttribute("msg", "reAssigned");
			return "redirect:/assignproject";
		}

		pu.setAssignStatus(1);
		projectUserRepo.save(pu);
		redirectAttributes.addAttribute("projectId", pu.getProjectId());
		redirectAttributes.addAttribute("msg", "assigned");
		return "redirect:/assignproject";
	}

	@PostMapping("/pmassignproject")
	public String pmAssignProjectToUser(ProjectUserEntity pu, RedirectAttributes redirectAttributes, HttpSession session) {
		UserEntity pm = (UserEntity) session.getAttribute("user");
		if (pm == null) {
			return "redirect:/login";
		}

		if (pu.getProjectId() == null || pu.getUserId() == null || pu.getProjectId() <= 0 || pu.getUserId() <= 0) {
			redirectAttributes.addAttribute("msg", "invalid");
			return "redirect:/pmassignproject";
		}

		List<ProjectEntity> pmProjects = projectRepo.myProjects(pm.getUserId());
		if (!hasProjectAccess(pmProjects, pu.getProjectId())) {
			redirectAttributes.addAttribute("msg", "unauthorized");
			return "redirect:/pmassignproject";
		}

		ProjectUserEntity existingAssignment = projectUserRepo.findByProjectIdAndUserId(pu.getProjectId(), pu.getUserId());
		if (existingAssignment != null) {
			if (existingAssignment.getAssignStatus() != null && existingAssignment.getAssignStatus() == 1) {
				redirectAttributes.addAttribute("projectId", pu.getProjectId());
				redirectAttributes.addAttribute("msg", "alreadyAssigned");
				return "redirect:/pmassignproject";
			}

			existingAssignment.setAssignStatus(1);
			projectUserRepo.save(existingAssignment);
			redirectAttributes.addAttribute("projectId", pu.getProjectId());
			redirectAttributes.addAttribute("msg", "reAssigned");
			return "redirect:/pmassignproject";
		}

		pu.setAssignStatus(1);
		projectUserRepo.save(pu);
		redirectAttributes.addAttribute("projectId", pu.getProjectId());
		redirectAttributes.addAttribute("msg", "assigned");
		return "redirect:/pmassignproject";
	}

	@GetMapping("/listprojectuser")
	public String listProjectUser(@RequestParam("projectId") Integer projectId,Model model) {
	
		model.addAttribute("project",projectRepo.findById(projectId).get());
		model.addAttribute("users",userRepo.getUserByProjectId(projectId));
	
		model.addAttribute("usersHold",userRepo.getUserByProjectIdHold(projectId));
		model.addAttribute("usersRevoke",userRepo.getUserByProjectIdRevoke(projectId));
		
		
		return "ListProjectUser";
	}

	@GetMapping("/projectrevoke")
	public String projectRevoke(@RequestParam("userId") Integer userId,@RequestParam("projectId") Integer projectId,@RequestParam("status") Integer status) {
		
		ProjectUserEntity pe = projectUserRepo.findByProjectIdAndUserId(projectId,userId);
		pe.setAssignStatus(status);
		projectUserRepo.save(pe);
		return "redirect:/listprojectuser?projectId="+projectId;
	}

	private boolean hasProjectAccess(List<ProjectEntity> projects, Integer projectId) {
		for (ProjectEntity project : projects) {
			if (project.getProjectId().equals(projectId)) {
				return true;
			}
		}
		return false;
	}

}
