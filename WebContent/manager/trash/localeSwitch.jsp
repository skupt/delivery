<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<meta charset="UTF-8">
</head>
<title><fmt:message key="userCabinet.title"/></title>
<body>
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
</body>
</html>