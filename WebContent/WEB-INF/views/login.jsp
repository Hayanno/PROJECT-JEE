<%@ include file="/WEB-INF/views/assets/header.jsp"%>
<%@ include file="/WEB-INF/views/assets/navbar.jsp"%>

<form method="POST" action="${pageContext.request.contextPath}/login" class="form-signin">
    <h2 class="form-heading">Connectez vous</h2>

    <div class="form-group ${error != null ? 'has-error' : ''} label-floating">
        <span>${message}</span>
        <label for="username" class="control-label">Adresse mail</label>
        <input name="username" type="text" class="form-control" value="" />
    </div>
    <div class="form-group ${error != null ? 'has-error' : ''} label-floating">
        <label for="password" class="control-label">Mot de passe</label>
        <input name="password" type="password" class="form-control" value="" />
    </div>
    
    <span>${error}</span>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<button class="btn btn-lg btn-primary btn-block" type="submit">Connexion</button>
	<h4 class="text-center"><a href="${pageContext.request.contextPath}/registration">Créer un compte</a></h4>
    

</form>

<%@ include file="/WEB-INF/views/assets/footer.jsp"%>