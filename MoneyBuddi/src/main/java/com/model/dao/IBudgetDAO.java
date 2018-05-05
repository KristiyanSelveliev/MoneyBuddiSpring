package com.model.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.exceptions.InvalidDataException;
import com.model.Budget;

public interface IBudgetDAO {
	
	void addBudget(Budget budget) throws SQLException;
	
	void updateBudget(Budget budget) throws SQLException;
	
	void deleteBudget(int id) throws SQLException;
	
	void deleteExpiredBudgets() throws SQLException;
	
	Budget getBudgetById(long id)throws Exception;
	
	Collection <Budget> getAllBudgetsForUser(long userId) throws Exception;
	
	Collection<Budget> getAllExpenseBudgetForUser(long userId) throws SQLException, InvalidDataException;
	
	Collection<Budget> getAllIncomeBudgetForUser(long userId) throws SQLException, InvalidDataException;

}
