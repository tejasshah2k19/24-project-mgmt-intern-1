<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>PMT | Task Users</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
<link rel="stylesheet"
	href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
<link rel="stylesheet"
	href="plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
<link rel="stylesheet"
	href="plugins/icheck-bootstrap/icheck-bootstrap.min.css">
<link rel="stylesheet" href="plugins/jqvmap/jqvmap.min.css">
<link rel="stylesheet" href="dist/css/adminlte.min.css">
<link rel="stylesheet"
	href="plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
<link rel="stylesheet"
	href="plugins/daterangepicker/daterangepicker.css">
<link rel="stylesheet" href="plugins/summernote/summernote-bs4.css">
<link
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700"
	rel="stylesheet">
</head>
<body class="hold-transition sidebar-mini layout-fixed">
	<div class="wrapper">

		<jsp:include page="AdminHeader.jsp"></jsp:include>
		<jsp:include page="AdminSidebar.jsp"></jsp:include>

		<div class="content-wrapper">
			<section class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-md-8">
							<h1 class="m-0 text-dark">${project.title} : ${module.moduleName} : ${task.title}</h1>
						</div>
						<div class="col-sm-4">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="admindashboard">Home</a></li>
								<li class="breadcrumb-item active">Task Users</li>
							</ol>
						</div>
					</div>
				</div>
			</section>

			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-12">
							<c:if test="${msg == 'assigned'}">
								<div class="alert alert-success">User assigned to task successfully.</div>
							</c:if>
							<c:if test="${msg == 'reAssigned'}">
								<div class="alert alert-info">User re-assigned to task successfully.</div>
							</c:if>

							<div class="card">
								<div class="card-header">
									<a href="assigntask?taskId=${task.taskId}">Assign New User</a>
								</div>

								<div class="card-body table-responsive p-0">
									<ul class="nav nav-pills p-2">
										<li class="nav-item"><a class="nav-link active" href="#active" data-toggle="tab">Active</a></li>
										<li class="nav-item"><a class="nav-link" href="#hold" data-toggle="tab">On Hold</a></li>
										<li class="nav-item"><a class="nav-link" href="#revoked" data-toggle="tab">Revoked</a></li>
									</ul>

									<div class="tab-content p-2">
										<div class="tab-pane active" id="active">
											<table class="table table-hover text-nowrap">
												<thead>
													<tr>
														<th>First Name</th>
														<th>Last Name</th>
														<th>Email</th>
														<th>Role</th>
														<th>Action</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${users}" var="u">
														<tr>
															<td>${u.firstName}</td>
															<td>${u.lastName}</td>
															<td>${u.email}</td>
															<td>${u.role}</td>
															<td>
																<a href="taskrevoke?taskId=${task.taskId}&userId=${u.userId}&status=2">Revoke</a> |
																<a href="taskrevoke?taskId=${task.taskId}&userId=${u.userId}&status=3">Hold</a>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

										<div class="tab-pane" id="hold">
											<table class="table table-hover text-nowrap">
												<thead>
													<tr>
														<th>First Name</th>
														<th>Last Name</th>
														<th>Email</th>
														<th>Role</th>
														<th>Action</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${usersHold}" var="u">
														<tr>
															<td>${u.firstName}</td>
															<td>${u.lastName}</td>
															<td>${u.email}</td>
															<td>${u.role}</td>
															<td>
																<a href="taskrevoke?taskId=${task.taskId}&userId=${u.userId}&status=1">Re-Assign</a> |
																<a href="taskrevoke?taskId=${task.taskId}&userId=${u.userId}&status=2">Revoke</a>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

										<div class="tab-pane" id="revoked">
											<table class="table table-hover text-nowrap">
												<thead>
													<tr>
														<th>First Name</th>
														<th>Last Name</th>
														<th>Email</th>
														<th>Role</th>
														<th>Action</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${usersRevoke}" var="u">
														<tr>
															<td>${u.firstName}</td>
															<td>${u.lastName}</td>
															<td>${u.email}</td>
															<td>${u.role}</td>
															<td>
																<a href="taskrevoke?taskId=${task.taskId}&userId=${u.userId}&status=1">Re-Assign</a> |
																<a href="taskrevoke?taskId=${task.taskId}&userId=${u.userId}&status=3">Hold</a>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>

		<jsp:include page="AdminFooter.jsp"></jsp:include>

		<aside class="control-sidebar control-sidebar-dark"></aside>
	</div>

	<script src="plugins/jquery/jquery.min.js"></script>
	<script src="plugins/jquery-ui/jquery-ui.min.js"></script>
	<script>
		$.widget.bridge('uibutton', $.ui.button)
	</script>
	<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="plugins/chart.js/Chart.min.js"></script>
	<script src="plugins/sparklines/sparkline.js"></script>
	<script src="plugins/jqvmap/jquery.vmap.min.js"></script>
	<script src="plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
	<script src="plugins/jquery-knob/jquery.knob.min.js"></script>
	<script src="plugins/moment/moment.min.js"></script>
	<script src="plugins/daterangepicker/daterangepicker.js"></script>
	<script
		src="plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
	<script src="plugins/summernote/summernote-bs4.min.js"></script>
	<script
		src="plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
	<script src="dist/js/adminlte.js"></script>
	<script src="dist/js/pages/dashboard.js"></script>
	<script src="dist/js/demo.js"></script>
</body>
</html>
