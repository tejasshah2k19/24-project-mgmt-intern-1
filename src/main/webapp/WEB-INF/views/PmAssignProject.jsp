<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>PMT | PM Assign Project</title>
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
							<h1 class="m-0 text-dark">Assign Project</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="pmdashboard">Home</a></li>
								<li class="breadcrumb-item active">Assign Project</li>
							</ol>
						</div>
					</div>
				</div>
			</section>

			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-6">

							<c:if test="${msg == 'alreadyAssigned'}">
								<div class="alert alert-warning">User is already assigned to this project.</div>
							</c:if>
							<c:if test="${msg == 'reAssigned'}">
								<div class="alert alert-info">User was re-assigned to this project.</div>
							</c:if>
							<c:if test="${msg == 'assigned'}">
								<div class="alert alert-success">Project assigned successfully.</div>
							</c:if>
							<c:if test="${msg == 'invalid'}">
								<div class="alert alert-danger">Please select valid project and user.</div>
							</c:if>
							<c:if test="${msg == 'unauthorized'}">
								<div class="alert alert-danger">You can assign users only for projects allocated to you.</div>
							</c:if>

							<div class="card card-info">
								<div class="card-header">
									<h3 class="card-title">Project Allocation</h3>
								</div>
								<form class="form-horizontal" action="pmassignproject" method="post">
									<div class="card-body">
										<div class="form-group row">
											<label class="col-sm-2 col-form-label">Project</label>
											<div class="col-sm-10">
												<select id="projectId" name="projectId" class="form-control" onchange="reloadUsersForProject()">
													<option value="-1">----Please Select Project----</option>
													<c:forEach items="${projects}" var="p">
														<option value="${p.projectId}" <c:if test="${selectedProjectId == p.projectId}">selected="selected"</c:if>>${p.title}</option>
													</c:forEach>
												</select>
											</div>
										</div>

										<div class="form-group row">
											<label class="col-sm-2 col-form-label">User</label>
											<div class="col-sm-10">
												<select name="userId" class="form-control">
													<option value="-1">----Please Select User----</option>
													<c:forEach items="${users}" var="u">
														<option value="${u.userId}">${u.firstName} : ${u.email}</option>
													</c:forEach>
												</select>
												<c:if test="${selectedProjectId != null and selectedProjectId > 0 and empty users}">
													<small class="text-muted">All users are already mapped to this project.</small>
												</c:if>
											</div>
										</div>
									</div>

									<div class="card-footer">
										<button type="submit" class="btn btn-info">Assign</button>
										<a href="pmlistprojects" class="btn btn-default float-right">Cancel</a>
									</div>
								</form>
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
		function reloadUsersForProject() {
			const projectId = document.getElementById('projectId').value;
			if (projectId && projectId !== '-1') {
				window.location.href = 'pmassignproject?projectId=' + projectId;
			} else {
				window.location.href = 'pmassignproject';
			}
		}
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
