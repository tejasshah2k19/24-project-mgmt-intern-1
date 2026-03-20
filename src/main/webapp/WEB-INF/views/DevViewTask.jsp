<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>PMT | Dev View Task</title>
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
					<h1 class="m-0 text-dark">Task Detail</h1>
				</div>
			</section>
			<section class="content">
				<div class="container-fluid">
					<c:if test="${param.msg == 'saved'}">
						<div class="alert alert-success">Efforts saved successfully.</div>
					</c:if>
					<c:if test="${param.msg == 'edited'}">
						<div class="alert alert-success">Effort log updated successfully.</div>
					</c:if>
					<div class="card">
						<div class="card-body">
							<b>Project:</b> ${project.title}<br>
							<b>Module:</b> ${module.moduleName}<br>
							<b>Task:</b> ${task.title}<br>
							<b>Status:</b>
							<c:if test="${task.statusId == 1}">InProgress</c:if>
							<c:if test="${task.statusId == 2}">Hold</c:if>
							<c:if test="${task.statusId == 3}">Completed</c:if>
							<c:if test="${task.statusId == 4}">Pipeline</c:if>
							<c:if test="${task.statusId == 5}">Due</c:if>
							<br>
							<b>Estimated Minutes:</b> ${task.estimatedMinutes}<br>
							<b>Utilized Minutes:</b> ${task.totalUtilizedMinutes}<br>
							<b>Description:</b> ${task.description}<br><br>
							<a href="devlogefforts?taskId=${task.taskId}" class="btn btn-info">Log Efforts</a>
						</div>
					</div>

					<div class="card">
						<div class="card-header">
							<h3 class="card-title">My Effort Logs For This Task</h3>
						</div>
						<div class="card-body table-responsive p-0">
							<table class="table table-hover text-nowrap">
								<thead>
									<tr>
										<th>Date</th>
										<th>Time</th>
										<th>Utilized Minutes</th>
										<th>Comments</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${empty effortLogs}">
										<tr>
											<td colspan="5">No logs found for this task yet.</td>
										</tr>
									</c:if>
									<c:forEach items="${effortLogs}" var="log">
										<tr>
											<td>${log.logDate}</td>
											<td>${log.logTime}</td>
											<td>${log.utilizedMinutes}</td>
											<td>${log.comments}</td>
											<td><a href="devediteffort?logId=${log.logId}">Edit</a></td>
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
