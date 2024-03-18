package com.arth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.arth.entity.ProjectEntity;
import com.arth.entity.ProjectStatusEntity;
import com.arth.entity.UserEntity;
import com.arth.repository.ProjectRepository;
import com.arth.repository.ProjectStatusRepository;
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

	@GetMapping("pmlistuser")
	public String pmListUser(Model model, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");

		List<UserEntity> users = userRepo.pmWiseTeam(user.getUserId());
		model.addAttribute("listuser",users);
		return "PmListUser";
	}

}
