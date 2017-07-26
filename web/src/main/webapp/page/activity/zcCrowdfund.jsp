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
            <div class="layui-tab layui-tab-brief mt20" lay-filter="type">
                <ul class="layui-tab-title">
                    <li class="" id="representing">代言<span id="total_0" class="pl5">${activity.representNum}</span>人
                    </li>
                    <li class="layui-this" id="funding">众筹<span id="total_1" class="pl5">${crowdfundNum}</span>人
                    </li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item " >

                    </div>
                    <div class="layui-tab-item layui-show">
                        <div class="layui-tab layui-tab-brief" lay-filter="zc">
                            <ul class="layui-tab-title" id="zc_tab">

                                <li <c:if test="${empty projectWithAuthor.isSuccess}">class="layui-this"</c:if>>全部</li>
                                <li <c:if test="${projectWithAuthor.isSuccess == 0}">class="layui-this"</c:if>>众筹中</li>
                                <li <c:if test="${projectWithAuthor.isSuccess == 1}">class="layui-this"</c:if>>众筹成功</li>
                                <li <c:if test="${projectWithAuthor.isSuccess == 3}">class="layui-this"</c:if>>退款中</li>
                                <li <c:if test="${projectWithAuthor.isSuccess == 4}">class="layui-this"</c:if>>退款成功</li>
                            </ul>
                            <div class="layui-tab-content">
                                <form class="layui-form" action="${ctx}/activity/activity/zcCrowdfundList.do?id=${activity.id}" id="myForm" method="post">
                                    <input type="hidden" name="pageNo" id="pageNo" />
                                    <input type="hidden" name="isSuccess" id="isSuccess" value="${projectWithAuthor.isSuccess}"/>
                                    <div class="f-search-bar">
                                        <div class="search-container">
                                            <ul class="search-form-content">
                                                <li class="form-item-inline"><label class="search-form-lable" style="text-indent: 13px;">众筹者</label>
                                                    <div class="layui-input-inline">
                                                        <input type="text" name="authorName" autocomplete="off" class="layui-input" value="${projectWithAuthor.authorName}" placeholder="众筹者">
                                                    </div>
                                                </li>
                                                <li class="form-item-inline"><label class="search-form-lable">联系电话</label>
                                                    <div class="layui-input-inline">
                                                        <input type="text" name="authorMobile" autocomplete="off" class="layui-input" value="${projectWithAuthor.authorMobile}" placeholder="联系电话">
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
                                    <input type="hidden" id="id" name="id"  value="${activity.id}">
                                </form>
                                <div class="my-act-list-content">
                                    <ul class="num">
                                        <div class="l">
                                            <li class="f16">共<span class="red">${page.totalCount}</span>数据</li>
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
                                                <col>
                                                <col>
                                                <col>
                                                <col>
                                                <col width="130px">
                                                <col width="90px">
                                                <col width="90px">
                                                <col width="90px">
                                                <col width="90px">
                                                <col>
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
                                                        <div class="member-cell">
                                                        <div class="member-logo" style="background-image: url('${distribution.authorLogo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                                        <div class="member-name ellipsis-1"><a class="blue" title="${distribution.authorName}" href="javascript:void(0);" >${distribution.authorName}</a></div>
                                                        </div>
                                                    </td>
                                                    <td class="ellipsis-1">
                                                            ${distribution.authorCompany}
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
                                                            <a href="javascript:openDialogShow('数据查看','${ctx}/crowdfund/support/chartView.do?projectId=${distribution.id}&targetId=${activity.id}','1050px','500px')" target="_self">
                                                                数据查看
                                                            </a>
                                                            <a href="javascript:openDialog('转移支持者','${ctx}/crowdfund/project/otherProject.do?projectId=${distribution.id}','550px','500px')" target="_self">
                                                                转移支持者
                                                            </a>
                                                            <a href="javascript:revise('${distribution.id}')" target="_self">
                                                                校正
                                                            </a>
                                                            <c:if test="${distribution.actualAmount <= 0}">
                                                                <a href="javascript:deleteProject('${distribution.id}');" target="_self">
                                                                    删除
                                                                </a>
                                                            </c:if>
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


            element.on('tab(type)', function(data) {
                if (data.index == 0){
                    window.location.href = "${ctx}/activity/activity/zcRepresentList.do?id=${activity.id}";
                }
                else if (data.index == 1){
                    window.location.href = "${ctx}/activity/activity/zcCrowdfundList.do?id=${activity.id}";
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
            window.location.href = '${ctx}/crowdfund/support/listForProjectId.do?id=${activity.id}&projectId='+projectId + "&name=" + name;
        });

        $('.actualAmount').click(function () {
            var projectId = this.dataset.id;
            var name = this.dataset.name;
            window.location.href = '${ctx}/crowdfund/support/listForProjectId.do?id=${activity.id}&projectId='+projectId + "&name=" + name;
        });

        $("#btnExport").click(function () {
            var id = $('#id').val();
            layer.confirm('确认要导出Excel吗?', {
                icon : 3,
                title : '系统提示'
            }, function(index) {
                var index = layer.load(1);
                $.post("${ctx}/activity/activity/crowdfundListExport.do", $("#exportForm").serialize(), function(data) {
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

    function deleteProject(id){
        layer.confirm('确定删除该众筹', {
            icon : 3,
            title : '系统提示'
        }, function(index) {
            $.post("${ctx}/crowdfund/project/delete.do", {
                id : id
            }, function(data) {
                if (data.success == true) {
                    layer.alert("删除成功", {
                        icon : 6
                    }, function(index) {
                        window.location.reload();
                    });
                } else {
                    layer.alert("删除失败", {
                        icon : 6
                    });
                }
            });
        });
    }

    function refund(id){
        layer.confirm('确定要退款', {
            icon : 3,
            title : '系统提示'
        }, function(index) {
            $.post("${ctx}/crowdfund/project/refund.do", {
                id : id
            }, function(data) {
                if (data.success == true) {
                    layer.alert("退款成功", {
                        icon : 6
                    }, function(index) {
                        window.location.reload();
                    });
                } else {
                    layer.alert("退款失败", {
                        icon : 6
                    });
                }
            });
        });
    }

    function revise(id){
        $.post("${ctx}//crowdfund/project/revise.do", {
            id : id
        }, function(data) {
            if (data.success == true) {
                layer.alert("校正成功", {
                    icon : 6
                }, function(index) {
                    window.location.reload();
                });
            } else {
                layer.alert("退款失败", {
                    icon : 6
                });
            }
        });
    }

    function openDialog(title, url, width, height, target) {
        layer.open({
            type : 2,
            area : [ width, height ],
            title : title,
            maxmin : true, //开启最大化最小化按钮
            content : url,
            btn : [ '确定', '关闭' ],
            yes : function(index, layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var inputForm = body.find('#inputForm');
                var top_iframe;
                if (target) {
                    top_iframe = target;//如果指定了iframe，则在改frame中跳转
                } else {
                    top_iframe = '_parent';//获取当前active的tab的iframe
                }
                inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示

                if (iframeWin.contentWindow.doSubmit()) {
                    setTimeout(function() {
                        top.layer.close(index);
                    }, 100);//延时0.1秒，对应360 7.1版本bug

                    setTimeout(function() {
                        window.location.reload();
                    }, 200);
                }

            },
            cancel : function(index) {
            }
        });
    }
</script>
</body>
</html>
