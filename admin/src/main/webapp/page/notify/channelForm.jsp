<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>消息管理</title>
    <%@include file="../include/commonFile.jsp"%>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_form.css">
    <style>
        .layui-form-label{
            width: 120px!important
        }
    </style>
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
                    <a href="${ctx}/notify/channel/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">消息通道&nbsp;&gt;&nbsp;${event == null ? '发布' : '编辑'}通道</span>
                </div>
            </div>
            <!-- 正文请写在这里 -->
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/notify/event/save.do">
                <input type="hidden" name="id" value="${channel.id}" />
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">通道名称<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input type="text" name="name" lay-verify="name" autocomplete="off" class="layui-input"
                                   value="${channel.name}"
                            >
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">通道代码<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input type="text" name="code" lay-verify="code" autocomplete="off" class="layui-input"
                                   value="${channel.code}"
                            >
                        </div>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/notify/channel/list.do" class="layui-btn layui-btn-primary">取消</a>
                    </div>
                </div>
            </form>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script>
    $(function () {
        layui.use([ 'form', 'laydate' ], function() {
            var form = layui.form();

            //自定义验证规则
            form.verify({
                name : function(value) {
                    if (value == "") {
                        return '请填写通道名称';
                    }
                },
                code : function(value) {
                    if (value == "") {
                        return "请填写通道代码";
                    }
                }
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                $.post('${ctx}/notify/channel/save.do', $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/notify/channel/list.do";
                        });
                    } else {
                        top.layer.msg(res.description, {icon : 2});
                    }
                });
                return false;
            });

        });
    })



</script>
</body>
</html>