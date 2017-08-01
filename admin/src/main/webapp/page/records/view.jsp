<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>${signProject == null ? '发布' : '编辑'}打开项目</title>
    <%@include file="../include/commonFile.jsp"%>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_act.css">
    <style type="text/css">
        .place-text {
            width: 200px;
            float: left;
            margin-right: 10px;
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
                    <a href="${ctx}/records/records/list.do?targetId=${upRecords.targetId}" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">众筹管理&nbsp;&gt;&nbsp;${empty upRecords.id ? '发布' : '编辑'}跟进记录</span>
                </div>
            </div>
            <div class="add-form-content">
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/records/records/save.do">
                <input type="hidden" name="id" value="${upRecords.id}" />
                <div class="layui-form-item">
                    <label class="layui-form-label">众筹者<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <input type="hidden" name="targetId" value="${upRecords.targetId}" />
                        <input type="text"   class="layui-input" value="${authorName}" readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">图像</label>
                    <div class="cover-content">
                        <input type="hidden" name="pic" id="pic" lay-verify="pic" value="${upRecords.pic}" />
                        <c:if test="${upRecords == null || empty upRecords.pic}">
                            <span id="logo-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
                        </c:if>
                        <c:if test="${upRecords != null && not empty upRecords.pic}">
                            <span id="logo-img" class="cover-img" style="background-image:url('${upRecords.pic}')"></span>
                        </c:if>
                        <div class="u-single-upload">
                            <input type="file" id="upload_logo_img" class="u-single-file"> <span class="u-single-upload-icon">+添加图像</span>
                        </div>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">内容<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <textarea name="content" id="content" placeholder="内容" lay-verify="content" class="layui-textarea" >${upRecords.content}</textarea>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
                        <a href="${ctx}/records/records/list.do?targetId=${upRecords.targetId}" class="layui-btn layui-btn-primary">取消</a>
                    </div>
                </div>
            </form>
            </div>
        </div>
    </section>
</div>

<!--底部-->
<%@include file="../include/footer.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/resize.js"></script>

<script>
    var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');
    layui.use([ 'form', 'laydate' ], function() {
        var form = layui.form(), laydate = layui.laydate;


        //自定义验证规则
        form.verify({
            content : function (value) {
                if (value == ""){
                    return '请填写跟进内容';
                }
            }
        });

        //监听提交
        form.on('submit', function(data) {
            $(data.elem).removeAttr("lay-submit");
            var action = $("#myForm").attr("action");
            $.post(action, $('#myForm').serialize(), function(res) {
                if (res.success) {
                    top.layer.msg('提交成功', {icon : 1}, function(index){
                        location.href = "${ctx}/records/records/list.do?targetId=${upRecords.targetId}&activityId=${activityId}&eventId=${eventId}";
                    });
                } else {
                    top.layer.msg('提交失败', {icon : 2});
                }
            });
            return false;
        });
    });

    $(function() {

        $('#upload_logo_img').change(function() {
            if (util.isValid(this.value)) {
                uploadFile.uploadSinglePhoto(this.files[0], function(ret) {
                    console.log('回调：' + ret);
                    $('#logo-img').css('background-image', 'url(' + ret.data.download_url + ')');
                    $('#pic').val(ret.data.download_url);
                    $('#upload_logo_img').val('');
                });
            }
        });
    })
</script>
</body>
</html>