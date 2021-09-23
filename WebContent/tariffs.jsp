<%@page import="rozaryonov.delivery.dao.DeliveryConnectionPool"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Set actual locale -->
<c:choose>
<c:when test="${empty locale}">
	<fmt:setLocale value="ru_RU" scope="session"/>
	<c:set var="locale" value="ru_RU" scope="session"/>
</c:when>
<c:otherwise>
	<fmt:setLocale value="${locale}" scope="session"/>
</c:otherwise>
</c:choose>
<fmt:setBundle basename="resources.root"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script src="js/bootstrap.min.js"></script>
<title><fmt:message key="pageTariffs.tariffsArchive"/></title>
</head>
<body>
	<div class="container">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/delivery/index.jsp">MDS</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="costs.jsp"><fmt:message key="register.linkCostCalc" /><span class="sr-only">(current)</span></a></li>
						<li class="active"><a href="/delivery/Controller?cmd=TariffArchiveEnter"><fmt:message key="register.linkTariffs" /></a></li>
						<li><a href="login.jsp"><fmt:message key="register.linkLogin" /></a></li>
						<li><a href="/delivery/Controller?cmd=Logout"><fmt:message key="register.linkLogout" /></a></li>
						<li><a href="register.jsp"><fmt:message key="register.linkRegister" /></a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li>
							<!-- Language switcher begin -->
							<form name="locales" action="Controller" method="post"
								class="navbar-form navbar-left" role="search">
								<select name="lang" onchange="this.form.submit()">
									<option selected disabled><fmt:message
											key="register.chooseLang" /></option>
									<option value="ru"><fmt:message key="register.ru" /></option>
									<option value="en"><fmt:message key="register.en" /></option>
								</select> <input type="hidden" name="cmd" value="SetLocale" /> <input
									type="hidden" name="goTo" value="tariffs.jsp">
							</form> <!-- end Language switcher -->

						</li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>

<h1><fmt:message key="pageTariffs.tariffsArchive"/></h1>
<form name="tariffsForm" id="tariffsForm" method="post" action="Controller">
	<div>
	<fieldset>
	<fmt:message key="pageTariffs.sortIncr"/><input type="radio" name="sorting" value="incr" required/>
	<fmt:message key="pageTariffs.sortDecr"/><input type="radio" name="sorting" value="decr" required/>
	</fieldset>
	<fmt:message key="pageTariffs.filtLogConf"/><input type="number" name="logConf" min="0" value="1" size="4" required/>
	<button type="submit" name="cmd" value="TariffArchiveApply"><fmt:message key="pageTariffs.someApplySortFilt"/></button>
	</div>
</form>
<br>
<table class="table">
<tr>
<th><fmt:message key="pageTariffs.date"/></th>
<th><fmt:message key="pageTariffs.LogConf"/></th>
<th><fmt:message key="pageTariffs.TruckVel"/></th>
<th><fmt:message key="pageTariffs.Density"/></th>
<th><fmt:message key="pageTariffs.Paper"/></th>
<th><fmt:message key="pageTariffs.TargRec"/></th>
<th><fmt:message key="pageTariffs.TargDel"/></th>
<th><fmt:message key="pageTariffs.ShRate"/></th>
<th><fmt:message key="pageTariffs.InsW"/></th>
<th><fmt:message key="pageTariffs.InsR"/></th>
</tr>

<!-- 				t.setId(rs.getLong(1)); -->

<!-- 				t.setCreationTimestamp(rs.getTimestamp(2)); -->
<!-- 				t.setLogisticConfigId(rs.getLong(3)); -->
<!-- 				t.setTruckVelocity(rs.getInt(4)); -->
<!-- 				t.setDensity(rs.getDouble(5)); -->
<!-- 				t.setPaperwork(rs.getDouble(6)); -->
<!-- 				t.setTargetedReceipt(rs.getInt(7)); -->
<!-- 				t.setTargetedDelivery(rs.getInt(8)); -->
<!-- 				t.setShippingRate(rs.getDouble(9)); -->
<!-- 				t.setInsuranceWorth(rs.getDouble(10)); -->
<!-- 				t.setInsuranceRate(rs.getDouble(11)); -->


<c:if test="${not empty tariffArchiveList}">
<c:forEach var="t" items="${tariffArchiveList}">
	<tr>
		<td>${t.getCreationTimestamp()}</td>
		<td>${t.getLogisticConfigId()}</td>
		<td>${t.getTruckVelocity()}</td>
		<td>${t.getDensity()}</td>
		<td>${t.getPaperwork()}</td>
		<td>${t.getTargetedReceipt()}</td>
		<td>${t.getTargetedDelivery()}</td>
		<td>${t.getShippingRate()}</td>
		<td>${t.getInsuranceWorth()}</td>
		<td>${t.getInsuranceRate()}</td>
	</tr>
</c:forEach>
</c:if>
</table>
<form name="nextPrev" id="nextPrev" action="Controller" method="post">
	<button type="submit" name="cmd" value="TariffArchiveNext"><fmt:message key="root.next"/></button>
	<button type="submit" name="cmd" value="TariffArchivePrev"><fmt:message key="root.prev"/></button>

</form>


		<footer class="panel panel-default" >
		  <div class="panel-body" style="text-align:center">
			<span><fmt:message key="root.footer"/></span>
			</div>
		</footer>
</div>
</body>
</html>