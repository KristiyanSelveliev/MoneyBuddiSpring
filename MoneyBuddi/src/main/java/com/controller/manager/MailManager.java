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
import org.springframework.scheduling.annotation.Scheduled;
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

            System.out.println("Email send!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    //sending every minute now for the purpose of testing
    @Scheduled(fixedRate=60000)
	public  void sendEmailToAllInactiveUsers()  {
		try {
	        ArrayList<String> emails=userDAO.getAllEmailsToSendEmail();
	        
	        //iterate all users and send email to those, who have not made a transaction in the last day
	        for(String email : emails) {
	        	//sendMail(email,SUBJECT,MESSAGE);
	        }
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
