package springweb.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

//for redirect testing

@Controller
public class ReController {
	
	@RequestMapping("/one")
	public String one() {
		System.out.println("This is first Handler");
		return "redirect:/enjoy";
	}
	
	@RequestMapping("/enjoy")
	public String two() {
		System.out.println("This is second Handler(/enjoy)");
		return "contact";
	}
	
	@RequestMapping("/three")
	public RedirectView three() {
		System.out.println("This is third Handler");
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("enjoy");
		return redirectView;
	}
	
	// as in JSP Servlet
	@RequestMapping("/four")
	public String four(HttpServletResponse response) {
		System.out.println("This is fourth Handler(/enjoy)");
		try {
			response.sendRedirect("enjoy");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "contact";
	}

}
