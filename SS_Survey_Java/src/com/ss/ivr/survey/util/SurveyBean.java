package com.ss.ivr.survey.util;

import java.util.Date;

public class SurveyBean {
	
	private int id;
	private String name;
	private String description;
	private boolean active;
	private Date startDate;
	private Date endDate;
	private Integer q1Type;
	private String q1Prompt;
	private Integer q2Type;
	private String q2Prompt;
	private Integer q3Type;
	private String q3Prompt;
	private Integer q4Type;
	private String q4Prompt;
	private Integer q5Type;
	private String q5Prompt;
	
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQ1Prompt() {
		return q1Prompt;
	}
	public void setQ1Prompt(String prompt) {
		q1Prompt = prompt;
	}
	public Integer getQ1Type() {
		return q1Type;
	}
	public void setQ1Type(Integer type) {
		q1Type = type;
	}
	public String getQ2Prompt() {
		return q2Prompt;
	}
	public void setQ2Prompt(String prompt) {
		q2Prompt = prompt;
	}
	public Integer getQ2Type() {
		return q2Type;
	}
	public void setQ2Type(Integer type) {
		q2Type = type;
	}
	public String getQ3Prompt() {
		return q3Prompt;
	}
	public void setQ3Prompt(String prompt) {
		q3Prompt = prompt;
	}
	public Integer getQ3Type() {
		return q3Type;
	}
	public void setQ3Type(Integer type) {
		q3Type = type;
	}
	public String getQ4Prompt() {
		return q4Prompt;
	}
	public void setQ4Prompt(String prompt) {
		q4Prompt = prompt;
	}
	public Integer getQ4Type() {
		return q4Type;
	}
	public void setQ4Type(Integer type) {
		q4Type = type;
	}
	public String getQ5Prompt() {
		return q5Prompt;
	}
	public void setQ5Prompt(String prompt) {
		q5Prompt = prompt;
	}
	public Integer getQ5Type() {
		return q5Type;
	}
	public void setQ5Type(Integer type) {
		q5Type = type;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	

}
