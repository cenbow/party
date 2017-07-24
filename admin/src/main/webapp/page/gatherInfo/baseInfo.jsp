<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>基本信息</title>
<%@include file="../include/commonFile.jsp"%>
<style type="text/css">
body {
	min-width: auto !important;
	background-color: white !important;
}

.info-container {
	padding-top: 10px;
	margin: 0px auto;
	width: 90%;
}

.layui-input {
	border: 0px;
	display: inline-block!important;
	padding-left: 0px!important;
}

.layui-form-item {
	margin-bottom: 0px !important;
}

.layui-form-item .layui-inline {
	margin-bottom: 0px !important;
}

.layui-form-item .layui-input-inline {
	margin-bottom: 0px !important;
}

.user-infos {
	display: block;
	width: 100%;
}

.avater-big {
	width: 80px;
	height: 80px;
	display: inline-block;
	background-size: cover;
	background-repeat: no-repeat;
	background-position: center;
	border-radius: 50%;
	position: relative;
	border: #fff 2px solid;
}

.layui-form-item .layui-input-inline {
	width: 300px !important;
}

.footer-buttons {
	position: fixed;
	bottom: 0;
	right: 0;
	left: 0;
	background: #fff;
	padding: 10px;
	text-align: right
}

.footer-place {
	height: 58px;
}
</style>
</head>
<body>
	<form id="myForm" class="layui-form info-container" action="${ctx}/gatherInfo/member/verifyBaseInfo.do">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"></label>
				<div class="layui-input-inline">
					<div class="member-logo">
						<div class="user-infos">
							<div class="avater-big"
								style="background-image: url('${memberInfo.logo}'),url(../../image/avatar1.png);"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">姓&emsp;&emsp;名</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.fullname}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">性&emsp;&emsp;别</label>
				<div class="layui-input-inline">
					<span class="layui-input"><c:if test="${memberInfo.sex == 1}">男</c:if><c:if test="${memberInfo.sex == 0}">女</c:if></span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">公&emsp;&emsp;司</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.company}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">职&emsp;&emsp;务</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.jobTitle}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">手&emsp;&emsp;机</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.mobile}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">微&emsp;&emsp;信</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.wechatNum}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">城&emsp;&emsp;市</label>
				<div class="layui-input-inline">
					<span class="layui-input">${cityName}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">行&emsp;&emsp;业</label>
				<div class="layui-input-inline">
					<span class="layui-input">${industryName}</span>
				</div>
			</div>
		</div>
<!-- 		<div class="layui-form-item"> -->
<!-- 			<div class="layui-inline"> -->
<!-- 				<label class="layui-form-label">衣服尺码</label> -->
<!-- 				<div class="layui-input-inline"> -->
<%-- 					<span class="layui-input">${memberInfo.size}</span> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">自我介绍</label>
				<div class="layui-input-inline">
					<span class="layui-input" style="word-break: break-all;">${memberInfo.signature}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">组内职务</label>
				<div class="layui-input-inline">
					<span class="layui-input">${infoMember.groupJobTitle}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">审核状态</label>
			<div class="layui-input-block">
				<input type="radio" name="perfectState" lay-filter="baseStatus" value="1" title="待审核"
					${memberInfo.perfectState == "1" ? 'checked="checked"' : ''}
				 />
				<input type="radio" name="perfectState" lay-filter="baseStatus" value="2" title="通过"
					${memberInfo.perfectState == "2" ? 'checked="checked"' : ''}
				 />
				<input type="radio" name="perfectState" lay-filter="baseStatus" value="3" title="未通过"
					${memberInfo.perfectState == "3" ? 'checked="checked"' : ''}
				 />
				<input type="hidden" name="id" value="${memberInfo.id}" />
			</div>
		</div>
		<div class="footer-buttons">
			<a class="layui-btn layui-btn-danger" lay-submit lay-filter="*" href="javascript:void(0)">提交</a>
			<a class="layui-btn layui-btn-normal" href="javascript:closeSelf()">取消</a>
		</div>
		<div class="footer-place"></div>
	</form>
</body>
<script type="text/javascript">
    $(function () {
        layui.use([ 'form' ],function() {
            var form = layui.form();

            //监听提交
			form.on('submit', function(data) {
				$(data.elem).removeAttr("lay-submit");
				var action = $("#myForm").attr("action");
				$.post(action, $('#myForm').serialize(), function(res) {
					if (res.success) {
						top.layer.msg('提交成功', {
							icon : 1
						}, function(index) {
							parent.submitFunction('#myForm');
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

	function closeSelf() {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		top.layer.close(index);
	}
</script>
</html>