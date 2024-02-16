package com.arth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arth.bean.UserBean;
import com.arth.entity.UserEntity;
import com.arth.repository.UserRepository;

@Controller
public class SessionController {

	@Autowired
	UserRepository userRepo;

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
	public String saveUser(UserEntity user) {
		user.setRoleId(3); // developer
		userRepo.save(user);// insert

		return "redirect:/login";
	}

	@PostMapping("/authenticate")
	public String authenticate(UserEntity user, Model model) { // email password read
		UserEntity loggedInUser = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
		System.out.println(loggedInUser);

		if (loggedInUser == null) {
			// credentials wrong
			model.addAttribute("error", "Invalid Credentials");
			return "Login";
		} else {

			if (loggedInUser.getRoleId() == null) {
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
