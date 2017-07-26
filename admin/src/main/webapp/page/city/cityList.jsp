<%@page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>开放城市管理</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">

    <%@include file="../include/commonFile.jsp" %>
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp" %>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/city/form.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-add btn-icon"></i> 添加开放城市 </a>
                </div>
                <div class="ovh">
                    <span class="title f18"><a href="${ctx}/city/list.do" class="">开放城市管理</a></span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/city/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo"/>
                <input type="hidden" name="id" id="id" value="${city.id }"/>
            </form>
            <div class="list-content">
                <div class="cl">
                    <table class="layui-table">
                        <colgroup>
                            <col>
                            <col>
                            <col width="150px">
                            <col>
                            <col>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>是否开放</th>
                            <th>更新时间</th>
                            <th>排序</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="city" items="${list}">
                            <tr>
                                <td>
                                        ${city.name}
                                </td>
                                <td>
                                        ${city.isOpen == 1?'开放':'不开放'}
                                </td>
                                <td>
                                    <div><fmt:formatDate value="${city.updateDate}" pattern="yyyy-MM-dd HH:mm"/></div>
                                </td>
                                <td>${city.sort}</td>
                                <td class="tb-opts">
                                    <div class="comm-opts">
                                        <a target="_self" class="green" href="${ctx}/city/form.do?id=${city.id}">
                                            编辑
                                        </a>
                                        <a target="_self" class="red" href="javascript:delCircle('${city.id}')">
                                            删除
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${page.totalCount == 0}">
                        <div class="f16 tc mt15">还没有开放城市</div>
                    </c:if>
                </div>
            </div>
            <div id="page_content" class="page-container"></div>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">
    function delCircle(id) {
        layer.confirm('确定删除该开放城市么？', {icon: 3, title: '提示'}, function (index) {
            $.ajax({
                url: "${ctx}/city/del.do",
                type: 'POST',
                dataType: 'json',
                data: {
                    id: id
                },
                success: function (ret) {
                    if (ret.success) {
                        layer.msg('删除成功', {icon: 1, time: 1000}, function (index) {
                            layer.close(index);
                            location.reload();
                        });
                    } else {
                        layer.msg('删除失败', {icon: 2, time: 1000}, function (index) {
                            layer.close(index);
                            location.reload();
                        });
                    }
                }
            });
        })
    }




    $(function () {
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    });

</script>
</body>
</html>