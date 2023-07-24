package com.springsearch.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SearchController {
	
	@RequestMapping("/user/{userId}")
	public String home(@PathVariable("userId") String userId) {
		System.out.println("userId  :  "+userId);
		return "home";
	}
	
	
	@RequestMapping("/home")
	public String home() {
		String p=null;
		System.out.println(p.length());
		return "home";
	}
	
	@RequestMapping("/search")
	public RedirectView search(@RequestParam("querybox") String query) {
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("https://www.google.com/search?q="+query);
		return redirectView;
	}
	
	
	//exception handling for this controller
	//commented because we have created centralized exceptionHandler
	
	/*
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = NullPointerException.class)
	public String nullPointerExceptionHandler(Model m) {
		m.addAttribute("error","NullPointerException");
		return "error";
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({IllegalArgumentException.class,NumberFormatException.class} )
	public String ExceptionHandler2(Model m) {
		m.addAttribute("error","NumberFormatException or IllegalArgumentException");
		return "error";
	}
	
	
	//for all exceptions
	@ExceptionHandler(value = {Exception.class} )
	public String ExceptionHandler(Model m) {
		m.addAttribute("error","Exception");
		return "error";
	}
	*/
	
}
