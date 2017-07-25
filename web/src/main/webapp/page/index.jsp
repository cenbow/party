<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="./include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>同行者-中国CEO户外活动与服务平台</title>
    <%@include file="./include/commonFile.jsp" %>
    <!-- 作者：Juliana 时间：2016-02-16 描述：PC公共css-->
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/index.css">
    <link rel="stylesheet" href="${ctx}/static/jquery.flexslider/flexslider.css">
</head>
<!--头部-->
<%@include file="./include/header.jsp" %>
<div class="banner">
    <div class="flexslider">
        <ul class="slides">
            <c:forEach var="advertise" items="${index.advertises}">
                <li class="slide-item">
                    <div class="banner-img" style="background-image: url('${advertise.pic}'),url(${ctx}/image/img_bg.png)"></div>
                </li>

            </c:forEach>
        </ul>
    </div>
</div>
<div class="index-outside">
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="acts-head">
                <span class="head-title f20">热门活动</span> <a href="javascript:void(0);" class="more-link">更多精彩活动></a>
            </div>
            <div class="acts-content">
                <div class="f-row mb10">
                    <div class="f-col-xs-6">
                        <a class="col-inner br" href="javascript:void(0);">
                            <img src="${index.bigPic.pic}?imageMogr2/auto-orient/crop/560x350" alt="${index.bigPic.title}">
                            <div class="act-front-content">
                                <p class="ellipsis-1">${index.bigPic.title}</p>
                                <p><span>${index.bigPic.area}</span>&nbsp;<span>${index.bigPic.thirdPartyName}</span></p>
                            </div>
                        </a>
                    </div>
                    <div class="f-col-xs-6">
                        <div class="f-row mb10">
                            <c:forEach items="${index.rightTopPicList}" var="topList" varStatus="status">
                                <div class="f-col-xs-6 col-small">
                                    <a class="col-inner bl <c:if test="${status.index == 0}">br</c:if>" href="javascript:void(0);">
                                        <img src="${topList.pic}?imageMogr2/auto-orient/crop/273x170" alt="${topList.title}">
                                        <div class="act-front-content">
                                            <p class="ellipsis-1">${topList.title}</p>
                                            <p><span>${topList.area}</span>&nbsp;<span>${topList.thirdPartyName}</span></p>
                                        </div>
                                    </a>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="f-row">
                            <c:forEach items="${index.rightBottomPicList}" var="bottomList" varStatus="status">
                                <div class="f-col-xs-6 col-small">
                                    <a class="col-inner bl <c:if test="${status.index == 0}">br</c:if>" href="javascript:void(0);">
                                        <img src="${bottomList.pic}?imageMogr2/auto-orient/crop/273x170" alt="${bottomList.title}">
                                        <div class="act-front-content">
                                            <p class="ellipsis-1">${bottomList.title}</p>
                                            <p><span>${bottomList.area}</span>&nbsp;<span>${bottomList.thirdPartyName}</span></p>
                                        </div>
                                    </a>
                                </div>

                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="f-row">
                    <c:forEach items="${index.bottomList}" var="bottomList" varStatus="status">
                        <div class="f-col-xs-3 col-small">
                            <a href="javascript:void(0);" class="col-inner <c:if test="${status.index == 1||status.index == 2}">br bl</c:if> <c:if test="${status.index == 0}">br</c:if> <c:if test="${status.index == 3}">bl</c:if>">
                                <img src="${bottomList.pic}?imageMogr2/auto-orient/crop/273x170" alt="${bottomList.title}">
                                <div class="act-front-content">
                                    <p class="ellipsis-1">${bottomList.title}</p>
                                    <p><span>${bottomList.area}</span>&nbsp;<span>${bottomList.thirdPartyName}</span></p>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="acts-head">
                <span class="head-title f20">多样玩法</span> <a href="javascript:void(0);" class="more-link">更多有趣玩法></a>
            </div>
            <div class="goods-content">
                <div class="f-row mb10">
                    <c:forEach items="${index.goodsTopList}" var="topList" varStatus="status">
                        <div class="f-col-xs-4">
                            <div class="good-item ${status.count==2?'ml10 mr10':''}">
                                <div class="good-item-bg" style="background-image:url(${topList.pic}),url(${ctx}/image/img_bg.png)">
                                </div>
                                <div class="good-front-content">
                                    <p class="f20 ellipsis-2 good-title">${topList.title}</p>
                                    <div class="good-body">
                                        <p class="good-detail r">
                                            <span class="good-type">${topList.categoryName}</span>
                                        </p>
                                        <p class="gray ellipsis-1">
                                            <span>${topList.area}</span>
                                            &nbsp;<span>${topList.thirdPartyName}</span>
                                            </span>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="f-row">
                    <c:forEach items="${index.goodsBottomList}" var="bottomList" varStatus="status">
                        <div class="f-col-xs-4">
                            <div class="good-item ${status.count==2?'ml10 mr10':''}">
                                <div class="good-item-bg" style="background-image:url(${bottomList.pic}),url(${ctx}/image/img_bg.png)">
                                </div>
                                <div class="good-front-content">
                                    <p class="f20 ellipsis-2 good-title">${bottomList.title}</p>
                                    <div class="good-body">
                                        <p class="good-detail r">
                                            <span class="good-type">${bottomList.categoryName}</span>
                                        </p>
                                        <p class="gray ellipsis-1">
                                            <span>${bottomList.area}</span>
                                            &nbsp;<span>${bottomList.thirdPartyName}</span>
                                            </span>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </section>
</div>
<!-- 合作伙伴 -->
<%@include file="./include/partner.jsp" %>

<!--底部-->
<%@include file="./include/footer.jsp" %>
<div class="f-def-dialog" id="download_dialog">
    <div class="f-dialog-shadow"></div>
    <div class="f-dialog-content">
        <span class="close-icon"><i class="iconfont icon-close"></i></span>
        <div class="dialog-header">
            <span class="title">下载App</span>
        </div>
        <div class="dialog-detail">
            <p class="f16 gray">扫描下方二维码，获取完整体验。</p>
            <img class="download-img" src="${ctx }/image/appqr_code.png"/>
        </div>
    </div>
</div>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/jquery.flexslider/jquery.flexslider-min.js"></script>
<script>
    $(function () {
        $('.more-link').click(function () {
            $('#download_dialog').fadeIn();
        });

        $(".acts-content .col-inner").click(function () {
            $('#download_dialog').fadeIn();
        });

        $(".acts-content .act-item-bg").click(function () {
            $('#download_dialog').fadeIn();
        });

        $(".goods-content .good-item-bg").click(function () {
            $('#download_dialog').fadeIn();
        });

        $(".flexslider").flexslider({
            slideshowSpeed: 4000, //展示时间间隔ms
            animationSpeed: 400, //滚动时间ms
            touch: true //是否支持触屏滑动
        });
    })
</script>
</body>
</html>