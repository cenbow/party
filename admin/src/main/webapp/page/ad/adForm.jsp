<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>${ad == null ?'发布':'编辑'}广告</title>
    <%@include file="../include/commonFile.jsp" %>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/ad/publish_form.css">
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp" %>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/ad/list.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
                </div>
                <div class="ovh">
                    <span class="title f20">广告管理&nbsp;&gt;&nbsp;${member == null ? '发布' : '编辑'}广告</span>
                </div>
            </div>
            <!-- 正文请写在这里 -->
            <div class="add-form-content">
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/ad/save.do">
                <input type="hidden" name="id" value="${ad.id}"/>
                <div class="layui-form-item">
                    <label class="layui-form-label">标题<span class="f-verify-red"></span></label>
                    <div class="layui-input-block">
                        <input type="text" name="title" autocomplete="off" placeholder="请输入标题"
                               class="layui-input" value="${ad.title}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">封面图<span class="f-verify-red">*</span></label>
                    <div class="cover-content">
                        <input type="hidden" name="pic" id="pic" lay-verify="pic" value="${ad.pic}"/>
                        <c:if test="${ad == null || empty ad.pic}">
                            <span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
                        </c:if>
                        <c:if test="${ad != null && not empty ad.pic}">
                            <span id="cover-img" class="cover-img" style="background-image:url('${ad.pic}')"></span>
                        </c:if>
                        <div class="u-single-upload">
                            <input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加广告图片</span>
                        </div>
                        <div class="form-word-aux">建议尺寸：800x450</div>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">广告来源<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <input type="radio" name="origin" value="0" lay-filter="origin" title="外部广告"
                        ${ad == null ||ad.origin == null || ad.origin == 0 ? 'checked="checked"' : ''}
                        >
                        <input type="radio" name="origin" value="1" lay-filter="origin" title="内部广告"
                        ${ad != null && ad.origin == 1 ? 'checked="checked"' : ''}
                        >
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">广告位置<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <select name="adPos" lay-filter="adPos"  lay-verify="adPos">
                                <option value="">请选择</option>
                                <c:forEach var="type" items="${adTypes}">
                                    <option value="${type.value}" ${ad.adPos == type.value ? 'selected="selected"' : ""}>${type.label}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item" adPos="5" <c:if test="${ad.adPos != 5}">style="display: none;"</c:if>>
                    <div class="layui-inline">
                        <label class="layui-form-label">播放秒数<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input type="number" name="playSecond" lay-verify="playSecond" autocomplete="off" class="layui-input" value="${ad != null && ad.playSecond != null ? ad.playSecond : 3}">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">排序号</label>
                        <div class="layui-input-inline">
                            <input type="number" name="sort" lay-verify="sort" autocomplete="off" class="layui-input" value="${ad.sort}">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item" adOrigin="1" <c:if test="${ad == null || ad.origin != 1}">style="display: none;"</c:if>>
                    <div class="layui-inline">
                        <label class="layui-form-label">内部广告类型<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <select name="tag"  lay-filter="tag">
                                <option value="">请选择</option>
                                <c:forEach var="type" items="${adTags}">
                                    <option value="${type.value}" ${ad.tag == type.value ? 'selected="selected"' : ""}>${type.label}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item" adOrigin="1" <c:if test="${ad == null || ad.origin != 1}">style="display: none;"</c:if>>
                    <label class="layui-form-label">
                        广告类型ID
                        <span class="f-verify-red">*</span>
                    </label>
                    <div class="layui-input-block">
                        <input type="text" name="refId"  lay-verify="refId" autocomplete="off" class="layui-input" value="${ad.refId}">
                    </div>
                </div>
                <div class="layui-form-item" adOrigin="0" <c:if test="${ad.origin != null && ad.origin != '0'}">style="display: none;"</c:if>>
                    <label class="layui-form-label">
                        广告链接地址<span class="f-verify-red">*</span>
                    </label>
                    <div class="layui-input-block">
                        <input type="text" name="link" lay-verify="link"  autocomplete="off" class="layui-input" value="${ad.link}" placeholder="以http://开头">
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/ad/list.do" class="layui-btn layui-btn-primary">取消</a>
                    </div>
                </div>
            </form>
            </div>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/myplugin/uploadCI.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/resize.js"></script>
<script>
    var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');




    $(function () {  layui.use(['form'], function () {
        var form = layui.form(), laydate = layui.laydate;

        form.on('radio(origin)', function (data) {
            var origin = data.value;
            var disOrigin = origin == 0 ? 1 : 0;
            $('[adOrigin=' + origin + ']').show();
            $('[adOrigin=' + disOrigin + ']').hide();
        });
        form.on('select(adPos)', function (data) {
            var pos = data.value;
            if(pos == 5){
                $('[adPos='+5+']').show();
            }else{
                $('[adPos='+5+']').hide();
            }
        });

        //自定义验证规则
        form.verify({
            pic: function (value) {
                if (value == "") {
                    return "请上传文章封面图";
                }
            },
            adPos: function (value) {
                if (value == "") {
                    return "请选择广告位置";
                }
            },
            playSecond: function (value) {
                if (value == "" && $('[name=adPos]').val() == 5) {
                    return "请输入播放秒数";
                }
            },
            tag: function (value) {
                if (value == "" && $('[name=origin]:checked').val() == 1) {
                    return "请选择内部广告类型";
                }
            },
            refId: function (value) {
                if (value == "" && $('[name=origin]:checked').val() == 1) {
                    return "请填写ID";
                }
            },
            link: function (value) {
                if (value == "" && $('[name=origin]:checked').val() == 0) {
                    return "请填写外部广告链接地址";
                }
            }
        });

        //监听提交
        form.on('submit', function (data) {
            $(data.elem).removeAttr("lay-submit");
            var action = $("#myForm").attr("action");
            $.post(action, $('#myForm').serialize(), function (res) {
                if (res.success) {
                    top.layer.msg('提交成功', {icon : 1}, function(index){
                        location.href = "${ctx}/ad/list.do";
                    });
                } else {
                    top.layer.msg('提交失败', {icon : 2});
                }
            });
            return false;
        });
    });
        $('#upload_single_img').change(function () {
            if (util.isValid(this.value)) {
                uploadFile.uploadSinglePhoto(this.files[0], function (ret) {
                    console.log('回调：' + ret);
                    $('#cover-img').css('background-image', 'url(' + ret.data.download_url + ')');
                    $('#pic').val(ret.data.download_url);
                    $('#upload_single_img').val('');
                });
            }
        });
    })

</script>
</body>
</html>