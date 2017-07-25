<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>活动报名管理</title>
	<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
	<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
	<link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/member_list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/member_act_list.css">
<%@include file="../include/commonFile.jsp" %>
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp" %>
    <!--内容-->
    <section>
        <div class="section-main">
          	<div class="my-act-list-header"><span class="title f16">

				<c:if test="${not empty targetId}">
				<a href="${ctx}/distribution/relation/targetList/${targetId}.do">分销管理 &gt;</a>
				</c:if>
				<c:if test="${not empty hrefId }">
				<a href="${ctx}/distribution/relation/childList/${hrefId}.do">下级分销管理 &gt;</a>
				</c:if>
				报名管理 </div>
           	<div class="my-act-list-title">
           		<span class="f16">报名管理</span>
           	</div>
			<form class="layui-form" action="${ctx}/distribution/apply/listView.do?distributionId=${distributionId}" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" />
				<input type="hidden" name="targetId" id="targetId" value="${targetId}" />
				<input type="hidden" name="hrefId" id="hrefId" value="${hrefId}" />
			</form>

            <div class="my-act-list-content">
				<div class="cl">
					<ul class="header">
						<li style="width: 12%">报名人</li>
						<li style="width: 10%">联系方式</li>
						<li style="width: 13%">报名时间</li>
						<li style="width: 8%">金额</li>
						<li style="width: 8%">状态</li>
						<li style="width: 17%">操作</li>
						<li style="width: 4%"></li>
					</ul>
					<label id="view" class="cl content-body">
						<c:forEach var="apply" items="${list}">
							<div class="info">
								<ul class="content">
									<li style="width: 12%" class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${apply.member.id}','400px','470px')">
										<div class="member-cell">
										<div class="member-logo"
											 style="background-image: url('${apply.member.logo}'),url(${ctx}/image/def_user_logo.png)"
										></div>
										<div class="member-name ellipsis-1">
											<a class="blue" title="${apply.member.realname}" href="javascript:void(0);">${apply.member.realname}</a>
										</div>
										</div>
									</li>
									<li style="width: 10%">${apply.mobile}</li>
									<li style="width: 13%"><fmt:formatDate value="${apply.createDate}" pattern="yyyy-MM-dd HH:mm" /></li>
									<li style="width: 8%"><c:if test="${apply.payment == 0}">免费报名</c:if> <c:if test="${apply.payment != 0}">¥${apply.payment}</c:if></li>
									<li style="width: 8%"><c:choose>
										<c:when test="${apply.checkStatus == 0}">
											<span>审核中</span>
										</c:when>
										<c:when test="${apply.checkStatus == 1}">
											<span>待支付</span>
										</c:when>
										<c:when test="${apply.checkStatus == 2}">
											<span>审核拒绝</span>
										</c:when>
										<c:when test="${apply.checkStatus == 3}">
											<span>已支付</span>
										</c:when>
										<c:when test="${apply.checkStatus == 4}">
											<span>已取消</span>
										</c:when>
										<c:when test="${apply.checkStatus == 5}">
											<span>未参与</span>
										</c:when>
									</c:choose></li>
									<li style="width: 17%">
										<c:if test="${apply.checkStatus == 0}">
										<a class="green" href="javascript:check('确认要审核通过该报名吗？', '${apply.id}', '1')" target="_self">通过</a>
										<span>|</span>
										<a class="red" href="javascript:check('确认要审核拒绝该报名吗？', '${apply.id}', '2')" target="_self">拒绝</a>
									</c:if></li>
									<li style="width: 4%; padding-left: 0" class="option"><i class="iconfont icon-unfold"></i>
										<i class="iconfont icon-fold"></i></li>
									<div class="cl"></div>
								</ul>
								<ul class="tr-extra-content">
									<c:if test="${apply.name != ''}">
										<li>
											<label class="ext-label">联系人</label>
											<div class="ext-value">${apply.name}</div>
										</li>
									</c:if>
									<c:if test="${apply.company != ''}">
										<li>
											<label class="ext-label">公司名称</label>
											<div class="ext-value">${apply.company}</div>
										</li>
									</c:if>
									<c:if test="${apply.jobTitle != ''}">
										<li>
											<label class="ext-label">公司职位</label>
											<div class="ext-value">${apply.jobTitle}</div>
										</li>
									</c:if>
									<c:if test="${apply.extra != ''}">
										<li>
											<label class="ext-label">备注信息</label>
											<div class="ext-value">${apply.extra}</div>
										</li>
									</c:if>
								</ul>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div id="page_content" class="page-container"></div>
    </section>
</div>

<!--底部-->
<%@include file="../include/footer.jsp" %>

<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">


	function check(content, memberActId, checkStatus) {
		layer.confirm(content, {
			icon : 3,
			title : '系统提示'
		}, function(index) {
			layer.close(index);
			$.post("${ctx}/activity/memberAct/verify.do", {id : memberActId, checkStatus : checkStatus}, function(data) {
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

	$(function(){
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
	})
</script>
</body>
</html>