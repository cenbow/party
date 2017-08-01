<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>发布众筹项目</title>
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
					<a href="${ctx}/activity/activity/zcActivityList.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">众筹项目管理&nbsp;&gt;&nbsp;${activity == null ? '发布' : '编辑'}众筹项目</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<div class="add-form-content">
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/activity/activity/save.do">
				<div class="layui-form-item">
					<label class="layui-form-label">活动标题<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="title" lay-verify="title" autocomplete="off"
							placeholder="活动标题" class="layui-input" value="${activity.title}"
						>
						<input type="hidden" name="id" value="${activity.id}" />
						<input type="hidden" name="isCrowdfunded" value="${activity.isCrowdfunded == null ? 1 : activity.isCrowdfunded}" />
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">发布者<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<c:if test="${empty activity.publisher}">
								<input type="text" name="publisher" lay-verify="publisher" autocomplete="off" class="layui-input" value="${sessionScope.currentUser.realname}">
							</c:if>
							<c:if test="${not empty activity.publisher}">
								<input type="text" name="publisher" lay-verify="publisher" autocomplete="off" class="layui-input" value="${activity.publisher}">
							</c:if>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">头像<span class="f-verify-red">*</span></label>
					<div class="cover-content">
						<c:if test="${activity == null || empty activity.publisherLogo}">
							<input type="hidden" name="publisherLogo" id="publisherLogo" lay-verify="publisherLogo" value="${sessionScope.currentUser.logo}" />
							<span id="logo-img" class="round-img" style="background-image:url(${sessionScope.currentUser.logo})"></span>
						</c:if>
						<c:if test="${activity != null && not empty activity.publisherLogo}">
							<input type="hidden" name="publisherLogo" id="publisherLogo" lay-verify="publisherLogo" value="${activity.publisherLogo}" />
							<span id="logo-img" class="round-img" style="background-image:url('${activity.publisherLogo}')"></span>
						</c:if>
						<div class="u-single-upload">
							<input type="file" id="upload_logo_img" class="u-single-file"> <span class="u-single-upload-icon">+添加头像</span>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">海报<span class="f-verify-red">*</span></label>
					<div class="cover-content">
						<input type="hidden" name="pic" id="pic" lay-verify="pic" value="${activity.pic}" />
						<c:if test="${activity == null || empty activity.pic}">
							<span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
						</c:if>
						<c:if test="${activity != null && not empty activity.pic}">
							<span id="cover-img" class="cover-img" style="background-image:url('${activity.pic}')"></span>
						</c:if>
						<div class="u-single-upload">
							<input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加海报</span>
						</div>
						<div class="form-word-aux">建议尺寸：800x450</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">报名截止时间<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input class="layui-input" name="endDate" lay-verify="endDate" placeholder="报名截止时间" id="endTime"
								value='<fmt:formatDate value="${activity.endTime}" pattern="yyyy-MM-dd HH:mm" />'
							/>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">活动开始时间<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input class="layui-input" name="startDate" lay-verify="startDate" placeholder="活动开始时间" id="startTime"
								value='<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm" />'
							/>
						</div>
					</div>
				</div>
