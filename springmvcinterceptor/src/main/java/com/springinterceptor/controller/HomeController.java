package com.springinterceptor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	@RequestMapping("/home")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/welcome")
	public String welcome(@RequestParam("name") String name,Model model) {
		System.out.println("Welcome handler called");
		model.addAttribute("name",name);
		return "welcome";
	}
}
