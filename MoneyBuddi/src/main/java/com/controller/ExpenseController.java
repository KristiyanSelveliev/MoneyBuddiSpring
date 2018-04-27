package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.Category;
import com.model.User;
import com.model.Transaction.TransactionType;
import com.model.dao.CategoryDAO;
import com.model.dao.TransactionDao;
import com.model.dao.UserDao;

@Controller
public class ExpenseController {

	@Autowired
	TransactionDao dao;
	@RequestMapping(value = "/addexpense", method = RequestMethod.GET)
	public String addExpense() {
		// retrieve all categories from db and pass them to the request
		try {
			// get all categories
			// get the user id from session and then retrieve from db by that id
			//User user = (User) request.getSession().getAttribute("user");
			//long userId = user.getId();
			//List<Category> categories = CategoryDAO.getInstance()
			//		.getAllCategoriesByUserAndType(UserDao.getInstance().getUserById(userId), TransactionType.EXPENSE);
			// add them to request
			//request.setAttribute("categories", categories);
			// forward to addincome.jsp
			//request.getRequestDispatcher("addexpense.jsp").forward(request, response);
		} catch (Exception e) {
			//request.getRequestDispatcher("error.jsp").forward(request, response);
		}

		return null;
	}

}
