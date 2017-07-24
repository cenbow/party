<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>人员管理</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<style type="text/css">
.layui-table td, .layui-table th {
	padding-left: 5px !important;
	padding-right: 5px !important;
}

.word-break {
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 2;
	word-break: break-all;
}

.layui-form input[type=text]{
	width: 212px!important;
}
</style>
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
          			<c:if test="${not empty project.id && empty group.id}">
						<a href="${ctx}/competition/project/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
					</c:if>
					<c:if test="${not empty group.id}">
						<a href="${ctx}/competition/group/list.do?projectId=${group.projectId}" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
					</c:if>
				</div>
				<div class="ovh">
					<c:if test="${not empty project.id && empty group.id}">
						<span class="title f20" title="${project.title}">赛事项目管理&nbsp;&gt;&nbsp;
							<c:if test="${fn:length(project.title) > 20}">${fn:substring(project.title,0,20)}...</c:if>
							<c:if test="${fn:length(project.title) <= 20}">${project.title}</c:if>
							&nbsp;&gt;&nbsp;人员管理
						</span>
					</c:if>
					<c:if test="${not empty group.id}">
						<span class="title f20" title="${group.groupName}">赛事项目管理&nbsp;&gt;&nbsp;
							<c:if test="${fn:length(project.title) > 10}">${fn:substring(project.title,0,10)}...</c:if>
							<c:if test="${fn:length(project.title) <= 10}">${project.title}</c:if>
							&nbsp;&gt;&nbsp;
							<c:if test="${fn:length(group.groupName) > 10}">${fn:substring(group.groupName,0,10)}...</c:if>
							<c:if test="${fn:length(group.groupName) <= 10}">${group.groupName}</c:if>
							&nbsp;&gt;&nbsp;人员管理
						</span>
					</c:if>
				</div>
			</div>
			<c:if test="${not empty project.id && empty group.id}">
				<form class="layui-form" action="${ctx}/competition/member/list.do?projectId=${project.id}" id="myForm" method="post">
			</c:if>
			<c:if test="${not empty group.id}">
				<form class="layui-form" action="${ctx}/competition/member/list.do?pGroupId=${group.id}" id="myForm" method="post">
			</c:if>
				<input type="hidden" name="pageNo" id="pageNo" value="${page.page}" />
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">姓名</label>
								<div class="layui-input-inline">
									<input type="text" name="realname" class="layui-input" value="${member.realname}" placeholder="请输入查询姓名">
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">号码牌</label>
								<div class="layui-input-inline">
									<input type="text" name="number" class="layui-input" value="${memberInfo.number}" placeholder="请输入查询号码牌">
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">手机</label>
								<div class="layui-input-inline">
									<input type="text" name="mobile" class="layui-input" value="${member.mobile}" placeholder="请输入查询手机">
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item-inline">
								<label class="search-form-lable">性别</label>
								<div class="layui-input-inline">
									<select name="sex">
										<option value="">全部</option>
										<option value="1" ${member.sex == "1" ? 'selected="selected"' : ''}>男</option>
										<option value="0" ${member.sex == "0" ? 'selected="selected"' : ''}>女</option>
									</select>
								</div>
							</li>
							<li class="form-item-inline">
								<label class="search-form-lable">分&emsp;页</label>
								<div class="layui-input-inline">
									<select name="limit">
										<option value="">请选择</option>
										<option value="20" ${input.limit == 20 ? 'selected="selected"' : ''}>20</option>
										<option value="50" ${input.limit == 50 ? 'selected="selected"' : ''}>50</option>
										<option value="100" ${input.limit == 100 ? 'selected="selected"' : ''}>100</option>
									</select>
								</div>
							</li>
							<c:if test="${empty pGroupId}">
								<li class="form-item-inline"><label class="search-form-lable">组名</label>
									<div class="layui-input-inline">
										<select name="groupId">
											<option value="">全部</option>
											<c:forEach var="group" items="${groups}">
												<option value="${group.id}" ${competitionMember.groupId == group.id ? 'selected="selected"' : ''}>${group.groupName}</option>
											</c:forEach>
										</select>
									</div>
								</li>
							</c:if>
							<c:if test="${not empty pGroupId}">
								<li class="form-item-inline">
									<div class="sub-btns" style="width: 235px"></div>
								</li>
							</c:if>
						</ul>
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">创建时间</label>
								<div class="check-btn-inner" id="timeType">
									<a id="all" href="javascript:void(0);" onclick="setTimeType($(this),0,'#myForm')" ${empty input.timeType || input.timeType == 0 ? 'class="active"' : ''}>全部</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),1,'#myForm')" ${input.timeType == 1 ? 'class="active"' : ''}>今天</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),2,'#myForm')" ${input.timeType == 2 ? 'class="active"' : ''}>本周内</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),3,'#myForm')" ${input.timeType == 3 ? 'class="active"' : ''}>本月内</a>
									<input type="hidden" name="timeType" value="${input.timeType}" />
								</div>
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="createStart" value="${input.createStart}" placeholder="开始日" onclick="layui.laydate({elem: this})" style="width: auto!important;">
									</div>
									-
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="createEnd" value="${input.createEnd}" placeholder="截止日" onclick="layui.laydate({elem: this})" style="width: auto!important;">
									</div>
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
				<ul class="num">
					<div class="r">
						<li style="cursor: pointer;" class="r">
							<a class="layui-btn layui-btn-danger layui-btn-small" id="btnExport">导出EXCEL</a>
						</li>
						<li style="cursor: pointer;" class="r">
							<c:if test="${not empty project.id && empty group.id}">
								<a class="layui-btn layui-btn-danger layui-btn-small" href="${ctx}/competition/member/addMember.do?projectId=${project.id}">新增人员</a>
							</c:if>
							<c:if test="${not empty group.id}">
								<a class="layui-btn layui-btn-danger layui-btn-small" href="${ctx}/competition/member/addMember.do?groupId=${group.id}">新增人员</a>
							</c:if>
						</li>
					</div>
					<p class="cl"></p>
				</ul>
				<div class="cl">
					<table class="layui-table">
						<colgroup>
							<col width="90">
							<col width="100">
							<col width="40">
							<col width="90">
							<col width="90">
							<col width="100">
							<col width="70">
							<col width="130">
							<col width="135">
						</colgroup>
						<thead>
							<tr>
								<th>小组名</th>
								<th>用户</th>
								<th>性别</th>
								<th style="text-align: center;">公司</th>
								<th style="text-align: center;">职务</th>
								<th style="text-align: center;">手机</th>
								<th style="text-align: center;">号码牌</th>
								<th>创建时间</th>
								<th style="text-align: center;">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="competitionMember" items="${competitionMembers}">
								<tr>
									<td title="${competitionMember.groupName}"><div class="word-break">${empty competitionMember.groupName ? '未分配' : competitionMember.groupName}</div></td>
									<td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${competitionMember.member.id}','400px','550px')">
										<div class="member-logo" style="background-image: url('${competitionMember.member.logo}'),url(${ctx}/image/def_user_logo.png)"></div>
										<c:set var="fullName" value="${empty competitionMember.member.fullname ? competitionMember.member.realname : competitionMember.member.fullname}"></c:set>
										<div class="member-name ellipsis-1"><a class="blue" title="${fullName}" href="javascript:void(0);" >${fullName}</a></div>
									</td>
									<td style="text-align: center;"><c:if test="${competitionMember.member.sex == 1}">男</c:if><c:if test="${competitionMember.member.sex == 0}">女</c:if></td>
									<td title="${competitionMember.member.company}"><div class="word-break">${competitionMember.member.company}</div></td>
									<td title="${competitionMember.member.jobTitle}"><div class="word-break">${competitionMember.member.jobTitle}</div></td>
									<td>${competitionMember.member.mobile}</td>
									<td>${competitionMember.number}</td>
									<td><fmt:formatDate value="${competitionMember.updateDate}" pattern="yyyy-MM-dd HH:mm"/></td>
									<td class="tb-opts">
										<div class="comm-opts">
											<a href="javascript:openDialogNoButton('新增成绩','${ctx}/competition/result/toForm.do?memberId=${competitionMember.id}&projectId=${competitionMember.projectId}','1050px','500px')">新增成绩</a>
											<a href="javascript:openDialogNoButton('查看成绩','${ctx}/competition/result/resultView.do?memberId=${competitionMember.id}&projectId=${competitionMember.projectId}','900px','500px')">查看成绩</a>
											<a href="javascript:openDialog('分配小组','${ctx}/competition/group/setGroup.do?cMemberId=${competitionMember.id}','450px','350px')">分组</a>
											<c:if test="${not empty project.id && empty group.id}">
												<a href="${ctx}/competition/member/editMember.do?cMemberId=${competitionMember.id}&projectId=${project.id}" class="green">编辑</a>
											</c:if>
											<c:if test="${not empty group.id}">
												<a href="${ctx}/competition/member/editMember.do?cMemberId=${competitionMember.id}&groupId=${group.id}" class="green">编辑</a>
											</c:if>
											<a class="red" href="javascript:deleteObj('确定要删除吗？','${ctx}/competition/member/delete.do?id=${competitionMember.id}&projectId=${project.id}','该参赛人员已有录入成绩，不能删除','#myForm');">删除</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<c:if test="${page.totalCount == 0}">
						<div class="f16 tc mt15">还没有参赛人员</div>
					</c:if>
				</div>
			</div>
		</div>
		<div class="page-content">
			<c:if test="${page.totalCount > 0}">
				<div class="l page-totalcount"><span class="f14 red">共<b id="totalCount">${page.totalCount}</b>条记录</span></div>
			</c:if>
			<div id="page_content" class="page-container"></div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">

    showActive('${input.createStart}', '${input.createEnd}', '#timeType');

	function openDialog(title, url, width, height, target) {
        layer.open({
            type : 2,
            area : [ width, height ],
            title : title,
            maxmin : true, //开启最大化最小化按钮
            content : url,
            btn : [ '确定', '关闭' ],
            yes : function(index, layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var inputForm = body.find('#inputForm');
                var top_iframe;
                if (target) {
                    top_iframe = target;//如果指定了iframe，则在改frame中跳转
                } else {
                    top_iframe = '_parent';//获取当前active的tab的iframe
                }
                inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示

                if (iframeWin.contentWindow.doSubmit()) {
                    setTimeout(function() {
                        top.layer.close(index);
                    }, 100);//延时0.1秒，对应360 7.1版本bug

                    setTimeout(function() {
                        window.location.reload();
                    }, 2000);
                }

            },
            cancel : function(index) {
            }
        });
    }

	$(function() {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');

        $("#btnExport").click(function() {
			layer.confirm('确认要导出Excel吗?', {
				icon : 3,
				title : '系统提示'
			}, function(index) {
				var url = "";
				if ('${project.id}' != "" && '${group.id}' == "") {
					url = "${ctx}/competition/member/exportMember.do?projectId=${project.id}";
				} else if ('${group.id}' != "") {
					url = "${ctx}/competition/member/exportMember.do?groupId=${group.id}";
				}
				var action = $("#myForm").attr("action");
				$("#myForm").attr("action", url);
				$("#myForm").submit();
				$("#myForm").attr("action", action);
				top.layer.close(index);
			});
		});
	})
	
	function deleteMember(){
		
	}
</script>
</body>
</html>