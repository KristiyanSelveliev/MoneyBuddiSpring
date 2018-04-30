package com.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.exceptions.InvalidDataException;
import com.model.Account;
import com.model.Currency;
import com.model.User;
import com.model.dao.AccountDao;
import com.model.dao.CurrencyDAO;

@Controller
public class AccountController {
	
	@Autowired 
	private CurrencyDAO currencyDao;
	@Autowired
	private AccountDao accountDao;
	
	@RequestMapping(value= "/accounts", method = RequestMethod.GET)
	public String showCategories(HttpServletRequest request,HttpSession session) throws Exception {
		List<Currency> currencies = (List<Currency>) currencyDao.getAllCurrencies();
		request.setAttribute("currencies", currencies);

		User u=(User) session.getAttribute("user");
		ArrayList<Account> accounts =(ArrayList<Account>) accountDao.getAllAccountsForUser(u);
		request.setAttribute("accounts", accounts);
		
		return "createAccount";
	}
	
	@RequestMapping(value= "/createAccount", method = RequestMethod.POST)
	public String createAccount(HttpServletRequest request,
			@RequestParam int currencyId,
			@RequestParam String name,
			@RequestParam double balance
			) throws SQLException,InvalidDataException {
		List<Currency> currencies=(List<Currency>) currencyDao.getAllCurrencies(); 
		request.setAttribute("currencies", currencies);         //loading categories again                                
																//because the user stays on the same page 
															    //after pressing create 
		
		//String name=request.getParameter("name");
		//String amount=request.getParameter("amount");
		//double value=Double.parseDouble(amount);
		
		User user=(User)request.getSession().getAttribute("user");
	
		Currency currency=currencyDao.getCurrencyById(currencyId);
		
		Account account=new Account(name,balance,user,currency);
		accountDao.addAccount(account);
		request.setAttribute("Success", account);

		return "createAccount";
	}
	
	@RequestMapping(value= "/updateAccount", method = RequestMethod.POST)
	public String updateAccount(@RequestParam int  id) throws SQLException, InvalidDataException {
		//get account by id
		
		
		//request.setAttribute("budgets",budgets);
		
		return "createAccount";
	}
	
	@RequestMapping(value= "/deleteAccount", method = RequestMethod.POST)
	public String deleteAccount(@RequestParam int id) throws SQLException, InvalidDataException {
		accountDao.deleteAccount(id);
		
		return "createAccount";
	}
}
