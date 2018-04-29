package com.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.model.Account;
import com.model.Transaction;
import com.model.User;
import com.model.dao.AccountDao;
import com.model.dao.TransactionDao;

@Controller
public class DonutChartsController {

	@Autowired
	private TransactionDao transactionDao;
	@Autowired
	private AccountDao accountDao;

	@RequestMapping(value = "/donutcharts", method = RequestMethod.GET)
	public String prepareToShowTransactions(HttpServletRequest request) {
		User u = (User) request.getSession().getAttribute("user");
		try {
			// get all accounts
			ArrayList<Account> accounts = accountDao.getAllAccountsForUser(u);
			request.setAttribute("accounts", accounts);
			return "donutchart";
		} catch (Exception e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(value = "/showDonutChart", method = RequestMethod.GET)
	public String piechart(HttpSession session, HttpServletRequest request) {

		try {
			// get account id
			long accountId = Long.parseLong(request.getParameter("accountId"));
			// get dates
			String from = request.getParameter("from");
			LocalDate fromDate = LocalDate.parse(from);
			String to = request.getParameter("to");
			LocalDate toDate = LocalDate.parse(to);

			ArrayList<Transaction> incomeTransactionsFromTo = transactionDao.getShortIncomeByAccountFromToDate(fromDate,
					toDate, accountId);
			//ArrayList<Transaction> expenseTransactionsFromTo = transactionDao.getExpenseByAccountFromToDate(fromDate,
			//		toDate, accountId);
			
			//create jsons
			String incomeJson = new Gson().toJson(incomeTransactionsFromTo);
			//String expenseJson = new Gson().toJson(expenseTransactionsFromTo);
			//set as sessions attributes
			session.setAttribute("incomeJson", incomeJson);
			//session.setAttribute("expenseJson", expenseJson);
			System.out.println("=========");
			System.out.println(incomeJson);
			System.out.println("=========");

			
			
			return "showDonutChart";
		} catch (Exception e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}

}
