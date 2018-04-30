package com.model.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.model.Budget;
import com.model.User;

public interface IBudgetDAO {
	
	void addBudget(Budget budget) throws SQLException;
	
	void updateBudget(Budget budget) throws SQLException;
	
	void deleteBudget(int id) throws SQLException;
	
	Budget getBudgetById(long id)throws Exception;
	
	Collection <Budget> getAllBudgetsForUser(User user) throws Exception;

}
