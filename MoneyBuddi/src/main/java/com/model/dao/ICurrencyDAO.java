package com.model.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.model.Currency;
import com.model.Currency.CurrencyType;


public interface ICurrencyDAO {
	
	Currency getCurrencyById(long id) throws SQLException;
	
	Currency getCurrencyByType(CurrencyType type) throws SQLException;
	
	Collection<Currency> getAllCurrencies() throws SQLException;
	

}
