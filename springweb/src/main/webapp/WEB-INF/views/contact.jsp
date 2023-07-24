<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  
  <link href="src/main/webapp/WEB-INF/views/fontawesome.css" rel="stylesheet" >
  
  <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
   -->
  <title>Contact</title>
  
  <style type="text/css">
	  @media (min-width: 1025px) {
		.h-custom {
			height: 100vh !important;
			}
		}
  </style>
</head>
 
<body>

	<div>
		<section class="vh-100" style="background-color: #eee;">
		  <div class="container h-100">
		    <div class="row d-flex justify-content-center align-items-center h-100">
		      <div class="col-lg-12 col-xl-11">
		        <div class="card text-black" style="border-radius: 25px;">
		          <div class="card-body p-md-5">
		            <div class="row justify-content-center">
		              <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
		
		                <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign up</p>
		
		                <form action="processform" method="post" class="mx-1 mx-md-4">
		
		                  <div class="d-flex flex-row align-items-center mb-4">
		                    <i class="fas fa-user fa-lg me-3 fa-fw"></i>
		                    <div class="form-outline flex-fill mb-0">
		                      <input type="text" id="userName" name="userName" class="form-control" />
		                      <label class="form-label" for="userName">Your Name</label>
		                    </div>
		                  </div>
		
		                  <div class="d-flex flex-row align-items-center mb-4">
		                    <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
		                    <div class="form-outline flex-fill mb-0">
		                      <input type="email" id="email" name="email" class="form-control" />
		                      <label class="form-label" for="email">Your Email</label>
		                    </div>
		                  </div>
		
		                  <div class="d-flex flex-row align-items-center mb-4">
		                    <i class="fas fa-lock fa-lg me-3 fa-fw"></i>
		                    <div class="form-outline flex-fill mb-0">
		                      <input type="password" id="password" name="password" class="form-control" />
		                      <label class="form-label" for="password">Password</label>
		                    </div>
		                  </div>
		
		                 
		
		                  <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
		                    <button type="submit" class="btn btn-primary btn-lg">Register</button>
		                  </div>
		
		                </form>
		
		              </div>
		              <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">
		
		                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
		                  class="img-fluid" alt="Sample image">
		
		              </div>
		            </div>
		          </div>
		        </div>
		      </div>
		    </div>
		  </div>
		</section>
	</div>


  <!-- Option 1: Bootstrap Bundle with Popper -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>


 </body>
</html>