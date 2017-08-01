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
                    <a href="${ctx}/notify/event/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">消息事件&nbsp;&gt;&nbsp;${event == null ? '发布' : '编辑'}事件</span>
                </div>
            </div>
            <!-- 正文请写在这里 -->
            <div class="add-form-content">
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/notify/event/save.do">
                <input type="hidden" name="id" value="${event.id}" />
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">事件名称<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input type="text" name="name" lay-verify="name" autocomplete="off" class="layui-input"
                                   value="${event.name}"
                            >
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">事件代码<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input type="text" name="code" lay-verify="code" autocomplete="off" class="layui-input"
                                   value="${event.code}"
                            >
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">事件类型<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <select name="type" lay-verify="type">
                                <option value="">请选择类型</option>
                                <c:forEach var="type" items="${types}">
                                    <option value="${type.key}" ${event.type == type.key ? 'selected="selected"' : ""}>${type.value}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">开关</label>
                    <div class="layui-input-block">
                        <input type="radio" name="msgSwitch" lay-filter="msgSwitch"  value="0" title="关闭"
                        ${event == null || event.msgSwitch ==0 ? 'checked="checked"' : ''}
                        > <input type="radio" name="msgSwitch" lay-filter="msgSwitch"  value="1" title="开启"
                    ${event != null && event.msgSwitch == 1 ? 'checked="checked"' : ''}
                    >
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">启动方式</label>
                    <div class="layui-input-block">
                        <input type="radio" name="way" lay-filter="way"  value="hand" title="手动"
                        ${event == null || event.way == 'hand'? 'checked="checked"' : ''}
                        > <input type="radio" name="way" lay-filter="way"  value="auto" title="自动"
                    ${event != null && event.way == 'auto' ? 'checked="checked"' : ''}
                    >
                    </div>
                </div>


                <div <c:if test="${event.way == 'hand'}"> hidden </c:if> id="job">
                    <div class="layui-form-item">
                        <label class="layui-form-label">是否异步</label>
                        <div class="layui-input-block">
                            <input type="radio" name="isSync" lay-filter="isSync"  value="0" title="否"
                            ${event == null || event.isSync == '0'? 'checked="checked"' : ''}
                            > <input type="radio" name="isSync" lay-filter="isSync"  value="1" title="是"
                           ${event != null && event.isSync == '1' ? 'checked="checked"' : ''}
                        >
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">时间表达式<span class="f-verify-red">*</span></label>
                            <div class="layui-input-inline">
                                <input type="text" name="cronExpression" lay-verify="cronExpression" autocomplete="off" class="layui-input"
                                       value="${event.cronExpression}"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">类名<span class="f-verify-red">*</span></label>
                            <div class="layui-input-inline">
                                <input type="text" name="className" lay-verify="className" autocomplete="off" class="layui-input" value="${event.className}"
                                       <c:if test="${not empty event.className}">readonly="readonly"</c:if>
                                       >
                            </div>

                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">方法名<span class="f-verify-red">*</span></label>
                            <div class="layui-input-inline">
                                <input type="text" name="method" lay-verify="method" autocomplete="off" class="layui-input" value="${event.method}"
                                       <c:if test="${not empty event.method}">readonly="readonly"</c:if>
                                >
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/notify/event/list.do" class="layui-btn layui-btn-primary">取消</a>
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
            var way = '${event.way}';

            form.on('radio(way)', function(data) {
                var value = data.value;
                way = value;
                if (value == "hand") {
                    $('#job').hide();
                } else if (value == "auto") {
                    $('#job').show();
                }
            });

            //自定义验证规则
            form.verify({
                name : function(value) {
                    if (value == "") {
                        return '请填写事件名称';
                    }
                },
                code : function(value) {
                    if (value == "") {
                        return "请填写事件代码";
                    }
                },
                cronExpression : function(value) {
                    if (way == 'auto' && value == "") {
                        return "请填写时间表达式";
                    }
                },
                className : function(value) {
                    if (way == 'auto' && value == "") {
                        return "请填写时间表达式";
                    }
                },
                method : function(value) {
                    if (way == 'auto' && value == "") {
                        return "请填写时间表达式";
                    }
                },
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                $.post('${ctx}/notify/event/save.do', $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/notify/event/list.do";
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