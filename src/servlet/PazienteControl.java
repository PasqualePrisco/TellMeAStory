package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Account;
import entity.Album;
import entity.Paziente;
import entity.Vignetta;
import model.AlbumModel;
import model.PazienteModel;

/**
 * Servlet implementation class PazienteControl
 */
@WebServlet("/PazienteControl")
public class PazienteControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static PazienteModel model = new PazienteModel();
	static AlbumModel model2= new AlbumModel();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PazienteControl() {
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
		String redirect="/pazienti.jsp";
		
		Account account = (Account) request.getSession().getAttribute("account");
		ArrayList<Album> albums = (ArrayList<Album>) request.getSession().getAttribute("albums");
		
		if (action != null) {
			
			// Album action
				
				if (action.equalsIgnoreCase("addPaziente") && account != null) {
					// Get parameter
					String nome = request.getParameter("nome");
					String cognome = request.getParameter("cognome");
					String email = request.getParameter("email");
					String password = request.getParameter("password");
					
					Paziente p = new Paziente();
					p.setNome(nome);
					p.setCognome(cognome);
					p.setEmail(email);
					p.setPassword(password);
					
					ArrayList<Paziente> pazienti;
					
					if(request.getSession().getAttribute("pazienti") != null) {
					pazienti= (ArrayList<Paziente>) request.getSession().getAttribute("pazienti"); 
					}
					else {
					pazienti = new ArrayList<Paziente>();
					}
					

					try {
						model.doSave(p, account.getId());
						pazienti=model.doGetPazienti(account.getId());
						request.getSession().removeAttribute("pazienti");
						request.getSession().setAttribute("pazienti", pazienti);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					response.sendRedirect(request.getContextPath()+redirect);
				}
				
				if (action.equalsIgnoreCase("deletePaziente") && account != null) {
					// Get parameter
					int id = Integer.parseInt(request.getParameter("id"));
					
					try {
						model.doDelete(id);
						ArrayList<Paziente> pazienti= model.doGetPazienti(account.getId());
						request.getSession().removeAttribute("pazienti");
						request.getSession().setAttribute("pazienti", pazienti);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					response.sendRedirect(request.getContextPath()+redirect);
				}
				
				if (action.equalsIgnoreCase("getPaziente") && account != null) {
					// Get parameter
					int id = Integer.parseInt(request.getParameter("id"));
					try {
						ArrayList<Album> associatedAlbum= model.doGetAssociatedAlbumById(id);
						request.removeAttribute("associatedAlbum");
						request.setAttribute("associatedAlbum", associatedAlbum);
						Paziente p=model.doGetPaziente(id);
						request.removeAttribute("paziente");
						request.setAttribute("paziente", p);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/paziente.jsp");
						dispatcher.forward(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				if (action.equalsIgnoreCase("doAssociate") && account != null) {
					// Get parameter
					int idA = Integer.parseInt(request.getParameter("id"));
					int idP = Integer.parseInt(request.getParameter("idP"));
					try {
						model.doAssociate(idP, idA);
						ArrayList<Album> associatedAlbum= model.doGetAssociatedAlbumById(idP);
						request.removeAttribute("associatedAlbum");
						request.setAttribute("associatedAlbum", associatedAlbum);
						Paziente p=model.doGetPaziente(idP);
						request.removeAttribute("paziente");
						request.setAttribute("paziente", p);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/paziente.jsp");
						dispatcher.forward(request, response);
					}catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (action.equalsIgnoreCase("deleteA") && account != null) {
					// Get parameter
					int idA = Integer.parseInt(request.getParameter("id"));
					int idP = Integer.parseInt(request.getParameter("idPaziente"));
					try {
						model.doDeleteAssociation(idA,idP);
						ArrayList<Album> associatedAlbum= model.doGetAssociatedAlbumById(idP);
						request.removeAttribute("associatedAlbum");
						request.setAttribute("associatedAlbum", associatedAlbum);
						Paziente p=model.doGetPaziente(idP);
						request.removeAttribute("paziente");
						request.setAttribute("paziente", p);
						
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/paziente.jsp");
						dispatcher.forward(request, response);
					
					}catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
		}
	}
	
	

}
