<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>创建等级</title>
<%@include file="../include/commonFile.jsp" %>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_form.css">
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
	<%@include file="../include/sidebar.jsp"%>
	<!--内容-->
	<section>
		<div class="section-main">
			<div class="c-time-list-header">
				<div class="r">
					<a href="javascript:history.back();" class="layui-btn layui-btn-radius layui-btn-danger"><i class="iconfont icon-refresh btn-icon"></i> 返回</a>
				</div>
				<div class="ovh">
					<span class="title f20">等级管理&nbsp;&gt;&nbsp;${member == null ? '创建' : '编辑'}等级</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/charge/level/save.do">
				<div class="layui-form-item">
					<label class="layui-form-label">名称<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="name" lay-verify="name" style="width: 85%" placeholder="等级名称" class="layui-input" value="${level.name}">
						<input type="hidden" name="id" value="${level.id}"/>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">封面图<span class="f-verify-red">*</span></label>
					<div class="cover-content">
						<input type="hidden" name="picture" id="pic" lay-verify="pic" value="${level.picture}" />
						<c:if test="${level == null || empty level.picture}">
							<span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
						</c:if>
						<c:if test="${level != null && not empty level.picture}">
							<span id="cover-img" class="cover-img" style="background-image:url('${level.picture}')"></span>
						</c:if>
						<div class="u-single-upload">
							<input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加封面图</span>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">费用<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="radio" name="isFree" lay-filter="isFree" id="noFree" value="noFree" title="收费"
							${level == null || level.price !=0.0 ? 'checked="checked"' : ''}
						>
						<input type="radio" name="isFree" lay-filter="isFree" id="free" value="free" title="免费"
							${level != null && level.price == 0.0 ? 'checked="checked"' : ''}
						>
					</div>
				</div>
				<div class="layui-form-item" id="priceDiv" ${level.price == 0.0 ? 'style="display:none"' : ''} >
					<div class="layui-inline">
						<label class="layui-form-label">金额<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" name="price" lay-verify="price" placeholder="￥" class="layui-input" value="${level.price}" />
						</div>
					</div>
				</div>
				<div class="layui-form-item" id="unitDiv" ${level.price == 0.0 ? 'style="display:none"' : ''} >
					<label class="layui-form-label">单位<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="radio" name="unit" lay-verify="unit" value="月" title="月" ${level.unit == "月" ? 'checked="checked"' : ''} />
						<input type="radio" name="unit" lay-verify="unit" value="季度" title="季度" ${level.unit == "季度" ? 'checked="checked"' : ''} />
						<input type="radio" name="unit" lay-verify="unit" value="半年" title="半年" ${level.unit == "半年" ? 'checked="checked"' : ''} />
						<input type="radio" name="unit" lay-verify="unit" value="年" title="年" ${level.unit == "年" ? 'checked="checked"' : ''} />
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">对应角色<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input name="sysRoleId" type="hidden" id="sysRoleId" value="${level.sysRoleId}" lay-verify="sysRoleId" />
							<input type="text" data-id="${level.sysRoleId}" class="layui-input" id="selectRole" value="${level.sysRoleName}" readonly="readonly" />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">排序号<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" name="sort" lay-verify="sort" class="layui-input" value="${level.sort}" />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">描述<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
				   		<textarea name="remarks" placeholder="等级描述" lay-verify="remarks" class="layui-textarea" style="width: 85%" >${level.remarks}</textarea>
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
	</section>
</div>

<!--底部-->
<%@include file="../include/footer.jsp" %>

<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/resize.js"></script>
<script>
	var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');


	$(function() {
        layui.use(['form'], function () {
            var form = layui.form();
            var freeRadioValue = null;

            form.on('radio(isFree)', function(data) {
                freeRadioValue = data.value;
                if (freeRadioValue == "noFree") {
                    $("[name=price]").prop("disabled", false);
                    $("[name=unit]").prop("disabled", false);
                    $("#priceDiv").show();
                    $("#unitDiv").show();
                } else {
                    $("[name=price]").val("");
                    $("[name=unit]").prop("checked", false);
                    $("[name=price]").prop("disabled", true);
                    $("[name=unit]").prop("disabled", true);
                    $("#priceDiv").hide();
                    $("#unitDiv").hide();
                }
            });

            //自定义验证规则
            form.verify({
                name : function(value) {
                    if (value == "") {
                        return '请填写等级名称';
                    }
                },
                pic : function(value) {
                    if (value == "") {
                        return "请上传等级封面图";
                    }
                },
                price : function(value) {
                    var reg = /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
                    if (freeRadioValue == null) {
                        var type = $("[name=isFree]:checked").val();
                        if (type == "noFree") {
                            if (value == "") {
                                return "请设置等级金额";
                            } else if (!reg.test(value)) {
                                return "请输入正确的金额";
                            }
                        }
                    } else {
                        if (freeRadioValue == "noFree") {
                            if (value == "") {
                                return "请设置等级金额";
                            } else if (!reg.test(value)) {
                                return "请输入正确的金额";
                            }
                        }
                    }
                },
                unit : function(value) {
                    var type = $("[name=isFree]:checked").val();
                    if (value == "" && type == "noFree") {
                        return '请填写费用单位';
                    }
                },
                sysRoleId : function (value) {
					if (value == "") {
					    return "请选择角色";
					}
                },
                sort : function(value) {
                    if (!util.checkNumber(value)) {
                        return "请输入正确的数字";
                    }
                },
                remarks : function(value) {
                    if (value == "") {
                        return '请填写等级描述';
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
                            location.href = "${ctx}/charge/level/levelList.do";
                        });
                    } else {
                        $(data.elem).attr("lay-submit","");
                        top.layer.msg('提交失败', {icon : 2});
                    }
                });
                return false;
            });
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

        $('#selectRole').on('click', function () {
            var id = $(this).data("id") || '0';
            layer.open({
                type: 2,
                area: ["400px", "300px"],
                title: "选择角色",
                maxmin: true,
                content: "${ctx}/charge/level/" + id + "/selectRole.do",
                btn: ['确定', '关闭'],
                yes: function (index, layero) {
                    var body = layer.getChildFrame('body', index);
                    var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    var inputForm = body.find('#inputForm');
                    var top_iframe;
                    top_iframe = '_parent';
                    inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示

                    var parent = iframeWin.contentWindow.doSubmit();
                    if (parent != false){
                        $('#sysRoleId').val(parent.id);
                        $('#selectRole').val(parent.name);
                        setTimeout(function () {
                            top.layer.close(index);
                        }, 100);
                    }
                },
                cancel: function (index) {
                }
            });
        })
	})
</script>
</body>
</html>