package com.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exceptions.InvalidDataException;
import com.model.Transaction;
import com.model.TransactionDTO;
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
		
		request.setAttribute("numExpenses", getNumberOfIncomesAndExpenses(u).get("numExpenses"));
		request.setAttribute("numIncomes", getNumberOfIncomesAndExpenses(u).get("numIncomes"));

		request.setAttribute("statistics",
				this.getTransactionsForStatistics(u, LocalDate.now().minusDays(DEFAULT_NUM_DAYS), LocalDate.now()));

		return "transactions";
	}

	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public String transactionsShow(HttpSession session, HttpServletRequest request, @RequestParam String beginDate,
			@RequestParam String endDate) throws Exception {

		User u = (User) session.getAttribute("user");

		request.setAttribute("numExpenses", getNumberOfIncomesAndExpenses(u).get("numExpenses"));
		request.setAttribute("numIncomes", getNumberOfIncomesAndExpenses(u).get("numIncomes"));

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
	
	@RequestMapping(value="editTransaction",method=RequestMethod.POST)
	@ResponseBody
	public TransactionDTO editTransaction(
			@RequestParam long transactionId,
			HttpSession session ) throws SQLException, InvalidDataException{
	
		session.setAttribute("transactionId", transactionId);
		Transaction transaction=transactionDao.getTransactionById(transactionId);
		
           if(transaction!=null) {	
			return new TransactionDTO(
				      transaction.getId(),
				      transaction.getCategory().getCategory(),
				      transaction.getAmount(), 
				      transaction.getAccount().getName()+"-"+transaction.getAccount().getCurrency().getType().toString(),
				      transaction.getDate().toLocalDate().toString(),
			          transaction.getType().toString());
           }
           return null;
	}

	private TreeMap<LocalDate, MyEntry> getTransactionsForStatistics(User user, LocalDate begin, LocalDate end)
			throws Exception {
		//date -> number of incomes and expenses
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

	private HashMap<String, Integer> getNumberOfIncomesAndExpenses(User u) throws Exception{
		ArrayList<Transaction> expenses = transactionDao.getAllExpenseTransactions(u);
		ArrayList<Transaction> incomes = transactionDao.getAllIncomeTransactions(u);

		// key is income or expense, value is the number of each
		HashMap<String, Integer> numberTransactions=new HashMap<String, Integer>();
		// count number of all transactions
		int transactionsCountIncome = 0;
		int transactionsCountExpense = 0;
		// income
		for (Transaction t : incomes) {
			transactionsCountIncome++;
		}
		numberTransactions.put("numIncomes", transactionsCountIncome);
		// expense
		for (Transaction t : expenses) {
			transactionsCountExpense++;
		}
		numberTransactions.put("numExpenses", transactionsCountExpense);
		
		return numberTransactions;
	}
}
