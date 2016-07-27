package com.hybris.oms.tata.exceltocsv.validator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hybris.oms.tata.exceltocsv.util.CsvServiceFactory;
import com.hybris.oms.tata.exceltocsv.util.ExcelConstants;
import com.hybris.oms.tata.exceltocsv.util.LogisticPartnerUtil;
import com.hybris.oms.tata.pincodesupload.pojo.LogisticPartner;
import com.hybris.oms.tata.pincodesupload.pojo.PriorityMaster;

/**
 * 
 * This class validates all logistic Partners data, if any of the fields of the logisticpartner 
 * fails to have manditory conditions, it will raise those validation message in validation file.
 *
 */
public class LogisticPartnerValidator {
	
	
	
	
	/**
	 * This method validates all the columns of the logisticPartner and then append data to output file.
	 * 
	 * @param deliveryMode
	 * @param priorityMaster
	 * @param logisticPartners
	 * @param writer
	 * @param rowNumber
	 * @throws IOException
	 */
	public  void validateLogisticPartners(final String deliveryMode,final PriorityMaster priorityMaster,final ArrayList<LogisticPartner> logisticPartners,
			final FileWriter writer,final int rowNumber) throws IOException{
		
		getAirCODPriorities(priorityMaster, logisticPartners.subList(0, 5));
		getSurfaceCODPriorities(priorityMaster, logisticPartners.subList(5, logisticPartners.size()));
		
		getAirPrepaidPriorities(priorityMaster, logisticPartners.subList(0, 5));
		getSurfacePrepaidPriorities(priorityMaster, logisticPartners.subList(5, logisticPartners.size()));
		
			for(final LogisticPartner logisticPartner : logisticPartners){
			validateCod(priorityMaster, logisticPartner,writer,rowNumber);
			validatePrepaidLimit(priorityMaster, logisticPartner,writer,rowNumber);
			validateCodLimit(priorityMaster, logisticPartner,writer,rowNumber);
			validateCareatoNewzone(priorityMaster, logisticPartner,writer);
			validateTransitTatPrepaid(priorityMaster, logisticPartner,writer,rowNumber);
			validateTransitTatCod(priorityMaster, logisticPartner,writer,rowNumber);
			validateFormRequired(priorityMaster, logisticPartner,writer);
			validateIsReturnPincode(priorityMaster, logisticPartner,writer,rowNumber);
			validatePickup(priorityMaster, logisticPartner,writer,rowNumber);
			assigningOfCodPriority(priorityMaster,logisticPartner,writer);
			assigningOfPrepaidPriority(priorityMaster, logisticPartner,writer);
			CsvServiceFactory.getCsvService().addLogisticPartnerData(deliveryMode,logisticPartner, priorityMaster);
			}
	}
	
	/**
	 * This method compares codLimit of codpriorit1 air and codpriority2 air, and appends the 
	 * lesser value to adjcodLimit in output file. 
	 * for eg: codPriority1 is BLUEDART and codPriority2 is GATI, it compares codlimit of both	
	 * logisticpartners and appends the lesser to both logisticpartner in output file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartners
	 */
	private  void getAirCODPriorities(final PriorityMaster priorityMaster,final List<LogisticPartner> logisticPartners){
				final String logisticPartnerName1=priorityMaster.getAirCodPriority1();
				final String logisticPartnerName2=priorityMaster.getAirCodPriority2();
				final LogisticPartner logisticPartnerResult1=getLogisticPartnerByName(logisticPartnerName1, logisticPartners);
				final LogisticPartner logisticPartnerResult2=getLogisticPartnerByName(logisticPartnerName2, logisticPartners);
				if(logisticPartnerResult1!=null && logisticPartnerResult2==null){
					logisticPartnerResult1.setAdjCodLimit(logisticPartnerResult1.getCodLimit());
				}else if(logisticPartnerResult1==null && logisticPartnerResult2!=null){
					logisticPartnerResult2.setAdjCodLimit(logisticPartnerResult2.getCodLimit());
				}
				else if(logisticPartnerResult1!=null && logisticPartnerResult2!=null){
				final String res1=getMinValueForCOD(logisticPartnerResult1, logisticPartnerResult2);
				logisticPartnerResult1.setAdjCodLimit(res1);
				logisticPartnerResult2.setAdjCodLimit(res1);
				}
	}
	
	
	
