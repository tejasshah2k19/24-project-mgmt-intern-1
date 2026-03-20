<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>PMT | Dev Task Users</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
<link rel="stylesheet"
	href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
<link rel="stylesheet" href="dist/css/adminlte.min.css">
</head>
<body class="hold-transition sidebar-mini layout-fixed">
	<div class="wrapper">
		<jsp:include page="DeveloperHeader.jsp"></jsp:include>
		<jsp:include page="DeveloperSidebar.jsp"></jsp:include>

		<div class="content-wrapper">
			<section class="content-header">
				<div class="container-fluid">
					<h1 class="m-0 text-dark">${project.title} : ${module.moduleName} : ${task.title}</h1>
				</div>
			</section>
			<section class="content">
				<div class="container-fluid">
					<div class="alert alert-info">Total Assigned Users: <b>${totalAssignedUsers}</b></div>
					<div class="card">
						<div class="card-body table-responsive p-0">
							<table class="table table-hover text-nowrap">
								<thead>
									<tr>
										<th>First Name</th>
										<th>Last Name</th>
										<th>Email</th>
										<th>Role</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${users}" var="u">
										<tr>
											<td>${u.firstName}</td>
											<td>${u.lastName}</td>
											<td>${u.email}</td>
											<td>${u.role}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</section>
		</div>

		<jsp:include page="AdminFooter.jsp"></jsp:include>
	</div>

	<script src="plugins/jquery/jquery.min.js"></script>
	<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="dist/js/adminlte.js"></script>
</body>
</html>
