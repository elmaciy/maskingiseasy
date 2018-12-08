package com.maskingiseasy.model;

import java.util.ArrayList;

public class Database {
	
	
	
	public static DatabaseType DATABASE_TYPE_ORACLE=DatabaseType.getDatabaseTypeOracle();

	
	DatabaseType databaseType;
	String databaseName;
	String driverName;
	String connectionString;
	String username;
	String password;
	String defaultDatabase;
	
	boolean isProduction;
	
	ArrayList<Table> tables=new ArrayList<Table>();




	public DatabaseType getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(DatabaseType databaseType) {
		this.databaseType = databaseType;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDefaultDatabase() {
		return defaultDatabase;
	}

	public void setDefaultDatabase(String defaultDatabase) {
		this.defaultDatabase = defaultDatabase;
	}

	public boolean isProduction() {
		return isProduction;
	}

	public void setProduction(boolean isProduction) {
		this.isProduction = isProduction;
	}

	public ArrayList<Table> getTables() {
		return tables;
	}

	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}
	
	
	
	
}
