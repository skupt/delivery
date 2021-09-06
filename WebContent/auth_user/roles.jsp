<%@page import="rozaryonov.delivery.dao.DeliveryConnectionPool"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.List" import="rozaryonov.delivery.entities.Role"
	import="rozaryonov.delivery.dao.impl.RoleDao" 
	import="java.sql.Connection"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Roles</title>
</head>
<body>
<% 
Connection cn = DeliveryConnectionPool.getConnection();
RoleDao rd = new RoleDao(cn);
List<Role> rl = (List<Role>) rd.findAll();
for (Role r : rl) {
out.println(r.getId() + " - " + r.getName());	
} %>
</body>
</html>