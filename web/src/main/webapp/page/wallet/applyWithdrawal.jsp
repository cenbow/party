<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>申请提现</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_act.css">
<style type="text/css">
	.layui-form-item .layui-input-inline{
		width: 280px!important;
	}
</style>
</head>
<!--头部-->
<%@include file="../include/header.jsp"%>
<div class="index-outside">
	<%@include file="../include/sidebar.jsp"%>
	<!--内容-->
	<section>
		<div class="section-main">
			<div class="f-search-bar" style="border: 1px solid #dbdee5; padding: 16px; border-radius: 5px;">
				<div class="search-container">
					<p class="mb5">可用余额：</p>
					<div>
						<div style="width: auto; margin-right: 10px;">
							<span style="font-size: 24px; font-family: '微软雅黑';"><fmt:formatNumber pattern="#,###.##" value="${totalPayment}"></fmt:formatNumber></span>&nbsp;元
						</div>
					</div>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/wallet/withdrawals/save.do">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">开户名<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" class="layui-input" name="name" lay-verify="name" placeholder="请输入开户名"
								value="${memberBank.name}" />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">手机号<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" class="layui-input" name="phone" lay-verify="phone" placeholder="请输入银行预留手机号"
								value = "${memberBank.phone}" />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">提现账号<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" name="accountNumber" lay-verify="accountNumber" class="layui-input"
								placeholder="请输入提现账号" value="${memberBank.accountNumber}">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">银行名称<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" name="bankName" lay-verify="bankName" class="layui-input"
								placeholder="请输入银行名称" value="${memberBank.bankName}">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">开户行<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" name="openedPlace" lay-verify="openedPlace" class="layui-input"
								placeholder="请输入银行卡开户行所在地 X省X市X区" value="${memberBank.openedPlace}">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">金额<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" name="payment" lay-verify="payment" class="layui-input"
								placeholder="请输入要提现的金额">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">手续费</label>
						<div class="layui-input-inline">
							<span id="serviceFee" class="layui-input" style="border: 0px" />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<a class="layui-btn layui-btn-danger" lay-submit lay-filter="infoForm">立即提交</a>
						<a href="${ctx}/wallet/orderList.do" class="layui-btn layui-btn-primary">取消</a>
					</div>
				</div>
			</form>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript">
    $(function () {
        layui.use([ 'form' ],function() {

            var value = $("[name=accountNumber]").val();
            if(value != ""){
                value = value.replace(/\D/g,'').replace(/....(?!$)/g,'$& ');
                $("[name=accountNumber]").val(value);
            }

            $("[name=accountNumber]").blur(function(){
                var value = $(this).val();
                value = value.replace(/\D/g,'').replace(/....(?!$)/g,'$& ');
                $("[name=accountNumber]").val(value);
            });

            $("[name=payment]").blur(function(){
                var value = $(this).val();
                if(value != ""){
                    value = parseFloat(value) * 0.006;
                    $("#serviceFee").text(value.toFixed(2) + "元");
                }
            });

            var form = layui.form();

            //自定义验证规则
            form.verify({
                name : function(value) {
                    if (value == "") {
                        return "请输入姓名";
                    }
                },
                phone : function(value) {
                    var regex = /^((13[0-9])|(14[5|7])|(15([0-9]))|(18[0-9])|(17[0-9]))\d{8}$/;
                    if (value == "") {
                        return "请输入手机号码";
                    } else if (!regex.test(value)) {
                        return "请输入正确格式的手机号码";
                    }
                },
                accountNumber : function(value) {
                    value = value.replace(/\s+/g, "");
                    if (value == "") {
                        return "请输入银行卡号";
                    } else if (!util.checkNumber(value)) {
                        return "请输入正确的银行卡号";
                    }
                },
                openedPlace : function(value) {
                    if (value == "") {
                        return "请输入银行卡开户行";
                    }
                },
                bankName : function(value) {
                    if (value == "") {
                        return "请输入银行名称";
                    }
                },
                payment : function(value) {
                    var reg = /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
                    if (value == "") {
                        return "请输入提现金额";
                    } else if (!reg.test(value)) {
                        return "提现金额必须为整数或小数，小数点后不超过2位。";
                    } else if (parseFloat(value) == 0) {
                        return "请输入0.01元以上的金额。";
                    } else {
                        var isRepeat = false;
                        var description = "";
                        $.ajax({
                            type : 'POST',
                            async : false, // 使用同步的方法
                            data : {
                                payment : value
                            },
                            dataType : 'json',
                            success : function(result) {
                                isRepeat = !result.success;
                                description = result.description;
                            },
                            url : '${ctx}/wallet/withdrawals/checkPayment.do'
                        });
                        if (isRepeat) {
                            return description;
                        }
                    }
                }
            });

            //监听提交 编辑资料
            form.on('submit(infoForm)', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        layer.msg("提现申请成功，请耐心等待", {
                            icon : 1
                        }, function(index) {
                            location.href="${ctx}/wallet/withdrawalList.do";
                        });
                    } else {
                        layer.msg(res.description, {
                            icon : 2
                        })
                    }
                });
                return false;
            });
        });
    })

</script>
</body>
</html>