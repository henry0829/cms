<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/admin/main/head.jsp"></jsp:include>
<title>Admin Login</title>
</head>
<body ng-app="angular" ng-controller="controller" style="background-color:#263238">

<div class="alert alert-dark">
	<h3>Admin Login</h3>
</div>
<div class="container">
	<c:if test="${not empty msgInfo}">
		<div class="alert alert-${css} alert-dismissible fade show">
			<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
			<strong>Message: </strong>${msgInfo}
		</div>
	</c:if>	
	<form:form method="post" modelAttribute="loginUser" action="/admin">
		<spring:bind path="username">
			<div class="form-group">
				<label for="username" class="text-light">Username</label>
				<form:input path="username" type="text" class="form-control" autocomplete="off"/>
				<form:errors path="username" class="invalid-feedback d-block"/>
			</div>
		</spring:bind>
		<spring:bind path="password">
			<div class="form-group">
				<label for="password" class="text-light">Password</label>
				<form:input path="password" type="password" class="form-control" autocomplete="off"/>
				<form:errors path="password" class="invalid-feedback d-block"/>
			</div>
		</spring:bind>
		<button type="submit" class="btn btn-dark btn-block" style="background-color:#00695c">Login</button>
		</form:form>
	</div>
</body>
</html>