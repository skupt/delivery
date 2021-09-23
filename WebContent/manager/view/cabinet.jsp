<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Set actual locale 2 -->
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.manager"/>
<!-- End Set locale 2 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="../../css/bootstrap.min.css"/>         
	<script src="../../js/bootstrap.min.js"></script>       

<title><fmt:message key="managerCabinet.title"/></title>
</head>
<body>
<div class="container">
<!-- Language switcher begin -->
<form name="locales" action="/delivery/Controller" method="post">
	<select name="lang" onchange="this.form.submit()">
		<option selected disabled><fmt:message key="pageLogin.chooseLang"/></option>
		<option value="ru"><fmt:message key="pageLogin.ru"/></option>
		<option value="en"><fmt:message key="pageLogin.en"/></option>
	</select>
	<input type="hidden" name="cmd" value="SetLocale"/>
	<input type="hidden" name="goTo" value="manager/view/cabinet.jsp">
</form>
<!-- end Language switcher -->

<h1><fmt:message key="managerCabinet.headerCabinet"/></h1>
<form id="formMenu" action="/delivery/Controller" method="post">
</form>
	<button type="submit" form="formMenu" formmethod="post" name="cmd" class="btn btn-default navbar-btn" value="CreateInvoicesEnter"><fmt:message key="managerCabinet.createInvoices"/></button>
	<button type="submit" form="formMenu" formmethod="post" name="cmd" class="btn btn-default navbar-btn" value="PaymentsEnter"><fmt:message key="managerCabinet.addPayments"/></button>
	<button type="submit" form="formMenu" formmethod="post" name="cmd" class="btn btn-default navbar-btn" value="FinishShippingsEnter"><fmt:message key="managerCabinet.finishShipping"/></button>
	<button type="submit" form="formMenu" formmethod="post" name="cmd" class="btn btn-default navbar-btn" value="ManagerReportDay"><fmt:message key="managerCabinet.showDayReport"/></button>
	<button type="submit" form="formMenu" formmethod="post" name="cmd" class="btn btn-default navbar-btn" value="ManagerReportDirections"><fmt:message key="managerCabinet.showDirectionReport"/></button>
</div>
</body>
</html>