<!-- 				<div class="layui-form-item"> -->
<!-- 					<div class="layui-inline"> -->
<!-- 						<label class="layui-form-label">活动类型<span class="f-verify-red">*</span></label> -->
<!-- 						<div class="layui-input-inline"> -->
<!-- 							<select name="activityType" lay-verify="activityType"> -->
<!-- 								<option value="">请选择类型</option> -->
<%-- 								<c:forEach var="type" items="${types}"> --%>
<%-- 									<option value="${type.value}" ${activity.activityType == type.value ? 'selected="selected"' : ""}>${type.label}</option> --%>
<%-- 								</c:forEach> --%>
<!-- 							</select> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">人数上限<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" name="limitNum" lay-verify="limitNum" autocomplete="off" class="layui-input"
								value="${activity.limitNum}"
							>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">活动金额<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" name="price" lay-verify="price" placeholder="￥" class="layui-input" value="${activity.price}" ${activity != null ? ' readonly="readonly"' : ''} />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">众筹事项</label>
						<div class="layui-input-inline">
							<select name="eventId" id="eventId" lay-verify="eventId" lay-filter="eventId">
								<option value=" ">无事件</option>
								<c:forEach var="event" items="${crowdfundEventList}">
									<option value="${event.id}" ${activity.eventId == event.id ? 'selected="selected"' : ""}>${event.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">消息提示<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="radio" name="crowdfundHintSwitch" value="0" title="否" ${activity == null || activity.crowdfundHintSwitch == 0 ? 'checked="checked"' : ''}>
						<input type="radio" name="crowdfundHintSwitch" value="1" title="是" ${activity != null && activity.crowdfundHintSwitch == 1 ? 'checked="checked"' : ''}>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">隐藏数据<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="radio" name="templateStyle" value="0" title="否" ${activity == null || activity.templateStyle == 0 ? 'checked="checked"' : ''}>
						<input type="radio" name="templateStyle" value="1" title="是" ${activity != null && activity.templateStyle == 1 ? 'checked="checked"' : ''}>
					</div>
				</div>
				<%@include file="./cityAreaMap.jsp" %>
<!-- 				<div class="layui-form-item"> -->
<!-- 					<div class="layui-inline"> -->
<!-- 						<label class="layui-form-label">经度<span class="f-verify-red">*</span></label> -->
<!-- 						<div class="layui-input-inline"> -->
<%-- 							<input class="layui-input" name="lng" lay-verify="lng" value='${activity.lng}'/> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="layui-form-item"> -->
<!-- 					<div class="layui-inline"> -->
<!-- 						<label class="layui-form-label">纬度<span class="f-verify-red">*</span></label> -->
<!-- 						<div class="layui-input-inline"> -->
<%-- 							<input class="layui-input" name="lat" lay-verify="lat" value='${activity.lat}'/> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div id="mapDiv" class="layui-form-item" style="display: none;">
					<div class="layui-inline">
						<label class="layui-form-label"></label>
						<div class="layui-input-inline">
							<div id="allmap" style="height: 500px;"></div>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">众筹宣言<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="crowdDeclaration" lay-verify="crowd_xy" autocomplete="off" placeholder="众筹宣言" class="layui-input" value="${activity.crowdDeclaration}">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">支持宣言<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="supportDeclaration" lay-verify="support_xy" autocomplete="off" placeholder="支持宣言" class="layui-input" value="${activity.supportDeclaration}">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">代言宣言<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="representDeclaration" lay-verify="represent_xy" autocomplete="off" placeholder="代言宣言" class="layui-input" value="${activity.representDeclaration}">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">活动描述</label>
					<div class="layui-input-block">
						<input type="text" name="remarks"  placeholder="活动描述" class="layui-input" value="${activity.remarks}">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">跑马灯图片</label>
					<div class="layui-input-block">
						<input type="hidden" name="picList" id="picList" />
						<div class="ci-img-list" id="img_list">
							<c:forEach var="resource" items="${activity.picList}">
								<div class="item" data-url="${resource.resourceUrl}">
									<div class="img-item" style="background-image: url('${resource.resourceUrl}')">
										<i class="layui-icon del-icon" onclick="delQImg('${resource.resourceUrl}',this, '${resource.id}')">&#x1007;</i>
									</div>
<%-- 									<input class="text-item" type="text" value="${resource.remarks}" placeholder="图片描述" /> --%>
								</div>
							</c:forEach>
						</div>
						<a href="javascript:openUploadCI()" class="layui-btn  layui-btn-danger">+添加图片</a>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">视频</label>
					<div class="layui-input-block">
						<input type="hidden" name="videoList" id="videoList" />
						<div style="margin-bottom: 10px;">
							<textarea class="layui-textarea video-link" style="width: calc(100% - 140px);float: left; margin-right: 10px;resize:none" placeholder="视频链接" id="vide_code">${activity.video.resourceUrl}</textarea>
<%-- 							<input type="text" style="width: 63%;float: left; margin-right: 10px;" placeholder="视频链接" class="layui-input video-link" value="${activity.video.resourceUrl}" id="vide_code"> --%>
							<a href="javascript:openUploadVideo()" class="layui-btn  layui-btn-danger">+添加视频提示</a>
							<div class="cl"></div>
						</div>
						<input type="text"  placeholder="视频描述" class="layui-input video-remarks" value="${activity.video.remarks}" >
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">排序号</label>
						<div class="layui-input-inline">
							<input type="text" name="sort" lay-verify="sort" autocomplete="off" class="layui-input" value="${activity.sort}" >
						</div>
					</div>
				</div>
				<div class="layui-form-item">
						<label class="layui-form-label">活动详情<span class="f-verify-red">*</span></label>
						<div class="layui-input-block">
							<script id="ueditor1" type="text/plain" style="width: 100%; height: 500px;"></script>
							<div style="display: none" id="contentView">${activityDetail.content}</div>
							<input type="hidden" name="content" id="content" lay-verify="content" />
						</div>
				</div>
				<div class="layui-form-item">
						<label class="layui-form-label">报名相关<span class="f-verify-red">*</span></label>
						<div class="layui-input-block">
							<script id="relatedUeditor" type="text/plain" style="width: 100%; height: 500px;"></script>
							<div style="display: none" id="applyRelatedView">${activityDetail.applyRelated}</div>
							<input type="hidden" name="applyRelated" id="applyRelated" lay-verify="applyRelated" />
						</div>
				</div>
				<div class="layui-form-item">
						<label class="layui-form-label">参赛标准<span class="f-verify-red">*</span></label>
						<div class="layui-input-block">
							<script id="standardUeditor" type="text/plain" style="width: 100%; height: 500px;"></script>
							<div style="display: none" id="matchStandardView">${activityDetail.matchStandard}</div>
							<input type="hidden" name="matchStandard" id="matchStandard" lay-verify="matchStandard" />
						</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/activity/activity/zcActivityList.do" class="layui-btn layui-btn-primary">取消</a>
					</div>
				</div>
			</form>
			</div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
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
	var ue = UE.getEditor('ueditor1');
	var relatedUeditor = UE.getEditor('relatedUeditor');
	var standardUeditor = UE.getEditor('standardUeditor');



	$(function() {
        layui.use([ 'form', 'laydate' ], function() {
            var form = layui.form(), laydate = layui.laydate;

            //自定义验证规则
            form.verify({
                title : function(value) {
                    if (value == "") {
                        return '请填写活动标题';
                    }
                },
                publisher : function(value) {
                    if (value == "") {
                        return "请填写发布者";
                    }
                },
                publisherLogo : function(value) {
                    if (value == "") {
                        return "请上传发布者头像";
                    }
                },
                pic : function(value) {
                    if (value == "") {
                        return "请上传活动海报";
                    }
                },
                startDate : function(value) {
                    if (value == "") {
                        return "请设置活动开始时间";
                    }
                },
                endDate : function(value) {
                    if (value == "") {
                        return "请设置报名截止时间";
                    }
                    var startDate = $("[name=startDate]").val();
                    var endDate = $("[name=endDate]").val();
                    if (endDate >= startDate) {
                        return "报名截止时间应该早于活动开始时间";
                    }
                },
                place : function(value) {
                    if (value == "") {
                        return "请设置活动场所";
                    }
                },
                area : function(value) {
                    if (value == "") {
                        return "请设置活动区域";
                    }
                },
// 			lng : function(value){
// 				if(value == ""){
// 					return "请填写经度"
// 				}
// 			},
// 			lat : function(value){
// 				if(value == ""){
// 					return "请填写纬度"
// 				}
// 			},
                city : function(value) {
                    if (value == "") {
                        return "请选择活动举办的城市";
                    }
                },
                limitNum : function(value) {
                    if (value == "") {
                        return "请设置活动人数上限";
                    } else if (!util.checkNumber(value)) {
                        return "请输入正确的数字";
                    } else if (parseInt(value) == 0) {
                        return "请输入大于0的数字";
                    }
                },
                price : function(value) {
                    var reg = /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
                    if(value == "") {
                        return "请设置活动报名金额";
                    }else if(!reg.test(value)){
                        return "请输入正确的金额";
                    }else if(parseFloat(value) == 0){
                        return "请输入大于0的金额";
                    }
                },
                sort : function(value) {
                    if(!util.checkNumber(value)){
                        return "请输入正确的数字";
                    }
                },
                content : function(value) {
                    $("#contentView").html(ue.getContent());
                    var content = $("#contentView").html();
                    if (content == "") {
                        return "请填写活动详情";
                    }
                },
                matchStandard : function (value) {
                    $("#matchStandardView").html(standardUeditor.getContent());
                    var content = $("#matchStandardView").html();
                    if (content == "") {
                        return "请填写参赛标准";
                    }
                },
                applyRelated : function (value) {
                    $("#applyRelatedView").html(relatedUeditor.getContent());
                    var content = $("#applyRelatedView").html();
                    if (content == "") {
                        return "请填写报名相关";
                    }
                }
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                $("#content").val(ue.getContent().replace(/&quot;/gi, ""));
                $("#applyRelated").val(relatedUeditor.getContent().replace(/&quot;/gi, ""));
                $("#matchStandard").val(standardUeditor.getContent().replace(/&quot;/gi, ""));
                // 跑马灯
                var length = $("#img_list .img-item").length;
                if(length > 5){
                    layer.msg("最多只允许上传5张图片");
                    return false;
                }
                var array = new Array();
                $("#img_list .item").each(function(index, element){
                    var pic = {
                        pic : $(element).attr("data-url"),
                        remarks : $(element).find(".text-item").val() || ''
                    }
                    array.push(pic);
                });
                $("#picList").val(JSON.stringify(array));
                // 视频
                var video = {
                    pic : $(".video-link").val(),
                    remarks : $(".video-remarks").val()
                }
                $("#videoList").val(JSON.stringify(video));

                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/activity/activity/zcActivityList.do";
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
            initMapForm(form);
            setTimeout(function () {
                form.render('radio');
            },100);
        });
		ue.addListener('ready', function() {
			if($("#contentView").html() != "" && $("#contentView").html().length > 500){
				this.setHeight(850);
			}
			this.setContent($("#contentView").html());
		});
		ue.addListener('blur', function() {
			$("#contentView").html(ue.getContent());
		});

		relatedUeditor.addListener('ready', function() {
			this.setContent($("#applyRelatedView").html());
			if($("#applyRelatedView").html() != "" && $("#applyRelatedView").html().length > 500){
				this.setHeight(500);
			}
		});
		relatedUeditor.addListener('blur', function() {
			$("#applyRelatedView").html(ue.getContent());
		});

		standardUeditor.addListener('ready', function() {
			this.setContent($("#matchStandardView").html());
			if($("#matchStandardView").html() != "" && $("#matchStandardView").html().length > 500){
				this.setHeight(500);
			}
		});
		standardUeditor.addListener('blur', function() {
			$("#matchStandardView").html(ue.getContent());
		});

		$('#upload_single_img').change(function() {
			if (util.isValid(this.value)) {
				uploadFile.uploadSinglePhoto(this.files[0], function(ret) {
					console.log('回调：' + ret);
					$('#cover-img').css('background-image', 'url(' + ret.data.download_url + ')');
					$('#pic').val(ret.data.download_url);
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
	})
	// 文本编辑器图片上传
	function uEditorUploadCI(editor) {
		var opts = {
			type : 2,
			area : [ '800px', '600px' ],
			title : '选择图片上传',
			content : '${ctx}/fileupload/uploadCIImage.do?min=1',
			btn : [ '确定', '关闭' ],
			yes : function(index, layero) {
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
			cancel : function(index) {
			}
		};
		layer.open(opts);
	}
	
	function openUploadCI() {
		var opts = {
			type : 2,
			area : [ '800px', '600px' ],
			title : '选择图片上传',
			content : '${ctx}/fileupload/uploadCIImage.do?min=1&max=5',
			btn : [ '确定', '关闭' ],
			yes : function(index, layero) {
				var body = top.layer.getChildFrame('body', index);
				var iframeWin = layero.find('iframe')[0];
				var succFiles = iframeWin.contentWindow.getSucceFiles();
				if (iframeWin.contentWindow.doSubmit()) {
					if (succFiles.length) {
						for (var i = 0, item; i < succFiles.length; i++) {
							item = succFiles[i];
							$("#img_list").append('<div class="item" data-url="'+ item.downloadUrl +'">'+
									'<div class="img-item" style="background-image: url('+ item.downloadUrl +')">'+
									'<i class="layui-icon del-icon" onclick="delQImg(\''+ item.downloadUrl +'\',this, \'\')">&#x1007;</i>'+
									'</div>'+
// 									'<input class="text-item" type="text" placeholder="图片描述" />'+
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

	function delQImg(picUrl, obj, resourceId) {
		var fileid = picUrl.replace(/(.*\/)*([^.]+).*/ig, "$2");
		var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');
		function cb() {
			uploadFile.delQcloudPhoto(fileid, function(ret) {
				$(obj).closest(".item").remove();
			});
		}
        if(resourceId == null || resourceId == ''){
            cb();
        }else{
            $.ajax({
                type : 'post',
                url : '${ctx}/activity/activity/delZcActivityPic.do',
                data : {
                	resourceId : resourceId
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
	
	function openUploadVideo(){
		var opts = {
			type : 2,
			area : [ '450px', '450px' ],
			title : '添加视频',
			content : '${ctx}/page/upload/myvideo.html',
			btn : [ '确定', '关闭' ],
			yes : function(index, layero) {
				var body = top.layer.getChildFrame('body', index);
				var iframeWin = layero.find('iframe')[0];
				if (iframeWin.contentWindow.doSubmit()) {
					var video = $(iframeWin.contentWindow.$.find("#video_code")).val();
					$("#vide_code").val(video);
					setTimeout(function() {
						top.layer.close(index);
					}, 100);//延时0.1秒，对应360 7.1版本bug
				}
			},
			cancel : function(index) {
			}
		};
		layer.open(opts);
	}
</script>
</body>
</html>