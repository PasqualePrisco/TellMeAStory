package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Account;
import entity.Album;
import entity.Paziente;
import model.AlbumModel;
import model.LoginModel;
import model.PazienteModel;
import util.GlobalConstants;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Log")
public class Log extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static boolean isDataSource = true;
	
	static LoginModel log = new LoginModel();
	static AlbumModel modelAlbum= new AlbumModel();
	static PazienteModel modelPaziente= new PazienteModel();
       
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Log() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		String redirect="/login.jsp";
		Account account = (Account) request.getSession().getAttribute("account");
		
		if (action != null) {
			if(action.equalsIgnoreCase("login")){
				String email= request.getParameter("email");
				String password= request.getParameter("password");
				account=null;
				try {
					account= log.doLogin(email, password);
					if(account!=null){
						if(account.getEMAIL_VERIFICATION_HASH()==null) {
						if(account.getSTATUS().equals(GlobalConstants.ACTIVE) || account.getSTATUS().equals(GlobalConstants.IN_RESET_PASSWORD))
						{
					request.getSession().setAttribute("account",account);
					int userID= account.getId();
						ArrayList<Album> albums= new ArrayList<Album>();
						albums= modelAlbum.doGetAlbums(account.getId());
						request.getSession().setAttribute("albums", albums);
						ArrayList<Paziente> pazienti= new ArrayList<Paziente>();
						pazienti= modelPaziente.doGetPazienti(account.getId());
						request.getSession().setAttribute("pazienti", pazienti);
						}
						redirect="/index.jsp";
						response.sendRedirect(request.getContextPath()+redirect);
						}
						else {
							String message= "L'account è in attesa di verifica";
							request.setAttribute("invalid", message);
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
							dispatcher.forward(request, response);
						}
					} 
					else{
						String message= "Nessun account esistente. Controlla l'email o la password";
						request.setAttribute("invalid", message);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
						dispatcher.forward(request, response);
					}
						
				}catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
			else if (action.equalsIgnoreCase("logout")) {
				account=null;
				request.getSession().invalidate();
				redirect="/login.jsp";
				response.sendRedirect(request.getContextPath()+redirect);
			}
				
		
		}
		
	}

}
