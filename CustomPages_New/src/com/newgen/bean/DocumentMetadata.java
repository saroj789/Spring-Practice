package com.newgen.bean;

import java.util.List;

public class DocumentMetadata {
	
	private String documentIndex;
	private String documentName;
	private String createdByAppName;
	private String createdDatetime;
	private String tag;
	private String ReceiptNo;
	private String cabinetName;
	private String pageCount;
	private String voucherCount;
	private String emailId;
	private String rowCount;
	private String String;
	
	private List<List<String>> values;
	
	
	
	public List<List<String>> getValues() {
		return values;
	}
	public void setValues(List<List<String>> values) {
		this.values = values;
	}
	public String getCabinetName() {
		return cabinetName;
	}
	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}
	public String getReceiptNo() {
		return ReceiptNo;
	}
	public String getDocumentIndex() {
		return documentIndex;
	}
	public void setDocumentIndex(String documentIndex) {
		this.documentIndex = documentIndex;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getCreatedByAppName() {
		return createdByAppName;
	}
	public void setCreatedByAppName(String createdByAppName) {
		this.createdByAppName = createdByAppName;
	}
	public String getCreatedDatetime() {
		return createdDatetime;
	}
	public void setCreatedDatetime(String createdDatetime) {
		this.createdDatetime = createdDatetime;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public void setReceiptNo(String ReceiptNo) {
		this.ReceiptNo = ReceiptNo;
	}
	public String getPageCount() {
		return pageCount;
	}
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getVoucherCount() {
		return voucherCount;
	}
	public void setVoucherCount(String voucherCount) {
		this.voucherCount = voucherCount;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getRowCount() {
		return rowCount;
	}
	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}
	public String getString() {
		return String;
	}
	public void setString(String string) {
		String = string;
	}	

}
