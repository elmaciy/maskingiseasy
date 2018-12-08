package com.maskingiseasy.model;

import java.util.ArrayList;

public class MaskList {
	
	String id;
	String maskListName;
	String description;
	ArrayList<MaskListColumn> columns=new ArrayList<MaskListColumn>();
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMaskListName() {
		return maskListName;
	}
	public void setMaskListName(String maskListName) {
		this.maskListName = maskListName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<MaskListColumn> getColumns() {
		return columns;
	}
	public void setColumns(ArrayList<MaskListColumn> columns) {
		this.columns = columns;
	}
	
	
	
}
