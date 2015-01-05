/**
 * 
 */
package com.ss.ivr.survey.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import com.ss.ivr.survey.util.DBConnectUtil;
import com.ss.ivr.survey.util.SurveyBean;


/**
 * 
 *
 */
public class SurveyDAO {

	private static  Logger logger = Logger.getLogger(SurveyDAO.class.getName());

	private static String connectionString = null;

	public SurveyDAO(String connectionString) {
		this.connectionString = connectionString;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String dbURL="jdbc:sqlserver://10.157.42.135:1433;user=sa;password=FGAadmin#123;database=ivrmanagement";
		SurveyOfferDAO so =  new SurveyOfferDAO(dbURL);
		System.out.println(so.getSurveyIdByTFN("4281"));
	}



	/**
	 * Get the matching survey id based on Business Unit
	 * 
	 * @return
	 */
	public ArrayList<String> getSurveyId(int businessUnitID) {
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		ArrayList<String> sSurveyId = new ArrayList<String>();
		try {
			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 

			String sql = "SELECT SURVEYID FROM SURVEYBU " +
			" WHERE BUID = " + businessUnitID;
			rset = stmt.executeQuery(sql);
			while(rset.next()) {
				sSurveyId.add(""+rset.getInt("SURVEYID"));
			}
			logger.debug("[SurveyOfferDAO] getSurveyIdFromParams() : " + sSurveyId);
		}
		catch (SQLException sqle) {
			logger.error("[SurveyOfferDAO] SQL Exception getSurveyIdFromParams: " + sqle.getLocalizedMessage());
		}
		catch (Exception e) {
			logger.error("[SurveyOfferDAO] Exception getSurveyIdFromParams: " + e.getLocalizedMessage());
		}
		finally {
			close(rset);
			close(stmt);
			close(conn);
		}
		return sSurveyId;
	}
	
	
	
	public SurveyBean getSurvey(int surveyId) {
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		SurveyBean bean = null;
		String sql = "";
		try {
			sql = new StringBuffer("SELECT * FROM CONFIGURED ") 
			.append("WHERE ID =" + surveyId) 
			.toString();
			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 
			rset = stmt.executeQuery(sql);
			bean = new SurveyBean();
			if(rset.next()) {
				bean.setId(rset.getInt("ID"));
				bean.setName(rset.getString("Name"));
				bean.setDescription(rset.getString("Description"));
				bean.setActive(rset.getBoolean("Active"));
				bean.setStartDate(rset.getDate("startDate"));
				bean.setEndDate(rset.getDate("EndDate"));
				bean.setQ1Type(rset.getInt("Q1Type"));
				bean.setQ1Prompt(rset.getString("Q1Prompt"));
				bean.setQ2Type(rset.getInt("Q2Type"));
				bean.setQ2Prompt(rset.getString("Q2Prompt"));
				bean.setQ3Type(rset.getInt("Q3Type"));
				bean.setQ3Prompt(rset.getString("Q3Prompt"));
				bean.setQ4Type(rset.getInt("Q4Type"));
				bean.setQ4Prompt(rset.getString("Q4Prompt"));
				bean.setQ5Type(rset.getInt("Q5Type"));
				bean.setQ5Prompt(rset.getString("Q5Prompt"));
			}
		}
		catch (SQLException sqle) {
			System.out.println("SQL Exception getSurveyQuestions: " + sqle.getLocalizedMessage());
			logger.error("SQL Exception getSurveyQuestions: " + sqle.getLocalizedMessage());
		}
		catch (Exception e) {
			System.out.println("Exception getSurveyQuestions: " + e.getLocalizedMessage());
			logger.error("Exception getSurveyQuestions: " + e.getLocalizedMessage());
		}
		finally {
			close(rset);
			close(stmt);
			close(conn);
		}
		return bean;
	}
		
	
	public int getSurveyTFNID(String TFN) {
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		String sql = "";
		int tfnID = 0;
		
		logger.debug("[SurveyOfferDAO1] getSurvey TFNID ");
		
		try {
			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 
			//SURVEY DNIS - get survey id based from dnis
			sql = "SELECT ID FROM TFN " +
			" WHERE tfn = '" + TFN + "' ";
			rset = stmt.executeQuery(sql);
			if (rset.next()) {
				tfnID = rset.getInt("ID");
			}
			logger.debug("[SurveyOfferDAO1] getSurveyIdFromParams() sTmpSurveyId: " + tfnID);
		}
		catch (SQLException sqle) {
			logger.error("[SurveyOfferDAO1] SQL Exception getSurveyIdFromParams: " + sqle.getLocalizedMessage());
		}
		catch (Exception e) {
			logger.error("[SurveyOfferDAO1] Exception getSurveyIdFromParams: " + e.getLocalizedMessage());
		}
		finally {
			close(rset);
			close(stmt);
			close(conn);
		}
		return tfnID;
	}
	
	
	/**
	 * It will get the user's survey details by giving the callId
	 * @param callId
	 * @return
	 */

