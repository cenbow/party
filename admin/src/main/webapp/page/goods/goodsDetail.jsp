<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>玩法详情</title>
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
				<c:if test="${goods.type == 0}">
					<div class="c-time-list-header">
						<div class="r">
							<a href="${ctx}/goods/goods/bzGoodsList.do"
								class="layui-btn layui-btn-radius layui-btn-danger"> <i
								class="iconfont icon-refresh btn-icon"></i>返回
							</a>
						</div>
						<div class="ovh">
							<span class="title f20">标准玩法管理 &gt; 标准玩法详情</span>
						</div>
					</div>
				</c:if>
				<c:if test="${goods.type == 1}">
					<div class="c-time-list-header">
						<div class="r">
							<a href="${ctx}/goods/goods/dzGoodsList.do"
								class="layui-btn layui-btn-radius layui-btn-danger"> <i
								class="iconfont icon-refresh btn-icon"></i>返回
							</a>
						</div>
						<div class="ovh">
							<span class="title f20">定制玩法管理 &gt; 定制玩法详情</span>
						</div>
					</div>
				</c:if>
				<div class="act-detail-head mt20">
					<div class="act-detail-content">
						<div class="detail-logo"
							style="background-image: url('${goods.picsURL}'),url(${ctx}/image/img_bg.png)"></div>
					</div>
					<div class="content-text">
						<p class="f24 text-titel">${goods.title}</p>
						<p class="f16 text-element">
							<i class="iconfont icon-remind btn-icon"></i> 开始时间：
							<fmt:formatDate value="${goods.startTime}" pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p class="f16 text-element">
							<i class="iconfont icon-moneybag btn-icon"></i> 玩法价格：<strong class="active-red"><fmt:formatNumber
									value="${goods.price}" pattern="0"></fmt:formatNumber></strong>&nbsp;元
						</p>
						<p class="f16 text-element">
							<i class="iconfont icon-people btn-icon"></i> 报名人数：<strong class="active-red">${goods.joinNum}</strong>&nbsp;人
						</p>
						<p class="f16 text-element">
							<i class="iconfont icon-location btn-icon"></i> 活动地点：<span>${goods.area}
								${goods.place}</span>
						</p>
					</div>
				</div>
				<div class="detail-head mt20">
					<span class="head-title f20">推荐理由</span>
				</div>
				<div id="recommend">
					<div class="act-main-body img-text-content">
						<textarea class="dh" id="img-text-temp">${goods.recommend}</textarea>
					</div>
				</div>
				<div class="detail-head mt20">
					<span class="head-title f20">玩法详情</span>
				</div>
				<div id="goodsDetail">
					<div class="act-main-body img-text-content">
						<textarea class="dh" id="img-text-temp">${goodsDetail.content}</textarea>
					</div>
				</div>
				<div class="detail-head mt20">
					<span class="head-title f20">注意事项</span>
				</div>
				<div id="notice">
					<div class="act-main-body img-text-content">
						<textarea class="dh" id="img-text-temp">${goods.notice}</textarea>
					</div>
				</div>
			</div>
		</section>
	</div>
	<%@include file="../include/footer.jsp"%>
	<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/img_text.js"></script>
	<script type="text/javascript">
		$(function() {
			$(".img-text-content").each(function(index, element) {
				imgText.initImgNew(this);
			});
			setTimeout(function() {
				var recommendSettings = {
					parent : $('#recommend .img-text-content')
				};
				$('.lazy-img').picLazyLoad(recommendSettings);

				var detailSettings = {
					parent : $('#goodsDetail .img-text-content')
				};
				$('.lazy-img').picLazyLoad(detailSettings);

				var noticeSettings = {
					parent : $('#notice .img-text-content')
				};
				$('.lazy-img').picLazyLoad(noticeSettings);
			}, 100);
		})
	</script>
</body>
</html>
