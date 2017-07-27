<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>添加人员</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/form.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/circle/add_cmember.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/member_form.css">
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
							&nbsp;&gt;&nbsp;人员管理&nbsp;&gt;&nbsp;添加人员
						</span>
					</c:if>
					<c:if test="${not empty group.id}">
						<span class="title f20" title="${group.groupName}">我发布的赛事项目&nbsp;&gt;&nbsp;
							<c:if test="${fn:length(project.title) > 10}">${fn:substring(project.title,0,10)}...</c:if>
							<c:if test="${fn:length(project.title) <= 10}">${project.title}</c:if>
							&nbsp;&gt;&nbsp;
							<c:if test="${fn:length(group.groupName) > 10}">${fn:substring(group.groupName,0,10)}...</c:if>
							<c:if test="${fn:length(group.groupName) <= 10}">${group.groupName}</c:if>
							&nbsp;&gt;&nbsp;人员管理&nbsp;&gt;&nbsp;添加人员
						</span>
					</c:if>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<form id="infoForm" class="layui-form mt20" method="post" action="${ctx}/competition/member/saveMember.do">
				<input type="hidden" name="projectId" value="${project.id}" />
				<input type="hidden" name="groupId" value="${group.id}" />
				<input type="hidden" name="memberId" id="memberId" />
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">真实姓名<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input class="layui-input" name="realname" lay-verify="realname" placeholder="真实姓名" id="realName" autocomplete="off"  />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">头像<span class="f-verify-red">*</span></label>
					<div class="cover-content">
						<input type="hidden" name="logo" id="pic" lay-verify="pic" />
						<span id="cover-img" class="round-img" style="background-image:url(${ctx}/image/avatar1.png)"></span>
						<div class="u-single-upload">
							<input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加头像</span>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
	         		<div class="layui-inline">
	              		<label class="layui-form-label">性别</label>
	                  	<div class="layui-input-inline">
	                      	<input type="radio" name="sex" value="1" title="男" disabled="disabled">
	                      	<input type="radio" name="sex" value="0" title="女" disabled="disabled">
	                  	</div>
	              	</div>
		     	</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">手机号<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input name="mobile" lay-verify="mobile" class="layui-input" id="phone" autocomplete="off" >
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">公司</label>
						<div class="layui-input-inline">
							<input type="text" name="company" class="layui-input" id="company">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">职位</label>
						<div class="layui-input-inline">
							<input type="text" name="jobTitle" class="layui-input" id="jobTitle">
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
									<option value="${industry.id}">${industry.name}</option>
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
									<option value="${area.id}">${area.name}</option>
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
					<div class="layui-input-block">
						<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="infoForm" id="saveMember">立即提交</a>
						<a href="javascript:addMember()" class="layui-btn layui-btn-danger" id="addMember" style="display: none;">立即提交</a>
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
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/resize.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/bootstrap3-typeahead/bootstrap3-typeahead.js"></script>
<script>
	var flag = false;
	var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');
	var form = null;


	
	$(function() {
        layui.use([ 'form', 'laydate', 'element' ], function() {
            form = layui.form(), laydate = layui.laydate(), element = layui.element();

            form.verify({
                realname : function(value) {
                    if (value == "") {
                        return '请填写真实姓名';
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
                        return "手机号必须填写";
                    } else if (value != "" && !regex.test(value)) {
                        return "请填写正确格式的手机号码";
                    } else if (value != "" && regex.test(value)) {
                        var isRepeat = ajaxSubmit(value, '', 'mobile');
                        if (isRepeat) {
                            return "手机号已存在";
                        }
                    }
                }
            });

            function ajaxSubmit(property, userId, type) {
                var isRepeat = false;
                $.ajax({
                    type : 'POST',
                    async : false, // 使用同步的方法
                    data : {
                        property : property,
                        userId : userId,
                        type : type
                    },
                    dataType : 'json',
                    success : function(result) {
                        isRepeat = !result;
                    },
                    url : '${ctx}/system/member/checkUniqueProperty.do'
                });
                return isRepeat;
            }

            //监听提交
            form.on('submit(infoForm)', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#infoForm").attr("action");
                $.post(action, $('#infoForm').serialize(), function(res) {
                    if (res.success) {
                        layer.msg('提交成功', {
                            icon : 1
                        }, function(index) {
                            if ('${project.id}' != "" && '${group.id}' == "") {
                                location.href = "${ctx}/competition/member/list.do?projectId=${project.id}";
                            } else if ('${group.id}' != "") {
                                location.href = "${ctx}/competition/member/list.do?pGroupId=${group.id}";
                            }
                        });
                    } else {
                        layer.msg('提交失败', {
                            icon : 2
                        });
                    }
                });
                return false;
            });

            // 城市
            form.on('select(province)', function(data) {
                $("#city").html("");
                var cityId = data.value;
                loadCityData(cityId, '');
            });

            // 行业
            form.on('select(industryParent)', function(data) {
                $("#industry").html("");
                var industryId = data.value;
                loadIndustryData(industryId, '');
            });

            $('#realName').typeahead({
                source : function(query, process) {
                    var value = $("#realName").val();
                    searchData('realName', value, process);
                },
                updater : function(item) {
                    updater(item, 'realName');
                }
            });

            $('#phone').typeahead({
                source : function(query, process) {
                    var value = $("#phone").val();
                    searchData('phone', value, process);
                },
                updater : function(item) {
                    updater(item, 'phone');
                }
            });
        });
		$("#realName").blur(function() {
			if(flag == false) {
				insert('realName');
			}
		});
		
		$("#phone").blur(function() {
			if(flag == false) {
				insert('phone');
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
	
	// 联动城市
	function loadCityData(arParent, cityId) {
		var array = new Array();
		if (arParent == "") {
			$("#city").html("");
			array.push("<option value=''>选择城市</option>");
			$("#city").append(array.join(""));
			form.render('select');
		} else {
			$.post("${ctx}/system/member/getCityByParentId.do", {
				"cityId" : arParent
			}, function(data) {
				for (var i = 0; i < data.length; i++) {
					if (cityId == data[i].id) {
						array.push("<option value = '" + data[i].id + "' selected='selected'>" + data[i].name + "</option>");
					} else {
						array.push("<option value = '" + data[i].id + "'>" + data[i].name + "</option>");
					}
				}
				$("#city").append(array.join(""));
				form.render('select');
			});
		}
	}

	// 联动行业
	function loadIndustryData(inParent, industryId) {
		var array = new Array();
		if (inParent == "") {
			$("#industry").html("");
			array.push("<option value=''>选择行业</option>");
			$("#industry").append(array.join(""));
			form.render('select');
		} else {
			$.post("${ctx}/system/member/getIndustryByParentId.do", {
				"industryId" : inParent
			}, function(data) {
				for (var i = 0; i < data.length; i++) {
					if (industryId == data[i].id) {
						array.push("<option value = '" + data[i].id + "' selected='selected' >" + data[i].name + "</option>");
					} else {
						array.push("<option value = '" + data[i].id + "'>" + data[i].name + "</option>");
					}
				}
				$("#industry").append(array.join(""));
				form.render('select');
			});
		}
	}
	
	function searchData(property, value, process) {
		flag = false;
		$.ajax({
			url : "${ctx}/system/member/findMemberByPhoneOrName.do",
			type : "post",
			dataType : "json",
			data : {
				property : property,
				value : value
			},
			success : function(ret) {
				var list = ret.data;
				if (list.length == 0) {
					if(property == "phone"){
						var realName = $("#realName").val();
						if(realName == ""){
							insert();
						}
					} else if(property == "realName"){
						var phone = $("#phone").val();
						if(phone == ""){
							insert();
						}
					}
				}
				var results = $.map(list, function(member) {
					var mobile = member.mobile ? '(' + member.mobile + ')' : '';
					var realname = member.realname ? member.realname : '';
					return '<span data-id="' + member.id + '" class="t-user-logo" style="background-image: url(' + member.logo
							+ '),url(${ctx}/image/def_user_logo.png)"></span><span class="t-user-name">' + realname + '' + mobile + '</span>';
				});
				if(typeof process == 'function'){
					process(results);
				}
			}
		});
	}

	function updater(item, property) {
		flag = true;
		var divObj = document.createElement("div");
		divObj.innerHTML = item;
		var id = $(divObj).find('.t-user-logo').data('id');
		$.ajax({
			url : '${ctx}/system/member/getMember.do',
			type : 'POST',
			dataType : 'JSON',
			data : {
				id : id
			},
			success : function(ret) {
				var member = ret.data.member;
				var inParent = ret.data.inParent;
				var arParent = ret.data.arParent;
				$("#infoForm").find("input[type=text]").prop("disabled", true);
				$("#infoForm").find("select").prop("disabled", true);
				
				$("input[name=sex][value="+member.sex+"]").attr("checked",true);
				
				form.render('radio');
				
				if(property == "realName"){
					$("#phone").prop("disabled",true);
				} else if(property == "phone"){
					$("#realName").prop("disabled",true);
				}
				
				var logo = member.logo || '';
				var realname = member.realname || '';
				var phone = member.mobile || '';
				var company = member.company || '';
				var jobTitle = member.jobTitle || '';
				var cityId = member.city || '';
				var industryId = member.industry || '';
				$("#cover-img").css('background-image', "url('" + logo + "')");
				$("#pic").val(logo);
				$("#realName").val(realname);
				$("#phone").val(phone);
				$("#company").val(company);
				$("#jobTitle").val(jobTitle);
				$("#inParent").val(inParent);
				$("#arParent").val(arParent);
				$("#memberId").val(member.id);

				loadCityData(arParent, cityId);
				loadIndustryData(inParent, industryId);

				$(".u-single-upload").hide();

				$("#addMember").show();
				$("#saveMember").hide();
			}
		});
	}

	function insert(property) {
		$("#infoForm").find("input[type=text]").prop("disabled", false);
		$("#infoForm").find("select").removeAttr("disabled");
		$("#infoForm").find("select").find("option:eq(0)").prop("selected",true);
		$("#infoForm").find("input[type=text]").val("");
		$("#infoForm").find("input[type=radio]").removeAttr("disabled");
		$("#infoForm").find("input[type=radio]").removeAttr("checked");
		if(property != ''){
			if(property == 'realName'){
				$("#phone").prop("disabled",false);
			} else if(property == 'phone'){
				$("#realName").prop("disabled",false);
			}
		}
		$("#memberId").val("");
		$(".u-single-upload").show();
		$("#addMember").hide();
		$("#saveMember").show();
		$("#city").html("<option value=''>选择城市</option>");
		$("#industry").html("<option value=''>选择行业</option>");
		form.render('select');
		form.render('radio');
	}
	
	// 添加成员
	function addMember() {
		$.post('${ctx}/competition/member/saveMember.do', {
			projectId : '${project.id}',
			groupId : '${group.id}',
			memberId : $("#memberId").val()
		}, function(res) {
			if (res.success) {
				layer.msg('添加成功', {
					icon : 1,
					time : 1000
				}, function(index) {
					layer.close(index);
					
					if ('${project.id}' != "" && '${group.id}' == "") {
						location.href = "${ctx}/competition/member/list.do?projectId=${project.id}";
					} else if ('${group.id}' != "") {
						location.href = "${ctx}/competition/member/list.do?pGroupId=${group.id}";
					}
				});
			} else {
				layer.msg('添加失败', {
					icon : 2,
					time : 1000
				}, function(index) {
					layer.close(index);
					location.reload();
				});
			}
		});
	}
</script>
</body>
</html>