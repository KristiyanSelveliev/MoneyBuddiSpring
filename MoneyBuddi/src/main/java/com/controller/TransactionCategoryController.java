package com.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public String showTransactionTypes(HttpServletRequest request,HttpSession session) throws Exception {

        User user=(User) session.getAttribute("user");
		
		ArrayList<Category> incomeCategories=(ArrayList<Category>) categoryDao.getAllCategoriesByUserAndType(user, TransactionType.INCOME);
		ArrayList<Category> expenseCategories=(ArrayList<Category>) categoryDao.getAllCategoriesByUserAndType(user, TransactionType.EXPENSE);
		
		ArrayList<TransactionType> types=(ArrayList<TransactionType>) transactionTypeDao.getAllTransactionTypes();
		
		request.setAttribute("expenses", categoryOrderer(expenseCategories));
		
		request.setAttribute("incomes", categoryOrderer(incomeCategories));
        request.setAttribute("types", types);
        
		return "createCategory";
	}

	@RequestMapping(value = "/createCategory", method = RequestMethod.POST)
	public String createCategory(HttpServletRequest request,
			@RequestParam String name,
			@RequestParam String type
			) throws Exception {
		

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

		
		return "redirect:/categories";
	}
	
	
	@RequestMapping(value="/categoryUpdate",method=RequestMethod.POST)
	public String updateCategory(
			@RequestParam long id,
			@RequestParam String name
			) throws Exception {
		
		 Category category=categoryDao.getCategoryByID(id);
		 category.setCategory(name);
		 categoryDao.updateCategory(category);
		 
		 return "redirect:/categories";
		
		
	}
	
	@RequestMapping(value="/categoryDelete",method=RequestMethod.POST)
	public String updateCategory(
			@RequestParam long id
			) throws Exception {
		
		 
		 
		 categoryDao.deleteCategory(id);
		 
		 return "redirect:/categories";
		
		
	}
	
	
	
	
	private ArrayList<Category> categoryOrderer(ArrayList<Category> categories) {
		
		Collections.sort(categories,new Comparator<Category>() {

			@Override
			public int compare(Category o1, Category o2) {
				return (int) (o1.getUserId()-o2.getUserId());
			}
		});			
			
		return categories;
	}

}
