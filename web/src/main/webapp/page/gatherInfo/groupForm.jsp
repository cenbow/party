<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>创建小组</title>
<%@include file="../include/commonFile.jsp" %>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_form.css">
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
	<%@include file="../include/sidebar.jsp"%>
	<!--内容-->
	<section>
		<div class="section-main">
			<div class="c-time-list-header">
				<div class="r">
					<a href="${ctx}/gatherInfo/group/list.do?projectId=${project.id}" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20" title="${project.title}">我发布的项目&nbsp;&gt;&nbsp;
						<c:if test="${fn:length(project.title) > 20}">${fn:substring(project.title,0,20)}...</c:if>
						<c:if test="${fn:length(project.title) <= 20}">${project.title}</c:if>
						&nbsp;&gt;&nbsp;小组管理&nbsp;&gt;&nbsp;${group == null ? '创建' : '编辑'}小组
					</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<div class="add-form-content">
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/gatherInfo/group/save.do">
				<div class="layui-form-item">
					<label class="layui-form-label">编号<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="groupNo" lay-verify="groupNo" placeholder="编号" class="layui-input" value="${group.groupNo}">
						<input type="hidden" name="id" value="${group.id}"/>
						<input type="hidden" name="projectId" value="${project.id}"/>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">名称<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="groupName" lay-verify="groupName" placeholder="名称" class="layui-input" value="${group.groupName}">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">描述</label>
					<div class="layui-input-block">
						<textarea name="remarks" class="layui-textarea">${group.remarks}</textarea>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
				   		<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/gatherInfo/group/list.do?projectId=${project.id}" class="layui-btn layui-btn-primary">取消</a>
				  	</div>
				</div>
			</form>
				<div class="add-form-content">
		</div>
	</section>
</div>

<!--底部-->
<%@include file="../include/footer.jsp" %>
<script>
    $(function () {
        layui.use(['form'], function () {
            var form = layui.form(), laydate = layui.laydate;

            //自定义验证规则
            form.verify({
                groupNo : function(value) {
                    if (value == "") {
                        return '请填写小组编号';
                    }
                },
                groupName : function(value) {
                    if (value == "") {
                        return "请填写小组名称";
                    }
                }
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/gatherInfo/group/list.do?projectId=${project.id}";
                        });
                    } else {
                        $(data.elem).attr("lay-submit","");
                        top.layer.msg('提交失败', {icon : 2});
                    }
                });
                return false;
            });
        });
    })

</script>
</body>
</html>