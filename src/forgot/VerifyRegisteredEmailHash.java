package forgot;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Account;
import model.LoginModel;
import util.BCrypt;
import util.GlobalConstants;



/**
 * Servlet implementation class VerifyRegisteredEmailHash
 */
@WebServlet("/VerifyRegisteredEmailHash")
public class VerifyRegisteredEmailHash extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static LoginModel UserDAO= new LoginModel();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyRegisteredEmailHash() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get user Id and email verification code Hash code  
		Integer userId =(Integer) Integer.parseInt(request.getParameter("userId"));
		String hash = BCrypt.hashpw(request.getParameter("hash"), GlobalConstants.SALT);
		String scope = request.getParameter("scope");
		String message = null;
		try {
			// verify with database
			if(UserDAO.verifyEmailHash(userId.toString(), hash) && scope.equals(GlobalConstants.ACTIVATION)) {
			   //update status as active
			   UserDAO.updateStaus(userId, "active");
			   UserDAO.updateEmailVerificationHash(userId.toString(), null);
			   message = "Email verified successfully. Account was activated. Click <a href=\"login.jsp\">here</a> to login";
			} else if(UserDAO.verifyEmailHash(userId.toString(), hash) && scope.equals(GlobalConstants.RESET_PASSWORD)) {
			   //update status as active
			   UserDAO.updateStaus(userId, "active");
			   UserDAO.updateEmailVerificationHash(userId.toString(), null);
			   //put some session for user
			   request.getSession().setAttribute(GlobalConstants.USER, userId);
			   request.getSession().setAttribute(GlobalConstants.IS_RESET_PASSWORD_VERIFIED, GlobalConstants.YES);
			   request.getRequestDispatcher("/resetPassword.html").forward(request, response);	
			} 
		} catch (SQLException e) {

			message = e.getMessage();
		} 
		if(message!=null) {
			request.setAttribute(GlobalConstants.MESSAGE, message);
			request.getRequestDispatcher("/messageToUser.jsp").forward(request, response);	
		} 
	}

}
