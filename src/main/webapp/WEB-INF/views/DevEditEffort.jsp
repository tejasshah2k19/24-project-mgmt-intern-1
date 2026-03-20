<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>PMT | Edit Effort</title>
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
					<h1 class="m-0 text-dark">Edit Effort Log</h1>
				</div>
			</section>
			<section class="content">
				<div class="container-fluid">
					<c:if test="${msg == 'invalid'}">
						<div class="alert alert-danger">Utilized minutes must be 0 or greater.</div>
					</c:if>
					<c:if test="${msg == 'invalidDate'}">
						<div class="alert alert-danger">Please select a valid date (today or past date only).</div>
					</c:if>
					<div class="card">
						<div class="card-body">
							<form action="devediteffort" method="post">
								<input type="hidden" name="logId" value="${effortLog.logId}">
								<div class="form-group">
									<label>Project</label>
									<input type="text" class="form-control" value="${project.title}" readonly="readonly">
								</div>
								<div class="form-group">
									<label>Module</label>
									<input type="text" class="form-control" value="${module.moduleName}" readonly="readonly">
								</div>
								<div class="form-group">
									<label>Task</label>
									<input type="text" class="form-control" value="${task.title}" readonly="readonly">
								</div>
								<div class="form-group">
									<label>Status</label>
									<select name="statusId" class="form-control">
										<c:forEach items="${projectStatusList}" var="ps">
											<option value="${ps.projectStatusId}" <c:if test="${task.statusId == ps.projectStatusId}">selected="selected"</c:if>>${ps.status}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<label>Utilized Minutes</label>
									<input type="number" min="0" class="form-control" name="utilizedMinutes" value="${effortLog.utilizedMinutes}">
								</div>
								<div class="form-group">
									<label>Log Date</label>
									<input type="date" class="form-control" name="logDate" value="${effortLog.logDate}" max="${todayDate}">
								</div>
								<div class="form-group">
									<label>Comments</label>
									<textarea class="form-control" rows="4" name="comments">${effortLog.comments}</textarea>
								</div>
								<button type="submit" class="btn btn-info">Update</button>
								<a href="devviewtask?taskId=${task.taskId}" class="btn btn-default">Cancel</a>
							</form>
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
