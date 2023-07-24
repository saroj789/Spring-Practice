<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="X-UA-Compatible" content="IE=11; charset=ISO-8859-1">
<title>Finnone View Document</title>
<link href="styles/docstyle.css" rel="stylesheet" type="text/css">
<script src="scripts/jquery3.min.js"></script>
<script type="text/javascript" src="scripts/CommonFunctions.js"></script>
<script type="text/javascript" src="scripts/ViewFunctions.js"></script>
</head>
<body onload="onload();" onunload="FunClose();">
	<c:if test="${not empty ErrorMessage}">
		<div style="margin-left: 10%">
			<br>
			<br> <span style="text-align: center;">Below Error found
				in input -- </span> <br>
			<br> <span style="color: red; text-align: center;">${ErrorMessage}</span>
		</div>
	</c:if>
	<c:if test="${empty ErrorMessage}">
	<form name="FinnoneViewDoc" id="FinnoneViewDoc" action="" method="post">
		<fieldset>
			<legend></legend>
			<table width="512" align="center" border="0">
				<tr height=30>
					<td width="100%" align="Center" class="EWSubHeaderNum"><p
							align="Center">
							<b>Finnone-OmniDocs: View Document</b></td>
				</tr>
			</table>
			<hr size="1%" width="512">
			<table width="512" border="1" align="center">
				<tr>
					<td>
						<table width=100% border="0" align="center">
							<tr height=20>
								<td width="100%" class="EWSubHeaderText"><p align="Center">Finnone
										Lead Details</td>
							</tr>
						</table>

						<table width=100% border="0" align="center">
							<tr height=30>
								<td width=200 class="EWSubHeaderText">UserName</td>
								<td width=200 class="EWTableContents"><input
									name="UserName" id="UserNameId" readonly="readonly"
									value="${param.lstr_param1
									}"></td>
							</tr>
							<tr height=30>
								<td width=200 class="EWSubHeaderText">Lead ID</td>
								<td width=200 class="EWTableContents"><input name="LeadId"
									id="LeadIdId" readonly="readonly" value="${param.lstr_param3}"></td>
							</tr>
							<tr height=30>
								<td width=200 class="EWSubHeaderText">Finnone DocID</td>
								<td width=200 class="EWTableContents"><input
									name="FinoneDocId" id="FinoneDocIdId" readonly="readonly"
									value="${param.lstr_param4}"></td>
							</tr>
							<tr height=30>
								<td width=200 class="EWSubHeaderText">WIID</td>
								<td width=200 class="EWTableContents"><input name="WIId"
									id="WIIdId" readonly="readonly" value="${WIID}"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<hr size="1%" width="512">
			<table width="512" border="1" align="center">
				<tr>
					<td>
						<table width=100% border="0" align="center">
							<tr height=20>
								<td width="100%" class="EWSubHeaderText"><p align="center">Search
										Criterion</td>
							</tr>
						</table>
						<table width=100% align="center" border="0">
							<tr id="row1">
								<td width=200 class="EWSubHeaderText"><input type="radio"
									name="searchCriteria" id="radio2" value="Details"
									onclick="HandleClick(this);">Receipt No</td>
								<td width="40%" align="left" class="EWTableContents"><input
									type="text" name="Details - ReceiptNo" id="Details - ReceiptNoId" maxlength=35
									class="EWTableContents" size="20" disabled></td>
								<td width="40%" align="left" class="EWTableContents"><input
									type="button" name="SearchButton1" id="Search2"
									class="EWButton" value="Search" disabled
									onclick="Search();"></td>
							</tr>
							<tr>
								<td colspan=2 align="right" class="EWTableContents"><input
									type="button" name="ClearButton" class="EWButton"
									value="Clear Search" onclick="ClearFields();"></td>
								<td colspan=2 align="left" class="EWTableContents"><input
									type="button" name="CloseButton" class="EWButton" value="Close"
									onclick="FunClose();"> <input
									type="button" name="ReloadButton" class="EWButton"
									value="Reload" onclick="FunReload();"> 
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table width="512" border="0" align="center" id="displayTable_lead">
				<tr height=20>
					<td nowrap colspan="11" align="center" valign="middle"
						class="EWMessage"><p align="center">&nbsp;&nbsp;Till
							Now,no documents have been added for the LeadId.</td>
				</tr>
			</table>
			<table width="512" border="0" align="center" id="displayTable">
				<tr height=20>
					<td nowrap colspan="11" align="center" valign="middle"
						class="EWMessage"><p align="center">&nbsp;&nbsp;Till
							Now,no documents have been added for the search.</td>
				</tr>
			</table>
			<div style="overflow: auto; width: 568; height: 400px" align="center">
				<table width="512" border="2" id="DocView_Table" cellspacing=0
					align="center">
					<tr>
						<td class="EWSubHeader">Sr. No.</td>
						<td class="EWSubHeader">Document Name</td>
						<td class="EWSubHeader">ReceiptNo.</td>
						<td class="EWSubHeader">New/Old</td>
						<td class="EWSubHeader">Download</td>
					</tr>
					<c:forEach var="doc" items="${DocList}" varStatus="loop">
					<tr>
						<td class="EWSubHeader">${loop.count}</td>
						<td class="EWSubHeader"><a href='javascript:callWebApi("${doc.documentIndex}");'>${doc.documentName}(${doc.createdDatetime})</a></td>
						<td class="EWSubHeader">${doc.receiptNo}</td>
						<td class="EWSubHeader">${doc.tag}</td>
						<td class="EWSubHeader"><input type="button" name="Download" value="Download"${loop.count} onclick="javascript:DownloadDocument(${doc.documentIndex});"></td>
					</tr>
					</c:forEach>
				</table>
			</div>
			
			<input type="hidden" id="doclength" name ="doclength" value="${fn:length(DocList)}">
			<input type="hidden" id="userDBId" name="userDBId" value="${USERDBID}">
			<input type="hidden" id="docIdToDownload" name="docIdToDownload" value="">
			
		</fieldset>
	</form>
	<form id="WebApiForm" action="/omnidocs/WebApiRequestRedirection" method="post" target="_blank">
		<input type="hidden" id="Application" name ="Application" value="Finnone_OmniWebAPI">
		<input type="hidden" id="Userdbid" name="Userdbid" value="${USERDBID}">
		<input type="hidden" id="DocumentId" name="DocumentId" value="">
		<input type="hidden" id="sessionIndexSet" name ="sessionIndexSet" value="false">
		<input type="hidden" id="cabinetName" name ="cabinetName" value="${CabinetName}">
	</form>
	</c:if>
</body>
</html>