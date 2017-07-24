<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>众筹管理</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <%@include file="../include/commonFile.jsp"%>
</head>
<!--头部-->
<%@include file="../include/header.jsp"%>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp"%>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/crowdfund/analyze/list.do?targetId=${targetId}&eventId=${eventId}" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">众筹管理&nbsp;&gt;&nbsp;跟进记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/records/records/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" />
                <input type="hidden" name="targetId" id="targetId" value="${upRecordWithProject.targetId}"/>
                <input type="hidden" name="eventId" id="eventId" value="${eventId}"/>
                <input type="hidden" name="activityId" id="activityId" value="${targetId}"/>
            </form>
            <div class="my-act-list-content">
                <ul class="num">
                    <div class="r">
                        <li style="cursor: pointer;" class="r">

                            <a href="${ctx}/records/records/view.do?projectId=${upRecordWithProject.targetId}&activityId=${targetId}&eventId=${eventId}" class="layui-btn layui-btn-danger layui-btn-small"><i
                                    class="iconfont icon-add btn-icon"></i>新增记录</a>
                        </li>
                    </div>
                    <p class="cl"></p>
                </ul>
                <div class="cl">
                    <table class="layui-table">
                        <colgroup>
                            <col width="100">
                            <col width="100">
                            <col width="150">
                            <col width="100">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>众筹者</th>
                            <th>内容</th>
                            <th>时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="record" items="${recordList}">
                            <tr>
                                <td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${record.authorId}','400px','470px')">
                                    <div class="member-logo" style="background-image: url('${record.authorLogo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                    <div class="member-name ellipsis-1"><a class="blue" title="${record.authorName}" href="javascript:void(0);" >${record.authorName}</a></div>
                                </td>
                                <td>
                                    <div class="dib ellipsis-1" style="width: 100px;"  title="${record.content}">${record.content}</div>
                                </td>
                                <td>
                                    <fmt:formatDate value="${record.createDate}" pattern="yyyy-MM-dd HH:mm" />
                                </td>
                                <td  class="tb-opts">
                                    <div class="comm-opts">
                                        <a href="${ctx}/records/records/view.do?id=${record.id}" target="_self">
                                            编辑
                                        </a>
                                        <a href="javascript:deleteRecord('${record.id}')">
                                            <i class="iconfont icon-delete btn-icon"></i> 删除
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
    </section>
</div>


<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">
    $(function () {
//加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })

    function deleteRecord(id) {
        layer.confirm('确定删除？', {
            icon : 3,
            title : '系统提示'
        }, function(index) {
            $.post("${ctx}/records/records/delete.do", {
                id : id
            }, function(data) {
                if (data.success == true) {
                    layer.alert("删除成功", {
                        icon : 6
                    }, function(index) {
                        window.location.reload();
                    });
                } else {
                    layer.alert(data.description, {
                        icon : 2
                    });
                }
            });
        });
    };
</script>
</body>
</html>