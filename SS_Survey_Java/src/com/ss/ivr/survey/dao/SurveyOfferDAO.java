/**
 * 
 */
package com.ss.ivr.survey.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import com.ss.ivr.survey.util.DBConnectUtil;
import com.ss.ivr.survey.util.SurveySettingsBean;


/**
 * 
 *
 */
public class SurveyOfferDAO {

	private static  Logger logger = Logger.getLogger(SurveyOfferDAO.class.getName());

	private String connectionString = null;

	public SurveyOfferDAO(String connectionString) {
		// System.out.println("dddd of mohdao");

		this.connectionString = connectionString;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dbURL="jdbc:sqlserver://10.157.42.135:1433;user=sa;password=FGAadmin#123;database=ivrmanagement";
		SurveyOfferDAO so =  new SurveyOfferDAO(dbURL);
		System.out.println(so.getSurveyIdByTFN("4281"));
		//	Map m = so.getSurveySetting();
		//	System.out.println(m);
		//	System.out.println(so.getSurveyParams());

		//so.setUserResponse(1, 1, "4080", "4696842829", "332223", "1234567890");

		// Add by mahdi
		// setUserResponse(int surveyId, int accepted, String DNIS, String ANI, String customerId, String callId,String routerCallKey,String routerCallKeyDay) 
		//	so.setUserResponse(1, 1, "DNIS2", "ANI", "CustomerID", "callID","key","day");

		//SurveyOfferManager som = new SurveyOfferManager();		
		//som.setUserResponse(1, 1, "DNIS4", "ANI", "CustomerID", "callID","key","day");
		System.out.println("Done");

//		so.setOnDemandUserResponse("8883574237", "4696842828");

	}



	/**
	 * Retrieve all data from survey table. Includes the DNISs attached to each survey
	 * Retrieve from Side B database only.
	 * @return ArrayList<SurveyBean>
	 */
	public Map getSurveySetting() {
		Map surveyMap = null;
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		String sql = "";

		int surveyId = 0;
		int prevId = 0;
		String surveyTime[] = null;
		List timeList = null;
		SurveySettingsBean settingBean = null;

		try {
			sql = new StringBuffer("SELECT A.ID, A.STARTDATE, A.ENDDATE, ")
			.append("A.CALLINTERVAL, A.ACCEPTEDDAYSINTERVAL, A.DECLINEDDAYSINTERVAL, A.STATUS, C.STARTTIME, C.ENDTIME ")
			.append("FROM SURVEY AS A, SURVEYOFFERTIME AS C WHERE A.ID = C.SURVEYID ORDER BY A.ID DESC ").toString();

			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 
			rset = stmt.executeQuery(sql);

			surveyMap = new HashMap();
			timeList = new ArrayList();
			settingBean = new SurveySettingsBean();
			while (rset.next()) {
				surveyTime = new String[2];
				surveyId = rset.getInt("ID");

				if (surveyId != prevId) {
					settingBean = new SurveySettingsBean();
					timeList = new ArrayList();

					settingBean.setSurveyId(surveyId);
					settingBean.setStartDate(rset.getString("STARTDATE"));
					settingBean.setEndDate(rset.getString("ENDDATE"));
					settingBean.setCallInterval(rset.getInt("CALLINTERVAL"));
					settingBean.setAcceptedDaysInterval(rset.getInt("ACCEPTEDDAYSINTERVAL"));
					settingBean.setDeclinedDaysInterval(rset.getInt("DECLINEDDAYSINTERVAL"));
					settingBean.setStatus(rset.getString("STATUS"));
				}
				surveyTime[0] = rset.getString("STARTTIME");
				surveyTime[1] = rset.getString("ENDTIME");
				timeList.add(surveyTime);
				settingBean.setSurveyTime(timeList);
				surveyMap.put(""+surveyId, settingBean);
				prevId = surveyId;
			}
		}
		catch (SQLException sqle) {
			System.out.println("SQL Exception getSurveySetting: " + sqle.getLocalizedMessage());
			logger.error("SQL Exception getSurveySetting: " + sqle.getLocalizedMessage());
		}
		catch (Exception e) {
			System.out.println("Exception getSurveySetting: " + e.getLocalizedMessage());
			logger.error("Exception getSurveySetting: " + e.getLocalizedMessage());
		}
		finally {
			close(rset);
			close(stmt);
			close(conn);
		}
		return surveyMap;
	}


