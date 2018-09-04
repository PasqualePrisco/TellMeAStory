package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import entity.Album;
import entity.Vignetta;

public class AlbumModel {
	
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

	private static final String TABLE_NAME = "albums";
	private static final String TABLE_NAME2 = "vignette";
	
	public synchronized void doSave(Album album, int id) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + AlbumModel.TABLE_NAME
				+ " (nome, path, tipo, idAccount) VALUES (?,?,?,?)";
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, album.getNome());
			preparedStatement.setString(2, album.getPath());
			preparedStatement.setInt(3, album.getTipo());
			preparedStatement.setInt(4, id);
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
	
	
	public synchronized ArrayList<Album> doGetAlbums(int id) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Album> albums= new ArrayList<Album>();
		int temp= 0;
		
		String loginSQL = "SELECT * FROM "+ AlbumModel.TABLE_NAME +" WHERE idAccount=?";
	
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(loginSQL);
			preparedStatement.setInt(1, id);
			
			ResultSet result = preparedStatement.executeQuery();
				
			while(result.next()){
				Album album= new Album();
					album.setId(result.getInt("id"));
					album.setNome(result.getString("nome"));
					album.setPath(result.getString("path"));
					album.setTipo(result.getInt("tipo"));
					albums.add(album);
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
	
		
	
	public synchronized ArrayList<Vignetta> doGetVignette(int id) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Vignetta> vignette= new ArrayList<Vignetta>();
		int temp= 0;
		
		String loginSQL = "SELECT * FROM "+ AlbumModel.TABLE_NAME2 +" WHERE idAlbum=? ORDER BY numero ASC";
	
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(loginSQL);
			preparedStatement.setInt(1, id);
			
			ResultSet result = preparedStatement.executeQuery();
				
			while(result.next()){
				Vignetta v= new Vignetta();
					v.setId(result.getInt("id"));
					v.setPath(result.getString("path"));
					v.setNumero(result.getInt("numero"));
					v.setAudio(result.getString("audio"));
					vignette.add(v);
					temp++;
				}
			if(temp==0){
				vignette=null;
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
		return vignette; 
	}
	
	
	public synchronized Album doFindById(int id) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Album album = new Album();

		String loginSQL = "SELECT * FROM "+ AlbumModel.TABLE_NAME +" WHERE id= ? ";
   
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(loginSQL);
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				album.setId(rs.getInt("id"));
				album.setNome(rs.getString("nome"));
				album.setPath(rs.getString("path"));
				album.setTipo(rs.getInt("tipo"));
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
		return album;
	}
	
	
	public synchronized void updateVignetta(Vignetta vignetta) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		System.out.println(vignetta.getNumero() +"    "+ vignetta.getId());
		String loginSQL = "UPDATE "+ AlbumModel.TABLE_NAME2 +" SET numero=? WHERE id=?";
	
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(loginSQL);
			preparedStatement.setInt(1, vignetta.getNumero());
			preparedStatement.setInt(2, vignetta.getId());
			
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
	
	public synchronized void updateAlbum(Album album) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String loginSQL = "UPDATE "+ AlbumModel.TABLE_NAME +" SET nome=?, path=? WHERE id=?";
	
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(loginSQL);
			preparedStatement.setString(1, album.getNome());
			preparedStatement.setString(2, album.getPath());
			preparedStatement.setInt(3, album.getId());
			
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
	
	
	public synchronized void removeAlbum(int id) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String loginSQL = "DELETE FROM "+ AlbumModel.TABLE_NAME +"  WHERE id=?";
	
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
	
	
	
	public synchronized void removeVignetta(int id) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String loginSQL = "DELETE FROM "+ AlbumModel.TABLE_NAME2 +"  WHERE id=?";
	
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
	
	public synchronized void doSaveV(Vignetta v, int id) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String insertSQL;

			insertSQL = "INSERT INTO " + AlbumModel.TABLE_NAME2
				+ " (path, numero, idAlbum) VALUES (?,?,?)";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, v.getPath());
			preparedStatement.setInt(2, v.getNumero());
			preparedStatement.setInt(3, id);
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
	
	
	public synchronized void doSaveAudio(String audio, int id) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String loginSQL = "UPDATE "+ AlbumModel.TABLE_NAME2 +" SET audio=? WHERE id=?";

		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(loginSQL);
			preparedStatement.setString(1, audio);
			preparedStatement.setInt(2, id);
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
	
	
	public synchronized void doDeleteAudio(int id) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String loginSQL = "UPDATE "+ AlbumModel.TABLE_NAME2 +" SET audio=NULL WHERE id=?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(loginSQL);
			preparedStatement.setInt(1, id);
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

}
