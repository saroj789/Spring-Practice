package com.newgen.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;

import com.newgen.bean.Constants;
import com.newgen.bean.DownloadDocBean;
import com.newgen.business.DownloadDocHelper;
import com.newgen.business.Validations;
import com.newgen.logger.CustomLogger;

/**
 * Servlet implementation class DownloadDocument
 */
@WebServlet("/DownloadDocument")
public class DownloadDocument extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadDocument() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
    		response.getWriter().append("InValid Request. Please use Post request");
    	}catch (Exception e) {
    		 CustomLogger.printErr("Error in DownloadDocument Servlet - "+Validations.stackTraceToString(e));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sessionId=RandomStringUtils.randomAlphanumeric(20);
		CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"servlet - "+request.getRequestURI()+" Started");
		try {
			String docId=request.getParameter("docIdToDownload");
			String userDBId=request.getParameter("userDBId");
			String userName=request.getParameter("UserName");
			CustomLogger.printOut("Doc ID is : "+docId);
			String invldDocId=docId.substring(0, 1);
			if(invldDocId.equals("1") || invldDocId=="1") {
				CustomLogger.printOut("Given doc ID is not valid");
				response.getWriter().print("<script>alert('Doc ID is not valid');window.close();</script>");
			}
			


			DownloadDocHelper downloadDocHelper=new DownloadDocHelper();
			DownloadDocBean downdoc=downloadDocHelper.downloadDoc(docId,userName,userDBId,sessionId);
			String dateBetween=downdoc.getDateBetween();
             if("Fail".equalsIgnoreCase(downdoc.getStatus())) {
				
 				response.getWriter().print("<script>alert('Image documents of only Current month & Previous month can be downloaded.');window.close();</script>");
				
			}
			if("Fail".equalsIgnoreCase(downdoc.getStatus())) {
				
				response.getWriter().print("<script>alert('Error in downloading document.');window.close();</script>");
				
				
			}else {
				
				response.setHeader("Content-Disposition", "attachment; filename=\"" +downdoc.getDocname() + "\"");	    
				response.setContentType("application/octet-stream");
				response.setContentLength(downdoc.getOutstream().size());
				
				ServletOutputStream os2 = response.getOutputStream();
				downdoc.getOutstream().writeTo(os2);
				os2.flush();
			}
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in DownloadDocs Servlet - "+Validations.stackTraceToString(e));
		}finally {
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"servlet - "+request.getRequestURI()+" Ended");
		}
	}

}
