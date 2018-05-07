package com.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.model.Account;
import com.model.Transaction;
import com.model.User;
import com.model.dao.AccountDao;
import com.model.dao.CategoryDAO;
import com.model.dao.CurrencyDAO;
import com.model.dao.TransactionDao;


@Controller
public class PdfController {
	//SOURCE http://www.baeldung.com/java-pdf-creation
	
	private static final int LOW_USER_ACTIVITY=5;
	private static final int MEDIUM_USER_ACTIVITY=15;
	private static final int HIGH_USER_ACTIVITY=25;
	private Font defaultFont=FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.DARK_GRAY);
	
	  @Autowired
	  TransactionDao transactionDAO;
		  
	  @Autowired
	  AccountDao accountDAO;
	

	@RequestMapping(value="/generatePDF", method = RequestMethod.POST)
	  public void generate(HttpServletResponse response,HttpSession session) throws IOException {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document,response.getOutputStream());
			document.open();
			
			User user=(User) session.getAttribute("user");	
			Paragraph name=new Paragraph(user.getUsername(),defaultFont);
			document.add(name);
			
			Chunk chunk = new Chunk("Statistics for the month  "+LocalDate.now().minusDays(30).toString()+" --> "+LocalDate.now().toString(), defaultFont);
			document.add(new Paragraph(chunk));
			
			generateExpenseInfo(document,session);
			generateIncomeInfo(document,session);
			generateOverallInfo(document,session);
				
			document.close();
			
			response.getOutputStream().flush();
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		}

	

	private void generateIncomeInfo(Document document,HttpSession session) throws Exception {
		User user=(User) session.getAttribute("user");
		ArrayList<Transaction> incomes=transactionDAO.getIncomeByUserFromToDate(LocalDate.now().minusDays(30),LocalDate.now(),user.getId());
		
		Font fontIncome = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLUE);
		Paragraph incomesParagraph = new Paragraph("\nIncomes:\n", fontIncome);
		document.add(incomesParagraph);
		
		Paragraph incomeCount = new Paragraph("\nTotal of incomes: "+ incomes.size()+ "\n",defaultFont);
		document.add(incomeCount);
		Paragraph favCategory = new Paragraph("\nFavorite Income category: "+ getFavouriteCategory(incomes)+ "\n",defaultFont);
		document.add(favCategory);
		
		Paragraph bestAccount = new Paragraph("\nFavorite Income Account: "+getMostUsedAccount(incomes)+ "\n",defaultFont);
		document.add(bestAccount);
		
		
	}

	private void generateExpenseInfo(Document document,HttpSession session) throws Exception {
		User user=(User) session.getAttribute("user");
		ArrayList<Transaction> expenses=transactionDAO.getExpenseByUserFromToDate(LocalDate.now().minusDays(30),LocalDate.now(),user.getId());
		
		Font fontExpense = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.RED);
		Paragraph expensesParagraph = new Paragraph("\nExpenses:\n", fontExpense);
		document.add(expensesParagraph);
	
		Paragraph expenseCount = new Paragraph("\nTotal of expenses: "+ expenses.size()+ "\n",defaultFont);
		document.add(expenseCount);
		Paragraph favCategory = new Paragraph("\nFavorite Expense category: "+ getFavouriteCategory(expenses)+ "\n",defaultFont);
		document.add(favCategory);
		
		Paragraph bestAccount = new Paragraph("\nFavorite Expense Account: "+getMostUsedAccount(expenses)+ "\n",defaultFont);
		document.add(bestAccount);
		
	
	}
	
    private void generateOverallInfo(Document document, HttpSession session) throws Exception {
    	User u=(User) session.getAttribute("user");
    	int expensesCount=transactionDAO.getExpenseByUserFromToDate(LocalDate.now().minusDays(30),LocalDate.now(),u.getId()).size();
    	int incomesCount=transactionDAO.getIncomeByUserFromToDate(LocalDate.now().minusDays(30),LocalDate.now(),u.getId()).size();
    	int activity=expensesCount+incomesCount;
    	
    	Font fontOverall = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.MAGENTA);
		Paragraph overallParagraph = new Paragraph("\nOverall :\n", fontOverall);
		document.add(overallParagraph);
    	
    	Chunk overall=new Chunk("Your activity in MoneyBuddi : ",defaultFont);
    	document.add(overall);
    	if(activity<=LOW_USER_ACTIVITY) {

			Font lowActivityFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.RED);
	    	Chunk lowActivity = new Chunk("LOW",lowActivityFont);
	    	document.add(lowActivity);
	    	
    	}else if(activity>LOW_USER_ACTIVITY && activity<HIGH_USER_ACTIVITY) {
    		
    		Font mediumActivityFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.ORANGE);
       	    Chunk mediumActivity = new Chunk("MEDIUM",mediumActivityFont);
       	    document.add(mediumActivity);
       	    
    	}else {
    		
    		Font highActivityFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.GREEN);
       	    Chunk highActivity = new Chunk("HIGH",highActivityFont);
       	    document.add(highActivity);
    	}
		
	}
	
	
	private String getFavouriteCategory(ArrayList<Transaction> transactions ) {
		if(transactions.size()==0) {
			return "NONE";
		}
		int counter=0;
		int maxCounter=0;
		String bestCategory=transactions.get(0).getCategory().getCategory();
		
		for(int i=0;i<transactions.size()-1;i++) {
			for(int j=i+1;j<transactions.size();j++) {
				if(transactions.get(i).getCategory().equals(transactions.get(j).getCategory())) {
					counter++;
				}
			}
			if(counter>maxCounter) {
				bestCategory=transactions.get(i).getCategory().getCategory();
				maxCounter=counter;
				
			}
			counter=0;
		}
		return bestCategory;
	}
	
	
	private String getMostUsedAccount(ArrayList<Transaction> transactions) {
		if(transactions.size()==0) {
			return "NONE";
		}
		int counter=0;
		int maxCounter=0;
		Account bestAccount=transactions.get(0).getAccount();
		
		for(int i=0;i<transactions.size()-1;i++) {
			for(int j=i+1;j<transactions.size();j++) {
				if(transactions.get(i).getAccount().equals(transactions.get(j).getAccount())) {
					counter++;
				}
			}
			if(counter>maxCounter) {
				bestAccount=transactions.get(i).getAccount();
				maxCounter=counter;
			}
			counter=0;
		}
		return bestAccount.getName();	
	}
	
}
