package com.maskingiseasy.service;






import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.maskingiseasy.libs.CommonLib;
import com.maskingiseasy.model.Database;
import com.maskingiseasy.model.DatabaseType;

@Service
@Configurable
public class GeneralService {
	
	@Autowired
	Environment env;
	
	final static Logger logger = LogManager.getLogger(GeneralService.class);
	
	public void makeSureWorkingDirectoriesExists() {
		String workingDir=getWorkingDir();
		try {
			
				ArrayList<String> directoryList=new ArrayList<String>();
				directoryList.add(workingDir);
				directoryList.add(workingDir+File.separator+"config");
				directoryList.add(workingDir+File.separator+"log");
				directoryList.add(workingDir+File.separator+"data");
	
				for (String path  : directoryList) {
					File f=new File(path);
					if (!f.exists()) {
						mylog("Working directory does not exist. Will be created...");
						boolean success=f.mkdirs();
						if (!success) {
							mylog("Working directory could not be created. go System.exit. ");
							System.exit(0);
						}
					}
				}
			
			} catch (Exception e) {
				mylog("Working directory could not be created. go System.exit. ");
				System.exit(0);
			}
		
		
	}

	
	
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
		try {ret1=env.getProperty(propertyKey);} catch(Exception e) {}
		return ret1;
	}

	public  String getWorkingDir() {
		String workingDir=getPropertyValue("working.dir");
		if (workingDir==null || workingDir.length()==0) {
			workingDir=System.getProperty("java.io.tmpdir");
		}
		return workingDir;
	}



	public ArrayList<Database> getAvailableDatabaseList() {
		ArrayList<Database> ret1=new ArrayList<Database>();
		
		Database database1=new Database();
		database1.setConnectionString("xxxxxxxxxxxxxxxx");
		database1.setDatabaseName("Oracle db 1");
		database1.setDriverName("oracle.driver.");
		database1.setDatabaseType(DatabaseType.getDatabaseTypeOracle());
		database1.setDefaultDatabase(null);
		database1.setUsername("user");
		database1.setPassword("123");
		
		
		Database database2=new Database();
		database2.setConnectionString("jdbc:oracle:thin:@localhost:1521:XE");
		database2.setDatabaseName("OracleXE @Local");
		database2.setDriverName("oracle.jdbc.driver.OracleDriver");
		database2.setDatabaseType(DatabaseType.getDatabaseTypeOracle());
		database2.setDefaultDatabase(null);
		database2.setUsername("system");
		database2.setPassword("Han#1323");
		
		
		ret1.add(database1);
		ret1.add(database2);

		return ret1;
	}




	
}
