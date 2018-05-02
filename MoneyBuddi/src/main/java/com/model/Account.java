package com.model;

import com.exceptions.InvalidDataException;

public class Account {
	
	private long id;
	private String name;
	private double balance;
	private User user;
	private Currency currency;

	private Account(long id, String name, double balance) throws InvalidDataException {
		this.setId(id);
		this.setName(name);
		this.setBalance(balance);
	}
	public Account(long id, String name, double balance, User user, 
			Currency currency) throws  InvalidDataException {
		this(id, name, balance);
		this.setUser(user);
		this.setCurrency(currency);
	}
	
	public Account(long id, String name, double balance,long user_id, int currency_id)
			throws  InvalidDataException {
		this(id, name, balance);
		this.user.setId(user_id);
		this.currency.setId(currency_id);
	}
	
	public Account(String name, double balance,User user, Currency currency)
			throws  InvalidDataException {
		this.setName(name);
		this.setBalance(balance);
		this.setUser(user);
		this.setCurrency(currency);

	}
	
	//====getters
	public double getBalance() {
		return balance;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public User getUser() {
		return user;
	}
	public long getCurrencyId() {
		return currency.getId();
	}
	
	public Currency getCurrency() {
		return currency;
	}

	//====setters
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setUser(User user) throws InvalidDataException {
		if(user == null) {
			throw new InvalidDataException("User cannot be null.");
		}
		this.user = user;
	}
	public void setName(String name) throws InvalidDataException {
		if(name==null || name.isEmpty()) {
			throw new InvalidDataException("The name of the transaction cannot be null or empty.");
		}
		this.name = name;
	}
	
	public void setCurrencyId(long id) {
		this.currency.setId(id);;
	}
	
	public void setCurrency(Currency currency) throws InvalidDataException {
		if(currency==null) {
			throw new InvalidDataException("Currency cannot be null");
		}
		
		this.currency=currency;
	}
}
