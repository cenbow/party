<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>订单详情</title>
    <%@include file="../include/commonFile.jsp" %>
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
            width: 140px;
            height: 90px;
            display: inline-block;
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center;
            position: relative;
            border: #fff 2px solid;
            border-radius: 4px;
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
        .footer-place{
            height: 58px;
        }
    </style>
</head>
<body>
<div class="layui-form info-container">
    <div class="layui-form-item">
        <div class="layui-inline">
            <div class="layui-input-inline">
                <div class="member-logo">
                    <div class="user-infos">
                        <div class="avater-big" id="logo"
                             style="background-image: url('${orderForm.picture}'),url(../../image/avatar1.png);"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">支付金额</label>
            <div class="layui-input-inline">
                <span class="layui-input" style="word-break: break-all;">￥${orderForm.payment}</span>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">订单名称</label>
            <div class="layui-input-inline">
                <span class="layui-input" style="word-break: break-all;">${orderForm.title}</span>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">下单时间</label>
            <div class="layui-input-inline">
                <span class="layui-input"><fmt:formatDate value="${orderForm.updateDate}"
                                                          pattern="yyyy-MM-dd HH:mm:ss"/></span>
            </div>
        </div>
    </div>
    <c:if test="${orderForm.payment != 0.0 && (orderForm.status == 2 || orderForm.status == 4) && orderForm.paymentWay != 2}">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">支付类型</label>
                <div class="layui-input-inline">
						<span class="layui-input" style="word-break: break-all;">
							<c:if test="${orderForm.paymentWay == 0}">支付宝</c:if>
							<c:if test="${orderForm.paymentWay == 1}">微信</c:if>
						</span>
                </div>
            </div>
        </div>
    </c:if>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">交易状态</label>
            <div class="layui-input-inline">
                <span class="layui-input">${orderForm.tradeState}</span>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">订单状态</label>
            <div class="layui-input-inline">
					<span class="layui-input">
						<c:choose>
                            <c:when test="${orderForm.status == 0}">
                                <span>审核中</span>
                            </c:when>
                            <c:when test="${orderForm.status == 1}">
                                <span>待支付</span>
                            </c:when>
                            <c:when test="${orderForm.status == 2}">
                                <span style="color: red;">已支付</span>
                            </c:when>
                            <c:when test="${orderForm.status == 3}">
                                <span>其它</span>
                            </c:when>
                            <c:when test="${orderForm.status == 4}">
                                <span>已退款</span>
                            </c:when>
                        </c:choose>
					</span>
            </div>
        </div>
    </div>
    <c:if test="${not empty orderForm.transactionId}">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">交易单号</label>
                <div class="layui-input-inline">
                    <span class="layui-input">${orderForm.transactionId}</span>
                </div>
            </div>
        </div>
    </c:if>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">订单编号</label>
            <div class="layui-input-inline">
                <span class="layui-input">${orderForm.id}</span>
            </div>
        </div>
    </div>
    <c:if test="${orderForm.paymentWay == 1}">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">商户名称</label>
                <div class="layui-input-inline">
                    <span class="layui-input">${orderForm.merchantName}</span>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">商户号</label>
                <div class="layui-input-inline">
                    <span class="layui-input">${orderForm.merchantId}</span>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${not empty orderForm.linkman}">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">联系人</label>
                <div class="layui-input-inline">
                    <span class="layui-input">${orderForm.linkman}</span>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${not empty orderForm.phone}">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">联系电话</label>
                <div class="layui-input-inline">
                    <span class="layui-input">${orderForm.phone}</span>
                </div>
            </div>
        </div>
    </c:if>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">购买份数</label>
            <div class="layui-input-inline">
                <span class="layui-input" style="word-break: break-all;">${orderForm.unit}份</span>
            </div>
        </div>
    </div>
    <c:if test="${fn:length(orderForm.goodsCoupons) > 0}">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">核销码</label>
                <div class="layui-input-inline">
						<span class="layui-input"> <c:forEach var="t" items="${orderForm.goodsCoupons}">
                            ${t.verifyCode} &nbsp;&nbsp;
                        </c:forEach>
						</span>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${not empty response.errCode}">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">错误代码</label>
                <div class="layui-input-inline">
                    <span class="layui-input">${response.errCode}</span>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${not empty response.errCodeDes}">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">错误描述</label>
                <div class="layui-input-inline">
                    <span class="layui-input">${response.errCodeDes}</span>
                </div>
            </div>
        </div>
    </c:if>
    <div class="footer-buttons">
        <a class="layui-btn layui-btn-danger"  href="javascript:check('${orderForm.id}')">对账</a>
        <a class="layui-btn layui-btn-normal" href="javascript:closeSelf()" >关闭</a>
    </div>
    <div class="footer-place"></div>
</div>
</body>
<script type="text/javascript">
    function check(orderId) {
        $.post("${ctx}/payquery/orderQuery.do", {
            "orderId": orderId
        }, function (data) {
            if (data.success == true) {
                top.layer.msg("对账成功！", {
                    icon: 1
                }, function (index) {
                    window.location.reload();
                });
            } else {
                top.layer.msg(data.description, {
                    icon: 2
                });
            }
        });
    }
    function closeSelf() {
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        top.layer.close(index);
    }
</script>
</html>