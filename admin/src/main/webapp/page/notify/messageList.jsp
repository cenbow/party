<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>消息管理</title>
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
                <div class="ovh">
                    <span class="title f20">短信列表</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/notify/message/listAll.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" />
                <input type="hidden" name="targetId" id="targetId" value="${instanceWithMember.targetId}"/>
                <div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">发送者</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="memberName" autocomplete="off" class="layui-input" value="${instanceWithMember.memberName}" placeholder="发送者">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">手机号</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="receiver" autocomplete="off" class="layui-input" value="${instanceWithMember.receiver}" placeholder="手机号">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">发送结果</label>
                                <div class="layui-input-inline">
                                    <select name="isSuccess">
                                        <option value="" >全部</option>
                                        <option value="1" ${instanceWithMember.isSuccess == 1? 'selected="selected"' : ''}>成功</option>
                                        <option value="0" ${instanceWithMember.isSuccess == 0 ? 'selected="selected"' : ''}>失败</option>
                                    </select>
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
            <div class="my-act-list-content">
                <div class="cl">
                    <table class="layui-table">
                        <colgroup>
                            <col width="150px">
                            <col width="130px">
                            <col>
                            <col width="90px">
                            <col width="90px">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>发送者</th>
                            <th>手机号</th>
                            <th>内容</th>
                            <th>是否成功</th>
                            <th>状态码</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="message" items="${list}">
                            <tr>
                                <td>
                                        ${message.memberName}
                                </td>
                                <td class="">
                                    <div class="dib ellipsis-1"  title="${message.receiver}">${message.receiver}</div>
                                </td>
                                <td>
                                        ${message.title}
                                </td>
                                <td>
                                    <c:if test="${message.isSuccess ==0}"> 失败</c:if>
                                    <c:if test="${message.isSuccess ==1}"> 成功</c:if>
                                </td>
                                <td>
                                        ${message.result}
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