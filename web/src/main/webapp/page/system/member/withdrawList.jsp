<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>个人中心</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<%@include file="../../include/commonFile.jsp"%>
<style type="text/css">
	thead th,tbody td{
		padding-left: 8px!important;
		padding-right: 8px!important;
	}
</style>
</head>
<!--头部-->
<%@include file="../../include/header.jsp"%>
<div class="index-outside">
	<%@include file="../../include/sidebar.jsp"%>
	<!--内容-->
	<section>
		<div class="section-main">
			<%@include file="memberIndex.jsp" %>
			<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
				<ul class="layui-tab-title">
					<li><span class="title f18 ml5 mr5">交易明细</span></li>
					<li><span class="title f18 ml5 mr5">收益明细</span></li>
					<li class="layui-this"><span class="title f18 ml5 mr5">提现明细</span></li>
				</ul>
				<div class="layui-tab-content">
					<div class="layui-tab-item"></div>
					<div class="layui-tab-item"></div>
					<div class="layui-tab-item layui-show">
						<form class="layui-form" action="${ctx}/order/order/withdrawList.do" id="myForm" method="post">
							<input type="hidden" name="pageNo" id="pageNo" value="${page.page}" />
						</form>
						<div class="my-act-list-content">
							<ul class="num">
								<div class="l" style="width: 70%">
									<li class="f16">提现总金额 
										<span class="red">
											<c:if test="${withdrawalTotal > 0}">
												<fmt:formatNumber pattern="#,###.##" value="${withdrawalTotal}"/>
											</c:if>
											<c:if test="${withdrawalTotal == 0.0}">
												${withdrawalTotal}
											</c:if>
										</span> 元（包含已扣除0.6%的微信商户服务费）
									</li>
								</div>
								<div class="l" style="width: 30%">
									<li style="cursor: pointer;" class="r"><a class="layui-btn layui-btn-danger layui-btn-small" id="btnExport">导出EXCEL</a></li>
								</div>
								<p class="cl"></p>
							</ul>
							<div class="cl">
								<table class="layui-table">
									<colgroup>
										<col width="120">
										<col width="120">
										<col width="120">
										<col width="90">
										<col width="90">
										<col width="100">
										<col width="90">
										<col width="90">
										<col width="70">
									</colgroup>
									<thead>
										<tr>
											<th>提现账号</th>
											<th>开户行</th>
											<th>开户名</th>
											<th>提现金额</th>
											<th>手续费</th>
											<th>支付净额</th>
											<th>提现时间</th>
											<th>到账时间</th>
											<th>状态</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${page.totalCount == 0}">
											<tr>
												<td colspan="9">
													<div class="f16 tc">没有更多记录了...</div>
												</td>
											</tr>
										</c:if>
										<c:forEach var="withdrawal" items="${withdrawals}">
											<tr>
												<td>${withdrawal.accountNumber}</td>
												<td>${withdrawal.openedPlace}</td>
												<td>${withdrawal.name}</td>
												<td><fmt:formatNumber pattern="#.##" value="${withdrawal.payment}"/>元</td>
												<td><fmt:formatNumber pattern="#.##" value="${withdrawal.serviceFee}"/>元</td>
												<td><fmt:formatNumber pattern="#.##" value="${withdrawal.netAmount}"/>元</td>
												<td><fmt:formatDate value="${withdrawal.createDate}" pattern="yyyy-MM-dd HH:mm" /></td>
												<td><fmt:formatDate value="${withdrawal.updateDate}" pattern="yyyy-MM-dd HH:mm" /></td>
												<td>
													<c:if test="${withdrawal.status == 1}">
														<span>处理中</span>
													</c:if>
													<c:if test="${withdrawal.status == 2}">
														<span style="color: red;">已付款</span>
													</c:if>
													<c:if test="${withdrawal.status == 3}">
														<span>已拒绝</span>
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
							<div class="l page-totalcount">
								<span class="f14 red">共<b id="totalCount">${page.totalCount}</b>条记录
								</span>
							</div>
						</c:if>
						<div id="page_content" class="page-container"></div>
					</div>
					<div class="cl">
						<p>注：</p>
						<dl style="line-height: 25px;">
							<dt>提现金额（之和）= 支付净额 - 手续费金额，提现金额（之和）指同一交易起至日期下的提现金额总和</dt>
							<dd>1.提现记录将在每日12点左右进行更新</dd>
							<dd>2.提现金额从结算日起1到3个工作日到账</dd>
						</dl>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../../include/footer.jsp"%>
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
				var url = "${ctx}/wallet/exportWithdrawal.do";
				var action = $("#myForm").attr("action");
				$("#myForm").attr("action", url);
				$("#myForm").submit();
				$("#myForm").attr("action", action);
				top.layer.close(index);
			});
		});
	})
</script>
</body>
</html>