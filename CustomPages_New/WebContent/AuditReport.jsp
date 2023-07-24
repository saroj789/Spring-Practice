<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%@ page import="com.newgen.business.ViewDocHelper,org.apache.commons.lang3.StringUtils,java.sql.*"%>
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
<link rel="stylesheet" type="text/css" href="scripts/datatables.min.css"/>
 

<script type="text/javascript" src="scripts/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="scripts/datatables.min.js"></script>
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

function getPicklistValueRegionCode() {
	var OD_UID = '<%=OD_UID%>';
	$("#OD_UID").val(OD_UID);
	//alert("OD_UID====>"+OD_UID);
	$.ajax({
		url : "GetPicklistDataRegionCode",
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
				var s = '<option value="-1">Select a Region Code</option>';  
	               for (var i = 0; i < data.length; i++) {  
	                   s += '<option value="' + data[i].Region_No + '">' + data[i].Region_No + '-->' + data[i].RegionName + '</option>';
	                   $("#UserNameLabel").html(data[0].UserName);
	                   $("#UserNameHiddenFld").val(data[0].UserName);
	               }  
	               $("#Field2").html(s);
	               
	               //alert("data.UserName");
			
		},
		error : function(data) {
			alert("Error while loading Region Codes.");
			return false;
		}
	});
	
}
		$(document).keydown(function(event) {
		    if (event.which === 13) {
		    	$("#RepSearchBtn").click();
		    	return false;
		    }
		});
		
		$(document).on("click", "#RepSearchBtn", function() {
			//$('#RepSearchBtn').attr("disabled", true);
			event.preventDefault();
			var Field1 = $('#Field1').val();
			var Field2 = $('#Field2').val();
			console.log(Field1);
            console.log(Field2);
            if(Field1 == "") {
				alert("Voucher Date cannot be blank");
				return false;
			}
			else if(Field2 == "") {
				alert("Region Code cannot be blank");
				return false;
			}
			$('#branchCode').val(Field2);
			$('#voucherDate').val(Field1);
			var OD_UID = '<%=OD_UID%>';
			var params = {
					Field1 : Field1,
					Field2 : Field2,
					OD_UID : OD_UID
				   };
			/* $.post("ValidateSession", params, function(responseJson) {
				console.log("ValidateSession====>"+responseJson);
				//console.log("ValidateSession DBID====>"+responseJson.docList);
				if(responseJson == '') {
	        		window.location.reload(true);
	        	}
			}); */
		    $.post("SearchReportData", $.param(params), function(responseJson) {  
		    	if(responseJson != '') {
		    	$('#reportDiv').show();
		    	$('#RegionNo').html(Field2);
	            //$('#Voucher_Date').html(Field1);
		    	event.preventDefault();
		    	$("#CSVData_Table").remove();
		         var $table2 = $("<table class='table table-bordered' style='margin-top:1rem;font-style:bold;font-size:17px;border:1px solid black;' id='CSVData_Table' cellspacing='0'>").appendTo($("#reportDiv"));
		        $("<tr style='text-align:center'>").appendTo($table2) 
		        		.append($("<td style='border:solid 1px;'>").html("S.<br/>No"))
		        		.append($("<td style='border:solid 1px;'>").html("Branch Code"))
		        		.append($("<td style='border:solid 1px;'>").html("Branch Name"))
				        .append($("<td style='border:solid 1px;'>").html("Verification Date"))
				        .append($("<td style='border:solid 1px;'>").html("Verified By"))
		                .append($("<td style='border:solid 1px;'>").html("Total EVVR Records"))
				        .append($("<td style='border:solid 1px;'>").html("Voucher Ok Records"))
                        .append($("<td style='border:solid 1px;'>").html("Rectified Records"))
						.append($("<td style='border:solid 1px;'>").html("Audit Status"));  
		       
		        var counter=0;
		        $.each(responseJson, function(index, DocList) {
		        	$('#Voucher_Date').html(DocList.VoucherDate);
				        console.log("DocList====>"+DocList);
				        console.log("index====>"+index);
				        counter++; 
				            $("<tr style='text-align:center' id='rowno"+counter+"'>").appendTo($table2) 
				            .append($("<td style='border:solid 1px;text-align:center' id='serial"+counter+"'>").text(counter)) 	     
			                .append($("<td style='border:solid 1px;text-align:center' id='BranchCode"+counter+"'>").text(DocList.BranchCode))
			                .append($("<td style='border:solid 1px;text-align:center' id='BranchCode"+counter+"'>").text(DocList.BranchName)) 
                            .append($("<td style='border:solid 1px;text-align:center' id='VerificationDate"+counter+"'>").text(DocList.VerificationDate)) 
			                .append($("<td style='border:solid 1px;text-align:center' id='VerifiedBy"+counter+"'>").text(DocList.VerifiedBy)) 	     
			                .append($("<td style='border:solid 1px;text-align:center' id='totalEVVRRecords"+counter+"'>").text(DocList.totalEVVRRecords))
                            .append($("<td style='border:solid 1px;text-align:center' id='voucherOkRecords"+counter+"'>").text(DocList.voucherOkRecords)) 
			                .append($("<td style='border:solid 1px;text-align:center' id='rectifiedRecords"+counter+"'>").text(DocList.rectifiedRecords)) 	     			  
			                .append($("<td style='border:solid 1px;text-align:center' id='auditStatus"+counter+"'>").text(DocList.auditStatus)); 	     
				            
		      
		        });
		        $('#CSVData_Table').DataTable();
		        ///$('#SearchBtn').attr("disabled", false);
		 }
		    	else {
		    		alert("No Data found");
		    		//$('#SearchBtn').attr("disabled", false);
		    	}
		    });
		});	
		
		$(document).ready(function () {
		    $('#CSVData_Table').DataTable();
		});
	/* function selectChange(event)
	{
	        console.log(event.target.value);
	        $("#reportDiv").css("display","none");
	        var optionValue = event.target.value;
	        if(optionValue == "PendBranch" || optionValue=="AllBranch")
	                {
	                $("#fieldRow").css("display","table-row");
	                $("#userSearchField").css("display","none");
	                $("#userSearchValue").css("display","none");
	                }
	        else if(optionValue=="Userwise")
	        {
	                $("#fieldRow").css("display","table-row");
	                $("#userSearchField").css("display","table-cell");
	                $("#userSearchValue").css("display","table-cell");

	        }
	        else{
	                $("#fieldRow").css("display","none");
	        }
	} */
	</script>
