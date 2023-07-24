package com.newgen.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import com.newgen.logger.CustomLogger;

/**
 * Servlet implementation class ViewDoc
 */
@WebServlet("/SaveAsDraft")
public class SaveAsDraft extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveAsDraft() {
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
			String accountfldArray=request.getParameter("accountfldArray");
			String chequefldArray=request.getParameter("chequefldArray");
			String custnamefldArray=request.getParameter("custnamefldArray");
			String productfldArray=request.getParameter("productfldArray");
			String txndescFldArray=request.getParameter("txndescFldArray");
			String hobranchfldArray=request.getParameter("hobranchfldArray");
			String useridfldArray=request.getParameter("useridfldArray");
			String checkerIdfldArray=request.getParameter("checkerIdfldArray");
			String checkerId1fldArray=request.getParameter("checkerId1fldArray");
			String checkerId2fldArray=request.getParameter("checkerId2fldArray");
			String supidfldArray=request.getParameter("supidfldArray");
			String timestampfldArray=request.getParameter("timestampfldArray");
			String addtRemarksArray=request.getParameter("addtRemarksArray");
			String rectifiedArray=request.getParameter("rectifiedArray");
			String remarksIfAny=request.getParameter("remarksIfAny");
			System.out.println("rectifiedArray===>"+rectifiedArray);
			String branchCode = request.getParameter("branchCode"); 
			String voucherDate = request.getParameter("voucherDate"); 
			String validatedRecords = request.getParameter("validatedRecords"); 
			String unvalidatedRecords = request.getParameter("unvalidatedRecords"); 
			String VoucherCounthidden = request.getParameter("VoucherCounthidden");
			String ScannedImagesCounthidden = request.getParameter("ScannedImagesCounthidden");
			String NoOfRows = request.getParameter("NoOfRows");
			String OD_UID = request.getParameter("OD_UID");
			String csvDocName = request.getParameter("csvDocName");
			String isSubmitted = request.getParameter("isSubmitted");
			String isMailedtoBranch = request.getParameter("isMailedtoBranch");
			String csvdocumentIndex = request.getParameter("csvdocumentIndex");
			String overallRemarks = request.getParameter("overallRemarks");
			String Field1=request.getParameter("Field1");
			String Field2=request.getParameter("Field2");
			if("".equalsIgnoreCase(isMailedtoBranch) || "".equals(isMailedtoBranch)) {
				isMailedtoBranch = "-";
			}
			if("".equalsIgnoreCase(isSubmitted) || "".equals(isSubmitted)) {
				isSubmitted = "-";
			}
			if("".equalsIgnoreCase(overallRemarks) || "".equals(overallRemarks)) {
				overallRemarks = "-";
			}
			viewDocHelper.changeDocProperty(OD_UID, Field1, Field2, sessionId, csvdocumentIndex, isSubmitted, isMailedtoBranch,overallRemarks);
			CustomLogger.printOut("OD_UID===>" + OD_UID);
			CustomLogger.printOut("checkfldArray===>" + checkfldArray);
			CustomLogger.printOut("textfldArray===>" + textfldArray);
			CustomLogger.printOut("serialfldArray===>" + serialfldArray);
			int it = Integer.parseInt(NoOfRows);
			String checkfldArray1[] = checkfldArray.split(",");
			String textfldArray1[] = textfldArray.split(",");
			String serialfldArray1[] = serialfldArray.split(",");
			String voucherfldArray1[] = voucherfldArray.split(",");
			String journalfldArray1[] = journalfldArray.split(",");
			String debitfldArray1[] = debitfldArray.split(",");
			String creditfldArray1[] = creditfldArray.split(",");
			String accountfldArray1[] = accountfldArray.split(",");
			String chequefldArray1[] = chequefldArray.split(",");
			String custnamefldArray1[] = custnamefldArray.split(",");
			String productfldArray1[] = productfldArray.split(",");
			String txndescFldArray1[] = txndescFldArray.split(",");
			String hobranchfldArray1[] = hobranchfldArray.split(",");
			String useridfldArray1[] = useridfldArray.split(",");
			String checkerIdfldArray1[] = checkerIdfldArray.split(",");
			String checkerId1fldArray1[] = checkerId1fldArray.split(",");
			String checkerId2fldArray1[] = checkerId2fldArray.split(",");
			String supidfldArray1[] = supidfldArray.split(",");
			String timestampfldArray1[] = timestampfldArray.split(",");
			String addtRemarksArray1[] = addtRemarksArray.split(",");
			String rectifiedArray1[] = rectifiedArray.split(",");
			System.out.println("rectifiedArray1===>"+rectifiedArray1);
			/*CustomLogger.printOut("checkfldArray1===>" + checkfldArray1.toString());
			CustomLogger.printOut("textfldArray1===>" + textfldArray1.toString());
			CustomLogger.printOut("serialfldArray1===>" + serialfldArray1.toString());
			CustomLogger.printOut("voucherfldArray1===>" + voucherfldArray1.toString());*/
			//String custnamefldArray1[] = custnamefldArray.split(",");
			try {
				//csvDocName=csvDocName.replaceAll("EVVR_", "");
				File csvFile = new File(Cabinet.getCsvDownloadPath()+csvDocName+"_"+isMailedtoBranch+"_"+isSubmitted+"_"+overallRemarks+".csv");
				FileWriter fileWriter = new FileWriter(csvFile);
				    String line = "";
				    //line += "VERIFIED,REMARKS,SR NO,VCH NO,JOURNAL NO,DEBIT AMT,CREDIT AMT,ACCOUNT NO,CHEQUE NO,CUSTOMER NAME,PRODUCT TYPE,TXN DESC,HOME BRANCH,USER ID,CHECKER ID,CHECKER ID1,CHECKER ID2,SUP ID,TIME STAMP";
				    line += "VCH OK,SR NO,VCH NO,DR,CR,ACCOUNT NO,CUSTOMER NAME,REMARKS,ADDT REMARKS,RECTIFIED,JOURNAL NO,CHEQUE NO,PRODUCT TYPE,TXN DESC,HOME BRANCH,USER ID,CHECKER ID,CHECKER ID1,CHECKER ID2,SUP ID,TIME STAMP";
				    line += "\n";
				    for (int i = 0; i < it ; i++) {
				    	line += checkfldArray1[i]+","+serialfldArray1[i]+","+voucherfldArray1[i]+","+debitfldArray1[i]+","+creditfldArray1[i]+","+accountfldArray1[i]+","+custnamefldArray1[i]+","+textfldArray1[i]+","+addtRemarksArray1[i]+","+rectifiedArray1[i]+","+journalfldArray1[i]+","+chequefldArray1[i]+","+productfldArray1[i]+","+txndescFldArray1[i]+","+hobranchfldArray1[i]+","+useridfldArray1[i]+","+checkerIdfldArray1[i]+","+checkerId1fldArray1[i]+","+checkerId2fldArray1[i]+","+supidfldArray1[i]+","+timestampfldArray1[i];
				    	line += "\n"; 
				    }
				    //line += "Remarks(if any):,"+remarksIfAny+", , , , , , , , , , , , , , , , , , ,"; 
				    //line.append("\n");
				    fileWriter.write(line.toString());
				fileWriter.close();
				
			} catch (Exception ex) {
			}
					
			
			
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in "+request.getRequestURI()+" - "+Validations.stackTraceToString(e));
		}finally {
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"servlet - "+request.getRequestURI()+" Ended");
		}
	
	}

}
