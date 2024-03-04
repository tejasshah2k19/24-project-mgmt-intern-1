
package com.arth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arth.entity.ModuleEntity;
import com.arth.entity.ProjectEntity;
import com.arth.entity.ProjectStatusEntity;
import com.arth.repository.ModuleRepository;
import com.arth.repository.ProjectRepository;
import com.arth.repository.ProjectStatusRepository;

@Controller
public class ModuleController {

	@Autowired
	ModuleRepository m;

	@Autowired
	ProjectRepository projectRepo;

	@Autowired
	ProjectStatusRepository projectStatuRepo;

	@GetMapping("/newmodule")
	public String newModule(Model model) {
		List<ProjectEntity> projectList = projectRepo.findAll();
		model.addAttribute("projectList", projectList);

		List<ProjectStatusEntity> projectStatusList = projectStatuRepo.findAll();
		model.addAttribute("projectStatusList", projectStatusList);
		return "NewModule";
	}

	@PostMapping("/savemodule")
	public String saveModule(ModuleEntity module) {
		m.save(module);
		return "redirect:/listmodule?projectId="+module.getProjectId();
	}

	@GetMapping("/listmodule")
	public String lisrModule(@RequestParam("projectId") Integer projectId,  Model model) {
		List<ModuleEntity> modules = m.findByProjectId(projectId);
		ProjectEntity project = projectRepo.findById(projectId).get();
		model.addAttribute("modules", modules);
		model.addAttribute("project",project);
		return "ListModule";
	}

	@GetMapping("/deletemodule")
	public String deleteModule(@RequestParam("moduleId") Integer moduleId) {
		m.deleteById(moduleId);
		return "redirect:/listmodule";
	}

}