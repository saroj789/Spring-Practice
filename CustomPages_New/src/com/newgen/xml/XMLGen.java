package com.newgen.xml;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import com.newgen.bean.Cabinet;
import com.newgen.bean.Constants;
import com.newgen.business.Validations;
import com.newgen.logger.CustomLogger;

public class XMLGen {

	private XMLGen() {
	}

	public static String getODConnectInput(String cabinetName, String userName, String password) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar now = Calendar.getInstance();
		return "<? Xml version=\"1.0\"?>" + "<NGOConnectCabinet_Input>" + "<Option>NGOConnectCabinet</Option>"
				+ "<CabinetName>" + cabinetName + "</CabinetName>" + "<UserName>" + userName + "</UserName>"
				+ "<UserPassword>" + password + "</UserPassword>" + "<CurrentDateTime>"
				+ formatter.format(now.getTime()) + "</CurrentDateTime>" + "<UserExist>N</UserExist>"
				+ "<MainGroupIndex></MainGroupIndex>" + "<UserType>S</UserType>" + "</NGOConnectCabinet_Input>";

	}

	public static String getODDisconnectInput(String cabinetName, String sessionID) {
		return "<?xml version=\"1.0\"?>" + "<NGODisconnectCabinet_Input>" + "<Option>NGODisconnectCabinet</Option>"
				+ "<CabinetName>" + cabinetName + "</CabinetName>" + "<UserDBId>" + sessionID + "</UserDBId>"
				+ "</NGODisconnectCabinet_Input>";
	}

	public static String getDataclassidxml(String cabinetName, String cabUserDbId, String dataclassname) {
		return "<?xml version=\"1.0\"?>" + "<NGOGetDataDefIdForName_Input>" + "<Option>NGOGetDataDefIdForName</Option>"
				+ "<CabinetName>" + cabinetName + "</CabinetName>" + "<UserDBId>" + cabUserDbId + "</UserDBId>"
				+ "<DataDefName>" + dataclassname + "</DataDefName>" + "</NGOGetDataDefIdForName_Input>";
	}

	public static String getDataDefinition(String cabinetname, String userdbid, String dataclassid) {

		return "<?xml version=\"1.0\"?>\r\n" + "<NGOGetDataDefProperty_Input>\r\n"
				+ "<Option>NGOGetDataDefProperty</Option>\r\n" + "<CabinetName>" + cabinetname + "</CabinetName>\r\n"
				+ "<UserDBId>" + userdbid + "</UserDBId>\r\n" + "<DataDefIndex>" + dataclassid + "</DataDefIndex>\r\n"
				+ "</NGOGetDataDefProperty_Input>";
	}

	public static String getDocument(String cabinetname, String userdbid, String docindex, String dataAlsoFlag) {
		return "<?xml version=\"1.0\"?>\r\n" + "<NGOGetDocumentProperty_Input>\r\n"
				+ "<Option>NGOGetDocumentProperty</Option>\r\n" + "<CabinetName>" + cabinetname + "</CabinetName>\r\n"
				+ "<UserDBId>" + userdbid + "</UserDBId>\r\n" + "<DocumentIndex>" + docindex + "</DocumentIndex>\r\n"
				+ "<VersionNo></VersionNo>\r\n" + "<DataAlsoFlag>" + dataAlsoFlag + "</DataAlsoFlag>\r\n"
				+ "</NGOGetDocumentProperty_Input>";
	}

	public static String getSeatchDocwithGIXML(String cabinetName, String cabUserDbId, String globalindexId,
			String docId) {
		return "<?xml version=\"1.0\"?><NGOSearchDocumentExt_Input>" + "<Option>NGOSearchDocumentExt</Option>"
				+ "<CabinetName>" + cabinetName + "</CabinetName>" + "<UserDBId>" + cabUserDbId + "</UserDBId>"
				+ "<SearchText></SearchText><Rank>Y</Rank><SearchOnPreviousVersions>N</SearchOnPreviousVersions>"
				+ "<LookInFolder>0</LookInFolder><IncludeSubFolder>Y</IncludeSubFolder><Name></Name>"
				+ "<Owner></Owner>"
				+ "<CreationDateRange></CreationDateRange><ExpiryDateRange></ExpiryDateRange><AccessedDateRange></AccessedDateRange><RevisedDateRange></RevisedDateRange>"
				+ "<DataDefCriterion></DataDefCriterion><SearchScope>0</SearchScope><PreviousList></PreviousList><SearchOnAlias>N</SearchOnAlias>"
				+ "<Keywords></Keywords>" + "<GlobalIndexCriterion><GlobalIndexCriteria>"
				+ "<IndexType>S</IndexType><IndexId>" + globalindexId + "</IndexId><Operator>=</Operator><IndexValue>"
				+ docId + "</IndexValue>" + "<JoinCondition></JoinCondition>"
				+ "</GlobalIndexCriteria></GlobalIndexCriterion>"
				+ "<ReferenceFlag>B</ReferenceFlag><SortOrder>A</SortOrder><GroupIndex>0</GroupIndex>"
				+ "<StartFrom>1</StartFrom>"
				+ "<NoOfRecordsToFetch>10</NoOfRecordsToFetch><OrderBy>2</OrderBy><CheckOutStatus></CheckOutStatus>"
				+ "<MaximumHitCountFlag>N</MaximumHitCountFlag><CheckOutByUser></CheckOutByUser>"
				+ "<ObjectTypes><ObjectType>1</ObjectType><ObjectType>2</ObjectType><ObjectType>8</ObjectType><ObjectType>11</ObjectType><ObjectType>13</ObjectType><ObjectType>14</ObjectType><ObjectType>15</ObjectType><ObjectType>16</ObjectType><ObjectType>17</ObjectType><ObjectType>20</ObjectType></ObjectTypes>"
				+ "<DataAlsoFlag>Y</DataAlsoFlag><CreatedByAppName></CreatedByAppName><Author></Author><AnnotationFlag>Y</AnnotationFlag><LinkDocFlag>Y</LinkDocFlag><PrevDocIndex>0</PrevDocIndex><IncludeSystemFolder>NN</IncludeSystemFolder><IncludeTrashFlag>N</IncludeTrashFlag><ThesaurusFlag>N</ThesaurusFlag><RecordAlsoFlag>N</RecordAlsoFlag></NGOSearchDocumentExt_Input>";
	}

	public static String getchangeDocPropertyXML(String cabinetName, String cabUserDbId, String docId, String dtName, Map<String, String> params) {
		StringBuilder dataDefForDocument = new StringBuilder();
		CustomLogger.writeXML("getchangeDocPropertyXML getAllDataClassFields====>"+Cabinet.getAllDataClassFields());
		String[] dataclassFieldsDocument = Cabinet.getAllDataClassFields().split(",");
		CustomLogger.writeXML("getchangeDocPropertyXML Map<String, String> params====>"+params.toString());
		CustomLogger.writeXML("getchangeDocPropertyXML dataclassFieldsDocument====>"+dataclassFieldsDocument[0] + " -" + dataclassFieldsDocument[1]);
		for (int i = 0; i < dataclassFieldsDocument.length; i++) {
			String[] fields = dataclassFieldsDocument[i].split("#");
			/*CustomLogger.writeXML("getchangeDocPropertyXML fields====>"+fields[i] + "i" +i);
			CustomLogger.writeXML("getchangeDocPropertyXML fields[0]====>"+fields[0]);
			CustomLogger.writeXML("getchangeDocPropertyXML params.get(fields[1])====>"+params.get(fields[1]));
			CustomLogger.writeXML("getchangeDocPropertyXML fields[2]====>"+fields[2]);*/
			//if (params.containsKey(fields[1]) && !Validations.isEmptyOrNullValue(params.get(fields[1]))) {
				dataDefForDocument.append("<Field>" + "<IndexId>" + fields[0] + "</IndexId>" 
						+ "<IndexType>" + fields[2] + "</IndexType>"
						+ "<IndexValue>" + params.get(fields[1]) + "</IndexValue>" 
						+ "</Field>");
			//}
		}
		CustomLogger.writeXML("getchangeDocPropertyXML dataDefForDocument====>"+dataDefForDocument);
		
		return "<?xml version=\"1.0\"?><NGOChangeDocumentProperty_Input>\r\n"
				+ "<Option>NGOChangeDocumentProperty</Option>\r\n" + "<CabinetName>" + cabinetName
				+ "</CabinetName>\r\n" + "<UserDBId>" + cabUserDbId + "</UserDBId>\r\n"
				+ "<GroupIndex>0</GroupIndex>\r\n" + "<Document><DocumentIndex>" + docId
				+ "</DocumentIndex><Owner></Owner>\r\n" + "<VersionFlag></VersionFlag>\r\n"
				+ "<DataDefinition>\r\n" + "<DataDefName>" + dtName + "</DataDefName>\r\n" 
				+ "<DataDefIndex>" + Cabinet.getDataDefId() + "</DataDefIndex>\r\n" 
				+ "<Fields>\r\n"
				+ dataDefForDocument + "</Fields>\r\n" + "</DataDefinition>\r\n"
				+ "<OwnerIndex></OwnerIndex><OwnerType>U</OwnerType>\r\n" + "<Comment></Comment>"
				+ "</Document></NGOChangeDocumentProperty_Input>";
	}

	public static String getDeletedocumentXml(String cabinetname, String userdbid, String docindex,
			String parentFolderindex) {
		return "<?xml version=\"1.0\"?>\r\n" + "<NGODeleteDocumentExt_Input>\r\n"
				+ "<Option>NGODeleteDocumentExt</Option>\r\n" + "<CabinetName>" + cabinetname + "</CabinetName>\r\n"
				+ "<UserDBId>" + userdbid + "</UserDBId>\r\n" + "<Documents>\r\n" + "<Document>\r\n" + "<DocumentIndex>"
				+ docindex + "</DocumentIndex>\r\n" + "<ParentFolderIndex>" + parentFolderindex
				+ "</ParentFolderIndex> \r\n" + "<ReferenceFlag>N</ReferenceFlag>" + "</Document>\r\n"
				+ "</Documents>\r\n" + "<Comment>Deleted</Comment>" + "</NGODeleteDocumentExt_Input>";
	}

	public static String addFolderxml(String cabinetName, String cabUserDbId, String parentfolderindex,
			String foldername) {
		return "<?xmlversion=\"1.0\"?>" + "<NGOAddFolder_Input>" + "<Option>NGOAddFolder</Option>" + "<CabinetName>"
				+ cabinetName + "</CabinetName>" + "<UserDBId>" + cabUserDbId + "</UserDBId>" + "<Folder>"
				+ "<ParentFolderIndex>" + parentfolderindex + "</ParentFolderIndex>" + "<FolderName>" + foldername
				+ "</FolderName>" + "<CreationDateTime></CreationDateTime>" + "<AccessType>S</AccessType>"
				+ "<ImageVolumeIndex></ImageVolumeIndex>" + "<FolderType>G</FolderType>" + "<Location>G</Location>"
				+ "<Comment></Comment>" + "<Owner></Owner>" + "<LogGeneration>Y</LogGeneration>"
				+ "<EnableFtsFlag>Y</EnableFtsFlag>" + "<DuplicateName>Y</DuplicateName>" + "</Folder>"
				+ "</NGOAddFolder_Input>";
	}

	public static String searchDocumentXML(String cabinetName, String cabUserDbId, String documentName,
			Map<String, String> params) {
		StringBuilder dataDefForDocument = new StringBuilder();
		String[] dataclassFieldsDocument = Cabinet.getDataClassFields().split(",");
		for (int i = 0; i < dataclassFieldsDocument.length; i++) {
			String[] fields = dataclassFieldsDocument[i].split("#");
			if (params.containsKey(fields[1]) && !Validations.isEmptyOrNullValue(params.get(fields[1]))) {
				dataDefForDocument.append("<DataDefCriteria>" + "<DataDefIndex>" + Cabinet.getDataDefId()
						+ "</DataDefIndex>" + "<IndexId>" + fields[0] + "</IndexId>" + "<Operator>=</Operator>"
						+ "<FieldSecureFlag>N</FieldSecureFlag>" + "<IndexValue>" + params.get(fields[1])
						+ "</IndexValue>" + "<IndexType>" + fields[2] + "</IndexType>"
						+ "<JoinCondition>AND</JoinCondition>" + "</DataDefCriteria>");
			}
		}

		return "?xml version=\"1.0\"?>" + "<NGOSearchDocumentExt_Input>" + "<Option>NGOSearchDocumentExt</Option>"
				+ "<CabinetName>" + cabinetName + "</CabinetName>" + "<UserDBId>" + cabUserDbId + "</UserDBId>"
				+ "<SearchText></SearchText>" + "<Rank>Y</Rank>"
				+ "<SearchOnPreviousVersions>N</SearchOnPreviousVersions>" + "<LookInFolder>0</LookInFolder>"
				+ "<IncludeSubFolder>Y</IncludeSubFolder>" + "<Name>" + documentName + "</Name>" + "<Owner></Owner>"
				+ "<CreationDateRange></CreationDateRange>" + "<ExpiryDateRange></ExpiryDateRange>"
				+ "<AccessedDateRange></AccessedDateRange>" + "<RevisedDateRange></RevisedDateRange>"
				+ "<DataDefCriterion>" + "<DataDefIndex>" + Cabinet.getDataDefId() + "</DataDefIndex>"
				+ dataDefForDocument.toString() + "</DataDefCriterion>" + "<SearchScope>0</SearchScope>"
				+ "<PreviousList></PreviousList>" + "<SearchOnAlias>N</SearchOnAlias>" + "<Keywords></Keywords>"
				+ "<GlobalIndexCriterion></GlobalIndexCriterion>" + "<ReferenceFlag>B</ReferenceFlag>"
				+ "<SortOrder>D</SortOrder>" + "<GroupIndex>0</GroupIndex>" + "<StartFrom>1</StartFrom>"
				+ "<NoOfRecordsToFetch></NoOfRecordsToFetch>" + "<OrderBy>4</OrderBy>"
				+ "<CheckOutStatus></CheckOutStatus>" + "<MaximumHitCountFlag>Y</MaximumHitCountFlag>"
				+ "<CheckOutByUser></CheckOutByUser>" + "<ObjectTypes><ObjectType>1</ObjectType>"
				+ "<ObjectType>2</ObjectType>" + "<ObjectType>8</ObjectType>" + "<ObjectType>11</ObjectType>"
				+ "<ObjectType>13</ObjectType>" + "<ObjectType>14</ObjectType>" + "<ObjectType>15</ObjectType>"
				+ "<ObjectType>16</ObjectType>" + "<ObjectType>17</ObjectType>"
				+ "</ObjectTypes><DataAlsoFlag>Y</DataAlsoFlag>" + "<CreatedByAppName></CreatedByAppName>"
				+ "<Author></Author>" + "<AnnotationFlag>Y</AnnotationFlag>" + "<LinkDocFlag>Y</LinkDocFlag>"
				+ "<PrevDocIndex>0</PrevDocIndex>" + "<IncludeSystemFolder>YY</IncludeSystemFolder>"
				+ "<ReportFlag>N</ReportFlag>" + "<ThesaurusFlag>N</ThesaurusFlag>" + "</NGOSearchDocumentExt_Input>";
	}

	public static String searchFolderXml(String cabinetName, String cabUserDbId, String parentfolderindex,
			String folderName) {
		return "<?xml version=\"1.0\"?>" + "<NGOSearchFolder_Input>" + "<Option>NGOSearchFolder</Option>"
				+ "<CabinetName>" + cabinetName + "</CabinetName>" + "<UserDBId>" + cabUserDbId + "</UserDBId>"
				+ "<LookInFolder>" + parentfolderindex + "</LookInFolder>" + "<IncludeSubFolder>N</IncludeSubFolder>"
				+ "<Name>" + folderName + "</Name>" + "<SearchScope>0</SearchScope>" + "<StartFrom>1</StartFrom>"
				+ "<NoOfRecordsToFetch>500</NoOfRecordsToFetch>" + "<MaximumHitCountFlag>Y</MaximumHitCountFlag>"
				+ "<FolderType></FolderType>" + "<ShowPath>Y</ShowPath>" + "<ReportFlag></ReportFlag>"
				+ "</NGOSearchFolder_Input>";
	}

	public static String searchFolderXml(String cabinetName, String cabUserDbId, String folderName) {
		return "?xml version=\"1.0\"?>" + "<NGOSearchFolder_Input>" + "<Option>NGOSearchFolder</Option>"
				+ "<CabinetName>" + cabinetName + "</CabinetName>" + "<UserDBId>" + cabUserDbId + "</UserDBId>"
				+ "<LookInFolder>0</LookInFolder>" + "<IncludeSubFolder>Y</IncludeSubFolder>" + "<Name>" + folderName
				+ "</Name>" + "<Owner></Owner>" + "<CreationDateRange></CreationDateRange>"
				+ "<ExpiryDateRange></ExpiryDateRange>" + "<AccessDateRange></AccessDateRange>"
				+ "<RevisedDateRange></RevisedDateRange>"
				+ "<DataDefinitions><DataDefIndex></DataDefIndex></DataDefinitions>" + "<SearchScope>0</SearchScope>"
				+ "<PrevFolderList></PrevFolderList>" + "<ReferenceFlag>O</ReferenceFlag>" + "<StartFrom>1</StartFrom>"
				+ "<NoOfRecordsToFetch>10</NoOfRecordsToFetch>" + "<OrderBy>2</OrderBy>" + "<SortOrder>A</SortOrder>"
				+ "<MaximumHitCountFlag>Y</MaximumHitCountFlag>" + "<FolderType>G</FolderType>"
				+ "<IncludeTrashFlag>N</IncludeTrashFlag>" + "<ReportFlag>N</ReportFlag>" + "<ShowPath>Y</ShowPath>"
				+ "</NGOSearchFolder_Input>";
	}
	
	

	public static String getDocumentList(String cabinetName, String cabUserDbId, String folderIndex) {
		return "?xml version=\"1.0\"?>\n" + "<NGOGetDocumentListExt_Input>\n"
				+ "<Option>NGOGetDocumentListExt</Option>\n" + "<CabinetName>" + cabinetName + "</CabinetName>\n"
				+ "<UserDBId>" + cabUserDbId + "</UserDBId>\n" + "<CurrentDateTime></CurrentDateTime>\n"
				+ "<FolderIndex>" + folderIndex + "</FolderIndex>\n" + "<DocumentIndex></DocumentIndex>\n"
				+ "<PreviousIndex>0</PreviousIndex>\n" + "<LastSortField></LastSortField>\n"
				+ "<StartPos>0</StartPos>\n" + "<NoOfRecordsToFetch>100</NoOfRecordsToFetch>\n"
				+ "<OrderBy>5</OrderBy>\n" + "<SortOrder>D</SortOrder>\n" + "<DataAlsoFlag>N</DataAlsoFlag>\n"
				+ "<AnnotationFlag>Y</AnnotationFlag>\n" + "<LinkDocFlag>Y</LinkDocFlag>\n"
				+ "<PreviousRefIndex>0</PreviousRefIndex>\n" + "<LastRefField></LastRefField>\n"
				+ "<RefOrderBy>2</RefOrderBy>\n" + "<RefSortOrder>A</RefSortOrder>\n"
				+ "<NoOfReferenceToFetch>10</NoOfReferenceToFetch>\n" + "<DocumentType></DocumentType>\n"
				+ "<RecursiveFlag>N</RecursiveFlag>\n" + "<ThumbnailAlsoFlag>N</ThumbnailAlsoFlag>\n"
				+ "</NGOGetDocumentListExt_Input>\n";
	}

	public static String fetchAuditLogXML(String cabinetname, String userdbid, String docindex) {
		return "<?xml version=\"1.0\"?>" + "<NGOFetchAuditTrail_Input>" + "<Option>NGOFetchAuditTrail</Option>"
				+ "<CabinetName>" + cabinetname + "</CabinetName>" + "<UserDBId>" + userdbid + "</UserDBId>"
				+ "<Category>D</Category>" + "<ObjectIndex>" + docindex + "</ObjectIndex>"
				+ "<ObjectType>D</ObjectType>" + "<AuditLogIndex>0</AuditLogIndex>"
				+ "<NoOfRecordsToFetch>100</NoOfRecordsToFetch>" + "<SortOrder>A</SortOrder>"
				+ "</NGOFetchAuditTrail_Input>";

	}

	public static String fetchAuditLogXML(String cabinetname, String userdbid, String docindex, String startDate,
			String endDate) {
		return "<?xml version=\"1.0\"?>" + "<NGOFetchAuditTrail_Input>" + "<Option>NGOFetchAuditTrail</Option>"
				+ "<CabinetName>" + cabinetname + "</CabinetName>" + "<UserDBId>" + userdbid + "</UserDBId>"
				+ "<Category>D</Category>" + "<ObjectIndex>" + docindex + "</ObjectIndex>"
				+ "<ObjectType>D</ObjectType>" + "<AuditLogIndex>0</AuditLogIndex>" + "<DateFrom>" + startDate
				+ "</DateFrom>" + "<DateTo>" + endDate + "</DateTo>" + "<NoOfRecordsToFetch>100</NoOfRecordsToFetch>"
				+ "<SortOrder>A</SortOrder>" + "</NGOFetchAuditTrail_Input>";

	}

	public static String getODConnectInput(String cabinetName, String userName) {

		return "<? Xml version=\"1.0\"?>" + "<NGOConnectCabinet_Input><Option>NGOConnectCabinet</Option>"
				+ "<CabinetName>" + cabinetName + "</CabinetName>" + "<UserName>" + userName + "</UserName>"
				+ "<UserPassword>" + "OmniDocs_Authentication_Manager_0123456789_!@#$%&*</UserPassword>" +
				// "<UserPassword>Password0</UserPassword>"+
				"<Hook>No</Hook>" + "<UserExist>N</UserExist>" + "<CurrentDateTime></CurrentDateTime>"
				+ "<UserType>U</UserType>" + "<Locale></Locale>" + "</NGOConnectCabinet_Input>";
	}
	
	public static String searchDocumentXML_New(String cabinetName, String cabUserDbId, Map<String, String> params) {
		StringBuilder dataDefForDocument = new StringBuilder();
		String[] dataclassFieldsDocument = Cabinet.getDataClassFields().split(",");
		CustomLogger.writeXML("Map<String, String> params====>"+params.toString());
		CustomLogger.writeXML("dataclassFieldsDocument====>"+dataclassFieldsDocument[0] + " -" + dataclassFieldsDocument[1]);
		for (int i = 0; i < dataclassFieldsDocument.length; i++) {
			String[] fields = dataclassFieldsDocument[i].split("#");
			CustomLogger.writeXML("fields====>"+fields[i]);
			CustomLogger.writeXML("fields[0]====>"+fields[0]);
			CustomLogger.writeXML("params.get(fields[1])====>"+params.get(fields[1]));
			CustomLogger.writeXML("fields[2]====>"+fields[2]);
			if (params.containsKey(fields[1]) && !Validations.isEmptyOrNullValue(params.get(fields[1]))) {
				dataDefForDocument.append("<DataDefCriteria>" + "<DataDefIndex>" + Cabinet.getDataDefId()
						+ "</DataDefIndex>" + "<IndexId>" + fields[0] + "</IndexId>" + "<Operator>=</Operator>"
						+ "<FieldSecureFlag>N</FieldSecureFlag>" + "<IndexValue>" + params.get(fields[1])
						+ "</IndexValue>" + "<IndexType>" + fields[2] + "</IndexType>"
						+ "<JoinCondition>AND</JoinCondition>" + "</DataDefCriteria>");
			}
		}
		CustomLogger.writeXML("dataDefForDocument====>"+dataDefForDocument);
		
		return "?xml version=\"1.0\"?>" + "<NGOSearchDocumentExt_Input>" + "<Option>NGOSearchDocumentExt</Option>"
				+ "<CabinetName>" + cabinetName + "</CabinetName>" + "<UserDBId>" + cabUserDbId + "</UserDBId>"
				+ "<SearchText></SearchText>" + "<Rank>N</Rank>"
				+ "<SearchOnPreviousVersions>N</SearchOnPreviousVersions>" + "<LookInFolder>0</LookInFolder>"
				+ "<IncludeSubFolder>Y</IncludeSubFolder>" + "<Name></Name>" + "<Owner></Owner>"
				+ "<CreationDateRange></CreationDateRange>" + "<ExpiryDateRange></ExpiryDateRange>"
				+ "<AccessedDateRange></AccessedDateRange>" + "<RevisedDateRange></RevisedDateRange>"
				+ "<DataDefCriterion>" + "<DataDefIndex>" + Cabinet.getDataDefId() + "</DataDefIndex>"
				+ dataDefForDocument.toString() + "</DataDefCriterion>" + "<SearchScope>0</SearchScope>"
				+ "<PreviousList></PreviousList>" + "<SearchOnAlias>N</SearchOnAlias>" + "<Keywords></Keywords>"
				+ "<GlobalIndexCriterion></GlobalIndexCriterion>" + "<ReferenceFlag>O</ReferenceFlag>"
				+ "<SortOrder>A</SortOrder>" + "<GroupIndex>0</GroupIndex>" + "<StartFrom>1</StartFrom>"
				+ "<NoOfRecordsToFetch></NoOfRecordsToFetch>" + "<OrderBy>9</OrderBy>"
				+ "<CheckOutStatus></CheckOutStatus>" + "<MaximumHitCountFlag>N</MaximumHitCountFlag>"
				+ "<CheckOutByUser></CheckOutByUser>" + "<DataAlsoFlag>Y</DataAlsoFlag>" + "<CreatedByAppName></CreatedByAppName>"
				+ "<Author></Author>" + "<AnnotationFlag>Y</AnnotationFlag>" + "<LinkDocFlag>N</LinkDocFlag>"
				+ "<PrevDocIndex>0</PrevDocIndex>" + "<IncludeSystemFolder>NY</IncludeSystemFolder>"
				+ "<ReportFlag>N</ReportFlag>" + "<ThesaurusFlag>N</ThesaurusFlag>" + "</NGOSearchDocumentExt_Input>";
	}
	
	public static String searchFolderXml_New(String cabinetName, String cabUserDbId, String Field1, String Field2) {
		return "?xml version=\"1.0\"?>" + "<NGOSearchFolder_Input>" + "<Option>NGOSearchFolder</Option>"
				+ "<CabinetName>" + cabinetName + "</CabinetName>" + "<UserDBId>" + cabUserDbId + "</UserDBId>"
				+ "<LookInFolder>0</LookInFolder>" + "<IncludeSubFolder>Y</IncludeSubFolder>" + "<Name>" + Field1
				+ "</Name>" + "<Owner></Owner>" + "<CreationDateRange></CreationDateRange>"
				+ "<ExpiryDateRange></ExpiryDateRange>" + "<AccessDateRange></AccessDateRange>"
				+ "<RevisedDateRange></RevisedDateRange>"
				+ "<DataDefinitions><DataDefIndex></DataDefIndex></DataDefinitions>" + "<SearchScope>0</SearchScope>"
				+ "<PrevFolderList></PrevFolderList>" + "<ReferenceFlag>O</ReferenceFlag>" + "<StartFrom>1</StartFrom>"
				+ "<NoOfRecordsToFetch>10</NoOfRecordsToFetch>" + "<OrderBy>2</OrderBy>" + "<SortOrder>A</SortOrder>"
				+ "<MaximumHitCountFlag>Y</MaximumHitCountFlag>" + "<FolderType>G</FolderType>"
				+ "<IncludeTrashFlag>N</IncludeTrashFlag>" + "<ReportFlag>N</ReportFlag>" + "<ShowPath>Y</ShowPath>"
				+ "</NGOSearchFolder_Input>";
	}
	
	public static String NGOSelect(String strEngineName, String strSessionId, String strTableName, String ColNames, String strWhereClause) {
        return "<?xml version=\"1.0\"?>\n<NGOUserSQL_Input>\n<Option>NGOUserSQL</Option>\n<CabinetName>" + strEngineName + "</CabinetName>"
        		+ "\n" + "<UserDBId>" + strSessionId + "</UserDBId>\n" + "<Operation>S</Operation>\n" + "<TableName>" + strTableName + "</TableName>"
        		+ "\n" + "<ColumnList>" + ColNames + "</ColumnList>\n" + "<WhereCriterion>\n" + strWhereClause + "\n" + "</WhereCriterion>\n" + "</NGOUserSQL_Input>";
    }
	
	public static String NGOGetUserProperty(String strEngineName, String strSessionId,String userIndex) {
		return "<?xml version=\"1.0\"?><NGOGetUserProperty_Input><Option>NGOGetUserProperty</Option><CabinetName>" + strEngineName + "</CabinetName>"
				+ "<UserDBId>" + strSessionId + "</UserDBId><UserIndex>" + userIndex + "</UserIndex><UserPrefernces>Y</UserPrefernces></NGOGetUserProperty_Input>";
	}
	
	public static String NGOUserSelectEx(String strEngineName, String strSessionId, String UserName) {
        return "<?xml version=\"1.0\"?>\n<NGOUserSQLEx_Input>\n<Option>NGOUserSQLEx</Option>\n<CabinetName>" + strEngineName + "</CabinetName>"
        		+ "\n" + "<UserDBId>" + strSessionId + "</UserDBId>\n" + "<Operation>S</Operation>\n" 
				+ "<Query>SELECT BR_CO,BR_NAME FROM OfficeBranchMaster where ZONE_NO in (Select ZONE_NO from UserOfficeMaster where USERNAME='"+UserName+"')</Query>"
				+ "</NGOUserSQLEx_Input>";
    }	
	

}
