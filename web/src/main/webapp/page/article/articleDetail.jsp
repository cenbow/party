<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>文章详情</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/act_detail.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/img_text.css">
</head>

<%@include file="../include/header.jsp"%>
<body>
	<div class="index-outside">
		<%@include file="../include/sidebar.jsp"%>
		<section>
			<div class="section-main">
				<div class="act-detail-head">
					<div class="act-detail-content">
						<div class="detail-logo" style="background-image: url('${article.pic}'),url(${ctx}/image/img_bg.png)"></div>
					</div>
					<div class="content-text">
						<p class="f24 text-titel">${article.title}</p>
						<p class="f16 text-element">
							${article.subTitle}
						</p>
						<p class="f16 text-element">
							<i class="iconfont icon-remind btn-icon"></i> 发布时间：
							<fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd HH:mm" />
						</p>
						<c:if test="${article.channel != null}">
							<p class="f16 text-element">
								<i class="iconfont icon-moneybag btn-icon"></i> 频道：<strong class="active-red">${article.channel.name}</strong>
							</p>
						</c:if>
						<div>
							<p class="f16 text-element" style="float: left; margin-right: 20px;">
								<i class="iconfont icon-people btn-icon"></i> 阅读量：<strong class="active-red">${article.readNum}</strong>
							</p>
							<p class="f16 text-element">
								文章类型：<span><strong class="active-red">${article.articleType}</strong></span>
							</p>
						</div>
					</div>
				</div>
				<div class="detail-head mt20">
					<span class="head-title f20">文章详情</span>
				</div>
				<div class="act-main-body img-text-content"><textarea class="dh" id="img-text-temp">${article.content}</textarea></div>
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
