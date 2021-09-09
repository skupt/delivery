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
<title><fmt:message key="createInvoices.title"/></title>
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
	<input type="hidden" name="goTo" value="manager/create_invoices.jsp">
</form>
<!-- end Language switcher -->

<h1><fmt:message key="createInvoices.header"/></h1>
<c:if test="${empty shippingList}">
	<c:set var="shippingList" value="${paginationShipping.nextPage()}"/>
</c:if>	
<form name="shippingForm" action="/delivery/Controller" method="post">
<table>
<th>
Date<br>
	<select name="sortDate">
		<option selected disabled><fmt:message key="pageCrInv.sortDate"/></option>
		<option value="1"><fmt:message key="pageCrInv.asc"/></option>
		<option value="2"><fmt:message key="pageCrInv.desc"/></option>
	</select>
</th>

<th>
Person<br>
	<select name="sortPerson">
		<option selected disabled><fmt:message key="pageCrInv.sortPerson"/></option>
		<option value="1"><fmt:message key="pageCrInv.asc"/></option>
		<option value="2"><fmt:message key="pageCrInv.desc"/></option>
	</select>
</th>
<th>Load</th>
<th>Unload</th>
<th>Fare</th>
<th>
Status<br>
	<select name="filterStatus">
		<option selected disabled><fmt:message key="pageCrInv.filter"/></option>
		<option value="1"><fmt:message key="pageCrInv.justCreated"/></option>
		<option value="2"><fmt:message key="pageCrInv.waiting"/></option>
		<option value="4"><fmt:message key="pageCrInv.delivering"/></option>
		<option value="5"><fmt:message key="pageCrInv.delivered"/></option>
	</select>
</th>
<th>Mark</th>
<c:forEach var="s" items="${shippingList}">
	<tr>
		<td>${s.getCreationTimestamp()}</td>
		<td>${s.getPerson().getName()}</td>
		<td>${s.getLoadLocality().getName()}</td>
		<td>${s.getUnloadLocality().getName()}</td>
		<td>${s.getFare()}</td>
		<td>${s.getShippingStatus().getName()}</td>
		<td><input type="checkbox" name="shippingId" value="${s.getId()}"/></td>
	</tr>
</c:forEach>
</table>
<button type="submit" name="cmd" value="CreateInvoices"><fmt:message key="pageCrInv.CreateInvoices"/></button>
<button type="submit" name="cmd" value="CreateInvoicesFilterSort"><fmt:message key="pageCrInv.ApplyFilterSort"/></button>
<button type="submit" name="cmd" value="CreateInvoicesShippingPrev"><fmt:message key="pageCrInv.prevPage"/></button>
<button type="submit" name="cmd" value="CreateInvoicesShippingNext"><fmt:message key="pageCrInv.nextPage"/></button>
</form>
</body>
</html>