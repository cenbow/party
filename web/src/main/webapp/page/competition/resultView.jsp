<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<html>
<head>
<title>${result == null ? '新增成绩' : '编辑成绩'}</title>
<link rel="stylesheet" href="${ctxStatic}/layui-v1.0.7/css/layui.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/circle/set_tag.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/common.css">
<script type="text/javascript" charset="utf-8" src='${ctxStatic}/jquery/jquery-2.1.4.min.js'></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/layui-v1.0.9_rls/layui.js"></script>
<style type="text/css">
.w-60p {
	width: 50% !important;
}

.w-30p {
	width: 20% !important;
}

.layui-form-label {
	width: 120px !important;
}

.label-text {
	padding-left: 0px !important;
	text-align: left !important;
	font-size: 16px !important;
}

.layui-form {
	border: 1px solid #e2e2e2 !important;
	background-color: white !important;
}

.footer-buttons {
	position: fixed;
	bottom: 0;
	right: 0;
	left: 0;
	background: #fff;
	padding: 10px;
	text-align: right
}

.layui-btn-small i {
	font-size: 15px !important;
}

.footer-place {
	height: 58px;
}
</style>
</head>
<body>
	<div class="tag-wrap">
		<table class="layui-table" lay-skin="line">
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
					<th>小组名</th>
					<th>用户</th>
					<th>性别</th>
					<th>公司</th>
					<th>职务</th>
					<th>手机</th>
					<th>号码牌</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td title="${competitionMember.groupName}">
						<div class="word-break">${empty competitionMember.groupName ? '未分配' : competitionMember.groupName}</div>
					</td>
					<td class="table-member">
						<div class="member-cell">
						<div class="member-logo" style="background-image: url('${competitionMember.member.logo}'),url(${ctx}/image/def_user_logo.png)"></div>
						<div class="member-name ellipsis-1">
							<a title="${competitionMember.member.realname}" href="javascript:void(0);">${competitionMember.member.realname}</a>
						</div>
						</div>
					</td>
					<td style="text-align: center;"><c:if test="${competitionMember.member.sex == 1}">男</c:if> <c:if test="${competitionMember.member.sex == 0}">女</c:if></td>
					<td title="${competitionMember.member.company}"><div class="word-break">${competitionMember.member.company}</div></td>
					<td title="${competitionMember.member.jobTitle}"><div class="word-break">${competitionMember.member.jobTitle}</div></td>
					<td>${competitionMember.member.mobile}</td>
					<td>${competitionMember.number}</td>
				</tr>
			</tbody>
		</table>
		<table class="layui-table" lay-skin="line">
			<colgroup>
				<col>
				<col>
				<col>
				<col>
				<col>
				<col>
				<col>
<!-- 				<col> -->
			</colgroup>
			<thead>
				<tr>
					<th>赛事日程</th>
					<th>打卡点</th>
					<th>里程距离</th>
					<th>用时</th>
					<th>配速</th>
					<th>状态</th>
<!-- 					<th>实际距离</th> -->
					<th>日程排名</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="cResult" items="${results}">
				<tr>
					<td><fmt:formatDate value="${cResult.playDay}" pattern="yyyy-MM-dd"/></td>
					<td>${cResult.place}</td>
					<td>${cResult.distance}KM</td>
					<td style="letter-spacing: 1.5">${cResult.result}</td>
					<td>${empty cResult.paceMinutes ? '0' : cResult.paceMinutes}'${empty cResult.paceSeconds ? '0' : cResult.paceSeconds}"</td>
					<td>${cResult.isComplete == 1 ? '完赛' : '未完赛'}</td>
<%-- 					<td>${not empty cResult.actualRange ? cResult.actualRange : '0'}KM</td> --%>
					<td>
						<c:if test="${not empty cResult.rankNo}">
							<fmt:formatNumber value="${cResult.rankNo}" maxFractionDigits="0"></fmt:formatNumber>
						</c:if>
						<c:if test="${empty cResult.rankNo}">--</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="footer-place"></div>
	</div>
</body>
<script type="text/javascript">
	var form = null;

	$(function() {
        layui.use([ 'form', 'laydate', 'element' ], function() {
            form = layui.form(), laydate = layui.laydate(), element = layui.element();
        });

        $('.layui-form').delegate('.result-item', 'click', function(e) {
			var $target = $(e.target);
			if ($target.hasClass("icon-delete")) {
				var $parent = $target.parent();
				if ($parent.hasClass("btn-clear")) {
					clear($parent);
				} else if ($parent.hasClass("btn-delete")) {
					deleteResult($parent);
				}
			}
			if ($target.hasClass("btn-clear")) {
				clear($target);
				e.stopPropagation();
			} else if ($target.hasClass("btn-delete")) {
				var item = $target.closest(".result-item");
				deleteResult(item);
				e.stopPropagation();
			}
		});

		function clear(target) {
			var item = $(target).closest(".result-item");
			$(item).find(".item-result").val("");
			$(item).find(".item-complete").each(function(index, ele) {
				$(this).removeAttr("checked");
			});
			form.render('radio');
		}
		
		function deleteResult(item) {
			var id = $(item).find(".item-id").val();

			$.ajax({
				type : 'POST',
				url : '${ctx}/competition/result/deleteResult.do',
				data : {
					id : id
				},
				dataType : 'JSON',
				success : function(data) {
					top.layer.msg("删除成功", {
						icon : 1
					}, function(index) {
						top.layer.close(index);
						location.reload();
					});
				},
				error : function(data) {
					top.layer.msg("删除失败", {
						icon : 2
					}, function(index) {
						top.layer.close(index);
					});
				}
			});
		}
	});

	function doSubmit() {
		var paramsArray = $("form").serializeArray();
		var values = {};
		$.each(paramsArray, function(i, param) {
			values[param.name] = param.value;
		});

		$.ajax({
			type : 'POST',
			url : '${ctx}/competition/result/saveResult.do',
			data : values,
			dataType : 'JSON',
			success : function(data) {
				top.layer.msg("提交成功",{icon : 1}, function(index) {
					top.layer.close(index);
					parent.location.href = "${ctx}/competition/member/list.do?projectId=${competitionMember.projectId}";
				});
			},
			error : function() {
				top.layer.msg("提交失败", {
					icon : 2
				}, function(index) {
					top.layer.close(index);
				});
			}
		});
	}

	function closeSelf() {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		top.layer.close(index);
	}
</script>
</html>