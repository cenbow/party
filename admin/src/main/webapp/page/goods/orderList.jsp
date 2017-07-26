<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>订单管理</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<%@include file="../include/commonFile.jsp"%>
</head>
<!--头部-->
<%@include file="../include/header.jsp"%>
<div class="index-outside">
	<%@include file="../include/sidebar.jsp"%>
	<!--内容-->
	<section>
		<div class="section-main">
			<c:if test="${goods.type == 0}">
				<div class="c-time-list-header">
	          		<div class="r">
						<a href="${ctx}/goods/goods/bzGoodsList.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
						</a>
					</div>
					<div class="ovh">
						<span class="title f20" title="${goods.title}">标准玩法管理&nbsp;&gt;&nbsp;
							<c:if test="${fn:length(goods.title) > 35}">${fn:substring(goods.title,0,30)}...</c:if>
							<c:if test="${fn:length(goods.title) < 35}">${goods.title}</c:if>
						</span>
					</div>
				</div>
			</c:if>
			<c:if test="${goods.type == 1}">
				<div class="c-time-list-header">
	          		<div class="r">
						<a href="${ctx}/goods/goods/dzGoodsList.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
						</a>
					</div>
					<div class="ovh">
						<span class="title f20">定制玩法管理 &gt; ${goods.title}</span>
					</div>
				</div>
			</c:if>
			<form class="layui-form" action="${ctx}/order/order/goodsOrderList.do" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" />
				<input name="goodsId" value="${orderForm.goodsId}" type="hidden">
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item-inline">
								<label class="search-form-lable">订单状态</label>
								<div class="layui-input-inline">
									<select name="status">
										<option value="">全部</option>
										<c:forEach var="map" items="${orderStatus}">
											<c:if test="${orderForm.status == map.key}">
												<option value="${map.key}" selected="selected">${map.value}</option>
											</c:if>
											<c:if test="${orderForm.status != map.key}">
												<option value="${map.key}">${map.value}</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</li>
							<li class="form-item-inline">
								<div class="sub-btns">
									<a class="layui-btn layui-btn-danger" href="javascript:submitFunction('#myForm')">确定</a>
									<a class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">发布时间</label>
								<div class="check-btn-inner" id="timeType">
									<a id="all" href="javascript:void(0);" onclick="setTimeType($(this),0,'#myForm')" ${empty input.timeType || input.timeType == 0 ? 'class="active"' : ''}>全部</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),1,'#myForm')" ${input.timeType == 1 ? 'class="active"' : ''}>今天</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),2,'#myForm')" ${input.timeType == 2 ? 'class="active"' : ''}>本周内</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),3,'#myForm')" ${input.timeType == 3 ? 'class="active"' : ''}>本月内</a>
									<input type="hidden" name="timeType" value="${input.timeType}" />
								</div>
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="createStart" value="${input.createStart}" placeholder="开始日" onclick="layui.laydate({elem: this})">
									</div>
									-
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="createEnd" value="${input.createEnd}" placeholder="截止日" onclick="layui.laydate({elem: this})">
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</form>
			<div class="my-act-list-content">
				<div class="cl">
					<table class="layui-table">
						<colgroup>
							<col>
							<col>
							<col width="130px">
							<col>
							<col>
							<col>
							<col width="150px">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th>购买者</th>
								<th>联系人</th>
								<th>电话</th>
								<th>份数</th>
								<th>金额</th>
								<th>订单状态</th>
								<th>购买时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${page.totalCount == 0}">
								<tr>
									<td colspan="8"><div class="f16 tc">还没有人购买该玩法</div></td>
								</tr>
							</c:if>
							<c:forEach var="orderForm" items="${orderForms}">
							<tr>
								<td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${orderForm.member.id}','400px','470px')">
									<div class="member-cell">
									<div class="member-logo" style="background-image: url('${orderForm.member.logo}'),url(${ctx}/image/def_user_logo.png)"></div>
									<div class="member-name ellipsis-1"><a class="blue" title="${orderForm.member.realname}" style="cursor: pointer;">${orderForm.member.realname}</a></div>
									</div>
								</td>
								<td class="table-member"><div class="ellipsis-1">${orderForm.linkman}</div></td>
								<td>${orderForm.phone}</td>
								<td>${orderForm.unit}份</td>
								<td>￥${orderForm.payment}</td>
								<td>
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
								</td>
								<td><fmt:formatDate value="${orderForm.createDate}" pattern="yyyy-MM-dd HH:mm" /></td>
								<td>
									<c:if test="${orderForm.status == 2 && orderForm.payment != 0.0}">
										<a class="red" href="javascript:refund('${orderForm.id}','${orderForm.paymentWay}')" target="_self">退款</a>
									</c:if>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div id="page_content" class="page-container"></div>
			</div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">

    showActive('${input.createStart}', '${input.createEnd}', '#timeType');


    $(function () {
//加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })
	//退款
	function refund(orderId) {
		layer.confirm('确认要退款吗?', {
			icon : 3,
			title : '系统提示'
		}, function(index) {
			$.post("${ctx}/refund/refund.do", {
				"orderId" : orderId
			}, function(data) {
				if (data.success == true) {
					top.layer.msg("退款成功！", {
						icon : 1
					}, function(index){
						window.location.reload();
					});
				} else {
					top.layer.msg(data.description, {
						icon : 2
					});
				}
				top.layer.close(index);
			});
		});
	}
</script>
</body>
</html>