package com.newgen.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.omg.PortableServer.RequestProcessingPolicyOperations;

import com.newgen.bean.BranchNameBean;
import com.newgen.bean.Cabinet;
import com.newgen.bean.Constants;
import com.newgen.bean.DocumentMetadata;
import com.newgen.bean.RegionNoBean;
import com.newgen.bean.ReportDataBean;
import com.newgen.bean.StatusBean;
import com.newgen.dmsapi.DMSCallBroker;
import com.newgen.logger.CustomLogger;
import com.newgen.omni.jts.cmgr.NGXmlList;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.xml.XMLGen;

import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPISException;
import Jdts.DataObject.JPDBString;

public class ViewDocHelper {
    private static Connection jdbcConnection;
	public StatusBean getSession(String userName,String sessionId) {
		String status=Constants.FAILSTATUSSTRING;
		String message="Error while connecting to cabinet";
		String userdbid;
		XMLParser parser=new XMLParser();
		try {
			String inXML=XMLGen.getODConnectInput(Cabinet.getCabinetName(), userName);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+inXML);
			String outXml=DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+outXml);
			parser.setInputXML(outXml);
			if("0".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))) {
				userdbid=parser.getValueOf("UserDBId");
				status=Constants.SUCCESSSTATUSSTRING;
				message=userdbid;
			}else {
				if("-50198".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))){
					status=Constants.FAILSTATUSSTRING;
					message="Max login user count reached.";
				}else {
					status=Constants.FAILSTATUSSTRING;
					message="Error while connecting to cabinet.";
				}
				
			}
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in getsession : "+Validations.stackTraceToString(e));
		}
		return new StatusBean(status,message);
	}

	public List<DocumentMetadata> getDocument(String userDbId, String leadId, String finoneDocId,  String receiptNo, String wiid,
			String sessionId) {
		List<DocumentMetadata> doclist=new ArrayList<>();
		
		try {
			Map<String, String> params=new HashMap<>();
			params.put("LeadId", leadId);
			params.put("Details - ReceiptNo", receiptNo);
			String inXML=XMLGen.searchDocumentXML(Cabinet.getCabinetName(), userDbId, finoneDocId, params);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+inXML);
			String outXml=DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+outXml);
			XMLParser parser=new XMLParser();
			parser.setInputXML(outXml);
			if("0".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))) {
				String noOfRecordsFetched=parser.getValueOf("NoOfRecordsFetched");
				if(!noOfRecordsFetched.equals("0")) {
					NGXmlList searchList=parser.createList("SearchResults", "SearchResult");
					while(searchList.hasMoreElements()) {
						DocumentMetadata documentMetadata=new DocumentMetadata();
						documentMetadata.setDocumentIndex(searchList.getVal("DocumentIndex"));
						documentMetadata.setDocumentName(searchList.getVal("DocumentName"));
						documentMetadata.setCreatedDatetime(searchList.getVal("CreationDateTime"));
						documentMetadata.setCreatedByAppName(searchList.getVal("CreatedByApp"));
						documentMetadata.setTag(getTagFromDate(documentMetadata.getCreatedDatetime(),sessionId));
						
						/*String dataDefDetails=searchList.getVal("DataDefinition");
						parser.setInputXML(dataDefDetails);
						NGXmlList fieldList=parser.createList("Fields", "Field");
						while(fieldList.hasMoreElements()) {
							String fieldName=fieldList.getVal("IndexName");
							if("Details - ReceiptNo".equalsIgnoreCase(fieldName)) {
								documentMetadata.setReceiptNo(fieldList.getVal("IndexValue"));
								break;
							}
							fieldList.skip();
						}
						if(Validations.isEmptyOrNullValue(documentMetadata.getReceiptNo())){
							documentMetadata.setReceiptNo("-");
						}*/
						doclist.add(documentMetadata);
						searchList.skip();
					}
				}
			}
			
			if(!Validations.isEmptyOrNullValue(wiid)) {
				List<DocumentMetadata> widocList=getDocumentForWIID(userDbId, wiid, sessionId);
				if(!widocList.isEmpty()) {
					doclist.addAll(widocList);
				}
			}
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in getDocument : "+Validations.stackTraceToString(e));
		}		
		
		
		return doclist;
	}
	
	public List<DocumentMetadata> getDocumentForWIID(String userDbId, String wiid,String sessionId) {
		List<DocumentMetadata> doclist=new ArrayList<>();
		try {
			
			String inXML=XMLGen.searchFolderXml(Cabinet.getCabinetName(), userDbId, wiid);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+inXML);
			String outXml=DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+outXml);
			XMLParser parser=new XMLParser();
			parser.setInputXML(outXml);
			if("0".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))) {
				String folderIndex=parser.getValueOf("FolderIndex");
				inXML=XMLGen.getDocumentList(Cabinet.getCabinetName(), userDbId, folderIndex);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+inXML);
				outXml=DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+outXml);
				parser.setInputXML(outXml);
				if("0".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))) {
					String noOfRecordsFetched=parser.getValueOf("NoOfRecordsFetched");
					if(!noOfRecordsFetched.equals("0")) {
						NGXmlList searchList=parser.createList("Documents", "Document");
						while(searchList.hasMoreElements()) {
							DocumentMetadata documentMetadata=new DocumentMetadata();
							documentMetadata.setDocumentIndex(searchList.getVal("DocumentIndex"));
							documentMetadata.setDocumentName(searchList.getVal("DocumentName"));
							documentMetadata.setCreatedDatetime(searchList.getVal("CreationDateTime"));
							documentMetadata.setCreatedByAppName(searchList.getVal("CreatedByApp"));
							documentMetadata.setTag(getTagFromDate(documentMetadata.getCreatedDatetime(),sessionId));
							//documentMetadata.setReceiptNo("-");					
							doclist.add(documentMetadata);
							searchList.skip();
						}
					}
				}
			}
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in getDocument : "+Validations.stackTraceToString(e));
		}
		
		return doclist;
	}

	private String getTagFromDate(String createdDatetime,String sessionId) {
		String tag="new";
		try {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		Date creationDate=simpleDateFormat.parse(createdDatetime);
		Date checkDate=simpleDateFormat.parse("2015-06-16");
		if(creationDate.before(checkDate)) {
			tag="old";
		}
		
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in getDocument : "+Validations.stackTraceToString(e));
		}
		return tag;
	}
	
	public StatusBean getSession_New(String userName,String sessionId, String password) {
		String status=Constants.FAILSTATUSSTRING;
		String message="Error while connecting to cabinet";
		String userdbid;
		XMLParser parser=new XMLParser();
		try {
			String inXML=XMLGen.getODConnectInput(Cabinet.getCabinetName(), userName, password);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+inXML);
			String outXml=DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+outXml);
			parser.setInputXML(outXml);
			if("0".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))) {
				userdbid=parser.getValueOf("UserDBId");
				status=Constants.SUCCESSSTATUSSTRING;
				message=userdbid;
			}else {
				if("-50198".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))){
					status=Constants.FAILSTATUSSTRING;
					message="Max login user count reached.";
				}
				else if("-50167".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))){
					
					String removesession = deleteSession(userName);
					if("success".equalsIgnoreCase(removesession)) {
						getSession_New(userName,sessionId,password);
					}
					else {
					status=Constants.FAILSTATUSSTRING;
					message="User already logged in!!";
					}	
				}
				else {
					status=Constants.FAILSTATUSSTRING;
					message="Error while connecting to cabinet.";
				}
				
			}
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in getsession : "+Validations.stackTraceToString(e));
		}
		return new StatusBean(status,message);
	}
	
	public List<DocumentMetadata> getDocument_New(String userDbId, String Field1, String Field2, String sessionId) {
		List<DocumentMetadata> doclist=new ArrayList<>();
		
		try {
			Map<String, String> params=new HashMap<>();
			params.put("Voucher_Date", Field1);
			params.put("Branch_Code", Field2);
			String inXML=XMLGen.searchDocumentXML_New(Cabinet.getCabinetName(), userDbId, params);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+inXML);
			String outXml=DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+outXml);
			XMLParser parser=new XMLParser();
			parser.setInputXML(outXml);
			String csvExt="";
			String iSIndex="";
			String docname="";
			String csvExt2 = "";
			int i = 0;
			int length2 = 0;
			File f = new File(Cabinet.getDownloadDir());
            if(f.isDirectory())
            {
                File[] file= f.listFiles();
                for(File f1 :file)
                {
                    if((f1.getName().endsWith(".csv") || f1.getName().endsWith(".CSV")) && !(f1.getName().equals(userDbId+".csv") || f1.getName().equals(userDbId+".CSV") ) )
                    {
                        f1.delete();
                        CustomLogger.printErr("CSV file deleted from Temp");
                    }
                }
            }
			if("0".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))) {
				
				String noOfRecordsFetched=parser.getValueOf("NoOfRecordsFetched");
				CustomLogger.printErr("noOfRecordsFetched==>"+noOfRecordsFetched);
				if(!noOfRecordsFetched.equals("0")) {
					NGXmlList searchList=parser.createList("SearchResults", "SearchResult");
					String pages="";
					int pageCount=0;
				    int Vouchercount=0;
				    int rowCount=0;
					while(searchList.hasMoreElements()) {
						DocumentMetadata documentMetadata=new DocumentMetadata();
						List<List<String>> records = new ArrayList<>();
						/*csvExt = parser.getValueOf("CreatedByAppName");
						CustomLogger.printOut("csvExt==>"+csvExt);*/
						csvExt2 = searchList.getVal("CreatedByAppName");
						pages = searchList.getVal("NoOfPages");
						CustomLogger.printOut("csvExt2==>"+csvExt2);
						//***********Getting pageCount********************//
						if(!(csvExt2.equals("csv") || csvExt2.equals("CSV"))) {
							pageCount=pageCount+Integer.parseInt(pages);
						}
						
						
						if(csvExt2.equals("csv") || csvExt2.equals("CSV")) {
						iSIndex = parser.getValueOf("ISIndex");
						docname = parser.getValueOf("DocumentName");
						CustomLogger.printOut("iSIndex==>"+iSIndex);
						CustomLogger.printOut("docname==>"+docname);
						CustomLogger.printOut("CSV file found");
						String result="";
						
						if(csvExt2.equals("csv") || csvExt2.equals("CSV")) {
							 result = downloadDocumentToFile(iSIndex, Cabinet.getDownloadDir(), Cabinet.getCabinetName(), csvExt2, userDbId+"."+csvExt2, Cabinet.getSiteId());
						}else {
						 result = downloadDocumentToFile(iSIndex, Cabinet.getDownloadDir(), Cabinet.getCabinetName(), csvExt2, docname+"."+csvExt2, Cabinet.getSiteId());
						}
						
						String[] msg = result.split("#");
						if (msg[0].equalsIgnoreCase("Success")) {
                        	CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+ sessionId +" Document downloaded Successfully ");
                        	String line = "";
                        	String fileName="";
                        	
                        	fileName = Cabinet.getDownloadDir() + File.separator + userDbId+".csv"; // "D:\\CBI\\EVVR_00138_13092022.csv";
                        	File file = new File(fileName);
                        	if(file.exists()) {
                        		FileInputStream file1 = new FileInputStream(fileName);
                                BufferedReader br = new BufferedReader(new InputStreamReader(file1));
        						//***********Getting voucher Count***************//

                              
                        		String comp="";
                                //ArrayList<String> arr = new ArrayList<String>();
                                  while ((line = br.readLine()) != null) {
                                	  rowCount++;
                                    String[] values = line.split(",");
                                    CustomLogger.printErr("values.length==>"+values.length);
                                    if(values[2].equals(comp)) {
                                    	
                                    }else {
                                    	Vouchercount++;
                                    	comp=values[2];
                                    }
                                    
                                   // records.contains("");
                                    for(int i1 = 0; i1 < values.length; i1++){
                                    	if("".equalsIgnoreCase(values[i1].trim())){
                                    		//CustomLogger.printErr("values====>" + values);
                                        	//CustomLogger.printErr("Blank values at==>" + i1+1);
                                        	values[i1] = "0";
                                        	
                                    	}
                                    }
                                    CustomLogger.printErr("values Null handled==>"+values);
                                    records.add(Arrays.asList(values));
                                    CustomLogger.printErr("records==>"+records);
                                   
                                  }
                        	}
                        	
                           // continue;
                        	}
						}
						//DocumentMetadata documentMetadata=new DocumentMetadata();
                        	
						documentMetadata.setDocumentIndex(searchList.getVal("DocumentIndex"));
						documentMetadata.setDocumentName(searchList.getVal("DocumentName"));
						documentMetadata.setCreatedDatetime(searchList.getVal("CreationDateTime"));
						documentMetadata.setCreatedByAppName(searchList.getVal("CreatedByApp"));
						documentMetadata.setTag(getTagFromDate(documentMetadata.getCreatedDatetime(),sessionId));
						documentMetadata.setValues(records);
						documentMetadata.setPageCount(Integer.toString(pageCount));
						documentMetadata.setVoucherCount(Integer.toString(Vouchercount));
						documentMetadata.setRowCount(Integer.toString(rowCount));
						/*String dataDefDetails=searchList.getVal("DataDefinition");
						parser.setInputXML(dataDefDetails);
						NGXmlList fieldList=parser.createList("Fields", "Field");
						while(fieldList.hasMoreElements()) {
							String fieldName=fieldList.getVal("IndexName");
							if("Details - ReceiptNo".equalsIgnoreCase(fieldName)) {
								documentMetadata.setReceiptNo(fieldList.getVal("IndexValue"));
								break;
							}
							fieldList.skip();
						}
						if(Validations.isEmptyOrNullValue(documentMetadata.getReceiptNo())){*/
							documentMetadata.setReceiptNo("-");
							documentMetadata.setCabinetName(Cabinet.getCabinetName());
						
							String dataDefDetails=searchList.getVal("DataDefinition");
							parser.setInputXML(dataDefDetails);
							CustomLogger.printOut("dataDefDetails====>"+dataDefDetails);
							NGXmlList fieldList=parser.createList("Fields", "Field");
							CustomLogger.printOut("fieldList====>"+fieldList);
							String str="";
							String dateString="";
							while(fieldList.hasMoreElements()) {
								CustomLogger.printOut("fieldList.getVal IndexValue====>"+fieldList.getVal("IndexValue"));
								if(fieldList.getVal("IndexValue") == null || fieldList.getVal("IndexValue").equalsIgnoreCase("null") || fieldList.getVal("IndexValue").equalsIgnoreCase("")){
									dateString = fieldList.getVal("IndexValue");
									dateString = "-";
									str=str+dateString+"##";
								}
								else{
									str=str+fieldList.getVal("IndexValue")+"##";
								}
								fieldList.skip();
							}
							documentMetadata.setString(str);	
							
					//}
						doclist.add(documentMetadata);
						searchList.skip();
					}
				}
			}
			
			/*if(!Validations.isEmptyOrNullValue(Field2)) {
				List<DocumentMetadata> widocList=getEmailIdForBranch(userDbId, Field1, Field2, sessionId);
				if(!widocList.isEmpty()) {
					doclist.addAll(widocList);
				}
			}*/
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in getDocument : "+Validations.stackTraceToString(e));
		}		
		
		
		return doclist;
	}
	
	public List<DocumentMetadata> getDocumentForWIID_New(String userDbId, String Field1, String Field2, String sessionId) {
		List<DocumentMetadata> doclist=new ArrayList<>();
		try {
			
			String inXML=XMLGen.searchFolderXml_New(Cabinet.getCabinetName(), userDbId, Field1, Field2);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+inXML);
			String outXml=DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+outXml);
			XMLParser parser=new XMLParser();
			parser.setInputXML(outXml);
			if("0".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))) {
				String folderIndex=parser.getValueOf("FolderIndex");
				inXML=XMLGen.getDocumentList(Cabinet.getCabinetName(), userDbId, folderIndex);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+inXML);
				outXml=DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+outXml);
				parser.setInputXML(outXml);
				if("0".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))) {
					String noOfRecordsFetched=parser.getValueOf("NoOfRecordsFetched");
					if(!noOfRecordsFetched.equals("0")) {
						NGXmlList searchList=parser.createList("Documents", "Document");
						while(searchList.hasMoreElements()) {
							DocumentMetadata documentMetadata=new DocumentMetadata();
							documentMetadata.setDocumentIndex(searchList.getVal("DocumentIndex"));
							documentMetadata.setDocumentName(searchList.getVal("DocumentName"));
							documentMetadata.setCreatedDatetime(searchList.getVal("CreationDateTime"));
							documentMetadata.setCreatedByAppName(searchList.getVal("CreatedByApp"));
							documentMetadata.setTag(getTagFromDate(documentMetadata.getCreatedDatetime(),sessionId));
							documentMetadata.setReceiptNo("-");					
							doclist.add(documentMetadata);
							searchList.skip();
						}
					}
				}
			}
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in getDocument : "+Validations.stackTraceToString(e));
		}
		
		return doclist;
	}
	public String downloadDocumentToFile(String isIndex, String downloadPath, String cabinetName, String fileExtension, String originalFileName, String siteId) {
		
		CustomLogger.printOut("[DocDownloader.downloadDocumentToFile] Inside downloadDocumentToFile");
        String documentPath = "Fail#Error";
        File dir = new File(downloadPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        JPDBString oSiteName = new JPDBString();
        downloadPath = String.valueOf(String.valueOf(downloadPath)) + File.separator + originalFileName;
        //downloadPath = String.valueOf(String.valueOf(downloadPath)) + File.separator + originalFileName + "." + fileExtension;
        documentPath = "Success#" + downloadPath;
        String jtsPort = Cabinet.getJtsPort();
        short port = Short.parseShort(jtsPort);
        if (!isIndex.equals("")) {
        	//  10#4
            String[] doc = isIndex.split("#");
            int ISDocIndex = Integer.parseInt(doc[0]);
            String strVolId = doc[1];
            boolean flag = true;
            int countDownload = 0;
            while (flag) {
                try {
                    if (countDownload > 0) {
                    	//CustomLogger.printErr("[DocDownloader.downloadDocumentToFile] Trying downloading Document with docIndex : " + ISDocIndex + " :Retrying again for " + countDownload + " Time ");
                    }
                    CPISDocumentTxn.GetDocInFile_MT(null, (String)Cabinet.getJtsIp(), port , cabinetName, (short)Short.parseShort(siteId), (short)Short.parseShort(strVolId), (int)ISDocIndex, (String)"", (String)downloadPath, (JPDBString)oSiteName);
                    flag = false;
                }
                catch (JPISException jpisexception) {
                    try {
                        if (jpisexception.getMessage() != null) {
                            if (jpisexception.getMessage().contains("GetDocInFile_MT : Network Down") && countDownload < 20) {
                                flag = true;
                                try {
                                    Thread.sleep(1000 * countDownload);
                                    //CustomLogger.printErr("[DocDownloader.downloadDocumentToFile] Exception While downloading Document with docIndex : " + ISDocIndex + " " + jpisexception.getMessage() + " :Retrying again");
                                }
                                catch (InterruptedException interruptedException) {}
                            } else {
                                flag = false;
                                //CustomLogger.printErr("[DocDownloader.downloadDocumentToFile] Exception While downloading Document with docIndex : " + ISDocIndex + " " + jpisexception.toString());
                                documentPath = "Fail#" + jpisexception.getMessage() + "#" + downloadPath;
                            }
                        }
                        flag = false;
                        //CustomLogger.printErr("[DocDownloader.downloadDocumentToFile] Exception While downloading Document with docIndex : " + ISDocIndex + " " + jpisexception.toString());
                        documentPath = "Fail#Error Downloading file#" + downloadPath;
                    }
                    catch (Exception e) {
                        flag = false;
                        CustomLogger.printErr("[DocDownloader.downloadDocumentToFile] Exception While downloading Document with docIndex : " + ISDocIndex + " " + jpisexception.toString());
                        documentPath = "Fail#" + e.getMessage() + "#" + downloadPath;
                    }
                }
                catch (Exception ex) {
                    flag = false;
                    CustomLogger.printErr("[DocDownloader.downloadDocumentToFile] Exception While downloading Document with docIndex : " + ISDocIndex + " " + ex.toString());
                    documentPath = "Fail#" + ex.getMessage() + "#" + downloadPath;
                }
                ++countDownload;
            }
        }
        return documentPath;
    }
	
	protected static void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName(Cabinet.getDriverName());
            	//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                                        Cabinet.getJdbcUrl(), Cabinet.getDBUsername(), Cabinet.getDBPassword());
           System.out.println("ViewDocHelper.java connect() ====>"+Cabinet.getJdbcUrl() + Cabinet.getDBUsername() + Cabinet.getDBPassword() + Cabinet.getDriverName());
           System.out.println("ViewDocHelper.java connect() Connection created====>"+jdbcConnection.toString());
        }
    }
	
	public Connection connectNew() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName(Cabinet.getDriverName());
            	//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                                        Cabinet.getJdbcUrl(), Cabinet.getDBUsername(), Cabinet.getDBPassword());
           System.out.println("ViewDocHelper.java connect() ====>"+Cabinet.getJdbcUrl() + Cabinet.getDBUsername() + Cabinet.getDBPassword() + Cabinet.getDriverName());
           System.out.println("ViewDocHelper.java connect() Connection created====>"+jdbcConnection.toString());
        }
		return jdbcConnection;
    }
     
    protected static void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
           // System.out.println("ViewDocHelper.java disconnect() Connection closed====>"+jdbcConnection.toString());
        }
    }
    
    public static void disconnectNew(Connection con) throws SQLException {
        if (con != null && !con.isClosed()) {
        	con.close();
        }
    }
	public static String deleteSession(String username) throws ClassNotFoundException, SQLException {
	    
	    try {
	    	

	        String sql = "delete from PDBConnection where UserIndex in (select UserIndex from PDBuser where username='"+username+")'";
	         
	        connect();
	        System.out.println("ViewDocHelper.java deleteSession()====>"+sql);  
	        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
	        boolean rowDeleted = statement.executeUpdate() > 0;
	        statement.close();
	        disconnect();
	 	    if(rowDeleted==true) {
	 	        return "success";
	 	    }
	 	    else {
	 	        return "error1";
	 	    }
	    	
	    }catch(Exception e) {
	        return "error2";
	    }
	    
	   
	}
	
	 public String checkSession(String ODSession) throws SQLException {
	    	System.out.println("ODSession ViewDocHelper.java===>"+ODSession);
	        String sql = "SELECT * FROM PDBCONNECTION where RANDOMNUMBER = '"+ODSession+"' and STATUSFLAG='Y'";
	        System.out.println("ViewDocHelper.java checkSession()====>"+sql); 
	        //System.out.println("sql===>"+sql); 
	        connect();
	        String sessID="";
	        Statement statement = jdbcConnection.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	        
	        if (resultSet.next()) {
	        	sessID = resultSet.getString("RANDOMNUMBER");
	        	System.out.println("sessID from PDBConnection checkSession()===>"+sessID);
	        }
	        else
	        {
	        	sessID = "";
	        	System.out.println("sessID from PDBConnection checkSession()===>"+sessID);
	        }
	        //System.out.println("sessID===>"+sessID);
	        resultSet.close();
	        statement.close();
	        disconnect();
	        return sessID;     
	    }
	 
	 public List getPickListData(String userDbId) throws SQLException {
			List<BranchNameBean> doclist=new ArrayList<>();
			
			try {
				
				String UserIndex = "";
				String UserName = "";

				String validateInXml = "<?xml version=\"1.0\"?><NGOValidateUserForSession_Input><Option>NGOValidateUserForSession</Option>" 
						+ "<CabinetName>"+Cabinet.getCabinetName()+"</CabinetName>"
						+ "<UserDBId>"+userDbId+"</UserDBId>"
						+ "<Password></Password>" 
						+ "</NGOValidateUserForSession_Input>";
				
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+" - "+validateInXml);
				String validateOutXml=DMSCallBroker.execute(validateInXml, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+" - "+validateOutXml);
				XMLParser parserNew=new XMLParser();
				parserNew.setInputXML(validateOutXml);
				
				if("0".equalsIgnoreCase(parserNew.getValueOf(Constants.STATUSSTRING))) { 
					//Change tyo Username  UserName
					UserIndex = parserNew.getValueOf("UserIndex");
					UserName=parserNew.getValueOf("UserName");
					
		              String sql = "SELECT BR_CO,BR_NAME FROM usr_0_OfficeBranchMaster where ZONE_NO in (Select ZONE_NO from usr_0_UserOfficeMaster where USERNAME='"+UserName+"')";
		              System.out.println("sql===>"+sql);
			        connect();
			        String branchName="";//Department,Section
			        String branchCode="";
			       /* PreparedStatement stmt=jdbcConnection.prepareStatement("SELECT BR_CO,BR_NAME FROM usr_0_OfficeBranchMaster where ZONE_NO in (Select ZONE_NO from usr_0_UserOfficeMaster where USERNAME='"+UserName+"')");  
			        ResultSet rs=stmt.executeQuery();*/
			        Statement stmt = jdbcConnection.createStatement();
			        ResultSet rs = stmt.executeQuery(sql);
			        /*System.out.println("statement===>"+stmt);
			        System.out.println("resultSet===>"+rs);
			        System.out.println("resultSet.getFetchSize===>"+rs.getFetchSize());*/
			       while(rs.next()){  
			        	System.out.println("Inside While===>");
			        	BranchNameBean BranchNameBean=new BranchNameBean();
			        	branchName = rs.getString("BR_NAME");
			        	branchCode = rs.getString("BR_CO");
			        	/*System.out.println("branchName===>"+branchName);
			        	System.out.println("branchCode===>"+branchCode);*/
		
			        	BranchNameBean.setBranchCode(rs.getString("BR_CO"));
			        	BranchNameBean.setBranchName(rs.getString("BR_NAME"));
			        	BranchNameBean.setUserName(UserName);
			        	doclist.add(BranchNameBean);
				        System.out.println("getPickListData from usr_0_OfficeBranchMaster ()===>"+branchName+branchCode);
			        } 
			        rs.close();
			        stmt.close();
			        disconnect();
			        return doclist;    
				}
							
			}catch (Exception e) {
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+" - "+"Error in getDocument : "+Validations.stackTraceToString(e));
			}		
			
			
			return doclist;
		}
	 public List<BranchNameBean> branchNameList(String username) throws SQLException {
			List<BranchNameBean> doclist=new ArrayList<>();
			
	    	System.out.println("username ViewDocHelper.java===>"+username);
	        /*String sql = "select o.br_name from branch_master b join usr_0_officemaster o on b.region_no = o.region_no where b.username='"+username+"'";*/
	    	String sql = "SELECT * FROM usr_0_OfficeBranchMaster BM inner join usr_0_UserOfficeMaster OM on BM.ZONE_NO=OM.ZONE_NO where OM.USERNAME='"+username+"'";
	        System.out.println("ViewDocHelper.java branchNameList()====>"+sql); 
	        //System.out.println("sql===>"+sql); 
	        connect();
	        String branchName="";
	        Statement statement = jdbcConnection.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	      
	        	 while(resultSet.next()) {
	        		 BranchNameBean branchnameData =new BranchNameBean();
	        		 System.out.println("BranchName====>"+resultSet.getString("BR_CO"));
	        		 System.out.println("BranchName====>"+resultSet.getString("BR_NAME"));
	        	   	  branchnameData.setBranchCode(resultSet.getString("BR_CO"));
					  branchnameData.setBranchName(resultSet.getString("BR_NAME"));
					  branchnameData.setUserName(username);
	        	   //branchnameData.setBranchName(branchName);
				   doclist.add(branchnameData); 
				 }
	        	 System.out.println("branchNameList====>"+doclist.toArray());
	        	 CustomLogger.printOut("branchNameList====>"+doclist.toArray());
	        resultSet.close();
	        statement.close();
	        disconnect();
	        return doclist;     
	    }
	 
	 public String getEmailIdForBranch(String branchCode) throws SQLException {
		 
			try {
	    	System.out.println("Barnch Code ViewDocHelper.java==> getEmailIdForBranch ===>"+branchCode);
	        /*String sql = "select o.br_name from branch_master b join usr_0_officemaster o on b.region_no = o.region_no where b.username='"+username+"'";*/
	    	String sql = "select * from usr_0_OfficeBranchMaster where BR_CO='"+branchCode+"'";
	        System.out.println("ViewDocHelper.java getEmailIdForBranch()====>"+sql); 
	        //System.out.println("sql===>"+sql); 
	        connect();
	        String branchMail="";
	        Statement statement = jdbcConnection.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	      
	        	 while(resultSet.next()) {
	        		 branchMail = resultSet.getString("BR_EMAIL");
				 }
	        	 CustomLogger.printOut("branchMail====>"+branchMail);
	        resultSet.close();
	        statement.close();
	        disconnect();
	        return branchMail;
			}
			catch(SQLException ex) {
				CustomLogger.printOut("ex====>"+ex.toString());
				return "Error";
			}
	       
		}
	 
	 public void changeDocProperty(String userDbId, String Field1, String Field2, String sessionId, String DocId,
			 String isSubmitted, String isMailedtoBranch, String overallRemarks) {
			try {
				Map<String, String> params=new HashMap<>();
				//Uncomment here for testing on Local Start
				/*params.put("Voucher_Date", Field1);
				params.put("brnchCode", Field2);
				params.put("TestDate", Field1);
				params.put("IsMailed", isMailedtoBranch);
				params.put("IsSubmitted", isSubmitted);
				params.put("Remarks", overallRemarks);*/
				//Uncomment here for testing on Local End
				
				params.put("Voucher_Date", Field1);
				params.put("Branch_Code", Field2);
				//params.put("TestDate", Field1);
				params.put("IsMailed", isMailedtoBranch);
				params.put("IsSubmitted", isSubmitted);
				params.put("Remarks", overallRemarks);
				String inXML=XMLGen.getchangeDocPropertyXML(Cabinet.getCabinetName(), userDbId, DocId, Cabinet.getDataDefName(), params);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+inXML);
				String outXml=DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+outXml);
				XMLParser parser=new XMLParser();
				parser.setInputXML(outXml);
				if("0".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))) {
					CustomLogger.printOut("Documents Metadata Updated");
					CustomLogger.writeXML("Documents Metadata Updated");
				}
				
			}catch (Exception e) {
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in getDocument : "+Validations.stackTraceToString(e));
			}
			
		}	 
	 
	 public void saveReportData(String branchCode,String voucherdate,String verifiedby,String rectifiedCount,
			 String voucherOkCount, String totalVoucher, String totalImages, String totalVoucherDistinct) throws SQLException 
	 	{
			try {
			Date date = new Date();
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		    String datenewformat = sdf2.format(date);
	    	String sql = "INSERT INTO usr_0_VOUCHERREPORT "
	    			+ " (BRANCH_CODE,VOUCHER_DATE,VERIFICATION_DATE,VERIFIED_BY,RECTIFIED_COUNT,VOUCHEROK_COUNT,TOTAL_VOUCHER,TOTAL_IMAGES,TOTAL_VOUCHER_DISTINCT) "
	    			+ " VALUES ('"+branchCode+"',To_Date('"+voucherdate+"','YYYY-MM-DD'),To_Date('"+datenewformat+"','YYYY-MM-DD'),'"+verifiedby+"','"+rectifiedCount+"',"
	    					+ " '"+voucherOkCount+"','"+totalVoucher+"','"+totalImages+"','"+totalVoucherDistinct+"')";
	        System.out.println("ViewDocHelper.java saveReportData()====>"+sql); 
	        CustomLogger.printOut("ViewDocHelper.java saveReportData()====>"+sql);
	        connect();
	        Statement statement = jdbcConnection.createStatement();
	        statement.executeUpdate(sql);
	        statement.close();
	        disconnect();
		}
			catch(SQLException ex) {
				//CustomLogger.printOut("ex====>"+ex.toString());
				CustomLogger.printErr("Exception in saveReportData()====>"+ex.toString());
				CustomLogger.printErr("Exception in saveReportData()====>"+ex.getStackTrace());
				CustomLogger.printOut("Exception in saveReportData()====>"+ex.toString());
			}
		}
	 
	 public List getPickListDataRegionCode(String userDbId) throws SQLException {
			List<RegionNoBean> doclist=new ArrayList<>();
			
			try {
				
				String UserIndex = "";
				String UserName = "";

				String validateInXml = "<?xml version=\"1.0\"?><NGOValidateUserForSession_Input><Option>NGOValidateUserForSession</Option>" 
						+ "<CabinetName>"+Cabinet.getCabinetName()+"</CabinetName>"
						+ "<UserDBId>"+userDbId+"</UserDBId>"
						+ "<Password></Password>" 
						+ "</NGOValidateUserForSession_Input>";
				
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+" - "+validateInXml);
				String validateOutXml=DMSCallBroker.execute(validateInXml, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+" - "+validateOutXml);
				XMLParser parserNew=new XMLParser();
				parserNew.setInputXML(validateOutXml);
				
				if("0".equalsIgnoreCase(parserNew.getValueOf(Constants.STATUSSTRING))) { 
					//Change tyo Username  UserName
					UserIndex = parserNew.getValueOf("UserIndex");
					UserName=parserNew.getValueOf("UserName");
					String sql = "SELECT distinct Region_No,Region_NAME FROM usr_0_OfficeBranchMaster where REGION_NO in (Select REGION_NO from usr_0_UserOfficeMaster where USERNAME='"+UserName+"')";
		              //String sql = "SELECT BR_CO,BR_NAME FROM usr_0_OfficeBranchMaster where ZONE_NO in (Select ZONE_NO from usr_0_UserOfficeMaster where USERNAME='"+UserName+"')";
		              System.out.println("sql===>"+sql);
		              CustomLogger.printOut("Region Code SQL===>"+sql);
			        connect();
			        String branchName="";//Department,Section
			        String branchCode="";
			       /* PreparedStatement stmt=jdbcConnection.prepareStatement("SELECT BR_CO,BR_NAME FROM usr_0_OfficeBranchMaster where ZONE_NO in (Select ZONE_NO from usr_0_UserOfficeMaster where USERNAME='"+UserName+"')");  
			        ResultSet rs=stmt.executeQuery();*/
			        Statement stmt = jdbcConnection.createStatement();
			        ResultSet rs = stmt.executeQuery(sql);
			        /*System.out.println("statement===>"+stmt);
			        System.out.println("resultSet===>"+rs);
			        System.out.println("resultSet.getFetchSize===>"+rs.getFetchSize());*/
			       while(rs.next()){  
			        	//System.out.println("Inside While===>");
			    	   RegionNoBean RegionNoBean=new RegionNoBean();
			        	branchName = rs.getString("Region_no");
			        	branchCode = rs.getString("Region_NAME");
			        	/*System.out.println("branchName===>"+branchName);
			        	System.out.println("branchCode===>"+branchCode);*/
		
			        	RegionNoBean.setRegion_No(rs.getString("Region_no"));
			        	RegionNoBean.setRegionName(rs.getString("Region_NAME"));
			        	RegionNoBean.setUserName(UserName);
			        	doclist.add(RegionNoBean);
				        System.out.println("getPickListDataRegionCode from usr_0_OfficeBranchMaster ()===>"+branchName+branchCode);
			        } 
			        rs.close();
			        stmt.close();
			        disconnect();
			        return doclist;    
				}
							
			}catch (Exception e) {
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+" - "+"Error in getDocument : "+Validations.stackTraceToString(e));
			}		
			
			
			return doclist;
		}
	 
	 public List getReport(String VoucherDate, String Region_No) throws SQLException {
		 List<ReportDataBean> doclist=new ArrayList<>();
			try {
			/*String sql = "select * from usr_0_OfficeBranchMaster OBM "
				+	" left join usr_0_VOUCHERREPORT VR on VR.Branch_Code = OBM.BR_CO where OBM.REGION_NO ='"+Region_No+"'";*/
				
		/////////Uncomment this to run on local environment		
		/*String sql ="select * from usr_0_OfficeBranchMaster OBM  left join usr_0_VOUCHERREPORT VR on VR.Branch_Code = OBM.BR_CO where OBM.REGION_NO ='"+Region_No+"' "
		+	"	and (VR.VOUCHER_DATE = '"+VoucherDate+"' or VR.VOUCHER_DATE is null or vr.voucher_date = '')";*/
				
				
	//CBI environment query /// Comment this to run on local			
	String sql ="select * from usr_0_OfficeBranchMaster OBM  left join usr_0_VOUCHERREPORT VR on VR.Branch_Code = OBM.BR_CO where OBM.REGION_NO ='"+Region_No+"' "
	+	"	and (VR.VOUCHER_DATE = To_Date('"+VoucherDate+"','YYYY-MM-DD') or VR.VOUCHER_DATE is null or trim(vr.voucher_date) = '')";
			
	              System.out.println("sql===>"+sql);
	              CustomLogger.printOut("Region Code SQL===>"+sql);
		        connect();
		        String branchName="";//Department,Section
		        String branchCode="";
		        Statement stmt = jdbcConnection.createStatement();
		        ResultSet rs = stmt.executeQuery(sql);
		        
		        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    			Date date = (Date)formatter.parse(VoucherDate);

    			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    			String newVoucherDate = dateFormat.format(date);
		       while(rs.next()){  
		    	   String verifiedBy="";
		    	   //String VERIFICATION_DATE="";
		    	   ReportDataBean ReportDataBean=new ReportDataBean();
		    	   ReportDataBean.setBranchCode(rs.getString("BR_CO"));
		    	   ReportDataBean.setBranchName(rs.getString("BR_NAME"));
		    	   verifiedBy = rs.getString("VERIFIED_BY");
		    	   
		    	   //CustomLogger.printOut("verifiedBy=====>"+verifiedBy);
		    	   if(verifiedBy == null || verifiedBy.equals("null") || "".equalsIgnoreCase(verifiedBy) || "null".equalsIgnoreCase(verifiedBy) ){
		    		   ReportDataBean.setVerificationDate("NA");
		    		   ReportDataBean.setVerifiedBy("NA");
		    		   ReportDataBean.setTotalEVVRRecords("0");
		    		   ReportDataBean.setVoucherOkRecords("0");
			    	   ReportDataBean.setRectifiedRecords("0");
		    		   ReportDataBean.setAuditStatus("Pending");
		    		   ReportDataBean.setVoucherDate(newVoucherDate);
		    	   }
		    	   else{
		    		   String emptyString1 = ""; 
		    		   emptyString1 = rs.getString("VERIFICATION_DATE");
	        		   emptyString1 = emptyString1.replaceAll("00:00:00", "");
	        			DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
	        			Date date2 = (Date)formatter2.parse(emptyString1);

	        			DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
	        			String mydate = dateFormat2.format(date2);
		    		   ReportDataBean.setVerificationDate(mydate);
		    		   ReportDataBean.setVerifiedBy(verifiedBy);
		    		   ReportDataBean.setTotalEVVRRecords(rs.getString("TOTAL_VOUCHER"));
		    		   ReportDataBean.setVoucherOkRecords(rs.getString("VOUCHEROK_COUNT"));
			    	   ReportDataBean.setRectifiedRecords(rs.getString("RECTIFIED_COUNT"));
		    		   ReportDataBean.setAuditStatus("Completed");
		    		   ReportDataBean.setVoucherDate(newVoucherDate);
		    	   }
		        	doclist.add(ReportDataBean);
		        	CustomLogger.printOut("getPickListDataRegionCode from usr_0_OfficeBranchMaster ()===>"+doclist.toArray().toString());
		        } 
		        rs.close();
		        stmt.close();
		        disconnect();
		        return doclist;    
						
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+" - "+"Error in getDocument : "+Validations.stackTraceToString(e));
		}
			return doclist;
	 }
	 
	 public String getEmailIdForRo(String branchCode) throws SQLException {
		 
			try {
	    	System.out.println("Branch Code ViewDocHelper.java==> getEmailIdForRo ===>"+branchCode);
	        /*String sql = "select o.br_name from branch_master b join usr_0_officemaster o on b.region_no = o.region_no where b.username='"+username+"'";*/
	    	String sql = "select * from usr_0_OfficeBranchMaster where BR_CO='"+branchCode+"'";
	        System.out.println("ViewDocHelper.java getEmailIdForRo()====>"+sql); 
	        CustomLogger.printOut("ViewDocHelper.java getEmailIdForRo()====>"+sql); 
	        //System.out.println("sql===>"+sql); 
	        connect();
	        String roEMail="";
	        Statement statement = jdbcConnection.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	      
	        	 while(resultSet.next()) {
	        		 roEMail = resultSet.getString("RO_EMAIL");
				 }
	        	 CustomLogger.printOut("RO_EMAIL getEmailIdForRo====>"+roEMail);
	        resultSet.close();
	        statement.close();
	        disconnect();
	        return roEMail;
			}
			catch(SQLException ex) {
				CustomLogger.printOut("ex getEmailIdForRo====>"+ex.toString());
				return "Error";
			}
	       
		}

}
