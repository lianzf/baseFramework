<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="modal-body">
		<div class="portlet-body form row-fluid">
			<form id="form_sample_1" action="#" class="form-horizontal dialog" onsubmit="return false;" method="post">
				<input type="hidden" id="initID" name="id" value="${dic.id}"/> 
				<div class="control-group">
					<label class="control-label">键</label>
					<div class="controls">
						<input id="name" name="name" type="text" value="${dic.name}" ${dic.id != null ? 'readonly' : ""} class="m-wrap large">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">值</label>
					<div class="controls">
						<textarea name="value" class="m-wrap large">${dic.value}</textarea>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">描述</label>
					<div class="controls">
						<textarea id="description" name="description" class="m-wrap large">${dic.description}</textarea>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn green" id="submit_btn" >保存</button>
		<button type="button" data-dismiss="modal" class="btn">关闭</button>
	</div>
<script>
	$("#submit_btn").click(function(){
		var id = $("#initID").val();
		if(id != "" && id != null){
			$("#form_sample_1").attr("action","./update");
		}else{
			$("#form_sample_1").attr("action","./insert");
		}
		if($('#form_sample_1').valid()){
			App.Ajax.submit('form_sample_1',{
				fn : function(json){
					App.Tables.refresh('portlet');
				}
			});
		}
	});
	
	App.validate('form_sample_1',{
		rules : {
			"name" : {
				required : true
			},
			"value" : {
				required : true
			}
		}
		
	});
</script>
</body>
<!-- END BODY -->
</html>