	/**
	 * Save the user's response after he was offered a survey. 
	 * This should connect to the Ops Console B database.
	 * @return int - number of rows inserted/updated
	 */
	public int setUserResponse(int surveyId, int accepted, String DNIS, String ANI, String customerId, String callId,String routerCallKey,String routerCallKeyDay, String language) {
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		String sql = "";
		boolean exist = false;
		int rows = 0;

		logger.debug("[SurveyOfferDAO] enter setUserResponse");
		try {
//			sql = new StringBuffer("SELECT SURVEYID FROM SURVEYUSERRESPONSE ") 
//			.append("WHERE DNIS = '").append(DNIS).append("' AND ANI = '").append(ANI).append("' AND CUSTOMERID = '").append(customerId).append("' ")
//			.toString();


			conn = DBConnectUtil.getConnection(connectionString);
			logger.debug("[SurveyOfferDAO] setUserResponse connectionString "+connectionString);
			stmt = conn.createStatement(); 
//			rset = stmt.executeQuery(sql);

//			if (rset.next()) {
//			exist = true;
//			}

//			if (exist) {
//			sql = new StringBuffer("UPDATE SURVEYUSERRESPONSE SET ACCEPTED = ").append(accepted).append(", DATEOFFERED = getDate(), ")
//			.append(" CALLID = '").append(callId).append("' ")
//			.append("WHERE DNIS = '").append(DNIS).append("' AND ANI = '").append(ANI).append("' AND CUSTOMERID = '")
//			.append(customerId).append("' ")
//			.toString();
//			}
//			else {

			sql = new StringBuffer("INSERT INTO SURVEYUSERRESPONSE (DNIS, ANI, ACCEPTED, SURVEYID, CUSTOMERID, CALLID, ROUTERCALLKEY,ROUTERCALLKEYDAY,DATEOFFERED, LANG) ")
			.append("VALUES('").append(DNIS).append("', '").append(ANI).append("', ").append(accepted)
			.append(", ").append(surveyId).append(", '").append(customerId).append("', '").append(callId).append("', '").append(routerCallKey).append("', '")
			.append(routerCallKeyDay).append("', getDate() ").append(", '").append(language).append("')")
			.toString();				

//			}
			logger.debug("[SurveyOfferDAO] setUserResponse insertsql " + sql);
			rows = stmt.executeUpdate(sql);

		}
		catch (SQLException sqle) {
//			System.out.println("SQL Exception setUserResponse: " + sqle.getLocalizedMessage());
			logger.error("[SurveyOfferDAO1] SQL Exception setUserResponse: " + sqle.getLocalizedMessage());
		}
		catch (Exception e) {
//			System.out.println("Exception setUserResponse: " + e.getLocalizedMessage());
			logger.error("[SurveyOfferDAO1] Exception setUserResponse: " + e.getLocalizedMessage());
		}
		finally {
			close(rset);
			close(stmt);
			close(conn);
		}
		return rows;
	}




	/**
	 * Close connection object 
	 * @return
	 */

	/**
	 * Releases the specified Connection object's database and JDBC resources
	 * immediately instead of waiting for them to be automatically released.
	 * by calling the close method of Connection object.
	 *
	 * @ param Connection, the connection object to be closed
	 */
	private static void close( Connection connection ){
		if(connection != null){
			try{
				connection.close();
			}
			catch(SQLException sqlException){
				connection = null;
			}
		}
	}

	/**
	 * Releases the specified ResultSet object's database and JDBC resources
	 * immediately instead of waiting for them to be automatically released.
	 * by calling the close method of ResultSet object.
	 *
	 * @ param ResultSet, the resultSet object to be closed
	 */
	private static void close( ResultSet resultSet ){
		if(resultSet != null){
			try{
				resultSet.close();
			}
			catch(SQLException sqlException){
				//Eg: resultSet is already closed
				//ignored
			}
		}
	}

	/**
	 * Releases the specified Statement object's database and JDBC resources
	 * immediately instead of waiting for them to be automatically released.
	 * by calling the close method of Statement object.
	 *
	 * @ param Statement, the statement object to be closed
	 */
	private static void close( Statement statement ){
		if(statement != null){
			try{
				statement.close();
			}
			catch(SQLException sqlException){
				//Eg: statement is already closed
				//ignored
			}
		}
	}
	
	
}

