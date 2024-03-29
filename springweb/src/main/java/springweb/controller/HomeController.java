package springweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@RequestMapping(path = "/home",method = RequestMethod.GET)
	public String home(Model model ) {
		model.addAttribute("name","Saroj");
		return "index";
	}
	
	@RequestMapping("/about")
	public String about() {
		return "about";
	}
	
	@RequestMapping("/help")
	public ModelAndView help() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("owner","Rahul");
		modelAndView.setViewName("help");
		return modelAndView;
	}
	
}
