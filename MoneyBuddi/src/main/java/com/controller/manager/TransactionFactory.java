package com.controller.manager;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.exceptions.InvalidDataException;
import com.model.Account;
import com.model.Category;
import com.model.Currency;
import com.model.Expense;
import com.model.Income;
import com.model.Transaction;
import com.model.Transaction.TransactionType;

@Component
public class TransactionFactory {
	
	
	  public Transaction createTransaction(TransactionType type,long id, double amount, Currency currency, Account account, LocalDate date, Category category) throws InvalidDataException {
		 if(type.equals(TransactionType.INCOME)) {
			 return new Income(id,amount,currency,account,date,category); 
		 }else 
			 return new Expense(id,amount,currency,account,date,category); 
		 
	}

}
