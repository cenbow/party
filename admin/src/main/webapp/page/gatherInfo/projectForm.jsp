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
					<a href="${ctx}/gatherInfo/project/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">项目管理&nbsp;&gt;&nbsp;${project == null ? '发布' : '编辑'}项目</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/gatherInfo/project/save.do">
				<div class="layui-form-item">
					<label class="layui-form-label">名称<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="title" lay-verify="title" placeholder="项目名称" class="layui-input" value="${project.title}">
						<input type="hidden" name="id" value="${project.id}"/>
					</div>
				</div>
<!-- 				<div class="layui-form-item"> -->
<!-- 					<label class="layui-form-label">封面图<span class="f-verify-red">*</span></label> -->
<!-- 					<div class="cover-content"> -->
<%-- 						<input type="hidden" name="picture" id="pic" lay-verify="picture" value="${project.picture}" /> --%>
<%-- 						<c:if test="${project == null || empty project.picture}"> --%>
<%-- 							<span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span> --%>
<%-- 						</c:if> --%>
<%-- 						<c:if test="${project != null && not empty project.picture}"> --%>
<%-- 							<span id="cover-img" class="cover-img" style="background-image:url('${project.picture}')"></span> --%>
<%-- 						</c:if> --%>
<!-- 						<div class="u-single-upload"> -->
<!-- 							<input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加封面图</span> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="layui-form-item">
					<label class="layui-form-label">基本信息描述<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
<!-- 						<script id="ueditor1" type="text/plain" style="width: 800px; height: 200px;"></script> -->
<%-- 						<div style="display: none" id="baseInfoView">${project.baseInfoDesc}</div> --%>
<!-- 						<input type="hidden" name="baseInfoDesc" id="baseInfoDesc" lay-verify="baseInfoDesc" /> -->
						<textarea name="baseInfoDesc" class="layui-textarea" lay-verify="required" placeholder="基本信息描述">${project.baseInfoDesc}</textarea>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">保险信息描述<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
<!-- 						<script id="insuranceUeditor" type="text/plain" style="width: 800px; height: 200px;"></script> -->
<%-- 						<div style="display: none" id="insuranceInfoView">${project.insuranceInfoDesc}</div> --%>
<!-- 						<input type="hidden" name="insuranceInfoDesc" id="insuranceInfoDesc" lay-verify="insuranceInfoDesc" /> -->
						<textarea name="insuranceInfoDesc" class="layui-textarea" lay-verify="required" placeholder="保险信息描述">${project.insuranceInfoDesc}</textarea>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">行程信息描述<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
<!-- 						<script id="itineraryUeditor" type="text/plain" style="width: 800px; height: 200px;"></script> -->
<%-- 						<div style="display: none" id="itineraryInfoView">${project.itineraryInfoDesc}</div> --%>
<!-- 						<input type="hidden" name="itineraryInfoDesc" id="itineraryInfoDesc" lay-verify="itineraryInfoDesc" /> -->
						<textarea name="itineraryInfoDesc" class="layui-textarea" lay-verify="required" placeholder="行程信息描述">${project.itineraryInfoDesc}</textarea>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
				   		<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/gatherInfo/project/list.do" class="layui-btn layui-btn-primary">取消</a>
				  	</div>
				</div>
			</form>
		</div>
	</section>
</div>

<!--底部-->
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/myplugin/uploadCI.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/resize.js"></script>
<script>
	var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');
	
// 	var ue = UE.getEditor('ueditor1');
// 	var itineraryUeditor = UE.getEditor('itineraryUeditor');
// 	var insuranceUeditor = UE.getEditor('insuranceUeditor');
	
	$(function() {
// 		ue.addListener('ready', function() {
// 			if($("#baseInfoView").html() != "" && $("#baseInfoView").html().length > 200){
// 				this.setHeight(850);
// 			}
// 			this.setContent($("#baseInfoView").html());
// 		});
// 		ue.addListener('blur', function() {
// 			$("#baseInfoView").html(ue.getContent());
// 		});

// 		itineraryUeditor.addListener('ready', function() {
// 			this.setContent($("#itineraryInfoView").html());
// 			if($("#itineraryInfoView").html() != "" && $("#itineraryInfoView").html().length > 200){
// 				this.setHeight(200);
// 			}
// 		});
// 		itineraryUeditor.addListener('blur', function() {
// 			$("#itineraryInfoView").html(itineraryUeditor.getContent());
// 		});

// 		insuranceUeditor.addListener('ready', function() {
// 			this.setContent($("#insuranceInfoView").html());
// 			if($("#insuranceInfoView").html() != "" && $("#insuranceInfoView").html().length > 200){
// 				this.setHeight(200);
// 			}
// 		});
// 		insuranceUeditor.addListener('blur', function() {
// 			$("#insuranceInfoView").html(insuranceUeditor.getContent());
// 		});
        layui.use(['form'], function () {
            var form = layui.form(), laydate = layui.laydate;

            //自定义验证规则
            form.verify({
                title : function(value) {
                    if (value == "") {
                        return '请填写项目名称';
                    }
                },
// 			picture : function(value) {
// 				if (value == "") {
// 					return "请上传项目封面图";
// 				}
// 			},
                baseInfoDesc : function(value) {
                    var content = $("#baseInfoView").html();
                    if (content == "") {
                        return "请填写基本信息描述";
                    }
                },
                itineraryInfoDesc : function (value) {
                    var content = $("#itineraryInfoView").html();
                    if (content == "") {
                        return "请填写行程信息描述";
                    }
                },
                insuranceInfoDesc : function (value) {
                    var content = $("#insuranceInfoView").html();
                    if (content == "") {
                        return "请填写保险信息描述";
                    }
                }
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
// 			$("#baseInfoDesc").val(ue.getContent().replace(/&quot;/gi, ""));
// 			$("#itineraryInfoDesc").val(itineraryUeditor.getContent().replace(/&quot;/gi, ""));
// 			$("#insuranceInfoDesc").val(insuranceUeditor.getContent().replace(/&quot;/gi, ""));
                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/gatherInfo/project/list.do";
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