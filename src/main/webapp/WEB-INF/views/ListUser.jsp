<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

 
 	
				
 
	
	     
	
		<table border="1" align="center"> 
				<tr>
					<th>FirstName</th>
					<th>LastName</th>
					<th>Email</th>
				</tr>
				
				
				<c:forEach items="${listUser}" var="user">
						<tr>
							<Td>${ user.firstName }</Td>
							<td>${ user.lastName }</td>
							<td>${ user.email }</td>
						</tr>
				</c:forEach>
				
				 
		</table>

 

</body>
</html>