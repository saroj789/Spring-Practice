function onload() {
	window.moveTo(50, 50);
	window.resizeTo(600, 700);
	if (document.getElementById("doclength").value == "0") {
		document.getElementById("DocView_Table").style.display = 'none';
	} else {
		document.getElementById("displayTable_lead").style.display = 'none';
	}
	document.getElementById("displayTable").style.display = 'none';

}

function FunClose() {
	var userDbId = document.getElementById("OD_UID").value;
	//alert("userDbId===>"+userDbId);
	$.ajax({
		url : "DisconnectCabinet",
		data : {
			userDbId : userDbId
		},
		type : "post",
		dataType:'json',
		success : function(data) {
			window.location= "./login.jsp";
		},
		error : function(data) {
			//alert("Error occured while closing user session.");
			window.location= "./login.jsp";
			//return false;
		}
	});

	//window.close();
}

function HandleClick(param) {

	if (param.value == "Details") {
		document.getElementById("Details - ReceiptNoId").disabled = false;
		document.getElementById("Search2").disabled = false;
		document.getElementById("Details - ReceiptNoId").value = "";
		deleteRow('DocView_Table');
		document.getElementById("DocView_Table").style.display = 'none';
		document.getElementById("displayTable").style.display = 'none';
		document.getElementById("displayTable_lead").style.display = 'none';
	}
}

function ClearFields() {
	window.FinnoneViewDoc.reset();
	deleteRow('DocView_Table');
	document.getElementById("txtDetailsId").disabled = true;
	document.getElementById("Search2").disabled = true;
	document.getElementById("DocView_Table").style.display = 'none';
	document.getElementById("displayTable").style.display = 'none';
	document.getElementById("displayTable_lead").style.display = 'none';
}

function deleteRow(tableID) {
	try {
		var table = document.getElementById(tableID);
		var rowCount = table.rows.length;

		for (var i = 1; i < rowCount; i++) {
			table.deleteRow(i);
			rowCount--;
			i--;
		}
	} catch (e) {
		alert(e);
	}
}

function Search() {

	$.ajax({
		url : "SearchDocument",
		data : encodeURI(decodeURIComponent($("#FinnoneViewDoc").serialize())),
		type : "post",
		success : function(data) {
			var obj = JSON.parse(data);
			var status = obj.Status[0];
			var lowerstatus = status.toLowerCase();
			if (lowerstatus == "fail") {
				alert(obj.Message[0]);
				return false;
			} else {
				var doclist = obj.DocList[0];
				gridDisplay(doclist);
			}
		},
		error : function(data) {
			alert("Error occured while searching documents.");
			return false;
		}
	});

}

function gridDisplay(doclist) {
	deleteRow('DocView_Table');
	if (doclist.length == 0) {
		document.getElementById("displayTable").style.display = 'inline';
		document.getElementById("DocView_Table").style.display = 'none';
		document.getElementById("displayTable_lead").style.display = 'none';
		return false;
	} else {
		
		document.getElementById("DocView_Table").style.display = 'inline';
		
		for (var i = 0; i < doclist.length; i++) {
			var tableObj=document.getElementById("DocView_Table");
			var tbllength = document.getElementById("DocView_Table").rows.length;
			var tableRow = tableObj.insertRow(-1);
			var tableCell = tableRow.insertCell(0);
			tableCell.width = 60;
			tableCell.className = "EWTableContents";
			var serialNo = tbllength;
			tableCell.innerHTML = serialNo;

			tableCell = tableRow.insertCell(1);
			tableCell.width = 150;
			tableCell.className = "EWTableContents";
			tableCell.innerHTML = "<a href='javascript:callWebApi(\""
					+ doclist[i].documentIndex + "\");'>"
					+ doclist[i].documentName + "("
					+ doclist[i].createdDatetime + ")</a>";

			tableCell = tableRow.insertCell(2);
			tableCell.width = 150;
			tableCell.className = "EWTableContents";
			tableCell.innerHTML = doclist[i].receiptNo;

			tableCell = tableRow.insertCell(3);
			tableCell.width = 150;
			tableCell.className = "EWTableContents";
			tableCell.innerHTML = doclist[i].tag;

			tableCell = tableRow.insertCell(4);
			tableCell.width = 150;
			tableCell.className = "EWTableContents";
			var t_name = "Download_" + i;
			tableCell.innerHTML = "<input type='button' name='" + t_name
					+ "' value='Download' onclick='DownloadDocument(\""
					+ doclist[i].documentIndex + "\");'>";

		}
	}
}

function DownloadDocument(docId) {
	document.getElementById("docIdToDownload").value = docId;
	document.getElementById('FinnoneViewDoc').action = 'DownloadDocument';
	document.getElementById('FinnoneViewDoc').target = 'newWin';
	document.getElementById('FinnoneViewDoc').submit();

}

