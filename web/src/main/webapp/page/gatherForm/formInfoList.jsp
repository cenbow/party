<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>信息管理</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<style type="text/css">

.word-break {
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 2;
	word-break: break-all;
}

.layui-table td, .layui-table th{
	padding-left: 10px!important;
	padding-right: 10px!important;
}

.layui-form input[type=text]{
	width: 212px!important;
}

.layui-table td:hover{
	cursor: pointer;
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
					<a href="${ctx}/gatherForm/project/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
				</div>
				<div class="ovh">
					<span class="title f20" title="${project.title}">项目管理&nbsp;&gt;&nbsp;
						<c:if test="${fn:length(project.title) > 20}">${fn:substring(project.title,0,20)}...</c:if>
						<c:if test="${fn:length(project.title) <= 20}">${project.title}</c:if>
						&nbsp;&gt;&nbsp;信息管理
					</span>
				</div>
			</div>
			<form class="layui-form" action="${ctx}/gatherForm/form/list.do?projectId=${project.id}" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" />
				<div class="f-search-bar">
					<div class="search-container">
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
					</div>
					<p class="cl"></p>
				</ul>
				<div class="cl">
					<table class="layui-table" id="myTable" style="table-layout:fixed;">
						<c:set value="${fn:length(fields)}" var="extOut"></c:set>
						<c:if test="${fn:length(fields) > 5}">
							<c:set value="5" var="extOut"></c:set>
						</c:if>
						<colgroup>
							<col width="120px">
							<c:forEach var="field" items="${fields}" end="${extOut}">
								<col>
							</c:forEach>
							<col width="60px">
						</colgroup>
						<thead>
							<tr>
								<th><div class="word-break">填写时间</div></th>
								<c:forEach var="field" items="${fields}" end="${extOut}">
									<th><div class="word-break" title="${field.title}">${field.title}</div></th>
								</c:forEach>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="formInfo" items="${formInfos}">
								<tr data-maxIndex="${formInfo.maxIndex}" title="点击查看更多">
									<td>
										<div class="word-break"><fmt:formatDate value="${formInfo.createDate}" pattern="yyyy-MM-dd HH:mm"/></div>
									</td>
									<c:forEach var="value" items="${formInfo.fieldValues}" end="${extOut}">
										<td><div class="word-break" title="${value}">${value}</div></td>
									</c:forEach>
									<td class="tb-opts">
										<div class="comm-opts">
											<a class="green button" href="javascript:openDialogShow('信息详情','${ctx}/gatherForm/form/formInfo.do?maxIndex=${formInfo.maxIndex}&projectId=${project.id}','600px','550px');">查看</a>
											<a class="red button" href="javascript:deleteObj('确定要删除吗？','${ctx}/gatherForm/form/delete.do?projectId=${project.id}&maxIndex=${formInfo.maxIndex}','');">删除</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<c:if test="${page.totalCount == 0}">
						<div class="f16 tc mt15">还没有收集</div>
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


	
	$(function(){
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
		$(".layui-table").delegate("td", "click", function(e){
			var $target = $(e.target);
			if($target.hasClass("button")){
				e.stopPropagation();
			} else {
				var $tr = $target.closest("tr");
				var maxIndex = $tr.attr("data-maxIndex");
				var projectId = '${project.id}';
				
				openDialogShow('信息详情', '${ctx}/gatherForm/form/formInfo.do?maxIndex=' + maxIndex + '&projectId=' + projectId, '600px', '550px');
				e.stopPropagation();
			}
		});
		
		$("#btnExport").click(function() {
			layer.confirm('确认要导出Excel吗?', {
				icon : 3,
				title : '系统提示'
			}, function(index) {
				var url = "${ctx}/gatherForm/form/exportFormInfo.do?projectId=${project.id}";
				var action = $("#myForm").attr("action");
				$("#myForm").attr("action", url);
				$("#myForm").submit();
				$("#myForm").attr("action", action);
				top.layer.close(index);
			});
		});
	});
</script>

<script type='text/javascript'>
    var subnav = document.getElementById('myTable'),
    aLi = document.querySelectorAll('#myTable colgroup col');
   	w = (subnav.offsetWidth - 60 - 120) / aLi.length;//通过容器的宽度除以li的个数来计算每个li的宽度
    for(var i=1;i<aLi.length - 1;i++){
        aLi[i].style.width = w + 'px';
    }
</script>
</body>
</html>