<%@page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>话题类型管理</title>
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
                    <a href="${ctx}/circle/topicTag/form.do?circleId=${circle.id}" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-add btn-icon"></i> 创建话题类型
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f18">话题类型管理</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/circle/topicTag/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo"/>
                <input type="hidden" name="circle" id="circle" value="${circle.id}"/>
                <div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="name" autocomplete="off" class="layui-input" value="${tag.name}" placeholder="请输入查询类型名称">
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
            <div class="list-content">
                <div class="cl">
                    <table class="layui-table">
                        <colgroup>
                            <col>
                            <col>
                            <col>
                            <col>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>排序</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="tag" items="${list}">
                            <tr>
                                <td>${tag.name}</td>
                                <td>${tag.sort}</td>
                                <td><fmt:formatDate value="${tag.createDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td class="tb-opts">
                                    <div class="comm-opts">
                                        <a target="_self" class="green" href="${ctx}/circle/topicTag/form.do?circleId=${circle.id}&id=${tag.id}">
                                            编辑
                                        </a>
                                        <a target="_self" class="red" href="javascript:delType('${tag.id}')">
                                            删除
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
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">
    $(function () {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })

    function delType(id) {
        layer.confirm('确定删除该类型么？', {icon: 3, title: '提示'}, function (index) {
            $.post('${ctx}/circle/topicTag/del.do', {id: id}, function (res) {
                if (res.success) {
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
            });
        })
    }
</script>
</body>
</html>