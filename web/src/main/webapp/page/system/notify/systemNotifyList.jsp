<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>系统消息</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <%@include file="../../include/commonFile.jsp" %>
    <style type="text/css">
        .message-logo {
            width: 40px;
            -webkit-filter: grayscale(1);
            filter: grayscale(1);
            padding-right: 10px;
        }

        .isNew .message-logo {
            -webkit-filter: grayscale(0);
            filter: grayscale(0);
        }

        .list-content {
            padding-bottom: 20px;
            padding-top: 20px;
        }

        .list-content ul .list-item {
            border-bottom: 1px solid #f3f3f3;
            padding: 10px;
            padding-top: 20px;
            padding-bottom: 20px;
        }

        .list-content ul .list-item:hover {
            background-color: #f5f5f5
        }

        .list-content ul .list-item .item-text {
            line-height: 40px;
            margin-right: 30px;
        }

        .list-content ul .list-item .item-title {
            width: 550px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        .list-content ul .list-item .item-title:hover {
            overflow: inherit !important;
            text-overflow: inherit !important;
            white-space: inherit !important;
        }

        .list-content ul .list-item .item-date,
        .list-content ul .list-item .item-delete {
            width: 80px;
            text-align: center;
        }

    </style>
</head>
<!--头部-->
<%@include file="../../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../../include/sidebar.jsp" %>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a id="btnClearAll" href="javascript:void(0);" class="layui-btn layui-btn-danger"> <i class="iconfont icon-delete btn-icon"></i> 清空所有消息</a>
                </div>
                <div class="ovh">
                    <span class="title f18">系统消息</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/system/notify/getNotifyList.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" value="${page.page}"/>
            </form>
            <div class="list-content">
                <ul>
                    <c:forEach var="message" items="${messages}">
                        <li class="list-item">
                            <div class="l ${message.isNew == 1 ? 'isNew' : ''}"><img src="${message.logo}" class="message-logo"/></div>
                            <div class="l item-text item-title" title="${message.title}">${message.title}</div>
                            <div class="l item-text item-date">${message.dateDiff}</div>
                            <div class="l item-text item-delete">
                                <a href="javascript:delMessage('${message.id}')"><i class="icon iconfont icon-delete"></i></a>
                            </div>
                            <div class="cl"></div>
                        </li>
                    </c:forEach>
                </ul>
                <c:if test="${page.totalCount == 0}">
                    <div style="height: 40px;line-height: 40px;text-align: center;">
                        没有消息
                    </div>
                </c:if>
            </div>
            <div id="page_content" class="page-container"></div>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">
    // 删除
    function delMessage(id) {
        layer.confirm('确定删除该条消息么？', {icon: 3, title: '提示'}, function (index) {
            $.post('${ctx}/system/notify/deleteNotify.do', {id: id}, function (res) {
                if (res.success) {
                    layer.msg('删除成功', {icon: 1, time: 1000}, function (index) {
                        submitFunction("#myForm");
                    });
                } else {
                    layer.msg('删除失败', {icon: 2, time: 1000});
                }
            });
        })
    }

    showActive('${input.createStart}', '${input.createEnd}', '#timeType');

    $(function () {
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');

        // 全部清空
        $("#btnClearAll").click(function () {
            layer.confirm('确定清空所有消息么？', {icon: 3, title: '提示'}, function (index) {
                $.post('${ctx}/system/notify/deleteAllNotify.do', {}, function (res) {
                    if (res.success) {
                        layer.msg('清空成功', {icon: 1, time: 1000}, function (index) {
                            submitFunction("#myForm");
                        });
                    } else {
                        layer.msg('清空失败', {icon: 2, time: 1000});
                    }
                });
            });
        });
    });
</script>
</body>
</html>