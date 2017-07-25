<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>活动报名管理</title>
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
          		<div class="r">
					<a href="javascript:history.back();" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20" title="${activity.title}">活动管理&nbsp;&gt;&nbsp;
						<c:if test="${fn:length(activity.title) > 35}">${fn:substring(activity.title,0,35)}...</c:if>
						<c:if test="${fn:length(activity.title) < 35}">${activity.title}</c:if>
					</span>
				</div>
			</div>
			<form class="layui-form" action="${ctx}/activity/memberAct/memberActList.do?actId=${activity.id}" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" />
				<input id="distributionId" name="distributionId" value="${distributionId}" type="hidden">
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">手机号</label>
								<div class="layui-input-inline">
									<input type="text" name="mobile" autocomplete="off" class="layui-input" value="${memberAct.mobile}"
										placeholder="请输入查询手机号"
									>
								</div></li>
							<li class="form-item-inline"><label class="search-form-lable">微信号</label>
								<div class="layui-input-inline">
									<input type="text" name="wechatNum" autocomplete="off" class="layui-input" value="${memberAct.wechatNum}"
										placeholder="请输入查询微信号"
									>
								</div></li>
							<li class="form-item-inline"><label class="search-form-lable">审核状态</label>
								<div class="layui-input-inline">
									<select name="checkStatus">
										<option value="">全部</option>
										<c:forEach var="map" items="${checkStatus}">
											<c:if test="${memberAct.checkStatus == map.key}">
												<option value="${map.key}" selected="selected">${map.value}</option>
											</c:if>
											<c:if test="${memberAct.checkStatus != map.key}">
												<option value="${map.key}">${map.value}</option>
											</c:if>
										</c:forEach>
									</select>
								</div></li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">报名时间</label>
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
							<li class="form-item-inline">
								<div class="sub-btns">
									<a class="layui-btn layui-btn-danger" href="javascript:submitFunction('#myForm')">确定</a>
									<a class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</form>
			<div class="my-act-list-content">
				<ul class="num">
					<div class="r">
						<li style="cursor: pointer;" class="r">
							<a class="layui-btn layui-btn-danger layui-btn-small" id="btnExport">导出EXCEL</a>
						</li>
					</div>
					<p class="cl"></p>
				</ul>
				<div class="cl">
					<ul class="header">
						<li class="table-member" style="width: 15%;">参与者</li>
						<li style="width: 9%">手机号</li>
						<li style="width: 9%">微信号</li>
						<li style="width: 13%">报名时间</li>
						<li style="width: 7%">金额</li>
						<li style="width: 6%">状态</li>
						<li style="width: 6%">签到</li>
						<li style="width: 8%">操作</li>
						<li style="width: 7%">展开详情</li>
					</ul>
					<div id="view" class="cl content-body">
						<c:if test="${page.totalCount == 0}">
							<div class="f16 tc mt15">还没有人报名</div>
						</c:if>
						<c:forEach var="memberAct" items="${memberActs}">
							<div class="info">
								<ul class="content">
									<li style="width: 15%;" class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${memberAct.member.id}','400px','470px')">
										<div class="member-cell">
										<div class="member-logo"
											style="background-image: url('${memberAct.member.logo}'),url(${ctx}/image/def_user_logo.png)"
										></div>
										<div class="member-name ellipsis-1">
											<a class="blue" title="${memberAct.member.realname}" href="javascript:void(0);">${memberAct.member.realname}</a>
										</div>
										</div>
									</li>
									<li style="width: 9%">${memberAct.mobile}</li>
									<li style="width: 9%" class="table-member ellipsis-1">${memberAct.wechatNum}</li>
									<li style="width: 13%"><fmt:formatDate value="${memberAct.updateDate}" pattern="yyyy-MM-dd HH:mm" /></li>
									<li style="width: 7%"><c:if test="${memberAct.payment == 0}">免费报名</c:if> <c:if
											test="${memberAct.payment != 0}"
										>¥${memberAct.payment}</c:if></li>
									<li style="width: 6%"><c:choose>
											<c:when test="${memberAct.checkStatus == 0}">
												<span>审核中</span>
											</c:when>
											<c:when test="${memberAct.checkStatus == 1}">
												<span>待支付</span>
											</c:when>
											<c:when test="${memberAct.checkStatus == 2}">
												<span class='red'>审核拒绝</span>
											</c:when>
											<c:when test="${memberAct.checkStatus == 3}">
												<span>已支付</span>
											</c:when>
											<c:when test="${memberAct.checkStatus == 4}">
												<span>已取消</span>
											</c:when>
											<c:when test="${memberAct.checkStatus == 5}">
												<span>未参与</span>
											</c:when>
										</c:choose></li>
									<li style="width: 6%;">${memberAct.signin == 1 ? '已签到' : '未签到'}</li>
									<li style="width: 8%">
										<c:if test="${memberAct.checkStatus == 0}">
											<a class="green" href="javascript:check('确认要审核通过该报名吗？', '${memberAct.id}', '1')" target="_self">通过</a>
											<span>|</span>
											<a class="red" href="javascript:check('确认要审核拒绝该报名吗？', '${memberAct.id}', '2')" target="_self">拒绝</a>
										</c:if>
										<c:if test="${memberAct.checkStatus == 3}">
											<c:if test="${memberAct.payment == 0.0}">
												<a class="red" href="javascript:check('确认要拒绝该报名吗？', '${memberAct.id}', '2')" target="_self">拒绝</a>
											</c:if>
											<c:if test="${memberAct.payment > 0.0}">
												<a class="red" href="javascript:check('确认要拒绝并退款该报名吗？', '${memberAct.id}', '2')" target="_self">拒绝并退款</a>
											</c:if>
										</c:if>
									</li>
									<li style="width: 7%;" class="option"><i class="iconfont icon-unfold"></i>
										<i class="iconfont icon-fold"></i></li>
									<div class="cl"></div>
								</ul>
								<ul class="tr-extra-content">
									<c:if test="${memberAct.name != ''}">
										<li>
											<label class="ext-label">联系人</label>
											<div class="ext-value">${memberAct.name}</div>
										</li>
									</c:if>
									<c:if test="${memberAct.company != ''}">
										<li>
											<label class="ext-label">公司名称</label>
											<div class="ext-value">${memberAct.company}</div>
										</li>
									</c:if>
									<c:if test="${memberAct.jobTitle != ''}">
										<li>
											<label class="ext-label">公司职位</label>
											<div class="ext-value">${memberAct.jobTitle}</div>
										</li>
									</c:if>
									<c:if test="${memberAct.extra != ''}">
										<li>
											<label class="ext-label">备注信息</label>
											<div class="ext-value">${memberAct.extra}</div>
										</li>
									</c:if>
								</ul>
							</div>
						</c:forEach>
					</div>
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

    showActive('${input.createStart}', '${input.createEnd}', '#timeType');



	function check(content, memberActId, checkStatus) {
		layer.confirm(content, {
			icon : 3,
			title : '系统提示'
		}, function(index) {
			layer.close(index);
			$.post("${ctx}/activity/memberAct/verify.do", {
				id : memberActId,
				checkStatus : checkStatus
			}, function(data) {
				if (data.success == true) {
					layer.alert("审核成功", {
						icon : 6
					}, function(index) {
						window.location.reload();
					});
				} else {
					layer.alert("审核失败", {
						icon : 6
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
				var url = "${ctx}/activity/memberAct/export.do?actId=${activity.id}";
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