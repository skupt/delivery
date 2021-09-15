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
	<link rel="stylesheet" href="css/bootstrap.min.css"/>         
	<script src="js/bootstrap.min.js"></script>       

<!-- DateTimePicker -->
	<!-- <link data-require="bootstrap@3.3.7" data-semver="3.3.7" rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /> -->
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker-standalone.css" />
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker-standalone.min.css" />
    <script data-require="jquery@3.1.1" data-semver="3.1.1" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<!-- <script data-require="bootstrap@3.3.7" data-semver="3.3.7" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>  --> 
	<script data-require="MomentJS@2.10.0" data-semver="2.10.0" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript">
 		$(function() {
			$('#datetimepicker1').datetimepicker();
		});
 	</script>
<!-- DateTimePicker -->

<title><fmt:message key="payments.title"/></title>
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
	<input type="hidden" name="goTo" value="manager/payments.jsp">
</form>
<!-- end Language switcher -->

<h1><fmt:message key="payments.header"/></h1>

<form name="shippingForm" action="/delivery/Controller" method="post">
<table class="table">
<tr>
<th><fmt:message key="payments.date"/></th>
<th><fmt:message key="payments.person"/></th>
<th><fmt:message key="payments.type"/></th>
<th><fmt:message key="payments.amount"/></th>
<th><fmt:message key="payments.mark"/></th>
</tr>
<c:if test="${not empty settlementsList}">
<c:forEach var="st" items="${settlementsList}">
	<tr>
		<td>${st.getCreationDatetime()}</td>
		<td>${st.getPerson().getSurname()} ${st.getPerson().getName()}</td>
		<td>${st.getSettlementType().getName()}</td>
		<td>${st.getAmount()}</td>
		<td><input type="checkbox" name="settlementId" value="${st.getId()}"/></td>
	</tr>
</c:forEach>
</c:if>
	<tr>
		<td>
<!-- DateTimePicker -->
    <div class="row">
      <div class="col-sm-8">
        <div class="form-group">
          <div class="input-group date" id="datetimepicker1">
            <input type="text" name="paymentDate" required class="form-control" />
            <span class="input-group-addon">
                <span class="glyphicon glyphicon-calendar"></span>
            </span>
          </div>
        </div>
      </div>
    </div>
<!-- DateTimePicker -->
				
				<!-- 
				<input type="number" name="year" min="2021" max="2100" required value="${date.getYear() }"/><br>
				<input type="number" name="month" min="1" max="12" required  value="${date.getMonthValue()}"/><br>
				<input type="number" name="day" min="1" max="31" required  value="${date.getDayOfMonth() }"/>
				 -->
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
</form>
<form name="nextPrevForm" action="/delivery/Controller" method="post">
<button type="submit" name="cmd" value="PaymentsPrev"><fmt:message key="payments.prevPage"/></button>
<button type="submit" name="cmd" value="PaymentsNext"><fmt:message key="payments.nextPage"/></button>
</form>
</div>
</body>
</html>