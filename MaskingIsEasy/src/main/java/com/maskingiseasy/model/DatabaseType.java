package com.maskingiseasy.model;

public class DatabaseType {
	
	public static final String DATABASE_TYPE_ORACLE="ORACLE";
	
	String name;
	boolean hasServer;
	boolean hasCatalog;
	boolean hasSchema;
	String testSql;
	String partitionCheckSql;
	String connectionStringTemplate;
	
	
	
	
	
	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public boolean isHasServer() {
		return hasServer;
	}





	public void setHasServer(boolean hasServer) {
		this.hasServer = hasServer;
	}





	public boolean isHasCatalog() {
		return hasCatalog;
	}





	public void setHasCatalog(boolean hasCatalog) {
		this.hasCatalog = hasCatalog;
	}





	public boolean isHasSchema() {
		return hasSchema;
	}





	public void setHasSchema(boolean hasSchema) {
		this.hasSchema = hasSchema;
	}





	public String getTestSql() {
		return testSql;
	}





	public void setTestSql(String testSql) {
		this.testSql = testSql;
	}





	public String getConnectionStringTemplate() {
		return connectionStringTemplate;
	}





	public void setConnectionStringTemplate(String connectionStringTemplate) {
		this.connectionStringTemplate = connectionStringTemplate;
	}


	public String getPartitionCheckSql() {
		return partitionCheckSql;
	}





	public void setPartitionCheckSql(String partitionCheckSql) {
		this.partitionCheckSql = partitionCheckSql;
	}




	public static DatabaseType getDatabaseTypeOracle() {
		DatabaseType dbType=new DatabaseType();
		dbType.setName(DATABASE_TYPE_ORACLE);
		dbType.setHasServer(false);
		dbType.setHasCatalog(false);
		dbType.setHasSchema(true);
		dbType.setTestSql("select 1 from dual");
		dbType.setConnectionStringTemplate("jdbc:oracle:thin:@<SERVER>:<PORT>:<SIDSERVICE>");
		dbType.setPartitionCheckSql("select PARTITION_NAME from ALL_TAB_PARTITIONS where owner=<SCHEMA> table_name=<TABLE>");
		return dbType;
	}
}
