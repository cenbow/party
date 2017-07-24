<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>${circle.id == null ? '添加' : '编辑'}圈子</title>
    <%@include file="../include/commonFile.jsp" %>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/form.css">
    <style type="text/css">
    	.isOpen .layui-form-radio {
			display: block !important;
			width: 30%!important;
		}
		
		.text-place {
			padding-left: 30px;
			color: #aaa;
		}
    </style>
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
                    <a href="javascript:history.back();" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
                </div>
                <div class="ovh">
                    <span class="title f20"><a href="${ctx}/circle/list.do" class="">圈子管理</a> > ${circle.id == null ? '添加' : '编辑'}圈子</span>
                </div>

            </div>
            <!-- 正文请写在这里 -->
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/circle/save.do">
                <input type="hidden" name="id" value="${circle.id}"/>
                <div class="layui-form-item">
                    <label class="layui-form-label">名称<span class="f-verify-red">*</span></label>
                    <div class="layui-input-block">
                        <input type="text" name="name" lay-verify="title" style="width: 80%" autocomplete="off" placeholder="请输入圈子名称"
                               class="layui-input" value="${circle.name}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">简介</label>
                    <div class="layui-input-block">
                        <input type="hidden" value="${circle.memo}" name="memo">
                        <textarea id="memo" style="width: 80%" placeholder="请输入圈子简介" class="layui-textarea"></textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">封面图<span class="f-verify-red">*</span></label>
                    <div class="cover-content">
                        <input type="hidden" name="logo" id="logo" lay-verify="logo" value="${circle.logo}"/>
                        <c:if test="${circle == null || empty circle.logo}">
                            <span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
                        </c:if>
                        <c:if test="${circle != null && not empty circle.logo}">
                            <span id="cover-img" class="cover-img" style="background-image:url('${circle.logo}')"></span>
                        </c:if>
                        <div class="u-single-upload">
                            <input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加封面图</span>
                        </div>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">圈子类型</label>
                        <div class="layui-input-inline">
                            <select name="circleType">
                                <option value="">请选择类型</option>
                                <c:forEach var="type" items="${types}">
                                    <option value="${type.value}" ${circle.circleType == type.value ? 'selected="selected"' : ""}>${type.label}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">城市</label>
                        <div class="layui-input-inline">
                            <select lay-verify="province" lay-filter="province">
                                <option value="">选择省份/直辖市</option>
                                <c:forEach var="area" items="${areas}">
                                    <option value="${area.id}" ${arParent == area.id?'selected="selected"' : ''}>${area.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="layui-input-inline">
                            <select name="area" lay-verify="city" id="city">
                                <option value="">选择城市</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">是否显示</label>
                        <div class="layui-input-inline">
                            <input type="radio" name="showFront" value="1" title="是" ${circle.showFront != null && circle.showFront == '1' ? 'checked="checked"' : ''}>
                            <input type="radio" name="showFront" value="0" title="否" ${circle.showFront == null || circle.showFront == '0' ? 'checked="checked"' : ''}>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
					<label class="layui-form-label">隐私控制<span class="f-verify-red">*</span></label>
					<div class="layui-input-block isOpen">
						<input type="radio" name="isOpen" lay-filter="isOpen" value="0" title="不公开" ${empty circle.isOpen || circle.isOpen == 0 ? 'checked="checked"' : ''} />
						<input type="radio" name="isOpen" lay-filter="isOpen" value="1" title="内部公开，会员之间可以互相查看" ${not empty circle.isOpen && circle.isOpen == 1 ? 'checked="checked"' : ''} />
						<input type="radio" name="isOpen" lay-filter="isOpen" value="2" title="对每个会员类型单独设置隐私" ${not empty circle.isOpen && circle.isOpen == 2 ? 'checked="checked"' : ''} />
						<div class="pl30" id="specialDiv" ${circle.isOpen != 2  ? 'style="display:none"' : ''}>
							<c:forEach var="tag" items="${circleTags}" varStatus="status">
								<dl class="circleTag_${tag.id}" data-id="${tag.id}">
									<input type="hidden" name="circleTags[${status.index}].id" value="${tag.id}" />
									<input type="hidden" name="circleTags[${status.index}].tagName" value="${tag.tagName}" />
									<dt class="red">${tag.tagName}</dt>
									<dd class="pl20">
										<input type="radio" name="circleTags[${status.index}].isOpen" value="0" title="所有人不可见" ${empty tag.isOpen || tag.isOpen == 0 ? 'checked="checked"' : ''} />
									</dd>
									<dd class="pl20">
										<input type="radio" name="circleTags[${status.index}].isOpen" value="1" title="所有人可见" ${not empty tag.isOpen && tag.isOpen == 1 ? 'checked="checked"' : ''} />
									</dd>
									<dd class="pl20">
										<input type="radio" name="circleTags[${status.index}].isOpen" value="2" title="对本类型成员可见" ${not empty tag.isOpen && tag.isOpen == 2 ? 'checked="checked"' : ''} />
									</dd>
								</dl>
							</c:forEach>
							<dl>
								<input type="hidden" name="circleTags[${fn:length(circleTags)}].tagName" value="其他" />
								<dt class="red">其他</dt>
								<dd class="pl20">
									<input type="radio" name="circleTags[${fn:length(circleTags)}].isOpen" value="0" title="所有人不可见" ${empty circle.noTypeIsOpen || circle.noTypeIsOpen == 0 ? 'checked="checked"' : ''} />
								</dd>
								<dd class="pl20">
									<input type="radio" name="circleTags[${fn:length(circleTags)}].isOpen" value="1" title="所有人可见" ${not empty circle.noTypeIsOpen && circle.noTypeIsOpen == 1 ? 'checked="checked"' : ''} />
								</dd>
							</dl>
						</div>
					</div>
				</div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">排序号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sort" lay-verify="sort" autocomplete="off" class="layui-input" value="${circle.sort}">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">描述</label>
                    <div class="layui-input-block">
                        <textarea name="remarks" style="width: 80%" placeholder="请输入圈子描述" class="layui-textarea">${circle.remarks}</textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
                        <a href="${ctx}/circle/list.do" class="layui-btn layui-btn-primary">取消</a>
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
    $(function () {
        layui.use(['form'], function () {
            var form = layui.form(), laydate = layui.laydate;

            var showRadioValue = null;
            var typeRadioValue = null;
            
            form.on('radio(isOpen)', function(data) {
    			var value = data.value;
    			if (value == "0" || value == "1") {
    				$("#specialDiv").hide();
    			} else if (value == "2") {
    				$("#specialDiv").show();
    			}
    		});

            // 城市
            form.on('select(province)', function (data) {
                $("#city").html("");
                var cityId = data.value;
                loadCityData(cityId);
            });

            function loadCityData(cityId) {
                $.post("${ctx}/system/member/getCityByParentId.do", {
                    "cityId": cityId
                }, function (data) {
                    var array = new Array();
                    for (var i = 0; i < data.length; i++) {
                        if ('${circle.area}' == data[i].id) {
                            array.push("<option value = '" + data[i].id + "' selected='selected'>" + data[i].name + "</option>");
                        } else {
                            array.push("<option value = '" + data[i].id + "'>" + data[i].name + "</option>");
                        }
                    }
                    $("#city").append(array.join(""));
                    form.render('select');
                });
            }

            //自定义验证规则
            form.verify({
            	title: function (value) {
                    if (value == "") {
                        return '请填写圈子名称';
                    }
                },
                logo: function (value) {
                    if (value == "") {
                        return "请上传圈子封面图";
                    }
                },
                sort: function (value) {
                    if (!util.checkNumber(value)) {
                        return "请输入正确的数字";
                    }
                }

            });

            //监听提交
            form.on('submit', function (data) {
            	$(data.elem).removeAttr("lay-submit");
                var action = $("#myForm").attr("action");
                $('input[name=memo]').val(util.textareaToHtml($('#memo').val()));
                $.post(action, $('#myForm').serialize(), function (res) {
                    if (res.success) {
						top.layer.msg('提交成功', {
							icon : 1
						}, function(index) {
							location.href = "${ctx}/circle/list.do";
						});
					} else {
						top.layer.msg('提交失败', {
							icon : 2
						});
					}
                });
                return false;
            });
            if ('${circle.area}' != "") {
                loadCityData('${arParent}');
            }
            $('#memo').val(util.htmlToTextarea($('input[name=memo]').val()));
        });
        $('#upload_single_img').change(function () {
            if (util.isValid(this.value)) {
                uploadFile.uploadSinglePhoto(this.files[0], function (ret) {
                    console.log('回调：' + ret);
                    $('#cover-img').css('background-image', 'url(' + ret.data.download_url + ')');
                    $('#logo').val(ret.data.download_url);
                    $('#upload_single_img').val('');
                });
            }
        });
    })

</script>
</body>
</html>