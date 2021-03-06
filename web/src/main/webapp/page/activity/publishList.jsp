<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>我发布的活动</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<%@include file="../include/commonFile.jsp"%>
<style type="text/css">
.f-def-dialog .f-dialog-content {
	width: 550px !important;
	height: 300px !important;
}

.f-dialog-content .dialog-detail {
	width: auto !important;
}

.dialog-detail .download-img {
	margin-top: 0px !important;
}
</style>
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
					<a href="${ctx}/activity/activity/publishAct.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i
						class="iconfont icon-add btn-icon"></i>发布活动
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">我发布的活动</span> 
				</div>
			</div>
			<form class="layui-form" action="${ctx}/activity/activity/activityList.do" id="myForm" method="get">
				<input type="hidden" name="pageNo" id="pageNo" />
				<div class="f-search-bar">
					<div class="search-container">
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
			<div class="c-time-list-content">
				<ul id="view">
					<c:if test="${page.totalCount == 0}">
						<div class="f16 tc mt15">还没有数据，请点击右上角的“发布活动”，发布和编辑内容</div>
					</c:if>
					<c:forEach var="activity" items="${activities}">
						<li>
							<div class="detail-content">
								<a href="${ctx}/activity/activity/activityDetail.do?actId=${activity.id}" class="act-logo"
									style="background-image: url('${activity.pic}'),url(${ctx}/image/img_bg.png)"> </a>
								<div class="detail">
									<div class="act-title">
