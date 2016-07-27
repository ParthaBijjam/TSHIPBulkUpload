package com.hybris.oms.tata.exceltocsv.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;




/**
 * 
 * This class is used to get Logistic Partner names from properties file
 *
 */
public class LogisticPartnerUtil {
	public static Properties prop=new Properties();
	
	
	
	static ArrayList<String> logisticPartnerNames=new ArrayList<String>();
	static Map<String, String> logisticPartners=new HashMap<String, String>(); 
	
	static{
		InputStream input = null;
		try {
			input = new FileInputStream(System.getProperty("stateCodePropertiesfilePath"));
			//input = new FileInputStream("resource/stateCodes.properties");
			prop.load(input);
		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	static{
		logisticPartnerNames.add(prop.getProperty("bluedartOutputName"));
		logisticPartnerNames.add(prop.getProperty("roadrunnrOutputName"));
		logisticPartnerNames.add(prop.getProperty("gojavasOutputName"));
		logisticPartnerNames.add(prop.getProperty("fedexOutputName"));
		logisticPartnerNames.add(prop.getProperty("gatiOutputName"));
		
		logisticPartners.put(prop.getProperty("bluedartHeaderName"),prop.getProperty("bluedartOutputName"));
		logisticPartners.put(prop.getProperty("roadrunnrHeaderName"),prop.getProperty("roadrunnrOutputName"));
		logisticPartners.put(prop.getProperty("gojavasHeaderName"),prop.getProperty("gojavasOutputName"));
		logisticPartners.put(prop.getProperty("fedexHeaderName"),prop.getProperty("fedexOutputName"));
		logisticPartners.put(prop.getProperty("gatiHeaderName"),prop.getProperty("gatiOutputName"));
		
	}
	/**
	 * 
	 * @param sheet
	 * @param cellContent
	 * @param row
	 * @param writer
	 * @return
	 * @throws IOException
	 */
	public static String findLogisticPartnerName(final String logisticPartnerName)
	{
		return logisticPartners.get(logisticPartnerName);
	}//

	public static boolean containsLogisticPartnerName(final String logisticPartnerName){
		return logisticPartnerNames.contains(logisticPartnerName.toUpperCase());
	}
}
