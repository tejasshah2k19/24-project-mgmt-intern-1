package com.arth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arth.bean.UserBean;

@Controller
public class SessionController {

	@GetMapping("/")
	public String welcome() {
		return "Welcome";// jsp name
	}

	@GetMapping("/signup")
	public String signup() {
		return "Signup";
	}

	@GetMapping("/login")
	public String login() {
		return "Login";
	}

	@PostMapping("/signup")
	public String addUser(UserBean user) {
		// read
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
		System.out.println(user.getEmail());
		System.out.println(user.getPassword());
		// validation
		// database save
		

		System.out.println("SaveUser");
		return "Home";
	}
}
