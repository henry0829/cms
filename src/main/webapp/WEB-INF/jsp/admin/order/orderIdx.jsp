<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
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
		<button class="btn btn-dark" style="background-color:#01579b" onclick="window.location.href='orderAdd'">Add Order</button>
	</div>
	<hr>
	<div class="row">
		<table class="table table-dark table-hover">
			<thead>
				<tr>
					<th>Product Id</th>
					<th>User Id</th>
					<th>Status</th>
					<th>Created Date</th>
					<th>Modified Date</th>
					<th>Action</th>
				</tr>
			</thead>
			<c:forEach var="order" items="${beanList}">
			<tbody>
				<tr>
					<td>${order.productid }</td>
					<td>${order.userid }</td>
					<td>${order.status }</td>
					<td>${order.createddate }</td>
					<td>${order.modifieddate }</td>
					<td>
						<div class="row">
							<div class="col">
								<button class="btn btn-dark btn-block" style="background-color:#00695c" onclick="window.location.href='/admin/orderEdit/${order.id}'">Update</button>
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
	$scope.title = "Order Management";
});
</script>
</html>