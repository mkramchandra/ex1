package com.ss.ivr.survey.mainivr;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.audium.server.AudiumException;
import com.audium.server.session.ActionElementData;
import com.ss.ivr.survey.base.BaseActionElement;
import com.ss.ivr.survey.util.SurveyQuestion;
import com.ss.ivr.survey.util.SurveyQuestionBean;


public class LoadIndividualQuestion extends BaseActionElement {
	static Long current= System.currentTimeMillis();
	protected static Logger logger = Logger.getLogger(LoadIndividualQuestion.class.getName());
	  static public synchronized String getFileName(){
		  Random rn = new Random();
		  Integer long1 = rn.nextInt(99);
		  String random = current.toString() + long1.toString();
		  return random;
	  }
	public void doAction(String name, ActionElementData actionData) {	
		//super.doAction(name,actionData);
		logger.debug("[LoadIndividualQuestions]entering LoadINdividual questions");
		Integer currentQuestionID = null;
		Integer nextQuestionYes = null;
		Integer nextQuestionNo = null;

		Integer current = new Integer((String) actionData.getSessionData("Survey_Sess_CurrentQuestion"));
		current--;
		List questions = (List)actionData.getSessionData("Survey_Sess_QuestionsList");
		if(logger.isDebugEnabled()) {
			logger.debug("[LoadIndividualQuestion] question size: " + questions.size() + "Survey_Sess_CurrentQuestion: " + current);
		}
		
		//check if the index of the current question is greater than the number of questions.
		if (current < questions.size()) { 
			//currentQuestionID = new Integer(((SurveyQuestion)questions.get(current)).getQuestionId());
			//nextQuestionYes = new Integer(((SurveyQuestionBean)questions.get(current)).getNextQuestionYes());
			//nextQuestionNo = new Integer(((SurveyQuestionBean)questions.get(current)).getNextQuestionNo());
			try {
				//actionData.setSessionData("Survey_QuestionAudio",((SurveyQuestionBean)questions.get(current)).getAudioFile());
				actionData.setSessionData("Survey_QuestionTTS",((SurveyQuestion)questions.get(current)).getTts());
				actionData.setSessionData("Survey_QuestionType",((SurveyQuestion)questions.get(current)).getType());
				//actionData.setSessionData("Survey_QuestionID",currentQuestionID);
				//actionData.setSessionData("Survey_NextQuestionYes",nextQuestionYes);
				//actionData.setSessionData("Survey_NextQuestionNo",nextQuestionNo);
				//actionData.setSessionData("Survey_Sess_CurrentQuestion",current);
			} catch (AudiumException e) {
				logger.error("[LoadIndividualQuestion] error setting current question info",e);
			}
		}
		
		if((Integer)actionData.getSessionData("Survey_QuestionType") == 4){
			try {
				actionData.setSessionData("Survey_RecordedFile", LoadIndividualQuestion.getFileName());
				if(logger.isDebugEnabled()){
					logger.debug("[LoadIndividualQuestion] Recorded file name " + (String)actionData.getSessionData("Survey_Sess_RecordedFile"));
				}
			} catch (AudiumException e) {
				logger.error("[LoadIndividualQuestion] error setting recorded filename. "+e);
			}
		}
				
		if(logger.isDebugEnabled()) {
			logger.debug("[LoadIndividualQuestion]Currentaudio - " + actionData.getSessionData("Survey_QuestionAudio"));
			logger.debug("[LoadIndividualQuestion]CurrentTTS - " + actionData.getSessionData("Survey_QuestionTTS"));
			logger.debug("[LoadIndividualQuestion]CurrentType - " +actionData.getSessionData("Survey_QuestionType"));
			logger.debug("[LoadIndividualQuestion]CurrentID - " +actionData.getSessionData("Survey_QuestionID"));
			logger.debug("[LoadIndividualQuestion]NextQuestionYes - " +actionData.getSessionData("Survey_NextQuestionYes"));
			logger.debug("[LoadIndividualQuestion]NextQuestionNo - " +actionData.getSessionData("Survey_NextQuestionNo"));
		}
			
	}
	
	public static void main(String [] args) {
		System.out.println(LoadIndividualQuestion.getFileName());
	}

}

