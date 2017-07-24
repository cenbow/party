<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>个人名片</title>
<%@include file="../include/commonFile.jsp"%>
<style type="text/css">

body{
	min-width: auto!important;
	background-color: white!important;
}

.info-container {
	padding-top: 10px;
	margin: 0px auto;
	width: 90%;
}

.info-container div {
	line-height: 30px !important;
}

.info-container .apply-logo {
	margin-bottom: 0;
	position: relative;
}

.layui-input {
	border: 0px;
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

.vip-big {
	position: absolute;
	right: -6px;
	bottom: 0px;
	background-color: #fff;
	display: block;
	width: 24px;
	height: 24px;
	text-align: center;
	border-radius: 50%;
	color: #e8473f;
}

.vip-big .vip-big-font{
    font-size: 26px;
    position: absolute;
    color: #e8473f;
    top: 50%;
    left: 50%;
    -webkit-transform: translate(-50%,-47%);
    transform: translate(-50%,-47%);
}

.user-infos {
	display: block;
	width: 100%;
}

.veritcal-center {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	-webkit-transform: translate(-50%, -50%);
}

.avater-big {
	width: 80px;
	height: 80px;
	display: inline-block;
	background-size: cover;
	background-repeat: no-repeat;
	background-position: center;
	position: relative;
	border: #fff 2px solid;
}
.huangguan-big {
    font-size: 20px !important;
    color: #ffc107 !important;
}

.layui-input, .layui-select, .layui-textarea{
	height: auto!important;
}

.layui-form-item .layui-input-inline{
	width: 300px!important;
}

</style>
</head>
<body>
	<div class="layui-form info-container">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">栏目图标</label>
				<div class="layui-input-inline">
					<div class="member-logo">
						<div class="user-infos">
							<div class="avater-big" id="logo" style="background-image: url('${apply.picture}'),url(../../image/avatar1.png);">
								<div class="vip-big" id="userStatus" ${member.userStatus != 1 ? 'style="display: none;"' : '' }>
									<span class="iconfont icon-vip red vip-big-font"></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">栏目名称</label>
				<div class="layui-input-inline">
					<span class="layui-input">${apply.name}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">路径</label>
				<div class="layui-input-inline">
					<span class="layui-input" style="word-break:break-all;"><a href="${apply.url}" target="_blank">${apply.url}</a></span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">所属专题</label>
				<div class="layui-input-inline">
					<span class="layui-input">${subject.name}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">栏目描述</label>
				<div class="layui-input-inline">
					<span class="layui-input">${apply.remarks}</span>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	
</script>
</html>