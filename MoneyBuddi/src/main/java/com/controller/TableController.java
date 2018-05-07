package com.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.controller.manager.CurrencyConverter;
import com.exceptions.InvalidDataException;
import com.model.Currency.CurrencyType;
import com.model.Account;
import com.model.Transaction;
import com.model.TransactionDTO;
import com.model.User;
import com.model.dao.CurrencyDAO;
import com.model.dao.TransactionDao;

@RestController
public class TableController {
	
	@Autowired
	TransactionDao transactionDAO;
	
	@Autowired 
	CurrencyDAO currencyDAO;
	
	
	
	@RequestMapping(value = "/userExpense", method = RequestMethod.GET)
	 public ArrayList<TransactionDTO> userExpense(
			 @RequestParam String begin,
			 @RequestParam String end,
			 HttpSession session) throws Exception {
		
		
		User user=(User) session.getAttribute("user");
		LocalDate from=LocalDate.parse(begin);
		LocalDate to=LocalDate.parse(end);
		
		ArrayList<Transaction> transactions=transactionDAO.getExpenseByUserFromToDate(from, to, user.getId());
		this.transactionOrderer(transactions);
		
		return convertToDTO(transactions);
	}
	
	@RequestMapping(value = "/userIncome", method = RequestMethod.GET)
	 public ArrayList<TransactionDTO> userIncome(
			 @RequestParam String begin,
			 @RequestParam String end,
			 HttpSession session) throws Exception {
		
		User user=(User) session.getAttribute("user");
		LocalDate from=LocalDate.parse(begin);
		LocalDate to=LocalDate.parse(end);
		
		ArrayList<Transaction> transactions=transactionDAO.getIncomeByUserFromToDate(from, to, user.getId());
		this.transactionOrderer(transactions);
		
	
		return convertToDTO(transactions);
	}
	
	@RequestMapping(value = "/accountIncome", method = RequestMethod.GET)
	 public ArrayList<TransactionDTO> accIncome(
			 @RequestParam String begin,
			 @RequestParam String end,
			 @RequestParam long id) throws Exception {
		
		LocalDate from=LocalDate.parse(begin);
		LocalDate to=LocalDate.parse(end);
		
		ArrayList<Transaction> transactions=transactionDAO.getIncomeByAccountFromToDate(from, to, id);
		this.transactionOrderer(transactions);
		
		return convertToDTO(transactions);
	}
	
	@RequestMapping(value = "/accountExpense", method = RequestMethod.GET)
	 public ArrayList<TransactionDTO> accExpense(
			 @RequestParam String begin,
			 @RequestParam String end,
			 @RequestParam long id) throws Exception {
		
		LocalDate from=LocalDate.parse(begin);
		LocalDate to=LocalDate.parse(end);
		
		ArrayList<Transaction> transactions=transactionDAO.getExpenseByAccountFromToDate(from, to, id);
		this.transactionOrderer(transactions);
		return convertToDTO(transactions);
		
	}
	
	@RequestMapping(value="showTransactions",method=RequestMethod.GET)
	public ArrayList<TransactionDTO> showTransactions(
			@RequestParam String date,
			HttpSession session ) throws SQLException, InvalidDataException{
		
		User user=(User) session.getAttribute("user");
		ArrayList<Transaction> transactions=transactionDAO.getAllTransactionsByUserAndDate(user,LocalDate.parse(date));
		return convertToDTO(transactions);
	}
	
	@RequestMapping(value="editTransaction",method=RequestMethod.POST)
	public TransactionDTO editTransaction(
			@RequestParam long transactionId,
			HttpSession session ) throws SQLException, InvalidDataException{
	
		session.setAttribute("transactionId", transactionId);
		Transaction transaction=transactionDAO.getTransactionById(transactionId);
		
           if(transaction!=null) {	
			return new TransactionDTO(
				      transaction.getId(),
				      transaction.getCategory().getCategory(),
				      transaction.getAmount(), 
				      transaction.getAccount().getName()+"-"+transaction.getAccount().getCurrency().getType().toString(),
				      transaction.getDate().toLocalDate().toString(),
			          transaction.getType().toString());
           }
           return null;
	}
	
	@RequestMapping(value = "/categoryIncome", method = RequestMethod.GET)
	 public ArrayList<TransactionDTO> categoryIncome(
			 @RequestParam String begin,
			 @RequestParam String end,
			 @RequestParam long id, HttpSession session) throws Exception {
		
		LocalDate from=LocalDate.parse(begin);
		LocalDate to=LocalDate.parse(end);
		
		User u=(User)session.getAttribute("user");
		ArrayList<Transaction> transactions=transactionDAO.getIncomeByUserAndCategoryFromToDate(from, to, u.getId(), id);
		this.transactionOrderer(transactions);
		
		return convertToDTO(transactions);
	}
	
	@RequestMapping(value = "/categoryExpense", method = RequestMethod.GET)
	 public ArrayList<TransactionDTO> categoryExpense(
			 @RequestParam String begin,
			 @RequestParam String end,
			 @RequestParam long id, HttpSession session) throws Exception {
		
		LocalDate from=LocalDate.parse(begin);
		LocalDate to=LocalDate.parse(end);
		
		User u=(User)session.getAttribute("user");
		ArrayList<Transaction> transactions=transactionDAO.getExpenseByUserAndCategoryFromToDate(from, to, u.getId(), id);
		this.transactionOrderer(transactions);
		
		return convertToDTO(transactions);
	}
	
	private ArrayList<TransactionDTO> convertToDTO(ArrayList<Transaction> transactions) throws InvalidDataException, SQLException{
		ArrayList<TransactionDTO> dtos=new ArrayList<TransactionDTO>();
		for(Transaction t:transactions) {
			dtos.add(new TransactionDTO(
					      t.getId(),
					      t.getCategory().getCategory(),
					      t.getAmount(),
					      CurrencyConverter.convert(
					    		  t.getAmount(),
					    		  t.getCurrency(),
					    		  currencyDAO.getCurrencyByType(CurrencyType.EUR)),
					      t.getAccount().getName()+"-"+t.getAccount().getCurrency().getType().toString(),
					      t.getDate().toLocalDate().toString(),
			              t.getType().toString()));
			}
		return dtos;
	}
	

	private  void  transactionOrderer(ArrayList<Transaction> transactions) {
		 
		Collections.sort(transactions,(Transaction o1, Transaction o2)-> o1.getDate().compareTo(o2.getDate()));
				
	}

}
