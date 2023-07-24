package com.newgen.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.newgen.bean.Cabinet;
import com.newgen.bean.Constants;
import com.newgen.bean.DocumentMetadata;
import com.newgen.bean.StatusBean;
import com.newgen.business.Validations;
import com.newgen.business.ViewDocHelper;
import com.newgen.dao.DBOperations;
import com.newgen.dmsapi.DMSCallBroker;
import com.newgen.logger.CustomLogger;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.xml.XMLGen;

/**
 * Servlet implementation class ViewDoc
 */
@WebServlet("/MailToBranch")
public class MailToBranch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailToBranch() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doPost(request,response);
    	}catch (Exception e) {
    		 CustomLogger.printErr("Error in ViewDoc Servlet - "+Validations.stackTraceToString(e));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId=RandomStringUtils.randomAlphanumeric(20);
		CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"servlet - "+request.getRequestURI()+" Started for KFV - "+request.getParameter("KFV"));
		try {
			PrintWriter out = response.getWriter();
			FileInputStream inputstream = null;
			Properties ini = new Properties();
			Validations validations=new Validations();
			ViewDocHelper viewDocHelper=new ViewDocHelper();
			String checkfldArray=request.getParameter("checkfldArray");
			String textfldArray=request.getParameter("textfldArray");
			String serialfldArray=request.getParameter("serialfldArray");
			String voucherfldArray=request.getParameter("voucherfldArray");
			String journalfldArray=request.getParameter("journalfldArray");
			String debitfldArray=request.getParameter("debitfldArray");
			String creditfldArray=request.getParameter("creditfldArray");
			String addtRemarksArray=request.getParameter("addtRemarksArray");
			String rectifiedArray=request.getParameter("rectifiedArray");	
			
			String branchCode = request.getParameter("branchCode"); 
			String voucherDate = request.getParameter("voucherDate"); 
			String validatedRecords = request.getParameter("validatedRecords"); 
			String unvalidatedRecords = request.getParameter("unvalidatedRecords"); 
			String VoucherCounthidden = request.getParameter("VoucherCounthidden");
			String ScannedImagesCounthidden = request.getParameter("ScannedImagesCounthidden");
			String NoOfRows = request.getParameter("NoOfRows");
			String OD_UID = request.getParameter("OD_UID");
			String csvDocName = request.getParameter("csvDocName");
			CustomLogger.printOut("OD_UID===>" + OD_UID);
			CustomLogger.printOut("checkfldArray===>" + checkfldArray);
			CustomLogger.printOut("textfldArray===>" + textfldArray);
			CustomLogger.printOut("serialfldArray===>" + serialfldArray);
			CustomLogger.printOut("voucherfldArray===>" + OD_UID);
			CustomLogger.printOut("journalfldArray===>" + checkfldArray);
			CustomLogger.printOut("debitfldArray===>" + textfldArray);
			CustomLogger.printOut("creditfldArray===>" + serialfldArray);
			int it = Integer.parseInt(NoOfRows);
			String checkfldArray1[] = checkfldArray.split(",");
			String textfldArray1[] = textfldArray.split(",");
			String serialfldArray1[] = serialfldArray.split(",");
			String voucherfldArray1[] = voucherfldArray.split(",");
			String journalfldArray1[] = journalfldArray.split(",");
			String debitfldArray1[] = debitfldArray.split(",");
			String creditfldArray1[] = creditfldArray.split(",");
			String addtRemarksArray1[] = addtRemarksArray.split(",");
			String rectifiedArray1[] = rectifiedArray.split(",");
			
			/*CustomLogger.printOut("checkfldArray1===>" + checkfldArray1.toString());
			CustomLogger.printOut("textfldArray1===>" + textfldArray1.toString());
			CustomLogger.printOut("serialfldArray1===>" + serialfldArray1.toString());
			CustomLogger.printOut("voucherfldArray1===>" + voucherfldArray1.toString());*/
			try {
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet("Excel Sheet");
				HSSFRow rowhead1 = sheet.createRow((short) 0);
				rowhead1.createCell((short) 0).setCellValue("Branch Code:");
				rowhead1.createCell((short) 1).setCellValue(branchCode);
				HSSFRow rowhead2 = sheet.createRow((short) 1);
				rowhead2.createCell((short) 0).setCellValue("Voucher Date:");
				rowhead2.createCell((short) 1).setCellValue(voucherDate);
				HSSFRow rowhead3 = sheet.createRow((short) 2);
				rowhead3.createCell((short) 0).setCellValue("No.of Vouchers in EVVR:");
				rowhead3.createCell((short) 1).setCellValue(VoucherCounthidden);
				HSSFRow rowhead4 = sheet.createRow((short) 3);
				rowhead4.createCell((short) 0).setCellValue("No.of Images Scanned:");
				rowhead4.createCell((short) 1).setCellValue(ScannedImagesCounthidden);
				HSSFRow rowhead5 = sheet.createRow((short) 4);
				rowhead5.createCell((short) 0).setCellValue("No.of Vouchers Records:");
				rowhead5.createCell((short) 1).setCellValue(NoOfRows);
				HSSFRow rowhead6 = sheet.createRow((short) 5);
				rowhead6.createCell((short) 0).setCellValue("No.of Vouchers Records verified:");
				rowhead6.createCell((short) 1).setCellValue(validatedRecords);
				
				HSSFRow rowhead7 = sheet.createRow((short) 6);
				rowhead7.createCell((short) 0).setCellValue("No.of Vouchers Records not verified:");
				rowhead7.createCell((short) 1).setCellValue(unvalidatedRecords);
				
				HSSFRow rowhead = sheet.createRow((short) 7);
	
				rowhead.createCell((short) 0).setCellValue("SR NO");
				rowhead.createCell((short) 1).setCellValue("VERIFIED");
				rowhead.createCell((short) 2).setCellValue("REMARKS");
				rowhead.createCell((short) 3).setCellValue("VOUCHER NO");
				rowhead.createCell((short) 4).setCellValue("JOURNAL NO");
				rowhead.createCell((short) 5).setCellValue("DEBIT AMOUNT");
				rowhead.createCell((short) 6).setCellValue("CREDIT AMOUNT");
				rowhead.createCell((short) 7).setCellValue("ADDT REMARKS");
				rowhead.createCell((short) 8).setCellValue("RECTIFIED");			  
				
				int counter = 8;
				for (int j=0; j < it; j++) {
					HSSFRow row = sheet.createRow((short) counter);
						row.createCell((short) 0).setCellValue(serialfldArray1[j]);
						row.createCell((short) 1).setCellValue(checkfldArray1[j]);
						row.createCell((short) 2).setCellValue(textfldArray1[j]);
						
						row.createCell((short) 3).setCellValue(voucherfldArray1[j]);
						row.createCell((short) 4).setCellValue(journalfldArray1[j]);
						row.createCell((short) 5).setCellValue(debitfldArray1[j]);
						row.createCell((short) 6).setCellValue(creditfldArray1[j]);
						row.createCell((short) 7).setCellValue(addtRemarksArray1[j]);
						row.createCell((short) 8).setCellValue(rectifiedArray1[j]);		 
						counter = counter+1;
				}
				String AttachmentPath = "";
				AttachmentPath = Cabinet.getExcelDownloadPath()+csvDocName+"_Audit_Report.xls";
				FileOutputStream fileOut = new FileOutputStream(Cabinet.getExcelDownloadPath()+csvDocName+"_Audit_Report.xls");
				wb.write(fileOut);
				fileOut.close();
				CustomLogger.printOut("Data is saved in excel file===>"+AttachmentPath);
				String UserIndex = "";
				String inXML = "";
				String validateInXml = "<?xml version=\"1.0\"?><NGOValidateUserForSession_Input><Option>NGOValidateUserForSession</Option>" 
						+ "<CabinetName>"+Cabinet.getCabinetName()+"</CabinetName>"
						+ "<UserDBId>"+OD_UID+"</UserDBId>"
						+ "<Password></Password>" 
						+ "</NGOValidateUserForSession_Input>";
				
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+validateInXml);
				String validateOutXml=DMSCallBroker.execute(validateInXml, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+validateOutXml);
				XMLParser parserNew=new XMLParser();
				parserNew.setInputXML(validateOutXml);
				
				if("0".equalsIgnoreCase(parserNew.getValueOf(Constants.STATUSSTRING))) { 
					UserIndex = parserNew.getValueOf("UserIndex");
				}	
				String ccEmail="";
				inXML = XMLGen.NGOGetUserProperty(Cabinet.getCabinetName(),OD_UID,UserIndex);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+inXML);
				String outXml=DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+outXml);
				XMLParser parser=new XMLParser();
				parser.setInputXML(outXml);
				if("0".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))) {
					ccEmail = parser.getValueOf("MailId");
				}
				CustomLogger.printOut("ccEmail===>"+ccEmail);
				String ToEmail = viewDocHelper.getEmailIdForBranch(branchCode);
				String roCCEmail = viewDocHelper.getEmailIdForRo(branchCode);
				CustomLogger.printOut("ToEmail getEmailIdForBranch===>"+ToEmail);
				CustomLogger.printOut("roCCEmail getEmailIdForRo===>"+roCCEmail);
				SendEmail abc = new SendEmail();
				if(ToEmail.equalsIgnoreCase("Error") || roCCEmail.equalsIgnoreCase("Error")){
					abc.sendMailwithAttachment(Cabinet.getToMail(), AttachmentPath, Cabinet.getFromMail(), Cabinet.getMailserverHost(), Cabinet.getMailserverPort(), Cabinet.getMailSubject(), Cabinet.getMailContent(),ccEmail);
					CustomLogger.printOut("Sending email blank Email Id!!!");
				}
				else{
				abc.sendMailwithAttachmentNew(ToEmail, AttachmentPath, Cabinet.getFromMail(), Cabinet.getMailserverHost(), Cabinet.getMailserverPort(), Cabinet.getMailSubject(), Cabinet.getMailContent(),ccEmail,roCCEmail);
				CustomLogger.printOut("Sending email Valid Email ID!!!");
				}
			} catch (Exception ex) {
				System.out.println("Exception in MailToBranch.java===>"+ex.toString());
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in MailToBranch.java"+request.getRequestURI()+" - "+Validations.stackTraceToString(ex));
				CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in MailToBranch.java"+request.getRequestURI()+" - "+Validations.stackTraceToString(ex));
			}
					
			
			
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in "+request.getRequestURI()+" - "+Validations.stackTraceToString(e));
		}finally {
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"servlet - "+request.getRequestURI()+" Ended");
		}
	
	}

}
