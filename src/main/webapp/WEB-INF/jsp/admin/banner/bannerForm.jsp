<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="../main/head.jsp"/>
</head>
<body ng-app="angular" ng-controller="controller" style="background-color:#263238">
<jsp:include page="../main/navbar.jsp"/>
<br>
<div class="container border bg-dark">
<br>
	<div class="card text-white text-left" style="background-color:#a1887f">
		<div class="card-body">
			<h2>
	    	<c:choose>
				<c:when test="${beanInfo['new']}">Create Banner</c:when>
				<c:otherwise>Update Banner</c:otherwise>
			</c:choose>
			</h2>
	    </div>
	</div>
	<hr>
	<spring:url value="${actionUrl}" var="actionUrl"/>
	<form:form method="post" modelAttribute="beanInfo" action="${actionUrl}">
		<form:hidden path="id"/>
		<spring:bind path="name">
			<div class="form-group">
				<label for="name" class="text-light">Name</label>
				<form:input path="name" type="text" class="form-control" autocomplete="off"/>
				<form:errors path="name" class="invalid-feedback d-block"/>
			</div>
		</spring:bind>
		<spring:bind path="description">
			<div class="form-group">
				<label for="description" class="text-light">Description</label>
				<form:input path="description" type="text" class="form-control" autocomplete="off"/>
				<form:errors path="description" class="invalid-feedback d-block"/>
			</div>
		</spring:bind>
		<spring:bind path="link">
			<div class="form-group">
				<label for="link" class="text-light">Link</label>
				<form:input path="link" type="text" class="form-control" autocomplete="off"/>
				<form:errors path="link" class="invalid-feedback d-block"/>
			</div>
		</spring:bind>	
		
		<spring:bind path="status">
			<div class="form-group">
				<label for="status" class="text-light">Status</label>
				<form:select path="status" class="form-control">
					<form:options items = "${statusList}"/>
				</form:select>
				<form:errors path="status" class="invalid-feedback d-block"/>				
			</div>
		</spring:bind>			
		<hr>
		<div class="row">
			<div class="col">
		  		<c:choose>
		  			<c:when test="${beanInfo['new']}">
		  				<button type="submit" class="btn btn-secondary btn-block" style="background-color:#00695c">Add</button>
		  			</c:when>
		  			<c:otherwise>
		  				<button type="submit" class="btn btn-secondary btn-block" style="background-color:#00695c">Update</button>
		  			</c:otherwise>
		  		</c:choose>			
			</div>
			<div class="col">
				<spring:url value="/admin/banners" var="home"/>
				<button type="button" class="btn btn-dark btn-block" style="background-color:#bf360c" onclick="window.location.href='${home}'">Back</button>
			</div>
		</div>
		</form:form>
		<hr>
</div>
</html>