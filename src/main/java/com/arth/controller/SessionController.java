package com.arth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arth.entity.UserEntity;
import com.arth.repository.UserRepository;
import com.arth.service.MailerService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class SessionController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	MailerService mailerService;

	@GetMapping("/")
	public String welcome() {
		return "Login";// jsp name
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
	public String saveUser(@Valid UserEntity user,BindingResult result ,Model model) {

		
		if(result.hasErrors()) {
			return "Signup";
		}
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("passwordError", "Password and Retype Password Must be same");
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
	public String authenticate(UserEntity user, Model model,HttpSession session) { // email password read
		UserEntity loggedInUser = userRepo.findByEmail(user.getEmail());
		System.out.println(loggedInUser);

		if (loggedInUser == null) {
			// credentials wrong
			model.addAttribute("error", "Invalid Credentials");
			return "Login";
		} else {
			

			//
			session.setAttribute("user", loggedInUser);//set user into session 
			
			session.setMaxInactiveInterval(60*10); //10 min -> no acitivity -> 
			
			boolean answer = passwordEncoder.matches(user.getPassword(), loggedInUser.getPassword());// true | false

			if (answer == false) {
				model.addAttribute("error", "Invalid Credentials");
				return "Login";
			} else if (loggedInUser.getRoleId() == null) {
				model.addAttribute("error", "You might be a HACKER");
				return "Login";
			} else if (loggedInUser.getRoleId() == 1) {
				// admin
				return "redirect:/admindashboard";
			} else if (loggedInUser.getRoleId() == 2) {
				// project manager
				return "redirect:/pmdashboard";
			} else if (loggedInUser.getRoleId() == 3) {
				// developer
				return "redirect:/developerdashboard";
			}
		}
		return "Login";
	}

	@GetMapping("/forgetpassword")
	public String forgetPassword() {
		return "ForgetPassword";
	}

	@PostMapping("/sendotpforrecoverpassword")
	public String sendOtpForRecoverPassword(UserEntity user) {

		UserEntity dbUser = userRepo.findByEmail(user.getEmail());

		if (dbUser != null) {
			// generate otp 123456
			int otp = (int) (Math.random() * 1000000); // 0258745

			// send otp on mail 123456
			System.out.println("otp => " + otp);
			mailerService.sendMailForOTP(user.getEmail(), otp);
			

			// set otp to user's account -> db
			dbUser.setOtp(otp);

			userRepo.save(dbUser);// userId
		}

		return "UpdatePassword";
	}

	@PostMapping("/updatepassword")
	public String updatePassword(UserEntity user, Model model) {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("passwordError", "Password and Re-type Password must be same");
			return "UpdatePassword";
		} else {
			UserEntity dbUser = userRepo.findByEmail(user.getEmail());
			
			System.out.println("dbUSer => "+dbUser.getOtp());
			System.out.println("user input => "+user.getOtp());
			
			if (dbUser == null || user.getOtp() == -1 || dbUser.getOtp().intValue() != user.getOtp().intValue()) {
				// error invalid otp or email
				System.out.println("ERROR");
				model.addAttribute("error", "Invalid OTP or Email");
				return "UpdatePassword";
			} else {
				// correct otp email

				String plainPassword = user.getPassword();

				String encPassword = passwordEncoder.encode(plainPassword);

				dbUser.setPassword(encPassword);
				dbUser.setOtp(-1);
				userRepo.save(dbUser);
				model.addAttribute("msg","Password modified");
			}
		}
		
		return "Login";//jsp 
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {	
		session.invalidate(); //destroy session 
		return "redirect:/login"; 
	}
}
