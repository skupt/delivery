<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Set actual locale 2 -->
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.user"/>
<!-- End Set locale 2 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="../css/bootstrap.min.css"/>         
	<script src="../js/bootstrap.min.js"></script>       

<title><fmt:message key="userCabinet.title"/></title>
</head>
<body>

<form id="formMenu" action="/delivery/Controller" method="post">
</form>

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
						<li><button type="submit" form="formMenu" formmethod="post" name="cmd" class="btn btn-default navbar-btn" value="InvoiceUserEnter"><fmt:message key="userCabinet.invoicesUserLink"/></button></li>
						<li><button type="submit" form="formMenu" formmethod="post" name="cmd" class="btn btn-default navbar-btn" value="OrderUserResumeEnter"><fmt:message key="userCabinet.resumeOrderLink"/></button></li>
						<li><button type="submit" form="formMenu" formmethod="get" formaction="/delivery/costs.jsp" class="btn btn-default navbar-btn" ><fmt:message key="userCabinet.costCalculation"/></button></li>
						<li><a  href="/delivery/Controller?cmd=Logout"><fmt:message key="userCabinet.linkLogout" /></a></li>
						<li></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
					<li>
						<button type="button" class="btn btn-default navbar-btn">
						${person.getName()} (${person.getLogin()}).
						<fmt:message key="common.balance"/>: ${balance }
						</button>	
					</li>
				</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>


</body>
</html>