	/**
	 * This method compares prepaidLimit of prepaidpriorit1 air and prepaidpriority2 air, and appends the 
	 * lesser value to adjcodLimit in output file. 
	 * for eg: prepaidPriority1 is BLUEDART and prepaidPriority2 is GATI, it compares prepaidlimit of both	
	 * logisticpartners and appends the lesser to both logisticpartner in output file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartners
	 */
	private  void getAirPrepaidPriorities(final PriorityMaster priorityMaster,final List<LogisticPartner> logisticPartners){
				final String e=priorityMaster.getAirPrepaidPriority1();
				final String f=priorityMaster.getAirPrepaidPriority2();
				final LogisticPartner logisticPartnerResult1=getLogisticPartnerByName(e, logisticPartners);
				final LogisticPartner logisticPartnerResult2=getLogisticPartnerByName(f, logisticPartners);
				if(logisticPartnerResult1!=null && logisticPartnerResult2==null){
					logisticPartnerResult1.setAdjPrepaidLimit(logisticPartnerResult1.getCodLimit());
				}else if(logisticPartnerResult1==null && logisticPartnerResult2!=null){
					logisticPartnerResult2.setAdjPrepaidLimit(logisticPartnerResult2.getCodLimit());
				}
				else if(logisticPartnerResult1!=null && logisticPartnerResult2!=null){
				final String res3=getMinValueForPrepaid(logisticPartnerResult1, logisticPartnerResult2);
				logisticPartnerResult1.setAdjPrepaidLimit(res3);
				logisticPartnerResult2.setAdjPrepaidLimit(res3);
				}
	}
	
	/**
	 * This method compares codLimit of codpriorit1 surface and codpriority2 surface, and appends the 
	 * lesser value to adjcodLimit in output file. 
	 * for eg: codPriority1 is BLUEDART and codPriority2 is GATI, it compares codlimit of both	
	 * logisticpartners and appends the lesser to both logisticpartner in output file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartners
	 */
	private   void  getSurfaceCODPriorities(final PriorityMaster priorityMaster,final List<LogisticPartner> logisticPartners) {
				final String logisticPartnerName1=priorityMaster.getSurfaceCodPriority1();
				final String logisticPartnerName2=priorityMaster.getSurfaceCodPriority2();
				final LogisticPartner logisticPartnerResult1=getLogisticPartnerByName(logisticPartnerName1, logisticPartners);
				final LogisticPartner logisticPartnerResult2=getLogisticPartnerByName(logisticPartnerName2, logisticPartners);
				if(logisticPartnerResult1!=null && logisticPartnerResult2==null){
					logisticPartnerResult1.setAdjCodLimit(logisticPartnerResult1.getCodLimit());
				}else if(logisticPartnerResult1==null && logisticPartnerResult2!=null){
					logisticPartnerResult2.setAdjCodLimit(logisticPartnerResult2.getCodLimit());
				}
				else if(logisticPartnerResult1!=null && logisticPartnerResult2!=null){
				final String res2=getMinValueForCOD(logisticPartnerResult1, logisticPartnerResult2);
				logisticPartnerResult1.setAdjCodLimit(res2);
				logisticPartnerResult2.setAdjCodLimit(res2);
				}
	}
	
	/**
	 * This method compares prepaidLimit of prepaidpriority1 surface and prepaidpriority2 surface, and appends the 
	 * lesser value to adjPepraidLimit in output file. 
	 * for eg: prepaidPriority1 is BLUEDART and prepaidPriority2 is GATI, it compares prepaidlimit of both	
	 * logisticpartners and appends the lesser to both logisticpartner in output file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartners
	 */
	private  void getSurfacePrepaidPriorities(final PriorityMaster priorityMaster,final List<LogisticPartner> logisticPartners){
				final String logisticPartnerName1=priorityMaster.getSurfacePrepaidPriority1();
				final String logisticPartnerName2=priorityMaster.getSurfacePrepaidPriority2();
				final LogisticPartner logisticPartnerResult1=getLogisticPartnerByName(logisticPartnerName1, logisticPartners);
				final LogisticPartner logisticPartnerResult2=getLogisticPartnerByName(logisticPartnerName2, logisticPartners);
				if(logisticPartnerResult1!=null && logisticPartnerResult2==null){
					logisticPartnerResult1.setAdjPrepaidLimit(logisticPartnerResult1.getCodLimit());
				}else if(logisticPartnerResult1==null && logisticPartnerResult2!=null){
					logisticPartnerResult2.setAdjPrepaidLimit(logisticPartnerResult2.getCodLimit());
				}
				else if(logisticPartnerResult1!=null && logisticPartnerResult2!=null){
				final String res4=getMinValueForPrepaid(logisticPartnerResult1, logisticPartnerResult2);
				logisticPartnerResult1.setAdjPrepaidLimit(res4);
				logisticPartnerResult2.setAdjPrepaidLimit(res4);
				}
	}
	
