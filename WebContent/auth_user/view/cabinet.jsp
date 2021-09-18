<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Set actual locale 2 -->
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.user"/>
<!-- End Set locale 2 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="../../css/bootstrap.min.css"/>         
	<script src="../../js/bootstrap.min.js"></script>       

<title><fmt:message key="userCabinet.title"/></title>
</head>
<body>
<div class="container">
						<!-- Language switcher begin -->
						<form name="locales" action="/delivery/Controller" method="post">
							<select name="lang" onchange="this.form.submit()"
								class="btn btn-default dropdown-toggle">
								<option selected disabled><fmt:message
										key="fragLang.chooseLang" /></option>
								<option value="ru"><fmt:message key="fragLang.ru" /></option>
								<option value="en"><fmt:message key="fragLang.en" /></option>
							</select> <input type="hidden" name="cmd" value="SetLocale" /> <input
								type="hidden" name="goTo"
								value="auth_user/view/cabinet.jsp">
						</form> <!-- end Language switcher -->

<c:import url="../auth_user_menu_inner.jsp" charEncoding="utf-8"  />

<div class="jumbotron">
  <h1><fmt:message key="userCabinet.headerCabinet"/></h1>
		<p><fmt:message key="userCabinet.tapMenuItem" /></p>
		<p><fmt:message key="userCabinet.bakcToCabinet" /></p>
		<p><a href="/delivery/auth_user/view/cabinet.jsp" class="btn btn-primary btn-lg" role="button"><fmt:message key="userCabinet.Back"/></a></p>
</div>

		<footer class="panel panel-default" >
		  <div class="panel-body" style="text-align:center">
			<span><fmt:message key="common.footer"/></span>
			</div>
		</footer>


</div>
</body>
</html>