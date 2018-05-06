package com.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exceptions.InvalidDataException;
import com.model.Currency;
import com.model.Currency.CurrencyType;

@Component
public class CurrencyDAO implements ICurrencyDAO{
	
	@Autowired
	private DataSource db;

	@Override
	public Currency getCurrencyById(long id) throws SQLException {
   
		try(PreparedStatement ps=db.getConnection().prepareStatement("SELECT id,type FROM currencies WHERE id=?")){
			ps.setLong(1, id);
			
			try(ResultSet rs=ps.executeQuery();){
				if(rs.next()) {//if there is such a row
					String currency_type=rs.getString("type");//String type from table currencies
					try {
						return new Currency(rs.getInt("id"),CurrencyType.valueOf(currency_type));
					}catch(Exception e) {
						throw new SQLException("Something went wrong with the currencies");
					}			
				}
			}
		}
		return null;
		
	}

	@Override
	public Currency getCurrencyByType(CurrencyType type) throws SQLException {
		try(PreparedStatement ps=db.getConnection().prepareStatement("SELECT id,type FROM currencies WHERE type=?")){
			ps.setString(1, type.toString());
			
			try(ResultSet rs=ps.executeQuery();){
				if(rs.next()) {
				  return new Currency(rs.getLong("id"),type);
						
				}
			}
		}
		return null;
		
	}

	@Override
	public Collection<Currency> getAllCurrencies() throws SQLException {
		Collection<Currency> currencies=new ArrayList<Currency>();
		try(PreparedStatement ps=db.getConnection().prepareStatement("SELECT id,type FROM currencies ")){
		
			
			try(ResultSet rs=ps.executeQuery();){
				while(rs.next()) {
				   currencies.add(this.getCurrencyById(rs.getLong("id")));
			}
		}
		
		
	}
		return currencies;
	}
	
	
	

}
