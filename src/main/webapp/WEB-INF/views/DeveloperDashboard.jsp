<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>PMT | Developer Dashboard</title>
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

		<%@include file="DeveloperHeader.jsp"%>
		<jsp:include page="DeveloperSidebar.jsp"></jsp:include>

		<div class="content-wrapper">
			<div class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1 class="m-0 text-dark">Developer Dashboard</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="developerdashboard">Home</a></li>
							</ol>
						</div>
					</div>
				</div>
			</div>

			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-3 col-6">
							<div class="small-box bg-info">
								<div class="inner">
									<h3>${projectCount}</h3>
									<p>My Projects</p>
								</div>
								<div class="icon">
									<i class="ion ion-briefcase"></i>
								</div>
								<a href="myprojects" class="small-box-footer">Open <i
									class="fas fa-arrow-circle-right"></i></a>
							</div>
						</div>

						<div class="col-lg-3 col-6">
							<div class="small-box bg-success">
								<div class="inner">
									<h3>${moduleCount}</h3>
									<p>My Modules</p>
								</div>
								<div class="icon">
									<i class="ion ion-cube"></i>
								</div>
								<a href="mymodules" class="small-box-footer">Open <i
									class="fas fa-arrow-circle-right"></i></a>
							</div>
						</div>

						<div class="col-lg-3 col-6">
							<div class="small-box bg-warning">
								<div class="inner">
									<h3 style="color: white">${taskCount}</h3>
									<p style="color: white">My Tasks</p>
								</div>
								<div class="icon">
									<i class="ion ion-clipboard"></i>
								</div>
								<a href="mytasks" class="small-box-footer">Open <i
									class="fas fa-arrow-circle-right"></i></a>
							</div>
						</div>

						<div class="col-lg-3 col-6">
							<div class="small-box bg-danger">
								<div class="inner">
									<h3>${dueProjects}</h3>
									<p>Due Projects</p>
								</div>
								<div class="icon">
									<i class="ion ion-alert-circled"></i>
								</div>
								<a href="myprojects?status=due" class="small-box-footer">Review <i
									class="fas fa-arrow-circle-right"></i></a>
							</div>
						</div>
					</div>

					<div class="row">
						<section class="col-lg-12 connectedSortable">
							<div class="card">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fas fa-tasks mr-1"></i> Work Snapshot
									</h3>
								</div>
								<div class="card-body table-responsive p-0">
									<table class="table table-hover text-nowrap">
										<thead>
											<tr>
												<th>Metric</th>
												<th>Count</th>
											</tr>
										</thead>
										<tbody>
											<tr><td>InProgress Projects</td><td>${inProgressProjects}</td></tr>
											<tr><td>InProgress Tasks</td><td>${inProgressTasks}</td></tr>
											<tr><td>Hold Tasks</td><td>${holdTasks}</td></tr>
											<tr><td>Pipeline Tasks</td><td>${pipelineTasks}</td></tr>
											<tr><td>Completed Tasks</td><td>${completedTasks}</td></tr>
											<tr><td>Due Tasks</td><td>${dueTasks}</td></tr>
										</tbody>
									</table>
								</div>
							</div>
						</section>
					</div>

					<div class="row">
						<section class="col-lg-12 connectedSortable">
							<div class="card">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fas fa-chart-bar mr-1"></i> Daily Working Hours (${currentMonthLabel})
									</h3>
								</div>
								<div class="card-body">
									<canvas id="workHoursChart" height="90"></canvas>
								</div>
							</div>
						</section>
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
	<script>
		var workHoursCtx = document.getElementById('workHoursChart').getContext('2d');
		new Chart(workHoursCtx, {
			type : 'bar',
			data : {
				labels : [ ${dayLabels} ],
				datasets : [ {
					label : 'Hours',
					data : [ ${dayHours} ],
					backgroundColor : 'rgba(54, 162, 235, 0.35)',
					borderColor : 'rgba(54, 162, 235, 1)',
					borderWidth : 1
				} ]
			},
			options : {
				responsive : true,
				maintainAspectRatio : false,
				scales : {
					yAxes : [ {
						ticks : {
							beginAtZero : true
						}
					} ]
				}
			}
		});
	</script>
</body>
</html>
