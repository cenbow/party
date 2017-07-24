<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>
        <c:choose>
            <c:when test="${type == 1}">${member.id == null ? '添加' : '编辑'}分销商</c:when>
            <c:when test="${type == 2}">${member.id == null ? '添加' : '编辑'}合作商</c:when>
            <c:when test="${type == 3}">${member.id == null ? '添加' : '编辑'}用户</c:when>
        </c:choose>
    </title>
    <%@include file="../../include/commonFile.jsp" %>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/member_form.css">
</head>
<!--头部-->
<%@include file="../../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../../include/sidebar.jsp" %>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="javascript:cancelFunction()" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
					<span class="title f20">
						<c:choose>
                            <c:when test="${type == 1}">分销商管理</c:when>
                            <c:when test="${type == 2}">合作商管理</c:when>
                            <c:when test="${type == 3}">用户管理</c:when>
                        </c:choose>
						&nbsp;&gt;&nbsp;${member.id == null ? '添加' : '编辑'}用户
					</span>
                </div>
            </div>

            <!-- 正文请写在这里 -->
            <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
                <ul class="layui-tab-title" ${member.id == null ? 'style="display:none"' : ''}>
                    <li class="layui-this"><span class="title f18 ml5 mr5">资料编辑</span></li>
                    <li><span class="title f18 ml5 mr5">修改密码</span></li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                        <form id="infoForm" class="layui-form mt20" method="post" action="${ctx}/system/member/save.do">
                            <input type="hidden" name="id" value="${member.id}"/>
                            <input type="hidden" name="type" value="${type}"/>
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label">用户名</label>
                                    <div class="layui-input-inline">
                                        <input class="layui-input" name="username" lay-verify="username" placeholder="用户名" value='${member.username}'/>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label">昵称<span class="f-verify-red">*</span></label>
                                    <div class="layui-input-inline">
                                        <input class="layui-input" name="realname" lay-verify="realname" placeholder="昵称" value='${member.realname}'/>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label">真实姓名<span class="f-verify-red">*</span></label>
                                    <div class="layui-input-inline">
                                        <input class="layui-input" name="fullname" lay-verify="fullname" placeholder="真实姓名" value='${member.fullname}'/>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">头像<span class="f-verify-red">*</span></label>
                                <div class="cover-content">
                                    <input type="hidden" name="logo" id="pic" lay-verify="pic" value="${member.logo}"/>
                                    <c:if test="${member == null || empty member.logo}">
                                        <span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/avatar1.png)"></span>
                                    </c:if>
                                    <c:if test="${member != null && not empty member.logo}">
                                        <span id="cover-img" class="cover-img" style="background-image:url('${member.logo}')"></span>
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
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label">手机号</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="mobile" lay-verify="mobile" autocomplete="off" class="layui-input"
                                               value="${member.mobile}"
                                        >
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
                                        <input type="text" name="company" autocomplete="off" class="layui-input" value="${member.company}">
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label">职位</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="jobTitle" autocomplete="off" class="layui-input" value="${member.jobTitle}">
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
                                    <label class="layui-form-label">对接资源</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="wantRes" lay-verify="wantRes" autocomplete="off" class="layui-input"
                                               value="${member.wantRes}"
                                        >
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label">是否达人</label>
                                    <div class="layui-input-inline">
                                        <input type="radio" name="isExpert" lay-filter="isExpert" value="1" title="是"
                                        ${member != null && member.isExpert == '1' ? 'checked="checked"' : ''}
                                        >
                                        <input type="radio" name="isExpert" lay-filter="isExpert" value="0" title="否"
                                        ${member.id == null || member.isExpert == '0' ? 'checked="checked"' : ''}
                                        >
                                    </div>
                                </div>
                            </div>
                            <div id="recommendDiv" class="layui-form-item" ${empty member.id || member.isExpert == 0 ? 'style="display:none"' : ''} >
                                <div class="layui-inline">
                                    <label class="layui-form-label">达人推荐理由</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="recommend" autocomplete="off" class="layui-input" value="${member.recommend}">
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label">是否公开名片</label>
                                    <div class="layui-input-inline">
                                        <input type="radio" name="isOpen" value="1" title="是"
                                        ${member != null && member.isOpen == '1' ? 'checked="checked"' : ''}
                                        >
                                        <input type="radio" name="isOpen" value="0" title="否"
                                        ${member.id == null || member.isOpen == '0' ? 'checked="checked"' : ''}
                                        >
                                    </div>
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="infoForm">立即提交</a>
                                    <a href="javascript:void(0)" class="layui-btn layui-btn-primary" onclick="cancelFunction()">取消</a>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="layui-tab-item">
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
                                    <label class="layui-form-label">新密码<span class="f-verify-red">*</span></label>
                                    <div class="layui-input-inline">
                                        <input type="password" class="layui-input" name="password" lay-verify="password" placeholder="新密码"/>
                                        <input type="hidden" name="id" value="${member.id}"/>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label">确认密码<span class="f-verify-red">*</span></label>
                                    <div class="layui-input-inline">
                                        <input type="password" class="layui-input" name="repassword" lay-verify="repassword" placeholder="再次确认密码"/>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <button class="layui-btn layui-btn-danger" lay-submit lay-filter="pwdForm">立即修改</button>
                                    <button type="reset" class="layui-btn layui-btn-primary" onclick="cancelFunction()">取消</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/resize.js"></script>
