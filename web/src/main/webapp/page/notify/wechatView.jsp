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
                    <a href="${ctx}/notify/wechat/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">消息关联&nbsp;&gt;&nbsp;${message == null ? '发布' : '编辑'}微信模板消息</span>
                </div>
            </div>
            <!-- 正文请写在这里 -->
            <div class="add-form-content">
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/notify/eventChannel/save.do">
                <input type="hidden" name="id" value="${message.id}" />

                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">事件名称<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <select name="eventChannelId" lay-verify="eventChannelId">
                                <option value="">请选择事件</option>
                                <c:forEach var="channel" items="${channelList}">
                                    <option value="${channel.id}" ${message.eventChannelId == channel.id ? 'selected="selected"' : ""}>${channel.eventName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">消息编号<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <input type="text" style="width: 85%" name="templateId" lay-verify="templateId" autocomplete="off" class="layui-input" value="${message.templateId}">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">消息连接<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <textarea name="url" placeholder="消息连接" lay-verify="msgurl" class="layui-textarea" style="width: 85%" >${message.url}</textarea>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">消息题目<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <textarea name="first" placeholder="消息题目" lay-verify="first" class="layui-textarea" style="width: 85%" >${message.first}</textarea>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">消息备注<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <textarea name="remark" placeholder="消息备注" lay-verify="remark" class="layui-textarea" style="width: 85%" >${message.remark}</textarea>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
                        <a href="${ctx}/notify/wechat/list.do" class="layui-btn layui-btn-primary">取消</a>
                    </div>
                </div>
            </form>
            </div>
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
                eventChannelId : function(value) {
                    if (value == "") {
                        return '请选择事件名称';
                    }
                },
                templateId : function(value) {
                    if (value == "") {
                        return '请填写消息编号';
                    }
                },
                msgurl : function(value) {
                    if (value == "") {
                        return '请填写消息连接';
                    }
                },
                first : function(value) {
                    if (value == "") {
                        return '请填写消息题目';
                    }
                },
                remark : function(value) {
                    if (value == "") {
                        return '请填写消息备注';
                    }
                }
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                $.post('${ctx}/notify/wechat/save.do', $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/notify/wechat/list.do";
                        });
                    } else {
                        top.layer.msg(res.description, {icon : 2});
                    }
                });
                return false;
            });
//        form.render();
        });
    })



</script>
</body>
</html>