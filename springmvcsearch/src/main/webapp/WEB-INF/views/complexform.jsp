<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- taglib form for writing bindresult error -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <title>Search Page</title>
  </head>
  <body>
 
    <div class="container-fluid bg-secondary py-5">
    	<div class="card mx-auto" style="width: 60%">
    		<div class="card-body py-5">
    			<h3 class="text-center" style="text-transform: uppercase;">Profile</h3>
    			<div class="alert alert-danger" role="alert">
					  <form:errors path="student.*"  />
				</div>

    			<form action="handleform" method="post" class="mt-3">
    				<div class="form-group">
    					<label for="name">Your name</label>
    					<input type="text" name="name" class="form-control" placeholder="enter your name" >
    				</div>
    				
    				<div class="form-group mt-3">
    					<label for="id">Your ID</label>
    					<input type="text" name="id" class="form-control" placeholder="enter your id" >
    				</div>
    				
    				<div class="form-group mt-3">
    					<label for="dob">Date of Birth</label>
    					<input type="text" name="date" class="form-control" placeholder="dd//mm//yyyy" >
    				</div>
    				
    				<div class="form-group mt-3">
    					<label for="selectedcourses">Select course</label>
    					<select name="courses" id="selectedcourses" class="form-control" multiple="multiple">
    						<option>Java</option>
    						<option>Python</option>
    						<option>Data Structute with Java</option>
    						<option>C++</option>
    						<option>Django</option>
    						<option>Spring</option>
    					</select>
    				</div>
    				
    				<div class="form-group mt-3">
    					<label>Select Gender</label></br>
    					<input type="radio" name="gender" id="g1" value="M" >
    					<label for="g1">Male</label>
    					<input type="radio" name="gender"id="g2" value="F" >
    					<label for="g2">Female</label>
    				</div>
    				
    				<div class="form-group mt-3">
    					<label for="student">Select course</label>
    					<select name="type" id="student" class="form-control">
    						<option value="oldstudent">Old Student</option>
    						<option value="normalstudent">Normal Student</option>
    					</select>
    				</div>
    				
    				<div class="card mt-3">
    					<div class="card-body">
    						<p>Address</p>
    						<div class="form-group mt-3">
	    						<input type="text" name="address.city" class="form-control" placeholder="city" >
	    					</div>
	    					<div class="form-group mt-3">
	    						<input type="number" name="address.pin" class="form-control" placeholder="pincode" >
	    					</div>
    						
    					</div>
    				</div>
    				
    				<div class="container text-center mt-3">
    					<button class="btn btn-outline-light bg-primary">Submit</button>
    				</div>
    			</form>
    		</div>
    	</div>
    </div>

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

  </body>
</html>