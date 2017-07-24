<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/tag.jsp" %>
<%@include file="../include/commonFile.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="javascript:createOrderForm()" id="createForm">点击我生成订单</a>
<div style="display: none" id="orderDiv">
    <img src="" id="wechatImg"/>
    <a target="_blank" id="oldType">点我1_原版</a>
    <a target="_blank" id="newType">点我_新版</a>
</div>
</body>
<script type="text/javascript">
    var checkTimeOut = null;

    function createOrderForm() {
        $.post("${ctx}/pay/wechat/pc/createOrderForm.do", {}, function (res) {
            $("#wechatImg").attr("src", "${ctx}/pay/wechat/pc/getQrCodeImg/" + res.data + ".do");
            $("#oldType").attr("href", "${ctx}/pay/ali/pc/getResponse/" + res.data + ".do");
            $("#newType").attr("href", "${ctx}/pay/ali/pc/getRequestUrl/" + res.data + ".do");
            $("#orderDiv").show();
            $("#createForm").hide();
            checkOrder(res.data);
        })
    }

    function checkOrder(orderId) {
        if (checkTimeOut != undefined) {
            clearInterval(checkTimeOut);
        }
        checkTimeOut = setInterval(function () {
            $.post("${ctx}/pay/ali/pc/checkOrderStatus.do", {orderId: orderId}, function (res) {
                if (res.success) {
                    if (res.data.status == 2) {
                        alert("订单支付完成");
                        clearInterval(checkTimeOut);
                    } else if (res.data.status == 0) {
                        console.log("1111");
                    }
                }
            })
        }, 5000);
    }
</script>
</html>
