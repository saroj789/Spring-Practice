package com.newgen.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;

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
@WebServlet("/ViewDoc")
public class ViewDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewDoc() {
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
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId=RandomStringUtils.randomAlphanumeric(20);
		CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"servlet - "+request.getRequestURI()+" Started for KFV - "+request.getParameter("KFV"));
		try {
			Validations validations=new Validations();
			ViewDocHelper viewDocHelper=new ViewDocHelper();
			String userId=request.getParameter("lstr_param1");
			String leadId=request.getParameter("lstr_param3");
			String finoneDocId=request.getParameter("lstr_param4");
			String errorMessage="";
			String wiid="";
			String userDbId="";
			List<DocumentMetadata> docList=new ArrayList<>();
			
			StatusBean validationBean=validations.validateUploadData(userId,leadId,finoneDocId,sessionId);
			if(validationBean.getStatus().equals(Constants.FAILSTATUSSTRING)) {
				errorMessage=validationBean.getMessage();
			}else {
				wiid=new DBOperations().getWIID(leadId, sessionId);
				StatusBean sessionBean=viewDocHelper.getSession(userId, sessionId);
				if(sessionBean.getStatus().equals(Constants.FAILSTATUSSTRING)) {
					errorMessage=sessionBean.getMessage();
				}else {
					userDbId=sessionBean.getMessage();
					String receiptNo="";
					docList=viewDocHelper.getDocument(userDbId,leadId,finoneDocId,receiptNo,wiid,sessionId);
					
				}
			}
			
			request.setAttribute("ErrorMessage", errorMessage);
			request.setAttribute("WIID", wiid);
			request.setAttribute("USERDBID", userDbId);
			request.setAttribute("CabinetName", Cabinet.getCabinetName());
			request.setAttribute("DocList", docList);
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"No of document found - "+docList.size());
			RequestDispatcher view = request.getRequestDispatcher("/ViewDocument.jsp");
			view.forward(request, response);
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in "+request.getRequestURI()+" - "+Validations.stackTraceToString(e));
		}finally {
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"servlet - "+request.getRequestURI()+" Ended");
		}
	
	}

}
