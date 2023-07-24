package com.newgen.bean;

public class Constants {

	private Constants() {
		
	}
	//For Recursive String
	public static final String SUCCESSSTATUSSTRING="Success";
	public static final String FAILSTATUSSTRING="Fail";
	public static final String ERRORSTATUSSTRING="Error";
	public static final String STATUSSTRING="Status";
	public static final String SESSIONIDFORLOGGER="For SessionId :";
	public static final String UTF8ENCODING="UTF-8";
	//For Logger
	public static final String ROLLINGAPPPROP="org.apache.log4j.RollingFileAppender";
    public static final String PATTERNLAYOUTPROP="org.apache.log4j.PatternLayout";
    public static final String FALSESTRING="false";
    public static final String CONVERSIONPATTERN="[%d{dd MMM yyyy HH:mm:ss,SSS} : %-5p] - %m%n%n";
    //For Dynamic HTML
    public static final String CLOSETDWITHNEWLINE="</td>\r\n";
	public static final String CLOSETD="</td>";
	public static final String CLOSEOPTIONTAG="</option>";
	public static final String DEFAULTOPTIONTAG="";
	
	
	//For JDBC
	public static final String SQLQURYSUCCESSSTRING="Result fetched Successfully .";
	public static final String SQLQURYDATASTRING=" With Data -";
	public static final String ERRCLOSINGRS="Error wile closing RS : ";
	public static final String ERRCLOSINGPS="Error wile closing PS : ";
	public static final String ERRCLOSINGCONN="Error wile closing CONN : ";
	
	//DataClass Tags
	public static final String DATADEFINDEXTAG="DataDefIndex";
	public static final String INDEXVALUETAG="IndexValue";
	public static final String INDEXNAMETAG="IndexName";
	
	public static final String FIELDTAG=",Field_";
	public static final String USERDIR="user.dir";
	public static final String TMPDIR="tmpDocs";
	public static final String ERRMSGTOVIEW="Errormsg";
	//Error Messages
	public static final String ALPHANUMERICERRMSG=" cannot contain any of these characters: \\ * | ? \" & <  >  / % '\n\r";
	public static final String ERRMSGGENERAL="Something went wrong,Please try later.\nIf error persist please contact administrator.";
	public static final String NOVIEWPERMISSION="You do not have the permissions to view the documents!";
	public static final String NOUPLOADPERMISSION="You do not have the permissions to upload the documents!";
	public static final String ColumnNames="BR_CODE,BR_NAME";
	public static final String Email_ColumnName="Branch_Email";
	
}
