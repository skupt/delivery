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
<fmt:setBundle basename="resources.msg"/>

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
		});  </script>
<!-- DateTimePicker -->
	
	<title><fmt:message key="index.title"/></title>
</head>
<body>
<div class="container">
<!-- Language switcher begin -->
<form name="locales" action="Controller" method="post">
	<select name="lang" onchange="this.form.submit()">
		<option selected disabled><fmt:message key="pageLogin.chooseLang"/></option>
		<option value="ru"><fmt:message key="pageLogin.ru"/></option>
		<option value="en"><fmt:message key="pageLogin.en"/></option>
	</select>
	<input type="hidden" name="cmd" value="SetLocale"/>
	<input type="hidden" name="goTo" value="index.jsp">
</form>
<!-- end Language switcher -->

<h1><fmt:message key="index.header"/></h1>
<c:url value="costs.jsp" var="varCost"/>
<p><a href="${varCost}"><fmt:message key="index.linkCostCalc"/></a></p>
<p><a href="tariffs.jsp"><fmt:message key="index.linkTariffs"/></a></p>
<p><a href="login.jsp"><fmt:message key="index.linkLogin"/></a></p>
<p><a href=""><fmt:message key="index.linkLogout"/></a></p>
<p><a href="register.jsp"><fmt:message key="index.linkRegister"/></a></p>

<form >
	<button type="submit" class="btn btn-info">
		<span class="glyphicon glyphicon-search"></span> Search
	</button>
	<br>
</form>

<!-- DateTimePicker -->
    <div class="row">
      <div class="col-sm-3">
        <div class="form-group">
          <div class="input-group date" id="datetimepicker1">
            <input type="text" class="form-control" />
            <span class="input-group-addon">
                <span class="glyphicon glyphicon-calendar"></span>
            </span>
          </div>
        </div>
      </div>
    </div>
<!-- DateTimePicker -->
	


</div>
</body>
</html>