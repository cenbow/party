<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>发布活动</title>
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
					<a href="${ctx}/activity/activity/activityList.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">活动管理&nbsp;&gt;&nbsp;${activity == null ? '发布' : '编辑'}活动</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/activity/activity/save.do">
				<div class="layui-form-item">
					<label class="layui-form-label">活动标题<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="title" lay-verify="title" style="width: 80%" autocomplete="off"
							placeholder="活动标题" class="layui-input" value="${activity.title}"
						>
						<input type="hidden" name="id" value="${activity.id}" />
						<input type="hidden" name="isCrowdfunded" value="${activity.isCrowdfunded == null ? 0 : activity.isCrowdfunded}" />
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
						<div class="form-word-aux">建议尺寸：600*350</div>
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
							<div id="allmap" style="height: 500px; width: 800px;"></div>
						</div>
					</div>
				</div>
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
					<label class="layui-form-label">报名费用</label>
					<div class="layui-input-block">
						<input type="radio" name="isFree" lay-filter="isFree" id="noFree" value="noFree" title="收费活动"
							${activity == null || activity.price !=0.0 ? 'checked="checked"' : ''}
						> <input type="radio" name="isFree" lay-filter="isFree" id="free" value="free" title="免费活动"
							${activity != null && activity.price == 0.0 ? 'checked="checked"' : ''}
						>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">活动金额<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<c:if test="${activity == null || activity.price != 0.0}">
								<input type="text" name="price" lay-verify="price" placeholder="￥" class="layui-input" value="${activity.price}" />
							</c:if>
							<c:if test="${activity != null && activity.price == 0.0}">
								<input type="text" name="price" lay-verify="price" placeholder="￥" class="layui-input" disabled="disabled" />
							</c:if>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">微网站置顶效果<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="radio" name="micWebStatus" value="1" title="是"
							${activity == null || activity.micWebStatus == 1 ? 'checked="checked"' : ''}
						>
						<input type="radio" name="micWebStatus" value="0" title="否"
							${activity != null && activity.micWebStatus == 0 ? 'checked="checked"' : ''}
						>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">持邀请码参与<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="radio" name="inviteOnly" lay-filter="isInvite" id="invite_yes" value="1" title="是"
							${activity != null && activity.inviteOnly == 1 ? 'checked="checked"' : ''}
						>
						<input type="radio" name="inviteOnly" lay-filter="isInvite" id="invite_no" value="0" title="否"
							${activity == null || activity.inviteOnly == 0 ? 'checked="checked"' : ''}
						>
					</div>
				</div>
				<div class="layui-form-item" id="inviteCodeDiv" ${empty activity.id || activity.inviteOnly == 0 ? 'style="display:none"' : ''}>
					<div class="layui-inline">
						<label class="layui-form-label">邀请码<span class="f-verify-red">*</span></label> 
						<div class="layui-input-inline">
							<input type="text" name="inviteCode" lay-verify="inviteCode" class="layui-input" value="${activity.inviteCode}" />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">隐藏报名人员<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="radio" name="joinHidden" value="1" title="是"
							${activity == null || activity.joinHidden == 1 ? 'checked="checked"' : ''}
						>
						<input type="radio" name="joinHidden" value="0" title="否"
							${activity != null && activity.joinHidden == 0 ? 'checked="checked"' : ''}
						>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">显示在前端<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="radio" name="showFront" value="1" title="是"
							${activity == null || activity.showFront == 1 ? 'checked="checked"' : ''}
						>
						<input type="radio" name="showFront" value="0" title="否"
							${activity != null && activity.showFront == 0 ? 'checked="checked"' : ''}
						>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">在其他城市显示<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="radio" name="isAllowedOutside" value="1" title="是"
							${activity != null && activity.isAllowedOutside == 1 ? 'checked="checked"' : ''}
						>
						<input type="radio" name="isAllowedOutside" value="0" title="否"
							${activity == null || activity.isAllowedOutside == 0 ? 'checked="checked"' : ''}
						>
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
					<label class="layui-form-label">活动描述</label>
					<div class="layui-input-block">
						<input type="text" name="remarks" style="width: 80%" placeholder="活动描述" class="layui-input" value="${activity.remarks}">
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">活动详情<span class="f-verify-red">*</span></label>
						<div class="layui-input-block">
							<script id="ueditor1" type="text/plain" style="width: 800px; height: 500px;"></script>
							<div style="display: none" id="contentView">${activityDetail.content}</div>
							<input type="hidden" name="content" id="content" lay-verify="content" />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/activity/activity/activityList.do" class="layui-btn layui-btn-primary">取消</a>
					</div>
				</div>
			</form>
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



	$(function() {
        layui.use([ 'form', 'laydate' ], function() {
            var form = layui.form(), laydate = layui.laydate;
            var freeRadioValue = null;
            var inviteRadioValue = null;

            form.on('radio(isCrowdfunded)', function(data) {
                var value = data.value;
                if (value == "0") {
                    $('#noFree').attr('checked', false);
                    $('#free').attr('disabled', false);
                } else if (value == "1") {
                    $('#noFree').attr('checked', 'checked');
                    $('#free').attr('disabled', 'disabled');
                }
            });
            form.on('radio(isFree)', function(data) {
                freeRadioValue = data.value;
                if (freeRadioValue == "noFree") {
                    $("[name=price]").prop("disabled", false);
                } else {
                    $("[name=price]").val("");
                    $("[name=price]").prop("disabled", true);
                }
            });

            form.on('radio(isInvite)', function(data) {
                inviteRadioValue = data.value;
                if (inviteRadioValue == "1") {
                    $("#inviteCodeDiv").show();
                } else {
                    $("#inviteCodeDiv").hide();
                    $("#inviteCodeDiv input[type=text]").val("");
                }
            });

            //自定义验证规则
            form.verify({
                title : function(value) {
                    if (value == "") {
                        return '请填写活动标题';
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
// 				var nowDate = new Date();
// 				var stdt = new Date(value.replace("-", "/"));
// 				if (stdt < nowDate) {
// 					return "活动开始时间不得早于当前时间";
// 				}
                },
                endDate : function(value) {
                    if (value == "") {
                        return "请设置报名截止时间";
                    }
                    var startDate = $("[name=startDate]").val();
                    var endDate = $("[name=endDate]").val();

// 				var nowDate = new Date();
// 				var eddt = new Date(endDate.replace("-", "/"));
// 				if (eddt < nowDate) {
// 					return "报名截止时间不得早于当前时间";
// 				}
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
                    if (freeRadioValue == null) {
                        var type = $("[name=isFree]:checked").val();
                        if (type == "noFree") {
                            if (value == "") {
                                return "请设置活动报名金额";
                            } else if (!reg.test(value)) {
                                return "请输入正确的金额";
                            }
                        }
                    } else {
                        if (freeRadioValue == "noFree") {
                            if (value == "") {
                                return "请设置活动报名金额";
                            } else if (!reg.test(value)) {
                                return "请输入正确的金额";
                            }
                        }
                    }
                },
                inviteCode : function(value) {
                    if (inviteRadioValue == null) {
                        var invite = $("[name=inviteOnly]:checked").val();
                        if (invite == "1") {
                            if (value == "") {
                                return "请填写邀请码";
                            }
                        }
                    } else {
                        if (inviteRadioValue == "1") {
                            if (value == "") {
                                return "请填写邀请码";
                            }
                        }
                    }
                },
                sort : function(value) {
                    if (!util.checkNumber(value)) {
                        return "请输入正确的数字";
                    }
                },
                content : function(value) {
                    $("#contentView").html(ue.getContent());
                    var content = $("#contentView").html();
                    if (content == "") {
                        return "请填写活动详情";
                    }
                }
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                $("#content").val(ue.getContent().replace(/&quot;/gi, ""));
                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/activity/activity/activityList.do";
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
// 			if ('${endTime}' != "") {
// 				start.min = '${endTime}';
// 			}
                start.elem = this;
                laydate(start);
            }
            document.getElementById('endTime').onclick = function() {
// 			if ('${endTime}' != "") {
// 				end.min = '${endTime}';
// 			}
                end.elem = this
                laydate(end);
            }
        });
		ue.addListener('ready', function() {
			if ($("#contentView").html() != "") {
				this.setHeight(850);
			}
			this.setContent($("#contentView").html());
		});

		ue.addListener('blur', function() {
			$("#contentView").html(ue.getContent());
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
</script>
</body>
</html>