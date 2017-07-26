<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>众筹分析状态</title>
    <%@include file="../include/commonFile.jsp"%>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_form.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/lable/lable.css">
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
                    <a href="${ctx}/label/label/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">众筹管理&nbsp;&gt;&nbsp;${label.id == null ? '发布' : '编辑'}分析状态</span>
                </div>
            </div>
            <!-- 正文请写在这里 -->
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/label/label/save.do">
                <input type="hidden" name="id" value="${label.id}" />
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">状态名称<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input type="text" name="name" lay-verify="name" autocomplete="off" class="layui-input"
                                   value="${label.name}"
                            >
                        </div>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">风格<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <input name="style" value="blue" lay-verify="style" id="style" type="hidden">
                        <ul class="styles-body pt5">
                            <li class="white  <c:if test="${empty label.style || label.style == 'white'}">c-active</c:if>"></li>
                            <li class="blue  <c:if test="${label.style == 'blue'}">c-active</c:if>"></li>
                            <li class="darkblue <c:if test="${label.style == 'darkblue'}">c-active</c:if>"></li>
                            <li class="green <c:if test="${label.style == 'green'}">c-active</c:if>"></li>
                            <li class="orange <c:if test="${label.style == 'orange'}">c-active</c:if>"></li>
                            <li class="purple <c:if test="${label.style == 'purple'}">c-active</c:if>"></li>
                            <li class="pink <c:if test="${label.style == 'pink'}">c-active</c:if>"></li>
                            <li class="red <c:if test="${label.style == 'red'}">c-active</c:if>"></li>
                            <li class="gray <c:if test="${label.style == 'gray'}">c-active</c:if>"></li>
                            <li class="brown <c:if test="${label.style == 'brown'}">c-active</c:if>"></li>
                        </ul>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
                        <a href="${ctx}/label/label/list.do" class="layui-btn layui-btn-primary">取消</a>
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
                        return '请填写分析状态名称';
                    }
                }
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                $.post('${ctx}/label/label/save.do', $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/label/label/list.do";
                        });
                    } else {
                        top.layer.msg(res.description, {icon : 2});
                    }
                });
                return false;
            });

            $('.styles-body li').click(function (e) {
                if(!$(this).hasClass('c-active')){
                    $('.styles-body .c-active').removeClass('c-active');
                    style = this.className;
                    $("#style").val(style.trim());
                    $(this).addClass('c-active');
                }
            })
        });
    })



</script>
</body>
</html>