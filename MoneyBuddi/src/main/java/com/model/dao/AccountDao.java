package com.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.model.Account;
import com.model.User;

@Component
public class AccountDao implements IAccountDao {
	@Autowired
    private CurrencyDAO currencyDAO;
	
	@Autowired
    private UserDao userDAO;
	
	@Autowired
	private DataSource db;
	
	
	
	@Override
	public void addAccount(Account account) throws SQLException {
		PreparedStatement s = null;
		try {
			s = db.getConnection().prepareStatement("INSERT INTO accounts (name, balance,"
					+ "user_id, currency_id)"
					+ "VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			s.setString(1, account.getName());
			s.setDouble(2, account.getBalance());
			s.setLong(3, account.getUser().getId());
			s.setLong(4, account.getCurrencyId());
			
			int rows = s.executeUpdate();
			if (rows == 0) {
				// if user is not inserted, throw exception
				throw new SQLException("Account was not inserted in DB.");
			}

			// retrieve user`s id
			ResultSet generatedKey = s.getGeneratedKeys();
			generatedKey.next();
			account.setId(generatedKey.getLong(1));

		} finally {
			s.close();
		}

		System.out.println("Account added in DB. ");
	}

	@Override
	public void updateAccount(Account account) throws SQLException {
		PreparedStatement ps=db.getConnection().prepareStatement("UPDATE accounts SET "
				+ "name=?, balance=?, user_id=?, currency_id=? "
				+ "WHERE id=?");
		ps.setString(1, account.getName());
		ps.setDouble(2, account.getBalance());
		ps.setLong(3, account.getUser().getId());
		ps.setLong(4, account.getCurrencyId());
		ps.setLong(5, account.getId());
		ps.executeUpdate();
	}

	@Override
	public void deleteAccount(int id) throws SQLException {
		String sql="DELETE FROM accounts WHERE id=?";
		PreparedStatement ps=db.getConnection().prepareStatement(sql);
		ps.setLong(1, id);
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public Account getAccountByName(String name, User u) throws Exception {
		Account account = null;
		PreparedStatement ps = null;
		try {
			ps = db.getConnection().prepareStatement("SELECT id,name,balance ,"
					+ "user_id, currency_id "
					+ "FROM accounts WHERE name=? AND user_id=?");
			ps.setString(1, name);
			ps.setLong(2, u.getId());
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
			account=new Account(rs.getLong("id"),//account id
								rs.getString("name"),//account name
								rs.getDouble("balance"),//account balance
								userDAO.getUserById(rs.getLong("user_id")),//user
								currencyDAO.getCurrencyById(rs.getLong("currency_id")));//Currency
			}else {
				throw new SQLException("No such account");
			}
			
			
			
		} finally {
			ps.close();
		}
		return account;
	}

	@Override
	public ArrayList<Account> getAllAccountsForUser(User u) throws Exception {
		ArrayList<Account> accounts=new ArrayList<>();
		PreparedStatement ps=null;
		try {
			ps=db.getConnection().prepareStatement("SELECT id, name, "
					+ "balance,user_id, currency_id FROM accounts WHERE user_id=?");
			ps.setLong(1, u.getId());
			
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				accounts.add(new Account(rs.getLong("id"),
						                 rs.getString("name"),
						                 rs.getDouble("balance"),
						                 userDAO.getUserById(rs.getLong("user_id")),
						                 currencyDAO.getCurrencyById(rs.getLong("currency_id"))));
			}
		}finally {
			ps.close();
		}
		
		return accounts;
	}

	@Override
	public Account getAccountById(long id) throws SQLException, com.exceptions.InvalidDataException {
		try(PreparedStatement ps=db.getConnection().prepareStatement("SELECT id,name,balance,user_id,currency_id FROM accounts WHERE id=?")){
			ps.setLong(1, id);
			try(ResultSet rs=ps.executeQuery()){
				if(rs.next()) {
					return new Account(rs.getLong("id"),
							           rs.getString("name"),
							           rs.getDouble("balance"),
							           userDAO.getUserById(rs.getLong("user_id")),
							           currencyDAO.getCurrencyById(rs.getLong("currency_id")));
				}
			}
		}
		return null;
	}

}
