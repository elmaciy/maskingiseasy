package com.maskingiseasy.model;

import java.sql.Connection;
import java.util.ArrayList;

import com.maskingiseasy.libs.CommonLib;

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

	Connection conn;
	String error;
	boolean valid;
	
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

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
	
	
	
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void connect() {
		String driver=getDriverName();
		String url=getConnectionString();
		String username=getUsername();
		String password=getPassword();
		Connection conn=CommonLib.getConn(driver, url, username, password);
		
		if (conn==null) {
			setError("Connection is invalid");
			return;
		}
		
		this.conn=conn;
		setValid(testConnection());
	}
	
	
	private boolean testConnection() {
		if (conn==null) return false;
		String sql=getDatabaseType().getTestSql();
		ArrayList<String[]> arr=CommonLib.getDbArray(conn, sql, 1, null, 5, null, null);
		if (arr.size()==0) return false;
		return true;
		
	}

	public void disconnect() {
		CommonLib.closeConn(conn);
	}
	
}
