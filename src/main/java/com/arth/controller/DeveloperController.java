package com.arth.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class DeveloperController {


	@GetMapping("/developerdashboard")
	public String developerDashboard() {
		
		
		return "DeveloperDashboard"; 
		
	}
}
