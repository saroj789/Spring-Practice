package com.newgen.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;

import com.newgen.bean.Cabinet;
import com.newgen.bean.Constants;
import com.newgen.business.Validations;
import com.newgen.business.ViewDocHelper;
import com.newgen.logger.CustomLogger;

@WebServlet("/SaveReportData")
public class SaveReportData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SaveReportData() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doPost(request,response);
    	}catch (Exception e) {
    		 CustomLogger.printErr("Error in SaveReportData Servlet - "+Validations.stackTraceToString(e));
		}
	}

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
			String remarksIfAny=request.getParameter("remarksIfAny");
			String branchCode = request.getParameter("branchCode"); 
			String voucherDate = request.getParameter("voucherDate"); 
			String verifiedBy = request.getParameter("UserNameHiddenFld");
			String rectifiedCount = request.getParameter("rectifiedCount"); 
			String voucherokCount = request.getParameter("voucherokCount"); 
			String totalVoucherDistinct = request.getParameter("VoucherCounthidden");
			String totalImages = request.getParameter("ScannedImagesCounthidden");
			String totalVoucher = request.getParameter("NoOfRows");
			String OD_UID = request.getParameter("OD_UID");
			String csvDocName = request.getParameter("csvDocName");
			String isSubmitted = request.getParameter("isSubmitted");
			String isMailedtoBranch = request.getParameter("isMailedtoBranch");
			String csvdocumentIndex = request.getParameter("csvdocumentIndex");
			String overallRemarks = request.getParameter("overallRemarks");
			String Field1=request.getParameter("Field1");
			String Field2=request.getParameter("Field2");
			CustomLogger.printOut("branchCode in SaveReport.java===>"+branchCode + "---" +voucherDate + "---" +verifiedBy + "---" +rectifiedCount);
			CustomLogger.printOut("voucherokCount in SaveReport.java===>"+voucherokCount + "---" +totalVoucherDistinct + "---" +totalImages + "---" +totalVoucher);
			try {
				viewDocHelper.saveReportData(branchCode, voucherDate, verifiedBy, rectifiedCount, voucherokCount, totalVoucher, totalImages,totalVoucherDistinct);
			} catch (Exception ex) {
				CustomLogger.printOut("Exception in saveReportData() inside SaveReport.java===>"+ex.toString());
			}
					
			
			
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in "+request.getRequestURI()+" - "+Validations.stackTraceToString(e));
		}finally {
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"servlet - "+request.getRequestURI()+" Ended");
		}
	
	}

}
