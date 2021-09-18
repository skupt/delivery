<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Set actual locale -->
<c:choose>
<c:when test="${not empty locale}">
	<fmt:setLocale value="${locale}" scope="session"/>
</c:when>
<c:otherwise>
	<fmt:setLocale value="ru_RU" scope="session"/>
</c:otherwise>
</c:choose>
<fmt:setBundle basename="resources.root"/>
<!-- end Set actual locale -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="css/bootstrap.min.css"/>         
	<script src="js/bootstrap.min.js"></script>       

<title><fmt:message key="pageLogin.titleLogin"/></title>
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
						<li><a href="costs.jsp"><fmt:message key="register.linkCostCalc" /><span class="sr-only">(current)</span></a></li>
						<li><a href="tariffs.jsp"><fmt:message key="register.linkTariffs" /></a></li>
						<li class="active"><a href="login.jsp"><fmt:message key="register.linkLogin" /></a></li>
						<li><a href="#"><fmt:message key="register.linkLogout" /></a></li>
						<li><a href="register.jsp"><fmt:message key="register.linkRegister" /></a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li>
							<!-- Language switcher begin -->
							<form name="locales" action="Controller" method="post"
								class="navbar-form navbar-left" role="search">
								<select name="lang" onchange="this.form.submit()" class="btn btn-default dropdown-toggle">
									<option selected disabled><fmt:message
											key="register.chooseLang" /></option>
									<option value="ru"><fmt:message key="register.ru" /></option>
									<option value="en"><fmt:message key="register.en" /></option>
								</select> <input type="hidden" name="cmd" value="SetLocale" /> <input
									type="hidden" name="goTo" value="login.jsp">
							</form> <!-- end Language switcher -->

						</li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>


<div>
<h1><fmt:message key="pageLogin.yourLogin"/></h1>
<c:if test="${not empty errorDescription }">
<p><fmt:message key="${errorDescription}"/></p>
</c:if>
<form name="login" action="Controller" method="post">
<input type="text" name="login" placeholder="John123" required class="form-control" />
<br>
<input type="password" name="pass" placeholder="yourPassword123" required class="form-control" />
<br>
<input type="submit" class="btn btn-primary gtn-lg" name="send" value="<fmt:message key="pageLogin.buttonSubmit"/>"/>
<br>
<input type="hidden" name="cmd" value="Login"/>
</form>

<c:url value="register.jsp" var="urlRegister"/>
<br>
<p><a href="${urlRegister}"><fmt:message key="pageLogin.registerLink"/></a>
<c:url value="index.jsp" var="urlIndex"/>
<p><a href="${urlIndex}"><fmt:message key="pageLogin.goHomePage"/></a>
</div>
	<footer class="panel panel-default" >
		  <div class="panel-body" style="text-align:center">
			<span><fmt:message key="index.footer"/></span>
			</div>
		</footer>
</div>

</body>
</html>