package com.ss.ivr.survey.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ss.ivr.survey.util.DBConnectUtil;


public class SurveyResponseDAO {
	
	private static  Logger logger = Logger.getLogger(SurveyResponseDAO.class.getName());
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SurveyResponseDAO sr =  new SurveyResponseDAO(null);
		
		List<SurveyResponseBean> lResponses = new ArrayList<SurveyResponseBean>();
		SurveyResponseBean srb = new SurveyResponseBean();
		srb.setReponseId(1);
		srb.setAnswer("Yes");
		srb.setQuestionId(2);
		lResponses.add(srb);
		
		srb = new SurveyResponseBean();
		srb.setReponseId(1);
		srb.setAnswer("5");
		srb.setQuestionId(3);
		lResponses.add(srb);
		
		srb = new SurveyResponseBean();
		srb.setReponseId(1);
		srb.setAnswer("No");
		srb.setQuestionId(4);
		lResponses.add(srb);
		
//		sr.insertSurveyResponses(lResponses);
		SurveyResponseDAO.setSurveyResponses(lResponses, null);
		
//		System.out.println("getSurveyDetails: " + sr.retrieveSurveyDetails("1234567890"));
	}
	
	private String connectionString = null;

	public SurveyResponseDAO(String connectionString) {
		this.connectionString = connectionString;
	}

	
	
	/**
	 * insert the caller's responses - static way
	 * @param ArrayList of SurveyResponseBean
	 * @return
	 */
	public static synchronized int[] setSurveyResponses(List<SurveyResponseBean> surveyResponses, String URL) {
		return new SurveyResponseDAO(URL).insertSurveyResponses(surveyResponses);
	}
	public int[] insertSurveyResponses(List<SurveyResponseBean> surveyResponses) {
		int ret[] = new int[0];
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		List<String> lSql = null;
		SurveyResponseBean srb = null;
		int i = 0;
		try {
			lSql = new ArrayList<String>();
			for (i = 0; i < surveyResponses.size(); i++) {
				srb = (SurveyResponseBean)surveyResponses.get(i);
				sql = new StringBuffer("INSERT INTO QUESTIONRESPONSE(RESPONSEID, QUESTIONID, VALUE, DATECOMPLETED) ")
					.append("VALUES(").append(srb.getReponseId()).append(",").append(srb.getQuestionId()).append(",")
					.append("'").append(srb.getAnswer()).append("',getDate())").toString();
				lSql.add(sql);
			}
			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement();
			for (i = 0; i < lSql.size(); i++) {
				stmt.addBatch(lSql.get(i));
			}
			ret = stmt.executeBatch();
			
			logger.debug("[SurveyResponseDAO] question responses to the survey saved.");

		}
		catch (SQLException sqle) {
			logger.error("[SurveyResponseDAO] SQL Exception insertSurveyResponses: " + sqle.getMessage());
			sqle.printStackTrace();
		}
		catch (Exception e) {
			logger.error("[SurveyResponseDAO] Exception insertSurveyResponses: " + e.getMessage());
		}
		finally {
			close(stmt);
			close(conn);
		}
		return ret;
	}
	

	/**
	 * Retrieves the questions assigned to the given survey id
	 * @param surveyId
	 * @return
	 */
	public List<SurveyQuestionBean> getSurveyQuestions(int surveyId) {
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		String sql = "";
		List<SurveyQuestionBean> lSurveyQuestions = null;
		SurveyQuestionBean sqb = null;
		try {
			sql = new StringBuffer("SELECT A.SURVEYID, A.SEQUENCENUM, A.QUESTIONID, B.TEXT, B.AUDIOFILE, B.QUESTIONTYPEID, ") 
			.append("C.QUESTIONTYPE, A.NEXTQUESTIONYES, A.NEXTQUESTIONNO ") 
			.append("FROM QUESTIONROUTING AS A, QUESTION AS B, QUESTIONTYPEREF AS C ") 
			.append("WHERE A.SURVEYID = ").append(surveyId).append(" ")
			.append("AND A.QUESTIONID = B.ID AND B.QUESTIONTYPEID = C.QUESTIONTYPEID AND B.ID = A.QUESTIONID ")
			.append("ORDER BY A.SEQUENCENUM ").toString();

			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 
			rset = stmt.executeQuery(sql);

			lSurveyQuestions = new ArrayList<SurveyQuestionBean>();
			while (rset.next()) {
				sqb = new SurveyQuestionBean();
				sqb.setSurveyId(rset.getInt("SURVEYID"));
				sqb.setSequenceNum(rset.getInt("SEQUENCENUM"));
				sqb.setQuestionId(rset.getInt("QUESTIONID"));
				sqb.setQuestion(rset.getString("TEXT"));
				sqb.setAudioFile(rset.getString("AUDIOFILE"));
				sqb.setQuestionTypeId(rset.getInt("QUESTIONTYPEID"));
				sqb.setQuestionType(rset.getString("QUESTIONTYPE"));
				sqb.setNextQuestionYes(rset.getInt("NEXTQUESTIONYES"));
				sqb.setNextQuestionNo(rset.getInt("NEXTQUESTIONNO"));

				lSurveyQuestions.add(sqb);				
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
		return lSurveyQuestions;
	}
	
	/**
	 * get the survey id, response id, and call guid on start of the survey - static way
	 * @param callId
	 * @return
	 */
	public static synchronized Map getSurveyDetails(String callId, String URL) {
		return new SurveyResponseDAO(URL).retrieveSurveyDetails(callId);
	}
	/**
	 * get the Survey  
	 * This should connect to the Ops Console B database.
	 * @return Map 
	 */
	public Map retrieveSurveyDetails( String callId ) {
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		String sql = "";
		Map surveyDetails = null;
		boolean exist = false;
		int rows = 0;


		try {
			sql = new StringBuffer("SELECT ID, CALLID, DNIS, ANI, CUSTOMERID, SURVEYID FROM SURVEYUSERRESPONSE ") 
					.append("WHERE CALLID = '").append(callId).append("' ORDER BY dateOffered DESC").toString();

			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 
			rset = stmt.executeQuery(sql);

			surveyDetails = new HashMap<String,String>();
			if (rset.next()) {
				surveyDetails.put("RESPONSEID", ""+rset.getInt("ID"));
				surveyDetails.put("CALLID", ""+rset.getInt("CALLID"));
				surveyDetails.put("SURVEYID", ""+rset.getInt("SURVEYID"));
			}
		}
		catch (SQLException sqle) {
//			System.out.println("SQL Exception getSurveyDetails: " + sqle.getLocalizedMessage());
			logger.error("SQL Exception getSurveyDetails: " + sqle.getMessage());
			sqle.printStackTrace();
		}
		catch (Exception e) {
//			System.out.println("Exception getSurveyDetails: " + e.getLocalizedMessage());
			logger.error("Exception getSurveyDetails: " + e.getMessage());
		}
		finally {
			close(rset);
			close(stmt);
			close(conn);
		}
		return surveyDetails;
	}
	
	/**
	 * Update the user response using the pAccepted param 
	 * 0 = declined survey
	 * 1 = accepted survey
	 * 2 = accepted but did not finish the survey
	 * ** there are 2 methods here bec the one below can be accessed static way
	 * @param pResponseId
	 * @param pAccepted
	 * @return - number of rows updated
	 */
	public static synchronized int setSurveyUserResponse(int pResponseId, int pAccepted, String db_URL) {
		return new SurveyResponseDAO(db_URL).updateUserResponse(pResponseId, pAccepted);
	}
	public int updateUserResponse(int pResponseId, int pAccepted) {
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		int updatecount = 0;
		try {
			sql = new StringBuffer("UPDATE SURVEYUSERRESPONSE SET ACCEPTED = ").append(pAccepted)
				.append(" WHERE ID = ").append(pResponseId).toString();
		
			logger.debug("[SurveyResponseDAO] updateUserResponse("+pResponseId+","+pAccepted+")  sql: " + sql);			
			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 
			updatecount = stmt.executeUpdate(sql);
		}
		catch (SQLException sqle) {
			logger.error("[SurveyResponseDAO] SQL Exception updateUserResponse: " + sqle.getMessage());
			sqle.printStackTrace();
		}
		catch (Exception e) {
			logger.error("[SurveyResponseDAO] Exception updateUserResponse: " + e.getMessage());
		}
		finally {
			close(stmt);
			close(conn);
		}
		return updatecount;
	}

	


	/**
	 * Releases the specified Connection object's database and JDBC resources
	 * immediately instead of waiting for them to be automatically released.
	 * by calling the close method of Connection object.
	 *
	 * @ param Connection, the connection object to be closed
	 */
	private void close( Connection connection ){
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
	private void close( ResultSet resultSet ){
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
	private void close( Statement statement ){
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
