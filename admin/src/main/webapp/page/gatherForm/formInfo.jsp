<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>表单信息详情</title>
<%@include file="../include/commonFile.jsp"%>
<style type="text/css">
body {
	min-width: auto !important;
	background-color: white !important;
}

.info-container {
	padding-top: 10px;
	margin: 0px auto;
	width: 90%;
}

.user-infos {
	display: block;
	width: 100%;
}

.avater-big {
	width: 140px;
	height: 90px;
	display: inline-block;
	background-size: cover;
	background-repeat: no-repeat;
	background-position: center;
	position: relative;
	border: #fff 2px solid;
	border-radius: 4px;
}

.layui-table[lay-skin=line] td, .layui-table[lay-skin=line] th {
	border: none;
	border-bottom: 1px dashed #E2E2E1;
}
</style>
</head>
<body>
	<div class="layui-form info-container">
		<table class="layui-table" lay-skin="line">
			<c:forEach var="field" items="${fields}">
				<tr>
					<td width="25%"><div style="word-break: break-all;">${field.title}</div></td>
					<c:set var="thatValue" value=""></c:set>
					<c:forEach var="info" items="${infos}">
						<c:if test="${field.id == info.fieldId}">
							<c:set var="thatValue" value="${info.fieldValue}"></c:set>
						</c:if>
					</c:forEach>
					<td><div style="word-break: break-all;">${thatValue}</div></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>