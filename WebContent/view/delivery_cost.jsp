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
<fmt:setBundle basename="resources.deliveryCost"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<script src="../js/bootstrap.min.js"></script>

<title><fmt:message key="deliveryCosts.calculationCosts"/></title>
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
						<li class="active"><a href="/delivery/costs.jsp"><fmt:message key="deliveryCosts.linkCostCalc" /><span class="sr-only">(current)</span></a></li>
						<li><a href="/delivery/tariffs.jsp"><fmt:message key="deliveryCosts.linkTariffs" /></a></li>
						<li><a href="/delivery/login.jsp"><fmt:message key="deliveryCosts.linkLogin" /></a></li>
						<li><a href="#"><fmt:message key="deliveryCosts.linkLogout" /></a></li>
						<li><a href="/delivery/register.jsp"><fmt:message key="deliveryCosts.linkRegister" /></a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li>
							<!-- Language switcher begin -->
							<form name="locales" action="/delivery/Controller" method="post"
								class="navbar-form navbar-left" role="search">
								<select name="lang" onchange="this.form.submit()">
									<option selected disabled><fmt:message
											key="deliveryCosts.chooseLang" /></option>
									<option value="ru"><fmt:message key="deliveryCosts.ru" /></option>
									<option value="en"><fmt:message key="deliveryCosts.en" /></option>
								</select> <input type="hidden" name="cmd" value="SetLocale" /> <input
									type="hidden" name="goTo" value="/delivery/view/delivery_cost.jsp">
							</form> 
							<!-- end Language switcher -->
						</li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>


<div class="panel panel-success">
  <div class="panel-heading">
    <h3 class="panel-title"><fmt:message key="deliveryCosts.totalMoney"/>: ${totalMoney}
	<a class="btn btn-default btn-lg" role="button" href="/delivery/view/make_order.jsp"><fmt:message key="deliveryCosts.makeOrder"/></a>
	</h3>
  </div>
  <div class="panel-body">
<p><fmt:message key="deliveryCosts.paperwork"/>: ${paperwork}</p>
<p><fmt:message key="deliveryCosts.targetReceipt"/>: ${targetReceipt}</p>
<p><fmt:message key="deliveryCosts.interCityCost"/>: ${interCityCost}</p>
<p><fmt:message key="deliveryCosts.targetDelivery"/>: ${targetDelivery}</p>
<p><fmt:message key="deliveryCosts.insuranceCost"/>: ${insuranceCost}</p> 
</div>
</div>


<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title"><fmt:message key="deliveryCosts.data"/></h3>
  </div>
  <div class="panel-body">
<p><fmt:message key="deliveryCosts.route"/>: ${route}</p>
<p><fmt:message key="deliveryCosts.distance"/>: ${distanceD}</p>
<p><fmt:message key="deliveryCosts.expectedDuration"/>: ${duration}</p>
<p><fmt:message key="deliveryCosts.weight"/>: ${weight}</p>
<p><fmt:message key="deliveryCosts.volumeWeight"/> ${volumeWeight}</p>
<p><fmt:message key="deliveryCosts.usedWeight"/>: ${usedWeight}</p>
</div>
</div>

<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title"><fmt:message key="deliveryCosts.tariffDetails"/></h3>
  </div>
  <div class="panel-body">
<p><fmt:message key="deliveryCosts.shippingRate"/>: ${shippingRate}</p>
<p><fmt:message key="deliveryCosts.targetReceiptDist"/>: ${targetReceiptDist}</p>
<p><fmt:message key="deliveryCosts.targetDeliveryDist"/>: ${targetDeliveryDist}</p>
<p><fmt:message key="deliveryCosts.insuranceWorth"/>: ${insuranceWorth}</p>
<p><fmt:message key="deliveryCosts.insuranceRate"/>: ${insuranceRate}</p>
</div>
</div>

<p><a class="btn btn-primary btn-lg" role="button" href="/delivery/costs.jsp"><fmt:message key="deliveryCosts.oneMoreCalculation"/></a></p>

		<footer class="panel panel-default" >
		  <div class="panel-body" style="text-align:center">
			<span><fmt:message key="root.footer"/></span>
			</div>
		</footer>

</div>
</body>
</html>