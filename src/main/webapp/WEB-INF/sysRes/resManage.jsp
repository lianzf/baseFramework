<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- BEGIN GLOBLE CSS FILE -->
<!-- Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">
<!-- metisMenu 菜单插件样式 -->
<link rel="stylesheet" href="../thirdparty/metisMenu/metisMenu.min.css">
<!-- sb admin自定义的metisMenu菜单样式 -->
<link rel="stylesheet" href="../thirdparty/metisMenu/sb-admin-2.css">
<!-- sb admin自定义的metisMenu菜单样式：菜单箭头 -->
<link href="../thirdparty/metisMenu/font-awesome.min.css" rel="stylesheet" type="text/css">
<!-- DataTables CSS -->
<link href="../thirdparty/datatable/dataTables.bootstrap.css" rel="stylesheet">
<link href="../thirdparty/datatable/dataTables.responsive.css" rel="stylesheet">
<!-- end datatables css -->
<!-- END GLOBLE CSS FILE -->
<!-- BEGIN PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
<title>sdk 管理平台</title>
</head>
<body>
	<div class="container-fluid">
		<!-- 放入右侧内容 -->
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">资源管理</h3>
			</div>
			<hr>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-table fa-fw"></i>列表
						</div>
						<!-- /.panel-heading -->
						<br />
						<div class="col-lg-12">
							<div class="form-inline">
								<div class="form-group">
									<input id='resourceName' type="text" class="form-control" placeholder="资源名称">
								</div>
								<div class="form-group">
									<input id='resourceDesc' type="text" class="form-control" placeholder="资源描述">
								</div>
								<div class="form-group">
									<input id='resourceString' type="text" class="form-control" placeholder="资源串">
								</div>
								<div class="form-group">
									<button id="search" class="btn btn-default" type="button">
										<i class="fa fa-search"></i>
									</button>
									<button class="btn btn-default" type="button" onclick="window.location = '../menu_add.action?type=resourceAdd';">
										<i class="fa fa-plus"></i>
									</button>
								</div>
							</div>
						</div>
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example">
									<thead>
										<tr>
											<th>资源ID</th>
											<th>资源名称</th> 
											<th>资源描述</th> 
											<th>资源类型</th>
											<th>资源串</th>
											<th>操作</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
		</div>
	</div>
	<!-- BEGIN GLOBLE JS PLUGINS -->
	<script src="../jquery/jquery-2.1.4.min.js"></script>
	<script src="../bootstrap/js/bootstrap.min.js"></script>
	<script src="../thirdparty/metisMenu/metisMenu.min.js"></script>
	<script src="../thirdparty/metisMenu/sb-admin-2.js"></script>
	<!-- END GLOBLE JS PLUGINS -->
	<script src="../thirdparty/datatable/jquery.dataTables.min.js"></script>
	<script src="../thirdparty/datatable/dataTables.bootstrap.min.js"></script>
	<script src="../thirdparty/common.js"></script>
	<script>
	jQuery(document).ready(function() {
		initPage();
	});
	$("#search").bind('click', function() {
		initPage();
	});
	function initPage() {
		$(document).ready(function() {
			var resourceName = document.getElementById("resourceName").value;
			var resourceDesc = document.getElementById("resourceDesc").value;
			var resourceString = document.getElementById("resourceString").value;
			var oTable = $('#dataTables-example').dataTable({
				"bPaginate" : true, //翻页功能 默认为true
				"bLengthChange" : false, //改变每页显示数据数量 可选的每页展示的数据数量，默认为10条
				"bFilter" : true, //过滤功能
				"bSort" : false, //排序功能 默认为true
				"bInfo" : true,//页脚信息 默认为true
				"bAutoWidth" : true,//自动宽度
				"sScrollX" : '100%',
				"iDisplayLength" : 10,
				"bDestroy" : true,
				"bServerSide" : true,
				"searching" : false,
				"fnServerParams" : function(aoData) {
					aoData.push({"name" : "resourceName","value" : resourceName});
					aoData.push({"name" : "resourceDesc","value" : resourceDesc});
					aoData.push({"name" : "resourceString","value" : resourceString});
				},
				"sServerMethod" : "POST",
				"sAjaxSource" : "getList.action",
				"aoColumns" : [{
					"sClass" : "center",
					"mDataProp" : "resourceId"
				},{
					"sClass" : "center",
					"mDataProp" : "resourceName"
				},{
					"sClass" : "center",
					"mDataProp" : "resourceDesc"
				},{
					"sClass" : "center",
					"mDataProp" : "resourceType"
				},{
					"sClass" : "center",
					"mDataProp" : "resourceString"
				},{
					"sClass" : "center",
					"mDataProp" : "resourceId",
					"mRender" : function(data, type, full) {
						return "<a class='del' href='javascript:;' onclick='editResource("+data+")'>修改</a>";
					}
				} ],
				"oLanguage" : { // 转移英文显示文字
					"sProcessing" : "处理中...", // 进度条显示信息 
					"sZeroRecords" : "没有匹配的记录", // 无记录的情况下显示的表格信息
					"sInfo" : "显示第 _START_ 至 _END_ 项记录，共 _TOTAL_ 项", // bInfo为true时，此处页脚显示信息
					"sInfoEmpty" : "显示第 0 至 0 项记录，共 0 项", // 当没有数据时显示的页脚信息
					"sInfoFiltered" : "(由 _MAX_ 项记录过滤)",
					"sInfoPostFix" : "",
					"oPaginate" : {
						"sFirst" : "首页",
						"sPrevious" : "上页",
						"sNext" : "下页",
						"sLast" : "尾页"
					}
				}, //多语言配置
			});
		});
	}
	function editResource(id){
		window.location = "../menu_add.action?type=resourceAdd&resourceId="+id;
	}
	</script>
	<!-- BEGIN PAGE LEVEL JS PLUGINS -->
	<!-- END PAGE LEVEL JS PLUGINS -->
	
</body>
</html>