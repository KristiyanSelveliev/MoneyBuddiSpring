package com.model.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.exceptions.InvalidDataException;
import com.model.User;
import com.util.security.BCrypt;

@Component
public class UserDao implements IUserDao {
	
	@Autowired
	DataSource db;
	

	@Override
	public synchronized void saveUser(User u) throws SQLException {
		PreparedStatement s=null;
		try {
			 s= db.getConnection().prepareStatement(
					"INSERT INTO users (username, " + "password, email, age)" 
			      + "VALUES (?,?,?,?)",
			      Statement.RETURN_GENERATED_KEYS);
			s.setString(1, u.getUsername());
			s.setString(2, u.getPassword());
			s.setString(3, u.getEmail());
			s.setInt(4, u.getAge());

			int rows = s.executeUpdate();
			if (rows == 0) {
				// if user is not inserted, throw exception
				throw new SQLException("User was not inserted in DB.");
			}

			// retrieve user`s id
			ResultSet generatedKey = s.getGeneratedKeys();
			generatedKey.next();
			u.setId(generatedKey.getLong(1));
		}finally {
			s.close();
		}

		System.out.println("Saved user in DB");
	}

	@Override
	public void deleteUser(User u) throws SQLException {
		PreparedStatement s = null;
		try {
			s = db.getConnection().prepareStatement("DELETE FROM users WHERE id=?");
			s.setLong(1, u.getId());
			s.executeUpdate();
		} catch (Exception e) {
			throw new SQLException("Could not delete user.");
		} finally {
			s.close();
		}

		System.out.println("Deleted user from DB. ");
	}

	@Override
	public void updateUser(User u) throws SQLException {
		PreparedStatement ps = null;
		LocalDate trDate=u.getLastTransactionDate();
	
		try {
			ps = db.getConnection().prepareStatement("UPDATE users SET username=?,"
					+ "password=?, email=?, age=?,last_transaction_date=? WHERE id=? ");
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getEmail());
			ps.setInt(4, u.getAge());
			ps.setDate(5, trDate==null?null:Date.valueOf(trDate));
			ps.setLong(6, u.getId());

			ps.executeUpdate();
		} catch (Exception e) {
			throw new SQLException("Could not update user.");
		} finally {
			ps.close();
		}

		System.out.println("User updated.");
	}

	@Override
	public User getUserById(long id) throws SQLException {
		User user = null;
		PreparedStatement ps = null;
		try {
			ps = db.getConnection().prepareStatement("SELECT username,password,"
					+ "email,age FROM users WHERE id=?");
			ps.setLong(1, id);
			ResultSet result = ps.executeQuery();
			result.next();
			user = new User(id, result.getString("username"), // get user`s username
					result.getString("password"), // get user`s password
					result.getString("email"), // get user`s email
					result.getInt("age"));// get user`s age
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("No user with id=" + id);
		} finally {
			ps.close();
		}
		return user;
	}

	@Override
	public User getUserByUsername(String username) throws SQLException {
		User user = null;
		PreparedStatement ps = null;
		try {
			ps = db.getConnection().prepareStatement("SELECT id,password,"
					+ "email,age FROM users WHERE username=?");
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			
			if(result.next()) {
			// create the user
			user = new User(result.getLong("id"),//get id
					username,
					result.getString("password"),//get password
					result.getString("email"),//get email
					result.getInt("age"));//get age
			}
		} catch (Exception e) {
			throw new SQLException("No user with username:" + username);
		} finally {
			ps.close();
		}
		return user;
	}

	@Override
	public boolean checkIfEmailExists(String email) throws SQLException {
		// checks if the email already exists in DB
		
		try(PreparedStatement ps =db.getConnection().prepareStatement("SELECT id FROM" + " users WHERE email=?");){
			ps.setString(1, email);
			ResultSet rs= ps.executeQuery();
			if(rs.next()) {
				return true;
			}
			return false;
		}
	}
	
	@Override
	public boolean validate(String username, String pass) throws SQLException {
		boolean status=false;
		PreparedStatement ps=null;
		try{  
			
			ps=db.getConnection().prepareStatement(  
			    "SELECT id FROM users WHERE username=? AND password=?");  
		
			ps.setString(1,username);  
			ps.setString(2, pass);  
			              
			ResultSet rs=ps.executeQuery();
		   
			status=rs.next();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception");
		}finally {
			ps.close();
		}
		  
		return status;
	}
	
	@Override
	public User getUserByUsernameAndPassword(String pass, String username) throws SQLException,
	InvalidDataException {
		String sql = "SELECT id, username, password, email, age FROM users WHERE username = ?";
				
		try(PreparedStatement ps = db.getConnection().prepareStatement(sql);){
			ps.setString(1, username);
			
			ResultSet result = ps.executeQuery();
			if(result.next()) {
				String hashed=result.getString("password");
				if(BCrypt.checkpw(pass, hashed)) {
				return new User(result.getLong("id"),
						result.getString("username"),
						result.getString("password"),
						result.getString("email"),
						result.getInt("age"));
					}
				}
			return null;
		}
	}

	@Override
	public ArrayList<User> getAllUsers() throws SQLException, InvalidDataException {
		ArrayList<User> users=new ArrayList<User>();
		try(PreparedStatement ps=db.getConnection().prepareStatement("SELECT id,username,password,email,age FROM users")){
			try(ResultSet rs=ps.executeQuery()){
				while(rs.next()) {
					users.add(new User(
							rs.getLong("id"),
							rs.getString("username"),
							rs.getString("password"),
							rs.getString("email"),
							rs.getInt("age")));
				}
			}
		}
		return users;
	}
	
	@Override
	public ArrayList<String> getAllEmailsToSendEmail() throws SQLException {
		ArrayList<String> emails=new ArrayList<String>();
		LocalDate yesterday=LocalDate.now().minusDays(1);
		//get all user`s emails that have been inactive in the last 24 hours to send them remindr via email
		try(PreparedStatement ps=db.getConnection().prepareStatement("SELECT email FROM users "
				+ "WHERE last_transaction_date < ? ;")){
			ps.setDate(1, Date.valueOf(yesterday));
			try(ResultSet rs=ps.executeQuery()){
				if(rs.next()) {
					emails.add(rs.getString("email"));
				}
			}
		}
		return emails;
	}
	
	@Override
	public void updateLastTransactionDateForUser(User u) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = db.getConnection().prepareStatement("UPDATE users SET "
					+ "last_transaction_date=? WHERE id=? ");
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			ps.setLong(2, u.getId());

			ps.executeUpdate();
		} catch (Exception e) {
			throw new SQLException("Could not update last transaction date.");
		} finally {
			ps.close();
		}

	}
}
