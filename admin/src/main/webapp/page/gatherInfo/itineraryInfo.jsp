<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>行程信息</title>
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

.layui-input {
	border: 0px;
	word-break: break-all;
	padding-left: 0px!important;
	display: initial!important;
}

.layui-form-item {
	margin-bottom: 0px !important;
}

.layui-form-item .layui-inline {
	margin-bottom: 0px !important;
}

.layui-form-item .layui-input-inline {
	margin-bottom: 0px !important;
}

.user-infos {
	display: block;
	width: 100%;
}

.layui-form-item .layui-input-inline {
	width: 300px !important;
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

.footer-place {
	height: 58px;
}

.aui-field-title {
	border: none;
	border-top: 1px solid #e2e2e2;
	text-align: center;
	margin: 15px 30px 0px;
}

.aui-field-title legend {
	padding: 0px 10px;
}

.layui-form-label{
	width: 90px!important;
}
</style>
</head>
<body>
	<form id="myForm" class="layui-form info-container" action="${ctx}/gatherInfo/member/verifyItineraryInfo.do">
		<fieldset class="aui-field-title">
			<legend>个人信息</legend>
		</fieldset>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">姓&emsp;&emsp;名</label>
				<div class="layui-input-inline">
					<span class="layui-input">${member.fullname}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">公&emsp;&emsp;司</label>
				<div class="layui-input-inline">
					<span class="layui-input">${member.company}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">职&emsp;&emsp;务</label>
				<div class="layui-input-inline">
					<span class="layui-input">${member.jobTitle}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">手&emsp;&emsp;机</label>
				<div class="layui-input-inline">
					<span class="layui-input">${member.mobile}</span>
				</div>
			</div>
		</div>
		<fieldset class="aui-field-title">
			<legend>去程信息</legend>
		</fieldset>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">出发城市</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.goDepartCity}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">出发方式</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.goType}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">航班/列车编号</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.goNumber}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">出发时间</label>
				<div class="layui-input-inline">
					<span class="layui-input"><fmt:formatDate value="${memberInfo.goDepartTime}" pattern="yyyy-MM-dd HH:mm"/></span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">到达时间</label>
				<div class="layui-input-inline">
					<span class="layui-input"><fmt:formatDate value="${memberInfo.goTime}" pattern="yyyy-MM-dd HH:mm"/></span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"> 到达站点名称</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.goStation}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"> 是否需要接</label>
				<div class="layui-input-inline">
					<span class="layui-input">
						<c:if test="${not empty memberInfo.backShuttle}">
							${memberInfo.goShuttle == "1" ? '是' : '否'}
						</c:if>
					</span>
				</div>
			</div>
		</div>
		<fieldset class="aui-field-title">
			<legend>返程信息</legend>
		</fieldset>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">返程方式</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.backType}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"> 航班/列车编号</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.backNumber}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">出发时间</label>
				<div class="layui-input-inline">
					<span class="layui-input"><fmt:formatDate value="${memberInfo.backDepartTime}" pattern="yyyy-MM-dd HH:mm"/></span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">到达时间</label>
				<div class="layui-input-inline">
					<span class="layui-input"><fmt:formatDate value="${memberInfo.backTime}" pattern="yyyy-MM-dd HH:mm"/></span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">到达城市</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.backDepartCity}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">到达站点名称</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.backStation}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"> 是否需要送</label>
				<div class="layui-input-inline">
					<span class="layui-input">
						<c:if test="${not empty memberInfo.backShuttle}">
							${memberInfo.backShuttle == "1" ? '是' : '否'}
						</c:if>
					</span>
				</div>
			</div>
		</div>
		<fieldset class="aui-field-title">
			<legend>其他</legend>
		</fieldset>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"> 备注信息</label>
				<div class="layui-input-inline">
					<span class="layui-input">${memberInfo.remarks}</span>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">审核状态</label>
			<div class="layui-input-block">
				<input type="radio" name="perfectState" lay-filter="itineraryStatus" value="1" title="待审核"
					${memberInfo.itineraryStatus == "1" ? 'checked="checked"' : ''} />
				<input type="radio" name="perfectState" lay-filter="itineraryStatus" value="2" title="通过"
					${memberInfo.itineraryStatus == "2" ? 'checked="checked"' : ''} />
				<input type="radio" name="perfectState" lay-filter="itineraryStatus" value="3" title="未通过"
					${memberInfo.itineraryStatus == "3" ? 'checked="checked"' : ''} />
				<input type="hidden" name="id" value="${memberInfo.id}" />
			</div>
		</div>
		<div class="footer-buttons">
			<a class="layui-btn layui-btn-danger" lay-submit lay-filter="*" href="javascript:void(0)">提交</a>
			<a class="layui-btn layui-btn-normal" href="javascript:closeSelf()">取消</a>
		</div>
		<div class="footer-place"></div>
	</form>
</body>
<script type="text/javascript">
    $(function () {
        layui.use([ 'form' ],function() {
            var form = layui.form();

          //监听提交
			form.on('submit', function(data) {
				$(data.elem).removeAttr("lay-submit");
				var action = $("#myForm").attr("action");
				$.post(action, $('#myForm').serialize(), function(res) {
					if (res.success) {
						top.layer.msg('提交成功', {
							icon : 1
						}, function(index) {
							parent.submitFunction('#myForm');
						});
					} else {
						top.layer.msg('提交失败', {
							icon : 2
						});
					}
				});
				return false;
			});
        });
    })

	function closeSelf() {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		top.layer.close(index);
	}
</script>
</html>