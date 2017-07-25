<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>项目详情</title>
    <%@include file="../include/commonFile.jsp"%>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">

</head>
<%@include file="../include/header.jsp"%>
<body>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp"%>
    <section>
        <div class="section-main">
            <%@include file="detailHead.jsp"%>
            <div class="layui-tab layui-tab-brief mt20">
                <ul class="layui-tab-title">
                    <li class="layui-this" id="representing">代言<span id="total_0" class="pl5">${activity.representNum}</span>人
                    </li>
                    <li class="" id="funding">众筹<span id="total_1" class="pl5">${crowdfundNum}</span>人
                    </li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item  layui-show">
                        <form class="layui-form" action="${ctx}/activity/activity/zcRepresentList.do?id=${activity.id}" id="myForm" method="post">
                            <input type="hidden" name="pageNo" id="pageNo" />
                            <div class="f-search-bar">
                                <div class="search-container">
                                    <ul class="search-form-content">
                                        <li class="form-item-inline"><label class="search-form-lable">姓名</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="authorName" autocomplete="off" class="layui-input" value="${withCount.authorName}" placeholder="姓名">
                                            </div></li>
                                        <li class="form-item-inline"><label class="search-form-lable">联系电话</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="authorMobile" autocomplete="off" class="layui-input" value="${withCount.authorMobile}" placeholder="联系电话">
                                            </div>
                                        </li>
                                        <li class="form-item-inline">
                                            <div class="sub-btns">
                                                <a class="layui-btn layui-btn-danger" href="javascript:submitFunction('#myForm')">确定</a>
                                                <a class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </form>
                        <form class="layui-form" action="${ctx}/activity/activity/representListExport.do" id="exportForm" method="post">
                            <input type="hidden" name="id"  value="${activity.id}">
                        </form>
                        <div class="my-act-list-content">
                            <ul class="num">
                                <div class="l">
                                    <li class="f16">代言<span class="red">${page.totalCount}</span>人</li>
                                </div>
                                <div class="l">
                                    <li style="cursor: pointer;" class="r">
                                        <c:if test="${empty zipUrl}">
                                            <a class="layui-btn layui-btn-danger layui-btn-small" id="btnExport">导出EXCEL</a>
                                        </c:if>
                                        <c:if test="${not empty zipUrl}">
                                            <a class="layui-btn layui-btn-danger layui-btn-small" href="/upload/${zipUrl}">点击下载</a>
                                        </c:if>
                                    </li>
                                </div>
                                <p class="cl"></p>
                            </ul>
                            <div class="cl">
                                <table class="layui-table">
                                    <colgroup>
                                        <col width="10%">
                                        <col width="10%">
                                        <col width="10%">
                                        <col width="10%">
                                        <col width="10%">
                                        <col width="10%">
                                        <col width="10%">
                                        <col width="10%">
                                        <col width="10%">
                                        <col width="10%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th>姓名</th>
                                        <th>公司</th>
                                        <th>区域</th>
                                        <th>职务</th>
                                        <th>联系电话</th>
                                        <th>众筹人数</th>
                                        <th>支持人数</th>
                                        <th>支持金额</th>
                                        <th>浏览量</th>
                                        <th>分享量</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="distribution" items="${list}">
                                        <tr>
                                            <td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${distribution.distributorId}','400px','470px')">
                                                <div class="member-cell">
                                                <div class="member-logo" style="background-image: url('${distribution.authorLogo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                                <div class="member-name ellipsis-1"><a class="blue" title="${distribution.authorName}" href="javascript:void(0);" >${distribution.authorName}</a></div>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="dib ellipsis-1" style="width: 70px;" title="${distribution.authorCompany}">${distribution.authorCompany}</div>
                                            </td>
                                            <td>
                                                    ${distribution.cityName}
                                            </td>
                                            <td>
                                                    ${distribution.authorJobTitle}
                                            </td>
                                            <td>
                                                    ${distribution.authorMobile}
                                            </td>
                                            <td>
                                                <a href="javascript:void(0);" class="blue crowdfundNum count-num" data-id="${distribution.id}" data-name="${distribution.authorName}">${distribution.crowdfundNum}</a>

                                            </td>
                                            <td>
                                                <a href="javascript:void(0);" class="blue favorerNum count-num" data-id="${distribution.id}" data-name="${distribution.authorName}">${distribution.favorerNum}</a>

                                            </td>
                                            <td>
                                                <a href="javascript:void(0);" class="blue actualAmount count-num" data-id="${distribution.id}" data-name="${distribution.authorName}"> ${distribution.actualAmount}</a>

                                            </td>
                                            <td>
                                                    ${distribution.viewNum}
                                            </td>
                                            <td>
                                                    ${distribution.shareNum}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div id="page_content" class="page-container"></div>
                    </div>
                    <div class="layui-tab-item">

                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">

    var laytpl = null;
    var laypage = null;
    var element = null;


    $(function() {

        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');

        layui.use([ 'laytpl', 'laypage', 'element' ], function() {
            laytpl = layui.laytpl;
            laypage = layui.laypage;
            element = layui.element();


            element.on('tab', function(data) {
                if (data.index == 0){
                    window.location.href = "${ctx}/activity/activity/zcRepresentList.do?id=${activity.id}";
                }
                else if (data.index == 1){
                    window.location.href = "${ctx}/activity/activity/zcCrowdfundList.do?id=${activity.id}";
                }
            });
        });
        $('.crowdfundNum').click(function () {
            var relationId = this.dataset.id;
            var name = this.dataset.name;
            window.location.href = '${ctx}/crowdfund/project/listForDistributorId.do?id=${activity.id}&relationId='+relationId +"&name=" + name;
        });


        $('.favorerNum').click(function () {
            var relationId = this.dataset.id;
            var name = this.dataset.name;
            window.location.href = '${ctx}/crowdfund/support/listForDistributorId.do?id=${activity.id}&relationId='+relationId +"&name=" + name;
        });

        $('.actualAmount').click(function () {
            var relationId = this.dataset.id;
            var name = this.dataset.name;
            window.location.href = '${ctx}/crowdfund/support/listForDistributorId.do?id=${activity.id}&relationId='+relationId +"&name=" + name;
        });

        $("#btnExport").click(function () {
            layer.confirm('确认要导出Excel吗?', {
                icon : 3,
                title : '系统提示'
            }, function(index) {
                var index = layer.load(1);
                $.post("${ctx}/activity/activity/representListExport.do", $("#exportForm").serialize(), function(data) {
                    if (data.success == true) {
                        layer.alert("操作成功,请稍后刷新下载", {
                            icon : 6
                        });
                    } else {
                        layer.alert("操作失败", {
                            icon : 6
                        });
                    }
                    layer.close(index);
                    top.layer.close(index);
                });
            });
        });
    })


</script>
</body>
</html>
