<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<%@ include file="/common/meta.jsp"%>
<%@include file="/common/footer.jsp"%>
<!-- BEGIN HEAD -->
<head>
<style type="text/css">
</style>
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
							<li><i class="icon-list"></i><a href="＃">数据字典</a><i class="icon-angle-right"></i></li>
							<li><a href="#">列表</a></li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box blue" id="portlet">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-th"></i>列表
								</div>
							</div>
							<div class="portlet-body">
								<div id="sample_2_wrapper" class="dataTables_wrapper form-inline" role="grid">
									<div class="row-fluid">
										<div class="input-append hidden-phone">
											<input class="m-wrap medium" size="10" id="name" type="text" placeholder="键">
											<button id="search" class="btn red"><i class="icon-search"></i> 查询</button>
											<security:authorize url="/sysRes/getPage">
												<a id="add" class="btn green" ><i class="icon-plus"></i> 添加</a>
											</security:authorize>
										</div>
									</div>
									<div class="row-fluid">
										<table class="table table-striped table-bordered table-hover table-full-width dataTable" id="portlet_Tables"
											aria-describedby="sample_2_info">
											<thead>
												<tr>
													<th>键</th>
													<th>值</th>
													<th>描述</th>
													<th>操作</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		jQuery(document).ready(function() {
			App.init(); // initlayout and core plugins
			UIModals.init();
			TableAdvanced.init();
			// initPage();
			FormComponents.init();
			$('.btn.green').click(function(){
				   App.Modal.load('./add',{},{'title':'添加数据字典'});
			});

		});
		function operation(oper,id,operation){
			document.getElementById("form_sample_1").reset();
			document.getElementById("title").innerHTML = oper;
			document.getElementById("method").value = operation;
			if(operation == 'edit'){
				getDictionaryById(id);
			}
		}
		function getDictionaryById(id){
			$.ajax({
				type : 'POST',
				url : "getById",
				dataType : "json",
				data : {id : id},
				success : function(data, status) {
					if(data != null){
						document.getElementsByName("name")[0].value = data.name;
						document.getElementsByName("value")[0].value = data.value;
						document.getElementById("description").value = data.description;
						document.getElementById("initID").value = data.id;
					}
				}
			});
		}
		$("#search").bind('click', function() {
			initPage();
		});
		function initPage() {
			var name = document.getElementById("name").value;
			var oTable = $('#portlet_Tables').dataTable({
				"bLengthChange" : false, //改变每页显示数据数量 可选的每页展示的数据数量，默认为10条
				"iDisplayLength" : 50, // 默认煤业显示条数
				"bDestroy" : true,
				"bServerSide" : true, // 使用服务器端处理
				"searching" : false, // 是否增加搜索功能
				"sDom": "<'row-fluid'<f>r>t<'row-fluid'<'span6'i><'span6'p>>", // table布局
		        "aoColumnDefs": [{'bSortable': false,'aTargets': [2,3]}],
		        "aaSorting" : [[ 0, "desc" ]], 
				"fnServerParams" : function(aoData) {
					aoData.push({"name" : "name","value" : name});
				},
				"sServerMethod" : "POST",
				"sAjaxSource" : "getList",
				"aoColumns" : [{
					"sClass" : "center",
					"mDataProp" : "name"
				}, {
					"sClass" : "center",
					"mDataProp" : "value",
					"mRender" : function(obj){
						if(obj != null){
							return cutString(obj,20);
						}else{
							return "";
						}
					}
				}, {
					"sClass" : "center",
					"mDataProp" : "description",
					"mRender" : function(obj){
						if(obj != null){
							return cutString(obj,20);
						}else{
							return "";
						}
					}
				}, {
					"sClass" : "center",
					"mDataProp" : "id",
					"mRender" : function(data, type, full) {
						
						var html = "";
						html += '<a class="btn mini purple" onclick="edit('+data+')">编辑</a>';
						html += " <a class='btn mini yellow' href='javascript:;' onclick=confirm('"+data+"','您确定要删除该数据字典吗？')>删除</a>";
						return html;
					}
				} ],
				"sPaginationType": "bootstrap",
		        "oLanguage": {
		           	"sProcessing" : "处理中...",
		           	"sZeroRecords" : "没有匹配的记录", // 无记录的情况下显示的表格信息
		           	"sInfoEmpty" : "显示第 0 至 0 项记录，共 0 项", // 当没有数据时显示的页脚信息
		           	"sInfo" : "显示第 _START_ 至 _END_ 项记录，共 _TOTAL_ 项",
		           	"oPaginate": {
		           		"sFirst" : "首页",
		              	"sPrevious": "上一页",
		              	"sNext": "下一页",
		              	"sLast" : "尾页"
		           	}
				}
			});
		}
		function edit(id){
			App.Modal.load("./add",{'id' : id},{"title":"修改数据字典"});
		}
		function confirm(id,value){
			$.ajax({
				type : 'POST',
				url : './delete',
				dataType : "text",
				data : {id : id},
				success : function(data, status) {
					initPage();
				}
			})
		}
	</script>
</body>
<!-- END BODY -->
</html>