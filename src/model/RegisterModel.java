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

public class RegisterModel {
	
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
	
	public synchronized int doSave(Account account) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement ps2 = null;
		ResultSet res = null;
		Integer id= null;
		String insertSQL = "INSERT INTO " + RegisterModel.TABLE_NAME
				+ " (nome, cognome, email, password, EMAIL_VERIFICATION_HASH) VALUES (?, ?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, account.getNome());
			preparedStatement.setString(2, account.getCognome());
			preparedStatement.setString(3, account.getEmail());
			preparedStatement.setString(4, account.getPassword());
			preparedStatement.setString(5, account.getEMAIL_VERIFICATION_HASH());
			preparedStatement.executeUpdate();
			
			// get id Account
			ps2 = connection.prepareStatement("SELECT LAST_INSERT_ID()");
			res = ps2.executeQuery();
			
			if (res != null) {
				while (res.next()) {
					id = res.getInt(1);
				}
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
		return id;
	}

	
}