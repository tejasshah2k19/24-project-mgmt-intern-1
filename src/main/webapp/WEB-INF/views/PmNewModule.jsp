<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>PMT | PM New Module</title>
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
							<h1 class="m-0 text-dark">New Module - ${project.title}</h1>
						</div>
					</div>
				</div>
			</section>

			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-8">
							<div class="card card-info">
								<div class="card-header">
									<h3 class="card-title">Add Module</h3>
								</div>
								<form class="form-horizontal" action="pmsavemodule" method="post">
									<div class="card-body">
										<div class="form-group row">
											<label class="col-sm-3 col-form-label">Title</label>
											<div class="col-sm-9"><input type="text" class="form-control" name="moduleName"></div>
										</div>
										<div class="form-group row">
											<label class="col-sm-3 col-form-label">Estimated Hours</label>
											<div class="col-sm-9"><input type="text" class="form-control" name="estimatedHours"></div>
										</div>
										<div class="form-group row">
											<label class="col-sm-3 col-form-label">Utilized Hours</label>
											<div class="col-sm-9"><input type="text" class="form-control" name="totalUtilizedHours"></div>
										</div>
										<div class="form-group row">
											<label class="col-sm-3 col-form-label">Status</label>
											<div class="col-sm-9">
												<select name="statusId" class="form-control">
													<option value="-1">---Please Select Status----</option>
													<c:forEach items="${projectStatusList}" var="ps">
														<option value="${ps.projectStatusId}">${ps.status}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group row">
											<label class="col-sm-3 col-form-label">Doc URL</label>
											<div class="col-sm-9"><input type="text" class="form-control" name="documentURL"></div>
										</div>
										<div class="form-group row">
											<label class="col-sm-3 col-form-label">Description</label>
											<div class="col-sm-9"><textarea rows="6" class="form-control" name="description"></textarea></div>
										</div>
										<input type="hidden" name="projectId" value="${project.projectId}">
									</div>
									<div class="card-footer">
										<button type="submit" class="btn btn-info">Save Module</button>
										<a href="pmlistmodule?projectId=${project.projectId}" class="btn btn-default float-right">Cancel</a>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>

		<jsp:include page="AdminFooter.jsp"></jsp:include>
	</div>

	<script src="plugins/jquery/jquery.min.js"></script>
	<script src="plugins/jquery-ui/jquery-ui.min.js"></script>
	<script>$.widget.bridge('uibutton', $.ui.button)</script>
	<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="plugins/moment/moment.min.js"></script>
	<script src="plugins/daterangepicker/daterangepicker.js"></script>
	<script src="dist/js/adminlte.js"></script>
</body>
</html>
