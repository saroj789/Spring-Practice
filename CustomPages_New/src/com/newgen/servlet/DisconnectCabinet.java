package com.newgen.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;

import com.newgen.bean.Cabinet;
import com.newgen.bean.Constants;
import com.newgen.business.Validations;
import com.newgen.logger.CustomLogger;

/**
 * Servlet implementation class DisconnectCabinet
 */
@WebServlet("/DisconnectCabinet")
public class DisconnectCabinet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisconnectCabinet() {
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
		try {
			PrintWriter out = response.getWriter();
			String userDbid=request.getParameter("userDbId");
			CustomLogger.printOut("userDbid====>"+userDbid);
			Cabinet cabinet=new Cabinet();
			cabinet.disconnectCabinet(userDbid);
			/*String output = cabinet.disconnectCabinet(userDbid);
			CustomLogger.printOut("output====>"+output);
			if("0".equalsIgnoreCase(output)) {
				response.setContentType("text/html");  
                out.println("<script type=\"text/javascript\">");  
                out.println("window.location= \"./login.jsp\"");
                out.println("</script>");
			}
			else {
				response.setContentType("text/html");  
                out.println("<script type=\"text/javascript\">");  
                out.println("window.location= \"./login.jsp\"");
                out.println("</script>");
			} */
			response.getWriter().print(Constants.SUCCESSSTATUSSTRING);
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in "
					+ request.getRequestURI() + " - " + Validations.stackTraceToString(e));
		} finally {
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "servlet - "
					+ request.getRequestURI() + " Ended");
		}
		
	}

}
