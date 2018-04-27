package com.controller;

import javax.servlet.http.HttpServletRequest;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(value=Exception.class)
	public String error(HttpServletRequest request,Exception e) {
		
	   /* ModelAndView mav = new ModelAndView();
	    mav.addObject("exception",e );
	    mav.addObject("url", request.getRequestURL());
	    mav.setViewName("error");
	    return mav;*/
		request.setAttribute("exception", e);
		return "error";
	}

}
