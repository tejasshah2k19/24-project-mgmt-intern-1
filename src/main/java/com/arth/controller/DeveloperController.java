package com.arth.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arth.entity.EffortLogEntity;
import com.arth.entity.ModuleEntity;
import com.arth.entity.ProjectEntity;
import com.arth.entity.ProjectStatusEntity;
import com.arth.entity.TaskUserEntity;
import com.arth.entity.TaskEntity;
import com.arth.entity.UserEntity;
import com.arth.repository.EffortLogRepository;
import com.arth.repository.ModuleRepository;
import com.arth.repository.ProjectRepository;
import com.arth.repository.ProjectStatusRepository;
import com.arth.repository.TaskRepository;
import com.arth.repository.TaskUserRepository;
import com.arth.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class DeveloperController {

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	ProjectStatusRepository projectStatusRepository;

	@Autowired
	TaskUserRepository taskUserRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EffortLogRepository effortLogRepository;

	@GetMapping("/developerdashboard")
	public String developerDashboard(HttpSession session, Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		List<ProjectEntity> projects = projectRepository.myProjects(user.getUserId());
		List<TaskEntity> assignedTasks = taskRepository.findAssignedTasksByUserId(user.getUserId());
		Set<Integer> assignedModuleIds = new HashSet<>();
		int dueProjects = 0;
		int inProgressProjects = 0;

		for (ProjectEntity project : projects) {
			if (project.getProjectStatusId() != null && project.getProjectStatusId() == 5) {
				dueProjects++;
			}
			if (project.getProjectStatusId() != null && project.getProjectStatusId() == 1) {
				inProgressProjects++;
			}

		}

		int inProgressTasks = 0;
		int holdTasks = 0;
		int completedTasks = 0;
		int pipelineTasks = 0;
		int dueTasks = 0;
		for (TaskEntity task : assignedTasks) {
			if (task.getStatusId() == null) {
				continue;
			}
			assignedModuleIds.add(task.getModuleId());
			if (task.getStatusId() == 1) {
				inProgressTasks++;
			} else if (task.getStatusId() == 2) {
				holdTasks++;
			} else if (task.getStatusId() == 3) {
				completedTasks++;
			} else if (task.getStatusId() == 4) {
				pipelineTasks++;
			} else if (task.getStatusId() == 5) {
				dueTasks++;
			}
		}

		model.addAttribute("projectCount", projects.size());
		model.addAttribute("moduleCount", assignedModuleIds.size());
		model.addAttribute("taskCount", assignedTasks.size());
		model.addAttribute("dueProjects", dueProjects);
		model.addAttribute("inProgressProjects", inProgressProjects);
		model.addAttribute("inProgressTasks", inProgressTasks);
		model.addAttribute("holdTasks", holdTasks);
		model.addAttribute("completedTasks", completedTasks);
		model.addAttribute("pipelineTasks", pipelineTasks);
		model.addAttribute("dueTasks", dueTasks);

		YearMonth currentMonth = YearMonth.now();
		LocalDate startDate = currentMonth.atDay(1);
		LocalDate endDate = currentMonth.atEndOfMonth();
		List<Object[]> dailyRecords = effortLogRepository.findDailyMinutesByUserIdAndDateRange(user.getUserId(), startDate,
				endDate);
		Map<Integer, Integer> minutesByDay = new LinkedHashMap<>();
		for (Object[] record : dailyRecords) {
			LocalDate logDate = (LocalDate) record[0];
			Number minutes = (Number) record[1];
			minutesByDay.put(logDate.getDayOfMonth(), minutes == null ? 0 : minutes.intValue());
		}

		StringBuilder dayLabels = new StringBuilder();
		StringBuilder dayHours = new StringBuilder();
		for (int day = 1; day <= currentMonth.lengthOfMonth(); day++) {
			int minutes = minutesByDay.getOrDefault(day, 0);
			double hours = minutes / 60.0;
			dayLabels.append("'").append(day).append("',");
			dayHours.append(String.format(Locale.US, "%.2f", hours)).append(",");
		}
		model.addAttribute("currentMonthLabel", currentMonth.getMonth().name() + " " + currentMonth.getYear());
		model.addAttribute("dayLabels", dayLabels.toString());
		model.addAttribute("dayHours", dayHours.toString());
		return "DeveloperDashboard";
	}

	@GetMapping("myprojects")
	public String myProjects(HttpSession session, Model model,
			@RequestParam(value = "status", required = false) String status) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		List<ProjectEntity> projects = projectRepository.myProjects(user.getUserId());
		if (status != null && !status.isBlank()) {
			ProjectStatusEntity projectStatus = projectStatusRepository.findByStatus(status.toLowerCase());
			if (projectStatus != null) {
				List<ProjectEntity> filteredProjects = new ArrayList<>();
				for (ProjectEntity project : projects) {
					if (projectStatus.getProjectStatusId().equals(project.getProjectStatusId())) {
						filteredProjects.add(project);
					}
				}
				projects = filteredProjects;
			}
		}
		model.addAttribute("projects", projects);
		return "MyProjects";
	}

	@GetMapping("mymodules")
	public String myModules(HttpSession session, Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		List<TaskEntity> assignedTasks = taskRepository.findAssignedTasksByUserId(user.getUserId());
		Map<Integer, ProjectEntity> projectMap = new LinkedHashMap<>();
		Map<Integer, List<ModuleEntity>> projectModuleMap = new LinkedHashMap<>();
		Set<Integer> moduleAdded = new HashSet<>();

		for (TaskEntity task : assignedTasks) {
			ModuleEntity module = moduleRepository.findById(task.getModuleId()).orElse(null);
			ProjectEntity project = projectRepository.findById(task.getProjectId()).orElse(null);
			if (module == null || project == null) {
				continue;
			}
			if (!moduleAdded.add(module.getModuleId())) {
				continue;
			}
			projectMap.put(project.getProjectId(), project);
			projectModuleMap.computeIfAbsent(project.getProjectId(), k -> new ArrayList<>()).add(module);
		}

		model.addAttribute("projectMap", projectMap);
		model.addAttribute("projectModuleMap", projectModuleMap);
		return "MyModules";
	}

	@GetMapping("mytasks")
	public String myTasks(HttpSession session, Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		List<TaskEntity> assignedTasks = taskRepository.findAssignedTasksByUserId(user.getUserId());
		Map<Integer, ProjectEntity> projectMap = new LinkedHashMap<>();
		Map<Integer, ModuleEntity> moduleMap = new LinkedHashMap<>();
		Map<Integer, List<TaskEntity>> projectTaskMap = new LinkedHashMap<>();

		for (TaskEntity task : assignedTasks) {
			ProjectEntity project = projectRepository.findById(task.getProjectId()).orElse(null);
			ModuleEntity module = moduleRepository.findById(task.getModuleId()).orElse(null);
			if (project == null || module == null) {
				continue;
			}

			projectMap.put(project.getProjectId(), project);
			moduleMap.put(module.getModuleId(), module);
			projectTaskMap.computeIfAbsent(project.getProjectId(), k -> new ArrayList<>()).add(task);
		}

		model.addAttribute("projectMap", projectMap);
		model.addAttribute("moduleMap", moduleMap);
		model.addAttribute("projectTaskMap", projectTaskMap);
		return "MyTasks";
	}

	@GetMapping("/devtaskuser")
	public String developerTaskUsers(@RequestParam("taskId") Integer taskId, HttpSession session, Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		if (!hasTaskAccess(user.getUserId(), taskId)) {
			return "redirect:/mytasks";
		}

		TaskEntity task = taskRepository.findById(taskId).orElse(null);
		if (task == null) {
			return "redirect:/mytasks";
		}
		ModuleEntity module = moduleRepository.findById(task.getModuleId()).orElse(null);
		ProjectEntity project = projectRepository.findById(task.getProjectId()).orElse(null);

		List<UserEntity> users = getTaskUsers(taskId, 1);
		model.addAttribute("task", task);
		model.addAttribute("module", module);
		model.addAttribute("project", project);
		model.addAttribute("users", users);
		model.addAttribute("totalAssignedUsers", users.size());
		return "DevTaskUsers";
	}

	@GetMapping("/devviewtask")
	public String devViewTask(@RequestParam("taskId") Integer taskId, HttpSession session, Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		if (!hasTaskAccess(user.getUserId(), taskId)) {
			return "redirect:/mytasks";
		}

		TaskEntity task = taskRepository.findById(taskId).orElse(null);
		if (task == null) {
			return "redirect:/mytasks";
		}
		ModuleEntity module = moduleRepository.findById(task.getModuleId()).orElse(null);
		ProjectEntity project = projectRepository.findById(task.getProjectId()).orElse(null);

		model.addAttribute("task", task);
		model.addAttribute("module", module);
		model.addAttribute("project", project);
		model.addAttribute("effortLogs",
				effortLogRepository.findByTaskIdAndUserIdOrderByLogDateDescLogTimeDesc(taskId, user.getUserId()));
		return "DevViewTask";
	}

	@GetMapping("/devediteffort")
	public String devEditEffort(@RequestParam("logId") Integer logId, HttpSession session, Model model,
			@RequestParam(value = "msg", required = false) String msg) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		EffortLogEntity effortLog = effortLogRepository.findById(logId).orElse(null);
		if (effortLog == null || !user.getUserId().equals(effortLog.getUserId())) {
			return "redirect:/mytasks";
		}

		TaskEntity task = taskRepository.findById(effortLog.getTaskId()).orElse(null);
		if (task == null || !hasTaskAccess(user.getUserId(), task.getTaskId())) {
			return "redirect:/mytasks";
		}
		ModuleEntity module = moduleRepository.findById(task.getModuleId()).orElse(null);
		ProjectEntity project = projectRepository.findById(task.getProjectId()).orElse(null);

		model.addAttribute("effortLog", effortLog);
		model.addAttribute("task", task);
		model.addAttribute("module", module);
		model.addAttribute("project", project);
		model.addAttribute("projectStatusList", projectStatusRepository.findAll());
		model.addAttribute("todayDate", LocalDate.now().toString());
		model.addAttribute("msg", msg);
		return "DevEditEffort";
	}

	@PostMapping("/devediteffort")
	public String devEditEffortSave(@RequestParam("logId") Integer logId, @RequestParam("statusId") Integer statusId,
			@RequestParam("utilizedMinutes") Integer utilizedMinutes,
			@RequestParam(value = "comments", required = false) String comments,
			@RequestParam(value = "logDate", required = false) String logDate, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		EffortLogEntity effortLog = effortLogRepository.findById(logId).orElse(null);
		if (effortLog == null || !user.getUserId().equals(effortLog.getUserId())) {
			return "redirect:/mytasks";
		}

		if (utilizedMinutes == null || utilizedMinutes < 0) {
			return "redirect:/devediteffort?logId=" + logId + "&msg=invalid";
		}
		LocalDate selectedDate = null;
		try {
			selectedDate = (logDate == null || logDate.isBlank()) ? LocalDate.now() : LocalDate.parse(logDate);
		} catch (Exception e) {
			return "redirect:/devediteffort?logId=" + logId + "&msg=invalidDate";
		}
		if (selectedDate.isAfter(LocalDate.now())) {
			return "redirect:/devediteffort?logId=" + logId + "&msg=invalidDate";
		}

		TaskEntity task = taskRepository.findById(effortLog.getTaskId()).orElse(null);
		if (task == null || !hasTaskAccess(user.getUserId(), task.getTaskId())) {
			return "redirect:/mytasks";
		}

		int oldMinutes = effortLog.getUtilizedMinutes() == null ? 0 : effortLog.getUtilizedMinutes();
		int deltaMinutes = utilizedMinutes - oldMinutes;
		int taskUtilized = task.getTotalUtilizedMinutes() == null ? 0 : task.getTotalUtilizedMinutes();
		task.setTotalUtilizedMinutes(Math.max(0, taskUtilized + deltaMinutes));
		task.setStatusId(statusId);
		taskRepository.save(task);

		int oldHours = (int) Math.ceil(oldMinutes / 60.0);
		int newHours = (int) Math.ceil(utilizedMinutes / 60.0);
		int deltaHours = newHours - oldHours;
		if (deltaHours != 0) {
			ModuleEntity module = moduleRepository.findById(task.getModuleId()).orElse(null);
			if (module != null) {
				int moduleHours = module.getTotalUtilizedHours() == null ? 0 : module.getTotalUtilizedHours();
				module.setTotalUtilizedHours(Math.max(0, moduleHours + deltaHours));
				moduleRepository.save(module);
			}

			ProjectEntity project = projectRepository.findById(task.getProjectId()).orElse(null);
			if (project != null) {
				int projectHours = project.getTotalUtilizedHours() == null ? 0 : project.getTotalUtilizedHours();
				project.setTotalUtilizedHours(Math.max(0, projectHours + deltaHours));
				projectRepository.save(project);
			}
		}

		effortLog.setUtilizedMinutes(utilizedMinutes);
		effortLog.setComments(comments);
		effortLog.setLogDate(selectedDate);
		effortLog.setLogTime(LocalTime.now());
		effortLogRepository.save(effortLog);

		return "redirect:/devviewtask?taskId=" + task.getTaskId() + "&msg=edited";
	}

	@GetMapping("/devlogefforts")
	public String devLogEfforts(@RequestParam("taskId") Integer taskId, HttpSession session, Model model,
			@RequestParam(value = "msg", required = false) String msg) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		if (!hasTaskAccess(user.getUserId(), taskId)) {
			return "redirect:/mytasks";
		}

		TaskEntity task = taskRepository.findById(taskId).orElse(null);
		if (task == null) {
			return "redirect:/mytasks";
		}
		ModuleEntity module = moduleRepository.findById(task.getModuleId()).orElse(null);
		ProjectEntity project = projectRepository.findById(task.getProjectId()).orElse(null);

		model.addAttribute("task", task);
		model.addAttribute("module", module);
		model.addAttribute("project", project);
		model.addAttribute("projectStatusList", projectStatusRepository.findAll());
		model.addAttribute("msg", msg);
		model.addAttribute("todayDate", LocalDate.now().toString());
		return "DevLogEfforts";
	}

	@PostMapping("/devlogefforts")
	public String devLogEffortsSave(@RequestParam("taskId") Integer taskId, @RequestParam("statusId") Integer statusId,
			@RequestParam("utilizedMinutes") Integer utilizedMinutes,
			@RequestParam(value = "comments", required = false) String comments,
			@RequestParam(value = "logDate", required = false) String logDate, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		if (!hasTaskAccess(user.getUserId(), taskId)) {
			return "redirect:/mytasks";
		}
		if (utilizedMinutes == null || utilizedMinutes < 0) {
			return "redirect:/devlogefforts?taskId=" + taskId + "&msg=invalid";
		}
		LocalDate selectedDate = null;
		try {
			selectedDate = (logDate == null || logDate.isBlank()) ? LocalDate.now() : LocalDate.parse(logDate);
		} catch (Exception e) {
			return "redirect:/devlogefforts?taskId=" + taskId + "&msg=invalidDate";
		}
		if (selectedDate.isAfter(LocalDate.now())) {
			return "redirect:/devlogefforts?taskId=" + taskId + "&msg=invalidDate";
		}

		TaskEntity task = taskRepository.findById(taskId).orElse(null);
		if (task == null) {
			return "redirect:/mytasks";
		}

		int currentTaskUtilized = task.getTotalUtilizedMinutes() == null ? 0 : task.getTotalUtilizedMinutes();
		task.setTotalUtilizedMinutes(currentTaskUtilized + utilizedMinutes);
		task.setStatusId(statusId);
		taskRepository.save(task);

		EffortLogEntity effortLog = new EffortLogEntity();
		effortLog.setProjectId(task.getProjectId());
		effortLog.setModuleId(task.getModuleId());
		effortLog.setUserId(user.getUserId());
		effortLog.setTaskId(taskId);
		effortLog.setUtilizedMinutes(utilizedMinutes);
		effortLog.setLogDate(selectedDate);
		effortLog.setLogTime(LocalTime.now());
		effortLog.setComments(comments);
		effortLogRepository.save(effortLog);

		int hoursToAdd = (int) Math.ceil(utilizedMinutes / 60.0);
		if (hoursToAdd > 0) {
			ModuleEntity module = moduleRepository.findById(task.getModuleId()).orElse(null);
			if (module != null) {
				int currentModuleUtilized = module.getTotalUtilizedHours() == null ? 0 : module.getTotalUtilizedHours();
				module.setTotalUtilizedHours(currentModuleUtilized + hoursToAdd);
				moduleRepository.save(module);
			}

			ProjectEntity project = projectRepository.findById(task.getProjectId()).orElse(null);
			if (project != null) {
				int currentProjectUtilized = project.getTotalUtilizedHours() == null ? 0 : project.getTotalUtilizedHours();
				project.setTotalUtilizedHours(currentProjectUtilized + hoursToAdd);
				projectRepository.save(project);
			}
		}

		return "redirect:/devviewtask?taskId=" + taskId + "&msg=saved";
	}

	@GetMapping("devlistmodule")
	public String devListModule(@RequestParam("projectId") Integer projectId, Model model) {
		List<ModuleEntity> modules = moduleRepository.findByProjectId(projectId);
		ProjectEntity project = projectRepository.findById(projectId).get();
		model.addAttribute("modules", modules);
		model.addAttribute("project", project);
		return "DevListModule";
	}

	private boolean hasTaskAccess(Integer userId, Integer taskId) {
		TaskUserEntity taskUser = taskUserRepository.findByTaskIdAndUserId(taskId, userId);
		return taskUser != null && taskUser.getAssignStatus() != null && taskUser.getAssignStatus() == 1;
	}

	private List<UserEntity> getTaskUsers(Integer taskId, Integer status) {
		List<TaskUserEntity> taskUsers = taskUserRepository.findByTaskIdAndAssignStatus(taskId, status);
		List<UserEntity> users = new ArrayList<>();
		for (TaskUserEntity taskUser : taskUsers) {
			UserEntity user = userRepository.findById(taskUser.getUserId()).orElse(null);
			if (user != null) {
				users.add(user);
			}
		}
		return users;
	}

	@GetMapping("/devlisttask")
	public String devListTask(@RequestParam("moduleId") Integer moduleId, Model model) {
		List<TaskEntity> tasks = taskRepository.findByModuleId(moduleId);
		ModuleEntity module = moduleRepository.findById(moduleId).get();
		// findById =>
		ProjectEntity project = projectRepository.findById(module.getProjectId()).get();

		model.addAttribute("task", tasks);
		model.addAttribute("module", module);
		model.addAttribute("project", project);
		return "DevListTask";
	}
	
	
	@GetMapping("/devnewtask")
	public String devNewTask(@RequestParam("moduleId") Integer moduleId, Model model) {
		
		ModuleEntity module = moduleRepository.findById(moduleId).get();
		ProjectEntity project = projectRepository.findById(module.getProjectId()).get();

 

		List<ProjectStatusEntity> projectStatusList = projectStatusRepository.findAll();
		model.addAttribute("projectStatusList", projectStatusList);
		
		model.addAttribute("module",module);
		model.addAttribute("project",project); 
		
		return "DevNewTask";
	}

}