<%-- 										<c:choose> --%>
<%-- 											<c:when test="${activity.checkStatus == 0}"> --%>
<!-- 												<span class="status f18 red">审核中</span> -->
<%-- 											</c:when> --%>
<%-- 											<c:when test="${activity.checkStatus == 1}"> --%>
<%-- 												<c:set var="nowDate" value="<%=new Date()%>"></c:set> --%>
<%-- 												<c:if test="${activity.endTime > nowDate}"> --%>
<!-- 													<span class="status f18 red">进行中</span> -->
<%-- 												</c:if> --%>
<%-- 												<c:if test="${activity.endTime < nowDate}"> --%>
<!-- 													<span class="status f18 dark">已截止</span> -->
<%-- 												</c:if> --%>
<%-- 											</c:when> --%>
<%-- 											<c:when test="${activity.checkStatus == 2}"> --%>
<!-- 												<span class="status f18 dark">已拒绝</span> -->
<%-- 											</c:when> --%>
<%-- 										</c:choose> --%>
										<a href="${ctx}/activity/activity/activityDetail.do?actId=${activity.id }" class="title f18 text-ellipsis">${activity.title }</a>
									</div>
									<div class="">
										<p class="act-price">
											<span style="margin-right: 10px;">活动开始时间：<fmt:formatDate value="${activity.startTime}"
													pattern="yyyy-MM-dd HH:mm" /></span> <span>报名截止时间：<fmt:formatDate value="${activity.endTime}"
													pattern="yyyy-MM-dd HH:mm" /><span>
										</p>
										<p class="act-price">
											<span style="margin-right: 10px;">报名费用：
												<c:if test="${activity.price == 0.0}">
													<b class="active-red">免费</b>
												</c:if>
												<c:if test="${activity.price > 0.0}">
													<b class="active-red"><fmt:formatNumber value="${activity.price}" pattern="0.00"></fmt:formatNumber></b>元
												</c:if>
											</span>
											<span>报名人数：<b class="active-red">${activity.joinNum }</b>人<span>
										</p>
										<p class="start-time">活动地点：${activity.area } ${activity.place }</p>
										<p class="act-price">
											发布时间：
											<fmt:formatDate value="${activity.updateDate}" pattern="yyyy-MM-dd HH:mm" />
										</p>
									</div>
									<div class="opts-btns tb-opts" style="width:475px;">
										<div class="comm-opts">
											<c:if test="${activity.checkStatus == 1}">
												<a class="qr-btn" href="javascript:void(0);"><i class="iconfont icon-qrcode btn-icon"></i> 二维码</a>
											</c:if>
											<a href="${ctx}/activity/activity/publishAct.do?id=${activity.id }"><i class="iconfont icon-edit btn-icon"></i>编辑</a>
											<a href="javascript:deleteObj('确定要删除吗？','${ctx}/activity/activity/delete.do?id=${activity.id}','该活动已有人报名，不能删除')">
												<i class="iconfont icon-delete btn-icon"></i> 删除
											</a>
											<c:if test="${activity.checkStatus == 1}">
												<a href="${ctx}/activity/memberAct/memberActList.do?actId=${activity.id }"><i class="iconfont icon-peoplelist btn-icon"></i>报名管理</a>
												<a href="${ctx}/distribution/relation/targetList/${activity.id }.do"><i class="iconfont icon-xinxi btn-icon"></i>分销</a>
											</c:if>
											<a href="javascript:createCircle('${activity.id}')"> <i class="iconfont icon-check btn-icon"></i>生成圈子</a>
										</div>
									</div>
								</div>
							</div>
							<div class="f-def-dialog bmDialog">
								<div class="f-dialog-shadow"></div>
								<div class="f-dialog-content">
									<span class="close-icon"><i class="iconfont icon-close"></i></span>
									<div class="dialog-detail">
										<p class="f16 gray">扫码二维码可预览分享</p>
										<div style="width: 490px; margin: 0px auto; margin-top: 10px">
											<div class="l mr20">
												<div class="f18">报名二维码</div>
												<img class="download-img" src="${qr_code}/${activity.qrCodeMap.detailQrCode }" />
												<div>
													<a class="download-qrcode" href="javascript:download('${ctx}','${activity.qrCodeMap.detailQrCode}')">下载二维码</a>
												</div>
												<div>
													<a class="download-qrcode looklink" href="javascript:lookLink('${ctx}','${activity.qrCodeMap.detailQrCodeContent}', '查看报名二维码链接')">查看报名链接</a>
												</div>
											</div>
											<div class="l mr20">
												<div class="f18">签到二维码</div>
												<img class="download-img" src="${qr_code}/${activity.qrCodeMap.signQrCode }" />
												<div>
													<a class="download-qrcode" href="javascript:download('${ctx}','${activity.qrCodeMap.signQrCode }')">下载二维码</a>
												</div>
											</div>
											<div class="l">
												<div class="f18">分销二维码</div>
												<img class="download-img" src="${qr_code}/${activity.qrCodeMap.disQrCode }" />
												<div>
													<a class="download-qrcode" href="javascript:download('${ctx}','${activity.qrCodeMap.disQrCode}')">下载二维码</a>
												</div>
												<div>
													<a class="download-qrcode looklink" href="javascript:lookLink('${ctx}','${activity.qrCodeMap.disQrCodeContent}', '查看分销二维码链接')">查看分销链接</a>
												</div>
											</div>
											<div class="cl"></div>
										</div>
									</div>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
				<div class="f-def-dialog linkDialog">
					<div class="f-dialog-shadow"></div>
					<div class="f-dialog-content" style="width: 490px!important;height: auto!important;">
						<span class="close-icon"><i class="iconfont icon-close"></i></span>
						<div class="dialog-detail">
							<p class="f16 gray" id="dialog_title"></p>
							<div style="width: auto!important;margin: 0px auto;margin-top: 10px;padding-left: 20px;padding-right: 20px;">
								<div id="dialog_content" class="f16" style="word-wrap: break-word;margin: 0px auto;line-height: 30px;"></div>
							</div>
						</div>
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
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">

    showActive('${input.createStart}', '${input.createEnd}', '#timeType');


	$(function () {
        // 加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })

	// 二维码弹窗显示/隐藏
	qrCodeDialog('.c-time-list-content', 'qr-btn', '.detail-content', '.bmDialog');
	
	function lookLink(ctx, content, title) {
		$(".linkDialog").find("#dialog_title").text(title);
		$(".linkDialog").find("#dialog_content").text(content);
		qrCodeDialog('.c-time-list-content', 'looklink', '.dialog-detail', '.linkDialog', "2");
	}
	
	function createCircle(actId) {
		var content = "确定要生成吗？请选择生成<br/><label for='all1' class='mr10'><input type='radio' id='all1' name='type' value='2' checked='checked' >全部报名人员</label>"+
					  "<label for='success'><input type='radio' id='success' name='type' value='1' >已成功报名人员</label>";
		// content = "确定要生成吗？";
  		layer.confirm(content, {
            icon: 3,
            title: '系统提示'
        }, function (index) {
        	var type = $("[name=type]:checked").val() || '2';
            layer.close(index);
          	//loading层
			var loadIndex = layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
            $.post("${ctx}/activity/activity/createCircle.do", {
                actId: actId,
                type : type
            }, function (data) {
            	layer.close(loadIndex);
            	setTimeout(function() {
	            	if (data.success == true) {
	                	layer.msg('生成成功', {icon : 1});
	                } else {
	                	layer.msg('生成失败', {icon : 2});
	                }
            	});
            })
        });
  	}
</script>
</body>
</html>