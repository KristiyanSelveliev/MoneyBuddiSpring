package com.controller.manager;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.model.dao.BudgetDao;

@Component
public class BudgetManager {
	
	 @Autowired 
	 BudgetDao budgetDAO;
	
     private static final int  CHECK_TIME=1000*60*60*24; //24 HOURS
	
     @Scheduled(fixedRate=CHECK_TIME)
	 public void deleteExpiredBudgets() throws SQLException {
    	 budgetDAO.deleteExpiredBudgets();
     }

}
