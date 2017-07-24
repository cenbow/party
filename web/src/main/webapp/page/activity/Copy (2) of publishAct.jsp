<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>${activity == null ? '发布' : '编辑'}活动</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_act.css">
<style type="text/css">
.place-text {
	width: 200px;
	float: left;
	margin-right: 10px;
}

.post_site_K {
	width: 350px;
	height: 36px;
	border: 1px solid #d9d9d9;
	float: left;
}

.post_site_K input {
	width: 250px;
	height: 100%;
	line-height: 100%;
	border: none;
	font-size: 14px;
	color: #444;
	float: left;
	margin: 8px 0 0 15px;
	margin-top: 0px;
}

.post_site_K span {
	display: block;
	cursor: pointer;
	width: 72px;
	height: 20px;
	line-height: 19px;
	font-size: 14px;
	color: #666;
	text-align: right;
	border-left: 1px solid #d9d9d9;
	float: left;
	margin-top: 8px;
	background:
		url(http://cdn.hudongba.com/static_v4/images/detail/address_icon3.png)
		no-repeat 10px 1px;
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
					<a href="${ctx}/activity/activity/activityList.do" class="layui-btn layui-btn-radius layui-btn-danger">
						<i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">我发布的活动&nbsp;&gt;&nbsp;${activity == null ? '发布' : '编辑'}活动</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/activity/activity/save.do">
				<input type="hidden" name="isCrowdfunded" value="0" />
				<div class="layui-form-item">
					<label class="layui-form-label">活动标题<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="title" lay-verify="title" style="width: 80%" placeholder="活动标题" class="layui-input"
							value="${activity.title}"> <input type="hidden" name="id" value="${activity.id}" />
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
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">报名截止时间<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input class="layui-input" name="endDate" lay-verify="endDate" placeholder="报名截止时间" id="endTime"
								value='<fmt:formatDate value="${activity.endTime}" pattern="yyyy-MM-dd HH:mm" />' />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">活动开始时间<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input class="layui-input" name="startDate" lay-verify="startDate" placeholder="活动开始时间" id="startTime"
								value='<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm" />' />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">活动地点<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<select name="cityId" lay-verify="city" lay-filter="city">
								<option value="">选择城市</option>
								<c:forEach var="city" items="${citys}">
									<option value="${city.id}" ${activity.cityId == city.id ? 'selected="selected"' : ""}>${city.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="layui-input-inline">
							<select name="areaSelect" lay-verify="area_select" id="areaSelect" lay-filter="area">
								<option value="">选择区域</option>
							</select> <input name="areaInput" type="text" id="areaInput" lay-verify="area_input" placeholder="填写区域" class="layui-input"
								value="${activity.area}" style="display: none;" />
						</div>
						<div class="post_site_K">
							<input type="text" placeholder="填写活动场所" id="place" name="place" value="${activity.place}" readonly="readonly">
							<input type="hidden" name="lat" id="lat" value="${activity.lat}" />
							<input type="hidden" name="lng" id="lng" value="${activity.lng}" />
							<span id="fadeIn" onclick="" style="display: none;cursor: pointer;">已标记</span>
						</div>
					</div>
				</div>
				<div id="mapDiv" class="layui-form-item" style="display: none;">
					<label class="layui-form-label"></label>
					<div class="layui-input-block">
						<div id="allmap" class="post_map"></div>
						<div class="field_list_wrap_big" style="display: block; left: 540px;">
							<div class="close" onclick="width_auto($(this));">
								<span style="background: url('${ctx}/image/activity/field_IconSprite4.png') 0px 0px no-repeat scroll;"></span>
							</div>
							<div class="field_list_wrap">
								<div class="top">场地列表</div>
								<div class="field_box" id="panel">
									<div style="position: relative; height: 100%; max-width: 100%;">
										<div style="position: relative;">
											<ul class="field_ul"></ul>
											<div class="field_fy" style="display: block;">
												<div class="field_fy_wrap"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">人数上限<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" name="limitNum" lay-verify="limitNum" class="layui-input" value="${activity.limitNum}">
						</div>
					</div>
				</div>
<!-- 				<div class="layui-form-item"> -->
<!-- 					<label class="layui-form-label">报名费用</label> -->
<!-- 					<div class="layui-input-block"> -->
<!-- 						<input type="radio" name="isFree" lay-filter="isFree" id="noFree" value="noFree" title="收费活动" -->
<%-- 							${activity == null || activity.price !=0.0 ? 'checked="checked"' : ''}> --%>
<!-- 						<input type="radio" name="isFree" lay-filter="isFree" id="free" value="free" title="免费活动" -->
<%-- 							${activity != null && activity.price == 0.0 ? 'checked="checked"' : ''}> --%>
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="layui-form-item">
					<label class="layui-form-label">报名费用<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<%@include file="../include/counterfoil.jsp" %>
					</div>
				</div>
<!-- 				<div class="layui-form-item"> -->
<!-- 					<div class="layui-inline"> -->
<!-- 						<label class="layui-form-label">活动金额<span class="f-verify-red">*</span></label> -->
<!-- 						<div class="layui-input-inline"> -->
<%-- 							<c:if test="${activity == null || activity.price != 0.0}"> --%>
<%-- 								<input type="text" name="price" lay-verify="price" placeholder="￥" class="layui-input" value="${activity.price}" /> --%>
<%-- 							</c:if> --%>
<%-- 							<c:if test="${activity != null && activity.price == 0.0}"> --%>
<!-- 								<input type="text" name="price" lay-verify="price" placeholder="￥" class="layui-input" disabled="disabled" /> -->
<%-- 							</c:if> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="layui-form-item">
					<label class="layui-form-label">隐藏报名人员<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="radio" name="joinHidden" value="1" title="是" ${activity == null || activity.joinHidden == 1 ? 'checked="checked"' : ''}>
						<input type="radio" name="joinHidden" value="0" title="否" ${activity != null && activity.joinHidden == 0 ? 'checked="checked"' : ''}>
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

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${ak}"></script>
<script>
	var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');
	var ue = UE.getEditor('ueditor1');

	var map = new BMap.Map("allmap", {
		enableMapClick : false
	});
	map.enableScrollWheelZoom(); //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom(); //启用地图惯性拖拽，默认禁用
	map.enableScrollWheelZoom(true);
	// 添加带有定位的导航控件
	var navigationControl = new BMap.NavigationControl({
		// 靠左上角位置
		anchor : BMAP_ANCHOR_TOP_LEFT,
		// LARGE类型
		type : BMAP_NAVIGATION_CONTROL_LARGE,
		// 启用显示定位
		enableGeolocation : true
	});
	map.addControl(navigationControl);

	layui.use([ 'form', 'laydate' ], function() {
		var form = layui.form(), laydate = layui.laydate;
		var radioValue = null;

		form.on('radio(isFree)', function(data) {
			radioValue = data.value;
			if (radioValue == "noFree") {
				$("[name=price]").prop("disabled", false);
			} else {
				$("[name=price]").val("");
				$("[name=price]").prop("disabled", true);
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
			area_select : function(value) {
				var display = $("#areaInput").css("display");
				if (value == "" && display == "none") {
					return "请设置活动区域";
				}
			},
			area_input : function(value) {
				var display = $("#areaInput").css("display");
				if (value == "" && display == "block") {
					return "请设置活动区域";
				}
			},
			city : function(value) {
				if (value == "") {
					return "请选择活动举办的城市";
				}
			},
			// 	       	lat : function(value){
			// 	       		if(value == ""){
			// 	       			return "请选择一个活动场所"
			// 	       		}
			// 	       	},
			// 	        activityType : function (value) {
			// 	        	if (value == ""){
			// 	        		return "请设置活动类型";
			// 	        	}
			// 	        },
			limitNum : function(value) {
				if (value == "") {
					return "请设置活动人数上限";
				} else if (!util.checkNumber(value)) {
					return "请输入正确的金额";
				} else if (parseInt(value) == 0) {
					return "请输入大于0的数字";
				}
			},
			price : function(value) {
				var reg = /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
				if (radioValue == null) {
					var type = $("[name=isFree]:checked").val();
					if (type == "noFree") {
						if (value == "") {
							return "请设置活动报名金额";
						} else if (!reg.test(value)) {
							return "请输入正确的金额";
						}
					}
				} else {
					if (radioValue == "noFree") {
						if (value == "") {
							return "请设置活动报名金额";
						} else if (!reg.test(value)) {
							return "请输入正确的金额";
						}
					}
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

		// 城市
		form.on('select(city)', function(data) {
			var $select = $("[name=cityId]").siblings(".layui-form-select");
			cityName = $select.find(".layui-this").text();
			loadCityData(cityName);
			map.centerAndZoom(cityName, 12);
			
			clearPlace();
		});

		// 区域
		form.on('select(area)', function(data) {
			var value = data.value;
			if (value != "") {
				map.centerAndZoom(value, 16);
			}
			clearPlace();
		});
		
		$("#areaInput").blur(function() {
			var value = $(this).val();
			if (value != "") {
				$("#mapDiv").show();
				map.centerAndZoom(value, 15);
				$("#place").removeAttr("readonly");
			} else {
				clearPlace();
			}
		});
		
		$("#place").blur(function() {
			var value = $(this).val();
			if (value != "") {
				appendListData(null);
			} else {
				clearPlace();
			}
		});

		if ('${cityName}' != "") {
			loadCityData('${cityName}');
		}
		
		if ('${activity.area}' != ""){
			$("#mapDiv").show();
			map.centerAndZoom('${activity.area}', 16);
			$("#place").removeAttr("readonly");
			
			appendListData(null);
		}

		if ('${activity.place}' != '') {
			var $select = $("[name=cityId]").siblings(".layui-form-select");
			var cityName = $select.find(".layui-select-title .layui-input").val();
			$("#mapDiv").show();
			map.centerAndZoom(cityName, 16);
			appendListData(null);
			containerHidden();
		}

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
			// 				var eddt = new Date('${endTime}'.replace("-", "/"));
			// 				if (eddt > new Date()) {
			// 					start.min = laydate.now();
			// 				} else {
			// 					start.min = '${endTime}';
			// 				}
			// 			}
			start.elem = this;
			laydate(start);
		}
		document.getElementById('endTime').onclick = function() {
			// 			if ('${endTime}' != "") {
			// 				var eddt = new Date('${endTime}'.replace("-", "/"));
			// 				if (eddt > new Date()) {
			// 					end.min = laydate.now();
			// 				} else {
			// 					end.min = '${endTime}';
			// 				}
			// 			}
			end.elem = this
			laydate(end);
		}
		
		// 加载Select数据
		function loadCityData(cityName) {
			$("#areaSelect").html("");
			$.post("${ctx}/activity/activity/getAreaByCityName.do", {
				"cityName" : cityName
			}, function(data) {
				var array = new Array();
				if (data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						if ('${activity.area}' == data[i].name) {
							array.push("<option value = '"+data[i].name+"' selected='selected'>" + data[i].name + "</option>");
						} else {
							array.push("<option value = '"+data[i].name+"'>" + data[i].name + "</option>");
						}
					}
					$("#areaSelect").append(array.join(""));
					form.render('select');
					$("#areaInput").hide();

					var $select = $("#areaSelect").siblings(".layui-form-select");
					var areaName = $select.find(".layui-select-title .layui-input").val();
					$("#mapDiv").show();
					map.centerAndZoom(areaName, 16);
					$("#place").removeAttr("readonly");
				} else {
					$("#areaInput").show();
					array.push("<option value = ''>选择区域</option>");
					$("#areaSelect").append(array.join(""));
					form.render('select');
					$("#areaInput").siblings(".layui-form-select").hide();
				}
			});
		}
	});
	
	containerHidden();
	
	$(function() {
		$("#fadeIn").click(function() {
			$("#mapDiv").toggle(1000);
		});

		$(".field_ul").delegate('.field_li', 'click', function(e) {
			map.clearOverlays();//清空原来的标注
			var $target = e.target;
			var parent = $target.closest(".field_li");
			$(parent).siblings(".field_li").removeClass("current_hover");
			$(parent).addClass("current_hover");
			var jsonStr = $(parent).attr("data-json");
			var jsonObject = JSON.parse(jsonStr);
			selectAddress(jsonObject, 'add');
			
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

	function clickFunction(obj) {
		if (obj != undefined) {
			var jsonStr = obj.attr("data-json");
			var jsonObject = JSON.parse(jsonStr);
			$("#lng").val(jsonObject.location.lng);
			$("#lat").val(jsonObject.location.lat);
			$("#place").val(jsonObject.name);
		}
	}

	function containerHidden() {
		$('.field_list_wrap').animate({
			'width' : '0px'
		}, 0, function() {
			$('.field_list_wrap').hide();
			$('.field_list_wrap_big').css('left', '782px');
			$('.field_list_wrap_big .close span').css("background", "url('${ctx}/image/activity/field_IconSprite4.png') no-repeat -20px 0px");
			$('.field_btn a').css('right', '10px');
		})
	}

	function containerVisible() {
		$('.field_list_wrap').animate({
			'width' : '240px'
		}, 0, function() {
			$('.field_list_wrap').show();
			$('.field_list_wrap_big').css('left', '540px');
			$('.field_list_wrap_big .close span').css("background", "url('${ctx}/image/activity/field_IconSprite4.png') no-repeat 0px 0px");
			$('.field_btn a').css('right', '250px');
		})
	}

	function width_auto(that) {
		if (that.children('span').css('background-position') == '0px 0px') {
			containerHidden();
		} else {
			containerVisible();
		}
	}

	function gotoPage(obj) {
		$("field_fy_wrap a").removeClass("current");
		$(obj).addClass("current");
		var page = parseInt($(obj).text());

		appendListData(page);
	}

	// 拼接列表数据
	function appendListData(page) {
		var $select = $("[name=cityId]").siblings(".layui-form-select");
		var cityName = $select.find(".layui-this").text(); 
		
		var $select = $("#areaSelect").siblings(".layui-form-select");
		var areaName = $select.find(".layui-select-title .layui-input").val();
		if (areaName == "") {
			areaName = $("#areaInput").val();
		}
		areaName = areaName + $("#place").val();

		$.ajax({
			url : '${ctx}/map/map/search.do',
			type : 'POST',
			async : false, // 使用同步的方法
			data : {
				region : cityName,
				query : areaName,
				pageNum : page
			},
			dataType : 'json',
			success : function(data) {
				var dataResults = data.data.results;
				var array = new Array();
				$(".field_ul").html("");
				if (dataResults.length > 0) {
					for (var i = 0; i < dataResults.length; i++) {
						var jsonStr = JSON.stringify(dataResults[i]);
						jsonStr = jsonStr.replace(new RegExp("\"", "gm"), "&quot;");
						jsonStr = jsonStr.replace(new RegExp("'", "gm"), "&#39;");
						array.push("<li class='field_li' data-json='"+jsonStr+"'>");
						array.push("<a href='javascript:void(0);'>");
						array.push("<div class='box'><h2>" + dataResults[i].name + "</h2>");
						if (dataResults[i].address != undefined) {
							array.push("<p class='address'>地址:" + dataResults[i].address + "</p>");
						}
						array.push("</div>");
						array.push("<div class='number'>" + (i + 1) + "</div></a></li>");
					}

					$(".field_ul").append(array.join(""));

					$(".field_fy_wrap").html("");
					var pageArray = new Array();
					for (var i = 0; i < data.data.totalPage; i++) {
						if (data.data.pageNum == i) {
							pageArray.push("<a class='current' onclick='gotoPage($(this))'>" + (i + 1) + "</a>");
						} else {
							pageArray.push("<a class='' onclick='gotoPage($(this))'>" + (i + 1) + "</a>");
						}
					}
					$(".field_fy_wrap").append(pageArray.join(""));
					
					containerVisible();
					
					var location = dataResults[0].location;
					if(location != undefined) {
						$("#lat").val(location.lat);
						$("#lng").val(location.lng);
						map.centerAndZoom(new BMap.Point(location.lng, location.lat), 18);
						selectAddress(dataResults[0], "edit");
					}
				} else {
					$(".field_fy_wrap").html("没有找到符合的数据");
					
					var lng = $("#lng").val();
					var lat = $("#lat").val();
					if (lng != '' && lat != '' && '${activity.id}' != "") {
						map.centerAndZoom(new BMap.Point(lng, lat), 18);
						$.ajax({
							url : '${ctx}/map/map/getAddress.do',
							type : 'POST',
							async : false, // 使用同步的方法
							data : {
								x : lng,
								y : lat
							},
							dataType : 'json',
							success : function(data) {
								var dataResults = data.data.result;
								var resultObject = {
									location : dataResults.location,
									name : $("#place").val(),
									address : dataResults.formatted_address + dataResults.sematic_description
								};
								selectAddress(eval(resultObject), "edit");
							}
						});
					}
				}

			}
		});
		
		toggleFadeIn();
	}
	
	function toggleFadeIn(){
		if($("#lat").val() != ""){
			$("#fadeIn").css("display","block");
		} else {
			$("#fadeIn").css("display","none");
		}
	}
	
	function clearPlace(){
		$("#place").val("");
		$("#lat").val("");
		$("#lng").val("");
		$(".field_ul").html("");
		$(".field_fy_wrap").html("");
		containerHidden();
		toggleFadeIn();
	}
	
	function selectAddress(obj, type) {
		var lng = obj.location.lng;
		var lat = obj.location.lat;
		var new_point = new BMap.Point(lng, lat);
		var marker = new BMap.Marker(new_point); // 创建标注，为要查询的地方对应的经纬度

		$("#place").val(obj.name);
		$("#lat").val(lat);
		$("#lng").val(lng);

		var array = new Array();
		array.push("<div class='activity-location'><div class='address-name'>" + obj.name + "</div>");
		array.push("<table cellspacing='0' ><tbody>");
		if (obj.address != undefined) {
			array.push("<tr><td>地址：  </td><td>" + obj.address + "</td></tr>");
		}
		// 		array.push("<tr><td></td><td><a class='set-address' data-json='" + JSON.stringify(obj)
		// 				+ "' onclick='clickFunction($(this))'>选为我的活动地点</a></td></tr>");
		array.push("</tbody></table>")
		array.push("</div>");

		map.addOverlay(marker);
		map.panTo(new_point);

		openInfo(array.join(""), lng, lat);

		addClickHandler(array.join(""), marker);

		function addClickHandler(content, marker) {
			marker.addEventListener("click", function(e) {
				var p = e.target;
				openInfo(content, p.getPosition().lng, p.getPosition().lat);
			});
		}

		function openInfo(content, lng, lat) {
			var point = new BMap.Point(lng, lat);
			var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
			map.openInfoWindow(infoWindow, point); //开启信息窗口

		}
		if (type != "edit") {
			layer.msg("标记成功", {
				icon : 6
			});
			
			toggleFadeIn();
		}
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