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
<title><fmt:message key="payments.title"/></title>
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
	<input type="hidden" name="goTo" value="manager/payments.jsp">
</form>
<!-- end Language switcher -->

<h1><fmt:message key="payments.header"/></h1>
<c:if test="${empty settlementsList}">
	<c:set var="settlementsList" value="${paginationSettlements.nextPage()}"/>
</c:if>	
<form name="shippingForm" action="/delivery/Controller" method="post">
<table>
<th><fmt:message key="payments.date"/></th>
<th><fmt:message key="payments.person"/></th>
<th><fmt:message key="payments.type"/></th>
<th><fmt:message key="payments.amount"/></th>
<th><fmt:message key="payments.mark"/></th>
<c:forEach var="st" items="${settlementsList}">
	<tr>
		<td>${st.getCreationDatetime()}</td>
		<td>${st.getPerson().getSurname()} ${st.getPerson().getName()}</td>
		<td>${st.getSettlementType().getName()}</td>
		<td>${st.getAmount()}</td>
		<td><input type="checkbox" name="settlementId" value="${st.getId()}"/></td>
	</tr>
</c:forEach>
	<tr>
		<td>
				<input type="number" name="year" min="2021" max="2100" required value="${date.getYear() }"/><br>
				<input type="number" name="month" min="1" max="12" required  value="${date.getMonthValue()}"/><br>
				<input type="number" name="day" min="1" max="31" required  value="${date.getDayOfMonth() }"/>
		</td>
		<td>
			<select name="person">
				<c:forEach var="person" items="${persons}">
					<option value="${person.getId()}">${person.getSurname()} ${person.getName()}</option>
				</c:forEach>
			</select>
		</td>
		<td>payment</td>
		<td><input type="number" name="amount"></td>
		<td><button type="submit" name="cmd" value="PaymentsInsert"><fmt:message key="payments.insertNew"/></button></td>
	</tr>
</table>
<button type="submit" name="cmd" value="PaymentsPrev"><fmt:message key="payments.prevPage"/></button>
<button type="submit" name="cmd" value="PaymentsNext"><fmt:message key="payments.nextPage"/></button>
</form>

</body>
</html>