<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>资料编辑</title>
<%@include file="../../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_act.css">
<style type="text/css">
.cover-content .cover-img {
	width: 80px !important;
	height: 80px !important;
}
.layui-tab-brief>.layui-tab-title .layui-this{
	color: #e8473f!important;
}
.layui-tab-brief>.layui-tab-title .layui-this:after{
	border-bottom-color: #e8473f!important;
}

#mchForm .layui-form-item .layui-input-inline{
		width: 280px!important;
	}
</style>
</head>
<!--头部-->
<%@include file="../../include/header.jsp"%>
<div class="index-outside">
	<%@include file="../../include/sidebar.jsp"%>
	<!--内容-->
	<section>
		<div class="section-main">
			<div class="c-time-list-header">
				<div class="r">
					<a href="${ctx}/system/member/memberIndex.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
				</div>
				<div class="ovh">
					<span class="title f20">账户中心&nbsp;&gt;&nbsp;编辑资料</span>
				</div>
			</div>
			<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
				<ul class="layui-tab-title">
					<%--<li><span class="title f20 ml5 mr5">服务升级</span></li>--%>
					<li class="layui-this"><span class="title f20 ml5 mr5">资料编辑</span></li>
					<li><span class="title f20 ml5 mr5">修改密码</span></li>
<!-- 					<li><span class="title f20 ml5 mr5">绑定商户信息</span></li> -->
					<c:if test="${empty member.mobile || member.mobile == ''}">
						<li><span class="title f20 ml5 mr5">绑定手机</span></li>
					</c:if>
				</ul>
				<div class="layui-tab-content">
					<%--<div class="layui-tab-item"></div>--%>
					<div class="layui-tab-item layui-show">
						<!-- 正文请写在这里 -->
						<div class="add-form-content">
						<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/system/member/save.do">
							<div class="layui-form-item" ${member.username != '' && not empty member.username ? '' : 'style="display:none"'}>
								<div class="layui-inline">
									<label class="layui-form-label">用户名</label>
									<div class="layui-input-inline">
										<div style="height: 38px; line-height: 38px;">${member.username}</div>
										<input type="hidden" name="id" value="${member.id}" />
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">昵称<span class="f-verify-red">*</span></label>
									<div class="layui-input-inline">
										<input class="layui-input" name="realname" lay-verify="realname" placeholder="昵称" value='${member.realname}' />
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">真实姓名<span class="f-verify-red">*</span></label>
									<div class="layui-input-inline">
										<input class="layui-input" name="fullname" lay-verify="fullname" placeholder="真实姓名" value='${member.fullname}' />
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">头像<span class="f-verify-red">*</span></label>
								<div class="cover-content">
									<input type="hidden" name="logo" id="pic" lay-verify="pic" value="${member.logo}" />
									<c:if test="${member == null || empty member.logo}">
										<span id="cover-img" class="round-img" style="background-image:url(${ctx}/image/def_user_logo.png)"></span>
									</c:if>
									<c:if test="${member != null && not empty member.logo}">
										<span id="cover-img" class="round-img" style="background-image:url('${member.logo}')"></span>
									</c:if>
									<div class="u-single-upload">
										<input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加头像</span>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label">性别<span class="f-verify-red">*</span></label>
                                    <div class="layui-input-inline">
                                        <input type="radio" name="sex" value="1" title="男"
                                        ${member.id == null || member.sex == null || member.sex == '1' ? 'checked="checked"' : ''}
                                        >
                                        <input type="radio" name="sex" value="0" title="女"
                                        ${member.id != null && member.sex == '0' ? 'checked="checked"' : ''}
                                        >
                                    </div>
                                </div>
                            </div>
							<div class="layui-form-item" ${member.mobile != '' && not empty member.mobile ? '' : 'style="display:none"'}>
								<div class="layui-inline">
									<label class="layui-form-label">手机号</label>
									<div class="layui-input-inline">
										<div style="height: 38px; line-height: 38px;">${member.mobile}</div>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label">QQ号</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="qq" class="layui-input" value="${member.qq}" lay-verify="qq" >
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label">微信号</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="wechatNum" class="layui-input" value="${member.wechatNum}">
                                    </div>
                                </div>
                            </div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">公司</label>
									<div class="layui-input-inline">
										<input type="text" name="company" class="layui-input" value="${member.company}">
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">职位</label>
									<div class="layui-input-inline">
										<input type="text" name="jobTitle" class="layui-input" value="${member.jobTitle}">
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">行业</label>
									<div class="layui-input-inline">
										<select lay-verify="industryParent" lay-filter="industryParent">
											<option value="">选择行业分类</option>
											<c:forEach var="industry" items="${industries}">
												<option value="${industry.id}" ${inParent == industry.id?'selected="selected"' : ''}>${industry.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="layui-input-inline">
										<select name="industry" lay-verify="industry" id="industry">
											<option value="">选择行业</option>
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
										<select name="city" lay-verify="city" id="city">
											<option value="">选择城市</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">资源对接</label>
									<div class="layui-input-inline">
										<input type="text" name="wantRes" lay-verify="wantRes" class="layui-input" value="${member.wantRes}">
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">是否公开名片</label>
									<div class="layui-input-inline">
										<input type="radio" name="isOpen" value="1" title="是" ${member != null && member.isOpen == '1' ? 'checked="checked"' : ''}>
										<input type="radio" name="isOpen" value="0" title="否" ${member == null || member.isOpen == '0' ? 'checked="checked"' : ''}>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-input-block">
									<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="infoForm">立即提交</a>
									<button type="reset" class="layui-btn layui-btn-primary">取消</button>
								</div>
							</div>
						</form>
						</div>
					</div>
					<div class="layui-tab-item">
						<div class="add-form-content">
						<form id="pwdForm" class="layui-form mt20" method="post" action="${ctx}/system/member/updatePwd.do">
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label"></label>
									<div class="layui-input-inline">
										<span style="color: #aaa">密码只能输入字母或数字</span>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">原密码<span class="f-verify-red">*</span></label>
									<div class="layui-input-inline">
										<input type="password" class="layui-input" name="oldPassword" lay-verify="oldPassword" placeholder="原密码" />
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">新密码<span class="f-verify-red">*</span></label>
									<div class="layui-input-inline">
										<input type="password" class="layui-input" name="password" lay-verify="password" placeholder="新密码" />
										<input type="hidden" name="id" value="${member.id}" />
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">确认密码<span class="f-verify-red">*</span></label>
									<div class="layui-input-inline">
										<input type="password" class="layui-input" name="repassword" lay-verify="repassword" placeholder="再次确认密码" />
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-input-block">
									<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="pwdForm">立即修改</a>
									<button type="reset" class="layui-btn layui-btn-primary">取消</button>
								</div>
							</div>
						</form>
						</div>
					</div>
<!-- 					<div class="layui-tab-item"> -->
<%-- 						<form id="mchForm" class="layui-form mt20" method="post" action="${ctx}/system/member/bindMerchant.do"> --%>
<!-- 							<div class="layui-form-item"> -->
<!-- 								<div class="layui-inline"> -->
<!-- 									<label class="layui-form-label">公众号名称<span class="f-verify-red">*</span></label> -->
<!-- 									<div class="layui-input-inline"> -->
<%-- 										<input type="text" class="layui-input" name="officialAccountName" lay-verify="required" placeholder="公众号名称" value="${memberMerchant.officialAccountName}" /> --%>
<%-- 										<input type="hidden" name="id" value="${memberMerchant.id}" /> --%>
<%-- 										<input type="hidden" name="memberId" value="${member.id}" /> --%>
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="layui-form-item"> -->
<!-- 								<div class="layui-inline"> -->
<!-- 									<label class="layui-form-label">公众号ID<span class="f-verify-red">*</span></label> -->
<!-- 									<div class="layui-input-inline"> -->
<%-- 										<input type="text" class="layui-input" name="officialAccountId" lay-verify="required" placeholder="公众号ID" value="${memberMerchant.officialAccountId}" /> --%>
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="layui-form-item"> -->
<!-- 								<div class="layui-inline"> -->
<!-- 									<label class="layui-form-label">公众号Secret<span class="f-verify-red">*</span></label> -->
<!-- 									<div class="layui-input-inline"> -->
<%-- 										<input type="text" class="layui-input" name="officialAccountSecret" lay-verify="required" placeholder="公众号Secret" value="${memberMerchant.officialAccountSecret}" /> --%>
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="layui-form-item"> -->
<!-- 								<div class="layui-inline"> -->
<!-- 									<label class="layui-form-label">商户号名称<span class="f-verify-red">*</span></label> -->
<!-- 									<div class="layui-input-inline"> -->
<%-- 										<input type="text" class="layui-input" name="merchantName" lay-verify="required" placeholder="商户号名称" value="${memberMerchant.merchantName}" /> --%>
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="layui-form-item"> -->
<!-- 								<div class="layui-inline"> -->
<!-- 									<label class="layui-form-label">商户号ID<span class="f-verify-red">*</span></label> -->
<!-- 									<div class="layui-input-inline"> -->
<%-- 										<input type="text" class="layui-input" name="merchantId" lay-verify="required" placeholder="商户号ID" value="${memberMerchant.merchantId}" /> --%>
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="layui-form-item"> -->
<!-- 								<div class="layui-inline"> -->
<!-- 									<label class="layui-form-label">商户号ApiKey<span class="f-verify-red">*</span></label> -->
<!-- 									<div class="layui-input-inline"> -->
<%-- 										<input type="text" class="layui-input" name="merchantApiKey" lay-verify="required" placeholder="商户号ApiKey" value="${memberMerchant.merchantApiKey}" /> --%>
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="layui-form-item"> -->
<!-- 								<div class="layui-inline"> -->
<!-- 									<label class="layui-form-label">是否启用</label> -->
<!-- 									<div class="layui-input-inline"> -->
<%-- 										<input type="radio" name="openStatus" value="1" title="是" ${memberMerchant != null && memberMerchant.openStatus == '1' ? 'checked="checked"' : ''} /> --%>
<%-- 										<input type="radio" name="openStatus" value="0" title="否" ${memberMerchant == null || memberMerchant.openStatus == '0' ? 'checked="checked"' : ''} /> --%>
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="layui-form-item"> -->
<!-- 								<div class="layui-input-block"> -->
<!-- 									<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="mchForm">立即保存</a> -->
<!-- 									<button type="reset" class="layui-btn layui-btn-primary">取消</button> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</form> -->
<!-- 					</div> -->
					<div class="layui-tab-item">
						<div class="add-form-content">
						<form id="bdPhoneForm" class="layui-form mt20" method="post" action="${ctx}/system/member/bindPhone.do">
							<input type="hidden" name="id" value="${member.id}" />
							<div class="layui-form-item">
								<label class="layui-form-label">手机号<span class="f-verify-red">*</span></label>
								<div class="layui-input-block login-input" style="width: 300px;">
									<input type="text" name="mobile" lay-verify="mobile" placeholder="请输入手机号码" class="layui-input l" style="width: calc(100% - 120px); margin-right: 10px;">
									<button id="smsbtn" type="button" onclick="sendsms()" class="layui-btn layui-btn-primary" style="width: 110px!important">发送验证码</button>	
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">验证码<span class="f-verify-red">*</span></label>
								<div class="layui-input-block login-input" style="width: 300px;">
									<input type="text" name="verifyCode" lay-verify="verifyCode"  placeholder="请输入手机验证码" class="layui-input">
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-input-block">
									<button class="layui-btn layui-btn-danger" lay-submit lay-filter="bdPhoneForm">绑定</button>
									<button type="reset" class="layui-btn layui-btn-primary">取消</button>
								</div>
							</div>
						</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/resize.js"></script>
<script>
	var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');

	$(function() {
        layui.use([ 'form', 'laydate', 'element' ], function() {
            var form = layui.form(), laydate = layui.laydate(), element = layui.element();

            element.on('tab', function(data) {
                console.log(this); //当前Tab标题所在的原始DOM元素
                console.log(data.index); //得到当前Tab的所在下标
                console.log(data.elem); //得到当前的Tab大容器

				/*if (data.index == 0){
				    window.location.href = "${ctx}/charge/level/levelList.do";
				}*/
            });

            //自定义验证规则
            form.verify({
                fullname : function(value) {
                    if (value == "") {
                        return '请输入真实姓名';
                    }
                },
                realname : function(value) {
                    if (value == "") {
                        return '请输入昵称';
                    }
                },
                pic : function(value) {
                    if (value == "") {
                        return "请上传头像";
                    }
                },
                mobile : function(value) {
                    var regex = /^((13[0-9])|(14[5|7])|(15([0-9]))|(18[0-9])|(17[0-9]))\d{8}$/;
                    if (value == "") {
                        return "请输入手机号码";
                    } else if (!regex.test(value)) {
                        return "请输入正确格式的手机号码";
                    } else if (value != "" && regex.test(value)) {
                        var isRepeat = ajaxSubmit({
                            property : value,
                            userId : '${member.id}',
                            type : 'mobile'
                        },"${ctx}/system/member/checkUniqueProperty.do");
                        if (isRepeat) {
                            return "手机号已存在";
                        }
                    }
                },
                oldPassword : function(value) {
                    if (value == "") {
                        return "原密码不能为空";
                    } else {
                        var isRepeat = ajaxSubmit({
                            password : value,
                            userId : '${member.id}'
                        },"${ctx}/system/member/checkPassword.do");
                        if (isRepeat) {
                            return "原密码错误";
                        }
                    }
                },
                password : function(value) {
                    var regex = /^[a-zA-Z0-9]{6,20}$/;
                    if (value == "") {
                        return "密码不能为空";
                    } else if (!regex.test(value)) {
                        return "密码格式不正确";
                    }
                },
                repassword : function(value) {
                    var pwd = $("#pwdForm [name=password]").val();
                    var regex = /^[a-zA-Z0-9]{6,20}$/;
                    if (value == "") {
                        return "确认密码不能为空";
                    } else if (!regex.test(value)) {
                        return "确认密码格式不正确";
                    } else if(pwd != value){
                        return "确认密码与新密码不匹配";
                    }
                },
                verifyCode : function(value) {
                    if(value == ""){
                        return "请输入手机验证码";
                    }
                },
                qq : function (value) {
                    var regex = /^[1-9][0-9]{4,14}$/;
                    if (value != "" && !regex.test(value)) {
                        return "QQ号格式不正确";
                    }
                }
            });

            function ajaxSubmit(data, url) {
                var isRepeat = false;
                $.ajax({
                    type : 'POST',
                    async : false, // 使用同步的方法
                    data : data,
                    dataType : 'json',
                    success : function(result) {
                        isRepeat = !result;
                    },
                    url : url
                });
                return isRepeat;
            }

            //监听提交 编辑资料
            form.on('submit(infoForm)', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        layer.msg("资料编辑成功", {
                            icon : 1
                        }, function(index) {
                            window.location.reload();
                        });
                    } else {
                        layer.msg('资料编辑失败', {
                            icon :2
                        })
                    }
                });
                return false;
            });
            //监听提交 修改密码
            form.on('submit(pwdForm)', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#pwdForm").attr("action");
                $.post(action, $('#pwdForm').serialize(), function(res) {
                    if (res.success) {
                        layer.msg("密码修改成功", {
                            icon : 1
                        }, function(index) {
                            window.location.reload();
                        });
                    } else {
                        layer.msg('密码修改失败', {
                            icon :2
                        })
                    }
                });
                return false;
            });

            //监听提交 修改密码
            form.on('submit(mchForm)', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#mchForm").attr("action");
                $.post(action, $('#mchForm').serialize(), function(res) {
                    if (res.success) {
                        layer.msg("商户信息保存成功", {
                            icon : 1
                        }, function(index) {
                            window.location.reload();
                        });
                    } else {
                        layer.msg('商户信息保存失败', {
                            icon :2
                        })
                    }
                });
                return false;
            });

            //监听提交 绑定手机
            form.on('submit(bdPhoneForm)', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#bdPhoneForm").attr("action");
                $.post(action, $('#bdPhoneForm').serialize(), function(res) {
                    if (res.success) {
                        layer.msg("手机绑定成功", {
                            icon : 1
                        }, function(index) {
                            window.location.reload();
                        });
                    } else {
                        layer.msg('手机绑定失败', {
                            icon :2
                        })
                    }
                });
                return false;
            });

            // 城市
            form.on('select(province)', function(data) {
                $("#city").html("");
                var cityId = data.value;
                loadCityData(cityId);
            });

            // 行业
            form.on('select(industryParent)', function(data) {
                $("#industry").html("");
                var industryId = data.value;
                loadIndustryData(industryId);
            });

            if ('${member.city}' != "") {
                loadCityData('${arParent}');
            }

            if ('${member.industry}' != "") {
                loadIndustryData('${inParent}');
            }

            function loadCityData(cityId) {
                $.post("${ctx}/system/member/getCityByParentId.do", {
                    "cityId" : cityId
                }, function(data) {
                    var array = new Array();
                    for (var i = 0; i < data.length; i++) {
                        if ('${member.city}' == data[i].id) {
                            array.push("<option value = '"+data[i].id+"' selected='selected'>" + data[i].name + "</option>");
                        } else {
                            array.push("<option value = '"+data[i].id+"'>" + data[i].name + "</option>");
                        }
                    }
                    $("#city").append(array.join(""));
                    form.render('select');
                });
            }

            function loadIndustryData(industryId) {
                $.post("${ctx}/system/member/getIndustryByParentId.do", {
                    "industryId" : industryId
                }, function(data) {
                    var array = new Array();
                    for (var i = 0; i < data.length; i++) {
                        if ('${member.industry}' == data[i].id) {
                            array.push("<option value = '"+data[i].id+"' selected='selected' >" + data[i].name + "</option>");
                        } else {
                            array.push("<option value = '"+data[i].id+"'>" + data[i].name + "</option>");
                        }
                    }
                    $("#industry").append(array.join(""));
                    form.render('select');
                });
            }
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
	
	var ttt = null;
	var wait = 60;
	function timeoutssss(mobile){
		var obj = $("#bdPhoneForm").find("#smsbtn");
		if (wait == 0) {
			$(obj).removeAttr("disabled");
			$(obj).text("发送验证码");
			wait = 60;
		} else {
			$(obj).attr("disabled", "disabled");
			$(obj).text("重新发送(" + wait + ")");
			if (wait == 60) {
				$.post('${ctx}/user/login/getCode.do', {
					"mobile" : mobile
				}, function(data) {
				});
			}
			wait--;
			ttt = setTimeout(function() {
				timeoutssss(mobile);
			}, 1000);
		}
	}

	// 发送短信
	function sendsms() {
		var mobile = $("#bdPhoneForm").find("[name=mobile]").val();
		if (mobile == "") {
			top.layer.alert("请先输入手机号", {
				icon : 5
			});
			return;
		}
		timeoutssss(mobile);
	}
	
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