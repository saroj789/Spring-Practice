package com.newgen.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
@WebServlet("/SearchandViewDoc")
public class SearchandViewDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchandViewDoc() {
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
			String Field1=request.getParameter("Field1");
			String Field2=request.getParameter("Field2");
			String OD_UID = request.getParameter("OD_UID"); 
			CustomLogger.printOut("OD_UID===>" + OD_UID);
			List<DocumentMetadata> docList=new ArrayList<>();
			
					docList=viewDocHelper.getDocument_New(OD_UID,Field1,Field2,sessionId);
					
				String gJson = new Gson().toJson(docList);
				CustomLogger.printErr("gJson===>" + gJson.toString());
			    response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(gJson);
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"No of document found - "+docList.size());
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in "+request.getRequestURI()+" - "+Validations.stackTraceToString(e));
		}finally {
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"servlet - "+request.getRequestURI()+" Ended");
		}
	
	}

}
