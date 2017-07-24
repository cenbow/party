<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>活动详情-${activity.title}</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/act_detail.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/img_text.css">
</head>

<%@include file="../include/header.jsp"%>
<body>
	<div class="index-outside">
		<%@include file="../include/sidebar.jsp"%>
		<section>
			<div class="section-main">
				<div class="c-time-list-header">
					<div class="r">
						<a href="javascript:history.back();" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
						</a>
					</div>
					<div class="ovh">
						<span class="title f20">我发布的活动 &gt; 活动详情</span>
					</div>
				</div>
				<div class="act-detail-head mt20">
					<div class="act-detail-content">
						<div class="detail-logo" style="background-image: url('${activity.pic}'),url(${ctx}/image/img_bg.png)"></div>
					</div>
					<div class="content-text">
						<p class="f24 text-titel">${activity.title}</p>
						<p class="f16 text-element">
							<i class="iconfont icon-remind btn-icon"></i> 开始时间：
							<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p class="f16 text-element">
							<i class="iconfont icon-moneybag btn-icon"></i> 活动费用：
							<c:if test="${activity.price == 0.0}">
								<strong class="active-red">免费</strong>
							</c:if>
							<c:if test="${activity.price > 0.0}">
								<strong class="active-red"><fmt:formatNumber value="${activity.price}" pattern="0.00"></fmt:formatNumber>&nbsp;元</strong>
							</c:if>
						</p>
						<p class="f16 text-element">
							<i class="iconfont icon-people btn-icon"></i> 报名人数：<strong class="active-red">${activity.joinNum}</strong>&nbsp;人
						</p>
						<p class="f16 text-element">
							<i class="iconfont icon-location btn-icon"></i> 活动地点：<span>${activity.area} ${activity.place}</span>
						</p>
					</div>
				</div>

				<div class="activity-count">
					<span>
						<i class="iconfont icon-rank btn-icon"></i> 浏览量：${count.viewNum}
					</span>
					<span class="ml-20">
						<i class="iconfont icon-group btn-icon"></i> 分销量：${count.shareNum}
					</span>
					<span class="ml-20">
						<i class="iconfont icon-people btn-icon"></i> 报名量：${count.applyNum}
					</span>
					<span class="ml-20">
						<i class="iconfont icon-roundcheck btn-icon"></i> 付费数：${count.salesNum}
					</span>
					<span class="ml-20">
						<i class="iconfont icon-recharge btn-icon"></i> 付费金额：${count.salesAmount}
					</span>
				</div>
				<div class="detail-head mt20">
					<span class="head-title f20">活动详情</span>
				</div>
				<div class="act-main-body img-text-content"><textarea class="dh" id="img-text-temp">${activityDetail.content}</textarea></div>				
			</div>
		</section>
	</div>
	<%@include file="../include/footer.jsp"%>
	<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/img_text.js"></script>
	<script type="text/javascript">
		$(function(){
			imgText.initImgNew('.img-text-content');
            setTimeout(function () {
                $('.lazy-img').picLazyLoad();
            },100);
		})
	</script>
</body>
</html>
