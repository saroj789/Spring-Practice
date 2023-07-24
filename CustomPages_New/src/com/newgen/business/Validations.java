package com.newgen.business;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.Tika;

import com.newgen.bean.Cabinet;
import com.newgen.bean.Constants;
import com.newgen.bean.StatusBean;
import com.newgen.logger.CustomLogger;

import ISPack.ISUtil.JPISException;

public class Validations {

	public static boolean isAlphaNumeric(String strFieldInput) {
		String regexString = "['`\"\\*?&%<>|]";
		boolean status = true;
		try {

			if ((regexString != null) && (regexString.length() > 0) && (strFieldInput != null)
					&& (strFieldInput.length() > 0)) {
				Pattern pattern = Pattern.compile(regexString);
				Matcher matcher = pattern.matcher(strFieldInput);

				if (matcher.find()) {
					status = false;
					return status;
				}

				int[] charAsciiVals = new int[regexString.length() - 2];
				for (int regIndex = 1; regIndex < regexString.length() - 1; regIndex++) {
					charAsciiVals[(regIndex - 1)] = regexString.charAt(regIndex);
				}

				int charAsciiVal = 0;
				for (int strFieldIndex = 0; strFieldIndex < strFieldInput.length(); strFieldIndex++) {
					charAsciiVal = strFieldInput.charAt(strFieldIndex);
					for (int charIndex = 0; charIndex < charAsciiVals.length; charIndex++) {
						if (charAsciiVal == charAsciiVals[charIndex]) {
							status = false;
							break;
						}

					}
					if (charAsciiVal != 8217 && ((charAsciiVal < 32) || (charAsciiVal > 126))) {
						status = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			CustomLogger.printErr("Error while validating Alphanumeric data : " + stackTraceToString(e));
		}

		return status;
	}

	public boolean isAlphaBetic(String s) {
		return s.matches("[a-zA-Z]+");
	}

	public boolean isNumeric(String s) {
		return s.matches("[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?");
	}

	public static String stackTraceToString(Throwable ex) {
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		ex.printStackTrace(printWriter);
		return result.toString();
	}

	public static String stackTraceToString(JPISException e) {
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		e.printStackTrace(printWriter);
		return result.toString();
	}

	public static boolean isEmpty(String str) {
		boolean retString = true;
		if (str != null && !str.trim().equals("")) {
			retString = false;
		}

		return retString;

	}

	public static boolean isEmptyOrNullValue(String str) {
		boolean retString = true;
		if (str != null && !str.trim().equals("") && !str.trim().equalsIgnoreCase("null")) {
			retString = false;
		}
		return retString;
	}

	public static String formatDate(String dt) {
		String formattedDate = dt;
		if (isEmptyOrNullValue(formattedDate))
			return "";
		try {
			SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
			Date tempdt = sd1.parse(dt);
			sd1 = new SimpleDateFormat("MMM dd,yyyy");
			formattedDate = sd1.format(tempdt);
		} catch (Exception e) {
			CustomLogger.printErr("Error in formatDate : " + e.toString());
			try {
				SimpleDateFormat sd1 = new SimpleDateFormat("MM/dd/yyyy");
				Date tempdt = sd1.parse(dt);
				sd1 = new SimpleDateFormat("MMM dd,yyyy");
				formattedDate = sd1.format(tempdt);
			} catch (Exception e2) {
				CustomLogger.printErr("Error in formatDate : " + e2.toString());
				try {
					SimpleDateFormat sd1 = new SimpleDateFormat("dd-MMM-yyyy");
					Date tempdt = sd1.parse(dt);
					sd1 = new SimpleDateFormat("MMM dd,yyyy");
					formattedDate = sd1.format(tempdt);
				} catch (Exception e3) {
					CustomLogger.printErr("Error in formatDate : " + e3.toString());
				}
			}
		}
		return formattedDate;
	}

	public static boolean validateDate(String value) {
		boolean isvalidDate = true;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			sdf.setLenient(false);
			sdf.parse(value);
		} catch (ParseException e) {
			CustomLogger.printErr("Error in validateDate : " + e.toString());
			isvalidDate = false;
		}
		return isvalidDate;
	}

	private String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf('.') != -1 && fileName.lastIndexOf('.') != 0) {
			return fileName.substring(fileName.lastIndexOf('.') + 1);
		}

		else
			return "";
	}

