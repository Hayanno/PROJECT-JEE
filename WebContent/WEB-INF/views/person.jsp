<%@ include file="/WEB-INF/views/assets/header.jsp"%>
<%@ include file="/WEB-INF/views/assets/navbar.jsp"%>

<h2 class="form-signin-heading text-center">Personne</h2>

<div id="person-view" class="panel panel-default">
	<div class="panel-heading">
	  	<h3 class="panel-title"><c:out value="${person.getFirstname()}"/> <c:out value="${person.getLastname()}"/></h3>
	</div>
	<div class="panel-body">
		<sec:authorize access="hasRole('ROLE_USER')">
			<div class="col-xs-12 col-sm-6 col-lg-4">
		  		Email : <c:out value="${person.getEmail()}"/>
			</div>
			<div class="col-xs-6 col-sm-6 col-lg-4">
		  		Date de naissance : <c:out value="${person.getBirthdate()}"/>
			</div>
		</sec:authorize>
		<div class="col-xs-6">
			Site Web : <c:out value="${person.getWebsite()}"/>
		</div>
		<a href="${pageContext.request.contextPath}/person/list/${person.getGroup().getId()}" class="btn btn-raised btn-primary pull-right"><c:out value="${person.getGroup().getName()}"/></a>
	</div>
</div>

<%@ include file="/WEB-INF/views/assets/footer.jsp"%>