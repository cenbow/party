<%@ page contentType="text/html;charset=UTF-8"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/layout.css">
<style type="text/css">

</style>
<!--头部-->
<nav class="header-public">
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
			<%--<c:if test="${empty sessionScope.currentUser}">
				<li><a id="login_btn" href="javascript:void(0);">用户登录</a></li>
			</c:if>--%>
			<c:if test="${empty sessionScope.currentUser}">
				<li><a id="partner_login_btn" href="${ctx}/pLogin.do">机构登录</a></li>
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
							<%--<li>--%>
								<%--<p class="f16" id="myInfo">个人资料</p>--%>
							<%--</li>--%>
							<%--<li>--%>
								<%--<p class="f16" id="wallet">帐户余额</p>--%>
							<%--</li>--%>
							<li>
								<p class="f16" id="logout" >退出</p>
							</li>
						</ul>

					</div>
				</li>
				<li>
					<a href="${ctx}/system/notify/getNotifyList.do">
						<i class="icon iconfont icon-notice" style="font-size: 20px;"></i>
						<span id="notifyCount" class="notify-label">0</span>
					</a>
				</li>
			</c:if>
			<li><a href="${ctx}/page/about.jsp" id="">关于同行者</a></li>
			<li class="download-app"><a href="javascript:void(0)" rel="同行者">下载App</a>
				<div class="qr-code">
					<img src="${ctx}/image/appqr_code.png" alt="">
				</div>
			</li>
            <li><a href="${ctx}/help/help/list.do">帮助教程</a></li>
		</ul>
	</div>
