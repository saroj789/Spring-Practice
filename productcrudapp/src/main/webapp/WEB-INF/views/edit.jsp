<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <%@include file="./base.jsp" %>
</head>
<body>

	<div class="container mt-5">
		<div class="col-md-6 offset-md-3">
			<h5 class="text-center mb-3" >Update product detail</h5>
			
		    <form action="${pageContext.request.contextPath}/handle-product" method="POST">
		    <input type="hidden" class="form-control" value="${product.id }" id="productPrice" name="id" placeholder="Enter Product Price" required>
		      
		      <div class="form-group">
		        <label for="productName">Product Name</label>
		        <input type="text" class="form-control" value="${product.name }" id="productName" name="name" placeholder="Enter Product Name" required>
		      </div>
		      <div class="form-group">
		        <label for="productDescription">Product Description</label>
		        <textarea class="form-control" id="productDescription" name="description" rows="5" placeholder="Enter Product Description" required>${product.description }</textarea>
		      </div>
		      <div class="form-group">
		        <label for="productPrice">Product Price</label>
		        <input type="text" class="form-control" value="${product.price }" id="productPrice" name="price" placeholder="Enter Product Price" required>
		      </div>
		      <div class="container text-center">
			      <button type="submit" class="btn btn-primary">Update</button>
			      <!-- <button type="button" class="btn btn-secondary" onclick="goBack()">Back</button> -->
			      <a href="${pageContext.request.contextPath}/"
			      	  class="btn btn-outline-warning">Back</a>
		      </div>
		    </form>
		</div>
  	</div>

</body>
</html>