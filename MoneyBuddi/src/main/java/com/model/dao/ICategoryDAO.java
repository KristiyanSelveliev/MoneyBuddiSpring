package com.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.exceptions.InvalidDataException;
import com.model.Category;
import com.model.User;
import com.model.Transaction.TransactionType;

public interface ICategoryDAO {
	
	
	void addCategory(Category category) throws SQLException;
	
	void deleteCategory(long id) throws SQLException; //User can only delete categories that he created and not the default ones
	
	void updateCategory(Category category) throws SQLException;
	
	Category getCategoryByID(long id) throws SQLException,InvalidDataException;
	
	ArrayList<Category> getAllCategoriesByUser(User user) throws SQLException,InvalidDataException;
	
	ArrayList<Category> getAllCategoriesByUserAndType(User user,TransactionType type) throws SQLException,InvalidDataException;
	
	
	

}
