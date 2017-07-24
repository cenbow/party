<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>专题栏目详情</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/channel/channel_detail.css">
<style type="text/css">
	.act-detail-content .detail-logo{
		width: 120px!important;
		height: 120px!important;
	}
	.act-detail-content{
		flex : unset!important;
	}
</style>
</head>

<%@include file="../include/header.jsp"%>
<body>
	<div class="index-outside">
		<%@include file="../include/sidebar.jsp"%>
		<section>
			<div class="section-main">
				<div class="act-detail-head">
					<div class="act-detail-content">
						<div class="detail-logo" style="background-image: url('${apply.picture}'),url(${ctx}/image/img_bg.png)"></div>
					</div>
					<div class="content-text">
						<p class="f24 text-titel">${apply.name}</p>
						<p class="f16 text-element">
							发布时间：<fmt:formatDate value="${apply.createDate}" pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p class="f16 text-element">
							专题：<strong class='active-red'>${subject.name}</strong>
						</p>
						<p class="f16 text-element">
							栏目路径：${apply.url}
						</p>
						<p class="f16 text-element">
							描述：${apply.remarks}
						</p>
					</div>
				</div>
			</div>
		</section>
	</div>
	<%@include file="../include/footer.jsp"%>
</body>
</html>
