<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<%@ include file="meta.jsp"%>
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="/media/css/error.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-500-full-page">
	<div class="row-fluid">
		<div class="span12 page-500">
			<div class="number">500</div>
			<div class="details">
				<h3>Opps, Something went wrong.</h3>
				<p>
					We are fixing it!<br /> Please come back in a while.<br />
					<br /> <a href="<c:url value='/welcome'></c:url>">Return home</a>.
				</p>
			</div>
		</div>
	</div>



	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<script src="/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>

	<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="/media/js/bootstrap.min.js" type="text/javascript"></script>
	<!--[if lt IE 9]>
<script src="<c:url value='/media/js/excanvas.min.js'></c:url>"></script>
<script src="<c:url value='/media/js/respond.min.js'></c:url>"></script>
<![endif]-->
	<script src="/media/js/jquery.uniform.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->

	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="/media/js/app.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->

	<script>
		jQuery(document).ready(function() {
			App.init();
		});
	</script>
	<!-- END JAVASCRIPTS -->

	<!-- END BODY -->
</html>