	/**
	 * Get all survey id and attached dnis, ani, and customer id.
	 * all in one shot to save connection
	 * @return
	 */
	public Map<String, Map<String, List<String>>> getSurveyParams() {
		Map<String, Map<String, List<String>>> returnMap = null;
		Map<String, List<String>> surveyMap = null;
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		String sql = "";

		String sSurveyId = "";
		String current = "", previous = "";
		List<String> lSurveyId = null;

		try {

			returnMap = new HashMap<String, Map<String, List<String>>>();
			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 

			//SURVEY DNIS
//			logger.debug("getting SURVEYDNIS");
			sql = "SELECT B.DNIS, B.SURVEYID FROM SURVEYDNIS AS B ORDER BY B.DNIS";
			rset = stmt.executeQuery(sql);
			lSurveyId = new ArrayList<String>();
			surveyMap = new HashMap<String, List<String>>();
			while (rset.next()) {
				current = rset.getString("DNIS");
				if (!current.equals(previous)) {
					lSurveyId = new ArrayList<String>();
					sSurveyId = ""+rset.getInt("SURVEYID");
					lSurveyId.add(sSurveyId);
					surveyMap.put(current, lSurveyId);
				}
				else {
					sSurveyId = ""+rset.getInt("SURVEYID");
					lSurveyId.add(sSurveyId);
					surveyMap.put(current, lSurveyId);
				}				
				previous = current;
			}
			returnMap.put("SURVEYDNIS", surveyMap);

			//SURVEY ANI
//			logger.debug("getting SURVEYANI");
			sql = "SELECT ANI, SURVEYID FROM SURVEYANI ORDER BY ANI";
			rset = stmt.executeQuery(sql);
			lSurveyId = new ArrayList<String>();
			surveyMap = new HashMap<String, List<String>>();
			while (rset.next()) {
				current = rset.getString("ANI");
				if (!current.equals(previous)) {
					lSurveyId = new ArrayList<String>();
					sSurveyId = ""+rset.getInt("SURVEYID");
					lSurveyId.add(sSurveyId);
					surveyMap.put(current, lSurveyId);
				}
				else {
					sSurveyId = ""+rset.getInt("SURVEYID");
					lSurveyId.add(sSurveyId);
					surveyMap.put(current, lSurveyId);
				}				
				previous = current;
			}
			returnMap.put("SURVEYANI", surveyMap);

			//SURVEY CUSTOMER ID
//			logger.debug("getting SURVEYCUSTOMERID");
			sql = "SELECT CUSTOMERID, SURVEYID FROM SURVEYCUSTOMERID ORDER BY CUSTOMERID";
			rset = stmt.executeQuery(sql);
			lSurveyId = new ArrayList<String>();
			surveyMap = new HashMap<String, List<String>>();
			while (rset.next()) {
				current = rset.getString("CUSTOMERID");
				if (!current.equals(previous)) {
					lSurveyId = new ArrayList<String>();
					sSurveyId = ""+rset.getInt("SURVEYID");
					lSurveyId.add(sSurveyId);
					surveyMap.put(current, lSurveyId);
				}
				else {
					sSurveyId = ""+rset.getInt("SURVEYID");
					lSurveyId.add(sSurveyId);
					surveyMap.put(current, lSurveyId);
				}				
				previous = current;
			}
			returnMap.put("SURVEYCUSTOMERID", surveyMap);

			logger.debug("[SurveyOfferDAO1] ...got SURVEYDNIS, SURVEYANI, and SURVEYCUSTOMERID");
		}
		catch (SQLException sqle) {
//			System.out.println("SQL Exception getSurveyParams: " + sqle.getLocalizedMessage());
			logger.error("[SurveyOfferDAO1] SQL Exception getSurveyParams: " + sqle.getLocalizedMessage());
		}
		catch (Exception e) {
//			System.out.println("Exception getSurveyParams: " + e.getLocalizedMessage());
			logger.error("[SurveyOfferDAO1] Exception getSurveyParams: " + e.getLocalizedMessage());
		}
		finally {
			close(rset);
			close(stmt);
			close(conn);
		}
		return returnMap;
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
	
	/**By Surya
	 * Get the matching survey id based on tfn.
	 * 
	 * @return
	 */
	public ArrayList<String> getSurveyIdByTFN(String tfn) {
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		ArrayList<String> sSurveyId = new ArrayList<String>();
		try {

			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 

			String sql = "SELECT SURVEYID FROM SURVEYDNIS,SURVEY " +
			" WHERE dnis = '" + tfn+"' AND SURVEYDNIS.SURVEYID=SURVEY.ID AND SURVEY.STATUS='active'";
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
	
	
	//get BusinessUnitID
	public int getBusinessUnitID(String application) {
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		String sql = "";

		int businessUnitId = 0;
		logger.debug("[SurveyOfferDAO1] getBusinessUnitID ");
		try {

			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 

			//SURVEY DNIS - get survey id based from dnis
			sql = "SELECT ID FROM BUSINESSUNIT " +
			" WHERE NAME = '" + application + "' ";
			rset = stmt.executeQuery(sql);
			if (rset.next()) {
				businessUnitId = rset.getInt("ID");
			}
			logger.debug("[SurveyOfferDAO1] getSurveyIdFromParams() sTmpSurveyId: " + businessUnitId);

			

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
		return businessUnitId;
	}
	
/**By Surya	
 * get SurveTFN ID
 * 
 * @param TFN
 * @return
 */
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
	 * Used to get the application name based on the TFN.
	 */
	public String getBUnameBasedonDNIS(String appName, String dnis)
	{
		String targetApp = appName;
		if(!appName.equalsIgnoreCase("GPX")){
			return targetApp;
		}
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		String sql = "";
		try {
			sql = new StringBuffer("SELECT name FROM BusinessUnit as BU, TFN as T where BU.id=T.businessUnitID and T.tfn='")
			.append(dnis)
			.append("'").toString();
			
			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 
			rset = stmt.executeQuery(sql);

			if (rset.next()) {
				if(rset.getString("name") != null && rset.getString("name").trim().length() >0){
					targetApp = rset.getString("name");
				}
			}
		}catch (Exception e) {
			logger.error("Exception in getting the app name");
		}finally {
			close(rset);
			close(stmt);
			close(conn);
		}
		
		return targetApp;
	}
	/**
	 * Retrieve all data from survey table. Includes the DNISs attached to each survey
	 * This should connect to the Ops Console B database.
	 * @return ArrayList<SurveySettingsBean>
	 */
	public Map<String, String> getUserResponse(String DNIS, String ANI, String callId, String customerId ) {
		Map<String,String> responseMap = null;
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		String sql = "";
		logger.debug("[SurveyOfferDAO]   getUserResponse for. . .");
		logger.debug("[SurveyOfferDAO]  DNIS. . ."+DNIS);
		logger.debug("[SurveyOfferDAO]  ANI. . ."+ANI);
		logger.debug("[SurveyOfferDAO]  callID. . ."+callId);
		
		try {
			if ((DNIS == null || ANI == null) && callId != null) {
				sql = new StringBuffer("SELECT ID, ACCEPTED, DATEOFFERED, CALLID, ANI, DNIS, SURVEYID, LANG FROM SURVEYUSERRESPONSE ") 
				.append("WHERE CALLID = '").append(callId)
				.append("' ORDER BY DATEOFFERED DESC").toString();
				logger.debug("[SurveyOfferDAO1] getUserResponse SQL: " + sql);
			}
			else if (DNIS != null && ANI == null) {
				sql = new StringBuffer("SELECT TOP 1 ID, ACCEPTED, DATEOFFERED, CALLID, ANI, DNIS, SURVEYID FROM SURVEYUSERRESPONSE ") 
				.append("WHERE DNIS = '").append(DNIS).append("' AND ANI IS NULL AND CALLID IS NULL ").append("AND CUSTOMERID = '").append(customerId)
				.append("' ORDER BY DATEOFFERED DESC").toString();
				logger.debug("[SurveyOfferDAO1] getUserResponse SQL: " + sql);
			}
			// According to the CustomSurveyMenu this if should match
			else if(DNIS == null && ANI != null && callId == null && customerId == null ) {
				sql = new StringBuffer("SELECT TOP 1 ID, ACCEPTED, DATEOFFERED, CALLID, ANI, DNIS, SURVEYID, LANG FROM SURVEYUSERRESPONSE ") 
				.append("WHERE ANI = '").append(ANI).append("' ORDER BY DATEOFFERED DESC").toString();
				logger.debug("[SurveyOfferDAO] getUserResponse SQL: " + sql);
			}
			else {
				sql = new StringBuffer("SELECT TOP 1 ID, ACCEPTED, DATEOFFERED, CALLID, ANI, DNIS, SURVEYID FROM SURVEYUSERRESPONSE ") 
				.append("WHERE DNIS = '").append(DNIS).append("' AND CUSTOMERID = '").append(customerId)
				.append("' ORDER BY DATEOFFERED DESC").toString();
				logger.debug("[SurveyOfferDAO1] getUserResponse SQL: " + sql);
			}

			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 
			rset = stmt.executeQuery(sql);

			responseMap = new HashMap<String, String>();
			if (rset.next()) {
				responseMap.put("RESPONSEID", "" + rset.getInt("ID"));
				responseMap.put("ACCEPTED", "" + rset.getInt("ACCEPTED"));
				responseMap.put("LASTDATEOFFERED", rset.getString("DATEOFFERED"));
				responseMap.put("CALLID", rset.getString("CALLID"));
				responseMap.put("ANI", rset.getString("ANI"));
				responseMap.put("DNIS", rset.getString("DNIS"));
				responseMap.put("SURVEYID", rset.getString("SURVEYID"));
				responseMap.put("LANG", rset.getString("LANG"));
			}
			logger.debug("[SurveyOfferDAO] responseMap size is "+responseMap.size() );
		}
		catch (SQLException sqle) {
//			System.out.println("SQL Exception getUserResponse: " + sqle.getLocalizedMessage());
			logger.error("[SurveyOfferDAO1] SQL Exception getUserResponse: " + sqle.getMessage());
			sqle.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("Exception getUserResponse: " + e.getLocalizedMessage());
			logger.error("[SurveyOfferDAO1] Exception getUserResponse: " + e.getLocalizedMessage());
		}
		finally {
			close(rset);
			close(stmt);
			close(conn);
		}
		return responseMap;
	}

	/**
	 * Retrieve all data from survey table. Includes the DNISs attached to each survey
	 * This should connect to the Ops Console B database.
	 * @return ArrayList<SurveySettingsBean>
	 */
	public Map<String, String> getOnDemandUserResponse(int pResponseId ) {
		Map<String,String> responseMap = null;
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		String sql = "";

		try {
			sql = new StringBuffer("SELECT TOP 1 ID, ACCEPTED, DATEOFFERED, CALLID, ANI, DNIS, SURVEYID FROM SURVEYUSERRESPONSE ") 
			.append("WHERE ID = ").append(pResponseId).append(" ")
			.append("ORDER BY DATEOFFERED DESC").toString();

			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 
			rset = stmt.executeQuery(sql);

			responseMap = new HashMap<String, String>();
			if (rset.next()) {
				responseMap.put("RESPONSEID", "" + rset.getInt("ID"));
				responseMap.put("ACCEPTED", "" + rset.getInt("ACCEPTED"));
				responseMap.put("LASTDATEOFFERED", rset.getString("DATEOFFERED"));
				responseMap.put("CALLID", rset.getString("CALLID"));
				responseMap.put("ANI", rset.getString("ANI"));
				responseMap.put("DNIS", rset.getString("DNIS"));
				responseMap.put("SURVEYID", rset.getString("SURVEYID"));
			}

		}
		catch (SQLException sqle) {
//			System.out.println("SQL Exception getUserResponse: " + sqle.getLocalizedMessage());
			logger.error("[SurveyOfferDAO1] SQL Exception getUserResponse: " + sqle.getMessage());
			sqle.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("Exception getUserResponse: " + e.getLocalizedMessage());
			logger.error("[SurveyOfferDAO1] Exception getUserResponse: " + e.getLocalizedMessage());
		}
		finally {
			close(rset);
			close(stmt);
			close(conn);
		}
		return responseMap;
	}

	/**
	 * It will get the user's survey details by giving the callId
	 * @param callId
	 * @return
	 */
	public Map<String, String> getUserResponse(String callId ) {
		return getUserResponse(null, null, callId, null);
	}

	/**
	 * Used by On Demand by giving DNIS only. It will get the user's survey detail.	
	 * @param DNIS
	 * @return
	 */
	public Map<String, String> getOnDemandUserResponse(String DNIS, String ANI ) {
		return getUserResponse(DNIS, ANI, null, null);
	}

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
	 * Save the user's response manually if on-demand. 
	 * This should connect to the Ops Console B database.
	 * @return responseId - number of rows inserted/updated
	 */
	public int setOnDemandUserResponse(String DNIS, String ANI,String customerId, String callId,String routerCallKey,String routerCallKeyDay) {
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		String sql = "";
		int rows = 0;

		int accepted = 1;
		int surveyId = 0;
		int responseId = 0;

		try {
			sql = new StringBuffer("SELECT DISTINCT A.ID SURVEYID FROM SURVEY AS A, SURVEYDNIS AS B ")
			.append("WHERE A.ID = B.SURVEYID AND A.OFFERDEMAND = 1 AND A.STATUS = 'active' AND GETDATE() > A.STARTDATE AND GETDATE() < A.ENDDATE ")
			.append("AND B.DNIS = '").append(DNIS).append("' ")
			.append("ORDER BY A.ID DESC ")
			.toString();

			conn = DBConnectUtil.getConnection(connectionString);
			stmt = conn.createStatement(); 
			rset = stmt.executeQuery(sql);

			if (rset.next()) {
				surveyId = rset.getInt("SURVEYID");
			}


			if (surveyId > 0) {
				//sql = new StringBuffer("INSERT INTO SURVEYUSERRESPONSE (DNIS, ANI, ACCEPTED, SURVEYID, DATEOFFERED) ")
				//.append("VALUES('").append(DNIS).append("','").append(ANI).append("',").append(accepted).append(", ").append(surveyId).append(", getDate()) ")
				//.toString();

				// Log customer id only if we have it.
				if (customerId == null) {
					sql = new StringBuffer("INSERT INTO SURVEYUSERRESPONSE (DNIS, ANI, ACCEPTED, SURVEYID, DATEOFFERED," +
					" CALLID, ROUTERCALLKEY, ROUTERCALLKEYDAY) ")
					.append("VALUES('").append(DNIS).append("','").append(ANI).append("',").append(accepted).append(", ").append(surveyId).append(", getDate(), '")
					.append(callId).append("','").append(routerCallKey).append("','").append(routerCallKeyDay).append("')")
					.toString();

				} else{
					sql = new StringBuffer("INSERT INTO SURVEYUSERRESPONSE (DNIS, ANI, ACCEPTED, SURVEYID, DATEOFFERED," +
					" CALLID, CUSTOMERID, ROUTERCALLKEY, ROUTERCALLKEYDAY) ")
					.append("VALUES('").append(DNIS).append("','").append(ANI).append("',").append(accepted).append(", ").append(surveyId).append(", getDate(), '")
					.append(callId).append("','").append(customerId).append("','").append(routerCallKey).append("','").append(routerCallKeyDay).append("')")
					.toString();
				}

				rows = stmt.executeUpdate(sql);

				sql = "";
				sql = new StringBuffer("SELECT TOP 1 ID, DATEOFFERED FROM SURVEYUSERRESPONSE ")
				.append("WHERE DNIS = '").append(DNIS).append("' AND ANI = '").append(ANI).append("' AND ACCEPTED = ").append(accepted)
				.append(" AND SURVEYID =  ").append(surveyId).append(" ORDER BY DATEOFFERED DESC ")
				.toString();

				rset = stmt.executeQuery(sql);

				if (rset.next()) {
					responseId = rset.getInt("ID");
				}
			}
		}
		catch (SQLException sqle) {
//			System.out.println("SQL Exception setOnDemandUserResponse: " + sqle.getMessage());
			logger.error("[SurveyOfferDAO1] SQL Exception setOnDemandUserResponse: " + sqle.getMessage());
		}
		catch (Exception e) {
//			System.out.println("Exception setOnDemandUserResponse: " + e.getMessage());
			logger.error("[SurveyOfferDAO1] Exception setOnDemandUserResponse: " + e.getMessage());
		}
		finally {
			close(rset);
			close(stmt);
			close(conn);
		}
		return responseId;
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

