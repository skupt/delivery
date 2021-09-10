<%@page import="rozaryonov.delivery.dao.DeliveryConnectionPool"%>
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
<title><fmt:message key="pageCosts.calculateCostTitle"/></title>
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
	<input type="hidden" name="goTo" value="costs.jsp">
</form>
<!-- end Language switcher -->

<h1><fmt:message key="pageCosts.calculateCostsHeader"/></h1>
<form name="calcShippingCosts" method="post" action="Controller">
	<div>	
	<p><fmt:message key="pageCosts.chooseDepartureLocality"/></p>
	<select name="departure" required>
		<c:forEach var="elem" items="${localities}">
			<option value="${elem.id}">${elem.name}</option> 
		</c:forEach>
	</select> 
	</div>
	
	<div>
	<p><fmt:message key="pageCosts.chooseArrivalLocality"/></p>
	<select name="arrival" required>
		<c:forEach var="elem" items="${localities}">
			<option value="${elem.id}">${elem.name}</option> 
		</c:forEach>
	</select> 
	</div>
	<p>
	<input name="length" type="range" min="20" max="120" step="5" value="5" oninput="this.nextElementSibling.value = this.value"/>
	<output>20</output><fmt:message key="units.cm"/>
	</p>
	
	<p>	
	<input name="width" type="range" min="15" max="80" step="5" value="5" oninput="this.nextElementSibling.value = this.value"/>
	<output>15</output><fmt:message key="units.cm"/>
	</p>

	<p>
	<input name="height" type="range" min="5" max="160" step="5" value="5" oninput="this.nextElementSibling.value = this.value"/>
	<output>5</output><fmt:message key="units.cm"/>
	</p>
	
	<p>	
	<input name="weight" type="range" min="1" max="800" step="0,5" value="1" oninput="this.nextElementSibling.value = this.value"/>
	<output>1</output><fmt:message key="units.kg"/>

	<p><input type="submit" name="button" value=<fmt:message key="pageCosts.calcCosts"/>></p>
	<input type="hidden" name="cmd" value="DeliveryCost"/>
	<!-- input type="hidden" name="goTo" value="page.jsp"-->

</form>
</body>
</html>