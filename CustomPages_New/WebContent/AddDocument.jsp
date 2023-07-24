<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="X-UA-Compatible" content="IE=11; charset=ISO-8859-1">
<title>Finnone Upload Document</title>
<link href="styles/docstyle.css" rel="stylesheet" type="text/css">
<script src="scripts/jquery3.min.js"></script>
<script type="text/javascript" src="scripts/CommonFunctions.js"></script>
<script type="text/javascript" src="scripts/AddFunctions.js"></script>
</head>
<body onload="onload()">
	<c:if test="${not empty ErrorMessage}">
		<div style="margin-left: 10%">
			<br>
			<br> <span style="text-align: center;">Below Error found
				in input -- </span> <br>
			<br> <span style="color: red; text-align: center;">${ErrorMessage}</span>
		</div>
	</c:if>
	<c:if test="${empty ErrorMessage}">
		<form name="FinnoneUploadDoc" id="FinnoneUploadDoc" method="POST"
			enctype="multipart/form-data" target="_self">
			<fieldset>
				<legend></legend>
				<table width="512" align="center" border="0">
					<tr height=30>
						<td width=200% align="Center" class="EWSubHeaderNum"><p
								align="Center">
								<b>Finnone-OmniDocs Upload Document</b></td>
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
										maxlength="255" value="${param.lstr_param1}"></td>
								</tr>
								<tr height=30>
									<td width=200 class="EWSubHeaderText">Lead ID</td>
									<td width=200 class="EWTableContents"><input name="LeadId"
										id="LeadIdId" readonly="readonly" maxlength="50"
										value="${param.lstr_param3}"></td>

								</tr>
								<tr height=30>
									<td width=200 class="EWSubHeaderText">Finnone DocID</td>
									<td width=200 class="EWTableContents"><input
										name="FinoneDocId" id="FinoneDocIdId" readonly="readonly"
										maxlength="50" value="${param.lstr_param4}"></td>
								</tr>
								<tr height=30>
									<td width=200 class="EWSubHeaderText">WIID</td>
									<td width=200 class="EWTableContents"><input name="WIId"
										id="WIIdId" readonly="readonly" maxlength="50"
										value="${WIID}"></td>
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

								<tr>
									<td width=200 class="EWSubHeaderText">Browse Document</td>
									<td width="200" align=left class="EWTableContents"><input
										type="file" name="fileBrowseDocument" class="EWButton"
										id="fileBrowseDocumentid" size=20></td>
								</tr>

								<tr>
									<td width=200 class="EWSubHeaderText">Receipt No<font
										color='red'>*</font></td>
									<td width="200" class="EWTableContents"><input type="text"
										name="Details - ReceiptNo" id="Details - ReceiptNoId"
										maxlength=15 size="30" class="EWTableContents" value=""
										title="Only alphanumeric characters,/,- allowed"></td>
								</tr>
								<tr class="EWTableContents">
									<td colspan=2 class="EWSubHeaderRemarks"><p align=left>*
											Please enter only alphanumeric characters, /, -.</p></td>

								</tr>


							</table>
						</td>
					</tr>
				</table>

				<table border="0" width="150" align="center">
					<tr>
						<td colspan=2 align="left"><input type="button"
							name="SubmitButton" class="EWButton" value="Upload"
							onclick="return UploadDocument();"></td>

						<td colspan=2 align="right"><input type="button"
							name="ClearButton" class="EWButton" value="Clear"
							onclick="ClearFields();"></td>
					</tr>
				</table>
			</fieldset>
		</FORM>
		<input type="hidden" name="LeadIdHidden" id="LeadIdHiddenId" value="">
	</c:if>
</body>
</html>