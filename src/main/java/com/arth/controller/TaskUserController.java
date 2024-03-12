package com.arth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arth.entity.TaskUserEntity;
import com.arth.repository.TaskUserRepository;
import com.arth.repository.UserRepository;

@Controller
public class TaskUserController {

	@Autowired
	TaskUserRepository taskUserRepo; 
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/assigntask")
	public String newAssignTask(Model model) {
		
		model.addAttribute("users",userRepository.findAll());
		
		return "AssignTask";
	}
	
	
	@PostMapping("/assigntask")
	public String assignTask(TaskUserEntity taskUser) {
		taskUserRepo.save(taskUser);
		return "";
	}
}
