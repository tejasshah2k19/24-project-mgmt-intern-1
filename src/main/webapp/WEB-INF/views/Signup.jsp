<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Signup</title>
</head>
<body>
	<h2>Signup</h2>


	<form action="saveuser"  method="post" >
		FirstName : <input type="text" name="firstName"  placeholder="Enter FirstName"/><Br><br> 
		LastName : <input type="text" name="lastName"/><Br><br>
		Email :<input type="text" name="email"/><Br><br>
		Password : <input type="password" name="password"/><Br><br>
		
		<input type="submit"  value="SignUP"/>
	</form>

	<a href="login">Login</a>
</body>
</html>