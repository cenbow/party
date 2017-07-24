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
			<div class="c-time-list-header">
				<div class="ovh">
					<span class="title f20">订单管理</span>
				</div>
			</div>
			<form class="layui-form" action="${ctx}/order/order/orderList.do" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" />
				<input id="distributionId" name="distributionId" value="${distributionId}" type="hidden">
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">订单名称</label>
								<div class="layui-input-inline">
									<input type="text" name="title" autocomplete="off" class="layui-input" value="${orderForm.title}" placeholder="请输入查询订单名称">
								</div>
							</li>
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
							<li class="form-item"><label class="search-form-lable">下单时间</label>
								<div class="check-btn-inner">
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
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">订单类型</label>
								<div class="check-btn-inner">
									<a id="all" href="javascript:void(0);" onclick="setTimeType($(this),'','#myForm')" ${empty orderForm.type ? 'class="active"' : ''}>全部</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),'2','#myForm')" ${orderForm.type == 2 ? 'class="active"' : ''}>活动订单</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),'3','#myForm')" ${orderForm.type == 3 ? 'class="active"' : ''}>众筹订单</a>
									<input type="hidden" name="type" value="${orderForm.type}" />
								</div>
							</li>
						</ul>
					</div>
				</div>
			</form>
			<div class="my-act-list-content">
				<ul class="num">
					<div class="l">
						<li class="f16">订单总额
							<span class="red">
								<c:if test="${page.totalCount == 0}">0</c:if>
								<c:if test="${page.totalCount > 0}">
									<fmt:formatNumber value="${totalAccount}" pattern="0.00"></fmt:formatNumber>
								</c:if>
							</span>
							元
						</li>
					</div>
					<div class="l">
						<li style="cursor: pointer;" class="r"><a class="layui-btn layui-btn-danger layui-btn-small" id="btnExport">导出EXCEL</a></li>
					</div>
					<p class="cl"></p>
				</ul>
				<div class="cl">
					<table class="layui-table">
						<colgroup>
							<col width="220">
							<col width="120">
							<col width="120">
							<col width="80">
							<col width="100">
							<col>
							<col>
						</colgroup>
						<thead>
							<tr>
								<th>订单名称</th>
								<th>下单者</th>
								<th>订单类型</th>
								<th>金额</th>
								<th>订单状态</th>
								<th>下单时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${page.totalCount == 0}">
								<tr>
									<td colspan="7">
										<div class="f16 tc">还没有订单数据</div>
									</td>
								</tr>
							</c:if>
							<c:forEach var="orderForm" items="${orderForms}">
							<tr>
								<td title="${orderForm.title}" onclick="openDialogShow('订单详情','${ctx}/order/order/orderInfo.do?id=${orderForm.id}','450px','570px')">
									<a style="width: 220px;cursor: pointer;" class="dib ellipsis-1 blue">${orderForm.title}</a>
								</td>
								<td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${orderForm.member.id}','400px','470px')">
									<div class="member-logo" style="background-image: url('${orderForm.member.logo}'),url(${ctx}/image/def_user_logo.png)"></div>
									<div class="member-name ellipsis-1"><a class="blue" title="${orderForm.member.realname}" style="cursor: pointer;">${orderForm.member.realname}</a></div>
								</td>
								<td>${orderForm.typeName}</td>
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
										<a class="red" href="javascript:refund('${orderForm.id}')" target="_self">退款</a>
									</c:if>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="page-content">
			<c:if test="${page.totalCount > 0}">
				<div class="l page-totalcount"><span class="f14 red">共<b id="totalCount">${page.totalCount}</b>条记录</span></div>
			</c:if>
			<div id="page_content" class="page-container"></div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">
	
	$(function(){
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');

        $("#btnExport").click(function() {
			layer.confirm('确认要导出Excel吗?', {
				icon : 3,
				title : '系统提示'
			}, function(index) {
				var url = "${ctx}/order/order/export.do";
				var action = $("#myForm").attr("action");
				$("#myForm").attr("action", url);
				$("#myForm").submit();
				$("#myForm").attr("action", action);
				top.layer.close(index);
			});
		});
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
						icon : 6
					}, function(index){
						window.location.reload();
					});
				} else {
					top.layer.msg(data.description, {
						icon : 5
					});
				}
				top.layer.close(index);
			});
		});
	}
</script>
</body>
</html>