package com.newgen.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
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
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
			ViewDocHelper viewDocHelper=new ViewDocHelper();

			String Field1=request.getParameter("username");
			String Field2=request.getParameter("userpassword");
			/*CustomLogger.printOut("username===>" + Field1);
			CustomLogger.printOut("userpassword===>" + Field2);*/
			String errorMessage="";
			String userDbId="";
			
			/*String[] chars = Field2.split(",");
			byte[] bytes = new byte[chars.length];
			for (int i = 0; i < chars.length; i++) {
			  bytes[i] = Byte.parseByte(chars[i]);
			}
			String newPass = new String(bytes);*/
			//CustomLogger.printOut("newPass===>" + newPass);
			Base64.Decoder dec = Base64.getDecoder();
			String newPass = new String(dec.decode(Field2));
				StatusBean sessionBean=viewDocHelper.getSession_New(Field1, sessionId, newPass);
				if("".equalsIgnoreCase(Field1)) {
	                response.setContentType("text/html");  
	                out.println("<script type=\"text/javascript\">");  
	                out.println("window.location= \"./login.jsp\"");
	                out.println("</script>");
				}
				else if("".equalsIgnoreCase(Field2)) {
	                response.setContentType("text/html");  
	                out.println("<script type=\"text/javascript\">");  
	                out.println("window.location= \"./login.jsp\"");
	                out.println("</script>");
				}
				else if(sessionBean.getStatus().equals(Constants.FAILSTATUSSTRING)) {
					errorMessage=sessionBean.getMessage();
					
					/*response.setCharacterEncoding("UTF-8"); 
					response.getWriter().print(errorMessage);*/
					CustomLogger.printErr("errorMessage==>"+errorMessage);
	                //response.setContentType("text/html");  
	                response.setContentType("text/html");  
	                out.println("<script type=\"text/javascript\">");  
	                //out.println("alert('Invalid Login!!');");
	                out.println("window.location= \"./login2.jsp\"");
	                out.println("</script>");
				}
				else {
					userDbId=sessionBean.getMessage();
					CustomLogger.printErr("userDbId==>"+userDbId);
					/*response.setCharacterEncoding("UTF-8"); 
					response.getWriter().print(userDbId);*/
					
					response.setContentType("text/html");  
	                response.setContentType("text/html");  
	                out.println("<script type=\"text/javascript\">");  
	                //out.println("alert('Successfully Logged In.');");
	                out.println("window.location= \"./VoucherArchival.jsp?userDbId="+userDbId+"\"");
	                out.println("</script>");
				}
							
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in "+request.getRequestURI()+" - "+Validations.stackTraceToString(e));
		}finally {
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"servlet - "+request.getRequestURI()+" Ended");
		}
	
	}

}