function callWebApi(docId) {
	document.getElementById("DocumentId").value = docId;
	var Application = document.getElementById("Application").value;
	var cabinetName = document.getElementById("cabinetName").value;
	var sessionIndexSet = document.getElementById("sessionIndexSet").value;
	var S = document.getElementById("S").value;
	var DataClassName = document.getElementById("DataClassName").value;
	var Voucher_Date = document.getElementById("Field1").value;
	var Branch_Code = document.getElementById("Field2").value;
	//alert(Voucher_Date);
	//alert(Branch_Code);
	var date1 = changeDateFormat(Voucher_Date);
	//var date = new Date(Voucher_Date).toLocalDateString();
	//alert("date===>"+date1);
	//var url = 'https://10.5.6.129:8443/omnidocs/WebApiRequestRedirection?Application='+Application+'&cabinetName='+cabinetName+'&sessionIndexSet=false&DataClassName='+DataClassName+'&DC.Branch_Code='+Branch_Code+'&DC.Voucher_Date='+date1+'&enableDCInfo=true&S=S';
	var url = 'https://dmsuat.centralbank.co.in/omnidocs/WebApiRequestRedirection?Application='+Application+'&cabinetName='+cabinetName+'&sessionIndexSet=false&DataClassName='+DataClassName+'&DC.Branch_Code='+Branch_Code+'&DC.Voucher_Date='+date1+'&enableDCInfo=true&S=S';
	//var url = 'https://dmsuat.centralbank.co.in/omnidocs/WebApiRequestRedirection?Application='+Application+'&cabinetName='+cabinetName+'&sessionIndexSet=false&DataClassName='+DataClassName+'&DC.Branch_Code='+Branch_Code+'&DC.Voucher_Date='+date1+'&enableDCInfo=true&S=S';
	//var url = 'http://127.0.0.1:8082/omnidocs/WebApiRequestRedirection?Application=Test_API&cabinetName=axistest1&sessionIndexSet=false&DataClassName=TestValidate&enableDCInfo=true&DC.brnchCode=1234&DC.Voucher_Date=2022-08-09&S=S';
	//alert(Application,cabinetName,sessionIndexSet,S,url);
	document.getElementById("demo").style.display = 'block'; 
	//document.getElementById("DocImage").src = 'http://127.0.0.1:8082/omnidocs/WebApiRequestRedirection?Application=Test_API&cabinetName=axistest1&sessionIndexSet=false&DocumentId=27&S=S';
	document.getElementById("DocImage").src = url;
	//document.getElementById('WebApiForm').submit();
}
function changeDateFormat(inputDate){  // expects Y-m-d
    var splitDate = inputDate.split('-');
    if(splitDate.count == 0){
        return null;
    }

    var year = splitDate[0];
    var month = splitDate[1];
    var day = splitDate[2]; 

    return day + '/' + month + '/' + year;
}
function SearchDoc() {

	$.ajax({
		url : "SearchandViewDoc",
		data : encodeURI(decodeURIComponent($("#FinnoneViewDoc").serialize())),
		type : "post",
		dataType:'json',
		/*data : {
			voucherDate : voucherDate,
			briedDescription : briedDescription
		},*/
		success : function(data) {
			var obj = JSON.parse(data);
			var status = obj.Status[0];
			var lowerstatus = status.toLowerCase();
			if (lowerstatus == "fail") {
				alert(obj.Message[0]);
				return false;
			} else {
				var doclist = obj.DocList[0];
				gridDisplay(doclist);
			}
		},
		error : function(data) {
			alert("Error occured while searching documents.");
			return false;
		}
	});

}

function SearchDoc_New() {
	/* var voucherDate = $("#voucherDate").val();
	var briedDescription = $("#briedDescription").val(); */
	$.ajax({
		url : "SearchandViewDoc",
		type : "post",
		dataType:'json',
		success : function(data) {
				var doclist = data.DocList;
				alert("doclist===>"+doclist);
				gridDisplay(doclist);
			
		},
		error : function(data) {
			alert("Error occured while searching documents.");
			return false;
		}
	});

}
/*function getPicklistValue() {
	var OD_UID = '<%=OD_UID%>';
	alert("OD_UID====>"+OD_UID);
	$.ajax({
		url : "GetPicklistData",
		type : "post",
		dataType:'json',
		data: "{}",
		success : function(data) {
				var doclist = data.DocList;
				alert("doclist===>"+doclist);
				gridDisplay(doclist);
				//alert("data====>"+data);
				var s = '<option value="-1">Select a Branch</option>';  
	               for (var i = 0; i < data.length; i++) {  
	                   s += '<option value="' + data[i].BranchCode + '">' + data[i].BranchCode + '-->' + data[i].BranchName + '</option>';  
	               }  
	               $("#Field2").html(s);
			
		},
		error : function(data) {
			alert("Error while loading Branch Codes.");
			return false;
		}
	});
	
}
*/
function LoginServlet() {
	alert("Inside LoginServlet function()");
	var username = $("#username").val();
	var password = $("#userpassword").val();
	alert(password  +"---"+ username);
	$.ajax({
		url : "LoginServlet",
		type : "post",
		dataType:'json',
		data : {
			username : username,
			password : password
		},
		success : function(data) {
			var userDbID = data.USERDBID;
			alert("USERDBID===>"+userDbID);
			if(userDbID != "") {
			return true;
			}
			else {
					alert("Error in Success!!");
					return false;
				}
		},
		error : function(data) {
			alert("Error while Connecting Cabinet!!");
			return false;
		}
	});
}