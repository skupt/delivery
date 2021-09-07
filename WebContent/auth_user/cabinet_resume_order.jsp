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
<title><fmt:message key="userCabinet.title"/></title>
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
	<input type="hidden" name="goTo" value="auth_user/cabinet_resume_order.jsp">
</form>
<!-- end Language switcher -->
<h1><fmt:message key="userCabinetResOrder.headerResumeOrder"/></h1>
<c:if test='${empty loadLocality}'>
	<p><fmt:message key="userCabinetResOrder.msgNoUnfinishedOrders"/>
</c:if>
<c:if test='${not empty loadLocality}'>
<c:if test="${not empty errorDescription}">
	<p>'${errorDescription}'</p>
</c:if>
<form name="resumeOrder" method="post" action="/delivery/Controller">
<h2><fmt:message key="userCabinetResOrder.enterData"/></h2>
<p><fmt:message key="userCabinetResOrder.loadLocality"/></p><p><input type="text" name="load" disabled value='${loadLocality.name}'/></p>
<p><fmt:message key="userCabinetResOrder.shipper"/></p><p><input type="text" name="shipper" pattern="^.{1,45}$" required placeholder=<fmt:message key="userCabinetResOrder.shipper" /> value='${shipper }' /></p>
<p><fmt:message key="userCabinetResOrder.downloadAddress"/></p><p><input type="text" name="downloadAddress" pattern="^.{1,45}$" required placeholder=<fmt:message key="userCabinetResOrder.downloadAddress" />value='${downloadAddress }'/></p>
<p><fmt:message key="userCabinetResOrder.downloadDateTime"/></p>
<p>
<fmt:message key="userCabinetResOrder.date"/>
 <input type="number" name="year" min='2021' max='2100' required value='${date.getYear() }'/>
 - <input type="number" name="month" min='1' max='12' required  value='${date.getMonthValue()}'/>
 - <input type="number" name="day" min='1' max='31' required  value='${date.getDayOfMonth() }'/>
</p>
<p>
<fmt:message key="userCabinetResOrder.time"/>
 <input type="number" name="hour" min='0' max='24' required  value='${date.getHour() }'/>
 : <input type="number" name="minute" min='0' max='59' required  value='${date.getMinute() }'/>
</p>
<p><fmt:message key="userCabinetResOrder.unloadLocality"/></p><p><input type="text" name="unload" disabled value='${unloadLocality.name}'/></p>
<p><fmt:message key="userCabinetResOrder.consignee"/></p><p><input type="text" name="consignee" pattern="^.{1,45}$" required placeholder=<fmt:message key="userCabinetResOrder.consignee"/>value='${consignee }' /></p>
<p><fmt:message key="userCabinetResOrder.unloadAddress"/></p><p><input type="text" name="unloadAddress" pattern="^.{1,45}$" required placeholder=<fmt:message key="userCabinetResOrder.unloadAddress"/>value='${unloadAddress }' /></p>
<p><fmt:message key="userCabinetResOrder.distance"/></p><p><input type="text" name="distanceD" disabled value='${distanceD}'/></p>
<p><fmt:message key="userCabinetResOrder.weight"/></p><p><input type="text" name="usedWeight" disabled value='${weight}'/></p>
<p><fmt:message key="userCabinetResOrder.volume"/></p><p><input type="text" name="volume" disabled value='${volume}'/></p>
<p><fmt:message key="userCabinetResOrder.fare"/></p><p><input type="text" name="fare" disabled value='${totalMoney}'/></p>
<p><input type="hidden" name="cmd" value="ResumeOrder"/></p>
<p><input type="hidden" name="goTo" value="auth_user/view/cabinet.jsp"></p>
<p><input type="submit" value=<fmt:message key="userCabinetResOrder.sendOrder"/>/></p> 
</form>
</c:if>
</body>
</html>