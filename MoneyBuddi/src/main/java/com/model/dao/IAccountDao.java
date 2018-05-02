package com.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.exceptions.InvalidDataException;
import com.model.Account;
import com.model.User;

public interface IAccountDao {

	void addAccount(Account account) throws SQLException;
	
	void updateAccount(Account account) throws SQLException;
	
	void deleteAccount(int id) throws SQLException;
	
	Account getAccountById(long id) throws SQLException,InvalidDataException;
	
	Account getAccountByName(String name, User u) throws SQLException,InvalidDataException;
	
	List<Account> getAllAccountsForUser(User u) throws Exception;
}
