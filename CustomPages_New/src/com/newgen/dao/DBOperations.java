package com.newgen.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.newgen.bean.Cabinet;
import com.newgen.bean.Constants;
import com.newgen.bean.StatusBean;
import com.newgen.business.Validations;
import com.newgen.logger.CustomLogger;
import com.newgen.omni.jts.srvr.NGDBConnection;

public class DBOperations {

	static String cabinetName = "";

	static {
		cabinetName = Cabinet.getCabinetName();
	}

	public String getWIID(String leadId, String sessionId) {

		String wiid = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = NGDBConnection.getDBConnection(cabinetName, "");
			String sql = "SELECT WIID FROM FINNONE_OMNIMAPING where leadid = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, leadId);
			CustomLogger.writeTxn(
					Constants.SESSIONIDFORLOGGER + sessionId + " - " + sql + Constants.SQLQURYDATASTRING + leadId);
			rs = ps.executeQuery();
			CustomLogger.writeTxn(Constants.SESSIONIDFORLOGGER + sessionId + " - " + Constants.SQLQURYSUCCESSSTRING);
			if (rs.next()) {
				wiid = rs.getString(1);
			}
		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in ValidateUser : "
					+ Validations.stackTraceToString(e));
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + Constants.ERRCLOSINGRS
						+ Validations.stackTraceToString(e));
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + Constants.ERRCLOSINGPS
						+ Validations.stackTraceToString(e));
			}
			try {
				if (conn != null)
					NGDBConnection.closeDBConnection(conn, "");
			} catch (Exception e) {
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + Constants.ERRCLOSINGCONN
						+ Validations.stackTraceToString(e));
			}
		}
		return wiid;
	}

	public StatusBean checkLeadIDDatabase(String leadId, String finoneDocId, String sessionId) {
		String status = Constants.FAILSTATUSSTRING;
		String message = "Error while checking lead id in DB";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = NGDBConnection.getDBConnection(cabinetName, "");
			String sql = "SELECT count(*) FROM (SELECT APPID,finndocid FROM "+Cabinet.getDepartmentSectionMasterTable()+"  where APPID = ? and finndocid = ? Union all Select APPID,finndocid FROM "+Cabinet.getUserAndDepartmentSectionMappingTable()+" where APPID = ? and finndocid = ?)a ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, leadId);
			ps.setString(2, finoneDocId);
			ps.setString(3, leadId);
			ps.setString(4, finoneDocId);
			CustomLogger.writeTxn(
					Constants.SESSIONIDFORLOGGER + sessionId + " - " + sql + Constants.SQLQURYDATASTRING + leadId);
			rs = ps.executeQuery();
			CustomLogger.writeTxn(Constants.SESSIONIDFORLOGGER + sessionId + " - " + Constants.SQLQURYSUCCESSSTRING);
			if (rs.next()) {
				int count = rs.getInt(1);
				if(count==0) {
					status = Constants.FAILSTATUSSTRING;
					message = "Data doesnot exist for the LeadID in maintenance";
				}else {
					status = Constants.SUCCESSSTATUSSTRING;
					message = "Data exists for the LeadID";
				}
			}
		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in ValidateUser : "
					+ Validations.stackTraceToString(e));
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + Constants.ERRCLOSINGRS
						+ Validations.stackTraceToString(e));
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + Constants.ERRCLOSINGPS
						+ Validations.stackTraceToString(e));
			}
			try {
				if (conn != null)
					NGDBConnection.closeDBConnection(conn, "");
			} catch (Exception e) {
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + Constants.ERRCLOSINGCONN
						+ Validations.stackTraceToString(e));
			}
		}
		return new StatusBean(status, message);
	}
	
	public boolean updateFinnOneStatus(String docId,String leadId, String finoneDocId, String sessionId) {
		boolean status=false;
		Connection conn = null;
		CallableStatement  cs = null;

		try {
			conn = NGDBConnection.getDBConnection(cabinetName, "");
			String sql = "{call FIC_FINNONE_INTEGRATION.UpdateFinnoneData (?, ?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, docId);
			cs.setString(2, leadId);
			cs.setString(3, finoneDocId);
			CustomLogger.writeTxn(
					Constants.SESSIONIDFORLOGGER + sessionId + " - " + sql + Constants.SQLQURYDATASTRING + leadId);
			cs.execute();
			CustomLogger.writeTxn(Constants.SESSIONIDFORLOGGER + sessionId + " - " + Constants.SQLQURYSUCCESSSTRING);
			status=true;
		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in ValidateUser : "
					+ Validations.stackTraceToString(e));
		} finally {
			
			try {
				if (cs != null)
					cs.close();
			} catch (Exception e) {
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + Constants.ERRCLOSINGPS
						+ Validations.stackTraceToString(e));
			}
			try {
				if (conn != null)
					NGDBConnection.closeDBConnection(conn, "");
			} catch (Exception e) {
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + Constants.ERRCLOSINGCONN
						+ Validations.stackTraceToString(e));
			}
		}
		return status;
	}
}
