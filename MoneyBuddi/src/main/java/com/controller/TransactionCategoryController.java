package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.exceptions.InvalidDataException;
import com.model.Category;
import com.model.Transaction.TransactionType;
import com.model.User;
import com.model.dao.CategoryDAO;
import com.model.dao.TransactionTypeDAO;

@Controller
public class TransactionCategoryController {

	@Autowired
	TransactionTypeDAO transactionTypeDao;
	@Autowired
	CategoryDAO categoryDao;

	@RequestMapping(value = "/transactioncategories", method = RequestMethod.GET)
	public String showTransactionTypes(HttpServletRequest request) throws Exception {

		// get all TransactionTypes
		List<TransactionType> types = transactionTypeDao.getAllTransactionTypes();
		request.setAttribute("Types", types);

		return "createCategory";
	}

	@RequestMapping(value = "/transactioncategories", method = RequestMethod.POST)
	public String createCategory(HttpServletRequest request) throws Exception {
		String name = request.getParameter("name");
		String type = request.getParameter("type");

		TransactionType transactionType = null;

		User user = (User) request.getSession().getAttribute("user");
		long userId = user.getId();
		try {
			transactionType = TransactionType.valueOf(type);
		} catch (IllegalArgumentException e) {
			throw new InvalidDataException("No such caregory");
		}
		Category category = new Category(name, transactionType, userId);
		request.setAttribute("Success", category);
		categoryDao.addCategory(category);

		List<TransactionType> types = transactionTypeDao.getAllTransactionTypes();
		request.setAttribute("Types", types); // loading types again

		return "createCategory";
	}

}
