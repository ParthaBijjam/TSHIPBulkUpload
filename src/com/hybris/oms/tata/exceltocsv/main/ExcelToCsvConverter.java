package com.hybris.oms.tata.exceltocsv.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hybris.oms.tata.exceltocsv.util.CsvServiceFactory;
import com.hybris.oms.tata.exceltocsv.util.ExcelConstants;
import com.hybris.oms.tata.exceltocsv.util.LogisticPartnerUtil;
import com.hybris.oms.tata.exceltocsv.util.RowCountUtil;
import com.hybris.oms.tata.exceltocsv.validator.LogisticPartnerValidator;
import com.hybris.oms.tata.exceltocsv.validator.PriorityMasterValidator;
import com.hybris.oms.tata.pincodesupload.pojo.LogisticPartner;
import com.hybris.oms.tata.pincodesupload.pojo.PriorityMaster;

/**
 * 
 * This class takes the input of excel and process the required validaions, if there are no validations then the 
 * output will be generated in CSV file in particular location,else validation messages can be seen in validation file.
 *
 */
public class ExcelToCsvConverter {
	
	
	private static String propertiesPath;
	
	
	
	private Map<String,String> stateCodesMap= new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);

	public static void setPropertiesPath(final String propertiesPath)
	{
		System.setProperty("stateCodePropertiesfilePath", propertiesPath);
		ExcelToCsvConverter.propertiesPath = propertiesPath;
		System.out.println("path"+propertiesPath);
		
	}
	public void setStateCodesMap(final Map<String,String> stateCodesMap){
		
		this.stateCodesMap=stateCodesMap;
	}
	
	public Map<String, String> getStateCodesMap() {
		return stateCodesMap;
	}


	public ExcelToCsvConverter(final String propPath){
		System.setProperty("stateCodePropertiesfilePath", propPath);
	}

	/**
	 * This method reads the input from excel file and loads the logisticPartner names,
	 * then validates priority masters data (i.e) all the
	 * priorities of air and surface. if validation of prioritymaster is successfull then 
	 * validates all the logisticPartner data and then writes the output to csv file.
	 * 
	 * @param deliveryMode
	 * @param inputFile
	 * @param outputFile
	 * @param validationFile
	 * @param fileNameTimeStamp
	 */
	public void convertEcelToCsv(final String deliveryMode,final File inputFile,final File outputFile,final File validationFile,
			final String fileNameTimeStamp){
		
		XSSFWorkbook wBook = null;
		XSSFSheet sheet=null;
		ArrayList<Integer> logisticPartnerIndexes=null;
		final Map<Integer, String> logisticPartners = new HashMap<Integer, String>();
		
		try
		{
			final File outputPath = new File(outputFile.getParent());
			if (!outputPath.exists()){
				outputPath.mkdirs();
			}if (!outputFile.exists()){
				outputFile.createNewFile();
			}
			final File validationPath = new File(validationFile.getParent());
			if (validationPath != null){
				validationPath.mkdirs();
			}if (!validationFile.exists()){
				validationPath.createNewFile();
			}
			final FileWriter writer = new FileWriter(validationFile);
			wBook = new XSSFWorkbook(new FileInputStream(inputFile));
			logisticPartnerIndexes=new ArrayList<Integer>();
			//final int numberOfSheets = wBook.getNumberOfSheets();
			sheet = wBook.getSheetAt(0);
			final int rowcount = RowCountUtil.determineRowCount(sheet);
			//appending of header data to csv file.
			CsvServiceFactory.getCsvService().appendHeaderData(deliveryMode);
			for(final Row row : sheet){
				final int rowNumber = row.getRowNum() + 1;
				//surfaceModeLastIndex = sheet.getRow(3).getLastCellNum();
				if (row.getRowNum() ==1){
					final int lastColumnIndex = Integer.parseInt(LogisticPartnerUtil.prop.getProperty("lastColumnIndex"));
					final int columnIndex=Integer.parseInt(LogisticPartnerUtil.prop.getProperty("codColumnIndex"));
					
					//store logistic partner names in a MAP
					for (int i = columnIndex ; i < lastColumnIndex; i=i+12){
						final String logisticPartnerName=LogisticPartnerUtil.findLogisticPartnerName(getCellData(row.getCell(i)));
						logisticPartners.put(i, logisticPartnerName);
						logisticPartnerIndexes.add(i);//keep track of starting index of logistic partner data
					}
				}
				
				if(row.getRowNum()>=3 && row.getRowNum()<=rowcount){
					//Get Priority Master first
					final PriorityMaster priorityMaster=new PriorityMaster();
					final LogisticPartnerValidator logisticPartnerValidator = new LogisticPartnerValidator();
					for(int i=0;i<logisticPartnerIndexes.get(0);i++){
						final String data=getCellData(row.getCell(i));
						if(i==0){
							final String pincodeResult=data.substring(0,data.length()-1);
							//System.out.println("pincode"+pincodeResult);
							boolean result= isNumeric(pincodeResult);
							//This condition is for checking whether pincode is numeric or not
							if(result){
							final Double dou = Double.valueOf(pincodeResult);
							int pincode= dou.intValue();
							final int length = (int) (Math.log10(pincode) + 1);
							//This condition is for pincode should have only 6 digitis
							if (length == 6)
							{
								priorityMaster.setPincode(pincode+ExcelConstants.ADD_COMMA);
							}else{
								writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE
										+LogisticPartnerUtil.prop.getProperty("pincode")+ExcelConstants.NEW_LINE);
							}
							}else{
								writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE
										+LogisticPartnerUtil.prop.getProperty("pincode")+ExcelConstants.NEW_LINE);
							}
						}
						else if(i==1)
							priorityMaster.setAirPrepaidPriority1(data);
						else if(i==2)
							priorityMaster.setAirPrepaidPriority2(data);
						else if(i==3)
							priorityMaster.setAirCodPriority1(data);
						else if(i==4)
							priorityMaster.setAirCodPriority2(data);
						else if(i==5)
							priorityMaster.setSurfacePrepaidPriority1(data);
						else if(i==6)
							priorityMaster.setSurfacePrepaidPriority2(data);
						else if(i==7)
							priorityMaster.setSurfaceCodPriority1(data);
						else if(i==8)
							priorityMaster.setSurfaceCodPriority2(data);
						else if(i==9){
							final String city=data;
							if(city.length()==1){
								writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
										ExcelConstants.EMPTY_SPACE+LogisticPartnerUtil.prop.getProperty("city")+ExcelConstants.NEW_LINE);
							}else
							priorityMaster.setCity(data);
						}
						else if(i==10){
							final String state = data.substring(0,data.length()-1).toLowerCase();
							final String stateResult=stateCodesMap.get(state);
							if(stateResult==null){
								writer.write(ExcelConstants.ROW_VALUE+ExcelConstants.EMPTY_SPACE+rowNumber+ExcelConstants.EMPTY_SPACE+
										state+ExcelConstants.EMPTY_SPACE+LogisticPartnerUtil.prop.getProperty("stateValidation")+ExcelConstants.NEW_LINE);
							}
							else{
								final String replaceResult=stateResult.replace("IN-", "");
								priorityMaster.setState(replaceResult+",");
								}
						}
					}//for
					//validate Priority Master data
					final PriorityMasterValidator pm = new PriorityMasterValidator();
					if(pm.validate(priorityMaster,writer,rowNumber)){// if priority mastr is correct validate lp data
						final ArrayList<LogisticPartner> logisticPartners1=new ArrayList<LogisticPartner>();
						for(int i=0;i<logisticPartnerIndexes.size();i++){
							final int index=logisticPartnerIndexes.get(i);//11
							//create Logistic Partner
							final LogisticPartner logisticPartner=new LogisticPartner();
							
							//store logicticPartner starting index from excel sheet into logistic partner object
							logisticPartner.setIndex(index);
							
							//get the name of logistic Partner and store in logistic partner object
							logisticPartner.setName(logisticPartners.get(index));
							int propertiesPopulated=0;
							for(int j=index;j<index+12;j++){
								if(propertiesPopulated==0){
									logisticPartner.setCod(getCellData(row.getCell(j)));
									propertiesPopulated++;
								}
								else if(propertiesPopulated==1){
									logisticPartner.setCodLimit(getCellData(row.getCell(j)));
									propertiesPopulated++;
								}
								else if(propertiesPopulated==2){
									//System.out.println("prepaid limit"+getCellData(row.getCell(j)));
									logisticPartner.setPrepaidLimit(getCellData(row.getCell(j)));
									propertiesPopulated++;
								}
								else if(propertiesPopulated==3){
									logisticPartner.setCarea(getCellData(row.getCell(j)));
									propertiesPopulated++;
								}
								else if(propertiesPopulated==4){
									logisticPartner.setCscrcd(getCellData(row.getCell(j)));
									propertiesPopulated++;
								}
								else if(propertiesPopulated==5){
									logisticPartner.setCloctype(getCellData(row.getCell(j)));
									propertiesPopulated++;
								}
								else if(propertiesPopulated==6){
									logisticPartner.setNewzone(getCellData(row.getCell(j)));
									propertiesPopulated++;
								}
								else if(propertiesPopulated==7){
									logisticPartner.setTransitTatPrepaid(getCellData(row.getCell(j)));
									propertiesPopulated++;
								}
								else if(propertiesPopulated==8){
									logisticPartner.setTransitTatCod(getCellData(row.getCell(j)));
									propertiesPopulated++;
								}
								else if(propertiesPopulated==9){
									logisticPartner.setFormRequired(getCellData(row.getCell(j)));
									propertiesPopulated++;
								}
								else if(propertiesPopulated==10){
									logisticPartner.setIsReturnPincode(getCellData(row.getCell(j)));
									propertiesPopulated++;
								}
								else{
									logisticPartner.setPickUp(getCellData(row.getCell(j)));
									propertiesPopulated=0;
									logisticPartners1.add(logisticPartner);//add whole details of Logistic Partner to list
								}//else
							}//for
						}//for
						//
						
						//Now validate all Logistic Partner Data
						logisticPartnerValidator.validateLogisticPartners(deliveryMode,priorityMaster, logisticPartners1,
								writer,rowNumber);
					}//if PM validated
				}//reading rows while loop
			}//sheet reading for loop
			writer.flush();
			writer.close();
			CsvServiceFactory.getCsvService().appendFooterData(deliveryMode, fileNameTimeStamp);
		//System.out.println("validation file"+validationFile.length());
			CsvServiceFactory.getCsvService().writeData(CsvServiceFactory.getCsvService().getData(),outputFile,validationFile);
			
	}//try
		catch(final Exception e){
			e.printStackTrace();
			
		}//catch
		
		

}
	
	/**
	 * This method handles two types of input data, one is with formulas and other is  without formula. 
	 * 
	 * @param cell
	 * @return
	 */
	public String getCellData(final Cell cell){
				String result="";

				if (cell != null)
				{
					switch (cell.getCellType())
					{
						case Cell.CELL_TYPE_BOOLEAN:
							result=(cell.getBooleanCellValue()+ExcelConstants.ADD_COMMA);
							break;
						case Cell.CELL_TYPE_NUMERIC:
							result=(cell.getNumericCellValue()+ExcelConstants.ADD_COMMA);
							break;
						case Cell.CELL_TYPE_STRING:
							result=(cell.getStringCellValue()+ExcelConstants.ADD_COMMA);
							
							break;
						case Cell.CELL_TYPE_BLANK:
							result=(""+",");
							break;
						case Cell.CELL_TYPE_FORMULA:
							switch (cell.getCachedFormulaResultType())
							{
								case Cell.CELL_TYPE_STRING:
									result=(cell.getStringCellValue()+ExcelConstants.ADD_COMMA);
								break;
								case Cell.CELL_TYPE_NUMERIC:
									result=(cell.getNumericCellValue()+ExcelConstants.ADD_COMMA);
									break;
								case Cell.CELL_TYPE_BLANK:
									result=(""+",");	
								break;
								default:
									result=(cell+"");
							}
							break;
						default:
							result=(cell+"");

					}// switch end
				}

				return result;
			}//


	/**
	 * 
	 * @param s
	 * @return
	 */
	public boolean isNumeric(String pincode) {  
	    return pincode.matches("[-+]?\\d*\\.?\\d+");  
	}
	
	
	
	

}