	public StatusBean validateUploadData(String userId, String leadId, String finoneDocId, String sessionId) {
		String status = Constants.SUCCESSSTATUSSTRING;
		StringBuilder message = new StringBuilder();
		int counter = 1;
		try {
			if (userId.length() > 255 || !isAlphaNumeric(userId)) {
				status = Constants.FAILSTATUSSTRING;
				message.append(
						counter++ + ".UserName should be alphanumeric and should be less than 255 character. <br><br>");
			}
			if (leadId.length() > 50 || !isAlphaNumeric(leadId)) {
				status = Constants.FAILSTATUSSTRING;
				message.append(
						counter++ + ".LeadId should be alphanumeric and should be less than 50 character. <br><br>");
			}
			if (finoneDocId.length() > 50 || !isAlphaNumeric(finoneDocId)) {
				status = Constants.FAILSTATUSSTRING;
				message.append(counter
						+ ".FinnOneDocId should be alphanumeric and should be less than 50 character. <br><br>");
			}
		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in validateUploadData : "
					+ stackTraceToString(e));
		}
		return new StatusBean(status, message.toString());

	}

	public StatusBean validateUploadData(Map<String, String> params, Map<String, String> files, String sessionId) {
		String status = Constants.SUCCESSSTATUSSTRING;
		StringBuilder message = new StringBuilder();
		try {
			String userId = params.get("UserName");
			String leadId = params.get("LeadId");
			String finoneDocId = params.get("FinoneDocId");
			String receiptNo = params.get("Details - ReceiptNo");

			if (userId.length() > 255 || !isAlphaNumeric(userId)) {
				status = Constants.FAILSTATUSSTRING;
				message.append("UserName should be alphanumeric and should be less than 255 character \n");
			}
			if (leadId.length() > 50 || !isAlphaNumeric(leadId)) {
				status = Constants.FAILSTATUSSTRING;
				message.append("LeadId should be alphanumeric and should be less than 50 character \n");
			}
			if (finoneDocId.length() > 50 || !isAlphaNumeric(finoneDocId)) {
				status = Constants.FAILSTATUSSTRING;
				message.append("FinnOneDocId should be alphanumeric and should be less than 50 character\n");
			}
			if (receiptNo.length() > 50 || !isAlphaNumeric(receiptNo)) {
				status = Constants.FAILSTATUSSTRING;
				message.append("Receiptno should be alphanumeric and should be less than 50 character\n");
			}
			if (files.isEmpty()) {
				status = Constants.FAILSTATUSSTRING;
				message.append("Please Select File");
			}
			Cabinet cabinet = new Cabinet();
			for (String filepath : files.keySet()) {
				File f2 = new File(filepath);
				Object isFileExtAllowed = cabinet.getExtensions().get(getFileExtension(f2).toLowerCase());
				String fileMimeType=new Tika().detect(f2).toUpperCase();
				if (isFileExtAllowed == null || "false".equalsIgnoreCase(isFileExtAllowed.toString())) {
					status = Constants.FAILSTATUSSTRING;
					message.append("File type " + getFileExtension(f2) + " is not supported");
				}else if(!Cabinet.getAllowedmimeType().contains(fileMimeType)) {
					status = Constants.FAILSTATUSSTRING;
					message.append("Selected file type is restricted for upload");
					CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "file restricted for upload : "
							+ "MimeType = "+fileMimeType);
				}
			}
		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in validateUploadData : "
					+ stackTraceToString(e));
		}

		return new StatusBean(status, message.toString());
	}

	public StatusBean validateViewData(String userId, String leadId, String finoneDocId, String wiid, String receiptNo, String sessionId) {
		String status = Constants.SUCCESSSTATUSSTRING;
		StringBuilder message = new StringBuilder();
		try {

			if (userId.length() > 255 || !isAlphaNumeric(userId)) {
				status = Constants.FAILSTATUSSTRING;
				message.append("UserName should be alphanumeric and should be less than 255 character \n");
			}
			if (leadId.length() > 50 || !isAlphaNumeric(leadId)) {
				status = Constants.FAILSTATUSSTRING;
				message.append("LeadId should be alphanumeric and should be less than 50 character \n");
			}
			if (finoneDocId.length() > 50 || !isAlphaNumeric(finoneDocId)) {
				status = Constants.FAILSTATUSSTRING;
				message.append("FinnOneDocId should be alphanumeric and should be less than 50 character\n");
			}
			if (wiid.length() > 50 || !isAlphaNumeric(wiid)) {
				status = Constants.FAILSTATUSSTRING;
				message.append("WIID should be alphanumeric and should be less than 50 character\n");
			}
			if (receiptNo.length() > 50 || !isAlphaNumeric(receiptNo)) {
				status = Constants.FAILSTATUSSTRING;
				message.append("Receiptno should be alphanumeric and should be less than 50 character\n");
			}
			
		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in validateUploadData : "
					+ stackTraceToString(e));
		}

		return new StatusBean(status, message.toString());
	}
	
	public StatusBean validateUploadData_New(String Field1, String Field2, String sessionId) {
		String status = Constants.SUCCESSSTATUSSTRING;
		StringBuilder message = new StringBuilder();
		int counter = 1;
		try {
			if (Field1.length() > 255 || !isAlphaNumeric(Field1)) {
				status = Constants.FAILSTATUSSTRING;
				message.append(
						counter++ + ".UserName should be alphanumeric and should be less than 255 character. <br><br>");
			}
			if (Field2.length() > 50 || !isAlphaNumeric(Field2)) {
				status = Constants.FAILSTATUSSTRING;
				message.append(
						counter++ + ".LeadId should be alphanumeric and should be less than 50 character. <br><br>");
			}
		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in validateUploadData : "
					+ stackTraceToString(e));
		}
		return new StatusBean(status, message.toString());

	}

}
