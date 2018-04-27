package com.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.exceptions.InvalidDataException;
import com.model.Transaction;
import com.model.User;
import com.model.dao.TransactionDao;

@Controller
public class ShowMixedTransactionsController {

	@Autowired
	TransactionDao transactionDao;

	@RequestMapping(value = "/showMixed", method = RequestMethod.GET)
	public String getTransactions(HttpServletRequest request) {
		try {
			User u=(User) request.getSession().getAttribute("user");
			ArrayList<Transaction> transactions=transactionDao.getAllTransactionsByUser(u);
			request.setAttribute("transactions", transactions);
			return "showMixedTransactions";
		} catch (SQLException | InvalidDataException e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}
	
}
