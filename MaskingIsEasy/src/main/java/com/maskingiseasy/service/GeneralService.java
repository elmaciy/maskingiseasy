package com.maskingiseasy.service;






import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Configurable
public class GeneralService {
	
	@Autowired
	Environment env;
	
	final static Logger logger = LogManager.getLogger(GeneralService.class);

	
	
	static public void mydebug(String logstr) {
		logger.debug(logstr);
	}
	
	//-------------------------------------------------------------------------------------
	static public void mylog(String logstr) {
		logger.info(logstr);
	}
	//-------------------------------------------------------------------------------------
	public  String getPropertyValue(String propertyKey) {
		String ret1="";
		try {ret1=env.getProperty(propertyKey);} catch(Exception e) {e.printStackTrace();}
		return ret1;
	}

	public  String getWorkingDir() {
		String workingDir=getPropertyValue("working.dir");
		if (workingDir.length()==0) {
			workingDir=System.getProperty("user.dir");
		}
		return workingDir;
	}
	
}
