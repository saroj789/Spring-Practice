package com.newgen.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONObject;

import com.newgen.bean.Constants;
import com.newgen.bean.DocumentMetadata;
import com.newgen.bean.StatusBean;
import com.newgen.business.Validations;
import com.newgen.business.ViewDocHelper;
import com.newgen.logger.CustomLogger;

/**
 * Servlet implementation class SearchDocument
 */
@WebServlet("/SearchDocument")
public class SearchDocument extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchDocument() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
    		response.getWriter().append("InValid Request. Please use Post request.");
    	}catch (Exception e) {
    		 CustomLogger.printErr("Error in SearchDocument Servlet - "+Validations.stackTraceToString(e));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = RandomStringUtils.randomAlphanumeric(20);
		CustomLogger.printOut(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "servlet - " + request.getRequestURI()
				+ " Started");
		List<DocumentMetadata> docList=new ArrayList<>();
		JSONObject jsonObject=new JSONObject();
		try {
			String userId=request.getParameter("UserName");
			String leadId=request.getParameter("LeadId");
			String finoneDocId=request.getParameter("FinoneDocId");
			String wiid=request.getParameter("WIId");
			String receiptNo=request.getParameter("Details - ReceiptNo");
			String userDbId=request.getParameter("userDBId");
			ViewDocHelper viewDocHelper=new ViewDocHelper();
			Validations validations=new Validations();
			StatusBean validationBean = validations.validateViewData(userId,leadId,finoneDocId,wiid,receiptNo,sessionId);
			
			if(validationBean.getStatus().equals(Constants.FAILSTATUSSTRING)) {
				jsonObject.append("Status", validationBean.getStatus());
				jsonObject.append("Message", validationBean.getMessage());
			}else {
				docList=viewDocHelper.getDocument(userDbId, leadId, finoneDocId, receiptNo, wiid, sessionId);
				jsonObject.append("Status", Constants.SUCCESSSTATUSSTRING);
				jsonObject.append("Message", "");
				jsonObject.append("DocList", docList);
			}		
			
			response.setContentType("text/plain");
	        response.setCharacterEncoding(Constants.UTF8ENCODING);
	        response.getWriter().print(jsonObject);
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in "
					+ request.getRequestURI() + " - " + Validations.stackTraceToString(e));
		} finally {
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "servlet - "
					+ request.getRequestURI() + " Ended");
		}
		
		
		
	}

}
