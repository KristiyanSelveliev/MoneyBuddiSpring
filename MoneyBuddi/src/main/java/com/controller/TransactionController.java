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

import com.controller.manager.CurrencyConverter;
import com.controller.manager.TransactionManager;
import com.exceptions.InvalidDataException;
import com.model.Account;
import com.model.Budget;
import com.model.Category;
import com.model.Currency;
import com.model.Expense;
import com.model.Income;
import com.model.Transaction;
import com.model.Transaction.TransactionType;
import com.model.User;
import com.model.dao.AccountDao;
import com.model.dao.BudgetDao;
import com.model.dao.CategoryDAO;
import com.model.dao.CurrencyDAO;
import com.model.dao.TransactionDao;

@Controller
public class TransactionController {

	@Autowired
	private TransactionDao transactionDao;
	@Autowired
	private CategoryDAO categoryDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private TransactionManager transactionManager;
	@Autowired
	private CurrencyDAO currencyDAO;
	@Autowired
	private BudgetDao budgetDAO;

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

	@RequestMapping(value = "/addIncome", method = RequestMethod.GET)
	public String income(HttpSession session, HttpServletRequest request) throws Exception {
		User u = (User) session.getAttribute("user");

		List<Category> categories = categoryDao.getAllCategoriesByUserAndType(u, TransactionType.INCOME);
		List<Currency> currencies = (List<Currency>) currencyDAO.getAllCurrencies();
		List<Account> accounts = accountDao.getAllAccountsForUser(u);
		List<Budget> budgets = (List<Budget>) budgetDAO.getAllIncomeBudgetForUser(u.getId());

		request.setAttribute("categories", categories);
		request.setAttribute("currencies", currencies);
		request.setAttribute("accounts", accounts);
		request.setAttribute("budgets", budgets);

		return "addInc";
	}

	@RequestMapping(value = "/addExpense", method = RequestMethod.GET)
	public String expense(HttpSession session, HttpServletRequest request) throws Exception {
		User u = (User) session.getAttribute("user");

		List<Category> categories = categoryDao.getAllCategoriesByUserAndType(u, TransactionType.EXPENSE);
		List<Currency> currencies = (List<Currency>) currencyDAO.getAllCurrencies();
		List<Account> accounts = accountDao.getAllAccountsForUser(u);
		List<Budget> budgets = (List<Budget>) budgetDAO.getAllExpenseBudgetForUser(u.getId());

		request.setAttribute("categories", categories);
		request.setAttribute("currencies", currencies);
		request.setAttribute("accounts", accounts);
		request.setAttribute("budgets", budgets);

		return "addExp";
	}

	@RequestMapping(value = "/addExpense", method = RequestMethod.POST)
	public String createExpense(@RequestParam long categoryId, @RequestParam long currencyId,
			@RequestParam long accountId, @RequestParam double amount, @RequestParam String date,
			HttpServletRequest request, HttpSession session) throws Exception {

		Account account = accountDao.getAccountById(accountId);
		Currency transactionCurrency = currencyDAO.getCurrencyById(currencyId);

		Expense expense = new Expense(amount, transactionCurrency, account, LocalDate.parse(date),
				categoryDao.getCategoryByID(categoryId));

		transactionManager.addTransaction(expense, null);
		request.setAttribute("accountAmount",
				CurrencyConverter.convert(amount, transactionCurrency, account.getCurrency()));
		request.setAttribute("currency", account.getCurrency());

		User u = (User) session.getAttribute("user");
		setLastTransactionDateToUser(u);

		return expense(session, request);

	}

	@RequestMapping(value = "/addBudgetExpense", method = RequestMethod.POST)
	public String createBudgetExpense(@RequestParam long budgetId, @RequestParam long currencyId,
			@RequestParam long accountId, @RequestParam double amount, @RequestParam String date,
			HttpServletRequest request, HttpSession session) throws Exception {

		Account account = accountDao.getAccountById(accountId);
		Currency transactionCurrency = currencyDAO.getCurrencyById(currencyId);
		Budget budget = budgetDAO.getBudgetById(budgetId);

		Expense expense = new Expense(amount, transactionCurrency, account, LocalDate.parse(date),
				budget.getCategory());

		transactionManager.addTransaction(expense, budget);
		request.setAttribute("accountAmount",
				CurrencyConverter.convert(amount, transactionCurrency, account.getCurrency()));
		request.setAttribute("budgetAmount",
				CurrencyConverter.convert(amount, transactionCurrency, budget.getCurrency()));
		request.setAttribute("currency", account.getCurrency());
		request.setAttribute("budgetCurrency", budget.getCurrency());

		User u = (User) session.getAttribute("user");
		setLastTransactionDateToUser(u);

		return expense(session, request);

	}

	@RequestMapping(value = "/addIncome", method = RequestMethod.POST)
	public String createIncome(@RequestParam long categoryId, @RequestParam long currencyId,
			@RequestParam long accountId, @RequestParam double amount, @RequestParam String date,
			HttpServletRequest request, HttpSession session) throws Exception {

		Account account = accountDao.getAccountById(accountId);
		Currency transactionCurrency = currencyDAO.getCurrencyById(currencyId);

		Income income = new Income(amount, transactionCurrency, account, LocalDate.parse(date),
				categoryDao.getCategoryByID(categoryId));

		transactionManager.addTransaction(income, null);

		request.setAttribute("accountAmount",
				CurrencyConverter.convert(amount, transactionCurrency, account.getCurrency()));
		request.setAttribute("currency", account.getCurrency());

		User u = (User) session.getAttribute("user");
		setLastTransactionDateToUser(u);

		return income(session, request);
	}

	@RequestMapping(value = "/addBudgetIncome", method = RequestMethod.POST)
	public String createBudgetIncome(@RequestParam long budgetId, @RequestParam long currencyId,
			@RequestParam long accountId, @RequestParam double amount, @RequestParam String date,
			HttpServletRequest request, HttpSession session) throws Exception {

		Account account = accountDao.getAccountById(accountId);
		Currency transactionCurrency = currencyDAO.getCurrencyById(currencyId);
		Budget budget = budgetDAO.getBudgetById(budgetId);

		Income income = new Income(amount, transactionCurrency, account, LocalDate.parse(date), budget.getCategory());

		transactionManager.addTransaction(income, budget);
		request.setAttribute("accountAmount",
				CurrencyConverter.convert(amount, transactionCurrency, account.getCurrency()));
		request.setAttribute("budgetAmount",
				CurrencyConverter.convert(amount, transactionCurrency, budget.getCurrency()));
		request.setAttribute("currency", account.getCurrency());
		request.setAttribute("budgetCurrency", budget.getCurrency());

		User u = (User) session.getAttribute("user");
		setLastTransactionDateToUser(u);

		return expense(session, request);
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
		System.out.println(
				expenses = transactionDao.getExpenseByUserFromToDate(currentDate, currentDate, user.getId()).size());
		while (currentDate.isAfter(begin)) {

			expenses = transactionDao.getExpenseByUserFromToDate(currentDate, currentDate, user.getId()).size();

			incomes = transactionDao.getIncomeByUserFromToDate(currentDate, currentDate, user.getId()).size();

			transactionCount.put(currentDate, new MyEntry(incomes, expenses));
			currentDate = currentDate.minusDays(1);

		}
		return transactionCount;
	}

}
