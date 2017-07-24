<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>小组管理</title>
    <%@include file="../include/commonFile.jsp"%>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_form.css">

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
                    <a href="${ctx}/sign/group/list.do?projectId=${signGroup.projectId}" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">小组管理&nbsp;&gt;&nbsp;${empty signGroup.id ? '创建' : '编辑'}队伍</span>
                </div>
            </div>
            <!-- 正文请写在这里 -->
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/sign/group/save.do">
                <input type="hidden" name="id" value="${signGroup.id}" />
                <input type="hidden" name="projectId" value="${signGroup.projectId}" />
                <div class="layui-form-item">
                    <label class="layui-form-label">队伍名称<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <input type="text" style="width: 85%" name="name" lay-verify="name" autocomplete="off" class="layui-input" value="${signGroup.name}">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">队伍备注</label>
                    <div class="layui-input-block">
                        <textarea name="remarks" placeholder="队伍备注" lay-verify="remarks" class="layui-textarea" style="width: 85%" >${signGroup.remarks}</textarea>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
                        <a href="${ctx}/sign/group/list.do?projectId=${signGroup.projectId}" class="layui-btn layui-btn-primary">取消</a>
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
                        return '请填写队伍名称';
                    }
                }
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                $.post('${ctx}/sign/group/save.do', $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/sign/group/list.do?projectId=${signGroup.projectId}";
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