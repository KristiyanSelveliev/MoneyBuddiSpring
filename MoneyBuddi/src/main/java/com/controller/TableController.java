package com.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exceptions.InvalidDataException;
import com.model.Transaction;
import com.model.User;
import com.model.dao.TransactionDao;

@RestController
public class TableController {
	
	
	
	public static class Helper{
		
		public long id;
		public String category;
		public double amount;
		public String account;
		public String date;
		public String type;
		
		public Helper(long id, String category, double amount, String account, String date) {
			
			this.id = id;
			this.category = category;
			this.amount = amount;
			this.account = account;
			this.date = date;
		}
		public Helper(long id, String category, double amount, String account, String date,String type) {
			this(id, category, amount, account, date);
			this.type=type;
			
		}
	}
		
	

		
	
	@Autowired
	TransactionDao transactionDAO;
	
	@RequestMapping(value = "/userExpense", method = RequestMethod.GET)
	@ResponseBody
	 public ArrayList<Helper> userExpense(
			 @RequestParam String begin,
			 @RequestParam String end,
			 HttpSession session) throws Exception {
		
		
		User user=(User) session.getAttribute("user");
		LocalDate from=LocalDate.parse(begin);
		LocalDate to=LocalDate.parse(end);
		
		ArrayList<Transaction> transactions=transactionDAO.getExpenseByUserFromToDate(from, to, user.getId());
		this.transactionOrderer(transactions);
		
		ArrayList<Helper> helpers =new ArrayList();
		System.out.println(1);
		for(Transaction t:transactions) {
		helpers.add(new Helper(
				      t.getId(),
				      t.getCategory().getCategory(),
				      t.getAmount(),
				      t.getAccount().getName()+"-"+t.getAccount().getCurrency().getType().toString(),
				      t.getDate().toLocalDate().toString()));
		}
		
		return helpers;
	}
	
	@RequestMapping(value = "/userIncome", method = RequestMethod.GET)
	@ResponseBody
	 public ArrayList<Helper> userIncome(
			 @RequestParam String begin,
			 @RequestParam String end,
			 HttpSession session) throws Exception {
		
		User user=(User) session.getAttribute("user");
		LocalDate from=LocalDate.parse(begin);
		LocalDate to=LocalDate.parse(end);
		
		ArrayList<Transaction> transactions=transactionDAO.getIncomeByUserFromToDate(from, to, user.getId());
		this.transactionOrderer(transactions);
		
		ArrayList<Helper> helpers =new ArrayList();
		
		for(Transaction t:transactions) {
		helpers.add(new Helper(
				      t.getId(),
				      t.getCategory().getCategory(),
				      t.getAmount(),
				      t.getAccount().getName()+"-"+t.getAccount().getCurrency().getType().toString(),
				      t.getDate().toLocalDate().toString()));
		}
		
		return helpers;
	}
	
	
	
	@RequestMapping(value = "/accIncome", method = RequestMethod.GET)
	@ResponseBody
	 public ArrayList<Helper> accIncome(
			 @RequestParam String begin,
			 @RequestParam String end,
			 @RequestParam long id) throws Exception {
		
		System.out.println(id);
		LocalDate from=LocalDate.parse(begin);
		LocalDate to=LocalDate.parse(end);
		
		ArrayList<Transaction> transactions=transactionDAO.getIncomeByAccountFromToDate(from, to, id);
		this.transactionOrderer(transactions);
		
		ArrayList<Helper> helpers =new ArrayList();
		for(Transaction t:transactions) {
		helpers.add(new Helper(
				      t.getId(),
				      t.getCategory().getCategory(),
				      t.getAmount(),
				      t.getAccount().getName()+"-"+t.getAccount().getCurrency().getType().toString(),
				      t.getDate().toLocalDate().toString()));
		}
		
		return helpers;
	}
	
	@RequestMapping(value = "/accExpense", method = RequestMethod.GET)
	@ResponseBody
	 public ArrayList<Helper> accExpense(
			 @RequestParam String begin,
			 @RequestParam String end,
			 @RequestParam long id) throws Exception {
		
		System.out.println(id);
		LocalDate from=LocalDate.parse(begin);
		LocalDate to=LocalDate.parse(end);
		
		ArrayList<Transaction> transactions=transactionDAO.getExpenseByAccountFromToDate(from, to, id);
		this.transactionOrderer(transactions);
		
		ArrayList<Helper> helpers =new ArrayList();
		for(Transaction t:transactions) {
		helpers.add(new Helper(
				      t.getId(),
				      t.getCategory().getCategory(),
				      t.getAmount(),
				      t.getAccount().getName()+"-"+t.getAccount().getCurrency().getType().toString(),
				      t.getDate().toLocalDate().toString()));
		}
		
		return helpers;
	}
	
	
	
	
	@RequestMapping(value="showTransactions",method=RequestMethod.GET)
	@ResponseBody
	public ArrayList<Helper> showTransactions(
			@RequestParam String date,
			HttpSession session ) throws SQLException, InvalidDataException{
		User user=(User) session.getAttribute("user");
		ArrayList<Transaction> transactions=transactionDAO.getAllTransactionsByUserAndDate(user,LocalDate.parse(date));
		ArrayList<Helper> helpers =new ArrayList();
		for(Transaction t:transactions) {
			helpers.add(new Helper(
					      t.getId(),
					      t.getCategory().getCategory(),
					      t.getAmount(),
					      t.getAccount().getName()+"-"+t.getAccount().getCurrency().getType().toString(),
					      t.getDate().toLocalDate().toString(),
			              t.getType().toString()));
			}
			return helpers;
		
		
	}
	
	@RequestMapping(value="editTransaction",method=RequestMethod.GET)
	@ResponseBody
	public Helper editTransaction(
			@RequestParam long transactionId,
			HttpSession session ) throws SQLException, InvalidDataException{
		User user=(User) session.getAttribute("user");
		session.setAttribute("transactionId", transactionId);
		Transaction transaction=transactionDAO.getTransactionById(transactionId);
		
		
           if(transaction!=null) {	
			return new Helper(
				      transaction.getId(),
				      transaction.getCategory().getCategory(),
				      transaction.getAmount(),
				      transaction.getAccount().getName()+"-"+transaction.getAccount().getCurrency().getType().toString(),
				      transaction.getDate().toLocalDate().toString(),
			          transaction.getType().toString());
           }
           return null;
		
		
	}
	
	
		
	
	
	
	private  void  transactionOrderer(ArrayList<Transaction> transactions) {
		 
		Collections.sort(transactions,new Comparator<Transaction>() {
			@Override
			public int compare(Transaction o1, Transaction o2) {
				
				return o1.getDate().compareTo(o2.getDate());
			}
		});
	}

}
