package com.arth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arth.entity.ProjectEntity;
import com.arth.repository.ProjectRepository;

@Controller
public class ProjectController {

	@Autowired
	ProjectRepository projectRepo;
	
	@GetMapping("/newproject")
	public String newProject() {
		return "NewProject";

	}

	@PostMapping("/saveproject")
	public String saveProject(ProjectEntity projectEntity) {
		projectRepo.save(projectEntity); 
		return "redirect:/listprojects";
	}
	
	@GetMapping("/listprojects")
	public String listProjects(Model model) {
		
	List<ProjectEntity> projects = 	  projectRepo.findAll();
		model.addAttribute("p",projects);
		return "ListProject";
	}
}


//new role -> NewRole.jsp 
//new project -> ProjectNew.jsp 
//new project status -> ProjectStatus.jsp 
