<%@ include file="/WEB-INF/views/assets/header.jsp"%>
<%@ include file="/WEB-INF/views/assets/navbar.jsp"%>

<h2 class="form-signin-heading text-center">Personnes<c:if test="${not empty h2Title}"> - <c:out value="${h2Title}" /></c:if></h2>

<c:choose>
	<c:when test="${not empty persons}">
		<table class="table table-striped table-hover table-responsive">
			<thead>
				<tr>
					<th>#</th>
					<th>Prénom</th>
					<th>Nom</th>
					<sec:authorize access="hasRole('ROLE_USER')">
						<th>Email</th>
						<th>Date de naissance</th>
					</sec:authorize>
					<th>Site web</th>
					<th>Groupe</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="person" items="${persons}">
					<tr onclick="window.document.location='${pageContext.request.contextPath}/person?id=${person.getId()}';" class="person-tr">
						<td><c:out value="${person.getId()}"/></td>
						<td><c:out value="${person.getFirstname()}"/></td>
						<td><c:out value="${person.getLastname()}"/></td>
						<sec:authorize access="hasRole('ROLE_USER')">
							<td><c:out value="${person.getEmail()}"/></td>
							<td><c:out value="${person.getBirthdate()}"/></td>
						</sec:authorize>
						<td><c:out value="${person.getWebsite()}"/></td>
						<td><c:out value="${person.getGroup().getName()}"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<div id="notFound" class="text-center">Aucune personne n'a été trouvé.</div>
	</c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/views/assets/footer.jsp"%>