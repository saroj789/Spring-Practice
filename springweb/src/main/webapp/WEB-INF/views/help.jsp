<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@page isELIgnored="false"%> 
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Help Page</title>
</head>
<body>
<h2>Help Page</h2>
<p> Contact to <%=request.getAttribute("owner") %></p>

<p> By JSTL, owner is ${owner}</p>
</body>
</html>