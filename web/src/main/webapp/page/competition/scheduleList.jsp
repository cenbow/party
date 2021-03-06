<%@page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>赛事日程管理</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <%@include file="../include/commonFile.jsp" %>
    <style type="text/css">
        .layui-table td, .layui-table th{
        	padding-left: 12px!important;
        	padding-right: 12px!important;
        }
    </style>
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
                    <a href="${ctx}/competition/project/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
                </div>
                <div class="ovh">
                	<span class="title f20" title="${project.title}">我发布的赛事项目&nbsp;&gt;&nbsp;
						<c:if test="${fn:length(project.title) > 20}">${fn:substring(project.title,0,20)}...</c:if>
						<c:if test="${fn:length(project.title) <= 20}">${project.title}</c:if>
						&nbsp;&gt;&nbsp;赛事日程管理
					</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/competition/schedule/list.do?projectId=${project.id}" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo"/>
                <div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">发布时间</label>
								<div class="check-btn-inner" id="timeType">
									<a id="all" href="javascript:void(0);" onclick="setTimeType($(this),0,'#myForm')" ${empty input.timeType || input.timeType == 0 ? 'class="active"' : ''}>全部</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),1,'#myForm')" ${input.timeType == 1 ? 'class="active"' : ''}>今天</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),2,'#myForm')" ${input.timeType == 2 ? 'class="active"' : ''}>本周内</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),3,'#myForm')" ${input.timeType == 3 ? 'class="active"' : ''}>本月内</a>
									<input type="hidden" name="timeType" value="${input.timeType}" />
								</div>
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="createStart" value="${input.createStart}" placeholder="开始日" onclick="layui.laydate({elem: this})">
									</div>
									-
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="createEnd" value="${input.createEnd}" placeholder="截止日" onclick="layui.laydate({elem: this})">
									</div>
								</div>
							</li>
						</ul>
                    </div>
                </div>
            </form>
            <div class="my-act-list-content">
            	<ul class="num">
					<div class="r">
						<li style="cursor: pointer;" class="r">
							<a class="layui-btn layui-btn-danger layui-btn-small" href="${ctx}/competition/schedule/toForm.do?projectId=${project.id}">创建赛事日程</a>
						</li>
					</div>
					<p class="cl"></p>
				</ul>
                <div class="cl">
                    <table class="layui-table">
                        <colgroup>
                            <col width="150">
                            <col width="150">
                            <col width="90">
                            <col width="150">
                            <col width="150">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>赛日</th>
                            <th>赛事地点</th>
                            <th>距离</th>
                            <th>发布时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="schedule" items="${scheduleList}">
                            <tr class="detail-content1">
                                <td><div><fmt:formatDate value="${schedule.playDay}" pattern="yyyy-MM-dd"/></div></td>
                                <td><div>${schedule.place}</div></td>
                                <td><div>${schedule.distance}KM</div></td>
                                <td><div><fmt:formatDate value="${schedule.updateDate}" pattern="yyyy-MM-dd HH:mm"/></div></td>
                                <td class="opts-btns tb-opts" style="width:250px">
                                    <div class="comm-opts">
                                        <a class="green" href="${ctx}/competition/schedule/toForm.do?id=${schedule.id}&projectId=${project.id}">编辑</a>
                                        <a class="red" href="javascript:deleteObj('确定要删除吗？','${ctx}/competition/schedule/delete.do?id=${schedule.id}','该赛事日程下已有人员成绩，不能删除','#myForm');">删除</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${page.totalCount == 0}">
						<div class="f16 tc mt15">还没有赛事日程</div>
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
   
	showActive('${input.createStart}', '${input.createEnd}', '#timeType');
	$(function () {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');

    })

</script>
</body>
</html>