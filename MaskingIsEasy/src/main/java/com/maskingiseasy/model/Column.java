package com.maskingiseasy.model;

import java.util.ArrayList;

public class Column {
	Integer id;
	String columnName;
	String columnType;
	Integer columnSize;
	boolean isNullable;
	boolean isPk;
	
	String description;
	
	 
	ArrayList<MaskingRule> maskingRules=new ArrayList<MaskingRule>();

 

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getColumnName() {
		return columnName;
	}


	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}


	public String getColumnType() {
		return columnType;
	}


	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}


	public Integer getColumnSize() {
		return columnSize;
	}


	public void setColumnSize(Integer columnSize) {
		this.columnSize = columnSize;
	}


	public boolean isNullable() {
		return isNullable;
	}


	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}


	public boolean isPk() {
		return isPk;
	}


	public void setPk(boolean isPk) {
		this.isPk = isPk;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public ArrayList<MaskingRule> getMaskingRules() {
		return maskingRules;
	}


	public void setMaskingRules(ArrayList<MaskingRule> maskingRules) {
		this.maskingRules = maskingRules;
	}
	
	
	
	
	
	
	
	
}
