<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>发布专题栏目</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_act.css">
<style type="text/css">
.layui-form-radio {
	display: block !important;
}

.text-place {
	padding-left: 30px;
	color: #aaa;
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
					<a href="${ctx}/subject/apply/applyList.do?subjectId=${subject.id}" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">我发布的专题 &gt; ${subject.name} &gt; ${apply == null ? '发布' : '编辑'}栏目</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/subject/apply/save.do">
				<div class="layui-form-item">
					<label class="layui-form-label">专题</label>
					<div class="layui-input-block">
						<input type="text" style="width: 85%" class="layui-input" value="${subject.name}" readonly="readonly" /> <input
							type="hidden" name="subjectId" value="${subject.id}" />
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">栏目名称<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="name" lay-verify="name" style="width: 85%" autocomplete="off" placeholder="栏目名称"
							class="layui-input" value="${apply.name}"> <input type="hidden" name="id" value="${apply.id}" />
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">栏目图标<span class="f-verify-red">*</span></label>
					<div class="cover-content">
						<input type="hidden" name="picture" id="pic" lay-verify="pic" value="${apply.picture}" />
						<c:if test="${apply == null || empty apply.picture}">
							<span class="cover-img" style="background-image:url(${ctx}/image/posterImg.png);width:120px;"></span>
						</c:if>
						<c:if test="${apply != null && not empty apply.picture}">
							<span class="cover-img" style="background-image:url('${apply.picture}');width:120px;"></span>
						</c:if>
						<div class="u-single-upload">
							<input type="file" class="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加栏目图标</span>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">栏目类型<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="radio" name="type" lay-filter="applyType" value="url" title="链接"
							${apply == null || apply.type == 'url' ? 'checked="checked"' : ''} /> <span class="text-place">可以链接到一个固定的网页链接地址</span> <input
							type="radio" name="type" lay-filter="applyType" value="article" title="单篇文章"
							${apply != null && apply.type == 'article' ? 'checked="checked"' : ''} /> <span class="text-place">可以指向一篇文章内容 </span> <input
							type="radio" name="type" lay-filter="applyType" value="channel" title="文章列表"
							${apply != null && apply.type == 'channel' ? 'checked="checked"' : ''} /> <span class="text-place">可以添加多篇文章内容</span>
					</div>
				</div>
				<!-- 链接 -->
				<div class="layui-form-item" id="type_url"
					${apply == null || apply.type == 'url' ? 'style="display:block"' : 'style="display:none"'}>
					<label class="layui-form-label">链接<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="url" lay-verify="url" style="width: 85%" autocomplete="off" placeholder="单链接" class="layui-input"
							value="${apply.url}" />
					</div>
				</div>
				<!-- 频道名称 -->
				<%-- 				<div class="layui-form-item" id="type_channel_name" ${apply != null && apply.type == 'channel' ? 'style="display:block"' : 'style="display:none"'}> --%>
				<!-- 					<label class="layui-form-label">頻道名称<span class="f-verify-red">*</span></label> -->
				<!-- 					<div class="layui-input-block"> -->
				<%-- 						<input type="text" name="channelName" lay-verify="channelName" style="width: 85%" autocomplete="off" placeholder="频道名称" class="layui-input" value="${channel.name}"> --%>
				<%-- 						<input type="hidden" name="channelId" value="${channel.id}"/> --%>
				<!-- 					</div> -->
				<!-- 				</div> -->
				<!-- 频道封面图 -->
				<div class="layui-form-item" id="type_channel_pic"
					${apply != null && apply.type == 'channel' ? 'style="display:block"' : 'style="display:none"'}>
					<label class="layui-form-label">列表封面图</label>
					<div class="cover-content">
						<input type="hidden" name="channelPicture" lay-verify="channelPic" value="${channel.picture}" />
						<c:if test="${channel == null || empty channel.picture}">
							<span class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
						</c:if>
						<c:if test="${channel != null && not empty channel.picture}">
							<span class="cover-img" style="background-image:url('${channel.picture}')"></span>
						</c:if>
						<div class="u-single-upload">
							<input type="file" class="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加封面图</span>
						</div>
					</div>
				</div>
				<div class="layui-form-item" ${apply == null ? 'style="display:none"' : ''}>
					<div class="layui-inline">
						<label class="layui-form-label">排序号</label>
						<div class="layui-input-inline">
							<input type="text" name="sort" lay-verify="sort" autocomplete="off" class="layui-input" value="${apply.sort}" >
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">描述</label>
					<div class="layui-input-block">
						<textarea name="remarks" placeholder="栏目描述" lay-verify="remarks" class="layui-textarea" style="width: 85%">${apply.remarks}</textarea>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/subject/apply/applyList.do?subjectId=${subject.id}" class="layui-btn layui-btn-primary">取消</a>
					</div>
				</div>
			</form>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script>
	var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');
	var radioValue = null;


	$(function() {
        layui.use([ 'form' ], function() {
            var form = layui.form(), laydate = layui.laydate;

            form.on('radio(applyType)', function(data) {
                radioValue = data.value;
                if (radioValue == "url") { // 链接
                    $("#type_url").show();
                    $("#type_channel_name").hide();
                    $("#type_channel_pic").hide();
                } else if (radioValue == "article") { // 文章
                    $("#type_url").hide();
                    $("type_url [type=text]").val("");
                    $("#type_channel_name").hide();
                    $("#type_channel_pic").hide();
                } else if (radioValue == "channel") { // 频道
                    $("#type_url").hide();
                    $("type_url [type=text]").val("");
                    $("#type_channel_name").show();
                    $("#type_channel_pic").show();
                }
            });

            //自定义验证规则
            form.verify({
                name : function(value) {
                    if (value == "") {
                        return '请填写栏目名称';
                    }
                },
                pic : function(value) {
                    if (value == "") {
                        return "请上传栏目图标";
                    }
                },
                url : function(value) {
                    if (radioValue == null) {
                        var type = $("[name=type]:checked").val();
                        if (type == "url" && value == "") {
                            return "请设置栏目链接";
                        }
                    } else {
                        if (radioValue == "url" && value == "") {
                            return "请设置栏目链接";
                        }
                    }
                },
                channelName : function(value) {
                    if (radioValue == "channel" && value == "") {
                        return "请设置频道名称";
                    }
                }
// 			,
// 			channelPic : function(value) {
// 				if (radioValue != null) {
// 					if (radioValue == "channel" && value == "") {
// 						return "请设置列表封面图";
// 					}
// 				} else {
// 					var type = $("[name=type]:checked").val();
// 					if (type == "channel" && value == "") {
// 						return "请设置列表封面图";
// 					}
// 				}
// 			}
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/subject/apply/applyList.do?subjectId=" + res.data.subjectId + "&applyId=" + res.data.applyId;
                        });
                    } else {
                        if(res.description != undefined){
                            layer.msg('当前排序号超出栏目数量,请重新填写',{
                                icon : 7
                            })
                            return false;
                        }
                        layer.msg('提交失败', {icon : 2});
                    }
                });
                return false;
            });
        });
		$('.upload_single_img').change(function() {
			var target = $(this);
			if (util.isValid(this.value)) {
				uploadFile.uploadSinglePhoto(this.files[0], function(ret) {
					console.log('回调：' + ret);
					$(target).closest(".cover-content").find(".cover-img").css('background-image', 'url(' + ret.data.download_url + ')');
					$(target).closest(".cover-content").find("[type=hidden]").val(ret.data.download_url);
					$(target).val('');
				});
			}
		});
	})
</script>
</body>
</html>