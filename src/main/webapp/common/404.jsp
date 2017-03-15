<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<%@ include file="meta.jsp"%>
<head>
<!-- BEGIN PAGE LEVEL STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed page-footer-fixed">
	<%@ include file="nav.jsp"%>
	<div class="page-container">
		<%@ include file="menu.jsp"%>
		<div class="page-content">
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span12 page-404">
						<h3>404</h3>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="footer.jsp"%>

	<script>
		jQuery(document).ready(function() {
			App.init();
		});
	</script>
</body>
<!-- END JAVASCRIPTS -->

<!-- END BODY -->

</html>