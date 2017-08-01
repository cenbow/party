<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>权限管理</title>
    <%@include file="../../include/commonFile.jsp"%>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_form.css">
    <style>
        .layui-form-label{
            width: 120px!important
        }
    </style>
</head>
<!--头部-->
<%@include file="../../include/header.jsp"%>
<div class="index-outside">
    <%@include file="../../include/sidebar.jsp"%>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/system/privilege/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">权限管理&nbsp;&gt;&nbsp;${privilege.id == null ? '新增' : '编辑'}权限</span>
                </div>
            </div>
            <!-- 正文请写在这里 -->
            <div class="add-form-content">
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/system/privilege/save.do">
                <input type="hidden" name="id" value="${privilege.id}" />
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">上级权限<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input id="parentId" name="parentId" value="${privilege.parentId}" hidden>
                            <input type="text" data-id = "${privilege.parentId}" autocomplete="off" class="layui-input"
                                   value="${privilege.parentName}" id="parent">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">权限名称<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input type="text" name="name" lay-verify="name" autocomplete="off" class="layui-input"
                                   value="${privilege.name}"
                            >
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">权限代码<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input type="text" name="permission" lay-verify="permission" autocomplete="off" class="layui-input"
                                   value="${privilege.permission}"
                            >
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">权限类型<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <select name="type" lay-verify="type">
                                <option value="">请选择类型</option>
                                <c:forEach var="type" items="${types}">
                                    <option value="${type.key}" ${privilege.type == type.key ? 'selected="selected"' : ""}>${type.value}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">排序<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sort" lay-verify="sort" autocomplete="off" class="layui-input"
                                   value="${privilege.sort}"
                            >
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
                        <a href="${ctx}/system/privilege/list.do" class="layui-btn layui-btn-primary">取消</a>
                    </div>
                </div>
            </form>
            </div>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../../include/footer.jsp"%>
<script>



    $(document).ready(function() {
        layui.use([ 'form', 'laydate' ], function() {
            var form = layui.form();

            //自定义验证规则
            form.verify({
                name : function(value) {
                    if (value == "") {
                        return '请填写权限名称';
                    }
                },
                code : function(value) {
                    if (value == "") {
                        return "请填写权限代码";
                    }
                }
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                $.post('${ctx}/system/privilege/save.do', $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/system/privilege/list.do";
                        });
                    } else {
                        top.layer.msg(res.description, {icon : 2});
                    }
                });
                return false;
            });

        });
        $('#parent').on('click', function () {
            var id = $(this).data("id");
            layer.open({
                type : 2,
                area : [ "500px", "500px" ],
                title : "父级权限",
                maxmin : true,
                content : "${ctx}/system/privilege/select.do?id="+id,
                btn : [ '确定', '关闭' ],
                yes : function(index, layero) {
                    var body = layer.getChildFrame('body', index);
                    var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    var inputForm = body.find('#inputForm');
                    var top_iframe;
                    top_iframe = '_parent';
                    inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示


                    var parent = iframeWin.contentWindow.doSubmit();
                    $('#parentId').val(parent.id);
                    $('#parent').val(parent.name);
                    setTimeout(function() {
                        top.layer.close(index);
                    }, 100);
                },
                cancel : function(index) {
                }
            });
        })
    });
</script>
</body>
</html>