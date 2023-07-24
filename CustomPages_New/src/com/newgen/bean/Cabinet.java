package com.newgen.bean;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.newgen.business.Validations;
import com.newgen.dmsapi.DMSCallBroker;
import com.newgen.logger.CustomLogger;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.xml.XMLGen;

public class Cabinet {

	private static String cabinetName;
	private static String appserverIp;
	private static String jtsIP;
	private static String jtsPort;
	private static String cabUserName;
	private static String cabPassword;
	private String cabUserDbId;
	private static String volumeId;
	private static String siteId;
	private static String dataDefId;
	private static String dataDefName;
	private static String dataClassFields;
	private static String downloadDir;
	private static String UserBranchMappingTable;
	private static String DepartmentSectionMasterTable;
	private static String UserAndDepartmentSectionMappingTable;
	private static String OfficeMasterTable;
	private static String jdbcUrl;
	private static String DBUsername;
	private static String DBPassword;
	private static String driverName;
	private static String csvDownloadPath;
	private static String excelDownloadPath;
	private static String mailserverHost;
	private static String mailserverPort;
	private static String mailSubject;
	private static String mailContent;
	private static String mailuser;
	private static String mailpassword;
	private static String fromMail;
	private static String toMail;
	private static String AllDataClassFields;
	
	private static Properties extensions = new Properties();
	private static Properties eworkstyleProps=new Properties();
	private static Set<String> allowedmimeType=new HashSet<>();
	
	static{
		readIniFunction();
	}
	
	public static String getDataDefId() {
		return dataDefId;
	}

	public static String getDataClassFields() {
		return dataClassFields;
	}

	public static String getVolumeId() {
		return volumeId;
	}

	public static String getSiteId() {
		return siteId;
	}


	public static String getCabinetName() {
		return cabinetName;
	}


	public static String getAppserverIp() {
		return appserverIp;
	}

	
	public static String getJtsIp() {
		return jtsIP;
	}

	

	public static String getJtsPort() {
		return jtsPort;
	}


	public static String getCabUserName() {
		return cabUserName;
	}

	public static String getCabPassword() {
		return cabPassword;
	}


	public String getCabUserDbId() {
		return cabUserDbId;
	}

	public void setCabUserDbId(String cabUserDbId) {
		this.cabUserDbId = cabUserDbId;
	}
	
	

	public Properties getExtensions() {
		return extensions;
	}
	
	public static Properties getEworkstyleProps() {
		return eworkstyleProps;
	}
	
	public static Set<String> getAllowedmimeType() {
		return allowedmimeType;
	}

	
	public static String getDownloadDir() {
		return downloadDir;
	}

	public static void setDownloadDir(String downloadDir) {
		Cabinet.downloadDir = downloadDir;
	}
	
	public static String getUserBranchMappingTable() {
		return UserBranchMappingTable;
	}

	public static void setUserBranchMappingTable(String userBranchMappingTable) {
		UserBranchMappingTable = userBranchMappingTable;
	}

	public static String getDepartmentSectionMasterTable() {
		return DepartmentSectionMasterTable;
	}

	public static void setDepartmentSectionMasterTable(String departmentSectionMasterTable) {
		DepartmentSectionMasterTable = departmentSectionMasterTable;
	}

	public static String getUserAndDepartmentSectionMappingTable() {
		return UserAndDepartmentSectionMappingTable;
	}

	public static void setUserAndDepartmentSectionMappingTable(String userAndDepartmentSectionMappingTable) {
		UserAndDepartmentSectionMappingTable = userAndDepartmentSectionMappingTable;
	}
	
	public static String getJdbcUrl() {
		return jdbcUrl;
	}

	public static void setJdbcUrl(String jdbcUrl) {
		Cabinet.jdbcUrl = jdbcUrl;
	}

	public static String getDBUsername() {
		return DBUsername;
	}

	public static void setDBUsername(String dBUsername) {
		DBUsername = dBUsername;
	}

