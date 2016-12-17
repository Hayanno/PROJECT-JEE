<c:if test="${pageContext.request.userPrincipal.name != null}">
	<form id="logoutForm" method="POST" action="${pageContext.request.contextPath}/logout">
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
</c:if>

<div class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="javascript:void(0)">Annuaire AMU 2016</a>
    </div>
    <div class="navbar-collapse collapse navbar-responsive-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="javascript:void(0)">Accueil</a></li>
        <li><a href="javascript:void(0)">Groupes</a></li>
        <li><a href="${pageContext.request.contextPath}/person/list">Personnes</a></li>
      </ul>
      <form class="navbar-form navbar-left">
        <div class="form-group">
          <input type="text" class="form-control col-sm-8" placeholder="Recherche">
        </div>
      </form>
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a id="ddnavright" href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          	Compte
            <b class="caret"></b>
          </a>
          <ul class="dropdown-menu" aria-labelledby="ddnavright">
            <li class="dropdown-header"><a href="javascript:void(0)">Bonjour ${pageContext.request.userPrincipal.name} !</a></li>
            <li class="divider"></li>
            <c:choose>
			  <c:when test="${pageContext.request.userPrincipal.name != null}">
			    <li><a href="javascript:void(0)">Paramètres</a></li>
           	  	<li><a href="javascript:void(0)" onclick="document.forms['logoutForm'].submit()">Déconnexion</a></li>
			  </c:when>
			  <c:otherwise>
           	  	<li><a href="${pageContext.request.contextPath}/login">Connexion</a></li>
			  </c:otherwise>
			</c:choose>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</div>