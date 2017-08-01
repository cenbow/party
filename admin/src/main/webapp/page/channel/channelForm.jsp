<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>发布频道</title>
<%@include file="../include/commonFile.jsp" %>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_form.css">
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
					<a href="${ctx}/channel/channel/channelList.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">频道管理&nbsp;&gt;&nbsp;${member == null ? '发布' : '编辑'}频道</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<div class="add-form-content">
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/channel/channel/save.do">
				<div class="layui-form-item">
					<label class="layui-form-label">名称<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="name" lay-verify="name" autocomplete="off" placeholder="频道名称" class="layui-input" value="${channel.name}">
						<input type="hidden" name="id" value="${channel.id}"/>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">封面图<span class="f-verify-red">*</span></label>
					<div class="cover-content">
						<input type="hidden" name="picture" id="pic" lay-verify="pic" value="${channel.picture}" />
						<c:if test="${channel == null || empty channel.picture}">
							<span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
						</c:if>
						<c:if test="${channel != null && not empty channel.picture}">
							<span id="cover-img" class="cover-img" style="background-image:url('${channel.picture}')"></span>
						</c:if>
						<div class="u-single-upload">
							<input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加封面图</span>
						</div>
						<div class="form-word-aux">建议尺寸：800x450</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">显示在前端<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="radio" name="isShow" value="1" title="是"
							${channel != null && channel.isShow == "1" ? 'checked="checked"' : ''}
						>
						<input type="radio" name="isShow" value="0" title="否"
							${channel == null || channel.isShow == "0" ? 'checked="checked"' : ''}
						>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">排序号</label>
						<div class="layui-input-inline">
							<input type="text" name="sort" lay-verify="sort" autocomplete="off" class="layui-input" value="${channel.sort}" >
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">描述<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
				   		<textarea name="remarks" placeholder="频道描述" lay-verify="remarks" class="layui-textarea">${channel.remarks}</textarea>
				    </div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
				   		<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/channel/channel/channelList.do" class="layui-btn layui-btn-primary">取消</a>
				  	</div>
				</div>
			</form>
			</div>
		</div>
	</section>
</div>

<!--底部-->
<%@include file="../include/footer.jsp" %>

<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/resize.js"></script>
<script>
	var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');


	$(function() {
        layui.use(['form'], function () {
            var form = layui.form(), laydate = layui.laydate;

            //自定义验证规则
            form.verify({
                name : function(value) {
                    if (value == "") {
                        return '请填写频道名称';
                    }
                },
                pic : function(value) {
                    if (value == "") {
                        return "请上传频道封面图";
                    }
                },
                sort : function(value) {
                    if (!util.checkNumber(value)) {
                        return "请输入正确的数字";
                    }
                },
                remarks : function(value) {
                    if (value == "") {
                        return '请填写频道描述';
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
                            location.href = "${ctx}/channel/channel/channelList.do";
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
				uploadFile.uploadSinglePhoto(this.files[0], function(
						ret) {
					console.log('回调：' + ret);
					$('#cover-img').css('background-image',
							'url(' + ret.data.download_url + ')');
					$('#pic').val(ret.data.download_url);
					$('#upload_single_img').val('');
				});
			}
		});
	})
</script>
</body>
</html>