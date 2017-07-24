<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>项目详情</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/zc_act_detail.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/img_text.css">
	<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
	<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
	<link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/member_list.css">
	<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/member_act_list.css">

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
				        <span class="title f20">我发布的众筹项目 &gt; 众筹项目详情</span>
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
				            <i class="iconfont icon-moneybag btn-icon"></i> 报名费用：<strong class="red"><fmt:formatNumber value="${activity.price}"></fmt:formatNumber></strong>&nbsp;元
				        </p>
				        <p class="f16 text-element">
				            <i class="iconfont icon-people btn-icon"></i> 报名人数：<strong class="red">${activity.joinNum}</strong>&nbsp;人
				            <i class="iconfont icon-people btn-icon  ml-30"></i> 众筹成功：<strong class="red">${activity.crowdfundedNum}</strong>&nbsp;人
				        </p>
				        <p class="f16 text-element">
				            <i class="iconfont icon-people btn-icon"></i> 支持人数：<strong class="red">${activity.favorerNum}</strong>&nbsp;人
				            <i class="iconfont icon-moneybag btn-icon ml-30"></i> 已筹金额：<strong class="red">${actualAmount}</strong>&nbsp;元
				        </p>
				    </div>
				</div>
				<div class="layui-tab mt20">
					<ul class="layui-tab-title">
						<li class="layui-this">详情描述</li>
						<li>报名相关</li>
						<li>参赛标准</li>
					</ul>
					<div class="layui-tab-content">
						<div class="layui-tab-item layui-show" >
							<div id="content">
								<div class="act-main-body img-text-content">
									<textarea class="dh" id="img-text-temp">${activityDetail.content}</textarea>
								</div>
							</div>
						</div>
						<div class="layui-tab-item" >
							<div id="applyRelated">
								<div class="act-main-body img-text-content">
									<textarea class="dh" id="img-text-temp">${activityDetail.applyRelated}</textarea>
								</div>
							</div>
						</div>
						<div class="layui-tab-item" >
							<div id="matchStandard">
								<div class="act-main-body img-text-content">
									<textarea class="dh" id="img-text-temp">${activityDetail.matchStandard}</textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
	<%@include file="../include/footer.jsp"%>
	<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/img_text.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
	<script type="text/javascript">
		var activeTab = 1;

		var laytpl = null;
		var laypage = null;
		var element = null;


		$(function() {

            layui.use([ 'laytpl', 'laypage', 'element' ], function() {
                element = layui.element();
                element.on('tab', function(data) {
                    var content = $(".layui-tab-content").find(".layui-show").find(".img-text-content");
                    var imgs = $(content).find("img");
                    for (var i = 0; i < imgs.length; i++) {
                        var img = imgs[i];
                        imgText.changeWH(img);
                    }
                });
            });
			$(".img-text-content").each(function(index, element) {
				imgText.initImgNew(this);
			});
			setTimeout(function() {
				var recommendSettings = {
					parent : $('#content .img-text-content')
				};
				$('.lazy-img').picLazyLoad(recommendSettings);

				var detailSettings = {
					parent : $('#applyRelated .img-text-content')
				};
				$('.lazy-img').picLazyLoad(detailSettings);

				var noticeSettings = {
					parent : $('#matchStandard .img-text-content')
				};
				$('.lazy-img').picLazyLoad(noticeSettings);
			}, 100);
		})
	</script>
</body>
</html>
