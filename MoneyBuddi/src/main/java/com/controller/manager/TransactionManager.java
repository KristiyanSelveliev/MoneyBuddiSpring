package com.controller.manager;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exceptions.InvalidDataException;
import com.model.Account;
import com.model.Budget;
import com.model.Expense;
import com.model.Transaction;
import com.model.Transaction.TransactionType;
import com.model.dao.TransactionDao;

@Component
public class TransactionManager {
	@Autowired
	private TransactionDao transactionDAO;
	
	private static TransactionManager instance;
	
	public synchronized static TransactionManager getInstance() {
		
		if(instance==null) {
			instance=new TransactionManager();
		}
		return instance;
			
	}
	
	
	private TransactionManager() {}
	
	
	public void addTransaction(Transaction transaction,Budget budget) throws InvalidDataException, SQLException {
	
		
		double  accountAmount=CurrencyConverter.convert(
					transaction.getAmount(),
					transaction.getCurrency(), 
					transaction.getAccount().getCurrency()
					);
		
		double budgetAmount=0;
		 if(budget!=null) {
		 budgetAmount=CurrencyConverter.convert(
					transaction.getAmount(),
					transaction.getCurrency(), 
					budget.getCurrency()
					);
		 }
		 
		Account acc = transaction.getAccount();
		if (transaction.getType().equals(TransactionType.EXPENSE)) {
			
			acc.setBalance(acc.getBalance() - accountAmount);
			
			if(budget!=null) { 
				
				budget.setAmount(budget.getAmount()-budgetAmount);
			}
		} else if (transaction.getType().equals(TransactionType.INCOME)) {
			
				acc.setBalance(acc.getBalance() + accountAmount);
				
			if(budget!=null) {
			     
					 budget.setAmount(budget.getAmount()+budgetAmount);
				}
			
		  }
		  
			transactionDAO.addTransaction(transaction, budget);
			
			
		
	}
	
	

}
