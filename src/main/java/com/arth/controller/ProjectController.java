package com.arth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arth.entity.ProjectEntity;
import com.arth.entity.ProjectStatusEntity;
import com.arth.repository.ProjectRepository;
import com.arth.repository.ProjectStatusRepository;

@Controller
public class ProjectController {

	@Autowired
	ProjectRepository projectRepo;

	@Autowired
	ProjectStatusRepository projectStatusRepo;

	@GetMapping("/newproject")
	public String newProject(Model model) {
		model.addAttribute("projectStatusList", projectStatusRepo.findAll());
		return "NewProject";

	}

	@PostMapping("/saveproject")
	public String saveProject(ProjectEntity projectEntity) {
		projectRepo.save(projectEntity);//save -> insert update -> id present update ,id not present insert 
		return "redirect:/listprojects";
	}

	@GetMapping("/listprojects")
	public String listProjects(Model model) {

		List<ProjectEntity> projects = projectRepo.findAll();
		model.addAttribute("p", projects);
		return "ListProject";
	}

	@GetMapping("/projects")
	public String listProjectsByStatus(@RequestParam("status") String status, Model model) {
		List<ProjectEntity> projects = null;

		try {
			ProjectStatusEntity ps = projectStatusRepo.findByStatus(status);
			projects = projectRepo.findByProjectStatusId(ps.getProjectStatusId());

		} catch (Exception e) {
			System.out.println("Invalid Status ");
			projects = projectRepo.findAll();

		}

		model.addAttribute("p", projects);
		return "ListProject";
	}
	
	@GetMapping("/viewproject")
	public String viewProject(@RequestParam("projectId") Integer projectId,Model model) {
		
		ProjectEntity project =  projectRepo.findById(projectId).get(); 
		model.addAttribute("project",project);
		
		return "ViewProject";
	}
	
	
	@GetMapping("/editproject")
	public String editProject(@RequestParam("projectId") Integer projectId,Model model) {
		
		ProjectEntity project =  projectRepo.findById(projectId).get(); 
		model.addAttribute("projectStatusList", projectStatusRepo.findAll());

		model.addAttribute("project",project);
		
		return "EditProject";
	}
}

//new role -> NewRole.jsp 
//new project -> ProjectNew.jsp 
//new project status -> ProjectStatus.jsp 
