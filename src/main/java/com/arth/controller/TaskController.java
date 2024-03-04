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
import com.arth.entity.TaskEntity;
import com.arth.repository.ModuleRepository;
import com.arth.repository.ProjectRepository;
import com.arth.repository.ProjectStatusRepository;
import com.arth.repository.TaskRepository;

@Controller
public class TaskController {

	@Autowired
	TaskRepository taskRepo;

	@Autowired
	ModuleRepository moduleRepo;

	@Autowired
	ProjectRepository projectRepo;

	@Autowired
	ProjectStatusRepository projectStatuRepo;

	@GetMapping("/newtask")
	public String newTask(@RequestParam("moduleId") Integer moduleId, Model model) {
		
		ModuleEntity module = moduleRepo.findById(moduleId).get();
		ProjectEntity project = projectRepo.findById(module.getProjectId()).get();

 

		List<ProjectStatusEntity> projectStatusList = projectStatuRepo.findAll();
		model.addAttribute("projectStatusList", projectStatusList);
		
		model.addAttribute("module",module);
		model.addAttribute("project",project); 
		
		return "NewTask";
	}

	@PostMapping("/savetask")
	public String svaeTask(TaskEntity task) {
		taskRepo.save(task);
		return "redirect:/listtask";
	}

	@GetMapping("/listtask")
	public String listTask(@RequestParam("moduleId") Integer moduleId, Model model) {
		List<TaskEntity> tasks = taskRepo.findByModuleId(moduleId);
		ModuleEntity module = moduleRepo.findById(moduleId).get();
		// findById =>
		ProjectEntity project = projectRepo.findById(module.getProjectId()).get();

		model.addAttribute("task", tasks);
		model.addAttribute("module",module);
		model.addAttribute("project",project);
		return "ListTask";
	}

	@GetMapping("/deletetask")
	public String deleteTask(@RequestParam("taskId") Integer taskId) {
		taskRepo.deleteById(taskId);
		return "redirect:/listtask";
	}

}