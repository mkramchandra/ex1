package com.ss.ivr.survey.util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;



public class DBConnectUtil {
	private static Logger logger = Logger.getLogger(DBConnectUtil.class.getName());	

	public static Connection getConnection(String dbURL) throws Exception {
		return getConnection(dbURL, DriverManager.getLoginTimeout());
	}

	
	public static Connection getConnection(String dbURL, int timeout) throws Exception {

		try {
			// load the driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		} catch (ClassNotFoundException e) {
			throw new Exception(
					"DBConnectUtil ClassNotFoundException: " + e.getMessage(),e);
		} catch (IllegalAccessException e) {
			throw new Exception(
					"DBConnectUtil IllegalAccessException: " + e.getMessage(),e);
		} catch (InstantiationException e) {
			throw new Exception(
					"DBConnectUtil InstantiationException: " + e.getMessage(),e);
		}
		
		DriverManager.setLoginTimeout(timeout);
		Connection sqlConn = DriverManager.getConnection(dbURL);
		if (sqlConn == null) {
			logger.error("DBCOnnectionUtil<getConnection> could not acquire db connection getting connection");
			throw new Exception("Could not acquire Database Connection",null);
		}
		return sqlConn;

	}
	
}
