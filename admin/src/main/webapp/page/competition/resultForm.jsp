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
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/util.js"></script>
<style type="text/css">
.w-60p {
	width: 310px !important;
}

.w-30p {
	width: 140px !important;
}

.w-110px {
	width: 110px !important;
}

.w-10p {
	width: 10% !important;
}

.layui-form-label {
	width: 110px !important;
	padding-left: 10px !important;
	padding-right: 10px !important;
	text-align: left !important;
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
					<td><c:if test="${competitionMember.member.sex == 1}">男</c:if> <c:if test="${competitionMember.member.sex == 0}">女</c:if></td>
					<td title="${competitionMember.member.company}"><div class="word-break">${competitionMember.member.company}</div></td>
					<td title="${competitionMember.member.jobTitle}"><div class="word-break">${competitionMember.member.jobTitle}</div></td>
					<td>${competitionMember.member.mobile}</td>
					<td>${competitionMember.number}</td>
				</tr>
			</tbody>
		</table>

		<form class="layui-form" method="post">
			<div class="layui-form-item" style="margin-bottom: 0px !important;">
				<div class="layui-inline w-110px">
					<label class="layui-form-label f16">赛事日程</label>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label f16" style="width: 70px!important;text-align: center;">目标距离</label>
				</div>
				<div class="layui-inline w-30p">
					<label class="layui-form-label f16">实际距离</label>
				</div>
				<div class="layui-inline w-60p">
					<label class="layui-form-label f16 label-text">赛事成绩</label>
				</div>
				<div class="layui-inline w-30p">
					<label class="layui-form-label label-text">是否完赛</label>
				</div>
				<div class="layui-inline w-10p">
					<label class="layui-form-label label-text">操作</label>
				</div>
			</div>
			<input type="hidden" name="projectId" value="${competitionMember.projectId}" />
			<input type="hidden" name="groupId" value="${competitionMember.groupId}" />
			<input type="hidden" name="id" value="${competitionMember.id}" />
			<c:forEach var="schedule" items="${schedules}" varStatus="status">
				<div class="layui-form-item result-item">
					<c:set var="hours" value=""></c:set>
					<c:set var="minutes" value=""></c:set>
					<c:set var="seconds" value=""></c:set>
					<c:set var="isComplete" value=""></c:set>
					<c:set var="id" value=""></c:set>
					<c:forEach var="cResult" items="${results}">
						<c:if test="${schedule.id == cResult.scheduleId}">
							<c:if test="${not empty cResult.hours}">
								<c:set var="hours" value="${cResult.hours}"></c:set>
								<c:set var="minutes" value="${cResult.minutes}"></c:set>
								<c:set var="seconds" value="${cResult.seconds}"></c:set>
							</c:if>
							<c:set var="isComplete" value="${cResult.isComplete}"></c:set>
							<c:set var="id" value="${cResult.id}"></c:set>
						</c:if>
					</c:forEach>
					<input type="hidden" name="results[${status.index}].scheduleId" value="${schedule.id}" />
					<input type="hidden" name="results[${status.index}].id" value="${id}" class="item-id" />
					<div class="layui-inline w-110px">
						<label class="layui-form-label"><fmt:formatDate value="${schedule.playDay}" pattern="yyyy年MM月dd日" /></label>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label f16" style="width: 70px!important;">${schedule.distance} KM</label>
					</div>
					<div class="layui-inline w-30p">
						<div class="layui-input-inline" style="width: 90px!important;">
			      			<input type="text" name="results[${status.index}].actualRange" class="layui-input item-range" readonly="readonly" data-distance="${schedule.distance}" value="${schedule.distance}">
			      		</div>
						<div class="layui-form-mid layui-word-aux">KM</div>
					</div>
					<div class="layui-inline w-60p">
						<div class="layui-input-inline" style="width: 70px!important;">
			      			<input type="text" name="results[${status.index}].hours" class="layui-input item-result-hours" value="${hours}">
			      		</div>
						<div class="layui-form-mid layui-word-aux">时</div>
						<div class="layui-input-inline" style="width: 70px!important;">
			      			<input type="text" name="results[${status.index}].minutes" class="layui-input item-result-minutes" value="${minutes}">
			      		</div>
						<div class="layui-form-mid layui-word-aux">分</div>
						<div class="layui-input-inline" style="width: 70px!important;">
			      			<input type="text" name="results[${status.index}].seconds" class="layui-input item-result-seconds" value="${seconds}">
			      		</div>
						<div class="layui-form-mid layui-word-aux">秒</div>
					
<%-- 						<input type="text" name="results[${status.index}].result" value="${result}" placeholder="当日参赛总用时，如05：25：03" --%>
<!-- 							class="layui-input item-result" style="width: 230px !important" /> -->
					</div>
					<div class="layui-inline w-30p">
						<input type="radio" name="results[${status.index}].isComplete" lay-filter="isComplete" title="是" value="1" ${isComplete == "1" ? 'checked="checked"' : ''}
							class="item-complete" />
						<input type="radio" name="results[${status.index}].isComplete" lay-filter="isComplete" title="否" value="2" ${isComplete == "2" ? 'checked="checked"' : ''}
							class="item-complete" />
					</div>
					<div class="layui-inline w-10p">
						<c:if test="${not empty id}">
							<a class="layui-btn layui-btn-danger layui-btn-small btn-delete"><i class="iconfont icon-delete btn-icon"></i> 删除</a>
						</c:if>
						<c:if test="${empty id}">
							<a class="layui-btn layui-btn-primary layui-btn-small btn-clear"><i class="iconfont icon-delete btn-icon"></i> 清除</a>
						</c:if>
					</div>
				</div>
			</c:forEach>
			<div class="footer-buttons">
				<a class="layui-btn layui-btn-danger" href="javascript:doSubmit()">立即提交</a>
				<a class="layui-btn layui-btn-primary" href="javascript:closeSelf()">取消</a>
			</div>
		</form>
		<div class="footer-place"></div>
	</div>
</body>
<script type="text/javascript">
	var form = null;


	$(function() {
        layui.use([ 'form', 'laydate', 'element' ], function() {
            form = layui.form(), laydate = layui.laydate(), element = layui.element();

            form.on('radio(isComplete)', function(data) {
                var value = data.value;
                var $resultItem = $(data.elem).parent().closest(".result-item");
                if (value == "2") {
                    $resultItem.find(".item-range").removeAttr("readonly");
                } else if (value == "1") {
                    var $range = $resultItem.find(".item-range");
                    var distance = $range.attr("data-distance");
                    $range.val(distance);
                    $range.attr("readonly","readonly");
                }
            });
        });
		$('.layui-form').delegate('.result-item', 'click', function(e) {
			var $target = $(e.target);
			if ($target.hasClass("icon-delete")) {
				var $parent = $target.parent();
				var item = $parent.closest(".result-item");
				if ($parent.hasClass("btn-clear")) {
					clear(item);
				} else if ($parent.hasClass("btn-delete")) {
					deleteResult(item);
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
			$(item).find(".item-result-hours").val("");
			$(item).find(".item-result-minutes").val("");
			$(item).find(".item-result-seconds").val("");
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
		var isSuccess = true;
		var _count = 0;
		$(".result-item").each(function(i, ele){
			var reg=/^\d+$/; 
			var _itemResult_hours = $(ele).find(".item-result-hours").val();
			
			var _itemResult_minutes = $(ele).find(".item-result-minutes").val();
			
			var _itemResult_seconds = $(ele).find(".item-result-seconds").val();
			
			var flag = true;
			if(util.isValid(_itemResult_hours) && !reg.test(_itemResult_hours)){
				flag = false;
			} else if(util.isValid(_itemResult_minutes) && !reg.test(_itemResult_minutes)){
				flag = false;
			} else if(util.isValid(_itemResult_seconds) && !reg.test(_itemResult_seconds)){
				flag = false;
			}
			if(!flag){
				top.layer.msg("参赛用时格式不正确", {icon : 5});
				isSuccess = false;
				return false;
			}
			
			flag = true;
			if(_itemResult_hours == "" && _itemResult_minutes == "" && _itemResult_seconds == ""){
				flag = false;
			}
			var _itemComplete = $(ele).find(".item-complete:checked").val();
			
			// 用时不为空，状态没选
			var _flag = true;
			if(flag == true){
				if(!util.isValid(_itemComplete)){
					_flag = false;
				}
			}
			
			// 状态选择为完赛 用时为空
			// 状态没选，用时不为空
			if(util.isValid(_itemComplete)){
				if(_itemComplete == "1" && flag == false){
					_flag = false;
				}
			} else {
				if(flag == true){
					_flag = false;
				}
			}
		
			if(!_flag){
				top.layer.msg("请将成绩信息填写完整", {icon : 5});
				isSuccess = false;
				return false;
			}
		});
		
		if(isSuccess){
			var paramsArray = $("form").serializeArray();
			var flag = true;
			var values = {};
			$.each(paramsArray, function(i, param) {
				values[param.name] = param.value;
			});

			if(flag){
				$.ajax({
					type : 'POST',
					url : '${ctx}/competition/result/saveResult.do',
					data : values,
					dataType : 'JSON',
					success : function(data) {
						top.layer.msg("提交成功",{icon : 1}, function(index) {
							parent.submitFunction('#myForm');
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
		}
	}

	function closeSelf() {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		top.layer.close(index);
	}
</script>
</html>