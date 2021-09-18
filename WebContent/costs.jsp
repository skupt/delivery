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
<title><fmt:message key="pageCosts.calculateCostTitle"/></title>
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
						<li class="active"><a href="costs.jsp"><fmt:message key="register.linkCostCalc" /><span class="sr-only">(current)</span></a></li>
						<li><a href="tariffs.jsp"><fmt:message key="register.linkTariffs" /></a></li>
						<li><a href="login.jsp"><fmt:message key="register.linkLogin" /></a></li>
						<li><a href="#"><fmt:message key="register.linkLogout" /></a></li>
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
									type="hidden" name="goTo" value="costs.jsp">
							</form> <!-- end Language switcher -->

						</li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>

<h1><fmt:message key="pageCosts.calculateCostsHeader"/></h1>
<form name="calcShippingCosts" method="post" action="Controller">
<div id="route">
	<div style="width:50%; float:left">	
	<p><fmt:message key="pageCosts.chooseDepartureLocality"/></p>
	<select name="departure" required>
		<c:forEach var="elem" items="${localities}">
			<option value="${elem.id}">${elem.name}</option> 
		</c:forEach>
	</select> 
	</div>
	<div style="width:50%; float:left">
	<p><fmt:message key="pageCosts.chooseArrivalLocality"/></p>
	<select name="arrival" required>
		<c:forEach var="elem" items="${localities}">
			<option value="${elem.id}">${elem.name}</option> 
		</c:forEach>
	</select> 
	</div>
</div>
	<p><fmt:message key="pageCosts.dimensions"/></p>
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

		<footer class="panel panel-default" >
		  <div class="panel-body" style="text-align:center">
			<span><fmt:message key="root.footer"/></span>
			</div>
		</footer>
</div>
</body>
</html>