<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Login</title>
	<style type="text/css">
		html,body{
			background-color: #ffcf2d;
			position: relative;
			height: 100%;
			width: 100%;
			margin: 0px;
			padding: 0px;
			font-size:90%;
			font-family: 'Open Sans', sans-serif;
		}
		.table{
			height: 100%;
			width: 100%;
			border-spacing: inherit;
		}
		.login-head{
			background-color: #005dff;
			text-transform: uppercase;
			height: 60px;
			font-size: 110%;
			color: #fff;
			text-align: center;
			font-weight: 600;
		}
		.login_wrapper{
			width: 360px;
			height: 360px;
			background-color: #ffffff;
			border-radius:4px;
			margin: auto;
		}
		.heading{
			text-transform: uppercase;
			color: #fff;
			font-size: 95%;
		}
		.subheading{
			text-transform: uppercase;
			color: #005dff;
			font-size: 70%;
		}
		.padding-15{
			padding: 15px;
		}
		.mt-30{
			margin-top: 30px;
		}
		.mt-15{
			margin-top: 15px;
		}
		.text-right{
			text-align: right;
		}
		
		.btn{
			padding: 10px 25px;
			text-align: center;
			cursor: pointer;
			font-size:80%;
			border-radius:4px; 
		}
		.btn-blue,
		.btn-blue:hover,
		.btn-blue:focus{
			color: #fff;
			outline: none !important;
			border: none !important;
			background-color: #005dff;
			text-transform: uppercase;
		}
		
		.ci,
		.ci:hover,
		.ci:focus{
			background-color: #fff;
			height: 38px;
			border-radius:4px;
			padding: 15px; 
			outline: none !important;
			box-shadow: none !important;
			border:  1px solid #005dff !important;
			width: 100%;
		}
		
	</style>
</head>
<body>
	
	<table class="table">
		<tr>
			<td>
				<div class="login_wrapper">
					<table class="table">
						<tr class="login-head">
							<td>
								Login
							</td>
						</tr>
						<tr>
							<td class="padding-15">
								<form action="${requestScope.contextPath}/login" method="post" autocomplete="off">
									<div class="username">
										<div class="heading">
											Email / Username
										</div>
										<div class="subheading">
											Please enter your email
										</div>
										<div class="mt-15">
											<input type="text" class="ci" name="username" placeholder="Enter email" autocomplete="off">										
										</div>
									</div>
									
									<div class="password mt-30">
										<div class="heading">
											Password
										</div>
										<div class="subheading">
											Please enter your password
										</div>
										<div class="mt-15">
											<input type="password" class="ci" name="password" placeholder="Enter password" autocomplete="off">										
										</div>
									</div>
									
									<div class="submitbutton mt-30 text-right">
										<button class="btn btn-sm btn-blue" type="submit">Login</button>
									</div>
								</form>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>