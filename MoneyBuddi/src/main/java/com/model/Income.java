package com.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.sql.rowset.spi.SyncProvider;

import com.exceptions.InvalidDataException;

public class Income extends Transaction{

	//constructor with id
	public Income(long id, double amount, Currency currency, Account account, LocalDate date, Category category) throws InvalidDataException {
		super(id, amount, currency, account, date, category, TransactionType.INCOME);
		
	}
	
	//constructor without id
	public Income( double amount, Currency currency, Account account, LocalDate date, Category category) throws InvalidDataException {
		super(amount, currency, account, date, category, TransactionType.INCOME);
		
	}
	
	public Income(double amount, Category category)throws InvalidDataException {
		super(amount,category,TransactionType.INCOME);
	}

}
