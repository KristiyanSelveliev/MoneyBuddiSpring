package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.Category;
import com.model.Transaction;
import com.model.User;
import com.model.dao.TransactionDao;

@Controller
public class ChartsController {

	@Autowired
	private TransactionDao transactionDao;

	@RequestMapping(value = "/charts", method = RequestMethod.GET)
	public String transactionsInPercents(HttpSession session, HttpServletRequest request) {

		// get all transactions for current month
		User u = (User) request.getSession().getAttribute("user");
		try {
			ArrayList<Transaction> incomeTransactionsByMonth = transactionDao.getIncomeByUserForMonth(u);
			ArrayList<Transaction> expenseTransactionsByMonth = transactionDao.getExpenseByUserForMonth(u);
			System.out.println("income size " + incomeTransactionsByMonth.size());
			System.out.println("expense size " + expenseTransactionsByMonth.size());
			// create map category -> amount
			Map<String, Double> incomeByMonth = new HashMap<String, Double>();
			Map<String, Double> expenseByMonth = new HashMap<String, Double>();
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
			for (Transaction t : incomeTransactionsByMonth) {
				if (incomeByMonth.containsKey(t.getCategory().getCategory())) {
					//t.getCategory().getCategory() returns String- the name of the category
					incomeByMonth.put(t.getCategory().getCategory(), incomeByMonth.get(t.getCategory().getCategory()) + t.getAmount());
				} else { 
					incomeByMonth.put(t.getCategory().getCategory(), t.getAmount());
				}
				totalIncomeAmount += t.getAmount();
				transactionsCountIncome++;
			}
			// expense
			for (Transaction t : expenseTransactionsByMonth) {
				if (expenseByMonth.containsKey(t.getCategory().getCategory())) {
					expenseByMonth.put(t.getCategory().getCategory(), expenseByMonth.get(t.getCategory().getCategory()) + t.getAmount());
				} else {
					expenseByMonth.put(t.getCategory().getCategory(), t.getAmount());
				}
				totalExpenseAmount += t.getAmount();
				transactionsCountExpense++;
			}
			// add maps to request and forward
			request.setAttribute("incomeByMonth", incomeByMonth);
			request.setAttribute("expenseByMonth", expenseByMonth);
			request.setAttribute("totalIncomeAmount", totalIncomeAmount);
			request.setAttribute("totalExpenseAmount", totalExpenseAmount);
			request.setAttribute("transactionsCountIncome", transactionsCountIncome);
			request.setAttribute("transactionsCountExpense", transactionsCountExpense);
			
			return "charts";
		} catch (Exception e) {
			request.setAttribute("exception", e);
			return "error";
		}

	}

}
