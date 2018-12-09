package com.maskingiseasy.model;

import java.util.ArrayList;

public class Table {

	public static final String OBJECT_TYPE_TABLE="TABLE";
	public static final String OBJECT_TYPE_VIEW="VIEW";
	
	Integer id;
	
	String serverName;
	Schema schema;
	String tableName;
	
	String objectType;
	
	String description;
	String filter;
	String  paralelismFormula;
	Integer paralelismCount;
	
	
	ArrayList<Column> columns=new ArrayList<Column>();


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getServerName() {
		return serverName;
	}


	public void setServerName(String serverName) {
		this.serverName = serverName;
	}


	public Schema getSchema() {
		return schema;
	}


	public void setSchema(Schema schema) {
		this.schema = schema;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getObjectType() {
		return objectType;
	}


	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getFilter() {
		return filter;
	}


	public void setFilter(String filter) {
		this.filter = filter;
	}


	public String getParalelismFormula() {
		return paralelismFormula;
	}


	public void setParalelismFormula(String paralelismFormula) {
		this.paralelismFormula = paralelismFormula;
	}


	public Integer getParalelismCount() {
		return paralelismCount;
	}


	public void setParalelismCount(Integer paralelismCount) {
		this.paralelismCount = paralelismCount;
	}


	public ArrayList<Column> getColumns() {
		return columns;
	}


	public void setColumns(ArrayList<Column> columns) {
		this.columns = columns;
	}


	
	
	
	
} 
