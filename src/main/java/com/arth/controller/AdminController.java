package com.arth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.arth.entity.ProjectEntity;
import com.arth.entity.ProjectStatusEntity;
import com.arth.repository.ProjectRepository;
import com.arth.repository.ProjectStatusRepository;
import com.arth.repository.UserRepository;

@Controller
public class AdminController {

	@Autowired
	ProjectRepository projectRepo;

	@Autowired
	ProjectStatusRepository projectStatusRepo;

	@Autowired
	UserRepository userRepo;

	@GetMapping("/admindashboard")
	public String adminDashboard(Model model) {

		ProjectStatusEntity ps = projectStatusRepo.findByStatus("notstarted");
		model.addAttribute("pipeline", projectRepo.findByProjectStatusId(ps.getProjectStatusId()).size());

		ps = projectStatusRepo.findByStatus("inprogress");
		List<ProjectEntity> inProgress = projectRepo.findByProjectStatusId(ps.getProjectStatusId());
		model.addAttribute("onGoing", inProgress.size());

		ps = projectStatusRepo.findByStatus("due");
		List<ProjectEntity> due = projectRepo.findByProjectStatusId(ps.getProjectStatusId());
		model.addAttribute("due", due.size());

		List<ProjectEntity> projects = new ArrayList<>();
		projects.addAll(inProgress);
		projects.addAll(due);

		String projectName = "";
		String set1 = "";
		String set2 = "";
		for (ProjectEntity p : projects) {
			projectName = projectName + "'" + p.getTitle() + "',";
			set1 = set1 + p.getEstimatedHours()+",";
			set2 = set2 + p.getTotalUtilizedHours()+","; 
		}

		model.addAttribute("projectName", projectName);
		model.addAttribute("set1", set1);
		model.addAttribute("set2", set2);

		model.addAttribute("team", userRepo.findAll().size());

		return "AdminDashboard";
	}
}
