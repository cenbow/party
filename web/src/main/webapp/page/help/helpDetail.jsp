<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>帮助教程</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/img_text.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <%@include file="../include/commonFile.jsp" %>
    <style type="text/css">
        .index-outside > section .section-main {

        }

        .section-main {
            padding: 0px;
        }

        .index-outside > aside {
            width: 270px;
            float: right;
            border-right: 1px solid #dbdee5;
        }

        .index-outside > aside > .aside-main-new {

        }

        .index-outside > aside > .aside-main-new .aside-dl-new {
            margin: 10px 0;
        }

        .index-outside > aside > .aside-main-new .aside-dt-new {
            line-height: 30px;
            color: #4c5161;
            font-size: 18px;
        }

        .index-outside > aside > .aside-main-new .aside-dt-new > a {
            padding-bottom: 10px;
            padding-top: 10px;
            padding-left: 20px;
            display: block;
        }

        .index-outside > aside > .aside-main-new .aside-nav-new {
            display: flex;
            line-height: 25px;
            padding-bottom: 10px;
            padding-top: 10px;
            padding-left: 20px;
            color: #4c5161;
        }

        .index-outside > aside > .aside-main-new .aside-nav-new div:first-child {
            float: left;
            width: 30px;
        }

        .index-outside > aside > .aside-main-new .aside-nav-new div:last-child {
            float: left;
            width: 240px;
        }

        .index-outside > section {
            width: 880px !important;
        }

        .bg-active-red {
            background-color: #e8473f !important;
            color: #FFFFFF!important;
        }
    </style>
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <!-- 侧边栏 -->
    <aside>
        <div class="aside-main-new">
            <dl class="aside-dl-new">
                <dt class="aside-dt-new">
                    <a class="${help.id == currentHelp.id ? 'bg-active-red' : ''}" href="${ctx}/help/help/${help.id}/getDetail.do"><fmt:formatNumber value="${help.serialNumber}" pattern="#.#"/>、${help.title}</a>
                </dt>
                <dd>
                    <c:forEach var="hh" items="${help.childrens}">
                        <a class="aside-nav-new ${hh.id == currentHelp.id ? 'bg-active-red' : ''}" href="${ctx}/help/help/${hh.id}/getDetail.do">
                            <div><fmt:formatNumber value="${hh.serialNumber}" pattern="#.#"/></div>
                            <div>${hh.title}</div>
                        </a>
                    </c:forEach>
                </dd>
            </dl>
        </div>
    </aside>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/help/help/list.do" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-refresh btn-icon"></i> 返回列表
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">帮助教程&nbsp;&gt;&nbsp;${currentHelp.title}</span>
                </div>
            </div>
            <div class="act-detail-head mt20">
                <div class="img-text-content"><textarea class="dh" id="img-text-temp">${currentHelp.content}</textarea></div>
            </div>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/img_text.js"></script>
<script type="text/javascript">
    $(function () {
        imgText.initImgNew('.img-text-content');
        setTimeout(function () {
            $('.lazy-img').picLazyLoad();
        }, 100);

        $(".aside-dl-new dd").delegate('a', 'click', function (e) {
            var $target = $(e.target);
            var $nav = $target.closest(".aside-nav-new");
            $nav.addClass("bg-active-red");
            $nav.siblings(".aside-nav-new").removeClass("bg-active-red");
        })
    })
</script>
</body>
</html>