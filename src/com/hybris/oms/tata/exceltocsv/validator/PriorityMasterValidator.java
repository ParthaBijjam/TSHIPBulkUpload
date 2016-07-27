package com.hybris.oms.tata.exceltocsv.validator;

import java.io.FileWriter;
import java.io.IOException;

import com.hybris.oms.tata.exceltocsv.util.ExcelConstants;
import com.hybris.oms.tata.exceltocsv.util.LogisticPartnerUtil;
import com.hybris.oms.tata.pincodesupload.pojo.PriorityMaster;

/**
 * 
 * This method validates the data of priorities, if this priorities fails to have validation check
 * then it will raise validation in validation file.
 *
 */
public class PriorityMasterValidator {


	/**
	 * this method validates priority master data. it returns true if all the priorities matches validation rules,
	 * else it will throw exception with respective message.
	 * 
	 * @param priorityMaster
	 * @return
	 * @throws IOException 
	 */
	public boolean validate(final PriorityMaster priorityMaster,final FileWriter writer,final int rowNumber) throws IOException{
		boolean result=false;
		if(validateAirAndSurfacePrepaidPriority1(priorityMaster,writer,rowNumber))
		if(validatePriorities(priorityMaster.getAirPrepaidPriority1(), priorityMaster.getAirPrepaidPriority2(),
				LogisticPartnerUtil.prop.getProperty("prepaidPriority1IsEmptyAir"),writer,rowNumber))
		if(validatePriorities(priorityMaster.getAirCodPriority1(), priorityMaster.getAirCodPriority2(),
				LogisticPartnerUtil.prop.getProperty("codPriority1IsEmptyAir"),writer,rowNumber))
			if(validatePriorities(priorityMaster.getSurfacePrepaidPriority1(), priorityMaster.getSurfacePrepaidPriority2(),
				LogisticPartnerUtil.prop.getProperty("prepaidPriority1IsEmptySurface"),writer,rowNumber))
			if(validatePriorities(priorityMaster.getSurfaceCodPriority1(), priorityMaster.getSurfaceCodPriority2(),
				LogisticPartnerUtil.prop.getProperty("codPriority1IsEmptySurface"),writer,rowNumber))
			if(checkPrioritiesforEquality(priorityMaster.getAirPrepaidPriority1(), priorityMaster.getAirPrepaidPriority2(),
				LogisticPartnerUtil.prop.getProperty("prepaidPriority1AndPriority2SameAir"),writer,rowNumber))
			if(checkPrioritiesforEquality(priorityMaster.getAirCodPriority1(), priorityMaster.getAirCodPriority2(),
				LogisticPartnerUtil.prop.getProperty("codPriority1AndPriority2SameSurface"),writer,rowNumber))
			if(checkPrioritiesforEquality(priorityMaster.getSurfacePrepaidPriority1(), priorityMaster.getSurfacePrepaidPriority2(),
				LogisticPartnerUtil.prop.getProperty("prepaidPriority1AndPriority2SameSurface"),writer,rowNumber))
			if(checkPrioritiesforEquality(priorityMaster.getSurfaceCodPriority1(), priorityMaster.getSurfaceCodPriority2(),
				LogisticPartnerUtil.prop.getProperty("codPriority1AndPriority2SameSurface"),writer,rowNumber))
				if(validateAirAndSurfacePriorities(priorityMaster,writer,rowNumber)){
					result = true;
				}
		return result;
	}
	
