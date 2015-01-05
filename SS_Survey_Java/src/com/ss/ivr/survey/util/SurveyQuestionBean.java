/**
 * 
 */
package com.ss.ivr.survey.util;

/**
 * @author Jasper
 *
 */
public class SurveyQuestionBean {

	private int surveyId;
	private int questionId;
	private int sequenceNum;
	private String question;
	private String audioFile;
	private int questionTypeId;
	private String questionType;
	private int nextQuestionYes;
	private int nextQuestionNo;
	private int type;
	
	public SurveyQuestionBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the surveyId
	 */
	public int getSurveyId() {
		return surveyId;
	}

	/**
	 * @param surveyId the surveyId to set
	 */
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	/**
	 * @return the questionId
	 */
	public int getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the sequenceNum
	 */
	public int getSequenceNum() {
		return sequenceNum;
	}

	/**
	 * @param sequenceNum the sequenceNum to set
	 */
	public void setSequenceNum(int sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the audioFile
	 */
	public String getAudioFile() {
		return audioFile;
	}

	/**
	 * @param audioFile the audioFile to set
	 */
	public void setAudioFile(String audioFile) {
		this.audioFile = audioFile;
	}

	/**
	 * @return the questionTypeId
	 */
	public int getQuestionTypeId() {
		return questionTypeId;
	}

	/**
	 * @param questionTypeId the questionTypeId to set
	 */
	public void setQuestionTypeId(int questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	/**
	 * @return the questionType
	 */
	public String getQuestionType() {
		return questionType;
	}

	/**
	 * @param questionType the questionType to set
	 */
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	/**
	 * @return the nextQuestionYes
	 */
	public int getNextQuestionYes() {
		return nextQuestionYes;
	}

	/**
	 * @param nextQuestionYes the nextQuestionYes to set
	 */
	public void setNextQuestionYes(int nextQuestionYes) {
		this.nextQuestionYes = nextQuestionYes;
	}

	/**
	 * @return the nextQuestionNo
	 */
	public int getNextQuestionNo() {
		return nextQuestionNo;
	}

	/**
	 * @param nextQuestionNo the nextQuestionNo to set
	 */
	public void setNextQuestionNo(int nextQuestionNo) {
		this.nextQuestionNo = nextQuestionNo;
	}


	
}
