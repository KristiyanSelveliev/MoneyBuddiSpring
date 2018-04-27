package com.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.controller.manager.DBManager;
import com.model.Currency;
import com.model.Currency.CurrencyType;

@Component
public class CurrencyDAO implements ICurrencyDAO{
	
	@Autowired
	private DriverManagerDataSource db;

	@Override
	public Currency getCurrencyById(long id) throws SQLException {
   
		try(PreparedStatement ps=db.getConnection().prepareStatement("SELECT id,type FROM currencies WHERE id=?")){
			ps.setLong(1, id);
			
			try(ResultSet rs=ps.executeQuery();){
				if(rs.next()) {//if there is such a row
					String currency_type=rs.getString("type");//String type from table currencies
					for(CurrencyType type:Currency.CurrencyType.values()) {
						if(type.toString().equals(currency_type)) {
											//checking if there is an enum which 
						                    //string value equals the type from the table in DB
						                    //because type in table is String whereas in class is enum
					   
						return new Currency(rs.getInt("id"), type);
						}
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
				if(rs.next()) {//if there is such a row

				  return new Currency(rs.getLong("id"),type);
						
				}
			}
		}
		return null;
		
	}

	@Override
	public Collection<Currency> getAllCurrencies() throws SQLException {
		Collection<Currency> currencies=new ArrayList();
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
