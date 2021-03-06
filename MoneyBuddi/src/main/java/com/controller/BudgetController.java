
package com.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.exceptions.InvalidDataException;
import com.model.Budget;
import com.model.Category;
import com.model.Currency;
import com.model.User;
import com.model.dao.BudgetDao;
import com.model.dao.CategoryDAO;
import com.model.dao.CurrencyDAO;

@Controller
public class BudgetController {
	
	@Autowired
	private BudgetDao budgetDAO;
	
	@Autowired 
	private CategoryDAO categoryDAO;
	
	@Autowired 
	private CurrencyDAO currencyDAO;
	
	@RequestMapping(value= "/budgets", method = RequestMethod.GET)
	public String showBudgets(HttpServletRequest request,HttpSession session) throws SQLException, InvalidDataException {
		User u=(User) session.getAttribute("user");
		
		ArrayList<Budget> budgets=(ArrayList<Budget>) budgetDAO.getAllBudgetsForUser(u.getId());
		ArrayList<Category> categories= (ArrayList<Category>) categoryDAO.getAllCategoriesByUser(u);
		ArrayList<Currency> currencies=(ArrayList<Currency>) currencyDAO.getAllCurrencies();
		
		
		request.setAttribute("budgets",budgets);
		request.setAttribute("categories",categories);
		request.setAttribute("currencies",currencies);
		request.setAttribute("now", LocalDate.now());
		
		return "budgetss";
		
	}
	
	@RequestMapping(value= "/budgetCreate", method = RequestMethod.POST)
	public String createBudget(
			@RequestParam int categoryId,
			@RequestParam int currencyId,
			@RequestParam double amount,
			@RequestParam String begin,
			@RequestParam String end ,HttpSession session
			) throws SQLException, InvalidDataException {
		 
		LocalDate beginDate=LocalDate.parse(begin);
		LocalDate endDate=LocalDate.parse(end);
		if(endDate.isBefore(LocalDate.now())) {
			throw new InvalidDataException("Sorry you can't create budgets previous to now");
		}
		
		     budgetDAO.addBudget(new Budget(
		    		 categoryDAO.getCategoryByID(categoryId),
		    		 amount,
		    		 (User) session.getAttribute("user"),
		    		 currencyDAO.getCurrencyById(currencyId), 
		    		 beginDate,
		    		 endDate));
		
		
		return "redirect:/budgets";
		
	}
	
	@RequestMapping(value= "/budgetUpdate", method = RequestMethod.POST)
	public String updateBudget(@RequestParam int  id,
			@RequestParam double  amount,
			@RequestParam int duration) throws Exception {
		
		Budget budget=budgetDAO.getBudgetById(id);
	    if(duration<=0) {
	    	throw new InvalidDataException("You probably entered wrong value");
	    }
	    budget.setEndDate(LocalDate.now().plusDays(duration));
		budget.setAmount(amount);
		budgetDAO.updateBudget(budget);
		
		return "redirect:/budgets";
	}
	
	@RequestMapping(value= "/budgetDelete", method = RequestMethod.POST)
	public String deleteBudget(@RequestParam int id) throws SQLException, InvalidDataException {
		budgetDAO.deleteBudget(id);
		
		return "redirect:/budgets";
	}
}
