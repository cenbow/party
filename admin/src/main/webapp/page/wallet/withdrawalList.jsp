<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>提现管理</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/member_act_list.css">
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
					<span class="title f20">提现管理</span>
				</div>
			</div>
			<form class="layui-form" action="${ctx}/wallet/withdrawals/withdrawalList.do" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" /> <input id="distributionId"
					name="distributionId" value="${distributionId}" type="hidden">
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">审核状态</label>
								<div class="layui-input-inline">
									<select name="status">
										<option value="">全部</option>
										<c:forEach var="map" items="${checkStatus}">
											<c:if test="${withdrawal.status == map.key}">
												<option value="${map.key}" selected="selected">${map.value}</option>
											</c:if>
											<c:if test="${withdrawal.status != map.key}">
												<option value="${map.key}">${map.value}</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">提现者</label>
								<div class="layui-input-inline">
									<input type="text" name="memberName" autocomplete="off" class="layui-input" value="${input.memberName}" placeholder="请输入查询提现者">
								</div>
							</li>
							<li class="form-item-inline">
								<div class="sub-btns">
									<a class="layui-btn layui-btn-danger" href="javascript:submitFunction('#myForm')">确定</a> <a
										class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">提现时间</label>
								<div class="check-btn-inner" id="timeType">
									<a id="all" href="javascript:void(0);" onclick="setTimeType($(this),0,'#myForm')" ${empty input.timeType || input.timeType == 0 ? 'class="active"' : ''}>全部</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),1,'#myForm')" ${input.timeType == 1 ? 'class="active"' : ''}>今天</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),2,'#myForm')" ${input.timeType == 2 ? 'class="active"' : ''}>本周内</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),3,'#myForm')" ${input.timeType == 3 ? 'class="active"' : ''}>本月内</a>
									<input type="hidden" name="timeType" value="${input.timeType}" />
								</div>
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="createStart" value="${input.createStart}"
											placeholder="开始日" onclick="layui.laydate({elem: this})">
									</div>
									-
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="createEnd" value="${input.createEnd}"
											placeholder="截止日" onclick="layui.laydate({elem: this})">
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</form>
			<div class="my-act-list-content">
				<ul class="num">
					<div class="l">
						<li class="f16">提现次数<span class="red">${page.totalCount}</span>次</li>
					</div>
					<div class="l">
						<li style="cursor: pointer;" class="r"><a class="layui-btn layui-btn-danger layui-btn-small" id="btnExport">导出EXCEL</a></li>
					</div>
					<p class="cl"></p>
				</ul>
				<div class="cl">
					<ul class="header">
						<li style="width: 20%;">提现者</li>
						<li style="width: 20%">提现账号</li>
						<li style="width: 13%">提现金额</li>
						<li style="width: 13%">提现时间</li>
						<li style="width: 6%">状态</li>
						<li style="width: 8%">操作</li>
						<li style="width: 7%">展开详情</li>
					</ul>
					<div id="view" class="cl content-body">
						<c:if test="${page.totalCount == 0}">
							<div class="f16 tc mt15">还没有提现记录</div>
						</c:if>
						<c:forEach var="withdrawal" items="${withdrawals}">
							<div class="info">
								<ul class="content">
									<li style="width: 20%;" class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${withdrawal.member.id}','400px','470px')">
										<div class="member-cell">
											<div class="member-logo" style="background-image: url('${withdrawal.member.logo}'),url(${ctx}/image/def_user_logo.png)"></div>
											<div class="member-name ellipsis-1">
												<a class="blue" title="${withdrawal.member.realname}" href="javascript:void(0);">${withdrawal.member.realname}</a>
											</div>
										</div>
									</li>
									<li style="width: 20%">${withdrawal.accountNumber}</li>
									<li style="width: 13%">¥<fmt:formatNumber pattern="#.##" value="${withdrawal.payment}"/></li>
									<li style="width: 13%"><fmt:formatDate value="${withdrawal.createDate}" pattern="yyyy-MM-dd HH:mm" /></li>
									<li style="width: 6%">
										<c:if test="${withdrawal.status == 1}">
											<span>处理中</span>
										</c:if>
										<c:if test="${withdrawal.status == 2}">
											<span style="color: red;">已付款</span>
										</c:if>
										<c:if test="${withdrawal.status == 3}">
											<span>已拒绝</span>
										</c:if>
									</li>
									<li style="width: 8%">
										<c:if test="${withdrawal.status == 1}">
											<a class="green" href="javascript:check('确认要审核通过该提现吗？', '${withdrawal.id}', '2')" >通过</a>
											<a class="red" href="javascript:check('确认要审核拒绝该提现吗？', '${withdrawal.id}', '3')" >拒绝</a>
										</c:if>
									</li>
									<li style="width: 7%;" class="option"><i class="iconfont icon-unfold"></i>
										<i class="iconfont icon-fold"></i></li>
									<div class="cl"></div>
								</ul>
								<ul class="tr-extra-content">
									<li>
										<label class="ext-label">开户名</label>
										<div class="ext-value">${withdrawal.name}</div>
									</li>
									<li>
										<label class="ext-label">预留手机号</label>
										<div class="ext-value">${withdrawal.phone}</div>
									</li>
									<li>
										<label class="ext-label">银行名称</label>
										<div class="ext-value">${withdrawal.bankName}</div>
									</li>
									<li>
										<label class="ext-label">开户行</label>
										<div class="ext-value">${withdrawal.openedPlace}</div>
									</li>
									<li>
										<label class="ext-label">手续费</label>
										<div class="ext-value"><fmt:formatNumber pattern="#.##" value="${withdrawal.serviceFee}"/>元</div>
									</li>
									<li>
										<label class="ext-label">支付净额</label>
										<div class="ext-value"><fmt:formatNumber pattern="#.##" value="${withdrawal.netAmount}"/>元</div>
									</li>
								</ul>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div id="page_content" class="page-container"></div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">

    showActive('${input.createStart}', '${input.createEnd}', '#timeType');


	function check(content, withdrawalId, checkStatus) {
		layer.confirm(content, {
			icon : 3,
			title : '系统提示'
		}, function(index) {
			layer.close(index);
			$.post("${ctx}/wallet/withdrawals/verify.do", {
				id : withdrawalId,
				status : checkStatus
			}, function(data) {
				if (data.success == true) {
					layer.msg("审核成功", {
						icon : 1
					}, function(index) {
						window.location.reload();
					});
				} else {
					layer.msg("审核失败", {
						icon : 2
					});
				}
			})
		});
	}

	$(function() {

        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
        $('.content-body').delegate('.option', 'click', function (e) {
            var info = $(this).closest('.info');
            if (!info.hasClass('active')) {//打开
                $('.info').removeClass('active');
                info.toggleClass('active');
            } else {
                info.toggleClass('active');
            }
        });

		$("#btnExport").click(function() {
			layer.confirm('确认要导出Excel吗?', {
				icon : 3,
				title : '系统提示'
			}, function(index) {
				var url = "${ctx}/wallet/withdrawals/exportWithdrawal.do";
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