<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<%@ include file="/common/meta.jsp"%>
<!-- BEGIN HEAD -->
<head>

</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed page-footer-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="/common/nav.jsp"%>
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<%@ include file="/common/menu.jsp"%>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<ul class="breadcrumb">
							<li><i class="icon-home"></i><a href="＃">首页</a><i class="icon-angle-right"></i></li>
							<!-- <li><a href="#">修改密码</a></li> -->
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<div class="row-fluid">
					<h3>首页</h3>
				</div>
			</div>
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@include file="/common/footer.jsp"%>
	<script>
		jQuery(document).ready(function() {
			App.init(); // initlayout and core plugins
		});
	</script>
</body>
<!-- END BODY -->
</html>