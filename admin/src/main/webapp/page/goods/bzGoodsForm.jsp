<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>发布标准玩法</title>
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
					<a href="${ctx}/goods/goods/bzGoodsList.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">标准玩法管理&nbsp;&gt;&nbsp;${goods == null ? '发布' : '编辑'}标准玩法</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<div class="add-form-content">
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/goods/goods/save.do">
				<div class="layui-form-item">
					<label class="layui-form-label">标题<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="title" lay-verify="title" placeholder="标题" class="layui-input" value="${goods.title}" />
						<input type="hidden" name="id" value="${goods.id}"/>
						<input type="hidden" name="type" value="0"/>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">封面图<span class="f-verify-red">*</span></label>
					<div class="cover-content">
						<input type="hidden" name="picsURL" id="pic" lay-verify="pic" value="${goods.picsURL}" />
						<c:if test="${goods == null || empty goods.picsURL}">
							<span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
						</c:if>
						<c:if test="${goods != null && not empty goods.picsURL}">
							<span id="cover-img" class="cover-img" style="background-image:url('${goods.picsURL}')"></span>
						</c:if>
						<div class="u-single-upload">
							<input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加封面图</span>
						</div>
						<div class="form-word-aux">建议尺寸：800x450</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">时间<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input class="layui-input" name="startDate" lay-verify="startDate" placeholder="开始时间" id="startTime"
								value='<fmt:formatDate value="${goods.startTime}" pattern="yyyy-MM-dd HH:mm" />'
							/>
						</div>
						<div class="layui-input-inline" style="width: 10px; margin-top: 8px; margin-left: 2px;">-</div>
						<div class="layui-input-inline">
							<input class="layui-input" name="endDate" lay-verify="endDate" placeholder="结束时间" id="endTime"
								value='<fmt:formatDate value="${goods.endTime}" pattern="yyyy-MM-dd HH:mm" />'
							/>
						</div>
					</div>
				</div>
				<%@include file="./cityAreaMap.jsp" %>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">玩法类型<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<select name="categoryId" lay-verify="categoryId">
								<option value="">请选择类型</option>
								<c:forEach var="category" items="${categories}">
									<option value="${category.id}" ${goods.categoryId == category.id ? 'selected="selected"' : ""}>${category.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">供应商<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline" style="margin-right: 70px;">
							<input type="hidden" name="thirdPartyId" lay-verify="thirdPartyId" value="${goods.thirdPartyId}" id="thirdPartyId" />
							<input type="text" class="layui-input" readonly="readonly" id="thirdPartyName" style="width: 250px;" value="${goods.thirdPartyName}"/>
						</div>
						<div class="layui-input-inline">
							<button class="layui-btn layui-btn-danger" type="button"
								onclick="openDialog('选择所属供应商','${ctx}/goods/goods/selectThirdParty.do','800px','570px')"
							>选择供应商</button>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">库存</label>
						<div class="layui-input-inline">
							<input type="text" name="limitNum" lay-verify="limitNum" class="layui-input" value="${goods.limitNum}" placeholder="不填表示不限">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">每人限购</label>
						<div class="layui-input-inline">
							<input type="text" name="maxBuyNum" lay-verify="maxBuyNum" class="layui-input" value="${goods.maxBuyNum}" placeholder="不填表示不限">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">金额<span class="f-verify-red">*</span></label>
					<div class="layui-input-inline">
						<input type="text" name="price" lay-verify="price" class="layui-input" placeholder="￥" value="${goods.price}">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">允许使用优惠券</label>
					<div class="layui-input-block">
						<input type="radio" name="isUseCoupon" value="1" title="是" ${goods != null && goods.isUseCoupon == 1 ? 'checked="checked"' : ''}> <input
							type="radio" name="isUseCoupon" value="0" title="否" ${goods == null || goods.isUseCoupon == 0 ? 'checked="checked"' : ''}
						>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">在其他城市显示</label>
					<div class="layui-input-block">
						<input type="radio" name="isAllowedOutside" value="1" title="是" ${goods != null && goods.isAllowedOutside == 1 ? 'checked="checked"' : ''}>
						<input type="radio" name="isAllowedOutside" value="0" title="否" ${goods == null || goods.isAllowedOutside == 0 ? 'checked="checked"' : ''}>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label"> 排序号</label>
						<div class="layui-input-inline">
							<input type="text" name="sort" lay-verify="sort" class="layui-input" value="${goods.sort}" placeholder="数字大的排在前面显示">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
						<label class="layui-form-label">推荐理由<span class="f-verify-red">*</span></label>
						<div class="layui-input-block">
							<script id="recommendUEditor" type="text/plain" style="width: 100%; height: 300px;"></script>
							<div style="display: none" id="recommendView">${goods.recommend}</div>
							<input type="hidden" name="recommend" id="recommend" lay-verify="recommend" />
						</div>
				</div>
				<div class="layui-form-item">
						<label class="layui-form-label">注意事项<span class="f-verify-red">*</span></label>
						<div class="layui-input-block">
							<script id="noticeUEditor" type="text/plain" style="width:100%; height: 300px;"></script>
							<div style="display: none" id="noticeView">${goods.notice}</div>
							<input type="hidden" name="notice" id="notice" lay-verify="notice" />
						</div>
				</div>
				<div class="layui-form-item">
						<label class="layui-form-label">详情<span class="f-verify-red">*</span></label>
						<div class="layui-input-block">
							<script id="contentUEditor" type="text/plain" style="width: 100%; height: 500px;"></script>
							<div style="display: none" id="contentView">${goodsDetail.content}</div>
							<input type="hidden" name="content" id="content" lay-verify="content" />
						</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/goods/goods/bzGoodsList.do" class="layui-btn layui-btn-primary">取消</a>
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
	var contentUEditor = UE.getEditor('contentUEditor');
	var recommendUEditor = UE.getEditor('recommendUEditor');
	var noticeUEditor = UE.getEditor('noticeUEditor');

	$(function() {
		contentUEditor.addListener('ready', function() {
			if ($("#contentView").html() != "") {
				this.setHeight(850);
			}
			this.setContent($("#contentView").html());
		});
		recommendUEditor.addListener('ready', function() {
			this.setContent($("#recommendView").html());
		});
		noticeUEditor.addListener('ready', function() {
			this.setContent($("#noticeView").html());
		});
		contentUEditor.addListener('blur', function() {
			$("#contentView").html(contentUEditor.getContent());
		});
		recommendUEditor.addListener('blur', function() {
			$("#recommendView").html(recommendUEditor.getContent());
		});
		noticeUEditor.addListener('blur', function() {
			$("#noticeView").html(noticeUEditor.getContent());
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


        layui.use([ 'form', 'laydate' ], function() {
            var form = layui.form(), laydate = layui.laydate;

            //自定义验证规则
            form.verify({
                title : function(value) {
                    if (value == "") {
                        return '请填写标题';
                    }
                },
                pic : function(value) {
                    if (value == "") {
                        return "请上传封面图";
                    }
                },
                startDate : function(value) {
                    if (value == "") {
                        return "请设置开始时间";
                    }
                },
                endDate : function(value) {
                    if (value == "") {
                        return "请设置结束时间";
                    }
                },
                place : function(value) {
                    if (value == "") {
                        return "请设置集合地点";
                    }
                },
                area : function(value) {
                    if (value == "") {
                        return "请设置区域";
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
                        return "请选择城市";
                    }
                },
                thirdPartyId : function(value) {
                    if (value == "") {
                        return "请选择供应商";
                    }
                },
                categoryId : function(value) {
                    if (value == "") {
                        return "请选择类型";
                    }
                },
                limitNum : function(value) {
                    if (!util.checkNumber(value)) {
                        return "请填写正确的数字";
                    } else if (parseInt(value) == 0) {
                        return "请填写大于0的数字";
                    }
                },
                maxBuyNum : function(value) {
                    if (value != "") {
                        if (!util.checkNumber(value)) {
                            return "请填写正确的数字";
                        } else if (parseInt(value) == 0) {
                            return "请填写大于0的数字";
                        }

                        var limitNum = $("[name=limitNum]").val();
                        if (limitNum != "") {
                            if (parseInt(value) > parseInt(limitNum)) {
                                return "每人限购数量必须小于库存";
                            }
                        }
                    }
                },
                price : function(value) {
                    var reg = /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
                    if (value == "") {
                        return "请设置玩法金额";
                    } else if (!reg.test(value)) {
                        return "请填写正确的金额";
                    }
                },
                sort : function(value) {
                    if (!util.checkNumber(value)) {
                        return "请填写正确的数字";
                    }
                },
                recommend : function(value) {
                    $("#recommendView").html(recommendUEditor.getContent());
                    var content = $("#recommendView").html();
                    if (content == "") {
                        return "请填写推荐理由";
                    }
                },
                notice : function(value) {
                    $("#noticeView").html(noticeUEditor.getContent());
                    var content = $("#noticeView").html();
                    if (content == "") {
                        return "请填写注意事项";
                    }
                },
                content : function(value) {
                    $("#contentView").html(contentUEditor.getContent());
                    var content = $("#contentView").html();
                    if (content == "") {
                        return "请填写详情";
                    }
                }
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                $("#content").val(contentUEditor.getContent().replace(/&quot;/gi, ""));
                $("#recommend").val(recommendUEditor.getContent().replace(/&quot;/gi, ""));
                $("#notice").val(noticeUEditor.getContent().replace(/&quot;/gi, ""));
                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/goods/goods/bzGoodsList.do";
                        });
                    } else {
                        top.layer.msg('提交失败', {icon : 2});
                    }
                });
                return false;
            });

            initMapForm(form);
            setTimeout(function () {
                form.render('radio');
            },100);
        })


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

	});

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

	function openDialog(title, url, width, height, target) {
		layer.open({
			type : 2,
			area : [ width, height ],
			title : title,
			maxmin : true, //开启最大化最小化按钮
			content : url,
			btn : [ '确定', '关闭' ],
			yes : function(index, layero) {
				var body = layer.getChildFrame('body', index);
				var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
				var inputForm = body.find('#inputForm');
				var top_iframe;
				if (target) {
					top_iframe = target;//如果指定了iframe，则在改frame中跳转
				} else {
					top_iframe = '_parent';//获取当前active的tab的iframe
				}
				inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示

				if (iframeWin.contentWindow.doSubmit()) {
					var thirdPartyId = $(iframeWin.contentWindow.$.find("#thirdPartyId")).val();
					var thirdPartyName = $(iframeWin.contentWindow.$.find("#thirdPartyName")).val();
					$("#thirdPartyId").val(thirdPartyId);
					$("#thirdPartyName").val(thirdPartyName);
					setTimeout(function() {
						top.layer.close(index);
					}, 100);//延时0.1秒，对应360 7.1版本bug
				}

			},
			cancel : function(index) {
			}
		});
	}
</script>
</body>
</html>