<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}/resources"/>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="Gestionnaire d'annuaire pour l'université d'Aix-Marseille">
    <meta name="author" content="Nicolas Léotier">

    <title>Annuaire AMU | Nicolas Léotier</title>
    
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Roboto:300,400,500,700">
  	<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/icon?family=Material+Icons">

    <link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/material.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/datetimepicker.css">
 	<link rel="stylesheet" type="text/css" href="${contextPath}/css/ripples.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">
	<div class="well page active">