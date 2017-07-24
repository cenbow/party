<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="./include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>机构登录</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/index.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/partner_login.css">
    <%@include file="./include/commonFile.jsp" %>
</head>
<!--头部-->
<div class="">
    <div class="banner-container">
        <div class="banner-bg-blur" style="background-image: url(${ctx}/image/login/p_login_banner.png)"></div>
        <img class="banner-bg" src="${ctx}/image/login/p_login_banner.png" alt="">
        <div class="login-inner-box">
            <a  href="${ctx}" class="inner-back">
                <span class="f18">< 返回首页</span>
            </a>
            <div class="inner-logo">
                <img src="${ctx}/image/login/logo" style="">
            </div>
            <div class="inner-form">
                <form id="my_from" class="layui-form my-form" method="post">
                    <div class="layui-inline">
                        <label class="layui-form-label form-label" style="">账号:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="loginName" lay-verify="loginName" autocomplete="off"
                                   placeholder="请输入您的账号" class="layui-input form-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label form-label">密码:</label>
                        <div class="layui-input-inline">
                            <input type="password" name="password" lay-verify="password" autocomplete="off" placeholder="请输入您的密码" class="layui-input form-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <div class="layui-input-inline ml30">
                            <button type="button" class="layui-btn layui-btn-danger login-btn" id="login_btn" lay-submit lay-filter="login-form">登录</button>
                        </div>
                    </div>
                </form>
                <div class="forget-pwd">
                    <span class="white f16">忘记密码？</span>
                    <a id="forget_pwd_btn" class="forget-pwd-btn" href="javascript:void(0)">找回密码</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="f-def-dialog" id="forget_pwd_dialog">
    <div class="f-dialog-shadow"></div>
    <div class="f-dialog-content" style="height: auto;">
        <span class="close-icon"><i class="iconfont icon-close"></i></span>
        <div class="dialog-header">
            <span class="title">找回密码</span>
        </div>
        <div class="dialog-detail" style="width: 370px">
            <!-- 找回密码 -->
            <form id="findpwd_form" class="layui-form" method="post">
                <div class="layui-form-item">
                    <div class="layui-input-block login-input">
                        <input type="text" name="loginName" lay-verify="mobile" autocomplete="off" placeholder="请输入手机号码" class="layui-input l" style="width: calc(100% - 120px);">
                        <button id="smsbtn" type="button" onclick="sendsms()" class="layui-btn layui-btn-primary" style="width: 110px!important">发送验证码</button>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block login-input">
                        <input type="text" name="verifyCode" lay-verify="verifyCode" autocomplete="off" placeholder="请输入手机验证码" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block login-input">
                        <input type="password" class="layui-input" name="password" lay-verify="password1" placeholder="请输入新密码"/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block login-input">
                        <input type="password" class="layui-input" name="repassword" lay-verify="repassword" placeholder="请再次输入确认密码"/>
                    </div>
                </div>
                <button type="button" class="layui-btn layui-btn-danger login-btn" lay-submit lay-filter="findpwd-form">重置密码</button>
                <button type="button" class="layui-btn layui-btn-primary login-btn" id="cancel_btn">取消</button>
            </form>
        </div>
    </div>
</div>
<!-- 合作伙伴 -->
<%@include file="./include/partner.jsp" %>

<!--底部-->
<%@include file="./include/footer.jsp" %>
<script>
    $(function () {
        layui.use(['form'], function () {
            var form = layui.form();
            form.verify({
                loginName: function (value) {
                    if (!util.isValid(value)) {
                        return "登录名不能为空";
                    }
                },
                password: function (value) {
                    if (!util.isValid(value)) {
                        return "登录密码不能为空";
                    }
                },
                mobile: function (value) {
                    var regex = /^((13[0-9])|(14[5|7])|(15([0-9]))|(18[0-9])|(17[0-9]))\d{8}$/;
                    if (!util.isValid(value)) {
                        return "请输入手机号码";
                    } else if (!regex.test(value)) {
                        return "手机号码格式不正确";
                    }
                },
                verifyCode: function (value) {
                    if (value == "") {
                        return "请输入手机验证码";
                    }
                },
                password1: function (value) {
                    var regex = /^[a-zA-Z0-9]{6,10}$/;
                    if (value == "") {
                        return "密码不能为空";
                    } else if (!regex.test(value)) {
                        return "密码格式不正确";
                    }
                },
                repassword: function (value) {
                    var pwd = $("#findpwd_form").find("[name=password]").val();
                    var regex = /^[a-zA-Z0-9]{6,10}$/;
                    if (value == "") {
                        return "确认密码不能为空";
                    } else if (!regex.test(value)) {
                        return "确认密码格式不正确";
                    } else if (pwd != value) {
                        return "确认密码与新密码不匹配";
                    }
                }
            });

            // 登录
            form.on('submit(login-form)', function (data) {
                $.ajax({
                    url: '${ctx}/user/login/login.do',
                    type: 'POST',
                    async: false, // 使用同步的方法
                    data: $('#my_from').serialize(),
                    dataType: 'json',
                    success: function (res) {
                        if (res.success) {
                            location.href = "${ctx}/system/member/memberIndex.do";
                        } else {
                            layer.alert(res.description, {
                                icon: 5
                            });
                            console.log(res.description);
                        }
                    }
                });
            });

            // 找回密码
            form.on('submit(findpwd-form)', function (data) {
                $(data.elem).removeAttr("lay-submit");
                $.ajax({
                    url: '${ctx}/user/login/resetPassword.do',
                    type: 'POST',
                    async: false, // 使用同步的方法
                    data: $('#findpwd_form').serialize(),
                    dataType: 'json',
                    success: function (res) {
                        if (res.success) {
                            layer.msg('密码重置成功', {icon: 1}, function (index) {
                                location.reload();
                            });
                        } else {
                            layer.msg(res.description, {
                                icon: 2
                            });
                        }
                    }
                });
            });
        });
        $('.f-def-dialog .close-icon').click(function () {
            $(this).closest('.f-def-dialog').fadeOut();
        });

        $('#forget_pwd_btn').click(function () {
            $('#forget_pwd_dialog').fadeIn();
        });

        // 回车登录
        $("#my_from").keydown(function () {
            var e = e || event, keycode = e.which || e.keyCode;
            if (keycode == 13) {
                $("#login_btn").trigger('click');
            }
        });

        $("#cancel_btn").click(function () {
            $(this).closest('.f-def-dialog').fadeOut();
        });
    })

    var ttt = null;
    var wait = 60;
    function timeoutssss(mobile) {
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
                    "mobile": mobile
                }, function (data) {
                });
            }
            wait--;
            ttt = setTimeout(function () {
                timeoutssss(mobile);
            }, 1000);
        }
    }

    // 发送短信
    function sendsms() {
        var mobile = $("#findpwd_form").find("[name=loginName]").val();
        if (mobile == "") {
            top.layer.alert("请先输入手机号", {
                icon: 5
            });
            return;
        }
        wait = 60;
        timeoutssss(mobile);
    }


</script>
</body>
</html>