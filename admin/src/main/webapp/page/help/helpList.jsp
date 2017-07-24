<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>帮助教程管理</title>
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
                    <a href="${ctx}/help/help/toForm.do" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-add btn-icon"></i> 新增帮助教程
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">帮助教程管理</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/help/help/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" value="${page.page}"/>
                <%--<div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
                            <li class="form-item-inline">
                                <label class="search-form-lable">序号</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="serialNumber" autocomplete="off" class="layui-input" value="${help.serialNumber}" placeholder="请输入查询序号">
                                </div>
                            </li>
                            <li class="form-item-inline">
                                <label class="search-form-lable">标题</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="title" autocomplete="off" class="layui-input" value="${help.title}" placeholder="请输入查询标题">
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
                </div>--%>
            </form>
            <div class="my-act-list-content">
                <ul id="view">
                    <table class="layui-table">
                        <colgroup>
                            <col>
                            <col width="150">
                            <col width="250">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>标题</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="help" items="${helpList}">
                            <c:set var="cHelp" value="${help}" scope="request"/>
                            <jsp:include page="treeInclude.jsp"></jsp:include>
                        </c:forEach>
                        </tbody>
                    </table>
                </ul>
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
</script>
</body>
</html>