package com.model;

import com.exceptions.InvalidDataException;
import com.model.Transaction.TransactionType;

public class Category {
	
	private long id;
	private String  category;
	private TransactionType type;
	private long userId;//not obligatory
	
	
	public Category(long id, String category, TransactionType type) throws InvalidDataException {
	    this(category,type);
		this.setId(id);
		
	}
	
	public Category(String category, TransactionType type) throws InvalidDataException {
		this.setCategory(category);
		this.setType(type);
	}
	
	public Category(String category, TransactionType type,long user_id) throws InvalidDataException {
		this(category,type);
		this.setUserId(user_id);
	}
	
	public Category(long id,String category, TransactionType type,long user_id) throws InvalidDataException {
		this(id,category,type);
		this.setUserId(user_id);
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public TransactionType getType() {
		return type;
	}
	
	public void setType(TransactionType type) throws InvalidDataException {
		if(type==null) {
			throw new InvalidDataException("Type cant be null");
		}
		this.type = type;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		return true;
	}
	
}
