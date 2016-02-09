<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="pragma" content="no-cache">
	<title>Favorite Sites</title>
	<style>
		.menu-head {font-size: 10pt; font-weight: bold; color: black; }
		.menu-item {font-size: 10pt;  color: black }
    </style>
</head>

<body>
<%@ page import="databean.UserBean" %>

<table cellpadding="4" cellspacing="0">
    <tr>
	    <!-- Banner row across the top -->
        <td width="130" bgcolor="#99FF99">
            <img border="0" src="login2.jpg" height="75">
            <img border="0" src="login3.jpg" height="75"> </td>
        <td bgcolor="#99FF99">&nbsp;  </td>
        <td width="500" bgcolor="#99FF99">
            <p align="center">
		    <font size="7">Favorite Site</font>

			</p>
		</td>
    </tr>
	
	<!-- Spacer row -->
	<tr>
		<td bgcolor="#99FF99" style="font-size:5px">&nbsp;</td>
		<td colspan="2" style="font-size:5px">&nbsp;</td>
	</tr>
	
	<tr>
		<td bgcolor="#99FF99" valign="top" height="500">
			<!-- Navigation bar is one table cell down the left side -->
            <p align="left">

	<c:choose> 
		<c:when test="${sessionScope.user == null}" > 
				<span class="menu-item"><a href="login.do">Login</a></span><br/>
				<span class="menu-item"><a href="register.do">Register</a></span></br></br>
				<span class="menu-head">Favorite Sites From:</span><br/>
		</c:when>
		<c:otherwise>
				<span class="menu-head">${sessionScope.user.getFirstName()} ${sessionScope.user.getLastName()}</span><br/>
				<span class="menu-item"><a href="managelist.do">Manage Your Favorite Sites</a></span><br/>
				<span class="menu-item"><a href="changepassword.do">Change Password</a></span><br/>
				<span class="menu-item"><a href="logout.do">Logout</a></span><br/>
				<span class="menu-item">&nbsp;</span><br/></br>
				<span class="menu-head">Favorite Sites From:</span><br/>
		</c:otherwise>
	</c:choose>		

		<c:forEach var="user" items="${userList}">	
			    <span class="menu-item">
					<a href="viewfavorite.do?uid=${user.getUser_id()}">
						${ user.getFirstName() } ${ user.getLastName() }
					</a>
				</span>
				<br/>
		</c:forEach>

		</p>
		</td>
		
		<td>
			<!-- Padding (blank space) between navbar and content -->
		</td>
		<td  valign="top">
