<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Admin Panel</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="resources/css/bootstrap.css">
	<link rel="stylesheet" href="resources/css/common.css">
	<link rel="stylesheet" href="resources/css/fontawesome.css">
</head>
<body>
	
	<div class="container">
		<div class="row">
			<%--Greeting message --%>
			<div class="col-12 col-sm-8 mt-3">
					<h4>Welcome , ${loggedInUser.fullName}</h4>
			</div>
			<%--Add new task button --%>
			<div class="col-12 col-sm-4 text-right mt-3">
				<button type="button" class="btn btn-warning"
					data-toggle="modal" data-target="#addTaskModal">Create New</button>
			</div>
			<%--List of  existing task--%>
			<div class="col-12 mt-3">
				<tiles:insertAttribute name="existingtasks"/>
			</div>
			<%--Add new task modal --%>
			<tiles:insertAttribute name="addtask"/>
			
			<%--Task History modal --%>
			<tiles:insertAttribute name="taskhistorymodal"/>
		</div>
	 </div>
	 
	<script src="resources/js/jquery.js"></script>
	<script src="resources/js/sockjs-0.3.4.js"></script>
	<script src="resources/js/stomp.js"></script>
	<script src="resources/js/popper.js"></script>
	<script src="resources/js/bootstrap.js"></script>
	<script src="resources/js/jquerytmpl.js"></script>
	<script src="resources/js/library.js"></script>
	<script src="resources/js/admin.js"></script>
	<script src="resources/js/websocket.js"></script>
</body>
</html>
