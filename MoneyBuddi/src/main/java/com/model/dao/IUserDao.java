package com.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.exceptions.InvalidDataException;
import com.model.User;

public interface IUserDao {
	
    ArrayList<User> getAllUsers() throws SQLException, InvalidDataException;
	void saveUser(User u) throws SQLException;
	void deleteUser(User u) throws SQLException;
	void updateUser(User u) throws SQLException;
	User getUserById(long id) throws SQLException;
	User getUserByUsername(String username) throws SQLException;
	boolean checkIfEmailExists(String email) throws SQLException;
	boolean validate(String email, String pass) throws SQLException;
	User getUserByUsernameAndPassword(String pass, String username) throws Exception;
	ArrayList<String> getAllEmailsToSendEmail() throws SQLException;
	void updateLastTransactionDateForUser(User u) throws SQLException;
}
