package com.hybris.oms.tata.exceltocsv.util;

import com.hybris.oms.tata.service.CsvService;

/**
 * 
 * A utility class which returns singleton service.
 *
 */
public class CsvServiceFactory {

	private static CsvService csvService=null;
	
	//private constructor
	private CsvServiceFactory(){
		
	}
	
	public static CsvService getCsvService(){
		if(csvService==null){
			csvService = new CsvService();
		}
		return csvService;
	}
}
