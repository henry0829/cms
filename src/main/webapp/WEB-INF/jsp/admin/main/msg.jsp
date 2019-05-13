<c:if test="${not empty msg || false}">
	<div class="alert alert-${css} alert-dismissible" role="alert">
	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
	<span aria-hidden="true">x</span>
	<strong>${msg}</strong>
	</button>
	</div>
</c:if>