	/**
	 * if any one of the logistic partner name is not matched with these GATI,BLUEDART,GOJAVAS,ROADRUNNR,FEDEX
	 * name, it throws validation exception with respective message.
	 * 
	 * @param priorityMaster
	 * @return
	 * @throws IOException 
	 */
	private boolean validateAirAndSurfacePriorities(final PriorityMaster priorityMaster,final FileWriter writer,
			final int rowNumber) throws IOException{
		boolean result=false;
		if(priorityMaster.getAirPrepaidPriority1().length()>1 && !LogisticPartnerUtil.containsLogisticPartnerName(priorityMaster.getAirPrepaidPriority1())){
			writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
					LogisticPartnerUtil.prop.getProperty("prepaidPriority1NameValidationAir")+ExcelConstants.NEW_LINE);
		} else if(priorityMaster.getAirPrepaidPriority2().length()>1 && !LogisticPartnerUtil.containsLogisticPartnerName(priorityMaster.getAirPrepaidPriority2())){
			writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
					LogisticPartnerUtil.prop.getProperty("prepaidPriority2NameValidationAir")+ExcelConstants.NEW_LINE);
		} else if(priorityMaster.getAirCodPriority1().length()>1 && !LogisticPartnerUtil.containsLogisticPartnerName(priorityMaster.getAirCodPriority1())){
			writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
					LogisticPartnerUtil.prop.getProperty("codPriority1NameValidationAir")+ExcelConstants.NEW_LINE);
		} else if(priorityMaster.getAirCodPriority2().length()>1 && !LogisticPartnerUtil.containsLogisticPartnerName(priorityMaster.getAirCodPriority2())){
			writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
					LogisticPartnerUtil.prop.getProperty("codPriority2NameValidationAir")+ExcelConstants.NEW_LINE);
		} else if(priorityMaster.getSurfacePrepaidPriority1().length()>1 && !LogisticPartnerUtil.containsLogisticPartnerName(priorityMaster.getSurfacePrepaidPriority1())){
			writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
					LogisticPartnerUtil.prop.getProperty("prepaidPriority1NameValidationSurface")+ExcelConstants.NEW_LINE);
		} else if(priorityMaster.getSurfacePrepaidPriority2().length()>1 && !LogisticPartnerUtil.containsLogisticPartnerName(priorityMaster.getSurfacePrepaidPriority2())){
			writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
					LogisticPartnerUtil.prop.getProperty("prepaidPriority2NameValidationSurface")+ExcelConstants.NEW_LINE);
		} else if(priorityMaster.getSurfaceCodPriority1().length()>1 && !LogisticPartnerUtil.containsLogisticPartnerName(priorityMaster.getSurfaceCodPriority1())){
			writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
					LogisticPartnerUtil.prop.getProperty("codPriority1NameValidationSurface")+ExcelConstants.NEW_LINE);
		} else if(priorityMaster.getSurfaceCodPriority2().length()>1 && !LogisticPartnerUtil.containsLogisticPartnerName(priorityMaster.getSurfaceCodPriority2())){
			writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
					LogisticPartnerUtil.prop.getProperty("codPriority2NameValidationSurface")+ExcelConstants.NEW_LINE);
		}else {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * if priority1_prepaid_air is empty and priority1_prepaid_surface is empty,throws validation exception with 
	 * respective message. 
	 * 
	 * @param priorityMaster
	 * @return
	 * @throws IOException 
	 */
	private boolean validateAirAndSurfacePrepaidPriority1(final PriorityMaster priorityMaster,final FileWriter writer,
			final int rowNumber) throws IOException{
		if(priorityMaster.getAirPrepaidPriority1().length()==1 &&  priorityMaster.getSurfacePrepaidPriority1().length()==1){
			writer.write((ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+LogisticPartnerUtil.prop.getProperty("prepaidPriority1AirOrSurfaceIsEmpty")+
					ExcelConstants.NEW_LINE));
		} 
		
		return true;
	}
	
	/**
	 * 
	 * if logisticPartner1 is empty and logisticPartner2 has one of the logisticPartner name then it will
	 * throws validation exception with respective message.
	 * 
	 * @param logisticPartner1
	 * @param logisticPartner2
	 * @param validationMessage
	 * @return
	 * @throws IOException 
	 */
	private boolean validatePriorities(final String logisticPartner1,final String logisticPartner2,final String validationMessage,
			final FileWriter writer,final int rowNumber) throws IOException{
		if(logisticPartner1.length()==1 && logisticPartner2.length()>1 ){
			writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+validationMessage+ExcelConstants.NEW_LINE);
		}
		return true;
	}
	
	/**
	 * 
	 * if both of the priorities have same logistic partner name then it will throw validation exception with
	 * respective message.
	 * 
	 * @param logisticPartner1
	 * @param logisticPartner2
	 * @param validationMessage
	 * @return
	 * @throws IOException 
	 */
	private boolean checkPrioritiesforEquality(final String logisticPartner1,final String logisticPartner2,final String validationMessage,
			final FileWriter writer,final int rowNumber) throws IOException{
		if((logisticPartner1.length()>1 && logisticPartner2.length()>1) && 
				logisticPartner1.equalsIgnoreCase(logisticPartner2)){
			writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
					validationMessage+ExcelConstants.NEW_LINE);
		}
		return true;
	}
	
	
}
