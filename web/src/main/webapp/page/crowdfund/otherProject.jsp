<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<html>
<head>
	<title>移动数据</title>
	<link rel="stylesheet" href="${ctxStatic}/layui-v1.0.7/css/layui.css">
	<script type="text/javascript" charset="utf-8" src='${ctxStatic}/jquery/jquery-2.1.4.min.js'></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/util.js"></script>
	<style type="text/css">
		div {
			padding: 10px;
		}

		li {
			border: 1px solid #ececec;
			float: left;
			padding-left: 20px;
			padding-right: 20px;
			margin-right: 10px;
			margin-bottom: 10px; height : 35px;
			line-height: 35px;
			height: 35px;
		}

		li:hover{
			cursor: pointer;
		}

		li.checked {
			background-color: #cfb31b;
			color: white;
		}
	</style>
</head>
<body>
<div>
	<ul>
		<c:if test="${empty list}">
			<li>无可用众筹，请先报名</li>
		</c:if>
		<c:if test="${not empty list}">
			<c:forEach var="project" items="${list}">
				<li data-id="${project.id}">${project.title}</li>
			</c:forEach>
		</c:if>
	</ul>
</div>
<form id="inputForm" action="${ctx}/crowdfund/project/dataTransfer.do" method="post">
	<input type="hidden" name="targetId" id="targetId" />
	<input type="hidden" name="sourceId" value="${sourceId}" />
</form>
</body>

<script type="text/javascript">

	$(function(){
		$("li").click(function(){
			$('li.checked').removeClass('checked');
			$(this).addClass('checked');
		});
	});

	function doSubmit() {
		var flag = false;
		var targetId = $('li.checked').data('id');
		if (!util.isValid(targetId)){
			top.layer.alert("请选择众筹!");
			return flag;
		}
		$('#targetId').val(targetId);

		var index = top.layer.load(1);
		$.ajax({
			url : $(inputForm).attr("action"),
			type : "POST",
			dataType : "JSON",
			async : false,
			data : $(inputForm).serialize(),
			async : false,
			success : function(res) {
				top.layer.msg('操作成功', {
					icon : 6
				});
				top.layer.close(index);
				flag = res.success;
			}
		});
		return flag;
	}
</script>
</html>