<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String rolesId = "";
	if(request.getParameter("rolesId")!=null){
		rolesId = request.getParameter("rolesId");
	}
	
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- BEGIN GLOBLE CSS FILE -->
<!-- Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<!-- metisMenu 菜单插件样式 -->
<link rel="stylesheet" href="thirdparty/metisMenu/metisMenu.min.css">
<!-- sb admin自定义的metisMenu菜单样式 -->
<link rel="stylesheet" href="thirdparty/metisMenu/sb-admin-2.css">
<!-- sb admin自定义的metisMenu菜单样式：菜单箭头 -->
<link href="thirdparty/metisMenu/font-awesome.min.css" rel="stylesheet"
	type="text/css">

<!-- DataTables CSS -->
<link href="thirdparty/datatable/dataTables.bootstrap.css"
	rel="stylesheet">
<link href="thirdparty/datatable/dataTables.responsive.css"
	rel="stylesheet">
<link href="thirdparty/metisMenu/bootstrapValidator2.min.css" rel="stylesheet"
	type="text/css">
<!-- end datatables css -->
<!-- END GLOBLE CSS FILE -->

<!-- BEGIN PAGE LEVEL STYLES -->

<!-- END PAGE LEVEL STYLES -->
<title>sdk 管理平台</title>
</head>
<body>
		<div id="container-fluid">
			<!-- 放入右侧内容 -->
			<div class="row">
				<div class="col-lg-12">
                    <h3 class="page-header">角色信息</h3>
                </div>
                <hr>
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">添加</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-8">
									<form id="form_sample_1" role="form" class="form-horizontal" >
										<input type="hidden" id="rolesId" name="sysRoles.rolesId" value="" />
										<div class="form-group">
											<label class="col-sm-2 control-label">角色名称:<span class="requreid">*</span></label>
											<div class="col-sm-8">
												<input type="text" class="form-control span6 m-wrap" id='name' maxlength="30" name="sysRoles.roleName" data-required="1">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">角色描述:<span class="requreid">*</span></label>
											<div class="col-sm-8">
												<textarea name="sysRoles.roleDesc" data-required="1" maxlength="512" class="form-control"></textarea>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">管理资源:</label>
											<div class="col-sm-8 control" id="roleRes">
											</div>
										</div>
										<div class="col-md-offset-5">
											<button type="submit" class="btn btn-default">提交</button>
											<button type="button" class="btn btn-default" onclick="javascript:window.history.go(-1);">返回</button>
	                                    </div>
									</form>
								</div>
							</div>
							<!-- /.row (nested) -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
		</div>
	<!-- BEGIN GLOBLE JS PLUGINS -->
	<script src="jquery/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="thirdparty/metisMenu/metisMenu.min.js"></script>
	<script src="thirdparty/metisMenu/sb-admin-2.js"></script>
	<!-- END GLOBLE JS PLUGINS -->

	<script src="thirdparty/datatable/jquery.dataTables.min.js"></script>
	<script src="thirdparty/datatable/dataTables.bootstrap.min.js"></script>
	<script src="thirdparty/formValidate/jquery.validate.min.js" type="text/javascript"></script>
	<script src="thirdparty/common.js"></script>
	<script type="text/javascript">
		var initId = "<%=rolesId%>";
		if(initId != null&&initId != ''){
			$.ajax({
				type : 'POST',
				url : "getById.action",
				dataType : "json",
				data : {rolesId : initId},
				success : function(data, status) {
					if(data != null){
						document.getElementsByName("sysRoles.rolesId")[0].value = data.rolesId;
						document.getElementsByName("sysRoles.roleName")[0].value = data.roleName;
						$("#name").attr("readonly","readonly");
						document.getElementsByName("sysRoles.roleDesc")[0].value = data.roleDesc;
						$(".panel-heading").html('修改');
					}
				}
			});
		}
		getRoleRes(initId);
		function getRoleRes(rolesId){
			$.ajax({
				type : 'POST',
				url : "getResByRoleId.action",
				dataType : "text",
				data : {rolesId : initId},
				success : function(data, status) {
					if(data != null){
						$("#roleRes").html(data);
					}
				}
			});
		}
		var form1 = $('#form_sample_1');
		form1.validate({
			onsubmit:true,// 是否在提交是验证 
			errorElement : 'span', //default input error message container
			errorClass : 'alert-danger', // default input error message class
			focusInvalid : false, // do not focus the last invalid input
			ignore : "",
			rules : {
				"sysRoles.roleName" : {
					required : true,
					remote : {
						type : "POST",
						url : "isExistsRole.action",
						data : {
							rolesId : function(){
								return document.getElementsByName("sysRoles.rolesId")[0].value;
							},
							roleName : function() {
								return document.getElementsByName("sysRoles.roleName")[0].value;
							}
						}
					}
				},
				"sysRoles.roleDesc" : {
					required : true
				}
			},
			messages : { // custom messages for radio buttons and checkboxes
				"sysRoles.roleName" : {
					required : "角色名称必填",
					remote : "名称不能重复"
				},
				"sysRoles.roleDesc" : {
					required : "请添加角色描述"
				}
			},
			submitHandler : function(form) {
				var url = "insert.action";
				var rolesId = document.getElementsByName("sysRoles.rolesId")[0].value;
				if(rolesId != null && rolesId != ''){
					url = "update.action";
				}
				$.ajax({
					type : 'POST',
					url : url,
					dataType : "text",
					async : false,
					data : form1.serialize(),
					success : function(respData) {
						if(respData != '操作成功'){
							setModal(respData);
							return false;
						}
						window.location = "${basePath}system/rolesManage.jsp";
					}
				});
			}
		});
		function checkSel(level,obj){
			var isSelect = $(obj).is(":checked");
			if(level == 'first'){
				if(isSelect){
					for(var i=0;i<$(obj).parent().find("ul>li>:checkbox").length;i++){
						$(obj).parent().find("ul>li>:checkbox:eq(" + i + ")").prop("checked",true);
					}
				}
				else{
					for(var i=0;i<$(obj).parent().find("ul>li>:checkbox").length;i++){
						$(obj).parent().find("ul>li>:checkbox:eq(" + i + ")").prop("checked",false);
					}
				}
			}
			else if(level == 'second'){
				if(isSelect){
					for(var i=0;i<$(obj).parent().find("ul>li>:checkbox").length;i++){
						$(obj).parent().find("ul>li>:checkbox:eq(" + i + ")").prop("checked",true);
					}
					$(obj).parent().parent().parent().find(":checkbox").first().prop("checked",true);
				}
				else{
					for(var i=0;i<$(obj).parent().find("ul>li>:checkbox").length;i++){
						$(obj).parent().find("ul>li>:checkbox:eq(" + i + ")").prop("checked",false);
					}
				}
			}
			else{
				if(isSelect){
					$(obj).parent().parent().parent().find(":checkbox").first().prop("checked",true);
					$(obj).parent().parent().parent().parent().parent().find(":checkbox").first().prop("checked",true);
				}
			}
		}
	</script>
	<!-- BEGIN PAGE LEVEL JS PLUGINS -->
	<!-- END PAGE LEVEL JS PLUGINS -->
</body>
</html>