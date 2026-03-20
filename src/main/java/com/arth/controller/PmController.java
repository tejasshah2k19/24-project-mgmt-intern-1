package com.arth.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.arth.entity.UserEntity;
import com.arth.repository.ModuleRepository;
import com.arth.repository.ProjectRepository;
import com.arth.repository.ProjectStatusRepository;
import com.arth.repository.TaskRepository;
import com.arth.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PmController {

	@Autowired
	ProjectRepository projectRepo;

	@Autowired
	ProjectStatusRepository projectStatusRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ModuleRepository moduleRepo;

	@Autowired
	TaskRepository taskRepo;

	@GetMapping("pmdashboard")
	public String pmDashboard(Model model, HttpSession session) {

		UserEntity user = (UserEntity) session.getAttribute("user");

		List<ProjectEntity> myProjects = projectRepo.myProjects(user.getUserId());
		int notStarted = 0;
		;
		int inProgress = 0;
		int due = 0;
		int hold = 0;
		int completed = 0;
		for (ProjectEntity p : myProjects) {
			if (p.getProjectStatusId() == 1) {
//				1->inProgress,2->hold,3->completed,4->notStarted,5->due
				inProgress++;
			} else if (p.getProjectStatusId() == 2) {
				hold++;
			} else if (p.getProjectStatusId() == 3) {
				completed++;
			} else if (p.getProjectStatusId() == 4) {
				inProgress++;
			} else if (p.getProjectStatusId() == 5) {
				due++;
			}
		}

		model.addAttribute("pipeline", notStarted);
		model.addAttribute("onGoing", inProgress);
		model.addAttribute("due", due);
		model.addAttribute("hold", hold);
		model.addAttribute("completed", completed);

		List<ProjectEntity> projects = new ArrayList<>();

		String projectName = "";
		String set1 = "";
		String set2 = "";
		for (ProjectEntity p : projects) {
			projectName = projectName + "'" + p.getTitle() + "',";
			set1 = set1 + p.getEstimatedHours() + ",";
			set2 = set2 + p.getTotalUtilizedHours() + ",";
		}

		model.addAttribute("projectName", projectName);
		model.addAttribute("set1", set1);
		model.addAttribute("set2", set2);

		model.addAttribute("team", userRepo.findAll().size());

		return "PMDashboard";
	}

	@GetMapping("pmlistprojects")
	public String pmListProjects(@RequestParam(value = "status", required = false) String status, Model model,
			HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		List<ProjectEntity> myProjects = projectRepo.myProjects(user.getUserId());
		if (status != null && !status.isBlank()) {
			ProjectStatusEntity projectStatus = projectStatusRepo.findByStatus(status.toLowerCase());
			if (projectStatus != null) {
				List<ProjectEntity> filteredProjects = new ArrayList<>();
				for (ProjectEntity project : myProjects) {
					if (projectStatus.getProjectStatusId().equals(project.getProjectStatusId())) {
						filteredProjects.add(project);
					}
				}
				myProjects = filteredProjects;
			}
		}

		model.addAttribute("p", myProjects);
		return "PmListProject";
	}

	@GetMapping("pmlistmodule")
	public String pmListModule(@RequestParam("projectId") Integer projectId, Model model, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		if (!hasProjectAccess(user.getUserId(), projectId)) {
			return "redirect:/pmlistprojects";
		}

		model.addAttribute("modules", moduleRepo.findByProjectId(projectId));
		model.addAttribute("project", projectRepo.findById(projectId).orElse(null));
		return "PmListModule";
	}

	@GetMapping("pmlisttask")
	public String pmListTask(@RequestParam("moduleId") Integer moduleId, Model model, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		ModuleEntity module = moduleRepo.findById(moduleId).orElse(null);
		if (module == null || !hasProjectAccess(user.getUserId(), module.getProjectId())) {
			return "redirect:/pmlistprojects";
		}

		ProjectEntity project = projectRepo.findById(module.getProjectId()).orElse(null);
		List<TaskEntity> tasks = taskRepo.findByModuleId(moduleId);
		model.addAttribute("task", tasks);
		model.addAttribute("module", module);
		model.addAttribute("project", project);
		return "PmListTask";
	}

	@GetMapping("pmlistuser")
	public String pmListUser(Model model, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		List<UserEntity> users = userRepo.pmWiseTeam(user.getUserId());
		model.addAttribute("listUser", users);
		return "PmListUser";
	}

	@GetMapping("pmmodules")
	public String pmModules(Model model, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		List<ProjectEntity> myProjects = projectRepo.myProjects(user.getUserId());
		List<ModuleEntity> modules = new ArrayList<>();
		Map<Integer, ProjectEntity> projectMap = new LinkedHashMap<>();
		for (ProjectEntity project : myProjects) {
			projectMap.put(project.getProjectId(), project);
			modules.addAll(moduleRepo.findByProjectId(project.getProjectId()));
		}

		model.addAttribute("modules", modules);
		model.addAttribute("projectMap", projectMap);
		return "PmModules";
	}

	@GetMapping("pmtasks")
	public String pmTasks(Model model, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		List<ProjectEntity> myProjects = projectRepo.myProjects(user.getUserId());
		List<ModuleEntity> modules = new ArrayList<>();
		Map<Integer, ProjectEntity> projectMap = new LinkedHashMap<>();
		Map<Integer, ModuleEntity> moduleMap = new LinkedHashMap<>();
		List<TaskEntity> tasks = new ArrayList<>();

		for (ProjectEntity project : myProjects) {
			projectMap.put(project.getProjectId(), project);
			List<ModuleEntity> projectModules = moduleRepo.findByProjectId(project.getProjectId());
			modules.addAll(projectModules);
		}

		for (ModuleEntity module : modules) {
			moduleMap.put(module.getModuleId(), module);
			tasks.addAll(taskRepo.findByModuleId(module.getModuleId()));
		}

		model.addAttribute("tasks", tasks);
		model.addAttribute("projectMap", projectMap);
		model.addAttribute("moduleMap", moduleMap);
		return "PmTasks";
	}

	@GetMapping("pmnewmodule")
	public String pmNewModule(@RequestParam("projectId") Integer projectId, Model model, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		if (!hasProjectAccess(user.getUserId(), projectId)) {
			return "redirect:/pmlistprojects";
		}

		model.addAttribute("project", projectRepo.findById(projectId).orElse(null));
		model.addAttribute("projectStatusList", projectStatusRepo.findAll());
		return "PmNewModule";
	}

	@PostMapping("pmsavemodule")
	public String pmSaveModule(ModuleEntity module, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		if (module.getProjectId() == null || !hasProjectAccess(user.getUserId(), module.getProjectId())) {
			return "redirect:/pmlistprojects";
		}

		moduleRepo.save(module);
		return "redirect:/pmlistmodule?projectId=" + module.getProjectId();
	}

	@GetMapping("pmnewtask")
	public String pmNewTask(@RequestParam("moduleId") Integer moduleId, Model model, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		ModuleEntity module = moduleRepo.findById(moduleId).orElse(null);
		if (module == null || !hasProjectAccess(user.getUserId(), module.getProjectId())) {
			return "redirect:/pmlistprojects";
		}

		ProjectEntity project = projectRepo.findById(module.getProjectId()).orElse(null);
		model.addAttribute("module", module);
		model.addAttribute("project", project);
		model.addAttribute("projectStatusList", projectStatusRepo.findAll());
		return "PmNewTask";
	}

	@PostMapping("pmsavetask")
	public String pmSaveTask(TaskEntity task, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		ModuleEntity module = moduleRepo.findById(task.getModuleId()).orElse(null);
		if (module == null || !hasProjectAccess(user.getUserId(), module.getProjectId())) {
			return "redirect:/pmlistprojects";
		}

		task.setProjectId(module.getProjectId());
		taskRepo.save(task);
		return "redirect:/pmlisttask?moduleId=" + task.getModuleId();
	}

	private boolean hasProjectAccess(Integer userId, Integer projectId) {
		List<ProjectEntity> myProjects = projectRepo.myProjects(userId);
		for (ProjectEntity project : myProjects) {
			if (project.getProjectId().equals(projectId)) {
				return true;
			}
		}
		return false;
	}

}
