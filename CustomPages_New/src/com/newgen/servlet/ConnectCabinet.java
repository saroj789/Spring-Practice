package com.newgen.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;

import com.newgen.bean.Cabinet;
import com.newgen.bean.Constants;
import com.newgen.bean.StatusBean;
import com.newgen.business.Validations;
import com.newgen.business.ViewDocHelper;
import com.newgen.logger.CustomLogger;

/**
 * Servlet implementation class DisconnectCabinet
 */
@WebServlet("/ConnectCabinet")
public class ConnectCabinet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectCabinet() {
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
    		 CustomLogger.printErr("Error in DisconnectCabinet Servlet - "+Validations.stackTraceToString(e));
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
		System.out.println("Inside ConnectCabinet.java");
		try {
			String errorMessage="";
			String userDbId="";
			ViewDocHelper viewDocHelper=new ViewDocHelper();
			String UserId=request.getParameter("UserId");
			String inputPassword=request.getParameter("inputPassword");
			//Cabinet cabinet=new Cabinet();
			//cabinet.disconnectCabinet(UserId);
			StatusBean sessionBean=viewDocHelper.getSession_New(UserId, sessionId, inputPassword);
			if(sessionBean.getStatus().equals(Constants.FAILSTATUSSTRING)) {
				errorMessage=sessionBean.getMessage();
			}else {
				userDbId=sessionBean.getMessage();
				//docList=viewDocHelper.getDocument_New(userDbId,Field1,Field2,sessionId);
				response.getWriter().print(Constants.SUCCESSSTATUSSTRING);
				
			}
			request.setAttribute("ErrorMessage", errorMessage);
			request.setAttribute("USERDBID", userDbId);
			RequestDispatcher view = request.getRequestDispatcher("/ArchivalSearch.jsp");
			view.forward(request, response);
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in "
					+ request.getRequestURI() + " - " + Validations.stackTraceToString(e));
		} finally {
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "servlet - "
					+ request.getRequestURI() + " Ended");
		}
		
	}

}
