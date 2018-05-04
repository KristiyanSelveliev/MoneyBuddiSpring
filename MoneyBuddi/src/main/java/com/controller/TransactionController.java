package com.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.exceptions.InvalidDataException;
import com.model.Transaction;
import com.model.User;
import com.model.dao.TransactionDao;

@Controller
public class TransactionController {

	@Autowired
	private TransactionDao transactionDao;
	
	private static final int DEFAULT_NUM_DAYS = 7;

	public static class MyEntry {
		private int incomeAmount;
		private int expenseAmount;

		public MyEntry(int incomeAmount, int expenseAmount) {

			this.incomeAmount = incomeAmount;
			this.expenseAmount = expenseAmount;
		}

		public int getIncomeAmount() {
			return incomeAmount;
		}

		public int getExpenseAmount() {
			return expenseAmount;
		}

	}

	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
	public String transactions(HttpSession session, HttpServletRequest request) throws Exception {
		User u = (User) session.getAttribute("user");

		ArrayList<Transaction> expenses = transactionDao.getAllExpenseTransactions(u);
		ArrayList<Transaction> incomes = transactionDao.getAllIncomeTransactions(u);

		// count number of all transactions
		int transactionsCountIncome = 0;
		int transactionsCountExpense = 0;
		// income
		for (Transaction t : incomes) {
			transactionsCountIncome++;
		}
		// expense
		for (Transaction t : expenses) {
			transactionsCountExpense++;
		}

		request.setAttribute("numExpenses", transactionsCountExpense);
		request.setAttribute("numIncomes", transactionsCountIncome);

		request.setAttribute("statistics",
				this.getTransactionsForStatistics(u, LocalDate.now().minusDays(DEFAULT_NUM_DAYS), LocalDate.now()));

		return "transactions";
	}

	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public String transactionsShow(HttpSession session, HttpServletRequest request, @RequestParam String beginDate,
			@RequestParam String endDate) throws Exception {

		User u = (User) session.getAttribute("user");

		ArrayList<Transaction> expenses = transactionDao.getAllExpenseTransactions(u);
		ArrayList<Transaction> incomes = transactionDao.getAllIncomeTransactions(u);

		int transactionsCountIncome = 0;
		int transactionsCountExpense = 0;
		// income
		for (Transaction t : incomes) {

			transactionsCountIncome++;
		}
		// expense
		for (Transaction t : expenses) {

			transactionsCountExpense++;
		}

		request.setAttribute("numExpenses", transactionsCountExpense);
		request.setAttribute("numIncomes", transactionsCountIncome);

		request.setAttribute("statistics",
				this.getTransactionsForStatistics(u, LocalDate.parse(beginDate), LocalDate.parse(endDate)));

		return "transactions";
	}

	
	@RequestMapping(value = "/updateTransaction", method = RequestMethod.POST)
	public String updateTransaction(HttpSession session, @RequestParam double amount)
			throws SQLException, InvalidDataException {
		long transactionId = (long) session.getAttribute("transactionId");
		Transaction transaction = transactionDao.getTransactionById(transactionId);
		transaction.setAmount(amount);
		transactionDao.changeTransaction(transaction);

		return "redirect:/transactions";
	}

	@RequestMapping(value = "/deleteTransaction", method = RequestMethod.POST)
	public String deleteTransaction(HttpSession session) throws SQLException, InvalidDataException {
		long id = (long) session.getAttribute("transactionId");
		Transaction transaction = transactionDao.getTransactionById(id);
		transactionDao.deleteTransaction(transaction);

		return "redirect:/transactions";
	}

	private void setLastTransactionDateToUser(User u) {
		// set new last transaction date for user
		u.setLastTransactionDate(LocalDate.now());
	}

	private TreeMap<LocalDate, MyEntry> getTransactionsForStatistics(User user, LocalDate begin, LocalDate end)
			throws Exception {
		TreeMap<LocalDate, MyEntry> transactionCount = new TreeMap<LocalDate, MyEntry>();

		int incomes = 0;
		int expenses = 0;
		
		LocalDate currentDate = end;
		 	while (currentDate.isAfter(begin)) {

			expenses = transactionDao.getExpenseByUserFromToDate(currentDate, currentDate, user.getId()).size();

			incomes = transactionDao.getIncomeByUserFromToDate(currentDate, currentDate, user.getId()).size();

			transactionCount.put(currentDate, new MyEntry(incomes, expenses));
			currentDate = currentDate.minusDays(1);

		}
		return transactionCount;
	}

}
