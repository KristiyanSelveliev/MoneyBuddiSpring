package com.model.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import com.exceptions.InvalidDataException;
import com.model.Budget;
import com.model.Transaction;
import com.model.User;

public interface ITransactionDao {
                                                         
	void addTransaction(Transaction transaction,Budget budget) throws SQLException;//budget is optional
	void deleteTransaction(Transaction transaction) throws SQLException;
	void changeTransaction(Transaction transaction) throws SQLException;
	Transaction getTransactionById(long transactionId) throws SQLException, InvalidDataException;
	Collection<Transaction> getAllTransactionsByUser(User u) throws SQLException,InvalidDataException;
	ArrayList<Transaction> getAllIncomeTransactions(User u) throws Exception;
	ArrayList<Transaction> getAllExpenseTransactions(User u) throws Exception;
	ArrayList<Transaction> getAllTransactionsByUserAndDate(User u, LocalDate date)throws SQLException, InvalidDataException;
	
	ArrayList<Transaction> getIncomeByAccountFromToDate(LocalDate from, LocalDate to,
			long accountId) throws Exception;
	ArrayList<Transaction> getExpenseByAccountFromToDate(LocalDate from, LocalDate to,
			long accountId) throws Exception;
	
	ArrayList<Transaction> getIncomeByUserFromToDate(LocalDate from, LocalDate to,
			long userId) throws Exception;
	
	ArrayList<Transaction> getExpenseByUserFromToDate(LocalDate from, LocalDate to,
			long userId) throws Exception;
	
	
}
