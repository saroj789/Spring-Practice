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
import com.newgen.bean.BranchNameBean;
import com.newgen.bean.Cabinet;
import com.newgen.bean.Constants;
import com.newgen.bean.DocumentMetadata;
import com.newgen.bean.StatusBean;
import com.newgen.business.Validations;
import com.newgen.business.ViewDocHelper;
import com.newgen.dao.DBOperations;
import com.newgen.logger.CustomLogger;

@WebServlet("/GetPicklistData")
public class GetPicklistData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetPicklistData() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doPost(request,response);
    	}catch (Exception e) {
    		 CustomLogger.printErr("Error in ViewDoc Servlet - "+Validations.stackTraceToString(e));
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
			/*String username = "";
			String DataClassField1 = "";
			String DataClassField2 = "";
			String password = "";
			String DataClassIndex = "";
			String IndexId1 = "";
			String IndexId2 = "";
					
			String errorMessage="";
			String userDbId="";*/
			String OD_UID = request.getParameter("OD_UID"); 
			CustomLogger.printOut("OD_UID===>" + OD_UID);
			List<BranchNameBean> docList=new ArrayList<>();
			/*try {
				inputstream = new FileInputStream(String.valueOf(String.valueOf(System.getProperty("user.dir"))) + File.separator + "CustomConfig_New.ini");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
	      try {
	          ini.load(inputstream);
	      }
	      catch (IOException e) {
	          e.printStackTrace();
	      }
	      
	      	username = ini.getProperty("UserName");
	      	password = ini.getProperty("password");
	      	DataClassIndex = ini.getProperty("DataClassIndex");
	      	DataClassField1 = ini.getProperty("DataClassField1");
	      	DataClassField2 = ini.getProperty("DataClassField2");
	      	IndexId1 = ini.getProperty("IndexId1");
	      	IndexId2 = ini.getProperty("IndexId2");
	      	
				StatusBean sessionBean=viewDocHelper.getSession_New(username, sessionId, password);
				if(sessionBean.getStatus().equals(Constants.FAILSTATUSSTRING)) {
					errorMessage=sessionBean.getMessage();
					CustomLogger.printErr("errorMessage==>"+errorMessage);
				}else {
					userDbId=sessionBean.getMessage();
					CustomLogger.printErr("userDbId==>"+userDbId);
					String receiptNo="";*/
					docList=viewDocHelper.getPickListData(OD_UID);
					
				//}
			
				String gJson = new Gson().toJson(docList);
				CustomLogger.printErr("gJson===>" + gJson.toString());
			    response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(gJson);
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"No of Branch Codes found - "+docList.size());
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in "+request.getRequestURI()+" - "+Validations.stackTraceToString(e));
		}finally {
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"servlet - "+request.getRequestURI()+" Ended");
		}
	
	}

}
