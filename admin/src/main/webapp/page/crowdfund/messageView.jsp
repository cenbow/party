<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<html>
<head>
    <title>消息推送</title>
    <%@include file="../include/commonFile.jsp"%>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_form.css">

</head>
<body>
<!--头部-->
<%@include file="../include/header.jsp"%>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp"%>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/notify/message/list.do?targetId=${targetTemplate.targetId}" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">众筹管理&nbsp;&gt;&nbsp;消息推送</span>
                </div>
            </div>

            <!-- 正文请写在这里 -->
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/crowdfund/target/send.do">
                <input type="hidden" name="id" value="${targetTemplate.id}" />
                <input type="hidden" name="targetId" value="${targetTemplate.targetId}" />

                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">消息类型</label>
                        <div class="layui-input-inline">
                            <select name="type" id="type" lay-verify="type" lay-filter="type">
                                <c:forEach var="type" items="${types}">
                                    <option value="${type.key}" ${type.key == targetTemplate.type ? 'selected="selected"' : ""}>${type.value}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">模板配置<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <textarea name="template" placeholder="模板配置" lay-verify="template" class="layui-textarea" style="width: 85%" >${targetTemplate.template}</textarea>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">模板配置说明</label>
                    <p class="pt10">{user} 表示众筹者, {project} 表示众筹名称, {favorerNum} 表示支持人数，{actualAmount} 表示筹集资金</p>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">发送</a>
                        <a href="${ctx}/notify/message/list.do?targetId=${targetTemplate.targetId}" class="layui-btn layui-btn-primary">取消</a>
                    </div>
                </div>
            </form>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript">
    var laytpl = null;
    var laypage = null;
    var element = null;
    var form = null;
    $(function () {
        layui.use([ 'laytpl', 'laypage', 'element', 'form'], function() {
            laytpl = layui.laytpl;
            laypage = layui.laypage;
            element = layui.element();
            form = layui.form();

            form.on('select(type)', function(data) {
                console.log(data);
                window.location.href = "${ctx}/crowdfund/target/messageView.do?targetId=${targetTemplate.targetId}&type="+data.value;
            });

            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                $.post('${ctx}/crowdfund/target/send.do', $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('发送成功', {icon : 1}, function(index){
                            window.location.href = "${ctx}/crowdfund/target/messageView.do?targetId=${targetTemplate.targetId}&type=${targetTemplate.type}";
                        });
                    } else {
                        top.layer.msg(res.description, {icon : 2});
                    }
                    $(data.elem).attr('lay-submit','');
                });
                return false;
            });
        });
    })



</script>
</body>
</html>
