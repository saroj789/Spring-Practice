package springweb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import springweb.model.User;
import springweb.service.UserService;

@Controller
public class ContactController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/contact")
	public String showForm() {
		return "contact";
		
	}
	
	
	
	@RequestMapping(path = "/processform", method = RequestMethod.POST)
	public String handleForm4( @ModelAttribute User user ,Model model) {
		System.out.println("User4 is "+user);
		
		if(user.getUserName().isBlank() || user.getEmail().isBlank() || user.getPassword().isBlank()) {
			return "redirect:contact";
		}
		int r = this.userService.createUser(user);
		System.out.println("r is "+r);
		model.addAttribute("msg","user created with id "+r);
		return "success2";
		
	}
	
	
	/*  // these three steps can be done in single step by ModelAttribute(Get,set and send)
	@RequestMapping(path = "/processform", method = RequestMethod.POST)
	public String handleForm3(@RequestParam("userName") String userName,
			@RequestParam(name="email", required = true) String userEmail,
			@RequestParam("password") String userPassword,
			Model model) {
		
		User user = new User();
		user.setEmail(userEmail);
		user.setUserName(userName);
		user.setPassword(userPassword);
		
		System.out.println("User3 is "+user);
		model.addAttribute("user",user);

		return "success2";
	
	}
	*/
		
	
	 // using RequestParams
	/*
	@RequestMapping(path = "/processform", method = RequestMethod.POST)
	public String handleForm2( @RequestParam("userName") String userName,
			@RequestParam(name="email", required = true) String userEmail,
			@RequestParam("password") String userPassword,
			Model m
			) {
		
		System.out.println("userName is "+userName);
		System.out.println("email is "+userEmail);
		System.out.println("userPassword is "+userPassword);
		
		m.addAttribute("userName",userName);
		m.addAttribute("userEmail",userEmail);
	
		return "success";
	
	}
	*/
	
		

	
	
	// as JSP servlet
	/*
	@RequestMapping(path = "/processform", method = RequestMethod.POST)
	public String handleForm1(HttpServletRequest request) {
		String email = request.getParameter("email");
		System.out.println("email is "+email);
		return "success";	
	}
	*/

}
