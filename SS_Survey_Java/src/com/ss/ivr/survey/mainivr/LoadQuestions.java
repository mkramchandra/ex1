package com.ss.ivr.survey.mainivr;

import java.util.ArrayList;
import java.util.List;

import com.audium.server.session.DecisionElementData;
import com.ss.ivr.survey.base.BaseDecisionElement;
import com.ss.ivr.survey.dao.SurveyDAO;
import com.ss.ivr.survey.util.SurveyBean;
import com.ss.ivr.survey.util.SurveyManager;
import com.ss.ivr.survey.util.SurveyQuestion;


public class LoadQuestions extends BaseDecisionElement {
	public String doDecision(String name, DecisionElementData decisionData) {
			
		String db_URL = (String)decisionData.getSessionData("db_URL");
		
		try {
			Integer surveyId = new Integer((String) decisionData.getSessionData("Survey_SurveyID"));
			if(logger.isDebugEnabled()) {
				logger.debug("[LoadQuestions] Survey_Sess_SurveyID: "+surveyId);
			}
			SurveyDAO sdao = new SurveyDAO(db_URL);
			SurveyBean sBean = sdao.getSurvey(surveyId);
			List<SurveyQuestion> surveyQuestions = new ArrayList<SurveyQuestion>();
			SurveyQuestion sq = null;
			if(sBean.getQ1Type()!= null) {
				sq = new SurveyQuestion();
				sq.setType(sBean.getQ1Type());
				sq.setTts(sBean.getQ1Prompt());
				surveyQuestions.add(sq);
			}
			if(sBean.getQ2Type()!= null) {
				sq = new SurveyQuestion();
				sq.setType(sBean.getQ2Type());
				sq.setTts(sBean.getQ2Prompt());
				surveyQuestions.add(sq);
			}
			if(sBean.getQ3Type()!= null) {
				sq = new SurveyQuestion();
				sq.setType(sBean.getQ3Type());
				sq.setTts(sBean.getQ3Prompt());
				surveyQuestions.add(sq);
			}
			if(sBean.getQ4Type()!= null) {
				sq = new SurveyQuestion();
				sq.setType(sBean.getQ4Type());
				sq.setTts(sBean.getQ4Prompt());
				surveyQuestions.add(sq);
			}
			if(sBean.getQ5Type()!= null) {
				sq = new SurveyQuestion();
				sq.setType(sBean.getQ5Type());
				sq.setTts(sBean.getQ5Prompt());
				surveyQuestions.add(sq);
			}
						
			Integer totalQuestions = new Integer(surveyQuestions.size());
			
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
			decisionData.setSessionData("Survey_Sess_QuestionsList",surveyQuestions);
			if(logger.isDebugEnabled())
				logger.debug("[LoadQuestions] set Survey_Sess_QuestionsList");
		}
		catch (Exception e) {
			logger.error("[LoadQuestions] EXCEPTION",e);
		}
		return "true";
	}
	
}
