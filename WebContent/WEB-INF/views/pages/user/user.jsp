<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html>
<html lang="en">
<head>
	<title>User</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="resources/css/bootstrap.css">
	<link rel="stylesheet" href="resources/css/common.css">
	<link rel="stylesheet" href="resources/css/fontawesome.css">
</head>
<body>
	
	<div class="container">
		<div class="row">
			<div class="col-12 mt-3">
				<h4>Welcome , ${loggedInUser.fullName}</h4>
			</div>
			
			<div class="col-12 mt-3">
				<%--High priority task --%>
				<tiles:insertAttribute name="highprioritytask"/>
			</div>
			<div class="col-12 mt-3">
				<%--previously added task --%>
				<tiles:insertAttribute name="tasks"/>
			</div>
		</div>
	 </div>
	 
	 <script id="test" type="text/x-jQuery-tmpl">
		\${test}
	</script>
	 
	<script src="resources/js/jquery.js"></script>
	<script src="resources/js/sockjs-0.3.4.js"></script>
	<script src="resources/js/stomp.js"></script>
	<script src="resources/js/popper.js"></script>
	<script src="resources/js/bootstrap.js"></script>
	<script src="resources/js/jquerytmpl.js"></script>
	<script src="resources/js/library.js"></script>
	<script src="resources/js/user.js"></script>
	<script src="resources/js/websocket.js"></script>
</body>
</html>
