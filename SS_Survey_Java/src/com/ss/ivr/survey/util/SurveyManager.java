/**
 * 
 */
package com.ss.ivr.survey.util;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ss.ivr.survey.dao.SurveyOfferDAO;


/**
 * @author 
 *
 */
public class SurveyManager {

	private static final Logger logger = Logger.getLogger(SurveyManager.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int surveyId = 0;
		SurveyManager sm = new SurveyManager();
//		surveyId = sm.getSurveyOffer("9997939875", "4696842829", null, "TS", 20);
//		System.out.println("Survey ID: " + surveyId);
		
//		System.out.println("CALL GUID - Surey id: " + SurveyManager.getSurveyIdFromUserResponse("1234567890"));

//		System.out.println(SurveyManager.checkFEOffered("12345"));
	}

/////////////////////////////////////////////////////////////////
	
	
	/**
	 * returns int array survey id and response id
	 */
	public synchronized static int[] getSurveyIdFromUserResponse(String callGUID, String URL) {
		String accepted = "";
		String surveyId = "";
		String responseId = "";
		int retSurveyId = 0;
		int retResponseId = 0;
		int[] ret = new int[2];
		Map<String, String> userResponse = null;
		userResponse = new SurveyOfferDAO(URL).getUserResponse(callGUID);
		accepted = (String)userResponse.get("ACCEPTED");
		surveyId = (String)userResponse.get("SURVEYID");
		responseId = (String)userResponse.get("RESPONSEID");

		if (accepted != null && !"".equals(accepted.trim()) 
				&& !"0".equals(accepted.trim())) { 
			try {
				retSurveyId = Integer.parseInt(surveyId);
				retResponseId = Integer.parseInt(responseId);
				ret[0] = retSurveyId;
				ret[1] = retResponseId;
			}
			catch (Exception ee) {
				ret[0] = 0;
				ret[1] = 0;
				logger.error("[SurveyManager] getSurveyIdFromUserResponse Exception: " + ee.getMessage());
			}
		}
		return ret;
	}
	
	/**
	 * returns int array survey id and response id
	 * @param DNIS, ANI
	 * @return
	 */
	public synchronized static int[] getOnDemandSurveyId(String DNIS, String ANI) {
		String accepted = "";
		String surveyId = "";
		String responseId = "";
		int retSurveyId = 0;
		int retResponseId = 0;
		int[] ret = new int[2];
		Map<String, String> userResponse = null;
		//userResponse = new SurveyOfferDAO().getOnDemandUserResponse(DNIS, ANI);
//		System.out.println("[SurveyManager]  userReponse Map: "+ userResponse);
		logger.debug("[SurveyManager] userReponse Map: "+ userResponse);
		if (userResponse == null) {
			ret[0] = 0;
			ret[1] = 0;
			return ret;
		}
		accepted = (String)userResponse.get("ACCEPTED");
		surveyId = (String)userResponse.get("SURVEYID");
		responseId = (String)userResponse.get("RESPONSEID");
		
		if (accepted != null && !"".equals(accepted.trim())) {
			try {
				retSurveyId = new Integer(surveyId).intValue();
				retResponseId = new Integer(responseId).intValue();
				ret[0] = retSurveyId;
				ret[1] = retResponseId;
			}
			catch (Exception ee) {
				ret[0] = 0;
				ret[1] = 0;
				logger.error("[SurveyManager] getOnDemandSurveyId Exception: " + ee.getMessage());
			}
		}
		return ret;
	}
	
	/**
	 * returns int array survey id and response id
	 * @param response Id
	 * @return
	 */
	public synchronized static int[] getOnDemandSurveyId(int pResponseId) {
		String accepted = "";
		String surveyId = "";
		String responseId = "";
		int retSurveyId = 0;
		int retResponseId = 0;
		int[] ret = new int[2];
		Map<String, String> userResponse = null;
//		userResponse = new SurveyOfferDAO().getOnDemandUserResponse(DNIS, ANI);
		//userResponse = new SurveyOfferDAO().getOnDemandUserResponse(pResponseId);
		logger.debug("[SurveyManager] userReponse Map: "+ userResponse);
		if (userResponse == null) {
			ret[0] = 0;
			ret[1] = 0;
			return ret;
		}
		accepted = (String)userResponse.get("ACCEPTED");
		surveyId = (String)userResponse.get("SURVEYID");
		responseId = (String)userResponse.get("RESPONSEID");
		
		if (accepted != null && !"".equals(accepted.trim())) {
			try {
				retSurveyId = new Integer(surveyId).intValue();
				retResponseId = new Integer(responseId).intValue();
				ret[0] = retSurveyId;
				ret[1] = retResponseId;
			}
			catch (Exception ee) {
				ret[0] = 0;
				ret[1] = 0;
				logger.error("[SurveyManager] getOnDemandSurveyId(responseId) Exception: " + ee.getMessage());
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * @param surveyId
	 * @return List of SurveyQuestionBean that contains the questions to be asked. 
	 */
	public List<SurveyQuestionBean> getSurveyQuestions(int surveyId, String db_URL) {
		List<SurveyQuestionBean> surveyQuestions = null;
		surveyQuestions = new SurveyResponseDAO(db_URL).getSurveyQuestions(surveyId);
		return surveyQuestions;
	}
	
	
}
