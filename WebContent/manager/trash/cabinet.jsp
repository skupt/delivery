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
<c:import url="../auth_user_menu_inner.jsp" charEncoding="utf-8"  />
<h1><fmt:message key="userCabinet.headerCabinet"/></h1>
<form id="formMenu" action="/delivery/Controller" method="post">
</form>
	<button type="submit" form="formMenu" formmethod="post" name="cmd" class="btn btn-default navbar-btn" value="InvoiceUserEnter"><fmt:message key="userCabinet.invoicesUserLink"/></button>
	<button type="submit" form="formMenu" formmethod="post" name="cmd" class="btn btn-default navbar-btn" value="OrderUserResumeEnter"><fmt:message key="userCabinet.resumeOrderLink"/></button>
	<button type="submit" form="formMenu" formmethod="get" formaction="/delivery/costs.jsp" class="btn btn-default navbar-btn" ><fmt:message key="userCabinet.costCalculation"/></button>
</div>
</body>
</html>