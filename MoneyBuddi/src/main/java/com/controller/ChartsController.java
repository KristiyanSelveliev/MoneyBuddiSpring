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

import com.model.Account;
import com.model.Transaction;
import com.model.User;
import com.model.dao.AccountDao;
import com.model.dao.TransactionDao;

@Controller
public class ChartsController {

	@Autowired
	private TransactionDao transactionDao;
	@Autowired
	private AccountDao accountDao;

	@RequestMapping(value = "/charts", method = RequestMethod.GET)
	public String prepareToShowTransactions(HttpServletRequest request) {
		User u = (User) request.getSession().getAttribute("user");
		try {
			//get all accounts
			ArrayList<Account> accounts=accountDao.getAllAccountsForUser(u);
			request.setAttribute("accounts", accounts);
			return "charts";
		} catch (Exception e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}
	
	@RequestMapping(value = "/showCharts", method = RequestMethod.GET)
	public String transactionsInPercents(HttpSession session, HttpServletRequest request) {

		User u = (User) request.getSession().getAttribute("user");
		try {
			// get account id
			long accountId=Long.parseLong(request.getParameter("accountId"));
			//get dates
			String from=request.getParameter("from");
        	LocalDate fromDate=LocalDate.parse(from);
        	String to=request.getParameter("to");
        	LocalDate toDate=LocalDate.parse(to);
			
			ArrayList<Transaction> incomeTransactionsFromTo = transactionDao.
					getIncomeByAccountFromToDate(fromDate,toDate,accountId);
			ArrayList<Transaction> expenseTransactionsFromTo = transactionDao.
					getExpenseByAccountFromToDate(fromDate,toDate,accountId);
			System.out.println("income size " + incomeTransactionsFromTo.size());
			System.out.println("expense size " + expenseTransactionsFromTo.size());
			// create map category -> amount
			Map<String, Double> incomeFromTo = new HashMap<String, Double>();
			Map<String, Double> expenseFromTo = new HashMap<String, Double>();
			// iterate income array and add all to map, when key is already seen, increase
			// amount

			// create two variables to hold total amount of income and expense and pass them
			// to request
			double totalIncomeAmount = 0;
			double totalExpenseAmount = 0;

			//count number of all transactions
			int transactionsCountIncome = 0;
			int transactionsCountExpense = 0;
			// income
			for (Transaction t : incomeTransactionsFromTo) {
				if (incomeFromTo.containsKey(t.getCategory().getCategory())) {
					//t.getCategory().getCategory() returns String- the name of the category
					incomeFromTo.put(t.getCategory().getCategory(), incomeFromTo.get(t.getCategory().getCategory()) + t.getAmount());
				} else { 
					incomeFromTo.put(t.getCategory().getCategory(), t.getAmount());
				}
				totalIncomeAmount += t.getAmount();
				transactionsCountIncome++;
			}
			// expense
			for (Transaction t : expenseTransactionsFromTo) {
				if (expenseFromTo.containsKey(t.getCategory().getCategory())) {
					expenseFromTo.put(t.getCategory().getCategory(), expenseFromTo.get(t.getCategory().getCategory()) + t.getAmount());
				} else {
					expenseFromTo.put(t.getCategory().getCategory(), t.getAmount());
				}
				totalExpenseAmount += t.getAmount();
				transactionsCountExpense++;
			}
			// add maps to request and forward
			request.setAttribute("incomeFromTo", incomeFromTo);
			request.setAttribute("expenseFromTo", expenseFromTo);
			request.setAttribute("totalIncomeAmount", totalIncomeAmount);
			request.setAttribute("totalExpenseAmount", totalExpenseAmount);
			request.setAttribute("transactionsCountIncome", transactionsCountIncome);
			request.setAttribute("transactionsCountExpense", transactionsCountExpense);
			
			return "showCharts";
		} catch (Exception e) {
			request.setAttribute("exception", e);
			return "error";
		}

	}

}
