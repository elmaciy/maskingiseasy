package com.maskingiseasy.model;

public class Condition {
	public static final String CONDITION_TYPE_ALLWAYS="ALLWAYS";
	public static final String CONDITION_TYPE_WHEN="WHEN";
	
	
	public static final String OPERATOR_EQUALS_TO="EQUALS_TO";
	public static final String OPERATOR_NOT_EQUALS_TO="NOT_EQUALS_TO";
	public static final String OPERATOR_EQUALS_IN="EQUALS_IN";
	public static final String OPERATOR_NOT_EQUALS_IN="NOT_EQUALS_IN";
	public static final String OPERATOR_REGEX="REGEX";
	public static final String OPERATOR_NOT_REGEX="NOT_REGEX";
	public static final String OPERATOR_JAVASCRIPT="JAVASCRIPT";
	public static final String OPERATOR_CATCH_ALL="CATCH_ALL";
	
	String conditionType;
	Integer conditionOrder;
	Column column;
	String operator;
	String operatorValues;
	public String getConditionType() {
		return conditionType;
	}
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
	public Integer getConditionOrder() {
		return conditionOrder;
	}
	public void setConditionOrder(Integer conditionOrder) {
		this.conditionOrder = conditionOrder;
	}
	public Column getColumn() {
		return column;
	}
	public void setColumn(Column column) {
		this.column = column;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatorValues() {
		return operatorValues;
	}
	public void setOperatorValues(String operatorValues) {
		this.operatorValues = operatorValues;
	}
	
	
	
	
	
}
