package com.newgen.business;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Set;

import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.TiffImage;
import com.newgen.bean.Cabinet;
import com.newgen.bean.Constants;
import com.newgen.bean.StatusBean;
import com.newgen.dao.DBOperations;
import com.newgen.dmsapi.DMSCallBroker;
import com.newgen.logger.CustomLogger;
import com.newgen.omni.jts.cmgr.NGXmlList;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.opall.Viewer;
import com.newgen.xml.XMLGen;

import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPDBRecoverDocData;
import ISPack.ISUtil.JPISException;
import ISPack.ISUtil.JPISIsIndex;

public class AddDocHelper {

	public StatusBean addDocument(Map<String, String> params, Map<String, String> files, String sessionId) {
		String status = Constants.FAILSTATUSSTRING;
		String message = "Fail to add document";
		String userdbid = "";
		String leadId = params.get("LeadId");
		String finoneDocId = params.get("FinoneDocId");
		String userName = params.get("UserName");
		XMLParser parser = new XMLParser();
		Cabinet cabinet = new Cabinet();
		try {
			String inXML = XMLGen.getODConnectInput(Cabinet.getCabinetName(), userName);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER + sessionId + " - " + inXML);
			String outXml = DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()),
					Constants.UTF8ENCODING, 0, true);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER + sessionId + " - " + outXml);
			parser.setInputXML(outXml);
			if ("0".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))) {
				userdbid = parser.getValueOf("UserDBId");
				cabinet.setCabUserDbId(userdbid);
			} else {
				if ("-50198".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))) {
					status = Constants.FAILSTATUSSTRING;
					message = "Max login user count reached.";
					return new StatusBean(status, message);
				} else {
					status = Constants.FAILSTATUSSTRING;
					message = "Error while connecting to cabinet.";
					return new StatusBean(status, message);
				}

			}

			DBOperations dbOperations = new DBOperations();
			StatusBean statusBean = dbOperations.checkLeadIDDatabase(leadId, finoneDocId, sessionId);
			if (statusBean.getStatus().equals(Constants.FAILSTATUSSTRING)) {
				return statusBean;
			}
			String folderPath = Cabinet.getCabinetName()+"/FINNONE/" + leadId;
			return uploadDoc(cabinet, params, folderPath, files, sessionId);

		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in AddDocument : "
					+ Validations.stackTraceToString(e));
		} finally {
			if (!Validations.isEmpty(cabinet.getCabUserDbId())) {
				cabinet.disconnectCabinet(sessionId);
			}

		}
		return new StatusBean(status, message);
	}

	public StatusBean uploadDoc(Cabinet cabinet, Map<String, String> params, String folderpath,
			Map<String, String> files, String sessionId) {
		String status = Constants.FAILSTATUSSTRING;
		String message = "Error while addinf document";
		JPISIsIndex isindex;
		XMLParser xmlparser = new XMLParser();
		try {

			StringBuilder dataDefForDocument = new StringBuilder();
			String[] dataclassFieldsDocument = Cabinet.getDataClassFields().split(",");
			for (int i = 0; i < dataclassFieldsDocument.length; i++) {
				String[] fields = dataclassFieldsDocument[i].split("#");
				dataDefForDocument.append("<Field>" + "<IndexId>" + fields[0] + "</IndexId>" + "<IndexName>" + fields[1]
						+ "</IndexName>" + "<IndexType>" + fields[2] + "</IndexType>");
				if (params.get(fields[1]) != null) {
					dataDefForDocument.append("<IndexValue>" + params.get(fields[1]) + "</IndexValue>");
				} else {
					dataDefForDocument.append("<IndexValue> </IndexValue>");
				}
				dataDefForDocument.append("</Field>");
			}

			String folderIndex = getFolderIndex(folderpath, cabinet, sessionId);
			if (Validations.isEmptyOrNullValue(folderIndex)) {
				status = Constants.FAILSTATUSSTRING;
				message = "Error while getting folder index.";
				return new StatusBean(status, message);
			}
			String finoneDocId = params.get("FinoneDocId");
			Set<String> fileList = files.keySet();
			for (String filepath : fileList) {
				int numberOfPages = 1;
				File f = new File(filepath);
				String fileName = files.get(filepath);
				String ext = getFileExtension(f);
				fileName = fileName.substring(0, fileName.indexOf(ext) - 1);
				fileName = fileName.replaceAll(":", "");
				fileName = fileName.replaceAll("\\\\", "_");
				fileName = fileName.replaceAll("/", "_");
				fileName = fileName.replaceAll("'", "_");
				String imagetype = "";
				if ("JPEG".equalsIgnoreCase(ext) || "TIF".equalsIgnoreCase(ext) || "TIFF".equalsIgnoreCase(ext)
						|| "JPG".equalsIgnoreCase(ext) || "png".equalsIgnoreCase(ext) || "pdf".equalsIgnoreCase(ext) || "bmp".equalsIgnoreCase(ext) || "BMP".equalsIgnoreCase(ext)) {
					imagetype = "I";
				} else {
					imagetype = "N";
				}

				if ("tif".equalsIgnoreCase(ext) || "tiff".equalsIgnoreCase(ext)) {
					numberOfPages = getNoOfPages(filepath, sessionId);
				}

				if ("pdf".equalsIgnoreCase(ext)) {

					imagetype = Character.toString(getPDFType(filepath, sessionId));
					numberOfPages = getNoOfPagesforPDF(filepath, sessionId);

				}

				isindex = addDocumentMT(filepath, sessionId);

				if (isindex.m_nDocIndex > 0 && isindex.m_sVolumeId > 0) {

					String inXml = "<?xml version=\"1.0\"?>" + "<NGOAddDocument_Input>"
							+ "<Option>NGOAddDocument</Option>" + "<CabinetName>" + Cabinet.getCabinetName()
							+ "</CabinetName>" + "<UserDBId>" + cabinet.getCabUserDbId() + "</UserDBId>"
							+ "<GroupIndex>0</GroupIndex>" + "<Document>" + "<ParentFolderIndex>" + folderIndex
							+ "</ParentFolderIndex>" + "<NoOfPages>" + numberOfPages + "</NoOfPages>"
							+ "<AccessType>I</AccessType>" + "<DocumentName>" + finoneDocId + "</DocumentName>"
							+ "<CreationDateTime></CreationDateTime>" + "<DocumentType>" + imagetype + "</DocumentType>"
							+ "<DocumentSize>" + f.length() + "</DocumentSize>" + "<CreatedByAppName>" + ext
							+ "</CreatedByAppName>" + "<ISIndex>" + isindex.m_nDocIndex + "#" + Cabinet.getVolumeId()
							+ "</ISIndex>" + "<ODMADocumentIndex></ODMADocumentIndex>" + "<Comment></Comment>"
							+ "<EnableLog>Y</EnableLog>" + "<FTSFlag>PP</FTSFlag>" + "<DataDefinition>"
							+ "<DataDefIndex>" + Cabinet.getDataDefId() + "</DataDefIndex>" + dataDefForDocument
							+ "</DataDefinition>" + "<Keywords></Keywords>" + "</Document>" + "<Comment>" + fileName
							+ "</Comment>" + "</NGOAddDocument_Input>";

					CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER + sessionId + " - " + inXml);
					String outXml = DMSCallBroker.execute(inXml, Cabinet.getJtsIp(),
							Integer.parseInt(Cabinet.getJtsPort()), Constants.UTF8ENCODING, 0, true);
					CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER + sessionId + " - " + outXml);

					xmlparser.setInputXML(outXml);
					String uploadStatus = xmlparser.getValueOf(Constants.STATUSSTRING);
					if ("0".equalsIgnoreCase(uploadStatus)) {
						status = Constants.SUCCESSSTATUSSTRING;
						StringBuilder messageBuilder=new StringBuilder("Document Added to Omnidocs succesfully!");
						String docIndex = xmlparser.getValueOf("DocumentIndex");
						String leadId = params.get("LeadId");

						if (new DBOperations().updateFinnOneStatus(docIndex, leadId, finoneDocId, sessionId)) {
							messageBuilder.append("\nStatus updated for document succesfully!");
						} else {
							messageBuilder.append("\nSome error while updating status.Please check logs!");
						}
						return new StatusBean(status, messageBuilder.toString());

					} else {

						status = Constants.FAILSTATUSSTRING;
						message = "Error while adding document";
						return new StatusBean(status, message);
					}

				} else {
					status = Constants.FAILSTATUSSTRING;
					message = "Error while adding document";
					return new StatusBean(status, message);
				}

			}

		} catch (JPISException e) {

			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "JPISException in uploadDoc : "
					+ Validations.stackTraceToString(e));

		} catch (Exception e) {

			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in uploadDoc : "
					+ Validations.stackTraceToString(e));

		}
		return new StatusBean(status, message);

	}

	private JPISIsIndex addDocumentMT(String filePath, String sessionId) throws JPISException {
		JPISIsIndex isIndex = new JPISIsIndex();
		try {
			JPDBRecoverDocData oRecoverDocData = new JPDBRecoverDocData();
			Short r = Short.valueOf("1");
			oRecoverDocData.m_sVolumeId = r;
			String strFilePath = filePath;
			File f1 = new File(strFilePath);
			oRecoverDocData.m_cDocumentType = 'N';
			oRecoverDocData.m_nDocumentSize = (int) f1.length();
			if (f1.exists()) {
				CustomLogger.printOut(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Calling AddDocument_MT");
				CPISDocumentTxn.AddDocument_MT(null, Cabinet.getJtsIp(), Short.parseShort(Cabinet.getJtsPort()),
						Cabinet.getCabinetName(), Short.parseShort(Cabinet.getVolumeId()), strFilePath, oRecoverDocData,
						"", isIndex);
				CustomLogger.printOut(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "AddDocument_MT Success");
			} else {
				isIndex.m_nDocIndex = -1;
			}
			return isIndex;
		} catch (JPISException jpisexception) {

			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in ADDDocument_MT : "
					+ Validations.stackTraceToString(jpisexception));
		}
		return isIndex;
	}

	private String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf('.') != -1 && fileName.lastIndexOf('.') != 0) {
			return fileName.substring(fileName.lastIndexOf('.') + 1);
		}

		else
			return "";
	}

	private String getFolderIndex(String folderpath, Cabinet cabinet, String sessionId) {
		String folderindex = "-1";
		String parentfolderindex = "0";
		try {
			String[] folders = folderpath.split("/");
			int i = 1;
			for (; i < folders.length; i++) {

				String inXML = XMLGen.searchFolderXml(Cabinet.getCabinetName(), cabinet.getCabUserDbId(),
						parentfolderindex, folders[i]);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER + sessionId + " - " + inXML);
				String outXml = DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()),
						Constants.UTF8ENCODING, 0, true);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER + sessionId + " - " + outXml);
				XMLParser searchfolderparse = new XMLParser();
				searchfolderparse.setInputXML(outXml);
				if (searchfolderparse.getValueOf(Constants.STATUSSTRING).equalsIgnoreCase("0")) {

					if (searchfolderparse.getValueOf("NoOfRecordsFetched").equalsIgnoreCase("0")
							|| searchfolderparse.getValueOf("Error").equalsIgnoreCase("No data found.")) {

						break;

					} else {

						NGXmlList folderList1 = searchfolderparse.createList("Folders", "Folder");
						if (folderList1.hasMoreElements()) {
							parentfolderindex = folderList1.getVal("folderIndex");
							folderList1.skip();
						}
					}
				} else {
					CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - "
							+ "Error in getFolderIndex while seraching folder");
					return "";
				}

			}
			while (i < folders.length) {
				String inXML = XMLGen.addFolderxml(Cabinet.getCabinetName(), cabinet.getCabUserDbId(),
						parentfolderindex, folders[i]);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER + sessionId + " - " + inXML);
				String outXml = DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()),
						Constants.UTF8ENCODING, 0, true);
				CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER + sessionId + " - " + outXml);
				XMLParser addfolderparse = new XMLParser();
				addfolderparse.setInputXML(outXml);
				if (addfolderparse.getValueOf(Constants.STATUSSTRING).equalsIgnoreCase("0")) {
					parentfolderindex = addfolderparse.getValueOf("folderIndex");
					i++;
				} else {
					CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - "
							+ "Error in getFolderIndex while adding folder");
					return "";
				}

			}
			folderindex = parentfolderindex;
		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in getFolderIndex : "
					+ Validations.stackTraceToString(e));
		}
		return folderindex;
	}

	private int getNoOfPages(String filepath, String sessionId) {
		int numberOfPages = 1;
		RandomAccessFileOrArray myTiffFile = null;
		try {
			RandomAccessSourceFactory randomAccessSourceFactory = new RandomAccessSourceFactory();
			myTiffFile = new RandomAccessFileOrArray(randomAccessSourceFactory.createBestSource(filepath));
			numberOfPages = TiffImage.getNumberOfPages(myTiffFile);
			myTiffFile.close();
		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - "
					+ "Error in read tiff in  getNoOfPages: " + Validations.stackTraceToString(e));
		} finally {
			try {
				if (myTiffFile != null) {
					myTiffFile.close();
				}
			} catch (Exception e) {
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - "
						+ "Error in read tiff in  getNoOfPages: " + Validations.stackTraceToString(e));
			}

		}
		return numberOfPages;
	}

	private int getNoOfPagesforPDF(String filepath, String sessionId) {
		int numberOfPages = 1;
		PdfReader reader = null;
		try {
			reader = new PdfReader(filepath);
			numberOfPages = reader.getNumberOfPages();
		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - "
					+ "Error in read tiff in  getNoOfPagesforPDF: " + Validations.stackTraceToString(e));
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return numberOfPages;
	}

	private char getPDFType(String filePath, String sessionId) {

		char chrDocumentType = 'N';
		if ("True".equalsIgnoreCase(Cabinet.getEworkstyleProps().getProperty("CheckPDF", "False"))) {
			try (FileInputStream fileInputStream = new FileInputStream(new File(filePath))) {
				int pdfForOpallStatus = Viewer.checkPDFSuport(fileInputStream);
				if (pdfForOpallStatus == 1) {
					chrDocumentType = 'I';
				} else if (pdfForOpallStatus > 1) {
					if ("Y".equalsIgnoreCase(Cabinet.getEworkstyleProps().getProperty("openpartialpdfinopall", "Y"))) {
						chrDocumentType = 'I';
					} else {
						chrDocumentType = 'N';
					}
				} else {
					chrDocumentType = 'N';
				}

			} catch (Exception ex) {
				CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in getPDFType: "
						+ Validations.stackTraceToString(ex));

			}
		}
		return chrDocumentType;
	}

}
