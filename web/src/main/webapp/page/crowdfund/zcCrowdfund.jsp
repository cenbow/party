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
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/crowdfund/event/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">我发布的众筹事项 &gt; 众筹项目</span>
                </div>
            </div>
            <div class="layui-tab layui-tab-brief mt20" lay-filter="type">
                <ul class="layui-tab-title" >
                    <li class="" id="representing">代言<span id="total_0" class="pl5">${representNum}</span>人
                    </li>
                    <li class="layui-this" id="funding">众筹<span id="total_1" class="pl5">${projectNum}</span>人
                    </li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item " >

                    </div>
                    <div class="layui-tab-item layui-show" >
                        <div class="layui-tab layui-tab-brief" lay-filter="zc">
                            <ul class="layui-tab-title" id="zc_tab">
                                <li <c:if test="${empty projectWithAuthor.isSuccess}">class="layui-this"</c:if>>全部</li>
                                <li <c:if test="${projectWithAuthor.isSuccess == 0}">class="layui-this"</c:if>>众筹中</li>
                                <li <c:if test="${projectWithAuthor.isSuccess == 1}">class="layui-this"</c:if>>众筹成功</li>
                                <li <c:if test="${projectWithAuthor.isSuccess == 3}">class="layui-this"</c:if>>退款中</li>
                                <li <c:if test="${projectWithAuthor.isSuccess == 4}">class="layui-this"</c:if>>退款成功</li>
                            </ul>
                            <div class="layui-tab-content">
                                <form class="layui-form" action="${ctx}/crowdfund/project/listForEvent.do?eventId=${projectWithAuthor.eventId}" id="myForm" method="post">
                                    <input type="hidden" name="isSuccess" id="isSuccess" value="${projectWithAuthor.isSuccess}"/>
                                    <input type="hidden" name="pageNo" id="pageNo" />
                                    <div class="f-search-bar">
                                        <div class="search-container">
                                            <ul class="search-form-content">
                                                <li class="form-item-inline"><label class="search-form-lable">众筹者：</label>
                                                    <div class="layui-input-inline">
                                                        <input type="text" name="authorName" autocomplete="off" class="layui-input" value="${projectWithAuthor.authorName}"
                                                               placeholder="众筹者"
                                                        >
                                                    </div></li>
                                                <li class="form-item-inline"><label class="search-form-lable">联系电话：</label>
                                                    <div class="layui-input-inline">
                                                        <input type="text" name="authorMobile" autocomplete="off" class="layui-input" value="${projectWithAuthor.authorMobile}"
                                                               placeholder="联系电话"
                                                        >
                                                    </div>
                                                </li>
                                                <li class="form-item-inline"><label class="search-form-lable">排序方式</label>
                                                    <div class="layui-input-inline">
                                                        <select name="sort">
                                                            <option value="0" ${projectWithAuthor.sort == 0 ? 'selected="selected"' : ''}>金额最多</option>
                                                            <option value="1" ${projectWithAuthor.sort == 1 ? 'selected="selected"' : ''}>时间最近</option>
                                                        </select>
                                                    </div>
                                                </li>
                                            </ul>
                                            <ul class="search-form-content">
                                                <li class="form-item-inline"><label class="search-form-lable">筛选金额</label>
                                                    <div class="layui-input-inline">
                                                        <select name="operator">

                                                            <option value="0" <c:if test="${projectWithAuthor.operator == 0}">selected</c:if>>等于</option>
                                                            <option value="1" <c:if test="${projectWithAuthor.operator == 1}">selected</c:if>>小于</option>
                                                            <option value="2" <c:if test="${projectWithAuthor.operator == 2}">selected</c:if>>大于</option>
                                                        </select>
                                                    </div>
                                                    <div class="layui-input-inline">
                                                        <input type="text" name="operatorNum" autocomplete="off" class="layui-input" value="${projectWithAuthor.operatorNum}" placeholder="金额">
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
                                <form class="layui-form" action="${ctx}/activity/activity/crowdfundListExport.do" id="exportForm" method="post">
                                    <input type="hidden" id="id" name="eventId"  value="${projectWithAuthor.eventId}">
                                </form>
                                <div class="my-act-list-content">
                                    <ul class="num">
                                        <div class="l">
                                            <li class="f16">众筹人数<span class="red">${page.totalCount}</span>人</li>
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
                                                <th>众筹者</th>
                                                <th>公司</th>
                                                <th>区域</th>
                                                <th>职务</th>
                                                <th>联系电话</th>
                                                <th>支持人数</th>
                                                <th>支持金额</th>
                                                <th>浏览量</th>
                                                <th>状态</th>
                                                <th>操作</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="distribution" items="${list}">
                                                <tr>
                                                    <td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${distribution.authorId}','400px','470px')">
                                                        <div class="member-logo" style="background-image: url('${distribution.authorLogo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                                        <div class="member-name ellipsis-1"><a class="blue" title="${distribution.authorName}" href="javascript:void(0);" >${distribution.authorName}</a></div>
                                                    </td>
                                                    <td>
                                                        <div class="dib ellipsis-1" style="width: 100px;" title="${distribution.authorCompany}">${distribution.authorCompany}</div>
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
                                                        <a href="javascript:void(0);" class="blue favorerNum count-num" data-id="${distribution.id}" data-name="${distribution.authorName}">${distribution.favorerNum}</a>

                                                    </td>
                                                    <td>
                                                        <a href="javascript:void(0);" class="blue actualAmount count-num" data-id="${distribution.id}" data-name="${distribution.authorName}"> ${distribution.actualAmount}</a>

                                                    </td>
                                                    <td>
                                                            ${distribution.viewNum}
                                                    </td>
                                                    <td>
                                                        <c:if test="${distribution.isSuccess == 0}">
                                                            <span>众筹中</span>
                                                        </c:if>
                                                        <c:if test="${distribution.isSuccess == 1}">
                                                            <span>众筹成功</span>
                                                        </c:if>
                                                        <c:if test="${distribution.isSuccess == 2}">
                                                            <span>众筹失败</span>
                                                        </c:if>
                                                        <c:if test="${distribution.isSuccess == 3}">
                                                            <span>退款中</span>
                                                        </c:if>
                                                        <c:if test="${distribution.isSuccess == 4}">
                                                            <span>退款成功</span>
                                                        </c:if>
                                                    </td>
                                                    <td  class="tb-opts">
                                                        <div class="comm-opts">
                                                            <a href="javascript:openDialogShow('数据查看','${ctx}/crowdfund/support/chartView.do?projectId=${distribution.id}&eventId=${projectWithAuthor.eventId}','1050px','500px')" target="_self">
                                                                数据查看
                                                            </a>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div id="page_content" class="page-container"></div>
                             </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
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
                    window.location.href = "${ctx}/crowdfund/represen/listForEvent.do?eventId=${projectWithAuthor.eventId}";
                }
                else if (data.index == 1){
                    window.location.href = "${ctx}/crowdfund/project/listForEvent.do?eventId=${projectWithAuthor.eventId}";
                }
            });

            element.on('tab(zc)', function(data) {
                if (data.index == 0){
                    $('#isSuccess').val('');
                    $('#myForm').submit();
                }
                else if (data.index == 1){
                    $('#isSuccess').val('0');
                    $('#myForm').submit();
                }
                else if (data.index == 2){
                    $('#isSuccess').val('1');
                    $('#myForm').submit();
                }
                else if (data.index == 3){
                    $('#isSuccess').val('3');
                    $('#myForm').submit();
                }
                else if (data.index == 4){
                    $('#isSuccess').val('4');
                    $('#myForm').submit();
                }
            });
        });

        $('.favorerNum').click(function () {
            var projectId = this.dataset.id;
            var name = this.dataset.name;
            window.location.href = '${ctx}/crowdfund/support/event/listForProjectId.do?eventId=${projectWithAuthor.eventId}&projectId='+projectId;
        });

        $('.actualAmount').click(function () {
            var projectId = this.dataset.id;
            var name = this.dataset.name;
            window.location.href = '${ctx}/crowdfund/support/event/listForProjectId.do?eventId=${projectWithAuthor.eventId}&projectId='+projectId;
        });

        $("#btnExport").click(function () {
            layer.confirm('确认要导出Excel吗?', {
                icon : 3,
                title : '系统提示'
            }, function(index) {

                $('#myForm').attr('action', '${ctx}/crowdfund/project/listForEventExport.do?eventId=${projectWithAuthor.eventId}');
                $('#myForm').submit();
                $('#myForm').attr('action', '${ctx}/crowdfund/project/listForEvent.do?eventId=${projectWithAuthor.eventId}');
                top.layer.close(index);
            });
        });
    })
</script>
</body>
</html>
