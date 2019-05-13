<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="../main/head.jsp"></jsp:include>
</head>
<body ng-app="angular" ng-controller="controller" style="background-color:#263238">
<jsp:include page="../main/navbar.jsp"/>
<div class="alert alert-dark">
	<h3>{{title}}</h3>
</div>
<div class="container">
	<c:if test="${not empty msgInfo}">
		<div class="alert alert-${css} alert-dismissible fade show">
			<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
			<strong>Message: </strong>${msgInfo}
		</div>
	</c:if>
	<div class="row">
		<button class="btn btn-dark" style="background-color:#01579b" onclick="window.location.href='productAdd'">Add Product</button>
	</div>
	<hr>
	<div class="row">
		<table class="table table-dark table-hover">
			<thead>
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Category</th>
					<th>Price</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>
			<c:forEach var="product" items="${beanList}">
			<tbody>
				<tr>
					<td>${product.name}</td>
					<td>${product.description}</td>
					<td>${product.category}</td>
					<td>${product.price}</td>
					<td>${product.status}</td>
					<td>
						<div class="row">
							<div class="col">
								<button class="btn btn-dark btn-block" style="background-color:#00695c" onclick="window.location.href='/admin/productEdit/${product.id}'">Update</button>
							</div>
							<div class="col">
								<button class="btn btn-dark btn-block" style="background-color:#bf360c" onclick="window.location.href=''">Delete</button>
							</div>
						</div>
					</td>
				</tr>
			</tbody>
			</c:forEach>
		</table>
	</div>
</div>
</body>
<script>
var app = angular.module('angular',[]);
app.controller('controller', function($scope){
	$scope.title = "Product Management";
});
</script>
</html>