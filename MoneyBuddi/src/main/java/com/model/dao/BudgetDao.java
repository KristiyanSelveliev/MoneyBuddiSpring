package com.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.exceptions.InvalidDataException;
import com.model.Budget;
import com.model.User;
@Component
public class BudgetDao implements IBudgetDAO{
	@Autowired
	private CurrencyDAO currencyDAO;
	
	@Autowired
    private UserDao userDAO;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private DriverManagerDataSource db;
	
	
	
	@Override
	public void addBudget(Budget budget) throws SQLException  {
		
		try(PreparedStatement ps=db.getConnection().prepareStatement("INSERT INTO budgets (amount,begin_date,end_date,user_id,currency_id,category_id)"
		+ "VALUES(?,?,?,?,?,?)");){
		ps.setDouble(1, budget.getAmount());
		ps.setDate(2, Date.valueOf(budget.getBeginDate()));
		ps.setDate(3, Date.valueOf(budget.getEndDate()));
		ps.setLong(4, budget.getUser().getId());
		ps.setLong(5, budget.getCurrency().getId());
		ps.setLong(6,budget.getCategory().getId());
		
		
		int rows=ps.executeUpdate();
		if(rows==0) {
			
			throw new SQLException("Cannot add budget in DB");
			}
		
		}	
	}

	@Override
	public void updateBudget(Budget budget) throws SQLException {
		try(PreparedStatement ps=db.getConnection().prepareStatement("UPDATE budgets SET amount=?,begin_date=?,end_date=?,user_id=?,currency_id=?,category_id=? "
	    +"WHERE id=?");){
		ps.setDouble(1, budget.getAmount());
		ps.setDate(2, Date.valueOf(budget.getBeginDate()));
		ps.setDate(3, Date.valueOf(budget.getEndDate()));
		ps.setLong(4, budget.getUser().getId());
		ps.setLong(5, budget.getCurrency().getId());
		ps.setLong(6, budget.getCategory().getId());
		ps.setLong(7, budget.getId());
		
		int rows=ps.executeUpdate();
		if(rows==0) {
			
			throw new SQLException("Cannot update budget in DB");
				}
		}
		
	}

	@Override
	public void deleteBudget(Budget budget) throws SQLException {
		
		try(PreparedStatement ps=db.getConnection().prepareStatement("DELETE FROM budgets WHERE id=?");){
		ps.setLong(1, budget.getId());
		
		int rows=ps.executeUpdate();
		if(rows==0) {
			throw new SQLException("Budget cannot be deleted from DB");
				}	
		
		}
	}

	@Override
	public Collection<Budget> getAllBudgetsForUser(User user) throws SQLException,InvalidDataException {//can throw SQL OR INVALID DATA EXCEPTION
		Collection<Budget> budgets=new ArrayList<>();
		try(PreparedStatement ps=db.getConnection().prepareStatement("SELECT id , amount , begin_date, "
				                                             +"end_date , user_id , currency_id , category_id "
				                                             + "FROM budgets WHERE user_id=?");){
			ps.setInt(1,(int) user.getId());
			try(ResultSet rs=ps.executeQuery()){
				while(rs.next()) {
					budgets.add(new Budget(rs.getLong("id"),
							               categoryDAO.getCategoryByID(rs.getLong("category_id")), 
							               rs.getDouble("amount"), 
							               userDAO.getUserById(rs.getLong("user_id")), 
							               currencyDAO.getCurrencyById(rs.getLong("currency_id")),
							               rs.getDate("begin_date").toLocalDate(), 
							               rs.getDate("end_date").toLocalDate()));
					
					                        
					
				}
			}
		}
		return budgets;
	}

	@Override
	public Budget getBudgetById(long id) throws Exception {
		Budget b=null;
		try(PreparedStatement ps=db.getConnection().prepareStatement("SELECT id , amount , begin_date,"
								                             + "end_date , user_id , currency_id , category_id " 
								                             + "FROM budgets WHERE id=?" )){
			ps.setLong(1, id);
			try(ResultSet rs=ps.executeQuery()){
				if(rs.next()) {
					b=new Budget(rs.getLong("id"),
				               categoryDAO.getCategoryByID(rs.getLong("category_id")), 
				               rs.getDouble("amount"), 
				               userDAO.getUserById(rs.getLong("user_id")), 
				               currencyDAO.getCurrencyById(rs.getLong("currency_id")),
				               rs.getDate("begin_date").toLocalDate(), 
				               rs.getDate("end_date").toLocalDate());
					return b;
				}
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	

}
