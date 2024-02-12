<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>New User</h2>


<form action="saveuser" method="post">
	
	FirstName : <input type="text" name="firstName"/><br><Br>
	
	LastName : <input type="text" name="lastName"/><br><Br>
	
	Email : <input type="text" name="email"/><br><Br>
	
	Password : <input type="password" name="password"/><br><Br>
	
	Gender :    Male<input type="radio" value="male" name="gender">
				Female <input type="radio" value="female" name="gender"  checked="checked" /><br><br>
	
	ContactNum : <input type="text" name="contactNum"/><br><Br>
	
	DateOfBirth : <input type="date" name="dateOfBirth"/><br><Br>
	
	Address : 
				<textarea rows="5" cols="25" name="address"></textarea>
	<br><Br>

	State :  <select name="state">
				<option value="-1">---Please Select State----</option>
				<option value="gj">Gujarat</option>
				<option value="mh">Maha</option>
				<option value="up">UP</option>
			</select>
			<Br><br>
			
			
	
	City :
				<select name="city">
						<option value="-1">----Please Select City----</option>
						<option value="ahmedabad">Ahmedabad</option>
						<option value="surat">Surat</option>
						<option value="mumbai">Mumbai</option>
						<option value="ayodhya">Ayodhya</option>
						<option value="mahesana">GJ2</option>
				</select> 
		<br><Br>
	
	RoleId : <input type="text" name="roleId"/><br><Br>
	
	
	<input type="submit" value="Save User"/>
	 


</form>

</body>
</html>