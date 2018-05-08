package com.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.Account;
import com.model.Category;
import com.model.Transaction;
import com.model.Transaction.TransactionType;
import com.model.User;
import com.model.dao.AccountDao;
import com.model.dao.CategoryDAO;
import com.model.dao.TransactionDao;

@Controller
public class FilterController {
	//this controller provides data needed to fill the tables in tablelist.jsp
	
	@Autowired
	TransactionDao transactionDAO;
	@Autowired
	AccountDao accountDAO;
	@Autowired
	CategoryDAO categoryDAO;
	
	
	@RequestMapping(value="/tables",method=RequestMethod.GET)
     public String showTables(HttpSession session,HttpServletRequest request) throws Exception {
		 
		User u =(User) session.getAttribute("user");
		ArrayList<Transaction> expenses=transactionDAO.getAllExpenseTransactions(u);
		ArrayList<Transaction> incomes=transactionDAO.getAllIncomeTransactions(u);
		ArrayList<Account> accounts=accountDAO.getAllAccountsForUser(u);
		ArrayList<Category> incomeCategories=categoryDAO.getAllCategoriesByUserAndType(u, TransactionType.INCOME);
		ArrayList<Category> expenseCategories=categoryDAO.getAllCategoriesByUserAndType(u, TransactionType.EXPENSE);
				
		request.setAttribute("expenses", expenses);
		request.setAttribute("incomes", incomes);
		request.setAttribute("accounts", accounts);
		request.setAttribute("incomeCategories", incomeCategories);
		request.setAttribute("expenseCategories", expenseCategories);
		
		return "tablelist";
	}
}
