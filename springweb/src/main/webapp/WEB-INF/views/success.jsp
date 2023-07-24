<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
  <%@page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>User details</h3>
	<h4>Name	 : ${userName}</h2>
	<h4>email 	 :  <%= request.getAttribute("userEmail") %> </h2>

</body>
</html>