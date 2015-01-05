package com.ss.ivr.survey.application;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.audium.server.global.ApplicationStartAPI;
import com.audium.server.proxy.StartApplicationInterface;
import com.ss.ivr.survey.util.BusinessConstants;

/**
 * @author , Speech-Soft Survey
 *
 * This class runs at application init.
 */
public class OnApplicationStart 
	implements StartApplicationInterface {

	private static Logger logger = Logger.getLogger(OnApplicationStart.class.getName());	
	
	public void onStartApplication(ApplicationStartAPI appStartAPI) {

		//initial log4j from log4j.properties file first
		try {
		
			URL url = OnApplicationStart.class.getResource(BusinessConstants.SURVEY_LOGFILEPATH);
			DOMConfigurator.configure(url);
			
			logger.info("Survey Application Started");
			
		} catch (Exception e) {

			// init RootLogger as default
			logger = Logger.getRootLogger();
			logger.addAppender(new ConsoleAppender());			
		}
		
				
		String hostname = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();	    
		    hostname = addr.getHostName();
		    if(logger.isDebugEnabled())
		    	logger.debug("Value of host name in "+hostname);
		    appStartAPI.setApplicationData(BusinessConstants.AppHostname, hostname);
		} catch (UnknownHostException ue) {
			logger.error("** Error getting hostname *** Webservices and Audios will not work ***",ue);
			hostname="default";
		}
		
		if (logger.isDebugEnabled())
	    	logger.debug("Hostname is '"+hostname+"'");
		
		
	}
}
