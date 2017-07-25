<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>分销商管理</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/member_act_list.css">
    <%@include file="../include/commonFile.jsp" %>
</head>

<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp" %>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="my-act-list-header"><span class="title f16">分销商列表  </div>
            <div class="my-act-list-content">
                <div class="cl">
                    <ul class="header">
                        <li style="width: 15%">姓名</li>
                        <li style="width: 10%">账号</li>
                        <li style="width: 13%">手机号码</li>
                        <li style="width: 8%">职位</li>
                        <li style="width: 17%">操作</li>
                        <li style="width: 4%"></li>
                    </ul>
                    <div id="view" class="cl content-body">
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<!--底部-->
<%@include file="../include/footer.jsp" %>
<script id="demo" type="text/html">
    {{#  layui.each(d, function(index, item){ }}
    <div class="info">
        <ul class="content">
            <li style="width: 15%" class="member-info">
                <div class="member-cell">
                <div class="member-logo" style="background-image: url('{{ item.logo }}'),url(${ctx}/image/def_user_logo.png)"></div>
                <span class="member-name">{{ item.realname || ''}}</span>
                </div>
            </li>
            <li style="width: 10%">{{ item.username || ''}}</li>
            <li style="width: 10%">{{ item.mobile || ''}}</li>
            <li style="width: 10%">{{ item.jobTitle ||''}}</li>
            </li>
            <li style="width: 17%">
                <button class="layui-btn layui-btn-small" id="onDistributor" id-data="{{item.id}}">
                    <i class="layui-icon">&#xe605;</i> 开启分销
                </button>
            </li>
        </ul>
    </div>
    {{#  }); }}
</script>
<script type="text/javascript">
    var pageNo = 1;
    var noMoreData = false;
    $(function () {
        layui.use('laytpl', function() {
            var laytpl = layui.laytpl;

            function fillData(){
                if(noMoreData){
                    return;
                }
                var url = "${ctx}/member/distributor/list.do";
                var params = {
                    pageNo : pageNo,
                };

                function callBack(data) {

                    $("#totalCount").text(data.page.totalCount);

                    var container = $(".my-act-list-content");
                    if (pageNo == 1) {
                        view.innerHTML = "";
                    }
                    if (pageNo == 1 && data.datas.length == 0) {
                        $(container).append("<div class='no-more-data f18' colspan='6'>还没有数据.</div>");
                        noMoreData = true;
                        return;
                    }
                    if (pageNo > 1 && data.datas.length == 0) {
                        $("#loadMore").hide();
                        $(container).append("<div class='no-more-data f18'>没有更多数据了.</div>");
                        noMoreData = true;
                        return;
                    }
                    var getTpl = demo.innerHTML;
                    laytpl(getTpl).render(data.datas, function(html) {
                        view.innerHTML += html;
                    });

                    var loadMore = $("#loadMore");
                    if(loadMore.length == 0){
                        $(container).append("<div id='loadMore' class='load-more-data f18'>点击加载更多.</div>");
                    }

                    bindEven();
                }

                $.post(url, params, function(data) {
                    callBack(data);
                });
            }

            fillData();

            $(".my-act-list-content").on("click",'div#loadMore', function () {
                if (!noMoreData) {
                    pageNo++;
                    fillData();
                }
            });
        });
    })


    function bindEven() {

        $('#onDistributor').on('click', function () {
            var id = $(this).attr('id-data');
            layer.confirm('确定开启分销？', {icon: 3, title:'系统提示'}, function(index){
                layer.close(index);
                $.post('', {id : id}, function (res) {
                    location.reload();
                });
            });
        });

        $('#offDistributor'),on('click', function () {
            var id = $(this).attr('id-data');
            layer.confirm('确定开启分销？', {icon: 3, title:'系统提示'}, function(index){
                layer.close(index);
                $.post('', {id : id}, function (res) {
                    location.reload();
                });
            });
        });
    }
</script>
</html>
