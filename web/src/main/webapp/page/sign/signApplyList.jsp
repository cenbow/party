<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>成员管理</title>
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
                    <c:if test="${not empty show}">
                        <a href="${ctx}/sign/project/list.do" class="layui-btn layui-btn-radius layui-btn-danger">
                            <i class="iconfont icon-refresh btn-icon"></i>返回
                        </a>
                    </c:if>
                    <c:if test="${empty show}">
                        <a href="${ctx}/sign/group/list.do?projectId=${myGroup.projectId}" class="layui-btn layui-btn-radius layui-btn-danger">
                            <i class="iconfont icon-refresh btn-icon"></i>返回
                        </a>
                    </c:if>

                </div>
                <div class="ovh">
                    <span class="title f20">打卡管理
                        <c:if test="${empty show}">
                            &nbsp;&gt;&nbsp;${myGroup.name}
                        </c:if>
                        &nbsp;&gt;&nbsp;成员管理</span>
                </div>
            </div>
            <div class="layui-tab layui-tab-brief" lay-filter="zc">
                <ul class="layui-tab-title" id="zc_tab">
                    <li <c:if test="${empty groupMember.status}">class="layui-this"</c:if>>全部</li>
                    <li <c:if test="${groupMember.status == 0}">class="layui-this"</c:if>>待审核</li>
                    <li <c:if test="${groupMember.status == 1}">class="layui-this"</c:if>>审核通过</li>
                    <li <c:if test="${groupMember.status == 2}">class="layui-this"</c:if>>审核拒绝</li>
                </ul>
                <div class="layui-tab-content">
                    <form class="layui-form" action="${ctx}/sign/member/list.do" id="myForm" method="post">
                        <input type="hidden" name="pageNo" id="pageNo" />
                        <input type="hidden" name="status" id="status" value="${groupMember.status}"/>
                        <div class="f-search-bar">
                            <div class="search-container">
                                <ul class="search-form-content">
                                    <li class="form-item-inline"><label class="search-form-lable">成员名</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="memberName" autocomplete="off" class="layui-input" value="${groupMember.memberName}"
                                                   placeholder="请输入查询成员名"
                                            >
                                        </div>
                                    </li>
                                    <c:if test="${show}">
                                        <li class="form-item-inline"><label class="search-form-lable">所属队伍</label>
                                            <div class="layui-input-inline">
                                                <select name="groupId" >
                                                    <option value="">全部</option>
                                                    <c:forEach var="group" items="${signGroupList}">
                                                        <option value="${group.id}" ${groupMember.groupId == group.id ? 'selected="selected"' : ""}>${group.name}</option>
                                                    </c:forEach>

                                                </select>
                                            </div>
                                        </li>
                                    </c:if>

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

                    <div class="my-act-list-content">
                        <div class="cl">
                            <table class="layui-table">
                                <colgroup>
                                    <col>
                                    <col>
                                    <col>
                                    <col width="130px">
                                    <col>
                                    <col width="150px">
                                    <col>
                                    <col>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>成员名</th>
                                    <th>公司</th>
                                    <th>职务</th>
                                    <th>联系电话</th>
                                    <th>所属队伍</th>
                                    <th>申请时间</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="apply" items="${list}">
                                    <tr>
                                        <td class="table-member" onclick="openDialogShow('成员名片','${ctx}/system/member/memberView.do?id=${apply.memberId}','400px','470px')">
                                            <div class="member-cell">
                                            <div class="member-logo" style="background-image: url('${apply.memberLogo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                            <div class="member-name ellipsis-1"><a class="blue" title="${apply.memberName}" href="javascript:void(0);" >${apply.memberName}</a></div>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="dib ellipsis-1" style="width: 100px;" title="${apply.memberCompany}">${apply.memberCompany}</div>
                                        </td>
                                        <td>
                                                ${apply.memberJobTitle}
                                        </td>
                                        <td>
                                                ${apply.memberMobile}
                                        </td>
                                        <td>
                                                ${apply.groupName}

                                        </td>
                                        <td>
                                            <fmt:formatDate value="${apply.createDate}" pattern="yyyy-MM-dd HH:mm" />

                                        </td>
                                        <td>
                                            <c:if test="${apply.status == 0}">
                                                待审核
                                            </c:if>
                                            <c:if test="${apply.status == 1}">
                                                审核通过
                                            </c:if>
                                            <c:if test="${apply.status == 2}">
                                                审核拒绝
                                            </c:if>
                                        </td>
                                        <td  class="tb-opts">
                                            <div class="comm-opts">
                                                <c:if test="${apply.status == 0}">
                                                    <a href="javascript:checkApply('${apply.id}', 1)" target="_self">
                                                        通过
                                                    </a>
                                                    <a href="javascript:checkApply('${apply.id}', 2)" target="_self">
                                                        拒绝
                                                    </a>
                                                </c:if>
                                                <c:if test="${apply.status == 1}">
                                                    <a href="javascript:deleteApply('${apply.id}')" target="_self">
                                                        移除成员
                                                    </a>
                                                    <a href="javascript:openDialog('分配通道','${ctx}/sign/member/distribute.do?id=${apply.id}','400px','300px')" target="_self">
                                                        移动队伍
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
    </section>
</div>
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>

<script type="text/javascript">
    var laytpl = null;
    var laypage = null;
    var element = null;

    var url = window.location.href;
    $("#myForm").attr("action", url);
    $(function () {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');

        layui.use([ 'laytpl', 'laypage', 'element' ], function() {
            laytpl = layui.laytpl;
            laypage = layui.laypage;
            element = layui.element();

            element.on('tab(zc)', function(data) {
                if (data.index == 0){
                    $('#status').val('');
                    $('#myForm').submit();
                }
                else if (data.index == 1){
                    $('#status').val('0');
                    $('#myForm').submit();
                }
                else if (data.index == 2){
                    $('#status').val('1');
                    $('#myForm').submit();
                }
                else if (data.index == 3){
                    $('#status').val('2');
                    $('#myForm').submit();
                }
            });

        });
    })



    function checkApply(id, status) {
        var info;
        if (status == 1){
            info = "确定要通过吗?";
        }
        else if (status == 2){
            info = "确定要拒绝吗?"
        }
        layer.confirm(info, {
            icon : 3,
            title : '系统提示'
        }, function(index) {
            $.post("${ctx}/sign/member/check.do", {id : id,status: status}, function(data) {
                if (data.success == true) {
                    layer.alert("审核成功", {
                        icon : 6
                    }, function(index) {
                        window.location.reload();
                    });
                } else {
                    layer.alert("审核失败", {
                        icon : 6
                    });
                }
            });
        });
    };

    function deleteApply(id) {
        layer.confirm('确定移除成员？', {
            icon : 3,
            title : '系统提示'
        }, function(index) {
            $.post("${ctx}/sign/member/delete.do", {
                id : id
            }, function(data) {
                if (data.success == true) {
                    layer.alert("移除成功", {
                        icon : 6
                    }, function(index) {
                        window.location.reload();
                    });
                } else {
                    layer.alert("移除失败", {
                        icon : 6
                    });
                }
            });
        });
    };

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
