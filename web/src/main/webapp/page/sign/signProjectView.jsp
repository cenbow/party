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
                    <a href="${ctx}/sign/project/list.do" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">我发布的活动&nbsp;&gt;&nbsp;${empty signProject.id ? '发布' : '编辑'}打卡项目</span>
                </div>
            </div>
            <div class="add-form-content">
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/sign/project/save.do">
                <div class="layui-form-item">
                    <label class="layui-form-label">活动标题<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <input type="text" name="title" lay-verify="title"  placeholder="活动标题" class="layui-input" value="${signProject.title}">
                        <input type="hidden" name="id" value="${signProject.id}" />
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">发布者<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <c:if test="${empty signProject.publisher}">
                                <input type="text" name="publisher" lay-verify="signProject" autocomplete="off" class="layui-input" value="${sessionScope.currentUser.realname}">
                            </c:if>
                            <c:if test="${not empty signProject.publisher}">
                                <input type="text" name="publisher" lay-verify="signProject" autocomplete="off" class="layui-input" value="${signProject.publisher}">
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">发布者头像<span class="f-verify-red">*</span></label>
                    <div class="cover-content">
                        <c:if test="${signProject == null || empty signProject.publisherLogo}">
                            <input type="hidden" name="publisherLogo" id="publisherLogo" lay-verify="publisherLogo" value="${sessionScope.currentUser.logo}" />
                            <span id="logo-img" class="round-img" style="background-image:url(${sessionScope.currentUser.logo})"></span>
                        </c:if>
                        <c:if test="${signProject != null && not empty signProject.publisherLogo}">
                            <input type="hidden" name="publisherLogo" id="publisherLogo" lay-verify="publisherLogo" value="${signProject.publisherLogo}" />
                            <span id="logo-img" class="round-img" style="background-image:url('${signProject.publisherLogo}')"></span>
                        </c:if>
                        <div class="u-single-upload">
                            <input type="file" id="upload_logo_img" class="u-single-file"> <span class="u-single-upload-icon">+添加头像</span>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">项目海报<span class="f-verify-red">*</span></label>
                    <div class="cover-content">
                        <input type="hidden" name="projectLogo" id="projectLogo" lay-verify="projectLogo" value="${signProject.projectLogo}" />
                        <c:if test="${signProject == null || empty signProject.projectLogo}">
                            <span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
                        </c:if>
                        <c:if test="${signProject != null && not empty signProject.projectLogo}">
                            <span id="cover-img" class="cover-img" style="background-image:url('${signProject.projectLogo}')"></span>
                        </c:if>
                        <div class="u-single-upload">
                            <input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加项目海报</span>
                        </div>
                        <div class="form-word-aux">建议尺寸：800x450</div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">个人主页海报<span class="f-verify-red">*</span></label>
                    <div class="cover-content">
                        <input type="hidden" name="signLogo" id="signLogo" lay-verify="signLogo" value="${signProject.signLogo}" />
                        <c:if test="${signProject == null || empty signProject.signLogo}">
                            <span id="sign-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
                        </c:if>
                        <c:if test="${signProject != null && not empty signProject.signLogo}">
                            <span id="sign-img" class="cover-img" style="background-image:url('${signProject.signLogo}')"></span>
                        </c:if>
                        <div class="u-single-upload">
                            <input type="file" id="upload_sign_img" class="u-single-file"> <span class="u-single-upload-icon">+添加主页海报</span>
                        </div>
                        <div class="form-word-aux">建议尺寸：800x450</div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">开始日期<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input class="layui-input" name="startStrTime" lay-verify="startDate" placeholder="活动开始时间" id="startTime"
                                   value='<fmt:formatDate value="${signProject.startTime}" pattern="yyyy-MM-dd HH:mm" />' />
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">截止日期<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input class="layui-input" name="endStrTime" lay-verify="endDate" placeholder="报名截止时间" id="endTime"
                                   value='<fmt:formatDate value="${signProject.endTime}" pattern="yyyy-MM-dd HH:mm" />' />
                        </div>
                    </div>
                </div>


                <div class="layui-form-item">
                    <label class="layui-form-label">需要审核</label>
                    <div class="layui-input-block">
                        <input type="radio" name="applyCheck" lay-filter="applyCheck" id="isApplyCheck" value="1" title="审核"
                        ${signProject.applyCheck ==1 ? 'checked="checked"' : ''}>
                        <input type="radio" name="applyCheck" lay-filter="applyCheck" id="noApplyCheck" value="0" title="不审核"
                        ${signProject.applyCheck == 0 ? 'checked="checked"' : ''}>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">显示小组排行</label>
                    <div class="layui-input-block">
                        <input type="radio" name="rankShow" lay-filter="rankShow" id="isRankShow" value="1" title="显示"
                        ${signProject.rankShow ==1 ? 'checked="checked"' : ''}>
                        <input type="radio" name="rankShow" lay-filter="rankShow" id="noRankShow" value="0" title="不显示"
                        ${signProject.rankShow == 0 ? 'checked="checked"' : ''}>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">是否收费</label>
                    <div class="layui-input-block">
                        <input type="radio" name="isFree" lay-filter="isFree" id="noFree" value="noFree" title="收费活动"
                        ${signProject == null || signProject.price !=0.0 ? 'checked="checked"' : ''}>
                        <input type="radio" name="isFree" lay-filter="isFree" id="free" value="free" title="免费活动"
                        ${signProject != null && signProject.price == 0.0 ? 'checked="checked"' : ''}>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">活动金额<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <c:if test="${signProject == null || signProject.price != 0.0}">
                                <input type="text" name="price" lay-verify="price" placeholder="￥" class="layui-input" value="${signProject.price}" />
                            </c:if>
                            <c:if test="${signProject != null && signProject.price == 0.0}">
                                <input type="text" name="price" lay-verify="price" placeholder="￥" class="layui-input" disabled="disabled" />
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">项目描述</label>
                    <div class="layui-input-block">
                        <input type="text" name="remarks"  placeholder="项目描述" class="layui-input" value="${signProject.remarks}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">规则<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <textarea name="rule" id="rule" placeholder="规则" lay-verify="rule" class="layui-textarea" >${signProject.rule}</textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
                        <a href="${ctx}/sign/project/list.do" class="layui-btn layui-btn-primary">取消</a>
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


    $(function() {
        layui.use([ 'form', 'laydate' ], function() {
            var form = layui.form(), laydate = layui.laydate;
            var radioValue = null;

            form.on('radio(isFree)', function(data) {
                radioValue = data.value;
                if (radioValue == "noFree") {
                    $("[name=price]").prop("disabled", false);
                } else {
                    $("[name=price]").val("");
                    $("[name=price]").prop("disabled", true);
                }
            });

            //自定义验证规则
            form.verify({
                title : function(value) {
                    if (value == "") {
                        return '请填写项目标题';
                    }
                },
                publisher : function (value) {
                    if (value == ""){
                        return '请填写发布者';
                    }
                },
                publisherLogo : function (value) {
                    if (value == ""){
                        return '请填写发布者头像' ;
                    }
                },
                projectLogo : function(value) {
                    if (value == "") {
                        return '请上传项目海报';
                    }
                },
                signLogo : function (value) {
                    if (value == ""){
                        return '请填写个人主页海报';
                    }
                },
                startDate : function(value) {
                    if (value == "") {
                        return "请设置开始时间";
                    }
                },
                endDate : function(value) {
                    if (value == "") {
                        return "请设置截止时间";
                    }
                    var startDate = $("[name=startDate]").val();
                    var endDate = $("[name=endDate]").val();
                    if (endDate <= startDate) {
                        return "截止时间应该小于开始时间";
                    }
                },
                place : function(value) {
                    if (value == "") {
                        return "请设置活动场所";
                    }
                },
                price : function(value) {
                    var reg = /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
                    if (radioValue == null) {
                        var type = $("[name=isFree]:checked").val();
                        if (type == "noFree") {
                            if (value == "") {
                                return "请设置活动报名金额";
                            } else if (!reg.test(value)) {
                                return "请输入正确的金额";
                            }
                        }
                    } else {
                        if (radioValue == "noFree") {
                            if (value == "") {
                                return "请设置活动报名金额";
                            } else if (!reg.test(value)) {
                                return "请输入正确的金额";
                            }
                        }
                    }
                },
                rule : function (value) {
                    if (value == ""){
                        return '请填写项目规则';
                    }
                }
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#myForm").attr("action");
                $('textarea[name=rule]').val(util.textareaToHtml($('#rule').val()));
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/sign/project/list.do";
                        });
                    } else {
                        top.layer.msg('提交失败', {icon : 2});
                    }
                });
                return false;
            });

            //日期
            var start = {
                min : '2015-01-01 00:00:00',
                max : '2099-06-16 23:59',
                istoday : false,
                choose : function(datas) {

                },
                istime : true, //是否开启时间选择
                format : 'YYYY-MM-DD hh:mm'
            };

            var end = {
                min : '2015-01-01 00:00:00',
                max : '2099-06-16 23:59',
                istoday : false,
                choose : function(datas) {

                },
                istime : true, //是否开启时间选择
                format : 'YYYY-MM-DD hh:mm' //日期格式
            };

            document.getElementById('startTime').onclick = function() {
                start.elem = this;
                laydate(start);
            }
            document.getElementById('endTime').onclick = function() {
                end.elem = this
                laydate(end);
            }
            setTimeout(function () {
                form.render('radio');
            },100);
        });
        $('#rule').val(util.htmlToTextarea($('textarea[name=rule]').val()));

        $('#upload_single_img').change(function() {
            if (util.isValid(this.value)) {
                uploadFile.uploadSinglePhoto(this.files[0], function(ret) {
                    console.log('回调：' + ret);
                    $('#cover-img').css('background-image', 'url(' + ret.data.download_url + ')');
                    $('#projectLogo').val(ret.data.download_url);
                    $('#upload_single_img').val('');
                });
            }
        });
        $('#upload_logo_img').change(function() {
            if (util.isValid(this.value)) {
                uploadFile.uploadSinglePhoto(this.files[0], function(ret) {
                    console.log('回调：' + ret);
                    $('#logo-img').css('background-image', 'url(' + ret.data.download_url + ')');
                    $('#publisherLogo').val(ret.data.download_url);
                    $('#upload_logo_img').val('');
                });
            }
        });
        $('#upload_sign_img').change(function() {
            if (util.isValid(this.value)) {
                uploadFile.uploadSinglePhoto(this.files[0], function(ret) {
                    console.log('回调：' + ret);
                    $('#sign-img').css('background-image', 'url(' + ret.data.download_url + ')');
                    $('#signLogo').val(ret.data.download_url);
                    $('#upload_sign_img').val('');
                });
            }
        });
    })
</script>
</body>
</html>