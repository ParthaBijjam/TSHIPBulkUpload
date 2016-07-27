package com.hybris.oms.tata.pincodesupload.pojo;


public class LogisticPartner {

	
	private String cod;
	private String prepaidLimit;
	private String codLimit;
	private String carea;
	private String cscrcd;
	private String cloctype;
	private String newzone;
	private String transitTatPrepaid;
	private String transitTatCod;
	private String formRequired;
	
	private String name;
	
	private String isReturnPincode;
	private String pickUp;
	
	private String codPriority;
	private String prepaidPriority;
	private String adjCodLimit;
	private String adjPrepaidLimit;

	private int index;// in order for comparing air or surface
	
	
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	public String getPrepaidLimit() {
		return prepaidLimit;
	}
	public void setPrepaidLimit(String prepaidLimit) {
		this.prepaidLimit = prepaidLimit;
	}
	public String getCodLimit() {
		return codLimit;
	}
	public void setCodLimit(String codLimit) {
		this.codLimit = codLimit;
	}
	public String getCarea() {
		return carea;
	}
	public void setCarea(String carea) {
		this.carea = carea;
	}
	public String getCscrcd() {
		return cscrcd;
	}
	public void setCscrcd(String cscrcd) {
		this.cscrcd = cscrcd;
	}
	public String getCloctype() {
		return cloctype;
	}
	public void setCloctype(String cloctype) {
		this.cloctype = cloctype;
	}
	public String getNewzone() {
		return newzone;
	}
	public void setNewzone(String newzone) {
		this.newzone = newzone;
	}
	public String getTransitTatPrepaid() {
		return transitTatPrepaid;
	}
	public void setTransitTatPrepaid(String transitTatPrepaid) {
		this.transitTatPrepaid = transitTatPrepaid;
	}
	public String getTransitTatCod() {
		return transitTatCod;
	}
	public void setTransitTatCod(String transitTatCod) {
		this.transitTatCod = transitTatCod;
	}
	public String getFormRequired() {
		return formRequired;
	}
	public void setFormRequired(String formRequired) {
		this.formRequired = formRequired;
	}
	public String getIsReturnPincode() {
		return isReturnPincode;
	}
	public void setIsReturnPincode(String isReturnPincode) {
		this.isReturnPincode = isReturnPincode;
	}
	public String getPickUp() {
		return pickUp;
	}
	public void setPickUp(String pickUp) {
		this.pickUp = pickUp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public String getCodPriority() {
		return codPriority;
	}
	public void setCodPriority(String codPriority) {
		this.codPriority = codPriority;
	}
	public String getPrepaidPriority() {
		return prepaidPriority;
	}
	public void setPrepaidPriority(String prepaidPriority) {
		this.prepaidPriority = prepaidPriority;
	}
	
	
	
	public String getAdjCodLimit() {
		return adjCodLimit;
	}
	public void setAdjCodLimit(String adjCodLimit) {
		this.adjCodLimit = adjCodLimit;
	}
	public String getAdjPrepaidLimit() {
		return adjPrepaidLimit;
	}
	public void setAdjPrepaidLimit(String adjPrepaidLimit) {
		this.adjPrepaidLimit = adjPrepaidLimit;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "LogisticPartner [cod=" + cod + ", prepaidLimit=" + prepaidLimit
				+ ", codLimit=" + codLimit + ", carea=" + carea + ", cscrcd="
				+ cscrcd + ", cloctype=" + cloctype + ", newzone=" + newzone
				+ ", transitTatPrepaid=" + transitTatPrepaid
				+ ", transitTatCod=" + transitTatCod + ", formRequired="
				+ formRequired + ", name=" + name + ", isReturnPincode="
				+ isReturnPincode + ", pickUp=" + pickUp + ", codPriority="
				+ codPriority + ", prepaidPriority=" + prepaidPriority
				+ ", adjCodLimit=" + adjCodLimit + ", adjPrepaidLimit="
				+ adjPrepaidLimit + "]";
	}

	

	
}
