<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>人员信息管理</title>
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
						<a href="${ctx}/gatherInfo/project/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
					</c:if>
					<c:if test="${not empty group.id}">
						<a href="${ctx}/gatherInfo/group/list.do?projectId=${group.projectId}" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
					</c:if>
				</div>
				<div class="ovh">
					<c:if test="${not empty project.id && empty group.id}">
						<span class="title f20" title="${project.title}">项目管理&nbsp;&gt;&nbsp;
							<c:if test="${fn:length(project.title) > 20}">${fn:substring(project.title,0,20)}...</c:if>
							<c:if test="${fn:length(project.title) <= 20}">${project.title}</c:if>
							&nbsp;&gt;&nbsp;人员管理
						</span>
					</c:if>
					<c:if test="${not empty group.id}">
						<span class="title f20" title="${group.groupName}">项目管理&nbsp;&gt;&nbsp;
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
				<form class="layui-form" action="${ctx}/gatherInfo/member/list.do?projectId=${project.id}" id="myForm" method="post">
			</c:if>
			<c:if test="${not empty group.id}">
				<form class="layui-form" action="${ctx}/gatherInfo/member/list.do?pGroupId=${group.id}" id="myForm" method="post">
			</c:if>
				<input type="hidden" name="pageNo" id="pageNo" value="${page.page}" />
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">姓&emsp;&emsp;名</label>
								<div class="layui-input-inline">
									<input type="text" name="fullname" class="layui-input" value="${member.fullname}" placeholder="请输入查询姓名">
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">手&emsp;&emsp;机</label>
								<div class="layui-input-inline">
									<input type="text" name="mobile" class="layui-input" value="${member.mobile}" placeholder="请输入查询手机">
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">组内职务</label>
								<div class="layui-input-inline">
									<input type="text" name="groupJobTitle" class="layui-input" value="${memberInfo.groupJobTitle}" placeholder="请输入查询组内职务">
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">基本状态</label>
								<div class="layui-input-inline">
									<select name="baseStatus">
										<option value="">全部</option>
										<option value="1" ${memberInfo.baseStatus == '1' ? 'selected="selected"' : ''}>待审核</option>
										<option value="2" ${memberInfo.baseStatus == '2' ? 'selected="selected"' : ''}>已通过</option>
										<option value="3" ${memberInfo.baseStatus == '3' ? 'selected="selected"' : ''}>未通过</option>
									</select>
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">保险状态</label>
								<div class="layui-input-inline">
									<select name="insuranceStatus">
										<option value="">全部</option>
										<option value="1" ${memberInfo.insuranceStatus == '1' ? 'selected="selected"' : ''}>待审核</option>
										<option value="2" ${memberInfo.insuranceStatus == '2' ? 'selected="selected"' : ''}>已通过</option>
										<option value="3" ${memberInfo.insuranceStatus == '3' ? 'selected="selected"' : ''}>未通过</option>
									</select>
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">行程状态</label>
								<div class="layui-input-inline">
									<select name="itineraryStatus">
										<option value="">全部</option>
										<option value="1" ${memberInfo.itineraryStatus == '1' ? 'selected="selected"' : ''}>待审核</option>
										<option value="2" ${memberInfo.itineraryStatus == '2' ? 'selected="selected"' : ''}>已通过</option>
										<option value="3" ${memberInfo.itineraryStatus == '3' ? 'selected="selected"' : ''}>未通过</option>
									</select>
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item-inline">
								<label class="search-form-lable">分&emsp;&emsp;页</label>
								<div class="layui-input-inline">
									<select name="limit">
										<option value="">请选择</option>
										<option value="20" ${input.limit == 20 ? 'selected="selected"' : ''}>20</option>
										<option value="50" ${input.limit == 50 ? 'selected="selected"' : ''}>50</option>
										<option value="100" ${input.limit == 100 ? 'selected="selected"' : ''}>100</option>
									</select>
								</div>
							</li>
							<li class="form-item-inline">
								<label class="search-form-lable">性&emsp;&emsp;别</label>
								<div class="layui-input-inline">
									<select name="sex">
										<option value="">全部</option>
										<option value="1" ${member.sex == "1" ? 'selected="selected"' : ''}>男</option>
										<option value="0" ${member.sex == "0" ? 'selected="selected"' : ''}>女</option>
									</select>
								</div>
							</li>
							<c:if test="${empty pGroupId}">
								<li class="form-item-inline"><label class="search-form-lable">组&emsp;&emsp;名</label>
									<div class="layui-input-inline">
										<select name="groupId">
											<option value="">全部</option>
											<c:forEach var="group" items="${groups}">
												<option value="${group.id}" ${memberInfo.groupId == group.id ? 'selected="selected"' : ''}>${group.groupName}</option>
											</c:forEach>
										</select>
									</div>
								</li>
							</c:if>
							<c:if test="${not empty pGroupId}">
								<li class="form-item-inline">
									<div class="sub-btns" style="width: 263px"></div>
								</li>
							</c:if>
						</ul>
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">填写时间</label>
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
								<a class="layui-btn layui-btn-danger layui-btn-small" href="${ctx}/gatherInfo/member/addMember.do?projectId=${project.id}">新增人员</a>
							</c:if>
							<c:if test="${not empty group.id}">
								<a class="layui-btn layui-btn-danger layui-btn-small" href="${ctx}/gatherInfo/member/addMember.do?groupId=${group.id}">新增人员</a>
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
							<col width="90">
							<col width="40">
							<col width="90">
							<col width="90">
							<col width="100">
							<col width="70">
							<col width="70">
							<col width="70">
							<col width="135">
						</colgroup>
						<thead>
							<tr>
								<th>组名</th>
								<th>用户</th>
								<th>真实姓名</th>
								<th>性别</th>
								<th style="text-align: center;">公司</th>
								<th style="text-align: center;">职务</th>
								<th style="text-align: center;">手机</th>
								<th>基本信息</th>
								<th>保险信息</th>
								<th>行程信息</th>
								<th style="text-align: center;">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="memberInfo" items="${memberInfos}">
								<tr>
									<td title="${memberInfo.groupName}"><div class="word-break">${empty memberInfo.groupName ? '未分配' : memberInfo.groupName}</div></td>
									<td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${memberInfo.memberId}','400px','550px')">
										<div class="member-cell">
										<div class="member-logo" style="background-image: url('${memberInfo.logo}'),url(${ctx}/image/def_user_logo.png)"></div>
										<div class="member-name ellipsis-1"><a class="blue" title="${memberInfo.realname}" href="javascript:void(0);" >${memberInfo.realname}</a></div>
										</div>
									</td>
									<td title="${memberInfo.fullname}"><div class="word-break">${memberInfo.fullname}</div></td>
									<td style="text-align: center;"><c:if test="${memberInfo.sex == 1}">男</c:if><c:if test="${memberInfo.sex == 0}">女</c:if></td>
									<td title="${memberInfo.company}"><div class="word-break">${memberInfo.company}</div></td>
									<td title="${memberInfo.jobTitle}"><div class="word-break">${memberInfo.jobTitle}</div></td>
									<td>${memberInfo.mobile}</td>
									<td style="text-align: center;">
										<c:if test="${not empty memberInfo.fullname}">
											<a class="blue" href="javascript:openDialogNoButton('基本信息','${ctx}/gatherInfo/member/getBaseInfo.do?gMemberId=${memberInfo.id}','450px','660px')">查看</a>
											<c:choose>
												<c:when test="${memberInfo.baseStatus == '1'}">/</c:when>
												<c:when test="${memberInfo.baseStatus == '2'}">✔</c:when>
												<c:when test="${memberInfo.baseStatus == '3'}">✘</c:when>
											</c:choose>
										</c:if>
										<c:if test="${empty memberInfo.fullname}">
											/
										</c:if>
									</td>
									<td style="text-align: center;">
										<c:if test="${not empty memberInfo.idCard}">
											<a class="blue" href="javascript:openDialogNoButton('保险信息','${ctx}/gatherInfo/member/getInsuranceInfo.do?memberId=${memberInfo.memberId}','450px','600px')">查看</a>
											<c:choose>
												<c:when test="${memberInfo.insuranceStatus == '1'}">/</c:when>
												<c:when test="${memberInfo.insuranceStatus == '2'}">✔</c:when>
												<c:when test="${memberInfo.insuranceStatus == '3'}">✘</c:when>
											</c:choose>
										</c:if>
										<c:if test="${empty memberInfo.idCard}">
											/
										</c:if>
									</td>
									<td style="text-align: center;">
										<c:if test="${not empty memberInfo.goTime}">
											<a class="blue" href="javascript:openDialogNoButton('行程信息','${ctx}/gatherInfo/member/getItineraryInfo.do?id=${memberInfo.id}','450px','600px')">查看</a>
											<c:choose>
												<c:when test="${memberInfo.itineraryStatus == '1'}">/</c:when>
												<c:when test="${memberInfo.itineraryStatus == '2'}">✔</c:when>
												<c:when test="${memberInfo.itineraryStatus == '3'}">✘</c:when>
											</c:choose>
										</c:if>
										<c:if test="${empty memberInfo.goTime}">
											/
										</c:if>
									</td>
									<td class="tb-opts">
										<div class="comm-opts">
											<a href="javascript:openDialog('分配小组','${ctx}/gatherInfo/group/setGroup.do?memberInfoId=${memberInfo.id}','450px','350px')">分组</a>
											<c:if test="${not empty project.id && empty group.id}">
												<a href="${ctx}/gatherInfo/member/toForm.do?id=${memberInfo.id}&projectId=${project.id}" class="green">编辑</a>
											</c:if>
											<c:if test="${not empty group.id}">
												<a href="${ctx}/gatherInfo/member/toForm.do?id=${memberInfo.id}&groupId=${group.id}" class="green">编辑</a>
											</c:if>
											<a class="red" href="javascript:deleteObj('确定要删除吗？','${ctx}/gatherInfo/member/delete.do?id=${memberInfo.id}','','#myForm');">删除</a>
<%-- 											<a href="javascript:syncMemberInfo('${memberInfo.id}')">同步用户</a> --%>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<c:if test="${page.totalCount == 0}">
						<div class="f16 tc mt15">还没有收集人员信息</div>
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
	function syncMemberInfo(id) {
		var content = "确定要同步吗？";
  		layer.confirm(content, {
            icon: 3,
            title: '系统提示'
        }, function (index) {
            $.post("${ctx}/gatherInfo/member/syncMemberInfo.do", {
                id: id
            }, function (data) {
            	if (data.success == true) {
                	layer.msg('同步成功', {icon : 1}, function(){
                		submitFunction('#myForm');
                	});
                } else {
                	layer.msg('同步失败', {icon : 2});
                }
            })
        });
  	}
	
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
					url = "${ctx}/gatherInfo/member/exportMemberInfo.do?projectId=${project.id}";
				} else if ('${group.id}' != "") {
					url = "${ctx}/gatherInfo/member/exportMemberInfo.do?groupId=${group.id}";
				}
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