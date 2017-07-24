<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>点赞管理</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<%@include file="../../include/commonFile.jsp"%>
</head>
<!--头部-->
<%@include file="../../include/header.jsp"%>
<div class="index-outside">
	<%@include file="../../include/sidebar.jsp"%>
	<!--内容-->
	<section>
		<div class="section-main">
			<div class="c-time-list-header">
				<div class="r">
					<a href="javascript:history.back();" class="layui-btn layui-btn-radius layui-btn-danger"> <i
						class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">动态管理&nbsp;&gt;&nbsp;
					<span title="${dynamic.content}">
						<c:if test="${fn:length(dynamic.content) > 25}">${fn:substring(dynamic.content,0,25)}...</c:if>
						<c:if test="${fn:length(dynamic.content) < 25}">${dynamic.content}</c:if>
					</span>
					&nbsp;&gt;&nbsp;点赞管理</span>
				</div>
			</div>
			<form class="layui-form" action="${ctx}/dynamic/comment/commentList.do" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" />
				<input type="hidden" name="refId" value="${dynamic.id}" />
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">评论时间</label>
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
				<ul id="view">
					<table class="layui-table">
						<colgroup>
							<col width="150">
							<col width="200">
							<col width="150">
							<col width="150">
						</colgroup>
						<thead>
							<tr>
								<th>作者</th>
								<th>点赞时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${page.totalCount == 0}">
								<tr>
									<td colspan="3"><div class="f16 tc">还没有人点赞</div></td>
								</tr>
							</c:if>
							<c:forEach var="love" items="${loves}">
								<tr>
									<td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${love.author.id}','400px','470px')">
										<div class="member-logo" style="background-image: url('${love.author.logo}'),url(${ctx}/image/def_user_logo.png)"></div>
										<div class="member-name ellipsis-1"><a class="blue" title="${love.author.realname}" href="javascript:void(0);" >${love.author.realname}</a></div>
									</td>
									<td><fmt:formatDate value="${love.createDate}" pattern="yyyy-MM-dd HH:mm" /></td>
									<td>
										<a href="javascript:deleteObj('确定要删除吗？','${ctx}/dynamic/love/delete.do?id=${love.id}','');">
											<i class="iconfont icon-delete btn-icon"></i> 删除
										</a>
									</td>
								</tr>
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
<%@include file="../../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">

    showActive('${input.createStart}', '${input.createEnd}', '#timeType');

	//加载分页
	loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');

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
					}, 200);
				}

			},
			cancel : function(index) {
			}
		});
	}
</script>
</body>
</html>