	public static String getDBPassword() {
		return DBPassword;
	}

	public static void setDBPassword(String dBPassword) {
		DBPassword = dBPassword;
	}
	public static String getDriverName() {
		return driverName;
	}

	public static void setDriverName(String driverName) {
		Cabinet.driverName = driverName;
	}


	public static String getCsvDownloadPath() {
		return csvDownloadPath;
	}

	public static void setCsvDownloadPath(String csvDownloadPath) {
		Cabinet.csvDownloadPath = csvDownloadPath;
	}

	public static String getExcelDownloadPath() {
		return excelDownloadPath;
	}

	public static void setExcelDownloadPath(String excelDownloadPath) {
		Cabinet.excelDownloadPath = excelDownloadPath;
	}

	public static String getMailserverHost() {
		return mailserverHost;
	}

	public static void setMailserverHost(String mailserverHost) {
		Cabinet.mailserverHost = mailserverHost;
	}

	public static String getMailserverPort() {
		return mailserverPort;
	}

	public static void setMailserverPort(String mailserverPort) {
		Cabinet.mailserverPort = mailserverPort;
	}

	public static String getMailSubject() {
		return mailSubject;
	}

	public static void setMailSubject(String mailSubject) {
		Cabinet.mailSubject = mailSubject;
	}

	public static String getMailContent() {
		return mailContent;
	}

	public static void setMailContent(String mailContent) {
		Cabinet.mailContent = mailContent;
	}
	

	public static String getMailuser() {
		return mailuser;
	}

	public static void setMailuser(String mailuser) {
		Cabinet.mailuser = mailuser;
	}

	public static String getMailpassword() {
		return mailpassword;
	}

	public static void setMailpassword(String mailpassword) {
		Cabinet.mailpassword = mailpassword;
	}

	public static String getFromMail() {
		return fromMail;
	}

	public static void setFromMail(String fromMail) {
		Cabinet.fromMail = fromMail;
	}
	

	public static String getToMail() {
		return toMail;
	}

	public static void setToMail(String toMail) {
		Cabinet.toMail = toMail;
	}
	

	public static String getOfficeMasterTable() {
		return OfficeMasterTable;
	}

	public static void setOfficeMasterTable(String officeMasterTable) {
		OfficeMasterTable = officeMasterTable;
	}

	public static String getDataDefName() {
		return dataDefName;
	}

	public static void setDataDefName(String dataDefName) {
		Cabinet.dataDefName = dataDefName;
	}

	public static String getAllDataClassFields() {
		return AllDataClassFields;
	}

	public static void setAllDataClassFields(String allDataClassFields) {
		AllDataClassFields = allDataClassFields;
	}

