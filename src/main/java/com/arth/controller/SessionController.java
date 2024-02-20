package com.arth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arth.entity.UserEntity;
import com.arth.repository.UserRepository;

@Controller
public class SessionController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

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
		return "Login";// jsp open
	}

	@PostMapping("/signup")
	public String saveUser(UserEntity user,Model model) {
		
		if(! user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("passwordError","Password and Retype Password Must be same");
			return "Signup";  
		}
		
		
		user.setRoleId(3); // developer
		// read plain text password
		String plainPassword = user.getPassword();

		// encrypt plain text password

		String encPassword = passwordEncoder.encode(plainPassword);// Bcrypt

		// user -> password -> set -> encPassword
		user.setPassword(encPassword);

		userRepo.save(user);// insert

		return "redirect:/login";
	}

	@PostMapping("/authenticate")
	public String authenticate(UserEntity user, Model model) { // email password read
		UserEntity loggedInUser = userRepo.findByEmail(user.getEmail());
		System.out.println(loggedInUser);

		if (loggedInUser == null) {
			// credentials wrong
			model.addAttribute("error", "Invalid Credentials");
			return "Login";
		} else {

			boolean answer = passwordEncoder.matches(user.getPassword(), loggedInUser.getPassword());// true | false

			if (answer == false) {
				model.addAttribute("error", "Invalid Credentials");
				return "Login";
			} else if (loggedInUser.getRoleId() == null) {
				model.addAttribute("error", "You might be a HACKER");
				return "Login";
			} else if (loggedInUser.getRoleId() == 1) {
				// admin
				return "AdminDashboard";
			} else if (loggedInUser.getRoleId() == 2) {
				// project manager
				return "ProjectManagerDashboard";
			} else if (loggedInUser.getRoleId() == 3) {
				// developer
				return "DeveloperDashboard";
			}
		}
		return "Login";
	}

}
