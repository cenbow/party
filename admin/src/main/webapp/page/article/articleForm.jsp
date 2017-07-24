<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>发布文章</title>
	<%@include file="../include/commonFile.jsp" %>
	<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
	<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_form.css">
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
	<%@include file="../include/sidebar.jsp" %>
	<!--内容-->
	<section>
		<div class="section-main">
			<div class="c-time-list-header">
				<c:if test="${subjectApply == null}">
					<div class="r">
						<a href="${ctx}/article/article/articleList.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
					</div>
					<div class="ovh">
						<span class="title f20">文章管理&nbsp;&gt;&nbsp;${member == null ? '发布' : '编辑'}文章</span>
					</div>
				</c:if>
				<c:if test="${subjectApply != null}">
					<div class="r">
						<a href="${ctx}/subject/apply/applyList.do?subjectId=${subject.id}" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
					</div>
					<div class="ovh">
						<span class="title f20">专题管理&gt; ${subject.name} &gt; ${subjectApply.name} &gt; ${article == null ? '发布' : '编辑'}文章</span>
					</div>
				</c:if>
			</div>
			<!-- 正文请写在这里 -->
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/article/article/save.do">
				<input type="hidden" name="applyId" value="${subjectApply.id}"/> <input type="hidden" name="subjectId" value="${subjectId}"/>
				<div class="layui-form-item">
					<label class="layui-form-label">标题<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="title" lay-verify="title" style="width: 80%" autocomplete="off" placeholder="文章标题"
							   class="layui-input" value="${article.title}"> <input type="hidden" name="id" value="${article.id}"/>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">简介</label>
					<div class="layui-input-block">
						<input type="text" name="subTitle" style="width: 80%" autocomplete="off" placeholder="文章简介" class="layui-input"
							   value="${article.subTitle}">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">封面图<span class="f-verify-red">*</span></label>
					<div class="cover-content">
						<input type="hidden" name="pic" id="pic" lay-verify="pic" value="${article.pic}"/>
						<c:if test="${article == null || empty article.pic}">
							<span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
						</c:if>
						<c:if test="${article != null && not empty article.pic}">
							<span id="cover-img" class="cover-img" style="background-image:url('${article.pic}')"></span>
						</c:if>
						<div class="u-single-upload">
							<input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加封面图</span>
						</div>
					</div>
				</div>
				<c:if test="${empty channel.id && fn:length(channels) > 0}">
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">频道</label>
							<div class="layui-input-inline">
								<select name="channel.id">
									<option value="">请选择频道</option>
									<c:forEach var="channel" items="${channels}">
										<option value="${channel.id}" ${article.channel.id == channel.id ? 'selected="selected"' : ""}>${channel.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty channel.id}">
					<div class="layui-form-item">
						<label class="layui-form-label">频道</label>
						<div class="layui-input-block">
							<input type="text" style="width: 80%" class="layui-input" value="${channel.name}" readonly="readonly"/> <input
								type="hidden" value="${channel.id}" name="channelId"/>
						</div>
					</div>
				</c:if>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">文章类型</label>
						<div class="layui-input-inline">
							<select name="articleType">
								<option value="">请选择类型</option>
								<c:forEach var="type" items="${types}">
									<option value="${type.value}" ${article.articleType == type.value ? 'selected="selected"' : ""}>${type.label}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">是否热门</label>
						<div class="layui-input-inline">
							<input type="radio" name="isHot" value="1" title="是" ${article != null && article.isHot == '1' ? 'checked="checked"' : ''} />
							<input type="radio" name="isHot" value="0" title="否" ${article == null || article.isHot == '0' ? 'checked="checked"' : ''} />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">显示底部按钮</label>
					<div class="layui-input-block">
						<input type="radio" name="showApply" lay-filter="isShow" value="1" title="是" ${article != null && article.showApply == 1 ? 'checked="checked"' : ''} />
						<input type="radio" name="showApply" lay-filter="isShow" value="0" title="否" ${article == null || article.showApply == 0 ? 'checked="checked"' : ''} />
					</div>
				</div>
				<div class="layui-form-item" data-showApply="1" ${article == null || article.showApply == 0 ? 'style="display:none"' : ''}>
					<div class="layui-inline">
						<label class="layui-form-label">按钮名称<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" name="btnName" lay-verify="btnName" class="layui-input" value="${article.btnName}"/>
						</div>
					</div>
				</div>
				<div class="layui-form-item" data-showApply="1"  ${article == null || article.showApply == 0 ? 'style="display:none"' : ''}>
					<label class="layui-form-label">按钮链接<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="applyUrl" style="width: 80%" autocomplete="off" placeholder="按钮点击链接以http://开头" class="layui-input" lay-verify="applyUrl"
							   value="${article.applyUrl}">
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">阅读数</label>
						<div class="layui-input-inline">
							<input type="text" name="readNum" lay-verify="readNum" autocomplete="off" class="layui-input" value="${article.readNum}">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">排序</label>
						<div class="layui-input-inline">
							<input type="text" name="sort" lay-verify="sort" autocomplete="off" class="layui-input" value="${article.sort}">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">文章内容来源<span class="f-verify-red">*</span></label>
						<div class="layui-input-block">
							<input type="radio" name="type" lay-filter="type" lay-verify="type" value="local" title="本地发布"
							${article == null ||article.type == null|| article.type == ''|| article.type == 'local' ? 'checked="checked"' : ''}> <input type="radio" name="type"
																											 lay-filter="type" lay-verify="type" value="out" title="指向外部链接"
						${article != null && article.type == 'out' ? 'checked="checked"' : ''}>
						</div>
					</div>
				</div>
				<div class="layui-form-item" id="urlDiv" ${article != null && article.type == "out" ? '' : 'style="display: none;"'}>
					<label class="layui-form-label">链接<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="url" style="width: 80%" class="layui-input"  value="${article.url}" lay-verify="url" id="url"
							   placeholder="以http://开头"/>
					</div>
				</div>
				<div class="layui-form-item" id="localDiv" ${article == null || article.type == null || article.type == ''|| article.type == "local" ? '' : 'style="display: none;"'}>
					<div class="layui-inline">
						<label class="layui-form-label">内容<span class="f-verify-red">*</span></label>
						<div class="layui-input-block">
							<script id="ueditor1" type="text/plain"></script>
							<div style="display: none" id="contentView">${article.content}</div>
							<input type="hidden" name="content" id="content" lay-verify="content"/>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<c:if test="${subjectApply == null}">
							<a href="${ctx}/article/article/articleList.do" class="layui-btn layui-btn-primary">取消</a>
						</c:if>
						<c:if test="${subjectApply != null}">
							<a href="${ctx}/subject/apply/applyList.do?subjectId=${subject.id}" class="layui-btn layui-btn-primary">取消</a>
						</c:if>
					</div>
				</div>
			</form>
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
    var ue = UE.getEditor('ueditor1',{
        initialFrameWidth :800,//设置编辑器宽度
        initialFrameHeight:500,//设置编辑器高度
        scaleEnabled:true
    });



    $(function () {
        layui.use(['form'], function () {
            var form = layui.form(), laydate = layui.laydate;

            var typeRadioValue = null;

            form.on('radio(isShow)', function (data) {
                if (data.value == "1") {
                    $('[data-showApply=1]').show();
                } else {
                    $('[data-showApply=1]').hide();
                }
            });

            form.on('radio(type)', function (data) {
                typeRadioValue = data.value;
                if (typeRadioValue == "out") {
                    $("#urlDiv").show();
                    $("#localDiv").hide();
                } else if (typeRadioValue == "local") {
                    $("#urlDiv").hide();
                    $("#localDiv").show();
                }
            });

            //自定义验证规则
            form.verify({
                title: function (value) {
                    if (value == "") {
                        return '请填写文章标题';
                    }
                },
                pic: function (value) {
                    if (value == "") {
                        return "请上传文章封面图";
                    }
                },
                readNum: function (value) {
                    var reg = /^\d+$/g;
                    if (value != "") {
                        if (!reg.test(value)) {
                            return "只能是数字";
                        }
                    }
                },
                btnName: function (value) {
                    if($('[name=showApply]:checked').val() == 1){
                        if (value == "") {
                            return "请设置按钮名称";
                        }
                    }
                },
                applyUrl:function (value) {
                    if($('[name=showApply]:checked').val() == 1){
                        if (value == "") {
                            return "请设置按钮链接";
                        }
                    }
                },
                sort: function (value) {
                    if (!util.checkNumber(value)) {
                        return "请输入正确的数字";
                    }
                },
                url: function (value) {
                    if (typeRadioValue == null) {
                        var type = $("[name=type]:checked").val();
                        if (type == "url") {
                            if (value == "") {
                                return "请填写外部链接";
                            }
                        }
                    } else {
                        if (typeRadioValue == "out") {
                            if (value == "") {
                                return "请填写外部链接";
                            }
                        }
                    }
                },
                content: function (value) {
                    $("#contentView").html(ue.getContent());
                    var content = $("#contentView").html();
                    if (typeRadioValue == null) {
                        var type = $("[name=type]:checked").val();
                        if (type == "local") {
                            if (content == "") {
                                return "请填写文章内容";
                            }
                        }
                    } else {
                        if (typeRadioValue == "local") {
                            if (content == "") {
                                return "请填写文章内容";
                            }
                        }
                    }
                }
            });
            form.render(); //更新全部
            //监听提交
            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                $("#content").val(ue.getContent().replace(/&quot;/gi, ""));
                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            if (res.data != undefined) {
                                // 从频道来
                                if (res.data.channelId != undefined) {
                                    location.href = "${ctx}/article/article/articleList.do?channelId=" + res.data.channelId;
                                }
                                // 从专题来
                                if (res.data.subjectId != undefined) {
                                    location.href = "${ctx}/subject/apply/applyList.do?subjectId=" + res.data.subjectId + "&applyId=" + res.data.applyId;
                                }
                            } else {
                                // 直接发布文章
                                location.href = "${ctx}/article/article/articleList.do";
                            }
                        });
                    } else {
                        top.layer.msg('提交失败', {icon : 2});
                    }
                });
                return false;
            });

        });
        ue.addListener('ready', function () {
            this.setContent($("#contentView").html());
        });

        ue.addListener('blur', function () {
            $("#contentView").html(ue.getContent());
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
    // 文本编辑器图片上传
    function uEditorUploadCI(editor) {
        var opts = {
            type: 2,
            area: ['800px', '600px'],
            title: '选择图片上传',
            content: '${ctx}/fileupload/uploadCIImage.do?min=1',
            btn: ['确定', '关闭'],
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = layero.find('iframe')[0];
                var succFiles = iframeWin.contentWindow.getSucceFiles();
                if (iframeWin.contentWindow.doSubmit()) {
                    if (succFiles.length) {
                        for (var i = 0, item; i < succFiles.length; i++) {
                            item = succFiles[i];
                            editor.focus();
                            editor.execCommand('inserthtml', item.htmlStr);
                        }
                    }
                    iframeWin.contentWindow.closeUpload();
                }
            },
            cancel: function (index) {
            }
        };
        layer.open(opts);
    }
</script>
</body>
</html>