package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import entity.Album;
import entity.Paziente;

public class PazienteModel {
		
		private static DataSource ds;

		static {
			try {
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx.lookup("java:comp/env");

				ds = (DataSource) envCtx.lookup("jdbc/storyDS");

			} catch (NamingException e) {
				System.out.println("Error:" + e.getMessage());
			}
		}

		private static final String TABLE_NAME = "paziente";
		private static final String TABLE_NAME2 = "album_paziente";
		private static final String TABLE_NAME3 = "albums";
		
		public synchronized void doSave(Paziente p, int id) throws SQLException {

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL = "INSERT INTO " + PazienteModel.TABLE_NAME
					+ " (nome, cognome, email, password, idAccount) VALUES (?,?,?,?,?)";
			try {
				connection = ds.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, p.getNome());
				preparedStatement.setString(2, p.getCognome());
				preparedStatement.setString(3, p.getEmail());
				preparedStatement.setString(4, p.getPassword());
				preparedStatement.setInt(5, id);
				preparedStatement.executeUpdate();

			} finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					if (connection != null)
						connection.close();
				}
			}
		}
		
		
		public synchronized ArrayList<Paziente> doGetPazienti(int id) throws SQLException {

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			ArrayList<Paziente> pazienti= new ArrayList<Paziente>();
			int temp= 0;
			
			String loginSQL = "SELECT * FROM "+ PazienteModel.TABLE_NAME +" WHERE idAccount=?";
		
			try {
				connection = ds.getConnection();
				preparedStatement = connection.prepareStatement(loginSQL);
				preparedStatement.setInt(1, id);
				
				ResultSet result = preparedStatement.executeQuery();
					
				while(result.next()){
					Paziente p= new Paziente();
						p.setNome(result.getString("nome"));
						p.setCognome(result.getString("cognome"));
						p.setEmail(result.getString("email"));
						p.setPassword(result.getString("password"));
						p.setId(result.getInt("id"));
						pazienti.add(p);
						temp++;
					}
				if(temp==0){
					pazienti=null;
				}
				
				}	
	          finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					if (connection != null)
						connection.close();
				}
			}
			return pazienti; 
		}
		
		public synchronized void doDelete(int id) throws SQLException {

			Connection connection = null;
			PreparedStatement preparedStatement = null;
			
			String loginSQL = "DELETE FROM "+ PazienteModel.TABLE_NAME +"  WHERE id=?";
		
			try {
				connection = ds.getConnection();
				preparedStatement = connection.prepareStatement(loginSQL);
				preparedStatement.setInt(1, id);
				
				preparedStatement.executeUpdate();
				}	
	          finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					if (connection != null)
						connection.close();
				}
			}
		}
		
		public synchronized Paziente doGetPaziente(int id) throws SQLException{
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			Paziente p = new Paziente();

			String loginSQL = "SELECT * FROM "+ PazienteModel.TABLE_NAME +" WHERE id= ? ";
	   
			try {
				connection = ds.getConnection();
				preparedStatement = connection.prepareStatement(loginSQL);
				preparedStatement.setInt(1, id);

				ResultSet rs = preparedStatement.executeQuery();

				while (rs.next()) {
					p.setNome(rs.getString("nome"));
					p.setCognome(rs.getString("cognome"));
					p.setEmail(rs.getString("email"));
					p.setPassword(rs.getString("password"));
					p.setId(rs.getInt("id"));
				}

			} finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					if (connection != null)
						connection.close();
				}
			}
			return p;
		}
		

		public synchronized void doAssociate(int idP,int idA) throws SQLException {

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL = "INSERT INTO " + PazienteModel.TABLE_NAME2
					+ " (idAlbum, idPaziente) VALUES (?,?)";
			try {
				connection = ds.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setInt(1, idA);
				preparedStatement.setInt(2, idP);
				preparedStatement.executeUpdate();

			} finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					if (connection != null)
						connection.close();
				}
			}
		}
		
		
		
		public synchronized void doDeleteAssociation(int idA, int idP) throws SQLException {

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL = "DELETE FROM " + PazienteModel.TABLE_NAME2 +" WHERE idAlbum=? and idPaziente=?";
			try {
				connection = ds.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setInt(1, idA);
				preparedStatement.setInt(2, idP);
			
				preparedStatement.executeUpdate();

			} finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					if (connection != null)
						connection.close();
				}
			}
		}
		
		public synchronized  ArrayList<Album> doGetAssociatedAlbumById(int id) throws SQLException {

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			ArrayList<Album> albums=new ArrayList<Album>();
			int temp= 0;
			String loginSQL = "SELECT * FROM "+ PazienteModel.TABLE_NAME3+","+ PazienteModel.TABLE_NAME2+" WHERE idPaziente=? AND id=idAlbum";
		
			try {
				connection = ds.getConnection();
				preparedStatement = connection.prepareStatement(loginSQL);
				preparedStatement.setInt(1, id);
				
				ResultSet result = preparedStatement.executeQuery();
					
				while(result.next()){
					Album a=new Album();
						a.setId(result.getInt("id"));
						a.setNome(result.getString("nome"));
						a.setPath(result.getString("path"));
						a.setTipo(result.getInt("tipo"));
						albums.add(a);
						temp++;
					}
				if(temp==0){
					albums=null;
				}
				
				}	
	          finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					if (connection != null)
						connection.close();
				}
			}
			return albums; 
		}
		
		
		
		
	
	}

