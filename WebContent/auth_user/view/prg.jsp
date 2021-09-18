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

<title>Insert title here</title>
</head>
<body>
<div class="container">
<div class="alert alert-info" role="alert">
<fmt:message key="${message}" />
<a href="${goTo}"><fmt:message key="prg.goBack"/></a>
</div>

</div>
</body>
</html>