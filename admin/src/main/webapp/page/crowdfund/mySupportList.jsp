<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>众筹项目列表</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/crowdfund/project_list.css">
    <%@include file="../include/commonFile.jsp" %>
</head>

<%@include file="../include/header.jsp" %>
<body>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp" %>
    <section>
        <div class="section-main">
            <div class="my-project-list-header">
                <div class="ovh">
                    <span class="title f20">我支持的众筹</span>
                    <span class="f12">共<b id="totalCount"></b>条记录</span>
                </div>
            </div>

            <div class="my-project-list-content">
                <ul id="view">

                </ul>
            </div>
        </div>
    </section>
</div>
<%@include file="../include/footer.jsp" %>
<script id="project_constant" type="text/html">
    {{#  layui.each(d, function(index, item){ }}
    <li >
        <div class="apply-lable" >
            <i class="time-line-round"></i>
            <div class="lable-bubble">
                <i class="bubble-arrow"></i>
                <span class="bubble-text"> {{ getDateStr(item.createDate).Format('yyyy-MM-dd hh:mm') }} 支持了该众筹</span>
            </div>
        </div>
        <div class="project-detail-content">
            <div class="project-logo" style="background-image: url({{ item.pic }}),url(${ctx}/image/img_bg.png)" data-id="{{ item.id }}"></div>
            <div class="project-detail">
                <div class="project-title" data-id="{{ item.id }}">
                    <span class="title f18">{{ item.title }}</span>
                </div>
                <div>
                    <p class="start-time">开始时间：{{ getDateStr(item.createDate).Format('yyyy-MM-dd') }} </p>
                    <p class="project-price">目标筹款：<span>{{ item.targetAmount }}</span>元</p>
                    <p class="projectt-price">已筹款：<span>{{ item.actualAmount }}</span>元</p>
                    <p class="project-price">支持人数：<span>{{ item.favorerNum }}</span>人</p>
                </div>
                <div class="opts-btns">
                    <button  data-id="{{ item.id }}" class="layui-btn layui-btn-primary"><i class="iconfont icon-edit btn-icon"></i> 修改</button>
                </div>
            </div>
        </div>
    </li>
    {{#  }); }}
</script>
<script type="text/javascript">
    var pageNo = 1;
    var noMoreData = false;

    function bind() {
        $('.project-detail-content button').on('click', function () {
            var id = $(this).attr('data-id');
            window.location.href = '${ctx}/crowdfund/project/launchView.do?id=' + id;
        });
        $('.my-project-list-content .project-title').on('click', function () {
            var id = $(this).attr('data-id');
            window.location.href = '${ctx}/crowdfund/project/detail.do?id=' + id;
        });
    }
    $(function () {
        layui.use('laytpl', function() {
            var laytpl = layui.laytpl;
            var $ = layui.jquery; //重点处

            function fillData(){
                if(noMoreData){
                    return;
                }
                var url = "${ctx}/crowdfund/project/asynSupportList.do";
                var params = {
                    pageNo : pageNo
                };

                function callBack(data) {
                    $("#totalCount").text(data.page.totalCount);
                    var container = $(".my-project-list-content");
                    if (pageNo == 1) {
                        view.innerHTML = "";
                    }
                    if (pageNo == 1 && data.datas.length == 0) {
                        $(container).append("<div class='no-more-data f18'>还没有数据.</div>");
                        noMoreData = true;
                        return;
                    }
                    if (pageNo > 1 && data.datas.length == 0) {
                        $("#loadMore").hide();
                        $(container).append("<div class='no-more-data f18'>没有更多数据了.</div>");
                        noMoreData = true;
                        return;
                    }
                    var getTpl = project_constant.innerHTML;
                    laytpl(getTpl).render(data.datas, function(html) {
                        view.innerHTML += html;
                        bind();
                    });

                    var loadMore = $("#loadMore");
                    if(loadMore.length == 0){
                        $(container).append("<div id='loadMore' class='load-more-data f18'>点击加载更多.</div>");
                    }
                }

                $.post(url, params, function(data) {
                    callBack(data);
                });
            }

            fillData();
            $(".my-project-list-content").on("click",'div#loadMore', function () {
                if (!noMoreData) {
                    pageNo++;
                    fillData();
                }
            });
        });
    })

</script>
</body>
</html>
