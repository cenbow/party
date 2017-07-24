<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>活动详情-${activity.title}</title>
<%@include file="../include/commonFile.jsp"%>
<style>
.p-view-wrap {
	margin: 0 auto;
}

.p-view-wrap .p-content {
	background-image: url(${ctx}/image/phone_bg.png);
	background-repeat: no-repeat;
	background-position: center;
	background-size: contain;
	margin: 0 auto;
	width: 285px;
	height: 568px;
	position: relative;
}

.p-view-wrap .p-content .p-view-inner iframe {
	position: absolute;
	top: 1px;
	left: -16px;
	width: 316px;
	height: 560px;
	transform: scale(0.8);
}
</style>
</head>

<%@include file="../include/header.jsp"%>
<body>
	<div class="index-outside">
		<section>
			<div class="section-main">
				<div class="p-view-wrap">
					<div class="p-content">
						<div class="p-view-inner">
							<iframe
								src="http://192.168.31.192:8980/micWeb/html/hd/hd_main.html"
								frameBorder="no"></iframe>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
	<%@include file="../include/footer.jsp"%>
	<script type="text/javascript" charset="utf-8"
		src="${ctx}/script/common/img_text.js"></script>
	<script type="text/javascript">
		$(function() {

		})
	</script>
</body>
</html>
