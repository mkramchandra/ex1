package com.ss.ivr.survey.mainivr;

import java.util.List;

import com.audium.server.session.DecisionElementData;
import com.ss.ivr.survey.base.BaseDecisionElement;
import com.ss.ivr.survey.util.SurveyManager;


public class LoadSurvey extends BaseDecisionElement {
	public String doDecision(String name, DecisionElementData decisionData) {
			
		String db_URL = (String)decisionData.getSessionData("db_URL");
		
		try {
			Integer surveyId = new Integer((String) decisionData.getSessionData("Survey_SurveyID"));
			if(logger.isDebugEnabled()) {
				logger.debug("[LoadQuestions] Survey_Sess_SurveyID: "+surveyId);
			}
			SurveyManager mySurveyMgr = new SurveyManager();
			List questions = (List)mySurveyMgr.getSurveyQuestions(surveyId.intValue(), db_URL);
			Integer totalQuestions = new Integer(questions.size());
			
			// need to check the size of the survey here
			if (totalQuestions<1){
				// no questions
				if(logger.isDebugEnabled()) {
					logger.debug("[LoadQuestions] no questions are assigned to survey id " + surveyId);
				}
				return "false";
			}
			
			decisionData.setSessionData("Survey_Sess_TotalQuestions",totalQuestions.toString());
			if(logger.isDebugEnabled())
				logger.debug("[LoadQuestions] set Survey_Sess_TotalQuestions ["+totalQuestions.toString()+"]");
			decisionData.setSessionData("Survey_Sess_CurrentQuestion","1");
			if(logger.isDebugEnabled())
				logger.debug("[LoadQuestions] set Survey_Sess_CurrentQuestion");
			decisionData.setSessionData("Survey_Sess_QuestionsList",questions);
			if(logger.isDebugEnabled())
				logger.debug("[LoadQuestions] set Survey_Sess_QuestionsList");
		}
		catch (Exception e) {
			logger.error("[LoadQuestions] EXCEPTION",e);
		}
		return "true";
	}
	
}
