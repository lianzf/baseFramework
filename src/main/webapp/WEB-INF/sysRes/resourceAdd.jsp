<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String resourceId = "";
	if(request.getParameter("resourceId")!=null){
		resourceId = request.getParameter("resourceId");
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
                    <h3 class="page-header">资源信息</h3>
                </div>
                <hr>
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">添加</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-8">
									<form id="form_sample_1" role="form" class="form-horizontal" >
										<input type="hidden" id="resourceId" name="sysResources.resourceId" value="" />
										<div class="form-group">
											<label class="col-sm-2 control-label">资源名称:<span class="requreid">*</span></label>
											<div class="col-sm-8">
												<input type="text" class="form-control span6 m-wrap" id='name' maxlength="30" name="sysResources.resourceName" data-required="1">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">资源类型:</label>
											<div class="col-sm-8">
												<select class="form-control" name="sysResources.resourceType">
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">资源串:</label>
											<div class="col-sm-8">
												<input type="text" class="form-control span6 m-wrap" id='name' maxlength="100" name="sysResources.resourceString" data-required="1">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">所属资源:</label>
											<div class="col-sm-8">
												<select class="form-control" id="allRes" name="sysResources.parentId"></select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">资源描述:</label>
											<div class="col-sm-8">
												<textarea name="sysResources.resourceDesc" data-required="1" maxlength="512" class="form-control"></textarea>
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
		var initId = "<%=resourceId%>";
		
		if(initId != null&&initId != ''){
			$.ajax({
				type : 'POST',
				url : "getById.action",
				dataType : "json",
				data : {resourceId : initId},
				success : function(data, status) {
					if(data != null){
						document.getElementsByName("sysResources.resourceId")[0].value = data.resourceId;
						document.getElementsByName("sysResources.resourceName")[0].value = data.resourceName;
						document.getElementsByName("sysResources.resourceType")[0].innerHTML = getTempType("RESOURCE_TYPE",data.resourceType);
						document.getElementsByName("sysResources.resourceString")[0].value = data.resourceString;
						document.getElementsByName("sysResources.resourceDesc")[0].value = data.resourceDesc;
						getResourceOpt(data.parentId);
						$(".panel-heading").html('修改');
					}
				}
			});
		}
		else{
			var resType = getResType();
			document.getElementsByName("sysResources.resourceType")[0].innerHTML = resType;
			getResourceOpt("");
		}

		function getResType(){
			var resType = getTempType("RESOURCE_TYPE","");
			if(resType == "empty"){
				setModal("请去数据字典配置资源类型");
			}
			else{
				return resType;
			}
		}

		function getResourceOpt(parentId){
			var option = "";
			$.ajax({
				type : 'POST',
				url : "getResourceOpt.action",
				async : false,
				data : {parentId:parentId},
				dataType : "json",
				success : function(data, status) {
					var html = "<option value='0'>请选择</option>";
					if(data != null){
						$.each(data, function(index, element) {
							if(parentId == element.resourceId){
								html += ("<option value='"+element.resourceId+"' selected>"+element.resourceDesc+"</option>");
							}
							else{
								html += ("<option value='"+element.resourceId+"'>"+element.resourceDesc+"</option>");
							}
						});
					}
					$("#allRes").html(html);
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
				"sysResources.resourceName" : {
					required : true
				}
			},
			messages : { // custom messages for radio buttons and checkboxes
				"sysResources.resourceName" : {
					required : "资源名称必填",
				}
			},
			submitHandler : function(form) {
				var url = "insert.action";
				var resourceId = document.getElementsByName("sysResources.resourceId")[0].value;
				if(resourceId != null && resourceId != ''){
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
						window.location = "${basePath}system/resManage.jsp";
					}
				});
			}
		});
	</script>
	<!-- BEGIN PAGE LEVEL JS PLUGINS -->
	<!-- END PAGE LEVEL JS PLUGINS -->
</body>
</html>