<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page
	import="java.util.*"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="styles/CBI.css">
<link rel="stylesheet" href="scripts/bootstrap.min.css">
<script type="text/javascript" src="scripts/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="scripts/CommonFunctions.js"></script>
<script type="text/javascript" src="scripts/ViewFunctions.js"></script>
<script type="text/javascript">
function validateForm() {
	var username = document.getElementById("username").value;
	var userpassword = document.getElementById("userpassword").value;
	let charCodeArr = [];
	if(username == "") {
		alert("Please enter UserName");
		return false;
	}
	else if(userpassword == "") {
		alert("Please enter Password");
		return false;
	}
	else{
		/* var val = document.getElementById("userpassword").value;
        for(var i = 0; i < val.length; i++) {
        	let code = val.charCodeAt(i); 
            charCodeArr.push(code)
        }
        document.getElementById("userpassword").value = charCodeArr;  */ 
        /* var pass = document.getElementById("userpassword").value; 
        alert("pass===>"+pass); */
		var encodedData = btoa(userpassword);
        //alert("userpassword===>"+encodedData);
        document.getElementById("userpassword").value = encodedData; 
       
	}
	
}
</script>
<title>Login</title>
</head>
<body>

	<div class="header">
        <img  class="logo" src="images/Central-Bank-of-India-logo.png"  >
        
       
    </div>
     <div class="line1"></div>

    <div class="container" style="margin-top:3rem;">
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12">
            <form onsubmit="validateForm();" action="LoginServlet" name="loginForm" id="loginForm" method="post" autocomplete="off">
                <label for="subtitle" class="sub"><i><b style="font-size:2rem;">Voucher Audit System</b></i></label>
                <div class="row ">
                        <div class="col-lg-5 col-md-12" style="padding-left: 0px;">
                            <img src="images/CBIBank.png" alt="Logo Image" height="300px" width="400px">
                        </div>

                        <div class="col-lg-7 col-md-12 col-sm-11">
                            
                            <div class="card col-md-11">
                            <div class="row" style="margin-top:2.5rem; margin-left:1.5rem;">
                                <div class="col-lg-4">
                                    <label for="username"><i>User Name:</i></label><br><br>
                                    <label for="password" class="mt-2"><i>Password:</i></label>
                                    <!-- <div class="float-end" >
                                        <input type="checkbox" name="checkbox" >
                                    </div> -->
                                
                                </div>
                                <div class="col-lg-8" >
                                    
                                    <input type="text" id="username" name="username"><br><br>
                                    <input type="password" id="userpassword" name="userpassword"><br><br>
                                    <button type="submit" class="btn btn-primary"><b>Login</b></button> 
                                    <!-- onclick="validateForm();" -->
                                   <!--  <div class="rem">
                                        <label for="rem"><i>Remember User Name</i></label>
                                    </div> -->

                                </div>
                            </div>
                            </div>
                        </div>
                </div>
                </form>
            </div>
        </div>
    </div>
	<br>
    <footer class="footer"><i>Copyright &#169; 2022 Newgen Software Technologies Limited All Rights Reserved.</i></footer>
	<!-- <section class="vh-100">
  <div class="container-fluid h-custom">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-md-6" style="padding-top:20px;">
        <img src="images/Central-Bank-of-India-Logo.jpg"
          class="img-fluid" alt="Sample image">
      </div>
      <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
        <form action="LoginServlet" name="loginForm" id="loginForm">
          
          <div class="form-outline mb-4">
            <input type="text" id="username" name="username" class="form-control form-control-lg"
              placeholder="Enter User Name" />
            <label class="form-label" for="form3Example3">User Name</label>
          </div>

          <div class="form-outline mb-3">
            <input type="password" id="userpassword" name="userpassword" class="form-control form-control-lg"
              placeholder="Enter password" />
            <label class="form-label" for="form3Example4">Password</label>
          </div>


          <div class="text-center text-lg-start mt-4 pt-2">
            <button type="submit" class="btn btn-primary btn-lg"
              style="padding-left: 2.5rem; padding-right: 2.5rem;">Login</button> onclick="LoginServlet();"
          </div>

        </form>
      </div>
    </div>
  </div>
</section> -->
</body>
</html>