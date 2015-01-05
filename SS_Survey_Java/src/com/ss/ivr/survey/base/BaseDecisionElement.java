package com.ss.ivr.survey.base;


import org.apache.log4j.Logger;

import com.audium.server.session.DecisionElementData;
import com.audium.server.voiceElement.DecisionElementBase;

public class BaseDecisionElement extends DecisionElementBase{

	protected static Logger logger = Logger.getLogger("survey.log");
	protected static boolean debug = false;
	
	public String doDecision(String name, DecisionElementData decisionData) {
		
		// set our debug flag 
		/*if (decisionData.getSessionData("Survey_Sess_CallFlowDebugMode").equals("DEBUG")) {
			logger.debug("setting debug flag to true for decision elements");
			debug = true;
		}*/
		return null;
	}
}
