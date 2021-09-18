<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="rozaryonov.delivery.repository.PageableFactory"%>


<!-- Set actual locale 2 -->
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.manager"/>
<!-- End Set locale 2 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="/delivery/css/bootstrap.min.css"/>         
	<script src="/delivery/js/bootstrap.min.js"></script>       

<title><fmt:message key="createInvoices.title"/></title>
</head>
<body>
<div class="container">
<!-- Language switcher begin -->
<form name="locales" action="/delivery/Controller" method="post">
	<select class="btn btn-default navbar-btn" name="lang" onchange="this.form.submit()">
		<option selected disabled><fmt:message key="pageLogin.chooseLang"/></option>
		<option value="ru"><fmt:message key="pageLogin.ru"/></option>
		<option value="en"><fmt:message key="pageLogin.en"/></option>
	</select>
	<input type="hidden" name="cmd" value="SetLocale"/>
	<input type="hidden" name="goTo" value="manager/create_invoices.jsp">
</form>
<!-- end Language switcher -->

<nav>
	<a class="btn btn-default navbar-btn" href="/delivery/manager/view/cabinet.jsp"><fmt:message key="common.goCabinet" /></a>
	<a class="btn btn-default navbar-btn" href="/delivery/Controller?cmd=Logout"><fmt:message key="common.linkLogout" /></a>
</nav>
<h1><fmt:message key="createInvoices.header"/></h1>
<form name="shippingForm" action="/delivery/Controller" method="post">
<table class="table">
<thead>
<tr>
<th><fmt:message key="pageCrInv.Date"/></th>
<th><fmt:message key="pageCrInv.Person"/></th>
<th><fmt:message key="pageCrInv.Load"/></th>
<th><fmt:message key="pageCrInv.Unload"/></th>
<th><fmt:message key="pageCrInv.Fare"/></th>
<th><fmt:message key="pageCrInv.Status"/></th>
<th><fmt:message key="pageCrInv.Mark"/></th>
</tr>
</thead>
<c:if test="${shippingList.size() > 0}">
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
</c:if>
<c:if test="${shippingList.size() == 0}">
<fmt:message key="pageCrInv.noNewShippings"/>
</c:if>
</table>
<button type="submit" name="cmd" value="CreateInvoices"><fmt:message key="pageCrInv.CreateInvoices"/></button>
<button type="submit" name="cmd" value="CreateInvoicesShippingPrev"><fmt:message key="pageCrInv.prevPage"/></button>
<button type="submit"  name="cmd" value="CreateInvoicesShippingNext"><fmt:message key="pageCrInv.nextPage"/></button>
</form>

</div>
</body>
</html>