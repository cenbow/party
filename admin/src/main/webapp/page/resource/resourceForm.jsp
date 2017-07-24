<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>发布动态</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_form.css">
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
					<a href="${ctx}/resource/resource/resourceList.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i
						class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">资源管理&nbsp;&gt;&nbsp;${resource == null ? '发布' : '编辑'}资源</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/resource/resource/save.do">
				<input type="hidden" name="id" value="${resource.id}" />
				
				<div class="layui-form-item">
					<label class="layui-form-label">标题<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="title" style="width: 80%" class="layui-input" value="${resource.title}">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">图片<span class="f-verify-red">*</span></label>
					<div class="cover-content">
						<input type="hidden" name="pic" id="pic" lay-verify="pic" value="${resource.pic}" />
						<c:if test="${resource == null || empty resource.pic}">
							<span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
						</c:if>
						<c:if test="${resource != null && not empty resource.pic}">
							<span id="cover-img" class="cover-img" style="background-image:url('${resource.pic}')"></span>
						</c:if>
						<div class="u-single-upload">
							<input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加图片</span>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">资源类型<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<select name="type" lay-verify="type">
								<option value="">请选择类型</option>
								<c:forEach var="type" items="${resourceTypes}">
									<option value="${type.value}" ${resource.type == type.value ? 'selected="selected"' : ""}>${type.label}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">链接</label>
					<div class="layui-input-block">
						<input type="text" name="link" style="width: 80%" autocomplete="off" class="layui-input" value="${resource.link}" />
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">排序号</label>
						<div class="layui-input-inline">
							<input type="text" name="sort" lay-verify="sort" class="layui-input" value="${resource.sort}" >
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/resource/resource/resourceList.do" class="layui-btn layui-btn-primary">取消</a>
					</div>
				</div>
			</form>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/resize.js"></script>
<script>
	var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');


	
	$(function(){
        layui.use([ 'form', 'laydate' ], function() {
            var form = layui.form(), laydate = layui.laydate;
            var freeRadioValue = null;
            var inviteRadioValue = null;

            form.verify({
                title : function(value) {
                    if (value == "") {
                        return '请填写资源标题';
                    }
                },
                pic : function(value) {
                    if (value == "") {
                        return "请上传资源图片";
                    }
                },
                sort : function(value) {
                    if (!util.checkNumber(value)) {
                        return "请输入正确的数字";
                    }
                }
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/resource/resource/resourceList.do";
                        });
                    } else {
                        top.layer.msg('提交失败', {icon : 2});
                    }
                });
                return false;
            });
        });
		$('#upload_single_img').change(function() {
			if (util.isValid(this.value)) {
				uploadFile.uploadSinglePhoto(this.files[0], function(ret) {
					console.log('回调：' + ret);
					$('#cover-img').css('background-image', 'url(' + ret.data.download_url + ')');
					$('#pic').val(ret.data.download_url);
					$('#upload_single_img').val('');
				});
			}
		});
	});
</script>
</body>
</html>