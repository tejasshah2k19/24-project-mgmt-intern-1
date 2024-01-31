package com.arth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arth.bean.RoleBean;

@Controller
public class RoleController {

	
	@GetMapping("/newrole")
	public String newRole() {
		
		return "NewRole";
	}

	@PostMapping("/saverole")
	public String saveRole(RoleBean role) {
		System.out.println(role.getRoleName());
		return "Home"; 
	}
	
}

