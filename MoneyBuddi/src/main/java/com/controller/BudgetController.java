package com.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.exceptions.InvalidDataException;
import com.model.Budget;
import com.model.User;
import com.model.dao.BudgetDao;

@Controller
public class BudgetController {
	
	@Autowired
	private BudgetDao budgetDAO;
	
	@RequestMapping(value= "/budgets", method = RequestMethod.GET)
	public String showBudgets(HttpServletRequest request,HttpSession session) throws SQLException, InvalidDataException {
		User u=(User) session.getAttribute("user");
		ArrayList<Budget> budgets=(ArrayList<Budget>) budgetDAO.getAllBudgetsForUser(u);
		request.setAttribute("budgets",budgets);
		
		return "budgetss";
		
	}

}
