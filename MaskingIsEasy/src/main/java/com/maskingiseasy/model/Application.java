package com.maskingiseasy.model;

import java.util.ArrayList;

public class Application {
	public static final String APPLICATION_TYPE_STATIC_MASKING="STATIC_MASKING";
	public static final String APPLICATION_TYPE_DYNAMIC_MASKING="DYNAMIC_MASKING";
	
	

	
	Integer id;
	String applicationName;
	String description;
	String applicationType;
	String databaseType;
	
	ArrayList<Table> tables=new ArrayList<Table>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public ArrayList<Table> getTables() {
		return tables;
	}

	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}
	
	
	
	
}