<script>
    var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');

    $(function () {
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

        layui.use(['form', 'laydate', 'element'], function () {
            var form = layui.form(), laydate = layui.laydate(), element = layui.element();

            element.on('tab', function (data) {
                console.log(this); //当前Tab标题所在的原始DOM元素
                console.log(data.index); //得到当前Tab的所在下标
                console.log(data.elem); //得到当前的Tab大容器
            });

            form.on('radio(isExpert)', function (data) {
                if (data.value == "1") {
                    $("#recommendDiv").show();
                } else {
                    $("#recommendDiv").hide();
                }
            });

            //自定义验证规则
            form.verify({
                username: function (value) {
                    var regex = /^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){1,19}$/;
                    if (value != "") {
                        if (!regex.test(value)) {
                            return "用户名只能以字母开头、可带数字和下划线";
                        } else if (regex.test(value)) {
                            var isRepeat = ajaxSubmit(value, '${member.id}', 'username');
                            if (isRepeat) {
                                return '用户名已存在';
                            }
                        }
                    }
                },
                realname: function (value) {
                    if (value == "") {
                        return '请填写昵称';
                    }
                },
                fullname: function (value) {
                    if (value == "") {
                        return '请填写真实姓名';
                    }
                },
                pic: function (value) {
                    if (value == "") {
                        return "请上传头像";
                    }
                },
                sex : function(value) {
                	if (value == "") {
                        return "请选择性别";
                    }
                },
                mobile: function (value) {
                    var regex = /^((13[0-9])|(14[5|7])|(15([0-9]))|(18[0-9])|(17[0-9]))\d{8}$/;
                    var username = $("#infoForm").find("[name=username]").val();
                    if (value == "" && username == "") {
                        return "用户名或手机号必须填写一个";
                    } else if (value != "" && !regex.test(value)) {
                        return "请填写正确格式的手机号码";
                    } else if (value != "" && regex.test(value)) {
                        var isRepeat = ajaxSubmit(value, '${member.id}', 'mobile');
                        if (isRepeat) {
                            return "手机号已存在";
                        }
                    }
                },
                qq : function (value) {
                	var regex = /^[1-9][0-9]{4,14}$/;
                	if (value != "" && !regex.test(value)) {
                		return "QQ号格式不正确";
                	}
                }
            });

            function ajaxSubmit(property, userId, type) {
                var isRepeat = false;
                $.ajax({
                    type: 'POST',
                    async: false, // 使用同步的方法
                    data: {
                        property: property,
                        userId: userId,
                        type: type
                    },
                    dataType: 'json',
                    success: function (result) {
                        isRepeat = !result;
                    },
                    url: '${ctx}/system/member/checkUniqueProperty.do'
                });
                return isRepeat;
            }

            //监听提交
            form.on('submit(infoForm)', function (data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#infoForm").attr("action");
                $.post(action, $('#infoForm').serialize(), function (res) {
                    if (res.success) {
                        layer.msg('提交成功', {icon: 1}, function (index) {
                            if (res.data == 1) {
                                location.href = "${ctx}/system/member/distributorList.do";
                            } else if (res.data == 2) {
                            	location.href = "${ctx}/system/member/partnerList.do";
                            } else if (res.data == 3) {
                                location.href = "${ctx}/system/member/memberList.do";
                            }
                        });
                    } else {
                        layer.msg('提交失败', {icon: 2});
                    }
                });
                return false;
            });

            //监听提交 修改密码
            form.on('submit(pwdForm)', function (data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#pwdForm").attr("action");
                $.post(action, $('#pwdForm').serialize(), function (res) {
                    if (res.success) {
                        top.layer.msg('密码修改成功', {icon: 1}, function (index) {
                            window.location.reload();
                        });
                    } else {
                        top.layer.msg('密码修改失败', {icon: 2});
                    }
                });
                return false;
            });

            // 城市
            form.on('select(province)', function (data) {
                $("#city").html("");
                var cityId = data.value;
                loadCityData(cityId);
            });

            // 行业
            form.on('select(industryParent)', function (data) {
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
                    "cityId": cityId
                }, function (data) {
                    var array = new Array();
                    for (var i = 0; i < data.length; i++) {
                        if ('${member.city}' == data[i].id) {
                            array.push("<option value = '" + data[i].id + "' selected='selected'>" + data[i].name + "</option>");
                        } else {
                            array.push("<option value = '" + data[i].id + "'>" + data[i].name + "</option>");
                        }
                    }
                    $("#city").append(array.join(""));
                    form.render('select');
                });
            }

            function loadIndustryData(industryId) {
                $.post("${ctx}/system/member/getIndustryByParentId.do", {
                    "industryId": industryId
                }, function (data) {
                    var array = new Array();
                    for (var i = 0; i < data.length; i++) {
                        if ('${member.industry}' == data[i].id) {
                            array.push("<option value = '" + data[i].id + "' selected='selected' >" + data[i].name + "</option>");
                        } else {
                            array.push("<option value = '" + data[i].id + "'>" + data[i].name + "</option>");
                        }
                    }
                    $("#industry").append(array.join(""));
                    form.render('select');
                });
            }
        });
    })
    function cancelFunction() {
    	if ('${type}' == '1') {
            location.href = "${ctx}/system/member/distributorList.do";
        } else if ('${type}' == '2') {
        	location.href = "${ctx}/system/member/partnerList.do";    
        } else if ('${type}' == '3') {
            location.href = "${ctx}/system/member/memberList.do";
        }
    }

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