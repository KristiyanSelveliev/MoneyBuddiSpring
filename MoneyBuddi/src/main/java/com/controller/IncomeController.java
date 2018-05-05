package com.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import com.model.Income;
import com.model.Transaction;
import com.model.User;
import com.model.Transaction.TransactionType;
import com.model.dao.AccountDao;
import com.model.dao.BudgetDao;
import com.model.dao.CategoryDAO;
import com.model.dao.CurrencyDAO;
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
	@Autowired
	private CurrencyDAO currencyDAO;
	@Autowired
	private BudgetDao budgetDAO;
	
	
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

	
	@RequestMapping(value = "/addIncome", method = RequestMethod.POST)
	public String createIncome(
			@RequestParam long categoryId, @RequestParam long currencyId,
			@RequestParam long accountId, @RequestParam double amount,
			@RequestParam String date,HttpSession session,
			HttpServletRequest request) throws Exception {

		Account account = accountDao.getAccountById(accountId);
		Currency transactionCurrency = currencyDAO.getCurrencyById(currencyId);

		Income income = new Income(amount, transactionCurrency, account, LocalDate.parse(date),
				categoryDao.getCategoryByID(categoryId));

		transactionManager.addTransaction(income, null);

		request.setAttribute("accountAmount",
				CurrencyConverter.convert(amount, transactionCurrency, account.getCurrency()));
		request.setAttribute("currency", account.getCurrency());

		//User u = (User) session.getAttribute("user");
		//u.setLastTransactionDate(LocalDate.now());

		return income(session, request);
	}

	@RequestMapping(value = "/addBudgetIncome", method = RequestMethod.POST)
	public String createBudgetIncome(
			@RequestParam long budgetId, @RequestParam long currencyId,
			@RequestParam long accountId, @RequestParam double amount,
			@RequestParam String date,HttpSession session,
			HttpServletRequest request) throws Exception {

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

		//User u = (User) session.getAttribute("user");
		//setLastTransactionDateToUser(u);

		return income(session, request);
	}

}
