<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Set actual locale 2 -->
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.msg"/>
<!-- Ens Set locale 2 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="pageMakeOrder.titleMakeOrder"/></title>
</head>
<body>
<!-- Language switcher begin -->
<form name="locales" action="/Controller" method="post">
	<select name="lang" onchange="this.form.submit()">
		<option selected disabled><fmt:message key="pageLogin.chooseLang"/></option>
		<option value="ru"><fmt:message key="pageLogin.ru"/></option>
		<option value="en"><fmt:message key="pageLogin.en"/></option>
	</select>
	<input type="hidden" name="cmd" value="SetLocale"/>
	<input type="hidden" name="goTo" value="/view/make_order.jsp">
</form>
<!-- end Language switcher -->

<h1><fmt:message key="makeOrder.headerMakeOrder"/></h1>

<c:if test="${not empty person}">
	<c:url value="/auth_user/cabinet.jsp" var="varRedirect"/>
	<c:redirect url="/auth_user/view/cabinet.jsp"/>
	<a href="/delivery/auth_user/view/cabinet.jsp"><fmt:message key="makeOrder.PressToGoToCabinet"/></a>


</c:if>

<c:if test="${empty person}">
	<p><fmt:message key="makeOrder.msgPlsRegBeforeMakeOrder"/></p>
	<p><fmt:message key="makeOrder.msgInstrAfterRegister"/></p>
	<c:url value="/register.jsp" var="varReg"/>
	<a href="${varReg}"><fmt:message key="makeOrder.msgGoRegister"/></a>
	<c:url value="/login.jsp" var="varLog"/>
	<a href="${varLog}"><fmt:message key="makeOrder.msgOrLogin"/></a>
</c:if>

</body>
</html>