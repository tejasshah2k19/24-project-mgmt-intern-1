<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>New Project</h2>



<form action="saveproject" method="post">
	Title :  <input type="text" name="title"/><br><br>
	Description :   :  <input type="text" name="description"/><br><br>
	ProjectStatus :  <input type="text" name="projectStatusId"/><br><br>
	Project Doc URL :  <input type="text" name="docURL"/><br><br>
	Estimated Hours :  <input type="text" name="esitematedHours"/><br><br>
	Total Utilized Hours : :  <input type="text" name="totalUtilizedHours"/><br><br>
	Project Start Date :  <input type="text" name="projectStartDate"/><br><br>
	Project Completion Date :  <input type="text" name="projectCompletionDate"/><br><br>
	Actual Completion Date :  <input type="text" name="actualCompletionDate"/><br><br> 	

	<input type="submit" value="Save Project"/>
</form>
</body>
</html>