	/**
	 * This method validates if logistic partner is present in priorities then corresponding logistic partner
	 * cod column should have yes, if not it would raise validation error in validation file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartners
	 * @throws IOException 
	 */
	private  void validateCod(final PriorityMaster priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer,final int rowNumber) throws IOException{
		final int surfaceModeStartingIndex=Integer.parseInt(LogisticPartnerUtil.prop.getProperty("surfaceModeStartingIndex"));
		if(logisticPartner.getIndex()>=surfaceModeStartingIndex){
			if(priorityMaster.getSurfaceCodPriority1().length()>1){
				validateCodOfPriorities(priorityMaster.getSurfaceCodPriority1(), logisticPartner, writer,rowNumber);
			}
			if(priorityMaster.getSurfaceCodPriority2().length()>1){
				validateCodOfPriorities(priorityMaster.getSurfaceCodPriority2(), logisticPartner, writer,rowNumber);
			}
			transform(priorityMaster.getSurfaceCodPriority1(),priorityMaster.getSurfaceCodPriority2(), logisticPartner);
		}// if condition for surface mode validation of logisticPartners
		else{
			if(priorityMaster.getAirCodPriority1().length()>1){
				validateCodOfPriorities(priorityMaster.getAirCodPriority1(), logisticPartner, writer,rowNumber);
			}// if condition for priority1 is not empty
			if(priorityMaster.getAirCodPriority2().length()>1){
				validateCodOfPriorities(priorityMaster.getAirCodPriority2(), logisticPartner, writer,rowNumber);
			}// if condition for priority2 is not empty
			transform(priorityMaster.getAirCodPriority1(),priorityMaster.getAirCodPriority2(), logisticPartner);
		}
		}// getting logistic partner details.
	
	
	/**
	 * This method validates if logistic partner is present in priorities then corresponding logistic partner
	 * cod limit should have value, if not it would raise validation error in validation file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartners
	 * @throws IOException 
	 */
	private void validateCodLimit(final PriorityMaster priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer,final int rowNumber) throws IOException{
		final int surfaceModeStartingIndex=Integer.parseInt(LogisticPartnerUtil.prop.getProperty("surfaceModeStartingIndex"));
		if(logisticPartner.getIndex()>=surfaceModeStartingIndex){
			
			if(!priorityMaster.getSurfaceCodPriority1().isEmpty()){
				validateCodLimitOfPriorities(priorityMaster.getSurfaceCodPriority1(), logisticPartner, writer,rowNumber);
			}
			if(!priorityMaster.getSurfaceCodPriority2().isEmpty()){
				validateCodLimitOfPriorities(priorityMaster.getSurfaceCodPriority1(), logisticPartner, writer,rowNumber);
			}
			if(!logisticPartner.getName().equalsIgnoreCase(priorityMaster.getSurfaceCodPriority1()) &&
					!logisticPartner.getName().equalsIgnoreCase(priorityMaster.getSurfaceCodPriority2())){
				logisticPartner.setCodLimit(logisticPartner.getCodLimit());
			}
	}
		else {
			if(priorityMaster.getAirCodPriority1().length()>1){
				validateCodLimitOfPriorities(priorityMaster.getAirCodPriority1(), logisticPartner, writer,rowNumber);
			}
			if(priorityMaster.getAirCodPriority2().length()>1){
				validateCodLimitOfPriorities(priorityMaster.getAirCodPriority2(), logisticPartner, writer,rowNumber);
			}
			if(!logisticPartner.getName().equalsIgnoreCase(priorityMaster.getAirCodPriority1()) &&
					!logisticPartner.getName().equalsIgnoreCase(priorityMaster.getAirCodPriority2())){
				logisticPartner.setCodLimit(logisticPartner.getCodLimit());
			}
		}
		
	}
	
