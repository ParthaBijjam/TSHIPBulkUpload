package com.hybris.oms.tata.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.hybris.oms.tata.exceltocsv.util.ExcelConstants;
import com.hybris.oms.tata.exceltocsv.util.LogisticPartnerUtil;
import com.hybris.oms.tata.pincodesupload.pojo.LogisticPartner;
import com.hybris.oms.tata.pincodesupload.pojo.PriorityMaster;

public class CsvService {

	private  StringBuffer headerData;
	private  StringBuffer data;
	private  StringBuffer data1;
	private  StringBuffer data2;
	private  StringBuffer data3;
	private  StringBuffer data4;
	private  StringBuffer data5;
	private  StringBuffer data6;
	private  StringBuffer data7;
	private  StringBuffer data8;
	private  StringBuffer data9;
	private  StringBuffer footerData;
		
	 
	public StringBuffer getData() {
		return data;
	}


	public void setData(StringBuffer data) {
		this.data = data;
	}


	/**
	 *  
	 * @param deliveryMode
	 * @param logisticPartner
	 * @param priorityMaster
	 * @throws IOException
	 */
	public  void addLogisticPartnerData(final String deliveryMode,final LogisticPartner logisticPartner,
			final PriorityMaster priorityMaster) throws IOException{
		//System.out.println("result");
		if(logisticPartner.getIndex()>=11 && logisticPartner.getIndex()<=70){
			if(logisticPartner.getName().equalsIgnoreCase("Bluedart,")){
				appendData(deliveryMode,logisticPartner,data,priorityMaster);
		
			}
			else if(logisticPartner.getName().equalsIgnoreCase("ROADRUNNR,")){
				appendData(deliveryMode,logisticPartner,data1,priorityMaster);
				
			}
			else if(logisticPartner.getName().equalsIgnoreCase("GOJAVAS,")){
				appendData(deliveryMode,logisticPartner,data2,priorityMaster);
				
			}
			else if(logisticPartner.getName().equalsIgnoreCase("FEDEX,")){
				appendData(deliveryMode,logisticPartner,data3,priorityMaster);
				
			}
			else if(logisticPartner.getName().equalsIgnoreCase("GATI,")){
				appendData(deliveryMode,logisticPartner,data4,priorityMaster);
			}
		}// this if condition is for getting air mode logistic Partners data.
		else {
			if(logisticPartner.getName().equalsIgnoreCase("BLUEDART,")){
				appendData(deliveryMode,logisticPartner,data5,priorityMaster);
				
				
			}
			else if(logisticPartner.getName().equalsIgnoreCase("ROADRUNNR,")){
				appendData(deliveryMode,logisticPartner,data6,priorityMaster);
				
			}
			else if(logisticPartner.getName().equalsIgnoreCase("GOJAVAS,")){
				appendData(deliveryMode,logisticPartner,data7,priorityMaster);
				
			}
			else if(logisticPartner.getName().equalsIgnoreCase("FEDEX,")){
				appendData(deliveryMode,logisticPartner,data8,priorityMaster);
				
			}
			else if(logisticPartner.getName().equalsIgnoreCase("GATI,")){
				appendData(deliveryMode,logisticPartner,data9,priorityMaster);
				
			}
		}//this condition is for getting surface mode logistic partner data.
	}
	
	
	/**
	 * This method adds all logistic partner d
	 * 
	 * @param deliveryMode
	 * @param logisticPartner
	 * @param data
	 * @param priorityMaster
	 * @throws IOException
	 */
	public  void appendData(final String deliveryMode,final LogisticPartner logisticPartner,final StringBuffer logisticdata,
			final PriorityMaster priorityMaster) throws IOException{
		    //final StringBuffer logisticdata=new StringBuffer();
			logisticdata.append(deliveryMode);
			logisticdata.append(",");
			logisticdata.append(priorityMaster.getPincode());
			if(logisticPartner.getIndex()<=70){
			logisticdata.append(LogisticPartnerUtil.prop.getProperty("airMode")+",");
			}else{
				logisticdata.append(LogisticPartnerUtil.prop.getProperty("surfaceMode")+",");
			}
			logisticdata.append(logisticPartner.getName());
			logisticdata.append(logisticPartner.getCod());
			logisticdata.append(logisticPartner.getPrepaidLimit());
			logisticdata.append(logisticPartner.getCodLimit());
			logisticdata.append(logisticPartner.getCarea());
			logisticdata.append(logisticPartner.getCscrcd());
			logisticdata.append(logisticPartner.getCloctype());
			logisticdata.append(logisticPartner.getNewzone());
			logisticdata.append(logisticPartner.getTransitTatPrepaid());
			logisticdata.append(logisticPartner.getTransitTatCod());
			logisticdata.append(logisticPartner.getFormRequired());
			logisticdata.append(logisticPartner.getIsReturnPincode());
			logisticdata.append(logisticPartner.getPickUp()); 
			logisticdata.append(logisticPartner.getCodPriority());
			logisticdata.append(logisticPartner.getPrepaidPriority());

			if(logisticPartner.getAdjCodLimit()!=null)
			logisticdata.append(logisticPartner.getAdjCodLimit());
			else
			logisticdata.append("0"+",");
			if(logisticPartner.getAdjPrepaidLimit()!=null)
			logisticdata.append(logisticPartner.getAdjPrepaidLimit());
			else
			logisticdata.append("0"+",");
			
			logisticdata.append(priorityMaster.getCity());
			logisticdata.append(priorityMaster.getState());
			logisticdata.append("IN");
			logisticdata.append("\r\n");
			
			//return logisticdata.toString();
		
	}
	
	
	/**
	 * 
	 * @param data
	 * @param outputFile
	 * @param validationFile
	 * @throws IOException
	 */
	public  void writeData(final StringBuffer data,final File outputFile,final File validationFile) throws IOException{
		if(validationFile.length()==0){
		final FileWriter bwr = new FileWriter(outputFile);
		bwr.write(headerData.toString().concat(data.toString().concat(data1.toString().concat(data2.toString().concat(data3.toString()
				.concat(data4.toString().concat(data5.toString().concat(data6.toString().concat(data7.toString()
						.concat(data8.toString().concat(data9.toString().concat(footerData.toString()))))))))))));
		bwr.flush();
		bwr.close();
		}
	}
	
