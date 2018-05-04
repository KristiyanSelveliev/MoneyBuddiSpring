package com.model;

public class TransactionDTO {
	
	
	public long id;
	public String category;
	public double amount;
	public double amount_converted;
	public String account;
	public String date;
	public String type;
	

	 public TransactionDTO(long id, String category, double amount, String account, String date) {
			this.id = id;
			this.category = category;
			this.amount = amount;
			this.account = account;
			this.date = date;	
		}
	
    public TransactionDTO(long id, String category, double amount, String account, String date,String type) {
		this(id,category,amount,account,date);
		this.type=type;
	}

	public TransactionDTO(long id, String category, double amount,double amount_converted, String account,String date) {
		this(id,category,amount,account,date);
		this.amount_converted=amount_converted;
	}
	public TransactionDTO(long id, String category, double amount,double amount_converted, String account, String date,String type) {
		this(id, category, amount,amount_converted, account, date);
		this.type=type;
		
	}


}
