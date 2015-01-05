package com.ss.ivr.survey.util;

public class SurveyResponseBean {

	private int reponseId;
	private int questionId;
	private String answer = "";
	private String dateAnswered = "";

	public SurveyResponseBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the reponseId
	 */
	public int getReponseId() {
		return reponseId;
	}
	/**
	 * @param reponseId the reponseId to set
	 */
	public void setReponseId(int reponseId) {
		this.reponseId = reponseId;
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
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * @return the dateAnswered
	 */
	public String getDateAnswered() {
		return dateAnswered;
	}
	/**
	 * @param dateAnswered the dateAnswered to set
	 */
	public void setDateAnswered(String dateAnswered) {
		this.dateAnswered = dateAnswered;
	}
	
	
}
