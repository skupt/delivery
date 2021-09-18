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

<title><fmt:message key="finishShippings.title"/></title>
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
	<input type="hidden" name="goTo" value="manager/finish_shippings.jsp">
</form>
<!-- end Language switcher -->

<nav>
	<a class="btn btn-default navbar-btn" href="/delivery/manager/view/cabinet.jsp"><fmt:message key="common.goCabinet" /></a>
	<a class="btn btn-default navbar-btn" href="/delivery/Controller?cmd=Logout"><fmt:message key="common.linkLogout" /></a>
</nav>


<h1><fmt:message key="finishShippings.header"/></h1>
<form name="shippingForm2" action="/delivery/Controller" method="post">
<table class="table">
<thead>
<tr>
<th><fmt:message key="finishShippings.Date"/></th>
<th><fmt:message key="finishShippings.Person"/></th>
<th><fmt:message key="finishShippings.Load"/></th>
<th><fmt:message key="finishShippings.Unload"/></th>
<th><fmt:message key="finishShippings.Fare"/></th>
<th><fmt:message key="finishShippings.Status"/></th>
<th>
	<fmt:message key="finishShippings.Mark"/>
	<!-- DateTimePicker -->
	    <div class="row">
    	  <div class="col-sm-8">
	        <div class="form-group">
    	      <div class="input-group date" id="datetimepicker1">
        	    <input type="text" name="unloadDate" class="form-control" />
   				  <span class="input-group-addon">
	         		<span class="glyphicon glyphicon-calendar"></span>
    	      	  </span>
	          </div>
   	    	</div>
   		  </div>
   		</div>
	<!-- DateTimePicker -->
</th>
</tr>
</thead>	
<c:if test="${shippingListFinish.size() > 0}">
<c:forEach var="s" items="${shippingListFinish}">
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
<c:if test="${shippingListFinish.size() == 0}">
	<fmt:message key="finishShippings.noNewShippings"/>
</c:if>
</table>
<button type="submit" name="cmd" value="FinishShippings"><fmt:message key="finishShippings.finishShippings"/></button>
<button type="submit" name="cmd" value="FinishShippingsPrev"><fmt:message key="finishShippings.prevPage"/></button>
<button type="submit" name="cmd" value="FinishShippingsNext"><fmt:message key="finishShippings.nextPage"/></button>
</form>

</div>
</body>
</html>