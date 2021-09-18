<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Set actual locale -->
<!-- Set actual locale 2 -->
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.index"/>
<!-- End Set locale 2 -->


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script src="js/bootstrap.min.js"></script>

<title></title>
</head>
<body>
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
						<li><a href="/delivery/costs.jsp"><fmt:message key="index.linkCostCalc" /><span class="sr-only">(current)</span></a></li>
						<li><a href="/delivery/tariffs.jsp"><fmt:message key="index.linkTariffs" /></a></li>
						<li><a href="/delivery/login.jsp"><fmt:message key="index.linkLogin" /></a></li>
						<li><a href="/delivery/Controller?cmd=Logout"><fmt:message key="index.linkLogout" /></a></li>
						<li><a href="/delivery/register.jsp"><fmt:message key="index.linkRegister" /></a></li>
						<li><a href="/delivery/auth_user/view/cabinet.jsp"><fmt:message key="index.linkRegister" /></a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li>
<!-- Language switcher begin -->
<form name="locales" action="/delivery/Controller" method="post">
	<select name="lang" onchange="this.form.submit()">
		<option selected disabled><fmt:message key="index.chooseLang"/></option>
		<option value="ru"><fmt:message key="index.ru"/></option>
		<option value="en"><fmt:message key="index.en"/></option>
	</select>
	<input type="hidden" name="cmd" value="SetLocale"/>
	<input type="hidden" name="goTo" value="${pageContext.request.requestURI }">
</form>
<!-- end Language switcher -->
						</li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>
		
		<!-- inner menu of auth user -->
		<c:import url="../auth_user_menu_inner.jsp" charEncoding="utf-8"  />

</body>
</html>