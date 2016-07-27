package com.hybris.oms.tata.exceltocsv.util;


public class ExcelConstants {

	public static final String ROW_VALUE="Row";
	public static final String EMPTY_SPACE=" ";
	public static final String ADD_COMMA=",";
	public static final String TEMP = ",";
	public static final String COMMAVALUE = "\"" + TEMP + "\"";
	public static final String REPCOMMAVALUE= COMMAVALUE.replaceAll("\"", "");
	public static final String NEW_LINE=LogisticPartnerUtil.prop.getProperty("newLine");
	
}