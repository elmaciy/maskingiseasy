package com.maskingiseasy.model;

import java.util.ArrayList;

import com.maskingiseasy.libs.CommonLib;
import com.maskingiseasy.service.GenService;

public class DatabaseType {
	
	public static final String DATABASE_TYPE_ORACLE="ORCL";
	public static final String DATABASE_TYPE_MSSQL="MSSQL";
	
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





	public static ArrayList<Column> getColumnList(GenService genService, Database database, Table table) {
		ArrayList<Column> ret1=new ArrayList<Column>();
		
		if (!database.isValid()) return ret1;
		if (database.getConn()==null) return ret1;
		if (database.getDatabaseType().getName().equals(DATABASE_TYPE_ORACLE)) return getColumnListOracle(genService, database , table);
		
		return ret1;
	}





	private static ArrayList<Column> getColumnListOracle(GenService genService, Database database, Table table) {
		ArrayList<Column> ret1=new ArrayList<Column>();
		
		
		ArrayList<String[]> bindlist=new ArrayList<String[]>();
		bindlist.add(new String[] {"STRING", table.getSchema().getSchemaName()});
		bindlist.add(new String[] {"STRING", table.getTableName()});
		
		String sql="\r\n" + 
				"SELECT \r\n" + 
				"cols.column_name\r\n" + 
				"FROM \r\n" + 
				"all_constraints cons, all_cons_columns cols\r\n" + 
				"WHERE \r\n" + 
				"cols.owner = ? \r\n" + 
				"and cols.table_name = ? \r\n" + 
				"AND cons.constraint_type = 'P'\r\n" + 
				"AND cons.constraint_name = cols.constraint_name\r\n" + 
				"AND cons.owner = cols.owner";
		
		ArrayList<String[]> arrPk=CommonLib.getDbArray(database.getConn(), sql, Integer.MAX_VALUE, bindlist, 0, null, null);
		
		ArrayList<String> arrPkColList=new ArrayList<String>();
		
		for (String[] sarr : arrPk) if (arrPkColList.indexOf(sarr[0])==-1) arrPkColList.add(sarr[0]);
		
		sql="\r\n" + 
				"select \r\n" + 
				"column_id, \r\n" + 
				"column_name, \r\n" + 
				"data_type,\r\n" + 
				"data_length, \r\n" + 
				"char_length, \r\n" + 
				"nullable \r\n" + 
				"from dba_tab_columns \r\n" + 
				"where \r\n" + 
				"owner=? \r\n" + 
				"and table_name=? \r\n" + 
				"order by column_id";
		
		
		ArrayList<String[]> arr=CommonLib.getDbArray(database.getConn(), sql, Integer.MAX_VALUE, bindlist, 0, null, null);
		
		for (String[] sarr : arr) {
			int c=0;
			String column_id=sarr[c++];
			String column_name=sarr[c++];
			String data_type=sarr[c++];
			String data_length=sarr[c++];
			String char_length=sarr[c++];
			String nullable=sarr[c++];
			
			Column column=new Column();
			column.setColumnName(column_name);
			column.setId(Integer.valueOf(column_id));
			column.setColumnType(data_type);
			column.setColumnSize(Integer.max(Integer.valueOf(data_length), Integer.valueOf(char_length)));
			column.setNullable(nullable.equals("Y"));
			column.setPk(arrPkColList.indexOf(column_name)>-1);
			
			ret1.add(column);

		}
		return ret1;
	}
	
	
}
