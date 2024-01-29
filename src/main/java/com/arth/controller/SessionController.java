package com.arth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {

	@GetMapping("/welcome")
	public String welcome(){
		return "Welcome";//jsp name 
	}

	@GetMapping("/signup")
	public String signup() {
		return "Signup";
	}
	 
}
