package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import entity.Account;
import util.GlobalConstants;

public class LoginModel {
	
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

	private static final String TABLE_NAME = "account";
	
	public synchronized Account doLogin(String email,String pass) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int temp= 0;
		Account account=new Account();
		
		String loginSQL = "SELECT * FROM "+ LoginModel.TABLE_NAME +" WHERE email= ? and password= ?";
	
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(loginSQL);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, pass);
			
			ResultSet result = preparedStatement.executeQuery();
				
			while(result.next()){
					account.setId(result.getInt("id"));
					account.setNome(result.getString("nome"));
					account.setCognome(result.getString("cognome"));
					account.setEmail(result.getString("email"));
					account.setPassword(result.getString("password"));
					account.setEMAIL_VERIFICATION_HASH(result.getString("EMAIL_VERIFICATION_HASH"));
					account.setSTATUS(result.getString("STATUS"));
					temp++;
				}
			if(temp==0){
				account=null;
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
		return account; 
	}
	
	// Forgot Password
	
	public static Account selectUSER(String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet res=null;
		Account pojo = null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement("select id, email, nome, cognome, EMAIL_VERIFICATION_HASH, STATUS, CREATED_TIME from Account where id = ?");
			ps.setString(1, userId);
			res = ps.executeQuery();
			if (res != null) {
				while (res.next()) {
					pojo = new Account();
					pojo.setId(res.getInt(1));
					pojo.setNome(res.getString(2));
					pojo.setCognome(res.getString(3));
					pojo.setEmail(res.getString(4));
					pojo.setEMAIL_VERIFICATION_HASH(res.getString(5));
					pojo.setSTATUS(res.getString(6));
				}
			}
		}finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
		return pojo;
	}

	public static boolean verifyEmailHash(String user_id, String hash) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean verified = false;
		ResultSet res=null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement("select 1 from Account where id = ? and EMAIL_VERIFICATION_HASH = ?");
			ps.setString(1, user_id);
			ps.setString(2, hash);
			res = ps.executeQuery();
			if (res != null) {
				while (res.next()) {
					verified = true;
				}
			}
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
		return verified;
	}

	public static boolean isEmailExists(String email) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean verified = false;
		ResultSet res=null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement("select 1 from Account where email = ?");
			ps.setString(1, email);
			res = ps.executeQuery();
			if (res != null) {
				while (res.next()) {
					verified = true;
				}
			}
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
		return verified;
	}



	public static void updateStaus(int user_id, String status) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement("update Account set STATUS = ? where id = ?");
			ps.setString(1,status);
			ps.setInt(2,user_id);
			ps.executeUpdate();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
	}

	public static void updateEmailVerificationHash(String user_id, String hash) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement("update Account set EMAIL_VERIFICATION_HASH = ? where id = ?");
			ps.setString(1,hash);
			ps.setString(2,user_id);
			ps.executeUpdate();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
	}


	

	public static boolean verifyUserIdAndPassword(String userId,
			String inputCurrentPassword) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean verified = false;
		ResultSet res=null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement("select 1 from Account where id = ? and password = ?");
			ps.setString(1, userId);
			ps.setString(2, inputCurrentPassword);
			res = ps.executeQuery();
			if (res != null) {
				while (res.next()) {
					verified = true;
				}
			}
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
		return verified;
	}

	public static void updatePassword(int userId, String inputPassword) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement("update Account set password = ? where id = ?");
			ps.setString(1,inputPassword);
			ps.setInt(2,userId);
			ps.executeUpdate();
		}finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
	}

	public static void updateEmailVerificationHashForResetPassword(String inputEmail,
			String hash) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement("update Account set EMAIL_VERIFICATION_HASH = ?, STATUS = ? where email = ?");
			ps.setString(1,hash);
			ps.setString(2,GlobalConstants.IN_RESET_PASSWORD);
			ps.setString(3,inputEmail);
			ps.executeUpdate();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
	}
	

	// End
	
	public synchronized Account getAccountByEmail(String email) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int temp= 0;
		Account account=new Account();
		
		String loginSQL = "SELECT * FROM "+ LoginModel.TABLE_NAME +" WHERE email= ?";
	
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(loginSQL);
			preparedStatement.setString(1, email);
			
			ResultSet result = preparedStatement.executeQuery();
				
			while(result.next()){
					account.setId(result.getInt("id"));
					account.setNome(result.getString("nome"));
					account.setCognome(result.getString("cognome"));
					account.setEmail(result.getString("email"));
					account.setPassword(result.getString("password"));
					temp++;
				}
			if(temp==0){
				account=null;
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
		return account; 
	}
	
	
}
