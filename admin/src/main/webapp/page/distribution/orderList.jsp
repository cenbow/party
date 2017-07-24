<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>订单管理</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/member_list.css">
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
                <div class="ovh">
                    <span class="title f20">订单管理</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/distribution/order/list/${distributionId}.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" />
                <div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="title" autocomplete="off" class="layui-input w212" value="${orderForm.title}">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">状态</label>
                                <div class="layui-input-inline">
                                    <select name="status">
                                        <option value=" ">全部</option>
                                        <c:forEach items="${statusMap}" var="status">
                                            <option value="${status.key}"  <c:if test="${status.key == orderForm.status}">selected</c:if>>${status.value}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </li>
                            <li  class="form-item-inline">
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
                            <col width="30%">
                            <col width="20%">
                            <col width="20%">
                            <col width="10%">
                            <col width="10%">
                            <col width="10%">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>买家</th>
                            <th>金额</th>
                            <th>份数</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="order" items="${list}">
                            <tr>
                                <td class="member-info" >
                                    <span class="member-name">${order.title}</span>
                                </td>
                                <td>
                                    <span >${order.buyer}</span>
                                </td>
                                <td>
                                        ${order.payment}
                                </td>
                                <td>
                                        ${order.unit}
                                </td>
                                <td>
                                    <c:forEach items="${statusMap}" var="status" >
                                        <c:if test="${status.key == order.status}">
                                            ${status.value}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>
                                    <a href="#" target="_self">
                                        无
                                    </a>
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
<script type="text/javascript">
    $(function () {
//加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })

</script>
</body>
</html>