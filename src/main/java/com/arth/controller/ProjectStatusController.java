package com.arth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arth.entity.ProjectStatusEntity;
import com.arth.repository.ProjectStatusRepository;

@Controller
public class ProjectStatusController {

	@Autowired
	ProjectStatusRepository projectStatusRepo; 
	
	@GetMapping("/newprojectstatus")
	public String newPojectStatus() {
		
		return "NewProjectStatus";
	}
	
	@PostMapping("/saveprojectstatus")
	public String saveProjectStatus(ProjectStatusEntity statusEntity) {
		projectStatusRepo.save(statusEntity);
		return "Home";
	}
}
