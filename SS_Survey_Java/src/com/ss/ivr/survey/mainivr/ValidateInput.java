package com.ss.ivr.survey.mainivr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.audium.server.AudiumException;
import com.audium.server.session.ActionElementData;
import com.audium.server.session.DecisionElementData;
import com.ss.ivr.survey.base.BaseActionElement;

public class ValidateInput extends BaseActionElement {
	protected static Logger logger = Logger.getLogger(ValidateInput.class
			.getName());

	public void doAction(String name, ActionElementData actionData) {
		logger.debug("[ValidateInput] validate input class");
		final String valid = "valid";
		final String invalid = "invalid";
		final String noAnswer = "NoAnswer";

		String questionType = (String) actionData
				.getSessionData("Survey_QuestionType");
		
		String value = (String) actionData.getElementData("SS_Survey_Menu",
				"value");
		String lastState = (String) actionData.getExitStateHistory()
				.lastElement();
		logger.debug(" [ValidateInput] currentValue: " + value);
		String inputValid = invalid;
		logger.debug(" [ValidateInput] currentQuestion: "
				+ actionData.getSessionData("Survey_Sess_CurrentQuestion"));
		logger.debug(" [ValidateInput] currentValue: " + value);
		logger.debug(" [ValidateInput] currentState: " + lastState);
		Integer nextYes = (Integer) actionData
				.getSessionData("Survey_Sess_NextQuestionYes");
		Integer nextNo = (Integer) actionData
				.getSessionData("Survey_Sess_NextQuestionNo");
		Integer nextQuestion = new Integer(0);

		logger.debug(" [ValidateInput] nextYes --> " + nextYes);
		logger.debug(" [ValidateInput] nextNo --> " + nextNo);

		if (questionType.equalsIgnoreCase("Message")) {
			logger.debug("[ValidateInput] entered Survey Start");
			inputValid = valid;
			value = "0";
			nextQuestion = nextYes;
		}
		else if (questionType.equalsIgnoreCase("Yes/No")) {
			value = (String) actionData.getElementData("SS_Survey_2_Menu",
					"value");
			logger.debug("[ValidateInput] entered Yes/No");
			if (value != null) {
				if (value.equalsIgnoreCase("1")) {
					inputValid = valid;
					value = "Yes";
					nextQuestion = nextYes;
				} else if (value.equalsIgnoreCase("2")) {
					inputValid = valid;
					value = "No";
					nextQuestion = nextNo;
				} else {
					inputValid = valid;
					nextQuestion = nextYes;
					value = noAnswer;
				}
			} else {
				inputValid = valid;
				nextQuestion = nextYes;
				value = noAnswer;
			}
		}
		else if (questionType.equalsIgnoreCase("Record Message")) {
			logger.debug("[ValidateInput] entered RecordMessage");
			inputValid = valid;
			value = (String) actionData
					.getSessionData("Survey_Sess_RecordedFile")
					+ ".wav";
			nextQuestion = nextYes;
		} 
		else if (lastState.equalsIgnoreCase("max_noinput")
				|| lastState.equalsIgnoreCase("max_nomatch")) {
			logger.debug("[ValidateInput] entered NMNI");
			inputValid = valid;
			value = noAnswer;
			nextQuestion = nextYes;

		} else {
			inputValid = valid;
			nextQuestion = nextYes;

			// one to five question type
			if (value != null) {
				if (value.equalsIgnoreCase("1")) {
					value = "1";
					// value = "one";
				} else if (value.equalsIgnoreCase("2")) {
					value = "2";
					// value = "two";
				} else if (value.equalsIgnoreCase("3")) {
					value = "3";
					// value = "three";
				} else if (value.equalsIgnoreCase("4")) {
					value = "4";
					// value = "four";
				} else if (value.equalsIgnoreCase("5")) {
					value = "5";
					// value = "five";
				} else {
					value = noAnswer;
				}
			} else {
				value = noAnswer;
			}
		}
		
		if (inputValid.equalsIgnoreCase("valid")) {
			List surveyAnswers = new ArrayList();
			Map<String, String> hm = new HashMap<String, String>();
			if (actionData.getSessionData("Survey_Sess_AnswersList") != null) {
				surveyAnswers = (List) actionData
						.getSessionData("Survey_Sess_AnswersList");
			}

			// don't record the input for start and end audio
			logger
					.debug(" [ValidateInput]   Survey_Sess_CurrentType > "
							+ questionType);
			if (!questionType.equals("Message")) {
				hm.put("ReponseId", (String) actionData
						.getSessionData("Survey_Sess_ResponseID"));
				Integer questionId = (Integer) actionData
						.getSessionData("Survey_Sess_CurrentID");
				hm.put("QuestionId", questionId.toString());
				hm.put("Answer", value);
				Date today = new Date();
				hm.put("DateAnswered", today.toString());

				surveyAnswers.add(hm);
			}

			logger.debug(" [ValidateInput]  surveyAnswers > " + surveyAnswers);

			try {
				actionData.setSessionData("Survey_Sess_AnswersList",
						surveyAnswers);
				actionData.setSessionData("Survey_Sess_CurrentQuestion",
						nextQuestion.toString());
				logger.debug("[ValidateInput] setting nextQuestion value "
						+ nextQuestion);
			} catch (AudiumException e) {
				logger.error(" [ValidateInput] ", e);
			}

		}

	}

}
