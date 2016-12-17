<%@ include file="/WEB-INF/views/assets/header.jsp"%>
<%@ include file="/WEB-INF/views/assets/navbar.jsp"%>

<h2 class="form-signin-heading text-center">Personnes<c:if test="${not empty group}"> - Groupe "<c:out value="${group.getName()}" />"</c:if></h2>

<c:if test="${not empty persons}">
	<table class="table table-striped table-hover table-responsive">
		<thead>
			<tr>
				<th>#</th>
				<th>Prénom</th>
				<th>Nom</th>
				<th>Email</th>
				<th>Site web</th>
				<th>Date de naissance</th>
				<th>Groupe</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="person" items="${persons}">
				<tr>
					<td>${person.getId()}</td>
					<td>${person.getFirstname()}</td>
					<td>${person.getLastname()}</td>
					<td>${person.getEmail()}</td>
					<td>${person.getWebsite()}</td>
					<td>${person.getBirthdate()}</td>
					<td>${person.getGroup().getId()}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>

<%@ include file="/WEB-INF/views/assets/footer.jsp"%>