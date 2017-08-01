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
                    <a href="${ctx}/notify/message/listAll.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">消息管理&nbsp;&gt;&nbsp;短信内容</span>
                </div>
            </div>
            <!-- 正文请写在这里 -->
            <div class="add-form-content">
            <form id="myForm" class="layui-form mt20" method="post" >
                <div class="layui-form-item">
                    <label class="layui-form-label">短信内容</label>
                    <div class="layui-input-block">
                        <textarea name="title" placeholder="短信内容"  class="layui-textarea" readonly="readonly">${message.title}</textarea>
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
</script>
</body>
</html>