<jsp:include page="template-top.jsp" />

<p style="font-size:medium">
    To register, enter the following information. (All fields required.)
</p>

<jsp:include page="error-list.jsp" />

<p>
	<form method="post" action= "register.do">
		<table>
			<tr>
				<td>Email-address: </td>
				<td><input type="text" name="emailAddress" value="${form.emailAddress}"/></td>
			</tr>
			<tr>
				<td>First Name: </td>
				<td><input type="text" name="firstName" value="${form.firstName}"/></td>
			</tr>
			<tr>
				<td>Last Name: </td>
				<td><input type="text" name="lastName" value="${form.lastName}"/></td>
			</tr>
			<tr>
				<td>Password: </td>
				<td><input type="password" name="password" value=""/></td>
			</tr>
			<tr>
				<td>Confirm Password: </td>
				<td><input type="password" name="confirm" value=""/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="button" value="Register"/>
				</td>
			</tr>
		</table>
	</form>
</p>

<jsp:include page="template-bottom.jsp" />

