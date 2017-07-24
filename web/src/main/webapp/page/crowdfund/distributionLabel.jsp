<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<html>
<head>
<title>分配角色</title>
<link rel="stylesheet" href="${ctxStatic}/layui-v1.0.7/css/layui.css">
<script type="text/javascript" charset="utf-8" src='${ctxStatic}/jquery/jquery-2.1.4.min.js'></script>
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
			<c:forEach var="label" items="${labelList}">
				<c:set var="flag" value="false"></c:set>
				<c:forEach var="myLabel" items="${myLabelList}">
					<c:if test="${myLabel.labelId == label.id}">
						<c:set var="flag" value="true"></c:set>
					</c:if>
				</c:forEach>
				<c:if test="${flag == true}">
					<li class="checked" data-id="${label.id}" >${label.name}</li>
				</c:if>
				<c:if test="${flag == false}">
					<li data-id="${label.id}">${label.name}</li>
				</c:if>
			</c:forEach>
		</ul>
	</div>
	<form id="inputForm" action="${ctx}/crowdfund/analyze/labelSave.do" method="post">
		<input type="hidden" name="ids" id="ids" />
		<input type="hidden" name="projectId" value="${projectId}" />
	</form>
</body>
<script type="text/javascript">
	$(function(){
		$("li").click(function(){
			if(!$(this).hasClass("checked")){
				$(this).addClass("checked");	
			}else{
				$(this).removeClass("checked");
			}
		});
	});

	function doSubmit() {
		var array = new Array();
		$("li.checked").each(function(index, ele){
			var id = $(ele).attr("data-id");
			array.push(id);
		});
		
		$("#ids").val(array.join(","));

		var flag = false;

		$.ajax({
			url : $(inputForm).attr("action"),
			type : "POST",
			dataType : "JSON",
			async : false,
			data : $(inputForm).serialize(),
			async : false,
			success : function(res) {
				top.layer.msg('分配成功', {
					icon : 6
				});
				flag = res.success;
			}
		});
		return flag;
	}
</script>
</html>