<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/tag.jsp" %>
<html>
<head>
    <title>购买套餐</title>
    <%@include file="../include/commonFile.jsp"%>
    <style type="text/css">
        .payment-alipay, .payment-wx {
            display: inline-block;
            width: 49%;
            text-align: center;
            vertical-align: top;
        }

        .payment-alipay > img, #wechat-pay > img {
            height: 60px;
            margin: 30px 0;
        }

        .payment-alipay > a, #wechat-btn {
            cursor: pointer;
            display: block;
            width: 120px;
            height: 30px;
            border: 1px solid #3091f2;
            line-height: 30px;
            border-radius: 2px;
        }

        .payment-alipay > a:hover, #wechat-btn:hover {
            background-color: #3091f2;
            color: #fff;
        }

        .payment-alipay > a {
            color: #fff;
            margin: 20px auto 0px;
            background-color: #3091f2;
        }

        #wechat-btn {
            margin: 20px auto 35px;
            color: #3091f2;
        }

        /**版本**/
        .payment-v {
            height: 30px;
            line-height: 30px;
        }

        .payment-v span {
            display: inline-block;
            vertical-align: top;
        }
        body {
            min-width: auto !important;
            background-color: white !important;
        }
        .payment-wx-logo {
            margin-top: 21px;
        }
        .payment-wx-logo > img {
            width: 50px;
            vertical-align: middle;
        }
    </style>
</head>
<body>
<div class="payment-inner" style="padding: 0 50px; padding-top: 10px;">
    <div class="payment-v">
        <span>购买套餐：</span>
        <span><b class="mr15 active-red">${level.name}</b><b class="active-red">${level.price}元/${level.unit}</b></span>
    </div>
    <div class="payment-v">
        <span>付款金额：</span>
        <span class="active-red b">${level.price}元</span>
    </div>
    <div class="payment-t select-payment" style="display: block;">
        <div class="payment-v">选择支付平台</div>
        <div class="third-payment" style="text-align:center;">
            <div class="payment-alipay">
                <img src="${ctx}/image/pay/alipay.png"/>
                <a target="blank" id="alipay-btn" href="${ctx}/pay/ali/pc/${orderForm.id}/buyOrder.do">点击去支付</a>
            </div>
            <div id="wechat-pay" class="payment-wx">
                <img src="${ctx}/image/pay/wechatpay.png"/>
                <div id="wechat-btn" class="">点击去支付</div>
            </div>
            <div id="wechar-pay-qrcode" class="payment-wx" style="display:none;">
                <img id="wechat-pay-qrcode-img" class="payment-qcode" src="${ctx}/pay/wechat/pc/${orderForm.id}/getQrCodeImg.do" alt="" style="width: 110px;">
                <div class="payment-wx-logo">
                    <img src="${ctx}/image/pay/wechat.jpg"/>
                    微信扫码支付
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var checkTimeOut = null;
    $(function () {
        $("#wechat-btn").click(function () {
            // 微信二维码
            $(this).parent().hide().siblings('#wechar-pay-qrcode').show();
        });
        checkOrder('${orderForm.id}');
    })

    function checkOrder(orderId) {
        if (checkTimeOut != undefined) {
            clearInterval(checkTimeOut);
        }
        checkTimeOut = setInterval(function () {
            $.post("${ctx}/pay/ali/pc/checkOrderStatus.do", {orderId: orderId}, function (res) {
                if (res.success) {
                    if (res.data.status == 2) {
                        top.layer.msg("订单支付完成", {icon : 1}, function () {
                            clearInterval(checkTimeOut);
                        });
                    } else if (res.data.status == 0) {
                        console.log("1111");
                    }
                }
            })
        }, 5000);
    }
</script>
</html>
