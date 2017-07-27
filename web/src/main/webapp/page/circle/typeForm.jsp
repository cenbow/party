<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>${circle.id == null ? '添加' : '编辑'}圈子类型</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/form.css">
</head>
<!--头部-->
<%@include file="../include/header.jsp"%>
<div class="index-outside">
	<%@include file="../include/sidebar.jsp"%>
	<!--内容-->
	<section>
		<div class="section-main">
			<div class="c-time-list-header">
				<div class="r">
					<a href="javascript:history.back();" class="layui-btn layui-btn-radius layui-btn-danger"> <i
						class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">我创建的圈子&nbsp;&gt;&nbsp; ${type == null ? '创建' : '编辑'}圈子类型</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<div class="add-form-content">
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/circle/type/save.do">
				<input type="hidden" name="id" value="${type.id}" />
				<div class="layui-form-item">
					<label class="layui-form-label">名称<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="label" lay-verify="typeName"
							placeholder="请输入圈子类型名称" class="layui-input" value="${type.label}">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">键值<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="value" lay-verify="typeValue"
							placeholder="请输入圈子类型值" class="layui-input" value="${type.value}">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">描述</label>
					<div class="layui-input-block">
						<textarea rows="" cols="" class="layui-textarea" name="description"></textarea>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">排序号</label>
						<div class="layui-input-inline">
							<input type="text" name="sort" lay-verify="sort" class="layui-input" value="${type.sort}">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<button class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</button>
						<a href="${ctx}/circle/type/list.do" class="layui-btn layui-btn-primary">取消</a>
					</div>
				</div>
			</form>
			</div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script>
	$(function() {
		layui.use([ 'form' ], function() {
			var form = layui.form();
			//自定义验证规则
			form.verify({
				typeName : function(value) {
					if (value == "") {
						return '请填写圈子类型名称';
					}
				},
				typeValue :function(value) {
					if (value == "") {
						return '请填写圈子类型值';
					}
				},
				sort : function(value) {
					if (!util.checkNumber(value)) {
						return "请输入正确的数字";
					}
				}
			});

			//监听提交
			form.on('submit', function(data) {
				$(data.elem).removeAttr("lay-submit");
				var action = $("#myForm").attr("action");
				$.post(action, $('#myForm').serialize(), function(res) {
					if (res.success) {
						top.layer.msg('提交成功', {
							icon : 1
						}, function(index) {
							location.href = "${ctx}/circle/type/list.do";
						});
					} else {
						top.layer.msg('提交失败', {
							icon : 2
						});
					}
				});
				return false;
			});
		});
	})
</script>
</body>
</html>