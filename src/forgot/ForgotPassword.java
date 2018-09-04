package forgot;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Account;
import model.LoginModel;
import util.BCrypt;
import util.GlobalConstants;
import util.MailUtil;
import util.Utils;

/**
 * Servlet implementation class ForgotPassword
 */
@WebServlet("/ForgotPassword")
public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static LoginModel UserDAO= new LoginModel();
	
    public ForgotPassword() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inputEmail = request.getParameter("inputEmail");
		StatusPojo sp = new StatusPojo(); 
		try {
			Account up = UserDAO.getAccountByEmail(inputEmail); 
			if(up!=null) {
				System.out.println("entro");
				String hash = Utils.prepareRandomString(30);
				UserDAO.updateEmailVerificationHashForResetPassword(inputEmail, BCrypt.hashpw(hash,GlobalConstants.SALT));
				MailUtil.sendResetPasswordLink(up.getId()+"",inputEmail, hash);
				request.setAttribute(GlobalConstants.MESSAGE, "Ti abbiamo inviato il link per resettare la password, controlla l'email.");
				request.getRequestDispatcher("/messageToUser.jsp").forward(request, response);
			} else {
				request.setAttribute(GlobalConstants.MESSAGE, "L'email inserita non è registrata. clicca <a href='register.jsp'>qui</a> per effettuare la registrazione");
				request.getRequestDispatcher("/messageToUser.jsp").forward(request, response);
			}
		} catch (SQLException | MessagingException e) {
			sp.setCode(-1);
			sp.setMessage(e.getMessage());
		}
		PrintWriter pw = response.getWriter();
	    pw.write(Utils.toJson(sp));
	    pw.flush();
	    pw.close();
	}

}
