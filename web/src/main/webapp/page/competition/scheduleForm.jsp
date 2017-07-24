<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>创建赛事日程</title>
<%@include file="../include/commonFile.jsp" %>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/form.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_form.css">
<style type="text/css">
</style>
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
	<%@include file="../include/sidebar.jsp"%>
	<!--内容-->
	<section>
		<div class="section-main">
			<div class="c-time-list-header">
				<div class="r">
					<a href="${ctx}/competition/schedule/list.do?projectId=${project.id}" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20" title="${project.title}">我发布的赛事项目&nbsp;&gt;&nbsp;
						<c:if test="${fn:length(project.title) > 20}">${fn:substring(project.title,0,20)}...</c:if>
						<c:if test="${fn:length(project.title) <= 20}">${project.title}</c:if>
						&nbsp;&gt;&nbsp;赛事日程管理
						&nbsp;&gt;&nbsp;${schedule == null ? '创建' : '编辑'}赛事日程
					</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/competition/schedule/save.do">
				<input type="hidden" name="id" value="${schedule.id}"/>
				<input type="hidden" name="projectId" value="${project.id}"/>
				<div class="layui-form-item">
					<label class="layui-form-label">比赛日<span class="f-verify-red">*</span></label>
					<div class="layui-input-inline">
						<input class="layui-input" name="playDayStr" lay-verify="playDayStr" type="text"
							value='<fmt:formatDate value="${schedule.playDay}" pattern="yyyy-MM-dd" />'
							onclick="layui.laydate({elem: this,format:'YYYY-MM-DD',min : '${startTime}'})"
						/>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">赛事地点<span class="f-verify-red">*</span></label>
					<div class="layui-input-inline">
						<input type="text" name="place" lay-verify="place" placeholder="请输入赛事地点" class="layui-input" value="${schedule.place}">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">日程距离<span class="f-verify-red">*</span></label>
					<div class="layui-input-inline" style="width: 150px!important;">
			      		<input type="text" name="distance" lay-verify="distance" placeholder="请设置当日赛程目标" class="layui-input" value="${schedule.distance}">
			      	</div>
					<div class="layui-form-mid layui-word-aux">KM</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
				   		<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/competition/schedule/list.do?projectId=${project.id}" class="layui-btn layui-btn-primary">取消</a>
				  	</div>
				</div>
			</form>
		</div>
	</section>
</div>

<!--底部-->
<%@include file="../include/footer.jsp" %>
<script>
	function ajaxSubmit(playDayStr, projectId, id) {
	    var isRepeat = false;
	    var desc = "";
	    $.ajax({
	        type: 'POST',
	        async: false, // 使用同步的方法
	        data: {
	        	playDayStr: playDayStr,
	        	projectId: projectId,
	        	id: id
	        },
	        dataType: 'json',
	        success: function (data) {
	            isRepeat = data.success;
	            desc = data.description;
	        },
	        url: '${ctx}/competition/schedule/checkPlayDay.do'
	    });
	   	var result = {
   			isRepeat : isRepeat,
   			desc : desc		
	   	}
	    return result;
	} 
$(function () {
    layui.use(['form', 'laydate'], function () {
        var form = layui.form(), laydate = layui.laydate;

        form.verify({
            playDayStr : function(value){
                if(value == ""){
                    return "比赛日不能为空";
                }
                var result = ajaxSubmit(value, '${project.id}', '${schedule.id}');
                if(!result.isRepeat){
                    return result.desc;
                }
            },
            place : function(value){
                if(value == ""){
                    return "赛事地点不能为空";
                }
            },
            distance : function(value){
                if(value == ""){
                    return "日程距离不能为空";
                }
                var pattern = /^[0-9]+(.[0-9]{1})?$/;
                if (!pattern.test(value)) {
                    return "日程距离格式不正确";
                }
            }
        });

        //监听提交
        form.on('submit', function(data) {
        	//loading层
			var loadIndex = layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
            var action = $("#myForm").attr("action");
            $.post(action, $('#myForm').serialize(), function(res) {
            	layer.close(loadIndex);
            	setTimeout(function() {
	                if (res.success) {
	                    layer.msg('提交成功', {icon : 1}, function(index){
	                        location.href = "${ctx}/competition/schedule/list.do?projectId=${project.id}";
	                    });
	                } else {
	                    layer.msg('提交失败', {icon : 2});
	                }
            	}, 500);// 延时0.5秒
            });
            return false;
        });
    });
})

</script>
</body>
</html>