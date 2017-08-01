<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>发布话题</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/static/uploadCI/upload.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_form.css">
<style type="text/css">
	.ci-img-list .item{
		width: 150px!important;
		height: 120px!important;
/* 		margin-bottom: 50px; */
		margin-bottom: 10px;
		margin-right: 10px;	
		float: left;
	}
	.ci-img-list .item .img-item{
		position: relative;
		width: 150px!important;
		height: 120px!important;
		margin-bottom: 0px!important;
		background-repeat: no-repeat;
		background-position: center;
		background-size: cover;
		cursor: pointer;
		float: none;
	}
	.ci-img-list .item .text-item{
		width: 148px;
		height: 30px;
		border: 1px solid #efefef;
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
					<a href="javascript:history.back();" class="layui-btn layui-btn-radius layui-btn-danger"> <i
						class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">${circle.name}&nbsp;&gt;&nbsp;话题管理&nbsp;&gt;&nbsp;${dynamic.id == null ? '发布' : '编辑'}话题</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<div class="add-form-content">
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/circle/topic/save.do">
				<input type="hidden" name="dyId" value="${dynamic.id}" />
				<input type="hidden" name="circle" value="${circle.id}" />
				<input type="hidden" name="dynamicWay" value="${dynamic.dynamicWay}" />
				<input type="hidden" name="createBy" value="${member.id}" />
				<c:if test="${member != null}">
				<div class="layui-form-item">
					<label class="layui-form-label">用户</label>
					<div class="layui-input-block">
						<span class="user-logo" style="background-image: url(${member.logo})"></span>
						<span class="user-name">${member.realname}</span>
					</div>
				</div>
				</c:if>

				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">话题标签</label>
						<div class="layui-input-inline">
							<c:if test="${fn:length(tags)>0}">
								<select name="topicTagId">
									<option value="">选择标签</option>
									<c:forEach var="tag" items="${tags}">
										<option value="${tag.id}" ${topic.topicTagId == tag.id?'selected="selected"' : ''}>${tag.name}</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${fn:length(tags)<=0}">
								<a href="${ctx}/circle/topicTag/list.do?circle=${circle.id}" class="layui-btn layui-btn-danger" lay-filter="">+添加标签</a>
							</c:if>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">话题图片<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="hidden" name="pics" id="picList" />
						<div class="ci-img-list" id="img_list">
							<c:forEach var="resource" items="${dypics}">
								<div class="item" data-url="${resource.picUrl}">
									<div class="img-item" style="background-image: url('${resource.picUrl}')">
										<i class="layui-icon del-icon" onclick="delQImg('${resource.picUrl}',this)">&#x1007;</i>
									</div>
								</div>
							</c:forEach>
						</div>
						<a href="javascript:openUploadCI()" class="layui-btn  layui-btn-danger">+添加图片</a>
					</div>
				</div>


				<div class="layui-form-item">
					<label class="layui-form-label">话题内容<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<textarea name="content" class="layui-textarea" style="resize: none" id="content">${dynamic.content}</textarea>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="javascript:history.back();" class="layui-btn layui-btn-primary">取消</a>
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
<script>
	var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');
	$(function () {
        layui.use([ 'form', 'laydate' ], function() {
            var form = layui.form(), laydate = layui.laydate;

            //监听提交
            form.on('submit', function(data) {
                var length = $("#img_list .img-item").length;
                var content = $("#content").val();
                if (length == 0) {
                    layer.msg("请上传话题图片");
                    return;
                }else if(length > 9){
                    layer.msg("最多上传9张图片，请删除多余图片");
                    return;
				}else if(content == ''){
                    layer.msg("请输入话题内容");
                    return;
				}
                $(data.elem).removeAttr("lay-submit");
                var picList = "";
                $("#img_list .item").each(function(index, element) {
                    picList += $(element).attr("data-url") + "|";
                });
                picList = picList != "" ? picList.substring(0, picList.length - 1) : "";

                $("#picList").val(picList);

                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href='${ctx}/circle/topic/list.do?circle=${circle.id}';
                        });
                    } else {
                        top.layer.msg('提交失败', {icon : 2});
                    }
                });
                return false;
            });
        });

    })

	function openUploadCI() {
		var opts = {
			type : 2,
			area : [ '800px', '600px' ],
			title : '选择图片上传',
			content : '${ctx}/fileupload/uploadCIImage.do?min=1&max=9',
			btn : [ '确定', '关闭' ],
			yes : function(index, layero) {
				var body = top.layer.getChildFrame('body', index);
				var iframeWin = layero.find('iframe')[0];
				var succFiles = iframeWin.contentWindow.getSucceFiles();
				if (iframeWin.contentWindow.doSubmit()) {
					if (succFiles.length) {
						for (var i = 0, item; i < succFiles.length; i++) {
							item = succFiles[i];
							$("#img_list").append(
									'<div class="item" data-url="'+ item.downloadUrl +'">' + '<div class="img-item" style="background-image: url('
											+ item.downloadUrl + ')">' + '<i class="layui-icon del-icon" onclick="delQImg(\'' + item.downloadUrl
											+ '\',this)">&#x1007;</i>' + '</div>' +
											'</div>');
						}
					}
					iframeWin.contentWindow.closeUpload();
				}
			},
			cancel : function(index) {
			}
		};
		layer.open(opts);
	}

	function delQImg(picUrl, obj) {
        var fileid = picUrl.replace(/(.*\/)*([^.]+).*/ig,"$2");
        var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');
        function cb(){
            uploadFile.delQcloudPhoto(fileid,function(ret){
                $(obj).closest('.item').remove();
            });
        }
        var dynamicId = $('input[name=dyId]').val();
        if(dynamicId == null || dynamicId == ''){
            cb();
        }else{
            $.ajax({
                type : 'post',
                url : '${ctx}/dynamic/dynamic/delDynamicPic.do',
                data : {
                    url:picUrl,
                    dynamicId:dynamicId
                },
                dataType : 'json',
                context : $('body'),
                success : function(data) {
                    cb();
                },
                error : function(xhr, type) {
                    console.err('err', 'ajax错误', xhr, type);
                }
            })
        }
	}
</script>
</body>
</html>