<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%@ page import="com.newgen.business.ViewDocHelper,org.apache.commons.lang3.StringUtils"%>
<%
String queryString = request.getQueryString();
if (StringUtils.isNotEmpty(queryString)) {
String OD_UID = request.getParameter("userDbId");
System.out.println("OD_UID UserDbId Index.jsp===>"+OD_UID);
if(!OD_UID.equals("") || !OD_UID.equalsIgnoreCase("") || !OD_UID.equalsIgnoreCase("null") || OD_UID != null )
{
ViewDocHelper getData = new ViewDocHelper();
String sessId = getData.checkSession(OD_UID);
System.out.println("sessId Index.jsp===>"+sessId);
if(!sessId.equals("") || !sessId.equalsIgnoreCase(""))	
{
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script> -->
<link rel="stylesheet" href="scripts/bootstrap.min.css">
<script type="text/javascript" src="scripts/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="scripts/CommonFunctions.js"></script>
<script type="text/javascript" src="scripts/ViewFunctions.js"></script>
<title>Voucher Archival System</title>
<style>
.scrollTable {
	display: block;
    overflow-x: auto;
    white-space: nowrap;
    overflow-y: auto;
}
.table_wrapper{
    display: block;
    overflow-x: auto;
    white-space: nowrap;
    overflow-y: auto;
    }
.left-image{
    position: absolute;
}
.cbi {
background-color: #176FC1;    
}
.borderless td, .borderless th {
    border: none;
}
</style>
<script>

var myArray =[];
var totalCount=0;

class VoucherDtl
{ 
constructor( serial, voucher, journal, account,custname, debit, credit, remark)
	{
		
		this.serial = serial;
		this.voucher = voucher;
		this.journal = journal;
		this.account = account;
		this.custname = custname;
		this.debit = debit;
		this.credit = credit;
		this.remark = remark;
	}
}
function redirectToOD() {
	window.open('https://dmsuat.centralbank.co.in/omnidocs/dist/#/web','_blank');
}

function submitAudit(key='default')
{
	console.log(key);			 
	//alert('Records Validated:'+myArray.length );
	/* $("#validatedRecords").val(myArray.length);
	$("#unvalidatedRecords").val(unAudited); */
	//document.getElementById("MailBtn").disabled = false;
	//document.getElementById("SubBtn").disabled = false;
	//var remarksIfAny = document.getElementById("remarksIfAny").value;
	var noOfRows = document.getElementById("NoOfRows").value;
	console.log("noOfRows====>"+noOfRows);
	var rownoArray = '';
	var checkfldArray = '';
	var textfldArray = '';
	var serialfldArray = '';
	var voucherfldArray = '';
	var journalfldArray = '';
	var debitfldArray = '';
	var creditfldArray = '';
	var accountfldArray='';
	var chequefldArray='';
	var custnamefldArray='';
	var productfldArray='';
	var txndescFldArray='';
	var hobranchfldArray='';
	var useridfldArray='';
	var checkerIdfldArray='';
	var checkerId1fldArray='';
	var checkerId2fldArray='';
	var supidfldArray='';
	var timestampfldArray='';
	var addtRemarksArray='';
	var rectifiedArray='';
	myArray=[];
	for(i = 0; i < noOfRows; i++) {
		//myArray=[];
		
		var rowno = 'rowno'+i;
    	var checkfld = 'checkbox'+i;
    	var textfld = 'textbox'+i;
    	var serialfld = 'serial'+i;
    	var voucherfld = 'voucher'+i;
    	var journalfld = 'journal'+i;
    	var debitfld = 'debit'+i;
    	var creditfld = 'credit'+i;
    	var accountfld = 'account'+i;
    	var chequefld = 'cheque'+i;
    	var custnamefld = 'custname'+i;
    	var productfld = 'product'+i;
    	var txndescFld = 'txndesc'+i;
    	var hobranchfld = 'hobranch'+i;
    	var useridfld = 'userid'+i;
    	var checkerIdfld = 'checkerId'+i;
    	var checkerId1fld = 'checkerId1'+i;
    	var checkerId2fld = 'checkerId2'+i;
    	var supidfld = 'supid'+i;
    	var timestampfld = 'timestamp'+i;
    	var addtRemarksfld = 'AddtRemarks'+i;
    	var rectifiedfld = 'rectified'+i;
    	/* if(document.getElementById(textfld).value == '0' && document.getElementById(checkfld).checked == false) {
    		alert("Please select remarks for unverified Vouchers!!!");
    		return false;
    	} */
    	if(document.getElementById(checkfld).checked) {
    		checkfldArray += 'Yes' + ',';
    		doCheck(i);
    	}
    	else {
    		checkfldArray += 'No' + ',';
    	}
    	if(document.getElementById(textfld).value == '') {
    		textfldArray += '0' + ',';
    	}
    	else {
    		textfldArray += document.getElementById(textfld).value + ',';
    	}
    	
    	if(document.getElementById(rectifiedfld).checked) {
    		rectifiedArray += 'Yes' + ',';
    	}
    	else {
    		rectifiedArray += 'No' + ',';
    	}
    	if(document.getElementById(addtRemarksfld).value == '') {
    		addtRemarksArray += '-' + ',';
    	}
    	else {
    		addtRemarksArray += document.getElementById(addtRemarksfld).value + ',';
    	}
    	//textfldArray += document.getElementById(textfld).value + ',';
    	serialfldArray += document.getElementById(serialfld).innerText + ',';
    	voucherfldArray += document.getElementById(voucherfld).innerText + ',';
    	journalfldArray += document.getElementById(journalfld).innerText + ',';
    	debitfldArray += document.getElementById(debitfld).innerText + ',';
    	creditfldArray += document.getElementById(creditfld).innerText + ',';
    	accountfldArray += document.getElementById(accountfld).innerText + ',';
    	chequefldArray += document.getElementById(chequefld).innerText + ',';
    	custnamefldArray += document.getElementById(custnamefld).innerText + ',';
    	productfldArray += document.getElementById(productfld).innerText + ',';
    	txndescFldArray += document.getElementById(txndescFld).innerText + ',';
    	hobranchfldArray += document.getElementById(hobranchfld).innerText + ',';
    	useridfldArray += document.getElementById(useridfld).innerText + ',';
    	checkerIdfldArray += document.getElementById(checkerIdfld).innerText + ',';
    	checkerId1fldArray += document.getElementById(checkerId1fld).innerText + ',';
    	checkerId2fldArray += document.getElementById(checkerId2fld).innerText + ',';
    	supidfldArray += document.getElementById(supidfld).innerText + ',';
    	timestampfldArray += document.getElementById(timestampfld).innerText + ',';
    	//addtRemarksArray += document.getElementById(addtRemarksfld).value + ',';
    	//rectifiedArray += document.getElementById(timestampfld).innerText + ',';
	}
	var paramMailToBranch;
	paramMailToBranch = document.getElementById("isMailedtoBranch").value;
	console.log(paramMailToBranch);							
	var unAudited = parseInt(noOfRows)- parseInt(myArray.length);
	console.log("unAudited====>"+unAudited);
	//alert('myArray.length:'+ myArray.length);
	$("#validatedRecords").val(myArray.length);
	//alert('unvalidatedRecords:'+ unAudited);
	$("#unvalidatedRecords").val(unAudited);
	$("#checkfldArray").val(checkfldArray);
	$("#textfldArray").val(textfldArray);
	$("#serialfldArray").val(serialfldArray);
	$("#voucherfldArray").val(voucherfldArray);
	$("#journalfldArray").val(journalfldArray);
	$("#debitfldArray").val(debitfldArray);
	$("#creditfldArray").val(creditfldArray);
	$("#accountfldArray").val(accountfldArray);
	$("#chequefldArray").val(chequefldArray);
	$("#custnamefldArray").val(custnamefldArray);
	$("#productfldArray").val(productfldArray);
	$("#txndescFldArray").val(txndescFldArray);
	$("#hobranchfldArray").val(hobranchfldArray);
	$("#useridfldArray").val(useridfldArray);
	$("#checkerIdfldArray").val(checkerIdfldArray);
	$("#checkerId1fldArray").val(checkerId1fldArray);
	$("#checkerId2fldArray").val(checkerId2fldArray);
	$("#supidfldArray").val(supidfldArray);
	$("#timestampfldArray").val(timestampfldArray);
	$("#addtRemarksArray").val(addtRemarksArray);
	$("#rectifiedArray").val(rectifiedArray);
	
	$.ajax({
		url : "SaveAsDraft",
		data : encodeURI(decodeURIComponent($("#FinnoneViewDoc").serialize())),
		type : "post",
		success : function(data) {
				if(key=='submit')
			{
				alert('Submitted Successfully.');
				$("#demo :input").attr("readonly", true);
				$("#UploadBtn").prop("disabled","true");
				$("#SaveBtn").prop("disabled","true");
				$("#SubBtn").prop("disabled","true");   
			}
			else{
			alert("The details have been saved successfully!");
			}				
			//alert("Changes has been saved!");			  
		},
		error : function(data) {
			//alert("Error occured while saving changes.");
			if(key=='submit')
			{
				alert('Unable to submit the details. Please try again later.');			
			}
			else{
			alert("'Unable to save the details. Please try again later.'");
		}
		return false;
	 }	
	});
	for(var s=0; s<myArray.length;s++)
		{
		
		var txtfld = 'checkbox'+myArray[s];
		var serialfld = 'serial'+myArray[s];
		var voucherfld = 'voucher'+myArray[s];
		var journalfld = 'journal'+myArray[s];
		var accountfld = 'account'+myArray[s];
		var custnamefld = 'custname'+myArray[s];
		var debitfld = 'debit'+myArray[s];
		var creditfld = 'credit'+myArray[s];
		var remarkfld = 'textbox'+myArray[s];
		
		serial =document.getElementById(serialfld).innerText,
		voucher = document.getElementById(voucherfld).innerText,
		journal= document.getElementById(journalfld).innerText,
		account= document.getElementById(accountfld).innerText,
		custname = document.getElementById(custnamefld).innerText,
		debit = document.getElementById(debitfld).innerText,
		credit = document.getElementById(creditfld).innerText,
		remark = document.getElementById(remarkfld).value
		
		
		var vouch = new VoucherDtl(serial, voucher, journal, account,custname, debit, credit, remark);
		console.log(vouch);
		}				   
}

function sendMail()
{
	
	$.ajax({
		url : "MailToBranch",
		data : encodeURI(decodeURIComponent($("#FinnoneViewDoc").serialize())),
		type : "post",
		success : function(data) {
			alert("Mail sent!");
			document.getElementById("UploadBtn").disabled = false; //Upload Voucher Disabled
			document.getElementById("SubBtn").disabled = false;
			var isSubmitted= document.getElementById('isSubmitted').value;
			if(isSubmitted == "Yes")
            {
                $("#demo :input").attr("readonly", true);
                $("#UploadBtn").prop("disabled","true");
                $("#SaveBtn").prop("disabled","true");
                $("#SubBtn").prop("disabled","true"); 
            }
		},
		error : function(data) {
			alert("Error occured while sending Email");
			return false;
		}
	});
	
}

function clearAll()
{
	for(var s=0; s<myArray.length;s++)
	{
		var myIndex = myArray.indexOf(s);
		console.log(s, myArray[s]);
 		var txtfld = 'checkbox'+myArray[s];
 		var remarkfld = 'textbox'+myArray[s];
 		document.getElementById(txtfld).checked =false;
 		document.getElementById(remarkfld).value ="";
	}
	
	myArray=[];
}

function selectChange(counter)
{
    var remarksID  = 'textbox'+counter;
    var addtRemarksID = 'AddtRemarks'+counter;
    //var checkboxId = 'checkbox'+counter;
    console.log(document.getElementById(remarksID).value);
    var remarksValue = document.getElementById(remarksID).value;
    if(remarksValue =="Other")
    {  
        document.getElementById(addtRemarksID).disabled = false;
    }
    else{
        console.log('IN else');
         document.getElementById(addtRemarksID).value = "-";
      	 document.getElementById(addtRemarksID).disabled = true;
    }
}
function doCheck(s)
{

	var rowno = 'rowno'+s;
	var txtfld = 'checkbox'+s;
	var serialfld = 'serial'+s;
	var voucherfld = 'voucher'+s;
	var journalfld = 'journal'+s;
	var accountfld = 'account'+s;
	var custnamefld = 'custname'+s;
	var debitfld = 'debit'+s;
	var creditfld = 'credit'+s;
	var remarkfld = 'textbox'+s;
	var addtRemarksID = 'AddtRemarks'+s;
	
	if(document.getElementById(txtfld).checked)
		{
		document.getElementById(rowno).style.backgroundColor="#7ab2e97a";
		document.getElementById(remarkfld).value = '0';
		document.getElementById(remarkfld).disabled = true;
		document.getElementById(addtRemarksID).value = "-";
		document.getElementById(addtRemarksID).disabled = true;
		myArray.push(s);
		}
	else
		{
		var myIndex = myArray.indexOf(s);
		if(myIndex != -1)
			{
			myArray.splice(myIndex,1);
			}
		document.getElementById(rowno).style.backgroundColor="#ffffff";
		document.getElementById(remarkfld).disabled = false;
		}

	
	
}
function getPicklistValue() {
	var OD_UID = '<%=OD_UID%>';
	$("#OD_UID").val(OD_UID);
	//alert("OD_UID====>"+OD_UID);
	$.ajax({
		url : "GetPicklistData",
		type : "post",
		dataType:'json',
		data: {
			OD_UID : OD_UID
		},
		success : function(data) {
				/*var doclist = data.DocList;
				alert("doclist===>"+doclist);
				gridDisplay(doclist);*/
				//alert("data====>"+data);
				var s = '<option value="-1">Select a Branch</option>';  
	               for (var i = 0; i < data.length; i++) {  
	                   s += '<option value="' + data[i].BranchCode + '">' + data[i].BranchCode + '-->' + data[i].BranchName + '</option>';
	                   $("#UserNameLabel").html(data[0].UserName);
	                   $("#UserNameHiddenFld").val(data[0].UserName);
	               }  
	               $("#Field2").html(s);
	               
	               //alert("data.UserName");
			
		},
		error : function(data) {
			alert("Error while loading Branch Codes.");
			return false;
		}
	});
	
}

		function showhide()
    {  
         var div = document.getElementById("demo");  
         if (div.style.display !== "none") 
         {  
             div.style.display = "none";  
         }  
         else
         {  
             div.style.display = "block";  
         }  
    }  	

		$(document).keydown(function(event) {
		    if (event.which === 13) {
		    	$("#SearchBtn").click();
		    	return false;
		    }
		});
		
		$(document).on("click", "#SearchBtn", function() {
			document.getElementById("SaveBtn").disabled = false; //Save as Draft enabled
			//document.getElementById("SubmitBtn").disabled = true; //Mail to Branch Disabled
            document.getElementById("MailBtn").disabled = false; //Mail to Branch Disabled
            document.getElementById("UploadBtn").disabled = true; //Upload Voucher Disabled
			document.getElementById("SubBtn").disabled = true; // Submit button disabled
            var csvChange = false;
			$("#RemarksDiv").show();
			$("#demo").show();
			var Field1 = $('#Field1').val();
			var Field2 = $('#Field2').val();
			console.log(Field1);
            console.log(Field2);
            if(Field1 == "") {
				alert("Voucher Date cannot be blank");
				return false;
			}
			else if(Field2 == "") {
				alert("Branch Code cannot be blank");
				return false;
			}
			$('#branchCode').val(Field2);
			$('#voucherDate').val(Field1);
			//$('#img').show();
			//alert("field1 and field2===>"+Field1+ "----"+Field2);
			var OD_UID = '<%=OD_UID%>';
			//alert("OD_UID====>"+OD_UID);
			var params = {
					Field1 : Field1,
					Field2 : Field2,
					OD_UID : OD_UID
				   };
			$.post("ValidateSession", params, function(responseJson) {
				console.log("ValidateSession====>"+responseJson);
				console.log("ValidateSession DBID====>"+responseJson.docList);
				if(responseJson == '') {
	        		window.location.reload(true);
	        	}
			});
		    $.post("SearchandViewDoc", $.param(params), function(responseJson) {  
		    	//alert("responseJson===>"+responseJson);
		    	//$('#img').hide();
		    	if(responseJson != '') {
		    		//alert("responseJson===>"+responseJson);
		    	//$('#DocDataDiv').css('display', 'block');
		    	
		        var $table = $("<table width='100%' border='2' id='DocView_Table' cellspacing='0' align='center'>").appendTo($("#DocDataDiv"));
		        $("<tr style='border:solid 2px;'>").appendTo($table) 
		        		.append($("<td style='border:solid 2px;'>").text("Sr.No")) 	
		            	.append($("<td style='border:solid 2px;'>").text("Document Name"))       
		                .append($("<td style='border:solid 2px;'>").text("Doc Link")); 
		        $("#CSVData_Table").remove();
		         var $table2 = $("<table class='scrollTable' width='100%' border='1' id='CSVData_Table' cellspacing='0' align='center' style='height:475px;'>").appendTo($("#CSVData"));
		        $("<tr style='border:solid 1px;font-size:12px;text-align:center'>").appendTo($table2) 
		        	    .append($("<td style='border:solid 1px;'>").html("VCH<br/>OK"))
		        		.append($("<td style='border:solid 1px;'>").html("SR<br/>NO"))
		        		.append($("<td style='border:solid 1px;'>").html("VCH<br />NO"))		            
				        .append($("<td style='border:solid 1px;'>").html("DR"))
				        .append($("<td style='border:solid 1px;'>").html("CR"))
		                .append($("<td style='border:solid 1px;'>").html("ACCOUNT<br/>NO"))
				        .append($("<td style='border:solid 1px;'>").html("CUSTOMER NAME"))
                        .append($("<td style='border:solid 1px;'>").html("REMARKS"))
						.append($("<td style='border:solid 1px;'>").html("ADDT REMARKS")) 	
		            	.append($("<td style='border:solid 1px;'>").html("RECTIFIED"))
                        .append($("<td style='border:solid 1px;'>").html("JOURNAL<br/>NO"))
						.append($("<td style='border:solid 1px;'>").html("CHEQUE NO"))
						.append($("<td style='border:solid 1px;'>").html("PRODUCT TYPE")) 
						.append($("<td style='border:solid 1px;'>").html("TXN DESC"))
		            	.append($("<td style='border:solid 1px;'>").html("HOME BRANCH"))       
		                .append($("<td style='border:solid 1px;'>").html("USER ID"))
				        .append($("<td style='border:solid 1px;'>").html("CHECKER<br/>ID")) 	
		            	.append($("<td style='border:solid 1px;'>").html("CHECKER<br/>ID1"))       
		                .append($("<td style='border:solid 1px;'>").html("CHECKER<br/>ID2"))
				        .append($("<td style='border:solid 1px;'>").html("SUP ID")) 	
		            	.append($("<td style='border:solid 1px;'>").html("TIME<br/>STAMP"));  	
		                //.append($("<td style='border:solid 2px;'>").text(""));
		            var vArr='';
		            var result='';
		            var isSubmitted = '';
					var isMailedtoBranch = '';
					var OverallRemarks="";
		        $.each(responseJson, function(index, DocList) {
		        	//$('#DocumentId').val(DocList.documentIndex);
		        	var rowCount=0;
		        	if(index == 0) {
		        		var csvName =  DocList.documentName;
		        		$("#csvDocName").val(csvName);
		        		var csvdocumentIndex =  DocList.documentIndex;
		        		var indexString = DocList.String;
		        		//alert("indexString====>"+indexString);
		        		console.log("indexString====>"+indexString);
		        		$("#csvdocumentIndex").val(csvdocumentIndex);
		        		var emailID =  DocList.emailId;
		        		$("#emailID").val(emailID);
		        		rowCount =  DocList.rowCount;
		        		rowCount = Number(rowCount) - 1;
		        		$("#NoOfRows").val(rowCount);
		        		/* isMailedtoBranch = indexString.split("##")[3];
						isSubmitted = indexString.split("##")[4];
						OverallRemarks = indexString.split("##")[5]; */
						isMailedtoBranch = indexString.split("##")[2];
						isSubmitted = indexString.split("##")[3];
						OverallRemarks = indexString.split("##")[4];
						try {
							document.getElementById('overallRemarks').value = OverallRemarks;
						} catch (error) {
							document.getElementById('overallRemarks').value = "";
						}
						if(OverallRemarks == '-') {
							document.getElementById('overallRemarks').value = "";
						}
						try {
							document.getElementById('isMailedtoBranch').value = isMailedtoBranch;
						} catch (error) {
							document.getElementById('isMailedtoBranch').value = "";
						}
						
						try {
							document.getElementById('isSubmitted').value = isSubmitted;
						} catch (error) {
							document.getElementById('isSubmitted').value = "";
						}
						
						console.log("MAILED::",isMailedtoBranch,"SUBMITTED::",isSubmitted)
		        	}
		        	//alert("csvName====>index===>"+csvName+ "=====>"+index);
		        	$("#VoucherCountDiv").show();
		        	$("#ButtonsDiv").show();
		        	$("#ScannedImagesCount").html(DocList.pageCount);
		        	$("#ScannedImagesCounthidden").val(DocList.pageCount);
		        	var EVVRCount = DocList.voucherCount;
		        	EVVRCount = Number(EVVRCount) - 1;
		        	totalCount = rowCount;
		        	//Number(EVVRCount) = Number(EVVRCount) - 1;
				    $("#VoucherCount").html(EVVRCount);
				    $("#VoucherCounthidden").val(EVVRCount);
		        	$('#cabinetName').val(DocList.cabinetName);
		        	var documentIndex = DocList.documentIndex;
		        	vArr = DocList.values;
		        	//alert("vArr====>"+vArr);
		        	var str = vArr.toString();
		        	//alert("str====>"+str);
		        	result = str.split(/,+/);
		        	//alert("result.length====>"+result.length);
		        	 var i = 20;
		        	 var rows=0;
				        var csvlength = result.length;
				        var orilength = round16(csvlength);
				        //alert("Length of CSV===>"+orilength +"----" + csvlength);
				        var counter=0;
				        for(rows ; rows < rowCount ; rows++,i++) {
				            $("<tr style='border:solid 1px;font-size:12px' id='rowno"+counter+"'>").appendTo($table2) 
			            	/* .append($("<td style='text-align:center'><input type='checkbox' id='checkbox"+counter+"' onchange='doCheck("+counter+")'/>"))
				            //.append($("<td><input type='text' id='textbox"+counter+"' />"))
				            //.append($("<td><select id='textbox"+counter+"'><option value='0' selected>None Selected</option><option value='Voucher Not Found'>Voucher Not Found</option><option value='Voucher Image Unclear'>Voucher Image Unclear</option><option value='Account No. Mismatch'>Account No. Mismatch</option><option value='Amount Mismatch'>Amount Mismatch</option><option value='Journal No. Mismatch'>Journal No. Mismatch</option></select>"))
			                .append($("<td style='border:solid 1px;' id='serial"+counter+"'>").text(result[i+1])) 	     
			                .append($("<td style='border:solid 1px;' id='voucher"+counter+"'>").text(result[i+2])) 
                            .append($("<td style='border:solid 1px;' id='debit"+counter+"'>").text(result[i+3])) 
			                .append($("<td style='border:solid 1px;' id='credit"+counter+"'>").text(result[i+4])) 	     
			                .append($("<td style='border:solid 1px;' id='account"+counter+"'>").text(result[i+5]))
                            .append($("<td style='border:solid 1px;' id='custname"+counter+"'>").text(result[i+6])) 
                            .append($("<td><select style='width: 7rem' id='textbox"+counter+"' onchange=selectChange('"+counter+"')><option value='0' selected>None Selected</option><option value='Voucher Not Found'>Voucher Not Found</option><option value='Voucher Image Unclear'>Voucher Image Unclear</option><option value='Account No. Mismatch'>Account No. Mismatch</option><option value='Amount Mismatch'>Amount Mismatch</option><option value='Journal No. Mismatch'>Journal No. Mismatch</option><option value='Signature Mismatch'>Signature Mismatch</option><option value='Other'>Other</option></select>"))
			                .append($("<td style='border:solid 1px;' id='journal"+counter+"'>").text(result[i+8])) 	     
			                .append($("<td style='border:solid 1px;text-align:center' id='cheque"+counter+"'>").text(result[i+9])) 	     
			                .append($("<td style='border:solid 1px;' id='product"+counter+"'>").text(result[i+10])) 	     
			                .append($("<td style='border:solid 1px;' id='txndesc"+counter+"'>").text(result[i+11])) 
			                .append($("<td style='border:solid 1px;' id='hobranch"+counter+"'>").text(result[i+12]))
			                .append($("<td style='border:solid 1px;' id='userid"+counter+"'>").text(result[i+13])) 
			                .append($("<td style='border:solid 1px;' id='checkerId"+counter+"'>").text(result[i+14]))
			                .append($("<td style='border:solid 1px;' id='checkerId1"+counter+"'>").text(result[i+15]))
			                .append($("<td style='border:solid 1px;' id='checkerId2"+counter+"'>").text(result[i+16]))
			                .append($("<td style='border:solid 1px;' id='supid"+counter+"'>").text(result[i+17]))
			                .append($("<td style='border:solid 1px;' id='timestamp"+counter+"'>").text(result[i+18]))
				            .append($("<td><input type='text' id='AddtRemarks"+counter+"' value='"+result[i+19]+"' disabled/></td>"))
			                .append($("<td style='text-align:center'><input type='checkbox' id='rectified"+counter+"' disabled/></td>")); */
			                
			                
							
							.append($("<td style='text-align:center'><input type='checkbox' id='checkbox"+counter+"' onchange='doCheck("+counter+")'/>"))
				            .append($("<td style='border:solid 1px;' id='serial"+counter+"'>").text(result[i+2])) 	     
			                .append($("<td style='border:solid 1px;' id='voucher"+counter+"'>").text(result[i+3])) 
                            .append($("<td style='border:solid 1px;' id='debit"+counter+"'>").text(result[i+4])) 
			                .append($("<td style='border:solid 1px;' id='credit"+counter+"'>").text(result[i+5])) 	     
			                .append($("<td style='border:solid 1px;' id='account"+counter+"'>").text(result[i+6]))
                            .append($("<td style='border:solid 1px;' id='custname"+counter+"'>").text(result[i+7])) 
                            .append($("<td><select style='width: 7rem' id='textbox"+counter+"' onchange=selectChange('"+counter+"')><option value='0' selected>None Selected</option><option value='Voucher Not Found'>Voucher Not Found</option><option value='Voucher Image Unclear'>Voucher Image Unclear</option><option value='Other'>Other</option></select>"))
							.append($("<td><input type='text' id='AddtRemarks"+counter+"' value='"+result[i+9]+"' disabled/></td>"))
			                .append($("<td style='text-align:center'><input type='checkbox' id='rectified"+counter+"' disabled/></td>"))
			                .append($("<td style='border:solid 1px;' id='journal"+counter+"'>").text(result[i+11])) 	     
			                .append($("<td style='border:solid 1px;text-align:center' id='cheque"+counter+"'>").text(result[i+12])) 	     			  
			                .append($("<td style='border:solid 1px;' id='product"+counter+"'>").text(result[i+13])) 	     
			                .append($("<td style='border:solid 1px;' id='txndesc"+counter+"'>").text(result[i+14])) 
			                .append($("<td style='border:solid 1px;' id='hobranch"+counter+"'>").text(result[i+15]))
			                .append($("<td style='border:solid 1px;' id='userid"+counter+"'>").text(result[i+16])) 
			                .append($("<td style='border:solid 1px;' id='checkerId"+counter+"'>").text(result[i+17]))
			                .append($("<td style='border:solid 1px;' id='checkerId1"+counter+"'>").text(result[i+18]))
			                .append($("<td style='border:solid 1px;' id='checkerId2"+counter+"'>").text(result[i+19]))
			                .append($("<td style='border:solid 1px;' id='supid"+counter+"'>").text(result[i+20]))
			                .append($("<td style='border:solid 1px;' id='timestamp"+counter+"'>").text(result[i+21]));
				            
                            var chkbox ='checkbox'+counter;
                            var txtbox ='textbox'+counter;
                            var rectCheck ='rectified'+counter;
                            var addtRemarks ='AddtRemarks'+counter;
                            if(result[i+9] =='0')
                            {
                            	document.getElementById(addtRemarks).value = "-";
                            }
                            if(result[i+1] =='Yes')
                            {
                                csvChange= true;
                                console.log(i, result[i+1])
                                document.getElementById(chkbox).checked = true;
                                document.getElementById(rectCheck).disabled = true;
                                document.getElementById(txtbox).disabled = true;
                                document.getElementById(addtRemarks).value = "-";
                                document.getElementById(addtRemarks).disabled = true;
                            }
                            else{
                                if(result[i+10] =='Yes')
                                {
                                    console.log(i, result[i+10])
                                    document.getElementById(rectCheck).checked = true;
                                    csvChange= true;
                                }
                                if(result[i+8] !='0')
                                {
                                    console.log(i, result[i+8])
                                    document.getElementById(txtbox).value = result[i+8];
                                    csvChange= true;
                                }
				        }
				            counter = counter + 1;
			                i = i + 20;
				            }
				       // $("#NoOfRows").val(counter);
				        callWebApi(documentIndex);
		            $("<tr>").appendTo($table)                     
		            	.append($("<td style='border:solid 2px;'>").text(index+1)) 	     
		                .append($("<td style='border:solid 2px;'>").text(DocList.documentName))      
		                .append($("<td style='border:solid 2px;'>").append($("<a>").attr({
			            	 href : 'javascript:callWebApi('+documentIndex+')'})
				            	.text(DocList.documentName+ "-" +DocList.createdDatetime)));
		            documentIndex = "";
		           
		            //var valueArr = vArr.split(',');
		            //alert("vArr====>"+vArr);
		           // alert("valueArr===>"+valueArr);
		            
		        });
 			console.log(csvChange)
 			console.log("last isMailedtoBranch:",isMailedtoBranch," isSubmitted:",isSubmitted)
 			if(isSubmitted == 'Yes') {
 				$("#demo :input").attr("readonly", true);
				$("#UploadBtn").prop("disabled","true");
				$("#SaveBtn").prop("disabled","true");
				$("#SubBtn").prop("disabled","true"); 
				$("#overallRemarks").prop("disabled","true");
 			}
			if(isMailedtoBranch == "true" && isSubmitted != 'Yes')
			{
				var VouchCount = document.getElementById('NoOfRows').value;
				document.getElementById("UploadBtn").disabled = false; //Upload Voucher Disabled
				document.getElementById("SubBtn").disabled = false; // Submit button disabled
				for(var countRow=0;countRow<VouchCount;countRow++)
				{
					console.log(isMailedtoBranch, countRow)
					var voucherok = 'checkbox'+countRow;
					if(document.getElementById(voucherok).checked == true)
					{
						var rectifiedCheck = 'rectified'+countRow;
						document.getElementById(rectifiedCheck).disabled = true;
					}
					else if(document.getElementById(voucherok).checked == false)
					{
						var rectifiedCheck = 'rectified'+countRow;
						document.getElementById(voucherok).disabled = true;
						document.getElementById(rectifiedCheck).disabled = false;
					}
				}
			}
               /*  if(csvChange)
                {
                    document.getElementById("SaveBtn").disabled = false; //Save as Draft enabled
			document.getElementById("MailBtn").disabled = false; //Mail to Branch Disabled
            document.getElementById("UploadBtn").disabled = false; //Upload Voucher Disabled
			document.getElementById("SubBtn").disabled = false; // Submit button disabled
                }
                else{
                    document.getElementById("SaveBtn").disabled = false; //Save as Draft enabled
			document.getElementById("MailBtn").disabled = true; //Mail to Branch Disabled
            document.getElementById("UploadBtn").disabled = true; //Upload Voucher Disabled
			document.getElementById("SubBtn").disabled = true; // Submit button disabled
                } */
		        //var dividend = result.length / 16;
		        //dividend = Math.
		       
		    	}
		    	else {
		    		//$('#img').hide();
		    		alert("No results found");
		    	}
		    });
		});	
		
		 function round16(x)
        {
            return Math.floor(x/22)*22;
        }
        /* function round16(x)
        {
            return Math.ceil(x/21)*21;
        } */
		function isNull(num) {
			if(num == "" || num == ",")
				{
					return "-";
				}
		}
		
		function checkforRemarks() {
			//alert("Inside checkforRemarks");
			var noOfRows = document.getElementById("NoOfRows").value;
			document.getElementById("isMailedtoBranch").value = 'true';
			var flag=true;
	        for (var i = 0; i < noOfRows; i++) {
	          var chkfld = "checkbox" + i;
	          if (!document.getElementById(chkfld).checked) {
	            var remarkfld = "textbox" + i;
	            if (document.getElementById(remarkfld).value == "0") {
	              alert(
	                "Some Vouchers are either unverified or have remarks missing. Please review the same and submit again."
	              );
	              flag=false;
	              break;
	              
	            }
	          }
	        }
	        
	        if(flag==true) {
	        	submitAudit('mail');
	        	sendMail();
	        }

	        //document.getElementById("SaveBtn").disabled = false;
	        //document.getElementById("MailBtn").disabled = false;
	        //document.getElementById("SubmitBtn").disabled = false;
	      }
		
		
		function submitCheck()
		  {
			var VouchCount = document.getElementById('NoOfRows').value;
			document.getElementById('isSubmitted').value='Yes';
			var submitFlag = true;
			var check = false;
			for(var i=0;i<VouchCount;i++)
			{
				var vouchOk = 'checkbox'+i;
				var rectOk = 'rectified'+i;
				console.log(document.getElementById(vouchOk).checked, document.getElementById(rectOk).checked,i);
				if(document.getElementById(vouchOk).checked || document.getElementById(rectOk).checked)
				{
						
				}
				else{
					submitFlag = false;
					break;
				}	
			}
			if(submitFlag)
			{
				check = confirm("Once submitted you will not be able to make any further changes. Do you wish to continue?")
			//	alert(check);
			}
			if(!submitFlag)
				{
					alert('Some vouchers are yet to be rectified. Please review and try again.');
				}
			else if(submitFlag && check ==true){
				submitAudit('submit');
				saveReportData();
			}
			
		  }
		
		function saveReportData() {
			 //var rectifiedCount = ExtractCount('rectifiedArray')['Yes'];
		     //var voucherokCount = ExtractCount('checkfldArray')['Yes'];
		     
		     var rectifiedArray = $("#rectifiedArray").val();
		     var fieldArray = rectifiedArray.split(',');
		     let rectifiedCount = 0

		     for (let i = 0; i < fieldArray.length; ++i) {
		      if (fieldArray[i] == 'Yes')
		    	  rectifiedCount++;
		     }
		     
		     var checkfldArray = $("#checkfldArray").val();
		     var fieldArray1 = checkfldArray.split(',');
		     let voucherokCount = 0

		     for (let i = 0; i < fieldArray1.length; ++i) {
		      if (fieldArray1[i] == 'Yes')
		    	  voucherokCount++;
		     }
		     $("#rectifiedCount").val(rectifiedCount);
		     $("#voucherokCount").val(voucherokCount);
			$.ajax({
				url : "SaveReportData",
				data : encodeURI(decodeURIComponent($("#FinnoneViewDoc").serialize())),
				type : "post",
				success : function(data) {
					console.log("Report Data saved successfully");
				},
				error : function(data) {
					alert("Error occured while saving report Data");
					return false;
				}
			});
		}
		
		/* function ExtractCount(fieldID)
		{
		    var fieldValue = document.getElementById(fieldID).value;
		    var fieldArray = fieldValue.split(',');
		    var fieldCount = {};
		    for(const num in fieldArray){
		        fieldCount[num] = fieldCount[num]?fieldCount[num]+1:1;
		    }
		    return fieldCount;

		} */
	</script>
</head>
<body onload="getPicklistValue();">
<form id="FinnoneViewDoc">
<div class="container-fluid">
    <div class="modal-content">
     <nav class="p-1 mb-1 cbi text-white"> 
	 <div  class="row">
	 <div class="col-sm-6">
	 <h5  class="text-lg-right" style= "padding-top: 1.4rem;padding-left: 2rem;font-size:1.5rem;"> DMS - Voucher Audit System </h5>
		</div>
		<div class="col-sm-6">
		<img src="images/OIP.jpg" alt="logo" width="300" height="80" align="right"  />
		</div>
   </div>
     
	  </nav>

	
     <!-- <nav class="p-0.5 mb-0.5 bg-primary text-white">  
	 <div  class="" >
        <h5 id="formModalLabel" >Voucher Archival System<img src="images/OIP_2.jpg" alt="logo" width="200" height="75" align="right" /></h5>
   
      </div>
	  </nav></br> -->
	
	<div class="test border border-dark" style="padding-bottom:.75rem;">
		<nav class="cbi text-white"> 
			<!-- <div  class="container-fluid">
				<h6 align="center" class="container-fluid" id="formModalLabel" style="font-size:25px;">Search Vouchers</h6>
			</div> -->
			<div class="row">
				<div class="col-lg-2">
                    <a href="./AuditReport.jsp?userDbId=<%=OD_UID%>" target="_self" style="font-size:22px;color:white;font-style:bold;">Audit Report</a>
                </div>
    			<div class="col-lg-7">
				<h6 align="center" class="container-fluid" id="formModalLabel" style="font-size:25px;padding-left:10rem;">Search Vouchers</h6>
    			<!-- <label id="UserNameLabel"></label> -->
    			</div>
    			<div class="col-lg-2">
    			<label style="font-size:22px;">User ID: </label>
				<label id="UserNameLabel" style="font-size:22px;">Supervisor</label></div>
    			<div class="col-lg-1" style="padding-left:3.1rem;">
        		<a href='javascript:FunClose();'><img src="images/shutdown.png" width="45px" height="37px;" alt="Logout Image" title="Logout"></a>
   				 </div>
			</div>
	  </nav>
	  
	  <div class="container col-md-12">
	   
	   <div style="padding-top:.70rem"> <!-- style="border:solid 1px #ced4da; -->
	  <table class="table table-borderless" style="margin-top:1rem;font-style:bold;font-size:20px;">
	  <tr>
		<td> Voucher Date: </td> 
		<td> <input type="date" class="form-control" id="Field1" name="Field1" style="width: 15rem; margin-right:1rem;"></td> 
		<td> Branch Code: </td> 
		<td> <select id="Field2" name="Field2" style="width:250px;height:40px;border-color:#ced4da;font-size:1rem;;
		padding:.250rem .54rem;color:#212529;border-radius:.25rem;"></select>
		<!-- <input type="text" class="form-control" id="Field2" name="Field2" style="width: 250px;margin-right:1rem;"> --> </td>
		<td><input type="button" style="align:center;margin:0 0 1rem 1.5rem;background-color:#176FC1;font-style:bold;
		font-weight:750;letter-spacing:1.5px;width:15rem" value="Search" class="btn btn-primary" id="SearchBtn" name="SearchBtn"/><!-- Search</button> --></td> 
		</tr>
		<!-- onclick="SearchDoc_New();"  -->
		</table>
		
		</div>
		<!-- <div class="left-image">
	   <img src="images/tenor.gif" id="img" style="display:none;align:center;width:150px;height:150px;" />
	   </div> 
		<div class="text-center">
		&nbsp;&nbsp;<input type="button" style="align:center;margin:1rem 0 1rem 0;background-color:#176FC1;font-style:bold;font-weight:750;letter-spacing:1.5px;width:250px" value="Search" class="btn btn-primary" id="SearchBtn" name="SearchBtn"/>Search</button>
		</div> -->
	</div>
	<div class="container-fluid col-md-12" style="margin-bottom: 0.75rem;">
	<div id="VoucherCountDiv" style="display:none;">
	<div class="row">
	<div class="col-md-6" style="text-align: center;">
	
		
		<label>No. of Vouchers in EVVR:</label>&nbsp;<label id="VoucherCount"></label></div>
	<div class="col-md-6" style="text-align: center;">	
	   	<label>No. of Images Scanned:</label>&nbsp;<label id="ScannedImagesCount"></label>
	   	</div>
	</div>	
		</div>
	</div>
		
		 <!-- <button  class="btn btn-primary" onclick="SearchDoc_New()" id="btn" >Search</button> --> 
		
	<div class="test border border-dark" id="DocDataDiv" style="display: none" align="center">
				<%-- <table width="100%" border="2" id="DocView_Table" cellspacing=0
					align="center">
					<tr>
						<td class="EWSubHeader">Sr. No.</td>
						<td class="EWSubHeader">Document Name</td>
						<td class="EWSubHeader">ReceiptNo.</td>
						<td class="EWSubHeader">New/Old</td>
						<td class="EWSubHeader">View</td>
					</tr>
					<c:forEach var="doc" items="${DocList}" varStatus="loop">
					<tr>
						<td class="EWSubHeader">${loop.count}</td>
						<td class="EWSubHeader"><a href='javascript:callWebApi("${doc.documentIndex}");'>${doc.documentName}(${doc.createdDatetime})</a></td>
						<td class="EWSubHeader">${doc.receiptNo}</td>
						<td class="EWSubHeader">${doc.tag}</td>
						<td class="EWSubHeader"><input type="button" name="View" value="View"${loop.count} onclick="javascript:DownloadDocument(${doc.documentIndex});"></td>
					</tr>
					</c:forEach>
				</table> --%>
			</div>	
	<div id="demo" style="display:none">
	
		<div  class="container-fluid" id="dis">
		<div class="form-group row">
		
		<div class="container col-md-6">
		<div class="test border border-dark" required style="height: 500px;" >
        <h5 class="text-center" id="evvr" style="margin-bottom:0; border:solid 1px black;">EVVR</h5>
        <div id="CSVData" class="table_wrapper" style="text-align:center" name="CSVData"></div>
		</div>
		</div>
		
		
		<div class="container col-md-6">
		<div class="test border border-dark" required style="height: 500px;" >
		
        <h5 class="text-center"  id="vi" style="margin-bottom:0; border:solid 1px black;">Voucher Image</h5>
        <input type="hidden" id="Application" name ="Application" value="DocViewAPI">
		<input type="hidden" id="DocumentId" name="DocumentId" value="">
		<input type="hidden" id="sessionIndexSet" name ="sessionIndexSet" value="false">
		<input type="hidden" id="cabinetName" name ="cabinetName" value="">
		<input type="hidden" id="S" name ="S" value="S">
		<input type="hidden" id="DataClassName" name="DataClassName" value="Voucher">
		<input type="hidden" id="OD_UID" name="OD_UID" value="">
		<input type="hidden" id="branchCode" name="branchCode" value="">
		<input type="hidden" id="voucherDate" name="voucherDate" value="">
		<input type="hidden" id="validatedRecords" name="validatedRecords" value="">
		<input type="hidden" id="unvalidatedRecords" name="unvalidatedRecords" value="">
		<input type="hidden" id="VoucherCounthidden" name="VoucherCounthidden" value="">
		<input type="hidden" id="ScannedImagesCounthidden" name="ScannedImagesCounthidden" value="">
		<input type="hidden" id="NoOfRows" name="NoOfRows" value="">
		 <input type="hidden" id="checkfldArray" name="checkfldArray" value="">
		<input type="hidden" id="textfldArray" name="textfldArray" value="">
		<input type="hidden" id="serialfldArray" name="serialfldArray" value="">
		<input type="hidden" id="voucherfldArray" name="voucherfldArray" value="">
		<input type="hidden" id="journalfldArray" name="journalfldArray" value="">
		<input type="hidden" id="debitfldArray" name="debitfldArray" value="">
		<input type="hidden" id="creditfldArray" name="creditfldArray" value="">
		<input type="hidden" id="accountfldArray" name="accountfldArray" value="">
		<input type="hidden" id="chequefldArray" name="chequefldArray" value="">
		<input type="hidden" id="custnamefldArray" name="custnamefldArray" value="">
		<input type="hidden" id="productfldArray" name="productfldArray" value="">
		<input type="hidden" id="txndescFldArray" name="txndescFldArray" value="">
		<input type="hidden" id="hobranchfldArray" name="hobranchfldArray" value="">
		<input type="hidden" id="useridfldArray" name="useridfldArray" value="">
		<input type="hidden" id="checkerIdfldArray" name="checkerIdfldArray" value="">
		<input type="hidden" id="checkerId1fldArray" name="checkerId1fldArray" value="">
		<input type="hidden" id="checkerId2fldArray" name="checkerId2fldArray" value="">
		<input type="hidden" id="supidfldArray" name="supidfldArray" value="">
		<input type="hidden" id="timestampfldArray" name="timestampfldArray" value="">
		<input type="hidden" id="addtRemarksArray" name="addtRemarksArray" value="">
		<input type="hidden" id="rectifiedArray" name="rectifiedArray" value="">
		<input type="hidden" id="csvDocName" name="csvDocName" value="">
		<input type="hidden" id="emailID" name="emailID" value="">
		<input type="hidden" id="isSubmitted" name="isSubmitted" value="">
		<input type="hidden" id="isMailedtoBranch" name="isMailedtoBranch" value="">
		<input type="hidden" id="csvdocumentIndex" name="csvdocumentIndex" value="">
		<input type="hidden" id="rectifiedCount" name="rectifiedCount" value="">
		<input type="hidden" id="UserNameHiddenFld" name="UserNameHiddenFld" value="">
		<input type="hidden" id="voucherokCount" name="voucherokCount" value="">	
									
		<iframe id="DocImage" name="DocImage" 
		src="" width="100%" height="95%" scrolling="yes">
		
		</iframe>
		</div>
		</div>
      </div>
	  </div>
	 
	</div>
	
  </div>
</div>
</div>

<div
        class="row container-fluid"
        id="RemarksDiv"
        style="display: none; margin-top: 0.75rem; margin-bottom: 0.75rem">
        <div class="col-md-2">
          <span>Overall Remarks</span>
        </div>
        <div class="col-md-6">
          <textarea id="overallRemarks" name="overallRemarks" style="width: 50%"></textarea>
        </div>
      </div>
<div class="row container-fluid" id="ButtonsDiv" style="display:none;margin-top:0.75rem;margin-bottom:0.75rem" >	
	<div class="col-md-12">
	<input type="button" onclick="submitAudit();" 
	style="background-color:#176FC1" value="Save as Draft" class="btn btn-primary" id="SaveBtn" name="SaveBtn"/>
	<input type="button" onclick="checkforRemarks();" 
    style="background-color:#176FC1;margin-left:0.5rem" value="Mail to Branch" class="btn btn-primary" id="MailBtn" name="MailBtn"/>
	<input
     type="button"
            onclick="redirectToOD();"
            style="background-color: #176fc1; margin-left: 0.5rem"
            value="Upload Voucher"
            class="btn btn-primary"
            id="UploadBtn"
            name="UploadBtn"
          />
	<input
    
            type="button"
            onclick="submitCheck();"
            style="background-color: #176fc1; margin-left: 0.5rem"
            value="Submit"
            class="btn btn-primary"
            id="SubBtn"
            name="SubBtn"
          />
	<!--<input type="button" onclick="submitAudit();sendMail();" style="background-color:#176FC1;margin-left:0.5rem" value="Mail to Branch" class="btn btn-primary" id="SubmitBtn" name="SubmitBtn"/>-->
<!-- 	<input type="button" onclick="clearAll()" style="background-color:#176FC1" value="Clear" class="btn btn-primary" id="ClearBtn" name="ClearBtn"/> -->
	</div>
	</div>
</form>
<!-- <form id="WebApiForm" method="post"> -->
		<!-- <input type="hidden" id="Application" name ="Application" value="tt">
		<input type="hidden" id="DocumentId" name="DocumentId" value="">
		<input type="hidden" id="sessionIndexSet" name ="sessionIndexSet" value="false">
		<input type="hidden" id="cabinetName" name ="cabinetName" value=""> -->
		<!-- <input type="hidden" name="DataClassName" value="Voucher"> -->
	<!-- </form> -->

</body>
</html>
<% } else { %>
<html>
<head>
<title>Error</title>
</head>
<body>
    <center>
        <h1>Error</h1>
        <h2>Invalid Session...You have been logged out!!<br/> </h2>
        <h2><a href='./login.jsp'>Click here to login</a><br/> </h2>
    </center>
</body>
</html>
<% } 
}
else{%>
<html>
<head>
<title>Error</title>
</head>
<body>
    <center>
        <h1>Error</h1>
        <h2>Invalid Session...You have been logged out!!<br/> </h2>
        <h2><a href='./login.jsp'>Click here to login</a><br/> </h2>
    </center>
</body>
</html>
<% }
}
else{%>
<html>
<head>
<title>Error</title>
</head>
<body>
    <center>
        <h1>Error</h1>
        <h2>Invalid Session...You have been logged out!!<br/> </h2>
        <h2><a href='./login.jsp'>Click here to login</a><br/> </h2>
    </center>
</body>
</html>
<% } %>