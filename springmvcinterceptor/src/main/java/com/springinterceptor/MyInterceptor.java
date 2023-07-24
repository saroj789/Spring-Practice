package com.springinterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//implements HandlerInterceptor: we need to override all three methods: preHandle(), postHandle() and afterCompletion()
//implements HandlerInterceptorAdapter: we may implement only required methods.
//but now its deprecated so we will use HandlerInterceptor

public class MyInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle called");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle called");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("afterCompletion called");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	
}