	/**
	 * THis method appends header columns to the output file.
	 * 
	 * @param deliveryMode
	 */
	public  void appendHeaderData(final String deliveryMode){
		headerData = new StringBuffer();
		data= new StringBuffer();
		data1 = new StringBuffer();
		data2= new StringBuffer();
		data3 = new StringBuffer();
		data4= new StringBuffer();
		data5 = new StringBuffer();
		data6= new StringBuffer();
		data7 = new StringBuffer();
		data8= new StringBuffer();
		data9 = new StringBuffer();
		final String headerColumns=LogisticPartnerUtil.prop.getProperty("headerColumns")+LogisticPartnerUtil.prop.getProperty("newLine");
		headerData.append(headerColumns);
		String result=null;
		if (deliveryMode.equalsIgnoreCase(LogisticPartnerUtil.prop.getProperty("dataModeType1"))){
			final String STATICDATAHD = LogisticPartnerUtil.prop.getProperty("STATICDATAHD") ;
			headerData.append(STATICDATAHD);
			 result=addCommasToBOF(ExcelConstants.REPCOMMAVALUE);
			headerData.append(result);
			headerData.append(LogisticPartnerUtil.prop.getProperty("country"));
			headerData.append(LogisticPartnerUtil.prop.getProperty("newLine"));
		}else if(deliveryMode.equalsIgnoreCase(LogisticPartnerUtil.prop.getProperty("dataModeType2"))){
			final String STATICDATAED = LogisticPartnerUtil.prop.getProperty("STATICDATAED") ;
			headerData.append(STATICDATAED);
			result=addCommasToBOF(ExcelConstants.REPCOMMAVALUE);
			headerData.append(result);
			headerData.append(LogisticPartnerUtil.prop.getProperty("country"));
			headerData.append(LogisticPartnerUtil.prop.getProperty("newLine"));
		}
		
	}
	
	/**
	 * This method appends EOF and fileNameTimeStamp and followed by commas at the foorter to the output file.
	 * 
	 * @param deliveryMode
	 * @param fileNameTimeStamp
	 */
	public  void appendFooterData(final String deliveryMode,final String fileNameTimeStamp){
		footerData = new StringBuffer();
		String result=null;
		footerData.append(deliveryMode + "," + "EOF,"+fileNameTimeStamp + "," );
		result=addCommasToEOF(ExcelConstants.REPCOMMAVALUE);
		footerData.append(result);
		footerData.append("IN");
	}
	
	/**
	 * 
	 * @param REPCOMMAVALUE
	 * @return
	 */
	public  String addCommasToBOF(final String REPCOMMAVALUE){
		final String commasCountOfBOF = LogisticPartnerUtil.prop.getProperty("commasCountOfBOF");
		final int commasCount = Integer.parseInt(commasCountOfBOF);
		final StringBuffer staticDataHd=new StringBuffer();
		for(int i=0;i<commasCount;i++){
			staticDataHd.append(REPCOMMAVALUE);
		}
		return staticDataHd.toString();
	}
	
	/**
	 * 
	 * @param REPCOMMAVALUE
	 * @return
	 */
public  String addCommasToEOF(final String REPCOMMAVALUE){
	final String commasCountOfEOF = LogisticPartnerUtil.prop.getProperty("commasCountOfEOF");
	final int commasCount = Integer.parseInt(commasCountOfEOF);
		final StringBuffer staticDataEd=new StringBuffer();
		for(int i=0;i<commasCount;i++){
			staticDataEd.append(REPCOMMAVALUE);
		}
		return staticDataEd.toString();
	}


}
