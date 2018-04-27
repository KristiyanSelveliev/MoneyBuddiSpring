package com.controller;

import java.sql.SQLException;
import java.util.List;

import javax.activation.DataSource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.model.Currency;
import com.model.User;
import com.model.dao.AccountDao;
import com.model.dao.BudgetDao;
import com.model.dao.CurrencyDAO;
import com.model.dao.UserDao;
@Component
@Controller
public class UserController {
	
	
	@Autowired
	private DriverManagerDataSource db;
	@Autowired
	private CurrencyDAO currencyDAO;
	@Autowired 
	private UserDao userDAO;
	@Autowired 
	private AccountDao accountDAO;
	@Autowired 
	private BudgetDao budgetDAO;
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() throws Exception {
		for(Currency c:(List<Currency>) currencyDAO.getAllCurrencies()) {
			System.out.println(c.getType().toString());
		}
		
		
		
		return "index";
	}
	
	@ExceptionHandler(value = Exception.class)
	public String error(HttpServletRequest request,Exception e) {
		request.setAttribute("exception", e);
		return "error";
	}


}
