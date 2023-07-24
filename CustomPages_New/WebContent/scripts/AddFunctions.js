function onload() {
	window.moveTo(50, 50);
	window.resizeTo(600, 700);
}

function ClearFields() {
	window.FinnoneUploadDoc.reset();
}

function validation() {
	if (document.getElementById("Details - ReceiptNoId").value == null
			|| document.getElementById("Details - ReceiptNoId").value == "") {
		document.getElementById("Details - ReceiptNoId").value = '-';
	}

	if (isAlphaNumeric(document.getElementById("Details - ReceiptNoId").value) == false) {
		alert('ReceiptNo should Only Alphabets,Number,Hyphen,Underscore and dot ');
		return false;
	}
	if (isAlphaNumeric(document.getElementById("UserNameId").value) == false) {
		alert('UserName should Only Alphabets,Number,Hyphen,Underscore and dot ');
		return false;
	}
	if (isAlphaNumeric(document.getElementById("LeadIdId").value) == false) {
		alert('LeadId should Only Alphabets,Number,Hyphen,Underscore and dot ');
		return false;
	}
	if (isAlphaNumeric(document.getElementById("FinoneDocIdId").value) == false) {
		alert('FinoneDocId should Only Alphabets,Number,Hyphen,Underscore and dot ');
		return false;
	}
	if (isAlphaNumeric(document.getElementById("WIIdId").value) == false) {
		alert('WIID should Only Alphabets,Number,Hyphen,Underscore and dot ');
		return false;
	}

	if (document.getElementById("fileBrowseDocumentid").value != null
			&& document.getElementById("fileBrowseDocumentid").value != "")
		return true;
	else {
		alert("Please Select Document to Attach");
		return false;
	}

}

function UploadDocument() {
	if (validation()) {
		var form = document.getElementById('FinnoneUploadDoc');
		var formdata = new FormData(form);

		$.ajax({
			url : "AddDocument",
			data : formdata,
			processData : false,
			contentType : false,
			type : "post",

			success : function(data) {
				alert(data);
				document.getElementById('Details - ReceiptNoId').value = '';
				document.getElementById('fileBrowseDocumentid').value = '';
			},
			error : function(data) {
				alert("Error occured while adding document");

			}

		});

	}

}