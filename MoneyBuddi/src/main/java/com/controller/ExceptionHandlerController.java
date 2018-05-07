package com.controller;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.exceptions.InvalidDataException;

@ControllerAdvice
public class ExceptionHandlerController {
	
	
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String  handleMissingParams(HttpServletRequest request,MissingServletRequestParameterException e) { 
		 InvalidDataException ex=new InvalidDataException("A parameter is missing");
	     request.setAttribute("exception",ex);
	     return "error";
	   
	}
	@ExceptionHandler(value=DateTimeParseException.class)
	public String  dateHandler(HttpServletRequest request,DateTimeParseException e) {
		InvalidDataException ex=new InvalidDataException("You probably entered wrong date");
		request.setAttribute("exception",ex);
		return "error";
	}
	
	@ExceptionHandler(value=TypeMismatchException.class)
	public String valueError(HttpServletRequest request,TypeMismatchException e) {
		InvalidDataException ex=new InvalidDataException("You probably entered wrong value");
		request.setAttribute("exception",ex);
		return "error";
	}
	
	@ExceptionHandler(SQLException.class)
	public String  handleDBError(HttpServletRequest request,SQLException e) { 
		 InvalidDataException ex=new InvalidDataException("Something went wrong with the Database");
	     request.setAttribute("exception",ex);
	     return "error";  
	}

	@ExceptionHandler(value=Exception.class)
	public String error(HttpServletRequest request,Exception e) {
		request.setAttribute("exception", e);
		return "error";
	}

}
