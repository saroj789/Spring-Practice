<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <%@include file="./base.jsp" %>
</head>
<body>

	<div class="container mt-3">
		<div class="row">
			<div class="col-md-12">
				<h2 class="text-center mb-3">Welcome to Product CRUD App</h2>
				<table class="table">
				  <thead class="thead-dark">
				    <tr>
				      <th scope="col">ID</th>
				      <th scope="col">Product Name</th>
				      <th scope="col">Description</th>
				      <th scope="col">Price</th>
				      <th scope="col">Action</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach items="${products}" var="p">
					    <tr>
					      <th scope="row">${p.id}</th>
					      <td>${p.name}</td>
					      <td>${p.description}</td>
					      <td><b> &#x20B9; ${p.price}</b></td>
					      <td>
					      	<a href="delete/${p.id}"><i class="fa fa-trash text-danger"></i></a>
					      	<a href="edit/${p.id}"><i class="far fa-edit px-3"></i></a>
					      </td>
					    </tr>
					</c:forEach>
				  </tbody>
				</table>
				
				<div class="container text-center">
			      <a href="${pageContext.request.contextPath}/add-product"
			      	  class="btn btn-outline-success">Add Product</a>
		     	 </div>
			</div>
		</div>
	</div>

</body>
</html>