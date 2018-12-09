package com.maskingiseasy.model;

import java.util.ArrayList;

import com.maskingiseasy.libs.CommonLib;
import com.maskingiseasy.service.GenService;

public class DatabaseType {
	
	public static final String DATABASE_TYPE_ORACLE="ORCL";
	
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
	
	
	
	public static void getTableList(GenService genService, Database database) {
		if (!database.isValid()) return;
		if (database.getConn()==null) return;
		if (database.getDatabaseType().getName().equals(DATABASE_TYPE_ORACLE)) getTableListOracle(genService, database);
		else return;
	}





	private static void getTableListOracle(GenService genService, Database database) {
		database.getTables().clear();
		String sql="select owner, table_name from dba_tables order by 1, 2";
		ArrayList<String[]> arr=CommonLib.getDbArray(database.getConn(), sql, Integer.MAX_VALUE, null, 0, null, null);
		if (arr.size()==0) return;
		
		for (String sarr[] : arr ) {
			String schemaName=sarr[0];
			String tableName=sarr[1];
			int id=(schemaName+"."+tableName).hashCode();
			
			if (id % 100 ==0)
				genService.mylog("Adding table : "+schemaName+"."+tableName);
			
			
			Table table=new Table();
			table.setId(id);
			table.setTableName(tableName);
			table.setObjectType(Table.OBJECT_TYPE_TABLE);
			table.setParalelismCount(1);
			table.setParalelismFormula("1");
			table.setServerName(null);
			
			
			Catalog catalog=new Catalog();
			catalog.setCatalogName("");
			
			Schema schema=new Schema();
			schema.setSchemaName(schemaName);
			schema.setCatalog(catalog);
			
			table.setSchema(schema);
			
			database.getTables().add(table);
			
		}
		
	}
	
	
}
