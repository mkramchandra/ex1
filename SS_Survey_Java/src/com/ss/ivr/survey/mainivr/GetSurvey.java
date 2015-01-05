package com.ss.ivr.survey.mainivr;

import com.audium.server.AudiumException;
import com.audium.server.session.ActionElementData;
import com.ss.ivr.survey.base.BaseActionElement;
import com.ss.ivr.survey.util.SurveyManager;



public class GetSurvey extends BaseActionElement {
	
	public void doAction(String name, ActionElementData actionData) {	
		
		// Read the CallID from the session variables set in OncallStart
		// This value is the unique call value from ICM media.id
		// Retireve the session ID using callid and set it into the session
//		System.out.println("[GetSurvey] enter ");
		if(logger.isDebugEnabled()) {
			logger.debug("[GetSurvey] enter ");
		}
		
		String callID = (String)actionData.getSessionData("callid");
			// regular survey
		if(logger.isDebugEnabled()) {
			logger.debug("[GetSurvey] getting survey ID for call ["+callID+"]");
			logger.debug("[GetSurvey] survey URL is ["+(String)actionData.getSessionData("db_URL")+"]");
		}
		//response = SurveyManager.getSurveyIdFromUserResponse(callID,(String)actionData.getSessionData("db_URL"));
		
		//Write code here based on the DNIS get SurveyID
		//for now surveyID = 1
		int surveyID = 1;

		try {
			actionData.setSessionData("SurveyID",surveyID);
		} 
		catch (AudiumException e) {
			logger.error("[GetSurvey] AudiumException in setting survey id and response id in vxml session. ", e);
		}
		if(logger.isDebugEnabled()) {
			logger.debug("[GetSurvey] Survey_SurveyID: ["+surveyID+"]");
		}
	}

}

