<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String usersId = "";
	if(request.getParameter("usersId")!=null){
		usersId = request.getParameter("usersId");
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
                    <h3 class="page-header">用户信息</h3>
                </div>
                <hr>
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">添加</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-8">
									<form id="form_sample_1" role="form" class="form-horizontal" >
										<input type="hidden" id="usersId" name="sysUsers.usersId" value="" />
										<div class="form-group">
											<label class="col-sm-2 control-label">用户账号:<span class="requreid">*</span></label>
											<div class="col-sm-8">
												<input type="text" class="form-control span6 m-wrap" id='name' maxlength="30" name="sysUsers.userAccount" data-required="1">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">用户名称:<span class="requreid">*</span></label>
											<div class="col-sm-8">
												<input type="text" class="form-control span6 m-wrap" name="sysUsers.userName" maxlength="40" data-required="1">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">用户密码:<span class="requreid">*</span></label>
											<div class="col-sm-8">
												<input type="password" id="password1" onfocus="value=''" onblur="if(document.getElementById('confirmPassword').value != null&&document.getElementById('confirmPassword').value != ''&&document.getElementById('password1').value == ''){value = document.getElementById('confirmPassword').value;}" class="form-control span6 m-wrap" name="sysUsers.userPassword" data-required="1">
												<span id="hiddenInfo" for="name"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">确认密码:<span class="requreid">*</span></label>
											<div class="col-sm-8">
												<input type="password" id="password2" onfocus="value=''" onblur="if(document.getElementById('confirmPassword').value != null&&document.getElementById('confirmPassword').value != ''&&document.getElementById('password2').value == ''){value = document.getElementById('confirmPassword').value;}" class="form-control span6 m-wrap"  name="sysUsers.userPassword1" data-required="1" onblur="" />
												<input type="hidden" id="confirmPassword" name="sysUsers.confirmPassword" value="">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">是否禁用:</label>
											<div class="col-sm-8">
												<select class="form-control" name="sysUsers.enabled">
													<option value="1" selected="selected">否</option>
													<option value="0">是</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">描述:</label>
											<div class="col-sm-8 control-label">
												<textarea name="sysUsers.userDesc" data-required="1" maxlength="512"
												class="form-control"></textarea>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">添加角色:</label>
											<div class="col-sm-8 control" id="userRole"></div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">所属合作方:</label>
											<div class="col-sm-8" >
												<select class="form-control" id="userCompany" name="sysUsers.companyId"></select>
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
		var initId = "<%=usersId%>";
		if(initId != null&&initId != ''){
			$.ajax({
				type : 'POST',
				url : "getById.action",
				dataType : "json",
				data : {usersId : initId},
				success : function(data, status) {
					if(data != null){
						document.getElementsByName("sysUsers.usersId")[0].value = data.usersId;
						document.getElementsByName("sysUsers.userAccount")[0].value = data.userAccount;
						$("#name").attr("readonly","readonly");
						document.getElementsByName("sysUsers.userName")[0].value = data.userName;
						document.getElementsByName("sysUsers.userPassword")[0].value = data.userPassword;
						document.getElementsByName("sysUsers.userPassword1")[0].value = data.userPassword;
						document.getElementsByName("sysUsers.confirmPassword")[0].value = data.userPassword;
						document.getElementsByName("sysUsers.enabled")[0].value = data.enabled;
						document.getElementsByName("sysUsers.userDesc")[0].value = data.userDesc;
						$(".panel-heading").html('修改');
					}
				}
			});
		}
		getUserRole(initId);
		function getUserRole(usersId){
			$.ajax({
				type : 'POST',
				url : "getRoleByUserId.action",
				async : false,
				data : {usersId:usersId},
				dataType : "json",
				success : function(data, status) {
					var html = "";
					if(data != null){
						$.each(data, function(index, element) {
							if(usersId == element.usersId){
								html += ("<label class='checkbox-inline'><input type='checkbox' checked name='sysUsers.rolesId' value='" + element.rolesId + "'>" + element.roleDesc + "</label>");
							}
							else{
								html += ("<label class='checkbox-inline'><input type='checkbox' name='sysUsers.rolesId' value='" + element.rolesId + "'>" + element.roleDesc + "</label>");
							}
						});
					}
					$("#userRole").html(html);
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
				"sysUsers.userAccount" : {
					required : true,
					remote : {
						type : "POST",
						url : "isExistsUser.action",
						data : {
							usersId : function(){
								return document.getElementsByName("sysUsers.usersId")[0].value;
							},
							userAccount : function() {
								return document.getElementsByName("sysUsers.userAccount")[0].value;
							}
						}
					}
				},
				"sysUsers.userName" : {
					required : true
				},
				"sysUsers.userPassword" : {
					required : true
				},
				"sysUsers.userPassword1" : {
					required : true,
					equalTo : "#password1"
				}
			},
			messages : { // custom messages for radio buttons and checkboxes
				"sysUsers.userAccount" : {
					required : "用户账号必填",
					remote : "用户账号已存在"
				},
				"sysUsers.userName" : {
					required : "用户名称必填"
				},
				"sysUsers.userPassword" : {
					required : "用户密码必填"
				},
				"sysUsers.userPassword1" : {
					required : "请确认密码",
					equalTo : "两次输入密码不一致"
				}
			},
			submitHandler : function(form) {
				var url = "insert.action";
				var usersId = document.getElementsByName("sysUsers.usersId")[0].value;
				if(usersId != null&&usersId != ''){
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
						window.location = "${basePath}system/userManage.jsp";
					}
				});
			}
		});
	</script>
	<!-- BEGIN PAGE LEVEL JS PLUGINS -->
	<!-- END PAGE LEVEL JS PLUGINS -->
</body>
</html>