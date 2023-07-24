package com.newgen.bean;

import java.io.ByteArrayOutputStream;

public class DownloadDocBean {
	
	private String docId;
	private String docname;
	private String status;
	private ByteArrayOutputStream outstream;
	private String errMsg;
	private String dateBetween;

	public String getDateBetween() {
		return dateBetween;
	}
	public void setDateBetween(String dateBetween) {
		this.dateBetween = dateBetween;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getDocname() {
		return docname;
	}
	public void setDocname(String docname) {
		this.docname = docname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ByteArrayOutputStream getOutstream() {
		return outstream;
	}
	public void setOutstream(ByteArrayOutputStream outstream) {
		this.outstream = outstream;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	

}
