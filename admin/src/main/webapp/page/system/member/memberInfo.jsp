<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>个人名片</title>
<%@include file="../../include/commonFile.jsp"%>
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

.info-container .member-logo {
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
	text-align: center;
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
	border-radius: 50%;
	position: relative;
	border: #fff 2px solid;
}
.huangguan-big {
    font-size: 20px !important;
    color: #ffc107 !important;
}

</style>
</head>
<body>
	<div class="layui-form info-container">
		<div class="member-logo">
			<div class="user-infos vertical-center">
				<div class="avater-big" id="logo" style="background-image: url('${member.logo}?imageMogr2/auto-orient/thumbnail/!50p'),url(${ctx}/image/def_user_logo.png);">
					<div class="vip-big" id="userStatus" ${member.userStatus != 1 ? 'style="display: none;"' : '' }>
						<span class="iconfont icon-vip red vip-big-font"></span>
					</div>
				</div>
				<div id="username_content">
					<span class="f16 red">${member.realname}</span>
					<span
						class="iconfont icon-huangguan huangguan-big" ${member.isExpert != 1 ? 'style="display: none;"' : '' } ></span>
					<span class="f16">${member.jobTitle}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">真实姓名</label>
				<div class="layui-input-inline">
					<span class="layui-input">${member.fullname}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">性&emsp;&emsp;别</label>
				<div class="layui-input-inline">
					<span class="layui-input"><c:if test="${not empty member.sex}">${member.sex == '1' ? '男' : '女'}</c:if></span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">联系方式</label>
				<div class="layui-input-inline">
					<span class="layui-input">${member.mobile}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">微&emsp;&emsp;信</label>
				<div class="layui-input-inline">
					<span class="layui-input">${member.wechatNum}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">公司名称</label>
				<div class="layui-input-inline">
					<span class="layui-input">${member.company}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">所在地点</label>
				<div class="layui-input-inline">
					<span class="layui-input">${city.name}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">所属行业</label>
				<div class="layui-input-inline">
					<span class="layui-input">${industry.name}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">对接资源</label>
				<div class="layui-input-inline">
					<span class="layui-input">${member.wantRes}</span>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	
</script>
</html>