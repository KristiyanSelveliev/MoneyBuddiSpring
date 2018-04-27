package com.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.manager.TransactionManager;
import com.model.Account;
import com.model.Category;
import com.model.Currency;
import com.model.Expense;
import com.model.Income;
import com.model.Transaction;
import com.model.User;
import com.model.Transaction.TransactionType;
import com.model.dao.AccountDao;
import com.model.dao.CategoryDAO;
import com.model.dao.TransactionDao;
import com.model.dao.UserDao;

@WebServlet("/addexpense")
public class AddExpenseServlet extends HttpServlet {
	/*
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//retrieve all categories from db and pass them to the request
		try {
			//get all categories
			//get the user id from session and then retrieve from db by that id
			User user=(User)request.getSession().getAttribute("user");
			long userId=user.getId();
			List<Category> categories = CategoryDAO.getInstance()
					.getAllCategoriesByUserAndType(UserDao.getInstance().getUserById(userId), TransactionType.EXPENSE);
			//add them to request
			request.setAttribute("categories", categories);
			//forward to addincome.jsp
			request.getRequestDispatcher("addexpense.jsp").forward(request, response);
		}catch(Exception e) {
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//if the entered value is not null or empty
			String amountAsString=request.getParameter("amount");
			if(amountAsString!=null && !amountAsString.isEmpty()) {
				//get data from request
				double amount=Double.parseDouble(amountAsString);
				long categoryId=Long.parseLong(request.getParameter("categoryId"));
				Category category=CategoryDAO.getInstance().getCategoryByID(categoryId);
				long accountId=(long)request.getSession().getAttribute("accountId");
				Account account=AccountDao.getInstance().getAccountById(accountId);
				Currency currency=account.getCurrency();
				
				//create transaction (needed: amount, currency, acount, date, category, type=income) 
				Transaction expense=new Expense(amount,currency,account,LocalDate.now(),category);
				//save it to db
				//TransactionDao.getInstance().addTransaction(expense);
				TransactionManager.getInstance().addTransaction(expense,null);
				//forward to main.jsp
				request.getRequestDispatcher("main.jsp").forward(request, response);
				System.out.println("Expense added");
			}else {
				//if the entered data is not valid throw exception 
			}
		}catch(Exception e) {
			//catch and forward to error.jsp
			request.setAttribute("exception", e);
			//TODO do it later in one error.jsp, check if the user is logged
			request.getRequestDispatcher("errorWhenLogged.jsp").forward(request, response);
		}
	}
	*/
}
