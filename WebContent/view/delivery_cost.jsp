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
<title><fmt:message key="pageCosts.calculationCosts"/></title>
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
	<input type="hidden" name="goTo" value="/delivery/view/delivery_cost.jsp">
</form>
<!-- end Language switcher -->

<h1><fmt:message key="pageCosts.calculationCosts"/></h1>
<div>
<h2><fmt:message key="pageCosts.data"/></h2>
<p><fmt:message key="pageCosts.route"/>: ${route}</p>
<p><fmt:message key="pageCosts.distance"/>: ${distance}</p>
<p><fmt:message key="pageCosts.expectedDuration"/>: ${duration}</p>
<p><fmt:message key="pageCosts.weight"/>: ${weight}</p>
<p><fmt:message key="pageCosts.volumeWeight"/> ${volumeWeight}</p>
<p><fmt:message key="pageCosts.usedWeight"/>: ${usedWeight}</p>
</div>
<div>
<h2>calculations</h2>
<p><fmt:message key="pageCosts.paperwork"/>: ${paperwork}</p>
<p><fmt:message key="pageCosts.targetReceipt"/>: ${targetReceipt}</p>
<p><fmt:message key="pageCosts.interCityCost"/>: ${interCityCost}</p>
<p><fmt:message key="pageCosts.targetDelivery"/>: ${targetDelivery}</p>
<p><fmt:message key="pageCosts.insuranceCost"/>: ${insuranceCost}</p>
<h3><fmt:message key="pageCosts.totalMoney"/>: ${totalMoney}</h3>
</div>
<div>
<h2>TariffDetails</h2>
<p><fmt:message key="pageCosts.shippingRate"/>: ${shippingRate}</p>
<p><fmt:message key="pageCosts.targetReceiptDist"/>: ${targetReceiptDist}</p>
<p><fmt:message key="pageCosts.targetDeliveryDist"/>: ${targetDeliveryDist}</p>
<p><fmt:message key="pageCosts.insuranceWorth"/>: ${insuranceWorth}</p>
<p><fmt:message key="pageCosts.insuranceRate"/>: ${insuranceRate}</p>
</div>
<p></p>

<!--
<form name="choseAction" method="get">
<button type="submit" formaction="/delivery/costs.jsp"><fmt:message key="pageCosts.oneMoreCalculation"/></button>
<button type="sumbit" formaction="/delivery/view/make_order.jsp"><fmt:message key="pageCosts.makeOrder"/></button>
</form>
-->
<a href="/delivery/costs.jsp"><fmt:message key="pageCosts.oneMoreCalculation"/></a>
<a href="/delivery/view/make_order.jsp"><fmt:message key="pageCosts.makeOrder"/></a>

</body>
</html>