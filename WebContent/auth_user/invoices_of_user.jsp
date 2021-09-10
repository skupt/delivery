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
<title><fmt:message key="invoicesUser.title"/></title>
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
	<input type="hidden" name="goTo" value="auth_user/invoices_of_user.jsp">
</form>
<!-- end Language switcher -->

<div>
${person.getName()} ( ${person.getLogin()} ) <br>
<fmt:message key="balance.balance"/>: ${balance }	
</div>

<h1><fmt:message key="payments.header"/></h1>
<c:if test="${empty invoicesList}">
	<c:set var="invoicesList" value="${paginationInvoiceUser.nextPage()}"/>
</c:if>	
<form name="invoicesUserForm" action="/delivery/Controller" method="post">
<table>
<th><fmt:message key="invoicesUser.date"/></th>
<th><fmt:message key="invoicesUser.person"/></th>
<th><fmt:message key="invoicesUser.type"/></th>
<th><fmt:message key="invoicesUser.sum"/></th>
<th><fmt:message key="invoicesUser.mark"/></th>
<if test="${invoicesList.size() > 0}">
<c:forEach var="inv" items="${invoicesList}">
	<tr>
		<td>${inv.getCreationDateTime()}</td>
		<td>${inv.getPerson().getSurname()} ${st.getPerson().getName()}</td>
		<td>${inv.getInvoiceStatus().getName()}</td>
		<td>${inv.getSum()}</td>
		<td><input type="radio" name="invoiceId" value="${inv.getId()}"/></td>
	</tr>
</c:forEach>
<if test="${invoicesList.size() == 0}">
	<fmt:message key="invoicesUser.noInvoices"/>
</if>
<p>
<button type="submit" name="cmd" value="InvoiceUserPrev"><fmt:message key="invoicesUser.prevPage"/></button>
<button type="submit" name="cmd" value="InvoiceUserNext"><fmt:message key="invoicesUser.nextPage"/></button>
<button type="submit" name="cmd" value="PayInvoice"><fmt:message key="invoicesUser.Pay"/></button>
</p>
</form>

</body>
</html>