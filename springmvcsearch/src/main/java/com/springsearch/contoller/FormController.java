package com.springsearch.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springsearch.model.Student;

@Controller
public class FormController {
	
	@RequestMapping("/complex")
	public String showForm() {
		return "complexform";
	}
	
	@RequestMapping(path ="/handleform", method = RequestMethod.POST)
	public String handleForm( @ModelAttribute("student") Student student,BindingResult result) {
		if(result.hasErrors()) {
			return "complexform";
		}
		System.out.println(student);
		//model.addAttribute("student",student);
		return "success";
	}

}
