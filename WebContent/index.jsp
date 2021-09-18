<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Set actual locale -->
<c:choose>
	<c:when test="${not empty locale}">
		<fmt:setLocale value="${locale}" scope="session" />
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="ru_RU" scope="session" />
	</c:otherwise>
</c:choose>
<fmt:setBundle basename="resources.index" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script src="js/bootstrap.min.js"></script>

<title><fmt:message key="index.title" /></title>
</head>
<body>
	<div class="container">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/delivery/index.jsp">MDS</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="costs.jsp"><fmt:message key="index.linkCostCalc" /><span class="sr-only">(current)</span></a></li>
						<li><a href="tariffs.jsp"><fmt:message key="index.linkTariffs" /></a></li>
						<li><a href="login.jsp"><fmt:message key="index.linkLogin" /></a></li>
						<li><a href="/delivery/Controller?cmd=Logout"><fmt:message key="index.linkLogout" /></a></li>
						<li><a href="register.jsp"><fmt:message key="index.linkRegister" /></a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li>
							<!-- Language switcher begin -->
							<form name="locales" action="Controller" method="post"
								class="navbar-form navbar-left" role="search">
								<select name="lang" onchange="this.form.submit()">
									<option selected disabled><fmt:message
											key="index.chooseLang" /></option>
									<option value="ru"><fmt:message key="index.ru" /></option>
									<option value="en"><fmt:message key="index.en" /></option>
								</select> <input type="hidden" name="cmd" value="SetLocale" /> <input
									type="hidden" name="goTo" value="index.jsp">
							</form> <!-- end Language switcher -->

						</li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>

<div class="jumbotron">
  <h1><fmt:message key="index.header" /></h1>
		<p><fmt:message key="index.about1"/></p>
		<p><fmt:message key="index.about2"/></p>
		<p><fmt:message key="index.about3"/></p>
		<p><fmt:message key="index.about4"/></p>
		<p><fmt:message key="index.about5"/></p>
  <p><a class="btn btn-primary btn-lg" href="costs.jsp" role="button"><fmt:message key="index.makeOrder" /></a></p>
</div>

		<footer class="panel panel-default" >
		  <div class="panel-body" style="text-align:center">
			<span><fmt:message key="index.footer"/></span>
			</div>
		</footer>


	</div>
</body>
</html>