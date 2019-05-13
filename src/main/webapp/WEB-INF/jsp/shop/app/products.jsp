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
<jsp:include page="../main/navbar.jsp"/>
	<div class="container container-fluid bg-dark">
	<c:forEach var="product" items="${beanList}">
		<div class="card">
			<div class="card-header">${product.name}</div>
			<div class="card-body">
				<div class="row">
					<div class="col">
						<img alt="dummy" style="background-color:green;" src="https://cdnjs.cloudflare.com/ajax/libs/octicons/8.5.0/svg/package.svg" height="200px" width="200px" >
					</div>
					<div class="col">
						${product.description}
					</div>
				</div>
			</div>
			<div class="card-footer">
            	<p><button type="button" onclick="window.location.href='/shop/product/${product.id}'" class="btn btn-primary btn-block">View</button></p>
			</div>
		</div>
		<br>
	</c:forEach>
	</div>
</body>

</html>