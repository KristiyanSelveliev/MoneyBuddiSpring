package com.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.controller.manager.TransactionManager;
import com.exceptions.InvalidDataException;
import com.model.Account;
import com.model.Category;
import com.model.Currency;
import com.model.Income;
import com.model.Transaction;
import com.model.User;
import com.model.Transaction.TransactionType;
import com.model.dao.AccountDao;
import com.model.dao.CategoryDAO;
import com.model.dao.TransactionDao;
import com.model.dao.UserDao;

@Controller
public class IncomeController {

	@Autowired
	private TransactionDao transactionDao;
	@Autowired
	private CategoryDAO categoryDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private TransactionManager transactionManager;
	
	@RequestMapping(value = "/addincome", method = RequestMethod.GET)
	public String showExpenseCategories(HttpSession session, HttpServletRequest request) {
		// retrieve all categories from db and pass them to the request
		try {
			// get all categories
			// get the user id from session and then retrieve from db by that id
			User user = (User) session.getAttribute("user");
			long userId = user.getId();
			ArrayList<Account> accounts=accountDao.getAllAccountsForUser(user);
			request.setAttribute("accounts", accounts);
			List<Category> categories = categoryDao
				.getAllCategoriesByUserAndType(userDao.getUserById(userId), TransactionType.INCOME);
			// add them to request
			request.setAttribute("categories", categories);
			return "addincome";
		} catch (Exception e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(value = "/addincome", method = RequestMethod.POST)
	public String addExpense(HttpSession session, HttpServletRequest request) {
		try {
			//if the entered value is not null or empty
			String amountAsString=request.getParameter("amount");
			if(amountAsString!=null && !amountAsString.isEmpty()) {
				//get data from request
				double amount=Double.parseDouble(amountAsString);
				long categoryId=Long.parseLong(request.getParameter("categoryId"));
				Category category=categoryDao.getCategoryByID(categoryId);
				long accountId=Long.parseLong(request.getParameter("accountId"));
				Account account=accountDao.getAccountById(accountId);
				Currency currency=account.getCurrency();
				
				//create transaction (needed: amount, currency, acount, date, category, type=income) 
				Transaction income=new Income(amount,currency,account,LocalDate.now(),category);
				//save it to db
				transactionManager.addTransaction(income,null);
				//forward to main.jsp
				return "main";
			}else {
				//if the entered data is not valid throw exception 
				throw new InvalidDataException("invalid amount");
			}
		}catch(Exception e) {
			//catch and forward to error.jsp
			request.setAttribute("exception", e);
			//TODO do it later in one error.jsp, check if the user is logged
			return "errorWhenLogged";
		}
	}
	
	@RequestMapping(value = "/showIncome", method = RequestMethod.GET)
	public String showExpense( HttpServletRequest request, HttpSession session) {
		try {
			  //show expense for the selected account
			  User user=(User)session.getAttribute("user");
			  ArrayList<Transaction> incomeTransactions=transactionDao.getAllIncomeTransactions(user);
			  //add them to request and forward it
			  request.setAttribute("incomeTransactions", incomeTransactions);
			  return "showIncomeTransactions";
		  }catch(Exception e) {
			  request.setAttribute("exception", e);
			  return "error";
		  }
	}
	
}
