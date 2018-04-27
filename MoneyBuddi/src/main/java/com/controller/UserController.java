package com.controller;

import java.sql.SQLException;
import java.util.List;

import javax.activation.DataSource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.exceptions.InvalidDataException;
import com.model.Currency;
import com.model.User;
import com.model.dao.AccountDao;
import com.model.dao.BudgetDao;
import com.model.dao.CurrencyDAO;
import com.model.dao.UserDao;
import com.util.UserValidator;
import com.util.security.BCrypt;

@Component
@Controller
public class UserController {

	@Autowired
	private DriverManagerDataSource db;
	@Autowired
	private CurrencyDAO currencyDAO;
	@Autowired
	private UserDao userDAO;
	@Autowired
	private AccountDao accountDAO;
	@Autowired
	private BudgetDao budgetDAO;
	
	
	@RequestMapping(value = "/login2", method = RequestMethod.GET)
	public String login2() throws Exception {

		return "profile";
	}
	
	
	

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() throws Exception {

		return "login";
	}
	
	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model m) throws Exception {
		String username = request.getParameter("username");
		String pass = request.getParameter("password");

		User u = userDAO.getUserByUsernameAndPassword(pass, username);

		if (u != null) {

			request.getSession().setAttribute("user", u);
			request.getSession().setAttribute("logged", true);
			return "main";
		} else {
			throw new InvalidDataException("invalid username or password");
		}

	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPage() throws Exception {
		return "register";
	}

	

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request) throws Exception {
		String username = request.getParameter("username");
		String pass1 = request.getParameter("password1");
		String pass2 = request.getParameter("password2");
		String email = request.getParameter("email");
		int age = Integer.parseInt(request.getParameter("age"));
		// validate data
		if (UserValidator.validateRegisterParameters(username, pass1, pass2, email, age)) {// if parameters match
																							// certain criteria
			if (userDAO.getUserByUsername(username) == null) {// if username is free
				if (!userDAO.checkIfEmailExists(email)) {// if email is free

					pass1 = BCrypt.hashpw(pass1, BCrypt.gensalt());
					User u = new User(username, pass1, email, age);
					// add to db
					// synchronized
					userDAO.saveUser(u);
					request.getSession().setAttribute("user", u);
					request.getSession().setAttribute("logged", true);

					return "main";
				}
			}
		}
		return "main";
		
	}
	
}
