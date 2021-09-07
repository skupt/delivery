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
<fmt:setBundle basename="resources.msg"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index page</title>
</head>
<body>
<!-- Language switcher begin -->
<form name="locales" action="Controller" method="post">
	<select name="lang" onchange="this.form.submit()">
		<option selected disabled><fmt:message key="pageLogin.chooseLang"/></option>
		<option value="ru"><fmt:message key="pageLogin.ru"/></option>
		<option value="en"><fmt:message key="pageLogin.en"/></option>
	</select>
	<input type="hidden" name="cmd" value="SetLocale"/>
	<input type="hidden" name="goTo" value="index.jsp">
</form>
<!-- end Language switcher -->

<h1>Index page</h1>
<c:url value="costs.jsp" var="varCost"/>
<p><a href='${varCost}'><fmt:message key="index.linkCostCalc"/></a></p>


</body>
</html>