</nav>
<div class="header-public-bot"></div>
<div class="f-def-dialog" id="login_dialog">
	<div class="f-dialog-shadow"></div>
	<div class="f-dialog-content">
		<span class="close-icon"><i class="iconfont icon-close"></i></span>
		<div class="dialog-header">
			<span class="title">登录</span>			
		</div>
		<div class="dialog-detail" style="width: 370px">
			<!-- 登录 -->
			<form id="my_from" class="layui-form" method="post">
				<div class="layui-form-item">
					<label class="layui-form-label login-lable"><span class="iconfont icon-my"></span></label>
					<div class="layui-input-block login-input">
						<input type="text" name="loginName" lay-verify="loginName" autocomplete="off" placeholder="请输入您的账号" class="layui-input" style="width: 257px">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label login-lable"><span class="iconfont icon-lock"></span></label>
					<div class="layui-input-block login-input">
						<input type="password" name="password" lay-verify="password" autocomplete="off" placeholder="请输入密码" class="layui-input" style="width: 257px; float: left;">
						<a id="findPwd" style="display: block; height: 38px; line-height: 38px;" onclick="resetPwd()">忘记密码</a>
					</div>
				</div>
				<button type="button" class="layui-btn layui-btn-danger login-btn" id="login-btn"  lay-submit lay-filter="login-form">登录</button>
			</form>
			<!-- 找回密码 -->
			<form id="findpwd_form" class="layui-form" method="post" style="display: none;">
				<div class="layui-form-item">
					<div class="layui-input-block login-input">
						<input type="text" name="loginName" lay-verify="mobile" autocomplete="off" placeholder="请输入手机号码" class="layui-input l" style="width: calc(100% - 120px);">
						<button id="smsbtn" type="button" onclick="sendsms()" class="layui-btn layui-btn-primary" style="width: 110px!important">发送验证码</button>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block login-input">
						<input type="text" name="verifyCode" lay-verify="verifyCode" autocomplete="off" placeholder="请输入手机验证码" class="layui-input" >
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block login-input">
						<input type="password" class="layui-input" name="password" lay-verify="password1" placeholder="请输入新密码" />
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block login-input">
						<input type="password" class="layui-input" name="repassword" lay-verify="repassword" placeholder="请再次输入确认密码" />
					</div>
				</div>
				<button type="button" class="layui-btn layui-btn-danger login-btn" id="login-btn" lay-submit lay-filter="findpwd_form">重置密码</button>
				<button type="button" class="layui-btn layui-btn-primary login-btn" onclick="login()">返回</button>
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
			},
			mobile:function(value) {
				var regex = /^((13[0-9])|(14[5|7])|(15([0-9]))|(18[0-9])|(17[0-9]))\d{8}$/;
				if (!util.isValid(value)){
					return "请输入手机号码";
				} else if (!regex.test(value)) {
					return "手机号码格式不正确";
				}
			},
			verifyCode : function(value) {
				if(value == ""){
					return "请输入手机验证码";
				}
			},
			password1 : function(value) {
				var regex = /^[a-zA-Z0-9]{6,10}$/;
				if (value == "") {
					return "密码不能为空";
				} else if (!regex.test(value)) {
					return "密码格式不正确";
				}
			},
			repassword : function(value) {
				var pwd = $("#findpwd_form").find("[name=password]").val();
				var regex = /^[a-zA-Z0-9]{6,10}$/;
				if (value == "") {
					return "确认密码不能为空";
				} else if (!regex.test(value)) {
					return "确认密码格式不正确";
				} else if(pwd != value){
					return "确认密码与新密码不匹配";
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
						location.href = "${ctx}/system/member/memberIndex.do";
					} else {
						layer.alert(res.description, {
							icon : 5
						});
						console.log(res.description);
					}
				}
			});
		});
		
		// 回车登录
		$("#my_from").keydown(function() {
			var e = e || event, keycode = e.which || e.keyCode;
			if (keycode == 13) {
				$("#login-btn").trigger('click');
			}
		});

		// 找回密码
		form.on('submit(findpwd_form)', function(data) {
			$(data.elem).removeAttr("lay-submit");
			$.ajax({
				url : '${ctx}/user/login/resetPassword.do',
				type : 'POST',
				async : false, // 使用同步的方法
				data : $('#findpwd_form').serialize(),
				dataType : 'json',
				success : function(res) {
					if (res.success) {
						layer.msg('密码重置成功', {icon : 1}, function(index){
							location.reload();
						});
					} else {
						layer.msg(res.description, {
							icon : 2
						});
					}
				}
			});
		});
	});
	$(function() {
		$('.f-def-dialog .close-icon').click(function() {
			$(this).closest('.f-def-dialog').fadeOut();
		});
		$('#login_btn').click(function() {
			$('#login_dialog').fadeIn();
			login();
		});
// 		$('#login-btn').on('click', function() {
// 			$.post('${ctx}/user/login/login.do', $('#my_from').serialize(), function(res) {
// 				if (res.success) {
// 					location.href = "${ctx}/system/member/memberIndex.do";
// 				} else {
// 					layer.alert(res.description, {
// 						icon : 5
// 					});
// 					console.log(res.description);
// 				}
// 			});
// 		});

		$('#logout').on('click', function() {
			$.post('${ctx}/user/login/logout.do', function(res) {
				if (res.success) {
					location.href = "${ctx}/pLogin.do"
				} else {
					layer.alert(res.description, {
						icon : 5
					});
				}
			});
		});

		$('#home_page').on('click', function() {
			location.href = '${ctx}/system/member/memberIndex.do';
		});

		$("#myInfo").on('click', function() {
			location.href = "${ctx}/system/member/memberIndex.do";
		});
		
		$("#wallet").on('click', function(){
			location.href = "${ctx}/wallet/orderList.do";
		});

		// 获取消息数字
        $.post("${ctx}/system/notify/getNotifyCount.do", {}, function(res){
			if(res.success){
			    $("#notifyCount").text(res.data);
			}
        });
	})
	
	var ttt = null;
	var wait = 60;
	function timeoutssss(mobile){
		var obj = $("#findpwd_form").find("#smsbtn");
		if (wait == 0) {
			$(obj).removeAttr("disabled");
			$(obj).text("发送验证码");
			wait = 60;
		} else {
			$(obj).attr("disabled", "disabled");
			$(obj).text("重新发送(" + wait + ")");
			if (wait == 60) {
				$.post('${ctx}/user/login/getCode.do', {
					"mobile" : mobile
				}, function(data) {
				});
			}
			wait--;
			ttt = setTimeout(function() {
				timeoutssss(mobile);
			}, 1000);
		}
	}

	// 发送短信
	function sendsms() {
		var mobile = $("#findpwd_form").find("[name=loginName]").val();
		if (mobile == "") {
			top.layer.alert("请先输入手机号", {
				icon : 5
			});
			return;
		}
		wait = 60;
		timeoutssss(mobile);
	}

	function login() {
		$("#login_dialog .title").text("登录");
		$("#my_from").show();
		$("#findpwd_form").hide();
		$("#findpwd_form").find("input").val("");
		var obj = $("#findpwd_form").find("#smsbtn");
		$(obj).removeAttr("disabled");
		$(obj).text("发送验证码");
		wait = 0;
		clearTimeout('ttt');
	}

	function resetPwd() {
		$("#login_dialog .title").text("找回密码");
		$("#my_from").hide();
		$("#findpwd_form").show();
		$(".f-dialog-content").css("height", "auto");
		$("#my_from").find("input").val("");
	}
</script>