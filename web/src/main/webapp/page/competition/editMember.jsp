<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>编辑人员</title>
    <%@include file="../include/commonFile.jsp" %>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/form.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/circle/add_cmember.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/member_form.css">
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
                    <c:if test="${not empty project.id && empty group.id}">
                        <a href="${ctx}/competition/member/list.do?projectId=${project.id}" class="layui-btn layui-btn-radius layui-btn-danger">
                            <i class="iconfont icon-refresh btn-icon"></i>返回
                        </a>
                    </c:if>
                    <c:if test="${not empty group.id}">
                        <a href="${ctx}/competition/member/list.do?pGroupId=${group.id}" class="layui-btn layui-btn-radius layui-btn-danger">
                            <i class="iconfont icon-refresh btn-icon"></i>返回
                        </a>
                    </c:if>
                </div>
                <div class="ovh">
                    <c:if test="${not empty project.id && empty group.id}">
						<span class="title f20" title="${project.title}">我发布的赛事项目&nbsp;&gt;&nbsp;
							<c:if test="${fn:length(project.title) > 20}">${fn:substring(project.title,0,20)}...</c:if>
							<c:if test="${fn:length(project.title) <= 20}">${project.title}</c:if>
							&nbsp;&gt;&nbsp;人员管理&nbsp;&gt;&nbsp;编辑人员
						</span>
                    </c:if>
                    <c:if test="${not empty group.id}">
						<span class="title f20" title="${group.groupName}">我发布的赛事项目&nbsp;&gt;&nbsp;
							<c:if test="${fn:length(project.title) > 10}">${fn:substring(project.title,0,10)}...</c:if>
							<c:if test="${fn:length(project.title) <= 10}">${project.title}</c:if>
							&nbsp;&gt;&nbsp;
							<c:if test="${fn:length(group.groupName) > 10}">${fn:substring(group.groupName,0,10)}...</c:if>
							<c:if test="${fn:length(group.groupName) <= 10}">${group.groupName}</c:if>
							&nbsp;&gt;&nbsp;人员管理&nbsp;&gt;&nbsp;编辑人员
						</span>
                    </c:if>
                </div>
            </div>
            <!-- 正文请写在这里 -->
            <form id="infoForm" class="layui-form mt20" method="post" action="${ctx}/competition/member/updateCMember.do">
                <input type="hidden" name="projectId" value="${project.id}"/>
                <input type="hidden" name="groupId" value="${group.id}"/>
                <input type="hidden" name="id" value="${competitionMember.id}"/>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">昵称</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" value="${member.realname}"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">真实姓名</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" value="${member.fullname}"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">头像</label>
                    <div class="cover-content">
                        <span id="cover-img" class="cover-img" style="background-image:url('${member.logo}'),url(${ctx}/image/avatar1.png)"></span>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">性别</label>
                        <div class="layui-input-inline">
                            <input type="radio" name="sex" value="1" title="男" ${not empty member.sex && member.sex == '1' ? 'checked="checked"' : ''} disabled="disabled">
                            <input type="radio" name="sex" value="0" title="女" ${not empty member.sex && member.sex == '0' ? 'checked="checked"' : ''} disabled="disabled">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">手机号<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" value="${member.mobile}">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">公司</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" value="${member.company}">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">职位</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" value="${member.jobTitle}">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">行业</label>
                        <div class="layui-input-inline">
                            <select lay-verify="industryParent" lay-filter="industryParent" id="inParent">
                                <option value="">选择行业分类</option>
                                <c:forEach var="industry" items="${industries}">
                                    <option value="${industry.id}" ${inParent == industry.id ? 'selected="selected"' : ''}>${industry.name}</option>
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
                            <select lay-verify="province" lay-filter="province" id="arParent">
                                <option value="">选择省份/直辖市</option>
                                <c:forEach var="area" items="${areas}">
                                    <option value="${area.id}" ${arParent == area.id ? 'selected="selected"' : ''}>${area.name}</option>
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
                        <label class="layui-form-label">号码牌<span class="f-verify-red">*</span></label>
                        <div class="layui-input-inline">
                            <input name="number" class="layui-input" lay-verify="numberPai" value="${competitionMember.number}">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="infoForm" id="saveMember">立即提交</a>
                        <c:if test="${not empty project.id && empty group.id}">
                            <a href="${ctx}/competition/member/list.do?projectId=${project.id}" class="layui-btn layui-btn-primary">取消</a>
                        </c:if>
                        <c:if test="${not empty group.id}">
                            <a href="${ctx}/competition/member/list.do?pGroupId=${groupId.id}" class="layui-btn layui-btn-primary">取消</a>
                        </c:if>
                    </div>
                </div>
            </form>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script>
    $(function () {
        layui.use(['form', 'laydate', 'element'], function () {
            var form = layui.form(), laydate = layui.laydate(), element = layui.element();

            form.verify({
                numberPai: function (value) {
                    if (value == "") {
                        return '请输入号码牌';
                    }

                    var isRepeat = ajaxSubmit(value, '${project.id}', '${competitionMember.id}');
                    if (!isRepeat) {
                        return "号码牌已存在，请重新输入";
                    }
                }
            });

            form.on('submit(infoForm)', function (data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#infoForm").attr("action");
                $.post(action, $('#infoForm').serialize(), function (res) {
                    if (res.success) {
                        layer.msg("编辑成功", {
                            icon: 1
                        }, function (index) {
                            if ('${project.id}' != "" && '${group.id}' == "") {
                                location.href = "${ctx}/competition/member/list.do?projectId=${project.id}";
                            } else if ('${group.id}' != "") {
                                location.href = "${ctx}/competition/member/list.do?pGroupId=${group.id}";
                            }
                        });
                    } else {
                        layer.msg('编辑失败', {
                            icon: 2
                        })
                    }
                });
                return false;
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

            $("input[type=text]").attr("disabled", "disabled");
            $("select").attr("disabled", "disabled");
        });
    })
    function ajaxSubmit(numberPai, projectId, cMemberId) {
        var isRepeat = false;
        $.ajax({
            type: 'POST',
            async: false, // 使用同步的方法
            data: {
                numberPai: numberPai,
                projectId: projectId,
                cMemberId: cMemberId
            },
            dataType: 'json',
            success: function (data) {
                isRepeat = data;
            },
            url: '${ctx}/competition/member/checkNumberPai.do'
        });
        return isRepeat;
    }

</script>
</body>
</html>