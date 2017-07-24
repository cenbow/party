<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>帮助教程管理</title>
    <%@include file="../include/commonFile.jsp" %>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_form.css">
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp" %>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="javascript:history.back();" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-refresh btn-icon"></i> 返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">帮助教程管理&nbsp;&gt;&nbsp;${help.id == null ? '新增' : '编辑'}教程</span>
                </div>
            </div>
            <!-- 正文请写在这里 -->
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/help/help/save.do">
                <input type="hidden" name="id" value="${help.id}"/>
                <div class="layui-form-item">
                    <label class="layui-form-label">上级教程<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <input id="parentId" name="parentId" value="${parentHelp.id}" hidden>
                        <c:if test="${empty help.id}">
                            <input type="text" class="layui-input w85" value="${parentHelp.title}" id="selectParent" readonly="readonly" onclick="clickParent()" />
                        </c:if>
                        <c:if test="${not empty help.id}">
                            <input type="text" class="layui-input w85" value="${parentHelp.title}" id="selectParent" readonly="readonly" />
                        </c:if>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">教程序号<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <c:if test="${empty help.id}">
                                <input type="text" name="serialNumber" lay-verify="serialNumber" autocomplete="off" class="layui-input" value="${sort}" id="serialNumber">
                            </c:if>
                            <c:if test="${not empty help.id}">
                                <input type="text" name="serialNumber" lay-verify="serialNumber" autocomplete="off" class="layui-input" value="${help.serialNumber}" id="serialNumber">
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">教程标题<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <input type="text" name="title" lay-verify="title" autocomplete="off" class="layui-input w85" value="${help.title}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">内容<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <script id="ueditor1" type="text/plain" style="width: 677px; height: 400px;"></script>
                        <div style="display: none" id="contentView">${help.content}</div>
                        <input type="hidden" name="content" id="content" lay-verify="content" />
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
                        <a href="javascript:history.back();" class="layui-btn layui-btn-primary">取消</a>
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
<script type="text/javascript">
    var ue = UE.getEditor('ueditor1');
    $(document).ready(function () {
        layui.use(['form'], function () {
            var form = layui.form();

            //自定义验证规则
            form.verify({
                serialNumber: function (value) {
                    var reg = /^(([1-9]{1,9})((\.[0-9]{1,2})?))$/;
                    if (value == "") {
                        return "请输入序号";
                    } else if (!reg.test(value)) {
                        return "请输入正确格式的序号";
                    }
                },
                title: function (value) {
                    if (value == "") {
                        return '请输入标题';
                    }
                },
                content: function (value) {
                    var content = $("#contentView").html();
                    if (content == "") {
                        return "请输入内容";
                    }
                }
            });

            //监听提交
            form.on('submit', function (data) {
                $(data.elem).removeAttr("lay-submit");
                $("#content").val(ue.getContent().replace(/&quot;/gi, ""));
                $.post('${ctx}/help/help/save.do', $('#myForm').serialize(), function (res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon: 1}, function (index) {
                            location.href = "${ctx}/help/help/list.do";
                        });
                    } else {
                        $(data.elem).attr("lay-submit","");
                        top.layer.msg(res.description, {icon: 2});
                    }
                });
                return false;
            });

            ue.addListener('ready', function() {
                if ($("#contentView").html() != "" && $("#contentView").html().length > 200) {
                    this.setHeight(550);
                }
                this.setContent($("#contentView").html());
            });
            ue.addListener('blur', function() {
                $("#contentView").html(ue.getContent());
            });
        });
    });

    function clickParent() {
        var id = $("#parentId").val() || '0';
        layer.open({
            type: 2,
            area: ["400px", "300px"],
            title: "选择父级",
            maxmin: true,
            content: "${ctx}/help/help/" + id + "/selectParent.do",
            btn: ['确定', '关闭'],
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var inputForm = body.find('#inputForm');
                var top_iframe;
                top_iframe = '_parent';
                inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示

                var parent = iframeWin.contentWindow.doSubmit();
                if (parent != false){
                    $('#parentId').val(parent.id);
                    $('#selectParent').val(parent.name);
                    callBack(parent.id, '#serialNumber');
                    setTimeout(function () {
                        top.layer.close(index);
                    }, 100);

                }
            },
            cancel: function (index) {
            }
        });
    }

    function callBack(parentId, container) {
        $.post("${ctx}/help/help/getMaxSort.do", {"parentId" : parentId || '0'}, function (res) {
            $(container).val(res.data);
        })
    }

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