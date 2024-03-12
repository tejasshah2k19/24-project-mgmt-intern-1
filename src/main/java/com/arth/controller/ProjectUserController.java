package com.arth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arth.entity.ProjectUserEntity;
import com.arth.repository.ProjectRepository;
import com.arth.repository.ProjectUserRepository;
import com.arth.repository.UserRepository;

@Controller
public class ProjectUserController {

	
	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ProjectUserRepository projectUserRepo; 
	@GetMapping("/assignproject")
	public String assignProject(Model model) {

		//send all users and project to jsp 
		model.addAttribute("users",userRepo.findAll());
		model.addAttribute("projects",projectRepo.findAll()); 
		
		return "AssignProject"; 
	}
	
	
	@PostMapping("/assignproject")
	public String assignProjectToUser(ProjectUserEntity pu) {
		pu.setAssignStatus(1); //assign 
		projectUserRepo.save(pu);
		return "redirect:/assignproject";
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
	

}
