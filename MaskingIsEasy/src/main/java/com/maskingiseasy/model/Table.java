package com.maskingiseasy.model;

import java.util.ArrayList;

public class Table {

	public static final String OBJECT_TYPE_TABLE="TABLE";
	public static final String OBJECT_TYPE_VIEW="VIEW";
	
	Integer id;
	
	String serverName;
	String catalogName;
	String schemaName;
	String tableName;
	
	String objectType;
	
	String description;
	String filter;
	String  paralelismFormula;
	Integer paralelismCount;
	
	
	ArrayList<Column> columns=new ArrayList<Column>();
} 