	private static void readIniFunction()
	{
		FileInputStream inputstream=null;
	
		Properties ini=new Properties();
		try 
		{
		
			inputstream = new FileInputStream(System.getProperty(Constants.USERDIR)+File.separator+"CustomPages"+File.separator+"CustomPageConfig.ini");
			System.out.println("ini path..."+System.getProperty(Constants.USERDIR)+File.separator+"CustomPages"+File.separator+"CustomPageConfig.ini");
			ini.load(inputstream);
	
			cabinetName=ini.getProperty("CabinetName");
			appserverIp=ini.getProperty("AppServerIP");
			jtsIP=ini.getProperty("JTSIP");
			jtsPort=ini.getProperty("JTSPORT");
			volumeId=ini.getProperty("VolumeId");
			siteId=ini.getProperty("SiteId");
			cabUserName=ini.getProperty("UserName");
			cabPassword=ini.getProperty("UserPassword");
			dataDefId=ini.getProperty("DataDefIndex");
			dataClassFields=ini.getProperty("DataClassFields");
			UserBranchMappingTable=ini.getProperty("UserBranchMappingTable");
			DepartmentSectionMasterTable=ini.getProperty("DepartmentSectionMasterTable");
			UserAndDepartmentSectionMappingTable=ini.getProperty("UserAndDepartmentSectionMappingTable");
			OfficeMasterTable=ini.getProperty("OfficeMasterTable");
			downloadDir= System.getProperty(Constants.USERDIR)+File.separator+"CustomPages"+File.separator+ "Temp";//ini.getProperty("downloadDir");
			jdbcUrl = ini.getProperty("JDBCURL");
			DBUsername = ini.getProperty("DBUsername");
			DBPassword = ini.getProperty("DBPassword");
			driverName = ini.getProperty("driverName");
			csvDownloadPath = ini.getProperty("csvDownloadPath");
			excelDownloadPath = ini.getProperty("excelDownloadPath");
			mailserverHost = ini.getProperty("mailserverHost");
			mailserverPort = ini.getProperty("mailserverPort");
			mailSubject = ini.getProperty("mailSubject");
			mailContent = ini.getProperty("mailContent");
			mailuser = ini.getProperty("mailuser");
			mailpassword = ini.getProperty("mailpassword");
			fromMail = ini.getProperty("fromMail");
			toMail = ini.getProperty("toMail");
			dataDefName = ini.getProperty("dataDefName");
			AllDataClassFields = ini.getProperty("AllDataClassFields");
			CustomLogger.printOut("downloadDir====>"+downloadDir + " jdbcUrl -----DBUsername---DBPassword---driverName"+jdbcUrl+DBUsername+DBPassword+driverName);
			CustomLogger.printOut("Cabinet object load with values : cabinetName="+cabinetName+",appServerIp="+jtsIP+",appServerPort="+jtsPort+",volumeId="+volumeId+",siteId="+siteId+",cabUserName="+cabUserName+",dataDefId="+dataDefId+",dataClassFields="+dataClassFields);
		    
			inputstream=new FileInputStream(System.getProperty(Constants.USERDIR)+File.separator+"ngdbini"+File.separator+"odwebini"+File.separator+"uploadmime.conf");
			extensions.load(inputstream);
			
			inputstream=new FileInputStream(System.getProperty(Constants.USERDIR)+File.separator+"ngdbini"+File.separator+"Custom"+File.separator+cabinetName+File.separator+"eworkstyle.ini");
			eworkstyleProps.load(inputstream);
			
			String str = readUploadConfig();
			XMLParser xmlParser=new XMLParser(str);
			int len=xmlParser.getNoOfFields("MimeType");
			allowedmimeType.add(xmlParser.getFirstValueOf("MimeType").toUpperCase());
			for(int i=1;i<len;i++) {
				allowedmimeType.add(xmlParser.getNextValueOf("MimeType").toUpperCase());
			}
		} 
		catch (IOException e)
		{
			CustomLogger.printErr("Error in ReadIni cabinet : "+Validations.stackTraceToString(e));
		}
		

	}

	private static String readUploadConfig() {
		String str="";
		try(BufferedReader br=new BufferedReader(new FileReader(System.getProperty(Constants.USERDIR)+File.separator+"ngdbini"+File.separator+"odwebini"+File.separator+"uploadconf.xml"))) {
			String record = "";
			while ((record = br.readLine()) != null) {
				str = String.valueOf(str) + record;
			}
		} catch (Exception e) {
			CustomLogger.printErr("Error in readuploadconf : "+Validations.stackTraceToString(e));
		}
		return str;
	}
	
	

	public String disconnectCabinet(String sessionId) {
		String Status = "";
		try {
			String inXml=XMLGen.getODDisconnectInput(cabinetName, sessionId);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+inXml);
			String outXml=DMSCallBroker.execute(inXml, jtsIP, Integer.parseInt(jtsPort),Constants.UTF8ENCODING, 0, true);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+outXml);
			XMLParser parser=new XMLParser();
			parser.setInputXML(outXml);
			
			Status=parser.getValueOf("Status");
			CustomLogger.printOut("Status==>"+Status);
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in Disconnect cabinet : "+Validations.stackTraceToString(e));
		}
		return Status;
	}
}
