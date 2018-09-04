package servlet;

import java.io.File;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import entity.Account;
import entity.Album;
import entity.Vignetta;
import model.AlbumModel;

/**
 * Servlet implementation class AddAlbum
 */
@WebServlet("/AddAlbum")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB

public class AddAlbum extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static AlbumModel model = new AlbumModel();
	private static final String SAVE_DIR = "images";
	private static final String SAVE_DIR_AUD = "audio";

	public AddAlbum() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		String redirect = "/index.jsp";


		// gets absolute path of the web application
		String savePath = "C:\\xampp\\htdocs"
				+ "/" + SAVE_DIR;
		
		String savePathAudio = "C:\\xampp\\htdocs"
				+ "/" + SAVE_DIR_AUD;
		
		String modifyPath = "C:\\xampp\\htdocs";
		String modifyPathAudio = "C:\\xampp\\htdocs\\audio";

		Account account = (Account) request.getSession().getAttribute("account");
		
		
		try {
		if (action != null) {
			
		// Album action
			
			if (action.equalsIgnoreCase("add") && account != null) {
				// Get parameter
				String name = request.getParameter("nome");
				int tipo= Integer.parseInt(request.getParameter("type"));
				
				// creates the save directory if it does not exists
				File fileSaveDir = new File(savePath);
				if (!fileSaveDir.exists()) {
					fileSaveDir.mkdir();
				}

				Part part = request.getPart("file");
				String fileName =tipo+name+extractFileName(part);
				// refines the fileName in case it is an absolute path
				part.write(savePath + File.separator + fileName);

				String url = "http://localhost/images" + "/" + fileName;


				Album album = new Album();
				album.setNome(name);
				album.setPath(url);
				album.setTipo(tipo);
				
				System.out.println(url);
				ArrayList<Album> albums = new ArrayList<Album>();;
				

				try {
					model.doSave(album, account.getId());
					albums=model.doGetAlbums(account.getId());
					request.getSession().removeAttribute("albums");
					request.getSession().setAttribute("albums", albums);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(album.getTipo()==1) {
					redirect="/emozioni.jsp";
				}
				if(album.getTipo()==2) {
					redirect="/sequenze.jsp";
				}
			}

			if(action.equalsIgnoreCase("getAlbum")){
				int id = Integer.parseInt(request.getParameter("param"));
				Album album= model.doFindById(id);
				album.setVignette(model.doGetVignette(id));
				request.getSession().removeAttribute("album");
				request.getSession().setAttribute("album", album);
				redirect="/seeVignette.jsp";
			}
			
			
			if(action.equalsIgnoreCase("modifyAlbum")) {
				int id = Integer.parseInt(request.getParameter("idAlbum"));
				String nome = request.getParameter("nome");
				Album album= model.doFindById(id);
				File fileSaveDir = new File(savePath);
				if (!fileSaveDir.exists()) {
					fileSaveDir.mkdir();
				}
				Part part = request.getPart("file");
				String fileName = extractFileName(part);
				part.write(savePath + File.separator + fileName);
				String url = "images" + "/" + fileName;
				File oldFile= new File(modifyPath + File.separator + album.getPath());
				oldFile.delete();
				album.setNome(nome);
				album.setPath(url);
				model.updateAlbum(album);
				ArrayList<Album> albs= new ArrayList<Album>();
				albs=model.doGetAlbums(account.getId());
				request.getSession().removeAttribute("albums");
				request.getSession().setAttribute("albums", albs);
				redirect="/index.jsp";
			}
			
			if(action.equalsIgnoreCase("deleteA")) {
				int id= Integer.parseInt(request.getParameter("idA"));
				Album album=model.doFindById(id);
				ArrayList<Vignetta> v= model.doGetVignette(id);
				
				File oldFile= new File(modifyPath + File.separator + album.getPath());
				oldFile.delete();
				
				if(v!=null) {
				for(int i=0;i<v.size();i++) {
					oldFile= new File(modifyPath + File.separator + v.get(i).getPath());
					oldFile.delete();
				}
				}
				
				model.removeAlbum(id);
				
				ArrayList<Album> albums= model.doGetAlbums(account.getId());
				request.getSession().removeAttribute("albums");
				request.getSession().setAttribute("albums", albums);
				
				if(album.getTipo()==1) {
					redirect="/emozioni.jsp";
				}
				if(album.getTipo()==2) {
					redirect="/sequenze.jsp";
				}
			}
			
			// Vignette action
			
			if(action.equalsIgnoreCase("addV")){
				Album album=(Album) request.getSession().getAttribute("album");
				// creates the save directory if it does not exists
				File fileSaveDir = new File(savePath);
				if (!fileSaveDir.exists()) {
					fileSaveDir.mkdir();
				}

				Part part = request.getPart("file");
				String fileName = account.getId()+album.getId()+album.getNome()+extractFileName(part);
				// refines the fileName in case it is an absolute path
				part.write(savePath + File.separator + fileName);

				String url = "http://localhost/images" + "/" + fileName;

				Vignetta v= new Vignetta();
				v.setPath(url);

				
				int num=0;
				if(album.getVignette() != null) {
					num=album.getVignette().size()+1;
					}
					else {
					num=1;
					}
				
				v.setNumero(num);
				

				try {
					model.doSaveV(v, album.getId());
					album.setVignette(model.doGetVignette(album.getId()));
					request.getSession().removeAttribute("album");
					request.getSession().setAttribute("album", album);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				redirect="/seeVignette.jsp";
			}
			
			if(action.equalsIgnoreCase("changeOrder")){
				int i=1;
				int temp=0;
				Album album=(Album) request.getSession().getAttribute("album");
				// get JSON String
					String s= request.getParameter("prova");
				// convert JSON String in Array String java
					String[] arr = Arrays.stream(s.substring(1, s.length()-1).split(","))
			                .map(e -> e.replaceAll("\"", ""))
			                .toArray(String[]::new);
			
				for(i=1;i<=album.getVignette().size();i++) {
					int numero=Integer.parseInt(arr[temp]);
					album.getVignette().get(numero-1).setNumero(i);
					model.updateVignetta(album.getVignette().get(numero-1));
					temp++;
				}
				
				album.setVignette(model.doGetVignette(album.getId()));
				
				request.getSession().removeAttribute("album");
				request.getSession().setAttribute("album", album);
				redirect="/seeVignette.jsp";
			}
			
			
			// ########## Rimuoviamo Vignetta ###########
			if(action.equalsIgnoreCase("deleteV")) {
				int id= Integer.parseInt(request.getParameter("idV"));
				Album album=(Album) request.getSession().getAttribute("album");
				model.removeVignetta(id);
				Vignetta v=new Vignetta();
				for(int i=0;i<album.getVignette().size();i++) {
					if(album.getVignette().get(i).getId()==id) {
						 v= album.getVignette().get(i);
					}
				}
				
				for(int i=0;i<album.getVignette().size();i++) {
					if(v.getNumero()<album.getVignette().get(i).getNumero()) {
						Vignetta x= album.getVignette().get(i);
						x.setNumero(x.getNumero() - 1);
						model.updateVignetta(x);
					}
				}
				
				album.setVignette(model.doGetVignette(album.getId()));
				
				File oldFile= new File(v.getPath());
				oldFile.delete();
				File oldFileAudio= new File(v.getAudio());
				oldFileAudio.delete();
				
				request.getSession().removeAttribute("album");
				request.getSession().setAttribute("album", album);
				redirect="/seeVignette.jsp";
			}
			
			
			// ########## Aggiungiamo Audio ###########
			if(action.equalsIgnoreCase("addAudio")){
				int id= Integer.parseInt(request.getParameter("id"));
				File fileSaveDir = new File(savePathAudio);
				if (!fileSaveDir.exists()) {
					fileSaveDir.mkdir();
				}
				Part part = request.getPart("audio_data");
				String fileName = account.getId()+""+id+".wav";
				System.out.println(fileName);
				part.write(savePathAudio + File.separator + fileName);
				String url = "http://localhost/audio" + "/" + fileName;
				try {
					model.doSaveAudio(url, id);
					Album album=(Album) request.getSession().getAttribute("album");
					album.setVignette(model.doGetVignette(album.getId()));
					request.getSession().removeAttribute("album");
					request.getSession().setAttribute("album", album);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
			// ########## Rimuoviamo Audio ###########
			if(action.equalsIgnoreCase("removeAudio")){
				int id= Integer.parseInt(request.getParameter("id"));
				Album album=(Album) request.getSession().getAttribute("album");
			
				Vignetta v=new Vignetta();
				for(int i=0;i<album.getVignette().size();i++) {
					if(album.getVignette().get(i).getId()==id) {
						 v= album.getVignette().get(i);
					}
				}
				
				File oldFile= new File(modifyPath+File.separator+account.getId()+id+".wav" );
				System.out.println(modifyPath+File.separator+account.getId()+id+".wav");
				oldFile.delete();
				
				try {
					model.doDeleteAudio(id);
					album.setVignette(model.doGetVignette(album.getId()));
					request.getSession().removeAttribute("album");
					request.getSession().setAttribute("album", album);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath()+redirect);
	}

	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

}
