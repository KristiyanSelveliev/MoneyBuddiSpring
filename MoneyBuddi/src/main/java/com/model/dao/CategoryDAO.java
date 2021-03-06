 package com.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exceptions.InvalidDataException;
import com.model.Category;
import com.model.User;
import com.model.Transaction.TransactionType;
@Component
public class CategoryDAO implements ICategoryDAO{
	
	@Autowired
	private TransactionTypeDAO transactionTypeDAO;
	
	@Autowired
	private DataSource db;
	
	
	@Override
	public Category getCategoryByID(long id) throws SQLException, InvalidDataException {
															
		try(PreparedStatement ps=db.getConnection().prepareStatement("SELECT id,category,transaction_type_id,user_id FROM categories "
														  			+ " WHERE id=?")){
			ps.setLong(1, id);
			try(ResultSet rs=ps.executeQuery()){
				if(rs.next()) {//if there is such a row
					return new Category(rs.getLong("id"),
							rs.getString("category"),								   
							transactionTypeDAO.getTypeById(rs.getInt("transaction_type_id")),
							rs.getLong("user_id")
							);
				}
			}
			
		}
		return null;
	}

	@Override
	public void addCategory(Category category) throws SQLException {
											
		try(PreparedStatement ps=db.getConnection().prepareStatement("INSERT INTO categories (category,transaction_type_id,user_id)"
															+"VALUES (?,?,?)");){
			
			ps.setString(1, category.getCategory());
			ps.setInt(2, transactionTypeDAO.getIdByTranscationType(category.getType()));
			ps.setLong(3, category.getUserId());
			
			//
			int rows=ps.executeUpdate();
			if(rows==0) {
				throw new SQLException("Srry category can`t be inserted ");
			}
		}
		
	}
	
	@Override
	public void updateCategory(Category category) throws SQLException {
		try(PreparedStatement ps=db.getConnection().prepareStatement(
				"UPDATE categories SET category=? "
		 	   +"WHERE id=? ")){
			
			ps.setString(1, category.getCategory());
			ps.setLong(2, category.getId());
			ps.executeUpdate();
		}
		
	}
	
	@Override
	public ArrayList<Category> getAllCategoriesByUser(User user) throws SQLException, InvalidDataException {
		ArrayList<Category> categories=new ArrayList<>();	
		try(PreparedStatement ps=db.getConnection().prepareStatement("SELECT id,category,transaction_type_id,user_id FROM categories "
				                                             +"WHERE user_id IS NULL or user_id=?" )){

			ps.setLong(1, user.getId());
			try(ResultSet rs=ps.executeQuery();){
				while(rs.next()) {
					
					categories.add(new Category(rs.getLong("id"),
												rs.getString("category"),
												transactionTypeDAO.getTypeById(rs.getInt("transaction_type_id")),
												rs.getLong("user_id")));
				}
			}
		}
		return categories;
	}

	@Override
	public ArrayList<Category> getAllCategoriesByUserAndType(User user, TransactionType type)
			throws SQLException, InvalidDataException {
		
		ArrayList<Category> categories=new ArrayList<>();	
		 int id=transactionTypeDAO.getIdByTranscationType(type);
		 try(PreparedStatement ps=db.getConnection().prepareStatement("SELECT id,category,transaction_type_id,user_id FROM categories "
                 +"WHERE (user_id IS NULL OR user_id=?) and "
                 + "transaction_type_id=? " )){
			 ps.setLong(1, user.getId());
			 ps.setInt(2, id);
		
			 try(ResultSet rs=ps.executeQuery();){
				 while(rs.next()) {
					
					 categories.add(new Category(rs.getLong("id"),
							 					 rs.getString("category"),
							 					 type,
							 					 rs.getLong("user_id")));
				 }
			 }
		 }
		 return categories;
		
	}

	@Override
	public void deleteCategory(long id) throws SQLException {
		
		try(PreparedStatement ps=db.getConnection().prepareStatement("DELETE FROM categories WHERE id=? and user_id IS NOT NULL")){
			ps.setLong(1, id);
			int rows=ps.executeUpdate();
			if(rows==0) {
				throw new SQLException("Srry category can't be deleted");
			}
		}
		
	}

}
