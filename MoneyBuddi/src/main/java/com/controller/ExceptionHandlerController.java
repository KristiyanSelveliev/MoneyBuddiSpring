package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.exceptions.InvalidDataException;

@ControllerAdvice
public class ExceptionHandlerController {
	
	
	@ExceptionHandler(value=TypeMismatchException.class)
	public String valueError(HttpServletRequest request,TypeMismatchException e) {
		e.printStackTrace();
		InvalidDataException ex=new InvalidDataException("You probably entered wrong value");
		request.setAttribute("exception",ex);
		return "error";
	}
	
	
	
	@ExceptionHandler(value=Exception.class)
	public String error(HttpServletRequest request,Exception e) {
		e.printStackTrace();
		request.setAttribute("exception", e);
		return "error";
	}

}
