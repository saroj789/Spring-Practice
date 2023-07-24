package com.newgen.bean;

import java.util.List;

public class ReportDataBean {
	
	private String BranchCode;
	private String VerificationDate;
	private String VerifiedBy;
	private String totalEVVRRecords;
	private String voucherOkRecords;
	private String rectifiedRecords;
	private String auditStatus;
	private String BranchName;
	private String VoucherDate;
	
	public String getBranchName() {
		return BranchName;
	}
	public void setBranchName(String branchName) {
		BranchName = branchName;
	}
	private List<List<String>> values;
	
	public List<List<String>> getValues() {
		return values;
	}
	public void setValues(List<List<String>> values) {
		this.values = values;
	}
	public String getBranchCode() {
		return BranchCode;
	}
	public void setBranchCode(String branchCode) {
		BranchCode = branchCode;
	}
	public String getVerificationDate() {
		return VerificationDate;
	}
	public void setVerificationDate(String verificationDate) {
		VerificationDate = verificationDate;
	}
	public String getVerifiedBy() {
		return VerifiedBy;
	}
	public void setVerifiedBy(String verifiedBy) {
		VerifiedBy = verifiedBy;
	}
	public String getTotalEVVRRecords() {
		return totalEVVRRecords;
	}
	public void setTotalEVVRRecords(String totalEVVRRecords) {
		this.totalEVVRRecords = totalEVVRRecords;
	}
	public String getVoucherOkRecords() {
		return voucherOkRecords;
	}
	public void setVoucherOkRecords(String voucherOkRecords) {
		this.voucherOkRecords = voucherOkRecords;
	}
	public String getRectifiedRecords() {
		return rectifiedRecords;
	}
	public void setRectifiedRecords(String rectifiedRecords) {
		this.rectifiedRecords = rectifiedRecords;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getVoucherDate() {
		return VoucherDate;
	}
	public void setVoucherDate(String voucherDate) {
		VoucherDate = voucherDate;
	}
	
	

}
