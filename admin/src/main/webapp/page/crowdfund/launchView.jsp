<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>发布众筹</title>
    <%@include file="../include/commonFile.jsp" %>
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/crowdfund/launch_view.css">
</head>

<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <section>
        <div class="section-main">
            <div class="add-form-content">
            <form id="myForm" class="layui-form" method="post" action="${ctx}/crowdfund/crowdfund/launch.do">
                <input name="id" value="${project.id}" type="hidden">
               	<div class="layui-form-item">
                	<label class="layui-form-label">众筹标题<span class="f-verify-red">*</span></label>
                  	<div class="layui-input-block">
                    	<input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="众筹标题" class="layui-input" value="${project.title}" >
                  	</div>
               	</div>
                <div class="layui-form-item">
                    <label class="layui-form-label">众筹封面<span class="f-verify-red">*</span></label>
                    <div class="cover-content">
                        <input type="hidden" name="pic" id="pic" lay-verify="pic" value="${project.pic}"/>
                        <c:if test="${not empty project.pic}">
                            <span id="cover-img" class="cover-img" style="background-image:url(${project.pic}),url(${ctx}/image/posterImg.png)"></span>
                        </c:if>
                        <c:if test="${empty project.pic}">
                            <span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
                        </c:if>
                        <div class="u-single-upload">
                            <input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加封面图</span>
                        </div>
                        <div class="form-word-aux">建议尺寸：800x450</div>
                    </div>
                </div>
	         	<div class="layui-form-item">
	             	<label class="layui-form-label">筹资目标<span class="f-verify-red">*</span></label>
	              	<div class="layui-input-block">
	                 	<input  name="targetAmount" autocomplete="off" placeholder="￥" class="layui-input" value="${project.targetAmount}" ${not empty project.id ? 'readonly="readonly"' : 'lay-verify="number"'} >
	              	</div>
	          	</div>

                <div class="layui-form-item">
                    <label class="layui-form-label">众筹描述<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <textarea name="remarks" placeholder="用一句话描述您的众筹" lay-verify="remarks" class="layui-textarea">${project.remarks}</textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-block">
                        <label class="layui-form-label">众筹详情<span class="f-verify-red">*</span></label>
                        <div class="layui-input-block">
                            <script id="ueditor1" type="text/plain" style=" height: 500px; width: 867px;"></script>
                            <div style="display: none" id="contentView">${project.content}</div>
                            <input type="hidden" name="content" lay-verify="content" id="content" class="errorinput" />
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
					<div class="layui-input-block">
				   		<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
				  	</div>
				</div>
            </form>
            </div>
        </div>
    </section>
</div>

<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/myplugin/uploadCI.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/resize.js"></script>
<script type="text/javascript">
    var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');
    var ue = UE.getEditor('ueditor1');

    $(function(){
        layui.use(['form'], function () {
            var form = layui.form();
            //监听提交
            form.on('submit(*)', function (data) {
                $(data.elem).removeAttr("lay-submit");
                $("#content").val(ue.getContent().replace(/&quot;/gi, ""));
                $.post('${ctx}/crowdfund/project/launch.do', $('#myForm').serialize(), function (res) {
                    if (res.success){
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            window.location.href = '${ctx}/crowdfund/project/list.do';
                        });
                    }
                    else {
                        top.layer.msg(res.description, {icon : 2});
                    }
                });
                return false;
            });


            form.verify({
                title : function(value) {
                    if (value == "") {
                        return '请填写众筹标题';
                    }
                },
                pic : function(value) {
                    if (value == "") {
                        return "请上传众筹封面图";
                    }
                },
                remarks : function(value) {
                    if (value == "") {
                        return "请填写众筹描述";
                    }
                },
                content : function(value) {
                    $("#contentView").html(ue.getContent());
                    var content = $("#contentView").html();
                    if (content == "") {
                        return "请填写文章内容";
                    }
                }
            });
        });
        ue.addListener('ready', function() {
            this.setContent($("#contentView").text());
        });
        $('#upload_single_img').change(function () {
            if (util.isValid(this.value)) {
                uploadFile.uploadSinglePhoto(this.files[0], function (ret) {
                    console.log('回调：' + ret);
                    $('#cover-img').css('background-image', 'url(' + ret.data.download_url + ')');
                    $('#pic').val(ret.data.download_url);
                    $('#upload_single_img').val('');
                });
            }
        })
    });

    // 文本编辑器图片上传
    function uEditorUploadCI(editor) {
        var opts = {
            type: 2,
            area: ['800px', '600px'],
            title: '选择图片上传',
            content: '${ctx}/fileupload/uploadCIImage.do?min=1' ,
            btn: ['确定','关闭'],
            yes: function(index, layero) {
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
            cancel: function(index){
            }
        };
        layer.open(opts);
    }
</script>
</html>
