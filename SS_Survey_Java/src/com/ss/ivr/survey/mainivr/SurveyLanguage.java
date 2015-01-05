package com.ss.ivr.survey.mainivr;

import java.util.Map;

import com.audium.server.AudiumException;
import com.audium.server.session.ActionElementData;
import com.ss.ivr.survey.dao.SurveyOfferDAO;
import com.ss.ivr.survey.util.BusinessConstants;
import com.ss.ivr.survey.base.BaseActionElement;



public class SurveyLanguage extends BaseActionElement {
	

	
	public void doAction(String name, ActionElementData actionData) {	
		//super.doAction(name,actionData);
		
		logger.debug("[SurveyLanguage] enter ");
		Map response = null;
			String callID = (String)actionData.getSessionData("callid");
			// regular survey
			logger.debug("[SurveyLanguage] getting survey ID for call ["+callID+"]");
			logger.debug("[SurveyLanguage] survey URL is ["+(String)actionData.getSessionData("db_URL")+"]");
			//response = new SurveyOfferDAO((String)actionData.getSessionData("db_URL")).getUserResponse(callID);
		
		try {
			//for now set Language to english 
			//actionData.setSessionData("Survey_Lang",response.get("LANG"));
			actionData.setSessionData("Survey_Lang", "en-US");
			
			String audioPath = (String)actionData.getSessionData(BusinessConstants.AppAudioPath)+"/"+"en-US"+"/survey";
			actionData.setDefaultAudioPath(audioPath);
		} 
		catch (AudiumException e) {
			logger.error("[SurveyLanguage] AudiumException in setting survey id and response id in vxml session. ", e);
			e.printStackTrace();
		}

		logger.debug("[SurveyLanguage] Survey_Sess_Lang: ["+(String)actionData.getSessionData("Survey_Lang")+"]");
		
	}

}

