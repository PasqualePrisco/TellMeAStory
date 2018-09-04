package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import forgot.*;

import entity.Account;
import model.LoginModel;
import model.RegisterModel;
import util.BCrypt;
import util.GlobalConstants;
import util.MailUtil;
import util.Utils;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static boolean isDataSource = true;
	
	static RegisterModel reg = new RegisterModel();
	static LoginModel log = new LoginModel();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		String redirect="/register.jsp";
     
		try {
			if (action != null) {
				if(action.equalsIgnoreCase("register")){
					String nome= request.getParameter("nome");
					String cognome= request.getParameter("cognome");
					String email= request.getParameter("email");
					String password= request.getParameter("password");
					StatusPojo sp = new StatusPojo(); 
					String output = "";
					
					Account account = new Account(nome,cognome,email,password);
					
					// generate hash code for email verification
					String hash = Utils.prepareRandomString(30);
					
					// generate hash for password
					account.setEMAIL_VERIFICATION_HASH(BCrypt.hashpw(hash, GlobalConstants.SALT));

					    	
					    	// check whether email exists or not
					    	if(!log.isEmailExists(email)) {
					    		// create account if email not exists
					    		int id = reg.doSave(account);
					    		// send verification email
					    		String idSend= ""+id;
								MailUtil.sendEmailRegistrationLink(idSend, email, hash);
								sp.setCode(0);
								sp.setMessage("Registation Link Was Sent To Your Mail Successfully. Please Verify Your Email");
								output = Utils.toJson(sp);
					    	} else {
					    		// tell user that the email already in use
					    		request.setAttribute(GlobalConstants.MESSAGE, "Email già in uso clicca <a href='login.jsp'>qui</a> per effettuare il login");
								request.getRequestDispatcher("/messageToUser.jsp").forward(request, response);	
					    	}
					
					
					redirect="/login.jsp";
				}
		}
		}
		 catch (SQLException|MessagingException e) {
				System.out.println("Error:" + e.getMessage());
		 }
		
		
		response.sendRedirect(request.getContextPath()+redirect);
	}
}
