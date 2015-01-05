package com.ss.ivr.survey.base;

import org.apache.log4j.Logger;

import com.audium.server.session.ActionElementData;
import com.audium.server.voiceElement.ActionElementBase;

public  class BaseActionElement extends ActionElementBase{

	protected static Logger logger = Logger.getLogger("survey.log");
	protected static boolean debugCallFlow = false;
	
	public void doAction(String name, ActionElementData actionData) {
		
		// set our debug flag 
		/*if (actionData.getSessionData("Survey_Sess_CallFlowDebugMode").equals("DEBUG")) {			
			debugCallFlow = true;
		}
		*/		
	}
}
