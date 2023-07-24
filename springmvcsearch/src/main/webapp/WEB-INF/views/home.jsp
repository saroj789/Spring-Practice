<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <!-- custom css -->
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />"  >
    
    <script src="<c:url value="/resources/js/script.js" />"></script>
    
    <title>Search Page</title>
  </head>
  <body>
 
    <div class="container">
    	<div class="card mx-auto mt-5 w-50 bg-primary">
    		<div class="card-body py-5">
    			<h3 class="text-center" style="text-transform: uppercase;">MY SEARCH</h3>
    			<form action="search" class="mt-3">
    				<div class="form-group">
    					<input type="text" name="querybox" class="form-control" placeholder="enter your keyword" >
    				</div>
    				<div class="container text-center mt-3">
    					<button class="btn btn-outline-light">Search</button>
    				</div>
    			</form>
    		</div>
    	</div>
    </div>

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

  </body>
</html>