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
<fmt:setBundle basename="resources.msg" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="register.registerTitle" /></title>
<script type="text/javascript">
	function validateForm() {
		var p = document.getElementById("pass").value;
		var r = document.getElementById("repeat").value;
		if (p == r) {
			return true;
		}
		alert("Please re-enter your password");
		return false;
	}
</script>
</head>
<body>
	<!-- Language switcher begin -->
	<form name="locales" action="/delivery/Controller" method="post">
		<select name="lang" onchange="this.form.submit()">
			<option selected disabled><fmt:message
					key="pageLogin.chooseLang" /></option>
			<option value="ru"><fmt:message key="pageLogin.ru" /></option>
			<option value="en"><fmt:message key="pageLogin.en" /></option>
		</select> <input type="hidden" name="cmd" value="SetLocale" /> <input
			type="hidden" name="goTo" value="register.jsp" />
	</form>
	<!-- end Language switcher -->

	<h1>
		<fmt:message key="register.registerHead" />
	</h1>
	<form name="register" method="post" action="/delivery/Controller" onsubmit = "return validateForm()">
		<p>
			<input type="text" name="name" pattern="\w+" placeholder=<fmt:message key="register.name"/>
				 required />
		</p>
		<p>
			<input type="text" name="login" pattern="\w{3,}"
				placeholder=<fmt:message key="register.login"/> required />
		</p>
		<p>
			<input type="password" name="pass" id="pass" pattern="\w{3,}"
				placeholder=<fmt:message key="register.password"/> required />
		</p>
		<p>
			<input type="password" name="pass" id="repeat" pattern="\w{3,}"
				placeholder=<fmt:message key="register.repeatPass"/> required />
		</p>
		<p>
			<input type="submit" name=<fmt:message key="register.submit"/>  />
		</p>
		
		<input type="hidden" name="cmd" value="Registration" /> <input
			type="hidden" name="goTo" value="auth_user/view/cabinet.jsp" />
	</form>
</body>
</html>