	/**
	 * This method validates if logistic partner is present in priorities then corresponding logistic partner
	 * cod limit should have value, if not it would raise validation error in validation file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartners
	 * @throws IOException 
	 */
	private void validatePrepaidLimit(final PriorityMaster priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer,final int rowNumber) throws IOException{
		final int surfaceModeStartingIndex=Integer.parseInt(LogisticPartnerUtil.prop.getProperty("surfaceModeStartingIndex"));
		if(logisticPartner.getIndex()>=surfaceModeStartingIndex){
			if(priorityMaster.getSurfacePrepaidPriority1().length()>1){
				validatePrepaidLimitOfPriorities(priorityMaster.getSurfacePrepaidPriority1(), logisticPartner, writer,rowNumber);
			}
			if(priorityMaster.getSurfacePrepaidPriority2().length()>1){
				validatePrepaidLimitOfPriorities(priorityMaster.getSurfacePrepaidPriority2(), logisticPartner, writer,rowNumber);
			}
			if(!logisticPartner.getName().equalsIgnoreCase(priorityMaster.getSurfacePrepaidPriority1()) &&
					!logisticPartner.getName().equalsIgnoreCase(priorityMaster.getSurfacePrepaidPriority2())){
				logisticPartner.setPrepaidLimit(logisticPartner.getPrepaidLimit());
			}
		}
		else{
			if(priorityMaster.getAirPrepaidPriority1().length()>1){
				validatePrepaidLimitOfPriorities(priorityMaster.getAirPrepaidPriority1(), logisticPartner, writer,rowNumber);
			}
			if(priorityMaster.getAirPrepaidPriority2().length()>1){
				validatePrepaidLimitOfPriorities(priorityMaster.getAirPrepaidPriority2(), logisticPartner, writer,rowNumber);
			}
			if(!logisticPartner.getName().equalsIgnoreCase(priorityMaster.getAirPrepaidPriority1()) &&
					!logisticPartner.getName().equalsIgnoreCase(priorityMaster.getAirPrepaidPriority2())){
				logisticPartner.setPrepaidLimit(logisticPartner.getPrepaidLimit());
			}
		}
	}

	/**
	 * This method takes what ever the input in carea,cscrcd,cloctype,newzone these columns and 
	 * write these to csv file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartners
	 * @throws IOException 
	 */
	private  void validateCareatoNewzone(final PriorityMaster priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer) throws IOException{
			logisticPartner.setCarea(logisticPartner.getCarea());
			logisticPartner.setCscrcd(logisticPartner.getCscrcd());
			logisticPartner.setCloctype(logisticPartner.getCloctype());
			logisticPartner.setNewzone(logisticPartner.getNewzone());
			//System.out.println("c "+logisticPartner.getCarea()+" cs "+logisticPartner.getCscrcd()+" cl "+logisticPartner.getCloctype()
				//	+" n "+logisticPartner.getNewzone());
	}
	
