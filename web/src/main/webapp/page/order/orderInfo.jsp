<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>个人名片</title>
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

.vip-big .vip-big-font {
	font-size: 26px;
	position: absolute;
	color: #e8473f;
	top: 50%;
	left: 50%;
	-webkit-transform: translate(-50%, -47%);
	transform: translate(-50%, -47%);
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

.huangguan-big {
	font-size: 20px !important;
	color: #ffc107 !important;
}

.layui-input, .layui-select, .layui-textarea {
	height: auto !important;
}

.layui-form-item .layui-input-inline {
	width: 300px !important;
}
</style>
</head>
<body>
	<div class="layui-form info-container">
		<c:if test="${not empty orderForm.picture}">
			<div class="layui-form-item">
				<div class="layui-inline">
					<div class="layui-input-inline">
						<div class="member-logo">
							<div class="user-infos">
								<div class="avater-big" id="logo" style="background-image: url('${orderForm.picture}'),url(../../image/avatar1.png);"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>
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
					<span class="layui-input"><fmt:formatDate value="${orderForm.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
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
							<c:if test="${orderForm.paymentWay == 1}" >微信</c:if>
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
	</div>
</body>
<script>
	
</script>
</html>