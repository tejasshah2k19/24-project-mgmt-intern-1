<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>PMT | PM Tasks</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
<link rel="stylesheet"
	href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
<link rel="stylesheet"
	href="plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
<link rel="stylesheet" href="plugins/icheck-bootstrap/icheck-bootstrap.min.css">
<link rel="stylesheet" href="plugins/jqvmap/jqvmap.min.css">
<link rel="stylesheet" href="dist/css/adminlte.min.css">
<link rel="stylesheet"
	href="plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
<link rel="stylesheet" href="plugins/daterangepicker/daterangepicker.css">
<link rel="stylesheet" href="plugins/summernote/summernote-bs4.css">
<link
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700"
	rel="stylesheet">
</head>
<body class="hold-transition sidebar-mini layout-fixed">
	<div class="wrapper">
		<jsp:include page="AdminHeader.jsp"></jsp:include>
		<jsp:include page="PMSidebar.jsp"></jsp:include>

		<div class="content-wrapper">
			<section class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-md-6">
							<h1 class="m-0 text-dark">Task Management</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="pmdashboard">Home</a></li>
								<li class="breadcrumb-item active">Tasks</li>
							</ol>
						</div>
					</div>
				</div>
			</section>

			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-12">
							<div class="card">
								<div class="card-body table-responsive p-0">
									<table class="table table-hover text-nowrap">
										<thead>
											<tr>
												<th>Project</th>
												<th>Module</th>
												<th>Task</th>
												<th>Status</th>
												<th>Estimated Minutes</th>
												<th>Utilized Minutes</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${tasks}" var="t">
												<tr>
													<td>${projectMap[t.projectId].title}</td>
													<td>${moduleMap[t.moduleId].moduleName}</td>
													<td>${t.title}</td>
													<td><c:if test="${t.statusId == 1}">InProgress</c:if> <c:if
															test="${t.statusId == 2}">Hold</c:if> <c:if
															test="${t.statusId == 3}">Completed</c:if> <c:if
															test="${t.statusId == 4}">Pipeline</c:if> <c:if
															test="${t.statusId == 5}">Due</c:if></td>
													<td>${t.estimatedMinutes}</td>
													<td>${t.totalUtilizedMinutes}</td>
													<td><a href="taskusers?taskId=${t.taskId}">Users</a> | <a
														href="pmnewtask?moduleId=${t.moduleId}">Add Task</a> | <a
														href="viewtask?taskId=${t.taskId}">View</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
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
	<script>$.widget.bridge('uibutton', $.ui.button)</script>
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
