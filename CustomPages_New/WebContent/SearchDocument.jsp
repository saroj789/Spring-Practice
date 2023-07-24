<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="X-UA-Compatible" content="IE=11; charset=ISO-8859-1">
<title>Omnidocs</title>
<link href="styles/docstyle.css" rel="stylesheet" type="text/css">
<script src="scripts/jquery3.min.js"></script>
<script type="text/javascript" src="scripts/CommonFunctions.js"></script>
<script type="text/javascript" src="scripts/ViewFunctions.js"></script>
</head>
<body>
	<form name="FinnoneViewDoc" id="FinnoneViewDoc" action="" method="post">
		<fieldset>
			<legend></legend>
			<table width="512" align="center" border="0">
				<tr height=30>
					<td width="100%" align="Center" class="EWSubHeaderNum"><p
							align="Center">
							<b>OmniDocs: Search Document</b></td>
				</tr>
			</table>
			<hr size="1%" width="512">
			<table width="512" border="1" align="center">
				<tr>
					<td>
						<table width=100% border="0" align="center">
							<tr height=20>
								<td width="100%" class="EWSubHeaderText"><p align="Center">Search Criteria</td>
							</tr>
						</table>

						<table width=100% border="0" align="center">
							<tr height=30>
								<td width=200 class="EWSubHeaderText">DataClass Field1</td>
								<td width=200 class="EWTableContents"><input
									name="Field1" id="Field1"></td>
							</tr>
							<tr height=30>
								<td width=200 class="EWSubHeaderText">DataClass Field2</td>
								<td width=200 class="EWTableContents"><input name="Field2"
									id="Field2"></td>
							</tr>
							<tr id="row1">
								<td width="100%" align="center" class="EWTableContents"><input
									type="button" name="SearchButton1" id="Search2"
									class="EWButton" value="Search"
									onclick="SearchDoc();"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<hr size="1%" width="512">
			
			<!-- <table width="512" border="0" align="center" id="displayTable">
				<tr height=20>
					<td nowrap colspan="11" align="center" valign="middle"
						class="EWMessage"><p align="center">&nbsp;&nbsp;Till
							Now,no documents have been added for the search.</td>
				</tr>
			</table> -->
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
		<input type="hidden" id="Application" name ="Application" value="DocViewAPI">
		<input type="hidden" id="Userdbid" name="Userdbid" value="${USERDBID}">
		<input type="hidden" id="DocumentId" name="DocumentId" value="">
		<input type="hidden" id="sessionIndexSet" name ="sessionIndexSet" value="false">
		<input type="hidden" id="cabinetName" name ="cabinetName" value="${CabinetName}">
	</form>
</body>
</html>