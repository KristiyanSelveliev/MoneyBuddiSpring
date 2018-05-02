package com.controller.manager;


import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.model.User;
import com.model.dao.UserDao;


@Component
public class MailManager {
	
	@Autowired
	private  UserDao userDAO;
	
	//EMAIL
	private static final String SUBJECT="Transactions!";
	private static final String MESSAGE="Reminder!!You have been inactive with your transactions  !! ";
	
	private static final String EMAIL_USERNAME="MoneyBuddii1@gmail.com";
    private static final String PASSWORD="ittstudent-123";
    
    
 
    
    public static void sendMail(String receiver,String subject,String msg) {
    	Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                  protected PasswordAuthentication getPasswordAuthentication() {
                      return new PasswordAuthentication(EMAIL_USERNAME, PASSWORD);
                  }
                });
        try {

            Message message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(EMAIL_USERNAME));//Sender
            
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(receiver));//Receiver 
            
            message.setSubject(subject);//Subject 
            
            message.setText(msg);//The actual message

            Transport.send(message);//the actual sending

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }




	public  void sendEmailToAllInactiveUsers()  {
		try {
        ArrayList<User> users=userDAO.getAllUsers();
        //TODO
        //need to add new field in db 
        //Get the time when the user made  his last transaction 
        //if it is done more than a certain amount 
        //an email must be send to the user
        
        for(User u:users) {
        	sendMail(u.getEmail(),SUBJECT,MESSAGE);
        }
        
        
        
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

	

}
