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
                    <li class="" id="funding">众筹<span id="total_1" class="pl5">${activity.beCrowdfundNum + activity.crowdfundedNum}</span>人
                    </li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item  layui-show">
                        <form class="layui-form" action="${ctx}/crowdfund/support/listForDistributorId.do?id=${activity.id}" id="myForm" method="post">
                            <input type="hidden" name="pageNo" id="pageNo" />
                            <input type="hidden" name="name"  value="${name}">
                            <input type="hidden" name="relationId" value="${relationId}">
                        </form>
                        <form class="layui-form" action="${ctx}/crowdfund/support/listForDistributorIdExport.do" id="exportForm" method="post">
                            <input type="hidden" name="relationId"  value="${relationId}">
                            <input type="hidden" name="name"  value="${name}">
                        </form>
                        <div class="my-act-list-content">
                            <ul class="num">
                                <div class="l">
                                    <li class="f16">【${name}】 的代言的支持人数<span class="red">${page.totalCount}</span>人</li>
                                </div>
                                <div class="l">
                                    <li style="cursor: pointer;" class="r">
                                        <a class="layui-btn layui-btn-danger layui-btn-small" id="btnExport">导出EXCEL</a>
                                    </li>
                                </div>
                                <p class="cl"></p>
                            </ul>
                            <div class="cl">
                                <table class="layui-table">
                                    <colgroup>
                                        <col width="8%">
                                        <col width="10%">
                                        <col width="8%">
                                        <col width="8%">
                                        <col width="8%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th>支持者</th>
                                        <th>公司</th>
                                        <th>职务</th>
                                        <th>联系电话</th>
                                        <th>支持金额</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="distribution" items="${list}">
                                        <tr>
                                            <td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${distribution.favorerId}','400px','470px')">
                                                <div class="member-cell">
                                                <div class="member-logo" style="background-image: url('${distribution.favorerLogo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                                <div class="member-name ellipsis-1"><a class="blue" title="${distribution.favorerName}" href="javascript:void(0);" >${distribution.favorerName}</a></div>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="dib ellipsis-1" style="width: 100px;" title="${distribution.favorerCompany}">${distribution.favorerCompany}</div>
                                            </td>
                                            <td>
                                                    ${distribution.favorerJobTitle}
                                            </td>
                                            <td>
                                                    ${distribution.favorerMobile}
                                            </td>
                                            <td>
                                                    ${distribution.payment}
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

        $("#btnExport").click(function () {
            layer.confirm('确认要导出Excel吗?', {
                icon : 3,
                title : '系统提示'
            }, function(index) {
                $("#exportForm").submit();
                top.layer.close(index);
            });
        });
    })
</script>
</body>
</html>
