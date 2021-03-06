<%@ include file="/WEB-INF/views/assets/header.jsp"%>
<%@ include file="/WEB-INF/views/assets/navbar.jsp"%>

<form:form method="POST" modelAttribute="personForm" class="form-signin">
    <h2 class="form-signin-heading text-center">Cr�er votre compte</h2>
    <spring:bind path="email">
        <div class="form-group ${status.error ? 'has-error' : ''} label-floating">
        	<form:label path="email" class="control-label">Adresse mail *</form:label>
            <form:input path="email" class="form-control" type="text"></form:input>
            <form:errors path="email"></form:errors>
        </div>
    </spring:bind>

    <spring:bind path="password">
        <div class="form-group ${status.error ? 'has-error' : ''} label-floating">
        	<form:label path="password" class="control-label">Mot de passe *</form:label>
            <form:input path="password" type="password" class="form-control"></form:input>
            <form:errors path="password"></form:errors>
        </div>
    </spring:bind>

    <spring:bind path="firstname">
        <div class="form-group ${status.error ? 'has-error' : ''} label-floating">
        	<form:label path="firstname" class="control-label">Pr�nom *</form:label>
            <form:input path="firstname" type="text" class="form-control"></form:input>
            <form:errors path="firstname"></form:errors>
        </div>
    </spring:bind>

    <spring:bind path="lastname">
        <div class="form-group ${status.error ? 'has-error' : ''} label-floating">
        	<form:label path="lastname" class="control-label">Nom *</form:label>
            <form:input path="lastname" type="text" class="form-control"></form:input>
            <form:errors path="lastname"></form:errors>
        </div>
    </spring:bind>

    <spring:bind path="website">
        <div class="form-group ${status.error ? 'has-error' : ''} label-floating">
        	<form:label path="website" class="control-label">Site internet</form:label>
            <form:input path="website" type="text" class="form-control"></form:input>
            <form:errors path="website"></form:errors>
        </div>
    </spring:bind>

    <spring:bind path="birthdate">
        <div class="form-group ${status.error ? 'has-error' : ''} label-floating">
        	<form:label path="birthdate" class="control-label">Date de naissance</form:label>
            <form:input path="birthdate" class="form-control datetimepicker"></form:input>
            <form:errors path="birthdate"></form:errors>
        </div>
    </spring:bind>
    
    <div class="form-group ${status.error ? 'has-error' : ''} label-floating">
     <form:label path="group" class="control-label">Groupe *</form:label>
     <form:select path="group" items="${groups}" itemLabel="name" itemValue="id" class="form-control after-datetimepicker ${status.error ? 'has-error' : ''}" />
     <form:errors path="group"></form:errors>
 </div>

    <button class="btn btn-lg btn-primary btn-raised btn-block" type="submit">Envoyer</button>
</form:form>

<%@ include file="/WEB-INF/views/assets/footer.jsp"%>