</head>
<body onload="getPicklistValueRegionCode();">
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
	<div class="test border border-dark" style="padding-bottom:.75rem;">
		<nav class="cbi text-white"> 
			<div class="row">
				<div class="col-lg-2">
                  &nbsp;&nbsp; <a href="./VoucherArchival.jsp?userDbId=<%=OD_UID%>" style="font-size:22px;color:white;font-style:bold;">Back</a> 
                    <!-- <button name="Back" value="Back" onclick="history.back()">Back</button> -->
                </div>
    			<div class="col-lg-7">
				<h6 align="center" class="container-fluid" id="formModalLabel" style="font-size:25px;padding-left:10rem;">Audit Report</h6>
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
		<td> Region Code: </td> 
		<td> <select id="Field2" name="Field2" style="width:250px;height:40px;border-color:#ced4da;font-size:1rem;;
		padding:.250rem .54rem;color:#212529;border-radius:.25rem;"></select>
		<!-- <input type="text" class="form-control" id="Field2" name="Field2" style="width: 250px;margin-right:1rem;"> --> </td>
		<td><input type="button" style="align:center;margin:0 0 1rem 1.5rem;background-color:#176FC1;font-style:bold;
		font-weight:750;letter-spacing:1.5px;width:15rem" value="Generate Report" class="btn btn-primary" id="RepSearchBtn" name="RepSearchBtn"/><!-- Search</button> --></td> 
		</tr>
		<!-- onclick="SearchDoc_New();"  -->
		</table>
		
		</div>
	  </div>
	  <div id='reportDiv' style="display:none;">
	  <div class="container col-md-12">
	   
	   <div style="padding-top:.70rem">
	   <h3 align='center'>Voucher Audit Report for RO: <label id='RegionNo'></label> ,Date: <label id='Voucher_Date'></label></h3>
	  <%-- <table class="table table-bordered" style="margin-top:1rem;font-style:bold;font-size:17px;border:1px solid black;">
	  <tr>
	  	<td> S.No </td> 
		<td> Branch Code </td> 
		<td> Verification Date </td> 
		<td> Verified By</td>
		<td> Total EVVR Records</td>
		<td> Voucher Ok Records</td>
		<td> Rectified Records</td>
		<td> Audit Status </td>
		
		</tr>
		
		<% 
		try {
	    	String sql = "Select * from usr_0_VOUCHERREPORT";
	        System.out.println("AuditReport.jsp getReportData()====>"+sql); 
	        Connection con = getData.connectNew();
	        Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			String emptyString1 = "";
			String emptyString2 = "";
	        while (resultSet.next()) { %>
	        	<tr>
	        			<td><%= resultSet.getString("BRANCH_CODE") %></td> 
	        			<% if(resultSet.getString("VOUCHER_DATE").contains("00:00:00")){
		        			/*String emptyString= resultSet.getString(fields[u]);
		        			CustomLogger.printOut("emptyString===>"+emptyString);*/
		        		    emptyString1 = resultSet.getString("VOUCHER_DATE");
		        			emptyString1 = emptyString1.replaceAll("00:00:00", "");
		        		}	%>
						<td><%= emptyString1 %></td> 
						<% if(resultSet.getString("VERIFICATION_DATE").contains("00:00:00")){
		        			/*String emptyString= resultSet.getString(fields[u]);
		        			CustomLogger.printOut("emptyString===>"+emptyString);*/
		        			emptyString2 = resultSet.getString("VERIFICATION_DATE");
		        			emptyString2 = emptyString2.replaceAll("00:00:00", "");
		        		}	%>
						<td><%= emptyString2 %></td> 
						<td><%= resultSet.getString("VERIFIED_BY") %></td>
						<td><%= resultSet.getString("TOTAL_VOUCHER_DISTINCT") %></td>
						<td><%= resultSet.getString("TOTAL_VOUCHER") %></td>
						<td><%= resultSet.getString("VOUCHEROK_COUNT") %></td> 
						<td><%= resultSet.getString("RECTIFIED_COUNT") %></td>
						<td><%= resultSet.getString("TOTAL_IMAGES") %></td>
						
	        	 </tr>
	        <% }
	        resultSet.close();
	        statement.close();	        
	        getData.disconnectNew(con);
		}
			catch(SQLException ex) {
				System.out.println("Error while loading report data====>"+ex.toString());
			}
		%>
		<tr>
			
		</tr>
		</table> --%>
		<input type="hidden" id="OD_UID" name="OD_UID" value="">
		
		</div>
	</div>
	</div>
		</div>
	</div>
	</div>
</form>
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
else{ %>
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
else{ %>
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