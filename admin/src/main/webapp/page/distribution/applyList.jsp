<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>报名管理</title>
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
                    <span class="title f20">报名管理</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/distribution/apply/list/${distributionId}.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" />
                <div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="activityTitle" autocomplete="off" class="layui-input w212" value="${memberAct.activityTitle}">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">状态</label>
                                <div class="layui-input-inline">
                                    <select name="checkStatus">
                                        <option value=" ">全部</option>
                                        <c:forEach items="${actStatus}" var="status">
                                            <option value="${status.key}"  <c:if test="${status.key == memberAct.checkStatus}">selected</c:if>>${status.value}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </li>
                            <li  class="form-item-inline">
                                <div class="sub-btns">
                                    <a class="layui-btn layui-btn-danger" id="submitBtn">确定</a> <a class="layui-btn layui-btn-normal" id="resetBtn">重置</a>
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
                            <th>报名者</th>
                            <th>金额</th>
                            <th>手机</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="apply" items="${list}">
                            <tr>
                                <td class="member-info" >
                                    <span class="member-name">${apply.activityName}</span>
                                </td>
                                <td>
                                    <span >${apply.applicant}</span>
                                </td>
                                <td>
                                        ${apply.payment}
                                </td>
                                <td>
                                        ${apply.mobile}
                                </td>
                                <td>
                                    <c:forEach items="${actStatus}" var="status" >
                                        <c:if test="${status.key == apply.checkStatus}">
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
<script type="text/javascript">



    $(function() {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
        $("#submitBtn").click(function() {
            $("#myForm").submit();
        });
        $("#resetBtn").click(function() {
			$("#myForm input[type=text]").val("");
			$("#myForm select").find("option:eq(0)").attr("selected",true);
			$(".check-btn-inner").find("a").removeClass("active");
			$(".check-btn-inner").find("a:eq(0)").addClass("active")
			$(".check-btn-inner [name=timeType]").val("");
		});

    });


</script>
</body>
</html>