<%@ page contentType="text/html;charset=UTF-8"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/layout.css">
<!--头部-->
<div class="header-public">
	<div class="header-public-main">
		<div class="header-public-logo">
			<h1>
				<a href="${ctx}"> <img title="同行者" alt="同行者" src="${ctx}/themes/default/image/logo_white.png">
				</a>
			</h1>
		</div>	
		<%--<div class="user-address">
			<p class="show-city" data-code="guangzhou" id="show_city">
				<span>广州</span> <img src="${ctx}/themes/default/image/other/dropdown_icon.png" class="dropdown-icon">
			</p>
			<div class="city-box">
				<p class="current-city" id="j7">
					<span>当前：</span>广州
				</p>
				<p class="hot-city">热门城市：</p>
				<ul>
					<li class="hot-city-li"><a href=""><span  name="beijing">北京</span></a></li>
				</ul>
				<a href="/choose-city-info.html" class="more-city">更多城市&gt;</a>
			</div>
		</div>--%>
<%--		<div class="header-public-search">
			<input name="" value="" type="text" placeholder="搜索活动" id="pub-search"> <a href="javascript:void(0)" id="searchBtn" onclick=""> <img title="搜索" src="${ctx}/themes/default/image/other/searcnBtn.png">
			</a>
		</div>	--%>
		<ul class="header-public-nav-r">
			<li><a  href="${ctx}">首页</a></li>
			<c:if test="${empty sessionScope.currentUser}">
				<li><a id="login_btn" href="javascript:void(0);">用户登录</a></li>
			</c:if>
			<c:if test="${not empty sessionScope.currentUser}">
				<li class="header-user">
					<div class="header-user-info">
						<div class="user-logo" style="background-image: url(${sessionScope.currentUser.logo}">
						</div>
						<span class="user-name">${sessionScope.currentUser.realname} <img src="${ctx}/themes/default/image/other/dropdown_icon.png" class="dropdown-icon"></span>
					</div>
					<div class="user-info">
						<ul class="user-info-nav">
							<li>
								<p class="f16" id="home_page">我的主页</p>
							</li>
							<li>
								<p class="f16" id="logout" >退出</p>
							</li>
						</ul>

					</div>
				</li>
			</c:if>

			<li><a href="${ctx}/page/about.jsp" id="">关于同行者</a></li>
			<li class="download-app"><a href="javascript:void(0)" rel="同行者">下载App</a>
				<div class="qr-code">
					<img src="${ctx}/image/appqr_code.png" alt="">
				</div>
			</li>			
		</ul>
	</div>
</div>
<div class="header-public-bot"></div>
<div class="f-def-dialog" id="login_dialog">
	<div class="f-dialog-shadow"></div>
	<div class="f-dialog-content">
		<span class="close-icon"><i class="iconfont icon-close"></i></span>
		<div class="dialog-header">
			<span class="title">登录</span>			
		</div>
		<div class="dialog-detail">
			<form id="my_from" class="layui-form" method="post">
				<div class="layui-form-item">
					<label class="layui-form-label login-lable"><span class="iconfont icon-my"></span></label>
					<div class="layui-input-block login-input">
						<input type="text" name="loginName" lay-verify="loginName" autocomplete="off" placeholder="请输入您的账号" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label login-lable"><span class="iconfont icon-lock"></span></label>
					<div class="layui-input-block login-input">
						<input type="password" name="password" lay-verify="password" autocomplete="off" placeholder="请输入密码" class="layui-input">
					</div>
				</div>
				<button type="button" class="layui-btn layui-btn-danger login-btn" id="login-btn"  lay-submit lay-filter="login-form">登录</button>
			</form>
		</div>
	</div>
</div>
<div class="right-nav-content"><a class="dn" id="top_back" href="javascript:void(0);"><i class="iconfont icon-totop f30"></i></a></div>
<script>
	layui.use(['form'], function () {
		var form = layui.form();
		form.verify({
			loginName:function (value) {
				if (!util.isValid(value)){
					return "登录名不能为空";
				}
			},
			password:function (value) {
				if (!util.isValid(value)){
					return "登录密码不能为空";
				}
			}
		});
		
		// 登录
		form.on('submit(login-form)', function(data) {
			$.ajax({
				url : '${ctx}/user/login/login.do',
				type : 'POST',
				async : false, // 使用同步的方法
				data : $('#my_from').serialize(),
				dataType : 'json',
				success : function(res) {
					if (res.success) {
						location.href = "${ctx}/system/main/view.do";
					} else {
						layer.msg(res.description, {
							icon : 5
						});
						console.log(res.description);
					}
				}
			});
		});
	});
	$(function(){
		$('.f-def-dialog .close-icon').click(function(){
			$(this).closest('.f-def-dialog').fadeOut();
		});
		$('#login_btn').click(function(){
			$('#login_dialog').fadeIn();
		});
		
		// 回车登录
		$("#my_from").keydown(function() {
			var e = e || event, keycode = e.which || e.keyCode;
			if (keycode == 13) {
				$("#login-btn").trigger('click');
			}
		});
	
		$('#logout').on('click', function () {
			$.post('${ctx}/user/login/logout.do',function (res) {
				if (res.success){
					location.href =  "${ctx}/user/login/home.do"
				}
				else {
					layer.alert(res.description, {icon: 5});
				}
			});
		});
	
		$('#home_page').on('click', function () {
			location.href = '${ctx}/system/main/view.do';
		});
	})
	
</script>