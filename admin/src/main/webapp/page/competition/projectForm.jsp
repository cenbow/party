<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>发布项目</title>
<%@include file="../include/commonFile.jsp" %>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/form.css">
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
					<a href="${ctx}/competition/project/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">赛事项目管理&nbsp;&gt;&nbsp;${project == null ? '发布' : '编辑'}项目</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/competition/project/save.do">
				<div class="layui-form-item">
					<label class="layui-form-label">项目名称<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="title" lay-verify="required" placeholder="请输入项目名称" class="layui-input" value="${project.title}">
						<input type="hidden" name="id" value="${project.id}"/>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">封面图<span class="f-verify-red">*</span></label>
					<div class="cover-content">
						<input type="hidden" name="picture" id="pic" lay-verify="required" value="${project.picture}" />
						<c:if test="${project == null || empty project.picture}">
							<span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
						</c:if>
						<c:if test="${project != null && not empty project.picture}">
							<span id="cover-img" class="cover-img" style="background-image:url('${project.picture}')"></span>
						</c:if>
						<div class="u-single-upload">
							<input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加封面图</span>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">赛事时间<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input class="layui-input" name="startTimeStr" lay-verify="required" type="text"
							value='<fmt:formatDate value="${project.startTime}" pattern="yyyy-MM-dd HH:mm" />'
							onclick="layui.laydate({elem: this,format:'YYYY-MM-DD hh:mm',istime:true})"
						/>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">赛事描述</label>
					<div class="layui-input-block">
						<textarea name="remarks" class="layui-textarea" placeholder="请输入赛事描述">${project.remarks}</textarea>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
				   		<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/competition/project/list.do" class="layui-btn layui-btn-primary">取消</a>
				  	</div>
				</div>
			</form>
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
        layui.use(['form', 'laydate'], function () {
            var form = layui.form(), laydate = layui.laydate;

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/competition/project/list.do";
                        });
                    } else {
                        $(data.elem).attr("lay-submit","");
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
	})
	

	// 文本编辑器图片上传
	function uEditorUploadCI(editor) {
		var opts = {
			type : 2,
			area : [ '800px', '600px' ],
			title : '选择图片上传',
			content : '${ctx}/fileupload/uploadCIImage.do?min=1',
			btn : [ '确定', '关闭' ],
			yes : function(index, layero) {
				var body = layer.getChildFrame('body', index);
				var iframeWin = layero.find('iframe')[0];
				var succFiles = iframeWin.contentWindow.getSucceFiles();
				if (iframeWin.contentWindow.doSubmit()) {
					if (succFiles.length) {
						for (var i = 0, item; i < succFiles.length; i++) {
							item = succFiles[i];
							editor.focus();
                            editor.execCommand('inserthtml', item.htmlStr);
						}
					}
					iframeWin.contentWindow.closeUpload();
				}
			},
			cancel : function(index) {
			}
		};
		layer.open(opts);
	}
</script>
</body>
</html>