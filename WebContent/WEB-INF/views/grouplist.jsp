<%@ include file="/WEB-INF/views/assets/header.jsp"%>
<%@ include file="/WEB-INF/views/assets/navbar.jsp"%>

<h2 class="form-signin-heading text-center">Groupes</h2>

<c:if test="${not empty groups}">
	<table class="table table-striped table-hover table-responsive">
		<thead>
			<tr>
				<th>#</th>
				<th>Nom</th>
				<th>Personnes</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="group" items="${groups}">
				<tr>
					<td>${group.getId()}</td>
					<td>${group.getName()}</td>
					<td>
						${fn:length(group.getPersons())} 
						<a href="${pageContext.request.contextPath}/person/list/${group.getId()}"><i class="material-icons">forward</i></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>

<%@ include file="/WEB-INF/views/assets/footer.jsp"%>