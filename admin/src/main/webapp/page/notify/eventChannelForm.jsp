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
                    <a href="${ctx}/notify/channel/listForEven.do?eventId=${eventChannel.eventId}" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">消息事件&nbsp;&gt;&nbsp;${eventChannel == null ? '发布' : '编辑'}通道配置</span>
                </div>
            </div>
            <!-- 正文请写在这里 -->
            <div class="add-form-content">
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/notify/eventChannel/save.do">
                <input type="hidden" name="id" value="${eventChannel.id}" />
                <div class="layui-form-item">
                    <label class="layui-form-label">开关</label>
                    <div class="layui-input-block">
                        <input type="radio" name="channelSwitch" lay-filter="channelSwitch"  value="0" title="关闭"
                        ${eventChannel == null || eventChannel.channelSwitch ==0 ? 'checked="checked"' : ''}
                        > <input type="radio" name="channelSwitch" lay-filter="channelSwitch"  value="1" title="开启"
                    ${eventChannel != null && eventChannel.channelSwitch == 1 ? 'checked="checked"' : ''}
                    >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">写入开关</label>
                    <div class="layui-input-block">
                        <input type="radio" name="writeSwitch" lay-filter="writeSwitch"  value="0" title="关闭"
                        ${eventChannel == null || eventChannel.writeSwitch ==0 ? 'checked="checked"' : ''}
                        > <input type="radio" name="writeSwitch" lay-filter="writeSwitch"  value="1" title="开启"
                    ${eventChannel != null && eventChannel.writeSwitch == 1 ? 'checked="checked"' : ''}
                    >
                    </div>
                </div>


                <c:if test="${channel.code != 'wechat'}">
                    <div class="layui-form-item">
                        <label class="layui-form-label">模板配置<span class="f-verify-red">*</span></label>
                        <div class="layui-input-block">
                            <textarea name="template" placeholder="模板配置" lay-verify="template" class="layui-textarea">${eventChannel.template}</textarea>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">模板配置说明</label>

                        <p class="pt10">{code} 表示 验证码，{order} 表示订单 ，{verCode} 表示核销码，{phone} 表示联系电话, {address} 表示地址，
                            {user} 表示发布者，{project}表示众筹项目，{dataNum} 表示截止时间，{favorerNum} 表示支持人数，{actualAmount} 表示已筹集资金</p>
                    </div>
                </c:if>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/notify/channel/listForEven.do?eventId=${eventChannel.eventId}" class="layui-btn layui-btn-primary">取消</a>
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
                template : function(value) {
                    if (value == "") {
                        return '请填写模板内容';
                    }
                },
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                $.post('${ctx}/notify/eventChannel/save.do', $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/notify/channel/listForEven.do?eventId=${eventChannel.eventId}";
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