	/**
	 * This method should have numeric values only.
	 * 
	 * @param priorityMaster
	 * @param logisticPartners
	 * @throws IOException 
	 */
	private void validateTransitTatPrepaid(final PriorityMaster priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer,final int rowNumber) throws IOException{
			final String transitTatPrepaid = logisticPartner.getTransitTatPrepaid();
			if(!transitTatPrepaid.equals(",")){
				String tatPrepaid=transitTatPrepaid.substring(0,transitTatPrepaid.length()-1);
				//this conversion is for not allowing negitive numbers to transit tat prepaid column
				double tatPrepaidResult=Double.parseDouble(tatPrepaid);
				if(tatPrepaidResult>0){
			logisticPartner.setTransitTatPrepaid(String.valueOf(tatPrepaidResult).split("\\.")[0]+ExcelConstants.ADD_COMMA);
				}else{
					writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
							logisticPartner.getName()+LogisticPartnerUtil.prop.getProperty("transitTatPrepaid")+ExcelConstants.NEW_LINE);
				}
			}else{
				logisticPartner.setTransitTatPrepaid(transitTatPrepaid);
			}
	}
	
	/**
	 * This method should have numeric values only.
	 * 
	 * @param priorityMaster
	 * @param logisticPartners
	 * @throws IOException 
	 */
	private  void validateTransitTatCod(final PriorityMaster priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer,final int rowNumber) throws IOException{
			final String transitTatCod = logisticPartner.getTransitTatCod();
			if(!transitTatCod.equals(",")){
				String tatCod=transitTatCod.substring(0,transitTatCod.length()-1);
				//this conversion is for not allowing negitive numbers to transit tat prepaid column
				double tatCodResult=Double.parseDouble(tatCod);
				if(tatCodResult>0){
			logisticPartner.setTransitTatPrepaid(String.valueOf(tatCodResult).split("\\.")[0]+ExcelConstants.ADD_COMMA);
				}else{
					writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
							logisticPartner.getName()+LogisticPartnerUtil.prop.getProperty("transitTatCod")+ExcelConstants.NEW_LINE);
				}
			}else{
				logisticPartner.setTransitTatCod(transitTatCod);
			}
			//System.out.println("tcr "+logisticPartner.getTransitTatCod());
	}
	
	/**
	 * This method takes what ever the column input is and display in csv file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartners
	 * @throws IOException 
	 */
	private void validateFormRequired(final PriorityMaster priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer) throws IOException{
			final String formReuired=logisticPartner.getFormRequired();
			if(formReuired.equalsIgnoreCase("YES,")){
				logisticPartner.setFormRequired("Y,");
			}
			else if(formReuired.equalsIgnoreCase("NO,")){
				logisticPartner.setFormRequired("N,");
			}
			else{
				logisticPartner.setFormRequired(ExcelConstants.ADD_COMMA);
			}
			logisticPartner.setFormRequired(logisticPartner.getFormRequired());
	}
	
	/**
	 * This method validates if logistic partner is present in priorities then corresponding logistic partner
	 * isreturnpincode column should have yes/no, if not it would raise validation error in validation file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartner
	 * @param writer
	 * @param row
	 * @throws IOException
	 */
	private void validateIsReturnPincode(final PriorityMaster priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer,final int rowNumber) throws IOException{
		final int surfaceModeStartingIndex=Integer.parseInt(LogisticPartnerUtil.prop.getProperty("surfaceModeStartingIndex"));
			if(logisticPartner.getIndex()<surfaceModeStartingIndex){
			if(priorityMaster.getAirPrepaidPriority1().length()>1){
				//if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getAirPrepaidPriority1())){
				validatePincodeOfPriorities(priorityMaster.getAirPrepaidPriority1(), logisticPartner, writer,rowNumber);
				//}
			}
			 if(priorityMaster.getAirPrepaidPriority2().length()>1){
				//if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getAirPrepaidPriority2())){
				validatePincodeOfPriorities(priorityMaster.getAirPrepaidPriority2(), logisticPartner, writer,rowNumber);
				//}
			}
			 if(priorityMaster.getAirCodPriority1().length()>1){
				//if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getAirCodPriority1())){
				validatePincodeOfPriorities(priorityMaster.getAirCodPriority1(), logisticPartner, writer,rowNumber);
				//}
			}
			if(priorityMaster.getAirCodPriority2().length()>1){
				//if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getAirCodPriority2())){
				validatePincodeOfPriorities(priorityMaster.getAirCodPriority2(), logisticPartner, writer,rowNumber);
				//}
			}
			if(priorityMaster.getAirCodPriority1().length()==1 || priorityMaster.getAirCodPriority2().length()==1 ||
					priorityMaster.getAirPrepaidPriority1().length()==1 || priorityMaster.getAirPrepaidPriority2().length()==1){
				//System.out.println("yes");
				final String isReturnPincode = logisticPartner.getIsReturnPincode();
				transform(isReturnPincode, logisticPartner);
			}
			}else if(logisticPartner.getIndex()>=surfaceModeStartingIndex){
			if(priorityMaster.getSurfaceCodPriority1().length()>1){
				//if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getSurfaceCodPriority1())){
				validatePincodeOfPriorities(priorityMaster.getSurfaceCodPriority1(), logisticPartner, writer,rowNumber);
				//}
			}
			if(priorityMaster.getSurfaceCodPriority2().length()>1){
				//if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getSurfaceCodPriority2())){
				validatePincodeOfPriorities(priorityMaster.getSurfaceCodPriority2(), logisticPartner, writer,rowNumber);
				//}
			}
			if(priorityMaster.getSurfacePrepaidPriority1().length()>1){
				//if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getSurfacePrepaidPriority1())){
				validatePincodeOfPriorities(priorityMaster.getSurfacePrepaidPriority1(), logisticPartner, writer,rowNumber);
				//}
			}
			if(priorityMaster.getSurfacePrepaidPriority2().length()>1){
				//if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getSurfacePrepaidPriority2())){
				validatePincodeOfPriorities(priorityMaster.getSurfacePrepaidPriority2(), logisticPartner, writer,rowNumber);
				//}
			}
			if(priorityMaster.getSurfaceCodPriority1().length()==1 || priorityMaster.getSurfaceCodPriority2().length()==1 ||
					priorityMaster.getSurfacePrepaidPriority1().length()==1 || priorityMaster.getSurfacePrepaidPriority2().length()==1){
				//System.out.println("yes");
				final String isReturnPincode = logisticPartner.getIsReturnPincode();
				transform(isReturnPincode, logisticPartner);
			}
			}
	}
	
	
	
	/**
	 * This method takes input from pickup and writes into the csv file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartner
	 * @param writer
	 * @param row
	 * @throws IOException
	 */
	private void validatePickup(final PriorityMaster priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer,final int rowNumber) throws IOException{
			final String pickUp=logisticPartner.getPickUp();
			if(pickUp.equalsIgnoreCase("YES,")){
				logisticPartner.setPickUp("Y,");
			}else if(pickUp.equalsIgnoreCase("NO,")){
				logisticPartner.setPickUp("N,");
			}else if(!pickUp.equalsIgnoreCase("YES") && !pickUp.equalsIgnoreCase("NO") && pickUp.length()>1){
				writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
						logisticPartner.getName()+LogisticPartnerUtil.prop.getProperty("pickUpValidation")+ExcelConstants.NEW_LINE);
			}
			logisticPartner.setPickUp(logisticPartner.getPickUp());
		}
	//}
	
	/**
	 * 
	 * This method checks priority names and assign p001 for priority1 logistic partner 
	 * and p002 for priority2 logistic partner.
	 * for eg: codPriority1 : GATI and codPriority2 is FEDEX. so in out put file while 
	 * wrting the data of GATI CodPriority column will have P001 and Fedex will have P002.
	 * 
	 * @param priorityMaster
	 * @param logisticPartner
	 * @param writer
	 * @throws IOException
	 */
	private void assigningOfCodPriority(final PriorityMaster priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer) throws IOException{
		final int surfaceModeStartingIndex=Integer.parseInt(LogisticPartnerUtil.prop.getProperty("surfaceModeStartingIndex"));
		if(logisticPartner.getIndex()<surfaceModeStartingIndex){
			//System.out.println("cod priority1 "+priorityMaster.getAirCodPriority1().length() +" cp2 "+priorityMaster.getAirCodPriority2().length());
		if(priorityMaster.getAirCodPriority1().length()>1){
			//System.out.println("> 1");
			if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getAirCodPriority1())){
				logisticPartner.setCodPriority("P001,");
				
			}//if condition for checking priority equal or not with logisticpartner name
			else if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getAirCodPriority2())){
				logisticPartner.setCodPriority("P002,");
			}
			else{
				logisticPartner.setCodPriority(ExcelConstants.ADD_COMMA);
			}
		}
		else{
			logisticPartner.setCodPriority(ExcelConstants.ADD_COMMA);
		}
		}else{
			if(priorityMaster.getSurfaceCodPriority1().length()>1){
				if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getSurfaceCodPriority1())){
					logisticPartner.setCodPriority("P001,");
					
				}//if condition for checking priority equal or not with logisticpartner name
				else if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getSurfaceCodPriority2())){
					logisticPartner.setCodPriority("P002,");
				}
				else{
					logisticPartner.setCodPriority(ExcelConstants.ADD_COMMA);
				}
			}
			else{
				logisticPartner.setCodPriority(ExcelConstants.ADD_COMMA);
			}
		}
	}
	
	/**
	 * 
	 * This method checks priority names and assign p001 for priority1 logistic partner 
	 * and p002 for priority2 logistic partner.
	 * for eg: prepaidPriority1 : GATI and prepaidPriority2 is FEDEX. so in out put file while 
	 * wrting the data of GATI prepaidPriority column will have P001 and Fedex will have P002.
	 * 
	 * @param priorityMaster
	 * @param logisticPartner
	 * @param writer
	 * @throws IOException
	 */
	private void assigningOfPrepaidPriority(final PriorityMaster priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer) throws IOException{
		final int surfaceModeStartingIndex=Integer.parseInt(LogisticPartnerUtil.prop.getProperty("surfaceModeStartingIndex"));
		if(logisticPartner.getIndex()<surfaceModeStartingIndex){
		if(priorityMaster.getAirPrepaidPriority1().length()>1){
			if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getAirPrepaidPriority1())){
				logisticPartner.setPrepaidPriority("P001,");
				
			}//if condition for checking priority equal or not with logisticpartner name
			else if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getAirPrepaidPriority2())){
				logisticPartner.setPrepaidPriority("P002,");
			}
			else{
				logisticPartner.setPrepaidPriority(ExcelConstants.ADD_COMMA);
			}
		}
		else{
			logisticPartner.setPrepaidPriority(ExcelConstants.ADD_COMMA);
		}
		}else{
			if(priorityMaster.getSurfacePrepaidPriority1().length()>1){
				if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getSurfacePrepaidPriority1())){
					logisticPartner.setPrepaidPriority("P001,");
					
				}//if condition for checking priority equal or not with logisticpartner name
				else if(logisticPartner.getName().equalsIgnoreCase(priorityMaster.getSurfacePrepaidPriority2())){
					logisticPartner.setPrepaidPriority("P002,");
				}
				else{
					logisticPartner.setPrepaidPriority(ExcelConstants.ADD_COMMA);
				}
			}
			else{
				logisticPartner.setPrepaidPriority(ExcelConstants.ADD_COMMA);
			}
		}
	}
	
	/**
	 * 
	 * @param logisticPartner1
	 * @param logisticPartner2
	 * @param logisticPartner
	 */
	private void transform(final String logisticPartner1,final String logisticPartner2,final LogisticPartner logisticPartner){
		if(!logisticPartner.getName().equalsIgnoreCase(logisticPartner1) &&
				!logisticPartner.getName().equalsIgnoreCase(logisticPartner2)){
			final String cod=logisticPartner.getCod();
			if(cod.equalsIgnoreCase("YES,")){
				logisticPartner.setCod("Y,");
			}else if(cod.equalsIgnoreCase("NO")){
				logisticPartner.setCod("N");
			}else{
				logisticPartner.setCod(",");
			}
		}
	}
	
	/**
	 * In this method if a logistic partner is present in priorities then isreturn pincode of corresponding
	 * logistic partner should have yes/no else it will raise validation in validation file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartner
	 * @param writer
	 * @param rowNumber
	 * @throws IOException
	 */
	private void validatePincodeOfPriorities(final String priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer,final int rowNumber) throws IOException{
		if(logisticPartner.getName().equalsIgnoreCase(priorityMaster)){
			final String isReturnPincode=logisticPartner.getIsReturnPincode();
			if(isReturnPincode.equals(",")){
				writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
						logisticPartner.getName()+LogisticPartnerUtil.prop.getProperty("isReturnPincode")+ExcelConstants.NEW_LINE);
				}//if condition for checking prepaid limit is empty or not,if it is empty it will raise
			else{
				transform(isReturnPincode, logisticPartner);
				
			}
		}//if condition for checking priority equal or not with logisticpartner name
		else{
			final String isReturnPincode=logisticPartner.getIsReturnPincode();
			transform(isReturnPincode, logisticPartner);
		}
	}
	
	/**
	 * 
	 * In this method if priorities have logistic partner then corresponding logistic partner should 
	 * have value, else it will raise validation in validation file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartner
	 * @param writer
	 * @param rowNumber
	 * @throws IOException
	 */
	private void validatePrepaidLimitOfPriorities(final String priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer,final int rowNumber) throws IOException{
			if(logisticPartner.getName().equalsIgnoreCase(priorityMaster)){
			final String prepaidLimit=logisticPartner.getPrepaidLimit();
			if(prepaidLimit.equals(",")){
				writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
						logisticPartner.getName()+LogisticPartnerUtil.prop.getProperty("prepaidLimitMandatory")+ExcelConstants.NEW_LINE);
				}//if condition for checking prepaid limit is empty or not.
			else{
				logisticPartner.setPrepaidLimit(prepaidLimit);
			}
			}//if condition for checking priority equal or not with logisticpartner name
	}
	
	/**
	 * 
	 * In this method if priorities have logistic partner then corresponding logistic partner should 
	 * have value yes, else it will raise validation in validation file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartner
	 * @param writer
	 * @param rowNumber
	 * @throws IOException
	 */
	private void validateCodOfPriorities(final String priorityMaster,final LogisticPartner logisticPartner,final FileWriter writer,
			final int rowNumber) throws IOException{
	
		if(logisticPartner.getName().equalsIgnoreCase(priorityMaster)){
			final String cod=logisticPartner.getCod();
				if(cod.equalsIgnoreCase("YES,")){
					logisticPartner.setCod("Y,");
				}else if(!cod.equalsIgnoreCase("YES,")){
					writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
							logisticPartner.getName()+LogisticPartnerUtil.prop.getProperty("codColumnValidation")+ExcelConstants.NEW_LINE);
				}
			}//if condition for checking priority equal or not with logisticpartner name
		
	}
	
	/**
	 * 
	 * In this method if priorities have logistic partner then corresponding logistic partner should 
	 * have  value, else it will raise validation in validation file.
	 * 
	 * @param priorityMaster
	 * @param logisticPartner
	 * @param writer
	 * @param rowNumber
	 * @throws IOException
	 */
	private void validateCodLimitOfPriorities(final String priorityMaster,final LogisticPartner logisticPartner,
			final FileWriter writer,final int rowNumber) throws IOException{
		if(logisticPartner.getName().equalsIgnoreCase(priorityMaster)){
			final String codLimit=logisticPartner.getCodLimit();
			if(codLimit.equals(",")){
				writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
						logisticPartner.getName()+LogisticPartnerUtil.prop.getProperty("codLimitMandatory")+ExcelConstants.NEW_LINE);
				}//if condition for checking cod limit is empty or not.
			else{
				logisticPartner.setCodLimit(codLimit);
			}
			}//if condition for checking priority equal or not with logisticpartner name
	}
	
	/**
	 *
	 * This method compares result of the cod limit and assigns the lesser value.
	 * @param logisticPartner1
	 * @param logisticPartner2
	 * @return
	 */
	private String getMinValueForCOD(final LogisticPartner logisticPartner1,final LogisticPartner logisticPartner2){
		String result="";
		//System.out.println("l"+logisticPartner1+" 2 "+logisticPartner2);
		if(logisticPartner1!=null && logisticPartner2!=null){
		final String codValue1= logisticPartner1.getCodLimit().substring(0, logisticPartner1.getCodLimit().length()-1);
		final String codValue2= logisticPartner2.getCodLimit().substring(0, logisticPartner2.getCodLimit().length()-1);
		
			if(!codValue1.isEmpty() && !codValue2.isEmpty()){
			final double result1=Double.valueOf(codValue1);
			
			final double result2=Double.valueOf(codValue2);
		if(result1==result2){
			result=result1+ExcelConstants.ADD_COMMA;
		}
		else if(result1>result2){
			result=result2+ExcelConstants.ADD_COMMA;
		}
		else if(result1<result2){
			result=result1+ExcelConstants.ADD_COMMA;
		}
		else{
			result=""+ExcelConstants.ADD_COMMA;
		}
		}
		}
		return result;
		
	}
	
	/**
	 * This method compares result of the prepaid limit and assigns the lesser value.
	 * 
	 * @param logisticPartner1
	 * @param logisticPartner2
	 * @return
	 */
	private String getMinValueForPrepaid(final LogisticPartner logisticPartner1,final LogisticPartner logisticPartner2){
		String result="";
		
		if(logisticPartner1!=null && logisticPartner2!=null){
		final String codValue1= logisticPartner1.getPrepaidLimit().substring(0, logisticPartner1.getPrepaidLimit().length()-1);
		final String codValue2= logisticPartner2.getPrepaidLimit().substring(0, logisticPartner2.getPrepaidLimit().length()-1);;
		if(!codValue1.isEmpty() && !codValue2.isEmpty()){
		final double result1=Double.valueOf(codValue1);
		
		final double result2=Double.valueOf(codValue2);
		if(result1==result2){
			result=result1+ExcelConstants.ADD_COMMA;
		}
		else if(result1>result2){
			result=result2+ExcelConstants.ADD_COMMA;
		}
		else if(result1<result2){
			result=result1+ExcelConstants.ADD_COMMA;
		}
		else{
			result=""+ExcelConstants.ADD_COMMA;
		}
		}
		}
		return result;
		
	}
	
	/**
	 * 
	 * @param logisticPartnerName
	 * @param logisticPartners
	 * @return
	 */
	private LogisticPartner getLogisticPartnerByName(final String logisticPartnerName,final List<LogisticPartner> logisticPartners){
		LogisticPartner lp=null;
		for(final LogisticPartner partner : logisticPartners){
			if(partner.getName().equalsIgnoreCase(logisticPartnerName)){
				lp=partner;
				break;
			}
		}
		return lp;
	}
	

	/**
	 * 
	 * @param isReturnPincode
	 * @param logisticPartner
	 */
	private void transform(final String isReturnPincode,final LogisticPartner logisticPartner){
		if(isReturnPincode.equalsIgnoreCase("Yes,")){
			logisticPartner.setIsReturnPincode("Y,");
		}
		else if(isReturnPincode.equalsIgnoreCase("No,")){
			logisticPartner.setIsReturnPincode("N,");
		}
		else {
		logisticPartner.setIsReturnPincode(isReturnPincode);
		}
	}
	
}