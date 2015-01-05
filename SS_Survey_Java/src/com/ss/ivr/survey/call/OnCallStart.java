package com.ss.ivr.survey.call;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.audium.server.AudiumException;
import com.audium.server.global.ApplicationAPI;
import com.audium.server.proxy.StartCallInterface;
import com.audium.server.session.CallStartAPI;
import com.ss.ivr.survey.util.BusinessConstants;


/**
 * @author 
 *
 * This class runs as each call starts to initialize the session variables.
 */
public class OnCallStart  implements StartCallInterface {
	
	private static Logger logger = Logger.getLogger(OnCallStart.class.getName());	
	private Properties appProperties = new Properties();

	
	public void onStartCall(CallStartAPI startAPI) throws AudiumException {
		ApplicationAPI appAPI = startAPI.getApplicationAPI();

		// Loading properties file...
		InputStream fin = null;		
		try {			
			fin = OnCallStart.class.getResourceAsStream(BusinessConstants.SURVEY_PROPFILEPATH);	
			if(fin == null) {
				logger.error("OnCallStart fin is null");
			}
		} catch (Exception e) {
			logger.error("[OnCallStart] error opening properties file",e);
		}
		try {
			appProperties.load(fin);	
			startAPI.setSessionData(BusinessConstants.AppProperties, appProperties);
		} catch (IOException e) {
			logger.error("[OnCallStart] error loading properties file",e);
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					logger.error("[OnCallStart] error closing properties file input",e);
				}
			}
		}
		
		if ("DEBUG".equals(appProperties.getProperty(BusinessConstants.LoggerLevel))) {
			logger.debug("DEBUG MODE: Logger is logging DEBUG level"); 			
			logger.setLevel(Level.DEBUG);						
		} 		
		else {
			logger.error("ERROR MODE: logger is logging ERROR level"); 
			logger.setLevel(Level.ERROR);						
		}
		//Master switch
		if (("true").equalsIgnoreCase(appProperties.getProperty("SURVEY_ENABLED"))) {
			logger.debug("[OnCallStart] survey app is ENABLED. ");
			startAPI.setSessionData("SURVEY_ENABLED", "true");
		}
		else {
			logger.debug("[OnCallStart] survey app is DISABLED. ");
			startAPI.setSessionData("SURVEY_ENABLED", "false");
			return;
		}
		
//		Setting default audio path
		String hostname = (String) appAPI.getApplicationData(BusinessConstants.AppHostname);
		startAPI.setSessionData(BusinessConstants.AppAudioPath, appProperties.getProperty(hostname +"DEFAULT_AUDIO_PATH"));
		startAPI.setSessionData("DEFAULT_LANGUAGE", appProperties.getProperty(hostname+"MEDIA_LANGUAGE_en"));
		startAPI.setSessionData(BusinessConstants.CallFlowDebugMode, appProperties.getProperty("Global_CallFlowDebugMode"));
		

		if (logger.isDebugEnabled()) {
			logger.debug(" Default Audio path on app start: " + appProperties.getProperty(hostname+"DEFAULT_AUDIO_PATH"));
			logger.debug("Audio path: " + startAPI.getSessionData(BusinessConstants.AppAudioPath));
		}
		String mediaLanguage = (String)startAPI.getSessionData("DEFAULT_LANGUAGE");
		String audioPath = (String)startAPI.getSessionData(BusinessConstants.AppAudioPath)+mediaLanguage+"/survey";
		startAPI.setDefaultAudioPath(audioPath);
		
		String dbHostName = appProperties.getProperty(hostname+BusinessConstants.DBHOSTNAME);
		String dbPort = appProperties.getProperty(hostname+BusinessConstants.DBPORT);
		String dbName = appProperties.getProperty(hostname+BusinessConstants.DBNAME);
		String userName = appProperties.getProperty(hostname+BusinessConstants.USERNAME);
		String password = appProperties.getProperty(hostname+BusinessConstants.PASSWORD);

		String dbURL = "jdbc:sqlserver://"+dbHostName+":"+dbPort+";"
		+ "user="+userName+";password="+password+";"
		+ "database="+dbName;

		startAPI.setSessionData("db_URL", dbURL);
		
		startAPI.setSessionData("Locale", "en-us");
		
		String dnis = (String)startAPI.getSessionData("dnis");
		String ani = (String)startAPI.getSessionData("ani");

		startAPI.setSessionData("dnis", dnis);
		startAPI.setSessionData("ani", ani);

		String lang =(String)startAPI.getSessionData("Language");

		if(logger.isDebugEnabled())
			logger.debug("The language in app transfer :"+lang);
		logger.debug("The dnis in app transfer :"+dnis);
		
	}		
	
}
