package com.controller;

import java.sql.SQLException;
import java.util.List;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.model.Currency;


import com.model.dao.CurrencyDAO;
@Component
@Controller
public class UserController {
	
	
	@Autowired
	private DriverManagerDataSource db;
	@Autowired
	private CurrencyDAO currencyDAO;
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() throws Exception {
		for(Currency c:(List<Currency>) currencyDAO.getAllCurrencies()) {
			System.out.println(c.getType().toString());
		}
		
		
		return "index";
	}

}
