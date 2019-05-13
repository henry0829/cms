<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../main/head.jsp"/>
</head>
<style>
div.i.fa { display: none; }
div.img { display: block; }

div.show-icon i.fa { display: inline-block; }
div.show-icon img { display: none; }
</style>
<body class="bg-dark">
	<div class="container container-fluid bg-dark">
	<br>
		<div class="row">
			<h5 class="text-primary">Product Information</h5>				
			<table class="table table-light table-hover">
				<tbody>
					<tr class="bg-secondary">
						<td class="tbl-title"></td>
						<td class="tbl-content">
							<img alt="dummy" style="background-color:green;" src="https://cdnjs.cloudflare.com/ajax/libs/octicons/8.5.0/svg/package.svg" height="200px" width="200px" >
						</td>
					</tr>
					<tr>
						<td class="tbl-title">Name</td>
						<td class="tbl-content">${product.name}</td>
					</tr>
					<tr>
						<td class="tbl-title">Category</td>
						<td class="tbl-content">${product.category}</td>
					</tr>
					<tr>
						<td class="tbl-title">Description</td>
						<td class="tbl-content">${product.description}</td>
					</tr>
					<tr>
						<td class="tbl-title">Price</td>
						<td class="tbl-content">${product.price}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="row">
			<button class="btn btn-primary btn-block">Add to Cart</button>
		</div>
		<hr>
		<div class="row">
			<spring:url value="/shop/products" var="home"/>
			<button class="btn btn-primary btn-block" onclick="window.location.href='${home}'">Back to Home</button>
		</div>
	</div>
</body>
</html>