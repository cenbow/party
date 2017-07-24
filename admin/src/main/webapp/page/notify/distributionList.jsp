<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>消息管理</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/channel/channel_list.css">
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
                    <a href="${ctx}/notify/event/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">通道配置</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/notify/channel/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" />
            </form>
            <div class="list-content">
                <div class="cl">
                    <table class="layui-table">
                        <colgroup>
                            <col width="100">
                            <col width="100">
                            <col width="80">
                            <col width="100">
                            <col width="100">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>代码</th>
                            <th>开关</th>
                            <th>写入开关</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="channel" items="${list}">
                            <tr>
                                <td class="">
                                    <div class="dib ellipsis-1"  title="${channel.name}">${channel.name}</div>
                                </td>

                                <td>
                                        ${channel.code}
                                </td>
                                <td>
                                    <c:if test="${channel.channelSwitch == 0}">
                                        关
                                    </c:if>
                                    <c:if test="${channel.channelSwitch == 1}">
                                        开
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${channel.writeSwitch == 0}">
                                        关
                                    </c:if>
                                    <c:if test="${channel.writeSwitch == 1}">
                                        开
                                    </c:if>
                                </td>

                                <td  class="tb-opts">
                                    <div class="comm-opts">
                                        <a href="${ctx}/notify/eventChannel/view.do?id=${channel.id}" target="_self">
                                            编辑
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

</script>
</body>
</html>