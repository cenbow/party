<%@page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>${group.groupName}的小组成员成绩排行</title>
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
					<a href="${ctx}/competition/project/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
				</div>
                <div class="ovh">
					<span class="title f20" title="${project.title}">我发布的项目&nbsp;&gt;&nbsp;
						<c:if test="${fn:length(project.title) > 20}">${fn:substring(project.title,0,20)}...</c:if>
						<c:if test="${fn:length(project.title) <= 20}">${project.title}</c:if>
						&nbsp;&gt;&nbsp;数据查看
					</span>
				</div>
            </div>
            <div class="layui-tab layui-tab-brief mt20">
            	<ul class="layui-tab-title">
                    <li id="总排行">总排行</li>
                    <li class="layui-this" id="groupRank">小组排行</li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item  layui-show">
			            <form class="layui-form" action="${ctx}/competition/result/groupPersonalRank.do?projectId=${project.id}&groupId=${group.id}" id="myForm" method="post">
			                <input type="hidden" name="pageNo" id="pageNo"/>
			                <div class="f-search-bar">
			                    <div class="search-container">
			                        <ul class="search-form-content">
			                        	<li class="form-item-inline"><label class="search-form-lable">赛程</label>
											<div class="layui-input-inline">
												<select name="scheduleId">
													<option value="">总赛程</option>
													<c:forEach var="schedule" items="${schedules}">
														<option value="${schedule.id}" ${rankInput.scheduleId == schedule.id ? 'selected="selected"' : ''}><fmt:formatDate value="${schedule.playDay}" pattern="yyyy年MM月dd日"/></option>
													</c:forEach>
												</select>
											</div>
										</li>
										<li class="form-item-inline"><label class="search-form-lable">姓名</label>
			                                <div class="layui-input-inline">
			                                    <input type="text" name="fullName" autocomplete="off" class="layui-input" value="${rankInput.fullName}" placeholder="请输入查询姓名" style="width: 212px">
			                                </div>
			                            </li>
			                            <li class="form-item-inline"><label class="search-form-lable">号码牌</label>
			                                <div class="layui-input-inline">
			                                    <input type="text" name="numberPai" autocomplete="off" class="layui-input" value="${rankInput.numberPai}" placeholder="请输入查询号码牌" style="width: 212px">
			                                </div>
			                            </li>
			                        </ul>
			                        <ul class="search-form-content">
			                        	<li class="form-item-inline">
			                                <div class="sub-btns">
			                                    <a class="layui-btn layui-btn-danger" href="javascript:submitFunction('#myForm')">确定</a>
												<a class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
			                                </div>
			                            </li>
			                            <li class="form-item-inline" style="width: 176px"></li>
			                        </ul>
			                    </div>
			                </div>
			            </form>
			            <div class="my-act-list-content">
			            	<ul class="num">
                                <div class="l">
                                    <li class="f16">【${group.groupName}】的小组成员成绩排行</li>
                                </div>
                                <div class="l">
                                    <li style="cursor: pointer;" class="r">
                                        <a class="layui-btn layui-btn-danger layui-btn-small" id="btnExport">导出EXCEL</a>
                                    </li>
                                </div>
                                <p class="cl"></p>
                            </ul>
			                <div class="cl">
			                    <table class="layui-table">
			                        <colgroup>
										<col>
										<col>
										<col>
										<col>
										<col>
										<col>
										<col>
										<col>
			                        </colgroup>
			                        <thead>
				                        <tr>
				                        	<th>排名</th>
				                            <th>用户</th>
				                            <th>姓名</th>
				                            <th>比赛成绩</th>
				                            <th>总里程km</th>
				                            <th>号码牌</th>
				                            <th>平均速度</th>
				                            <th>状态</th>
				                        </tr>
			                        </thead>
			                        <tbody>
				                        <c:forEach var="memberResult" items="${memberResults}" varStatus="status">
				                            <tr>
				                            	<td><fmt:formatNumber value="${memberResult.rowno}" maxFractionDigits="0"></fmt:formatNumber></td>
				                            	<td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${memberResult.member.id}','400px','470px')">
													<div class="member-cell">
													<div class="member-logo" style="background-image: url('${memberResult.member.logo}'),url(${ctx}/image/def_user_logo.png)"></div>
													<div class="member-name ellipsis-1"><a class="blue" title="${memberResult.member.realname}" href="javascript:void(0);" >${memberResult.member.realname}</a></div>
													</div>
												</td>
				                            	<td>${memberResult.fullName}</td>
				                            	<td>${empty memberResult.result ? '00:00:00' : memberResult.result}</td>
				                            	<td>${empty memberResult.distance ? '0' : memberResult.distance}</td>
				                            	<td>${memberResult.numberPai}</td>
				                            	<td>${memberResult.paceMinutes}'${memberResult.paceSeconds}"</td>
				                            	<td>${memberResult.isComplete == 1 ? '完赛' : '未完赛'}</td>
				                            </tr>
				                        </c:forEach>
			                        </tbody>
			                    </table>
			                    <c:if test="${page.totalCount == 0}">
									<div class="f16 tc mt15">还没有参赛人员</div>
								</c:if>
			                </div>
			            </div>
            			<div class="page-content">
							<c:if test="${page.totalCount > 0}">
								<div class="l page-totalcount"><span class="f14 red">共<b id="totalCount">${page.totalCount}</b>条记录</span></div>
							</c:if>
							<div id="page_content" class="page-container"></div>
						</div>
            		 </div>
            		 <div class="layui-tab-item"></div>
           		</div>
           </div>
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

        layui.use([ 'laytpl', 'laypage', 'element' ], function() {
            laytpl = layui.laytpl;
            laypage = layui.laypage;
            element = layui.element();


            element.on('tab', function(data) {
                if (data.index == 0){
                    window.location.href = "${ctx}/competition/result/personalRank.do?projectId=${project.id}";
                }
                else if (data.index == 1){
                    window.location.href = "${ctx}/competition/result/groupRank.do?projectId=${project.id}";
                }
            });
        });
    

	
	$("#btnExport").click(function() {
		layer.confirm('确认要导出Excel吗?', {
			icon : 3,
			title : '系统提示'
		}, function(index) {
			var url = "${ctx}/competition/result/exportGroupMemberResult.do?projectId=${project.id}&groupId=${group.id}";
			var action = $("#myForm").attr("action");
			$("#myForm").attr("action", url);
			$("#myForm").submit();
			$("#myForm").attr("action", action);
			top.layer.close(index);
		});
	});
})
</script>
</body>
</html>