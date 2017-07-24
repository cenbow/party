<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>发布评论</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<%@include file="../../include/commonFile.jsp"%>
<style type="text/css">
body {
	min-width: auto !important;
	background-color: white !important;
}

.layui-input-block{
	margin-left: 60px!important;
}

.layui-form-label{
	width: auto!important;
}

.index-outside {
	width: auto !important;
}

.layui-form-checkbox {
	width: 22px !important;
	height: 22px !important;
	line-height: 22px !important;
	margin: 0px !important;
	padding: 0px !important;
}

.layui-form-checkbox i {
	right: -2px !important;
	width: 22px !important;
}

.layui-form-checked, .layui-form-checked:hover {
	border: 1px solid red !important;
}
.layui-form-checked i, .layui-form-checked:hover i{
	color: red!important;
}

.layui-form-radio{
	margin: 0px !important;
	padding: 0px !important;
}
</style>
</head>
<!--头部-->
<div class="index-outside">
	<!--内容-->
	<section>
		<div class="section-main">
			<form id="myForm" class="layui-form" method="post" action="${ctx}/dynamic/comment/save.do">
				<input type="hidden" name="id" value="${comment.id}" />
				<div class="layui-form-item">
					<label class="layui-form-label">评论<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<textarea name="content" class="layui-textarea" style="resize: none" id="content">${comment.content}</textarea>
					</div>
				</div>
			</form>
		</div>
	</section>
</div>
<!--底部-->
<script type="text/javascript">
	function doSubmit(){
		var content = $("#content").val();
		if(content == ""){
			top.layer.msg("请填写评论", {
				icon : 7
			});
			return false;
		}
		var isRepeat = false;
		$.ajax({
			type : 'POST',
			async : false, // 使用同步的方法
			data : $('#myForm').serialize(),
			dataType : 'json',
			success : function(res) {
				isRepeat = res.success;
				if(isRepeat){
					top.layer.msg('保存成功', {icon : 1});
				} else {
					top.layer.msg('保存失败', {icon : 2});
				}
			},
			url : $("#myForm").attr("action")
		});
		return isRepeat;
	}
</script>
</body>
</html>