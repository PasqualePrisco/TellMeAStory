package forgot;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import model.LoginModel;
import util.GlobalConstants;
import util.Utils;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static LoginModel UserDAO = new LoginModel();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePassword() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Integer userId = (Integer) request.getSession().getAttribute(GlobalConstants.USER);
    	if(userId != null) {
    		request.getRequestDispatcher("/WEB-INF/changePassword.html").forward(request, response);	
    	} else {
    		String message = "Clic <a href=\"index.html\">here</a> to login";
    		request.setAttribute(GlobalConstants.MESSAGE, message);
			request.getRequestDispatcher("/messageToUser.jsp").forward(request, response);
    	}
    }
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inputCurrentPassword = null;
		if(request.getParameter("inputCurrentPassword")!=null) {
			inputCurrentPassword = request.getParameter("inputCurrentPassword");	
		};
		String inputPassword = null;
		if(request.getParameter("inputPassword")!=null) {
			inputPassword = request.getParameter("inputPassword");	
		};
		Integer userId = (Integer) request.getSession().getAttribute(GlobalConstants.USER);
		String isResetPasswordVerified = (String) request.getSession().getAttribute(GlobalConstants.IS_RESET_PASSWORD_VERIFIED);
		StatusPojo sp = new StatusPojo();
		
		try {
			if(userId!=null && inputCurrentPassword != null) {
				if(UserDAO.verifyUserIdAndPassword(userId.toString(), inputCurrentPassword)) {
					UserDAO.updatePassword(userId, inputPassword);
					sp.setCode(0);
					sp.setMessage("Password changed successfully");
				} else {
					sp.setCode(-1);
					sp.setMessage("Current password didn't match");
				}	
			} else if(userId!=null && isResetPasswordVerified != null) {
				UserDAO.updatePassword(userId, inputPassword);
				sp.setCode(0);
				sp.setMessage("Password changed successfully");
			} else {
				sp.setCode(-1);
				sp.setMessage("Invalid input");
			}
		} catch (SQLException e) {
			sp.setCode(-1);
			sp.setMessage(e.getMessage());
		}
		PrintWriter pw = response.getWriter();
	    pw.write(Utils.toJson(sp));
	    pw.flush();
	    pw.close();
	}

}
