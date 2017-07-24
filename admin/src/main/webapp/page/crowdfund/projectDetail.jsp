<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>众筹详情</title>
    <%@include file="../include/commonFile.jsp" %>
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/crowdfund/project_detail.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/iconfont.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/img_text.css">
</head>

<%@include file="../include/header.jsp" %>
<body>
    <div class="index-outside">
        <%@include file="../include/sidebar.jsp" %>
            <section>
                <div class="section-main">
                    <div class="croedfund-detail-head">
                            <div class="croedfund-detail-content">
                                <div class="detail-logo" style="background-image:url(${project.pic}),url(${ctx}/image/img_bg.png)"></div>
                            </div>
                            <div class="content-text">
                                <p  class="f24 text-titel">${project.title}</p>
                                <p  class="f16 text-element"><i class="iconfont icon-remind"></i> 开始时间: <span><fmt:formatDate value="${project.createDate}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                                <p  class="f16 text-element"><i class="iconfont icon-moneybag"></i> 目标筹款 <span>${project.targetAmount}</span> 元</p>
                                <p  class="f16 text-element"><i class="iconfont icon-moneybag"></i> 已筹款 <span class="text-stress">${project.actualAmount}</span> 元</p>
                                <p  class="f16 text-element"><i class="iconfont icon-people"></i> 支持人数 <span class="text-stress">${project.favorerNum}</span>人</p>
                            </div>
                    </div>
                    <div class="detail-head mt20">
                        <span class="head-title f20">众筹详情</span>
                    </div>
                    <div class="crowdfund-main-body img-text-content"><textarea class="dh" id="img-text-temp">${project.content}</textarea></div>
                </div>
            </section>
    </div>
    <%@include file="../include/footer.jsp" %>
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
