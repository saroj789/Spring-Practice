package com.newgen.business;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.newgen.bean.Cabinet;
import com.newgen.bean.Constants;
import com.newgen.bean.DownloadDocBean;
import com.newgen.dmsapi.DMSCallBroker;
import com.newgen.logger.CustomLogger;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.xml.XMLGen;

import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPISException;
import Jdts.DataObject.JPDBString;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class DownloadDocHelper {
		
	public DownloadDocBean downloadDoc(String docId,String userName, String userdbid, String sessionId) {
		XMLParser parser = new XMLParser();
		DownloadDocBean downdoc=new DownloadDocBean();	
		try {
			String dataAlsoFlag="N";
			String inXML = XMLGen.getDocument(Cabinet.getCabinetName(), userdbid, docId,dataAlsoFlag);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+inXML);
			String outXml = DMSCallBroker.execute(inXML, Cabinet.getJtsIp(), Integer.parseInt(Cabinet.getJtsPort()),Constants.UTF8ENCODING, 0, true);
			CustomLogger.writeXML(Constants.SESSIONIDFORLOGGER+sessionId+" - "+outXml);
			parser.setInputXML(outXml);
			if ("0".equalsIgnoreCase(parser.getValueOf(Constants.STATUSSTRING))) {

				String isIndex = parser.getValueOf("ISIndex");
				String docName = parser.getValueOf("DocumentName");
				String docExtension = parser.getValueOf("CreatedByAppName");
				String createdDateTime=parser.getValueOf("CreationDateTime");
				createdDateTime=createdDateTime.replaceAll(":", "-");
				createdDateTime=createdDateTime.replaceAll(" ", "-");
				
				//////////////////////////////////////////////////////////////////////////////
				String fix=createdDateTime;
				StringBuilder str=new StringBuilder();
				str.append(fix);
				String year=str.substring(0, 4);
				String month=str.substring(5, 7);
				String day=str.substring(8, 10);

				DateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
				DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
				DateFormat dateFormat3 = new SimpleDateFormat("yyyy/MM/dd");

				Calendar fix3 = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));
				Calendar MaxDate = Calendar.getInstance();
				Calendar Minmonth = Calendar.getInstance();

			   Minmonth.add(Calendar.MONTH, -1);
			   Minmonth.set(Calendar.DATE, 1);
			   boolean b=false;
			   boolean b2=false;
			   String strDate = dateFormat3.format(fix3.getTime()); 
			   String strDate2 = dateFormat3.format(Minmonth.getTime()); 
			   System.out.println(strDate+" this "+strDate2);
			   downdoc.setDateBetween(strDate2+" and "+strDate);
				CustomLogger.printOut(strDate+" this "+strDate2);

             
			   
			 if((fix3.getTime().after(Minmonth.getTime()) && fix3.getTime().before(MaxDate.getTime())) || strDate.equals(strDate2)) {
				 
			 }else {
                if(docExtension.equals("pdf")||docExtension.equals("png")||docExtension.equals("jpg")||docExtension.equals("jpeg")||docExtension.equals("tiff")|| docExtension.equals("tif")|| docExtension.equals("bmp")|| docExtension=="pdf"||docExtension=="png"||docExtension=="jpg"||docExtension=="jpeg"||docExtension=="tiff" || docExtension=="tif"|| docExtension=="bmp"|| docExtension.equals("PDF")||docExtension.equals("PNG")||docExtension.equals("JPG")||docExtension.equals("JPEG")||docExtension.equals("TIFF")||docExtension=="PDF"||docExtension=="PNG"||docExtension=="JPG"||docExtension=="JPEG"||docExtension=="TIFF") {
                	downdoc.setStatus("Fail");
                	return downdoc;
                }
			 }
			   
			   
			   ///////////////////////////////////////////////////////////////////////////////////
			   
				String[] doc = isIndex.split("#");
				String strVolId = doc[1];
				if ("-1".equalsIgnoreCase(strVolId)) {
					downdoc.setStatus("Fail");
				}else {
					downdoc = downloadDocumentToFile(isIndex, docName,Cabinet.getSiteId(),userName,sessionId);
					if("Fail".equalsIgnoreCase(downdoc.getStatus())) {
						downdoc.setStatus("Fail");
						return downdoc;
					}else {
						downdoc.setDocId(docId);
						downdoc.setDocname(docName+"_"+createdDateTime+"."+docExtension);
						return downdoc;
					}
				}
				
			}	
				
		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in Download Document : "+Validations.stackTraceToString(e));
		}
		return downdoc;
	}

	

	
	public DownloadDocBean downloadDocumentToFile(String isIndex,
			String originalFileName, String siteId, String userName, String sessionId){

		DownloadDocBean downdoc2=new DownloadDocBean();
		downdoc2.setStatus("Fail");

		try {
			JPDBString oSiteName = new JPDBString();
			ByteArrayOutputStream out=new ByteArrayOutputStream();

			if (!Validations.isEmpty(isIndex)) {
				String[] doc = isIndex.split("#");
				int iSDocIndex = Integer.parseInt(doc[0]);
				String strVolId = doc[1];
				CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"[DocDownloader.downloadDocumentToFile] Downloading documents with docIndex : " + iSDocIndex + " docName : " + originalFileName + " site:"+ siteId + " VolumeId :" + strVolId);
				CPISDocumentTxn.GetDocInFile_MT(null, Cabinet.getJtsIp(),
						Short.parseShort(Cabinet.getJtsPort()), Cabinet.getCabinetName(),
						Short.parseShort(siteId), Short.parseShort(strVolId), iSDocIndex, userName, out,
						oSiteName);
				downdoc2.setStatus("Success");
				downdoc2.setOutstream(out);
				CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"[DocDownloader.downloadDocumentToFile] Document Downloaded Successfully ");
					
					

			}
			

		} 
		catch (Exception | JPISException ex) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"[DocDownloader.downloadDocumentToFile] Exception While downloading Document with docIndex : " + isIndex + " " + Validations.stackTraceToString(ex));
			downdoc2.setStatus("Fail");
			downdoc2.setErrMsg(ex.getMessage());
		}
		return downdoc2;
	}

}
