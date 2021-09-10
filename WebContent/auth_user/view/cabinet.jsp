<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Set actual locale 2 -->
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.msg"/>
<!-- End Set locale 2 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="userCabinet.title"/></title>
</head>
<body>
<!-- Language switcher begin -->
<form name="locales" action="/delivery/Controller" method="post">
	<select name="lang" onchange="this.form.submit()">
		<option selected disabled><fmt:message key="pageLogin.chooseLang"/></option>
		<option value="ru"><fmt:message key="pageLogin.ru"/></option>
		<option value="en"><fmt:message key="pageLogin.en"/></option>
	</select>
	<input type="hidden" name="cmd" value="SetLocale"/>
	<input type="hidden" name="goTo" value="auth_user/view/cabinet.jsp">
</form>
<!-- end Language switcher -->

<h1><fmt:message key="userCabinet.headerCabinet"/></h1>
<a href="/delivery/auth_user/cabinet_resume_order.jsp"><fmt:message key="userCabinet.resumeOrderLink"/></a><br>
<a href="/delivery/auth_user/invoices_of_user.jsp"><fmt:message key="userCabinet.invoicesUserLink"/></a><br>
<a href="/delivery/costs.jsp"><fmt:message key="userCabinet.costsCalculation"/></a><br>
</body>
</html>