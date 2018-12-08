package com.maskingiseasy.model;

import java.util.ArrayList;

public class MaskingProfile {
	public static final String MASKING_PROFILE_TYPE_UNMASKED="UNMASKED";
	public static final String MASKING_PROFILE_TYPE_FIXED_VALUE="FIXED_VALUE";
	public static final String MASKING_PROFILE_TYPE_HIDING="HIDING";
	public static final String MASKING_PROFILE_TYPE_SET_NULL="SET_NULL";
	public static final String MASKING_PROFILE_TYPE_SCRAMBLE="SCRAMBLE";
	public static final String MASKING_PROFILE_TYPE_BY_LIST="BY_LIST";
	public static final String MASKING_PROFILE_TYPE_SHUFFLE_IN_RECORDS="SHUFFLE_IN_RECORDS";
	public static final String MASKING_PROFILE_TYPE_SHUFFLE_IN_GROUP="SHUFFLE_IN_GROUP";
	public static final String MASKING_PROFILE_TYPE_FIXED_IN_GROUP="FIXED_IN_GROUP";
	public static final String MASKING_PROFILE_TYPE_JAVASCRIPT="JAVASCRIPT";
	public static final String MASKING_PROFILE_TYPE_SQL_STATEMENT="SQL_STATEMENT";
	public static final String MASKING_PROFILE_TYPE_DATE_SCRAMBLE="DATE_SCRAMBLE";
	
	
	public static final String MASK_SCRAMBLE_TYPE_ALL="ALL";
	public static final String SMASK_CRAMBLE_TYPE_FIRST_X="FIRST_X";
	public static final String MASK_SCRAMBLE_TYPE_LAST_X="LAST_X";
	public static final String MASK_SCRAMBLE_TYPE_BETWEEN_X_Y="BETWEEN_X_Y";
	public static final String MASK_SCRAMBLE_TYPE_EXCEPT_FIRST_X="EXCEPT_FIRST_X";
	public static final String MASK_SCRAMBLE_TYPE_EXCEPT_LAST_X="EXCEPT_LAST_X";
	public static final String MASK_SCRAMBLE_TYPE_EXCEPT_FIRST_X_LAST_Y="EXCEPT_FIRST_X_LAST_Y";


	String id;
	String profileType;
	String fixedValue;
	
	
	Integer maskScrambleType;	
	int maskScrambleX;
	int maskScrambleY;
	boolean keepSpaces;
	String maskScrambleCharacters;
	
	MaskList maskList;
	ArrayList<Column> listReferenceColumnsForHashing=new ArrayList<Column>();
	String listFilter;
	
	String javascript;
	String sqlStatement;
	
	String dateMaskSimpleDateFormat;
	
	int dateMaskYearDiff;
	int dateMaskMonthDiff;
	int dateMaskDayDiff;
	int dateMaskHourDiff;
	int dateMaskMinuteDiff;
	int dateMaskSecondDiff;
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProfileType() {
		return profileType;
	}
	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}
	public String getFixedValue() {
		return fixedValue;
	}
	public void setFixedValue(String fixedValue) {
		this.fixedValue = fixedValue;
	}
	public Integer getMaskScrambleType() {
		return maskScrambleType;
	}
	public void setMaskScrambleType(Integer maskScrambleType) {
		this.maskScrambleType = maskScrambleType;
	}
	public int getMaskScrambleX() {
		return maskScrambleX;
	}
	public void setMaskScrambleX(int maskScrambleX) {
		this.maskScrambleX = maskScrambleX;
	}
	public int getMaskScrambleY() {
		return maskScrambleY;
	}
	public void setMaskScrambleY(int maskScrambleY) {
		this.maskScrambleY = maskScrambleY;
	}
	public boolean isKeepSpaces() {
		return keepSpaces;
	}
	public void setKeepSpaces(boolean keepSpaces) {
		this.keepSpaces = keepSpaces;
	}
	public String getMaskScrambleCharacters() {
		return maskScrambleCharacters;
	}
	public void setMaskScrambleCharacters(String maskScrambleCharacters) {
		this.maskScrambleCharacters = maskScrambleCharacters;
	}
	public MaskList getMaskList() {
		return maskList;
	}
	public void setMaskList(MaskList maskList) {
		this.maskList = maskList;
	}
	public ArrayList<Column> getListReferenceColumnsForHashing() {
		return listReferenceColumnsForHashing;
	}
	public void setListReferenceColumnsForHashing(ArrayList<Column> listReferenceColumnsForHashing) {
		this.listReferenceColumnsForHashing = listReferenceColumnsForHashing;
	}
	public String getListFilter() {
		return listFilter;
	}
	public void setListFilter(String listFilter) {
		this.listFilter = listFilter;
	}
	public String getJavascript() {
		return javascript;
	}
	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}
	public String getSqlStatement() {
		return sqlStatement;
	}
	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}
	public String getDateMaskSimpleDateFormat() {
		return dateMaskSimpleDateFormat;
	}
	public void setDateMaskSimpleDateFormat(String dateMaskSimpleDateFormat) {
		this.dateMaskSimpleDateFormat = dateMaskSimpleDateFormat;
	}
	public int getDateMaskYearDiff() {
		return dateMaskYearDiff;
	}
	public void setDateMaskYearDiff(int dateMaskYearDiff) {
		this.dateMaskYearDiff = dateMaskYearDiff;
	}
	public int getDateMaskMonthDiff() {
		return dateMaskMonthDiff;
	}
	public void setDateMaskMonthDiff(int dateMaskMonthDiff) {
		this.dateMaskMonthDiff = dateMaskMonthDiff;
	}
	public int getDateMaskDayDiff() {
		return dateMaskDayDiff;
	}
	public void setDateMaskDayDiff(int dateMaskDayDiff) {
		this.dateMaskDayDiff = dateMaskDayDiff;
	}
	public int getDateMaskHourDiff() {
		return dateMaskHourDiff;
	}
	public void setDateMaskHourDiff(int dateMaskHourDiff) {
		this.dateMaskHourDiff = dateMaskHourDiff;
	}
	public int getDateMaskMinuteDiff() {
		return dateMaskMinuteDiff;
	}
	public void setDateMaskMinuteDiff(int dateMaskMinuteDiff) {
		this.dateMaskMinuteDiff = dateMaskMinuteDiff;
	}
	public int getDateMaskSecondDiff() {
		return dateMaskSecondDiff;
	}
	public void setDateMaskSecondDiff(int dateMaskSecondDiff) {
		this.dateMaskSecondDiff = dateMaskSecondDiff;
	}

	
	
	
	
}
