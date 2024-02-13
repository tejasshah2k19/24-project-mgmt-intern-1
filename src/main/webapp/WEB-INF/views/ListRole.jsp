<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>List Role</h2>

	<table border="1">
		<tr>
			<th>RoleId</th>
			<th>RoleName</th>
			<th>Action</th>
		</tr>

		<c:forEach items="${r}" var="a">

			<tr>
				<td>${a.roleId}</td>
				<td>${a.roleName}</td>
				<td><a href="deleterole?roleId=${a.roleId}">Delete</a></td>
			</tr>
		</c:forEach>

	</table>


</body>
</html>