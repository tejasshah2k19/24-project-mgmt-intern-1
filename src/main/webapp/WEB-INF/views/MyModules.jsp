<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>PMT | My Modules</title>
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
					<div class="row mb-2">
						<div class="col-md-6">
							<h1 class="m-0 text-dark">My Modules (Assigned Task Wise)</h1>
						</div>
					</div>
				</div>
			</section>
			<section class="content">
				<div class="container-fluid">
					<c:if test="${empty projectModuleMap}">
						<div class="alert alert-info">No assigned modules found for you.</div>
					</c:if>
					<c:forEach items="${projectModuleMap}" var="entry">
						<div class="card">
							<div class="card-header">
								<h3 class="card-title">${projectMap[entry.key].title}</h3>
							</div>
							<div class="card-body table-responsive p-0">
								<table class="table table-hover text-nowrap">
									<thead>
										<tr>
											<th>Module</th>
											<th>Status</th>
											<th>Estimated Hours</th>
											<th>Utilized Hours</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${entry.value}" var="m">
											<tr>
												<td>${m.moduleName}</td>
												<td><c:if test="${m.statusId == 1}">InProgress</c:if> <c:if
														test="${m.statusId == 2}">Hold</c:if> <c:if
														test="${m.statusId == 3}">Completed</c:if> <c:if
														test="${m.statusId == 4}">Pipeline</c:if> <c:if
														test="${m.statusId == 5}">Due</c:if></td>
												<td>${m.estimatedHours}</td>
												<td>${m.totalUtilizedHours}</td>
												<td><a href="devlisttask?moduleId=${m.moduleId}">Tasks</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</c:forEach>
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
