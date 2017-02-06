<%@ include file="/WEB-INF/views/assets/header.jsp"%>
<%@ include file="/WEB-INF/views/assets/navbar.jsp"%>

<div class="text-center alert alert-dismissible alert-success ${success ? '' : 'hidden'}">
  	<button type="button" class="close" data-dismiss="alert">×</button>
 	Bienvenue <strong><c:out value="${pageContext.request.userPrincipal.name}"/></strong> ! Ton inscription est maintenant terminé.
</div>

<div id="welcomeTitles">
	<h2 class="text-center">Bienvenue sur l'annuaire 2017</h2>
	<h3 class="text-center">proposé par l'université d'Aix-Marseille</h3>
</div>


<%@ include file="/WEB-INF/views/assets/footer.jsp"%>
