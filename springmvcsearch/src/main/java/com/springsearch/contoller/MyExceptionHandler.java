package com.springsearch.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class MyExceptionHandler {

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
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = {Exception.class} )
	public String ExceptionHandler(Model m) {
		m.addAttribute("error","Exception");
		return "error";
	}
	

}
