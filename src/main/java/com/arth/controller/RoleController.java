package com.arth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arth.entity.RoleEntity;
import com.arth.repository.RoleRepository;

@Controller
public class RoleController {

	@Autowired
	RoleRepository roleRepo;
	
	@GetMapping("/newrole")
	public String newRole() {
		
		return "NewRole";//jsp 
	}

	@PostMapping("/saverole")
	public String saveRole(RoleEntity  role) {
		System.out.println(role.getRoleName());
		//insert 
	
		roleRepo.save(role);//insert 
		return "redirect:/listrole"; //this will invoke method  
	}
	

	@GetMapping("/listrole")
	public String listRole(Model model) {
		List<RoleEntity> listRole =   roleRepo.findAll();//select * from roles 
		System.out.println("ListRole :: from controller");
		model.addAttribute("r",listRole);
		return "ListRole";//jsp 
	}

}

