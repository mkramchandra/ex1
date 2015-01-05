package com.ss.ivr.survey.util;

import java.util.ArrayList;
import java.util.List;

public class SurveySettingsBean {

	private int surveyId;
	private String surveyName;
	private int fe;
	private int ts;
	private int od;
//	private String dnis;
	private int callInterval;
	private int acceptedDaysInterval;
	private int declinedDaysInterval;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
//	private int masterSwitch;
//	private ArrayList<String> dnisList;
	private List<String[]> surveyTime;
	private String status;
	


	public SurveySettingsBean() {

	}
	
	public SurveySettingsBean(int surveyId, String surveyName, int fe, int ts, int od,
			String dnis, int callInterval, int acceptedDaysInterval,
			int declinedDaysInterval, String startDate, String endDate,
			String startTime, String endTime) {
		super();
		this.surveyId = surveyId;
		this.surveyName = surveyName;
		this.fe = fe;
		this.ts = ts;
		this.od = od;
//		this.dnis = dnis;
		this.callInterval = callInterval;
		this.acceptedDaysInterval = acceptedDaysInterval;
		this.declinedDaysInterval = declinedDaysInterval;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
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
	 * @return the surveyName
	 */
	public String getSurveyName() {
		return surveyName;
	}
	/**
	 * @param surveyName the surveyName to set
	 */
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}
	/**
	 * @return the fe
	 */
	public int getFe() {
		return fe;
	}
	/**
	 * @param fe the fe to set
	 */
	public void setFe(int fe) {
		this.fe = fe;
	}
	/**
	 * @return the ts
	 */
	public int getTs() {
		return ts;
	}
	/**
	 * @param ts the ts to set
	 */
	public void setTs(int ts) {
		this.ts = ts;
	}
	/**
	 * @return the od
	 */
	public int getOd() {
		return od;
	}
	/**
	 * @param od the od to set
	 */
	public void setOd(int od) {
		this.od = od;
	}
	/**
	 * 
	 * @return dnis assigned to this survey
	 */
//	public String getDnis() {
//		return dnis;
//	}
	/**
	 * @param dnis the dnis to set
	 */
//	public void setDnis(String dnis) {
//		this.dnis = dnis;
//	}
	/**
	 * @return the callInterval
	 */
	public int getCallInterval() {
		return callInterval;
	}
	/**
	 * @param callInterval the callInterval to set
	 */
	public void setCallInterval(int callInterval) {
		this.callInterval = callInterval;
	}
	/**
	 * @return the acceptedDaysInterval
	 */
	public int getAcceptedDaysInterval() {
		return acceptedDaysInterval;
	}
	/**
	 * @param acceptedDaysInterval the acceptedDaysInterval to set
	 */
	public void setAcceptedDaysInterval(int acceptedDaysInterval) {
		this.acceptedDaysInterval = acceptedDaysInterval;
	}
	/**
	 * @return the declinedDaysInterval
	 */
	public int getDeclinedDaysInterval() {
		return declinedDaysInterval;
	}
	/**
	 * @param declinedDaysInterval the declinedDaysInterval to set
	 */
	public void setDeclinedDaysInterval(int declinedDaysInterval) {
		this.declinedDaysInterval = declinedDaysInterval;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
		
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}


	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * @return the surveyTime
	 */
	public List<String[]> getSurveyTime() {
		return surveyTime;
	}

	/**
	 * @param surveyTime the surveyTime to set
	 */
	public void setSurveyTime(List<String[]> surveyTime) {
		this.surveyTime = surveyTime;
	}

	/**
	 * @return the masterSwitch
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param masterSwitch the masterSwitch to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
