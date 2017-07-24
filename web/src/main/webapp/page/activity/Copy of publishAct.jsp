<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>发布活动</title>
<%@include file="../include/commonFile.jsp" %>
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/publish_act.css">
<style type="text/css">
.field_list_wrap_big{width:260px; position: absolute;left: 630px; text-align: left;}
.field_list_wrap{float: right; width: 240px; border: 1px solid #cacaca;}
.field_list_wrap .top{ height: 35px; overflow:hidden; line-height: 40px; padding-left: 10px; font-size: 14px; color: #444; background: url(http://cdn.hudongba.com/static_v4/images/other/field_IconSprite5.png);}
.field_list_wrap .field_box{ height: 467px; overflow-y: auto; background: url(http://cdn.hudongba.com/static_v4/images/other/field_IconSprite5.png);}
.field_list_wrap .field_li a{ display: block; position: relative; padding-top: 10px;}
.field_list_wrap .field_li a.current,.field_list_wrap .field_li a:hover,.current_hover{ background: #f2fbff;}
.field_list_wrap .field_li .box{ border-bottom: 1px solid #ddd; padding: 0 5px 10px 25px; overflow: hidden; margin-left: 10px;}
.field_list_wrap .field_li .border_n{ border: none;}
.field_list_wrap .field_li .number{ position: absolute; top: 8px; left: 10px; width: 17px; height: 24px; line-height: 20px; text-align: center; font-size: 12px; color: #fff; background: url(http://cdn.hudongba.com/static_v4/images/other/address_bg.png) no-repeat;}
.field_list_wrap .field_li h2{ line-height: 20px; font-size: 14px; color: #0099e9; margin-bottom: 5px;}
.field_list_wrap .field_li .tel{ font-size: 12px; color: #999; line-height: 20px;}
.field_list_wrap .field_li .address{ font-size: 12px; color: #999; line-height: 16px;}
.field_list_wrap .field_fy{text-align: center;}
.field_list_wrap .field_fy_wrap{ overflow: hidden; margin: 10px auto; display: inline-block;}
.field_list_wrap .field_fy a{ height: 18px; line-height: 18px; padding: 0 5px; font-size: 12px; color: #0099e9; float: left; margin: 0 2px; border: 1px solid #ebebeb;}
.field_list_wrap .field_fy a:hover{ border-color: #0099e9;cursor: pointer;}
.field_list_wrap .field_fy .dian{ padding: 0 2px; text-align: center; color: #999; border: none;}
.field_list_wrap .field_fy .current{ background: #0099e9; border: 1px solid #0099e9; color: #fff;}
.field_list_wrap_big .close{ position: absolute; top: 0; width: 17px; height: 62px; border: 1px solid #cacaca; border-right:none; margin-top:210px; background: url(http://cdn.hudongba.com/static_v4/images/other/field_IconSprite5.png);border-radius: 2px 0 0 2px; cursor: pointer;}
.field_list_wrap_big .close span{ width: 7px; height: 11px; background: url(http://cdn.hudongba.com/static_v4/images/other/field_IconSprite4.png) no-repeat 0px 0; margin: 25px 5px; display: inline-block;}


.post_map {width:890px;height:512px;float:left;}

</style>
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
	<!--内容-->
	<section>
		<div class="section-main">
			<!-- 正文请写在这里 -->
			<form id="myForm" class="layui-form" method="post" action="${ctx}/activity/activity/save.do">
				<div class="layui-form-item">
					<label class="layui-form-label">活动标题<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="title" lay-verify="title" style="width: 85%" autocomplete="off" placeholder="活动标题（不少于5个字）" class="layui-input" value="${activity.title}">
						<input type="hidden" name="id" value="${activity.id}"/>
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
							<input class="layui-input" name="endDate" lay-verify="endDate" placeholder="报名截止时间" id="endTime" value='<fmt:formatDate value="${activity.endTime}" pattern="yyyy-MM-dd HH:mm" />' />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">活动开始时间<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input class="layui-input" name="startDate" lay-verify="startDate" placeholder="活动开始时间" id="startTime" value='<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm" />' />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">活动地点<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<select name="cityId" lay-verify="city" lay-filter="city" >
								<option value="">选择城市</option>
								<c:forEach var="city" items="${citys}">
									<option value="${city.id}" ${activity.cityId == city.id ? 'selected="selected"' : ""} >${city.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="layui-input-inline">
							<input type="text" id="area" name="area" lay-verify="area" autocomplete="off" placeholder="区域" class="layui-input" value="${activity.area}"/>
						</div>
						<div class="layui-input-inline">
							<input type="text" id="place" name="place" lay-verify="place" autocomplete="off" placeholder="活动场所" value="${activity.place}" class="layui-input"  style="width: 400px;"  />
							<input type="hidden" name="lat" id="lat" lay-verify="lat" value="${activity.lat}" />
							<input type="hidden" name="lng" id="lng" value="${activity.lng}" />
						</div>
					</div>
				</div>
				<div id="mapDiv" class="layui-form-item" style="display: none;">
					<label class="layui-form-label"></label>
					<div class="layui-input-block">
						<div id="allmap" class="post_map" ></div>
						<div class="field_list_wrap_big" style="display: block; left: 630px;">
							<div class="close" onclick="width_auto($(this));">
								<span style="background: url(&quot;http://cdn.hudongba.com/static_v4/images/other/field_IconSprite4.png&quot;) 0px 0px no-repeat scroll;"></span>
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
						<label class="layui-form-label">活动类型<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<select name="activityType" lay-verify="activityType" >
								<option value="">请选择类型</option>
								<c:forEach var="type" items="${types}">
									<option value="${type.value}" ${activity.activityType == type.value ? 'selected="selected"' : ""} >${type.label}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">人数上限<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" name="limitNum" lay-verify="limitNum" autocomplete="off" class="layui-input" value="${activity.limitNum}">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">报名费用</label>
					<div class="layui-input-block">
						<input type="radio" name="isFree" lay-filter="isFree" id="noFree" value="noFree" title="收费活动" ${activity == null || activity.price !=0.0 ? 'checked="checked"' : ''} >
						<input type="radio" name="isFree" lay-filter="isFree" id="free" value="free" title="免费活动"
						${activity != null && activity.price == 0.0 ? 'checked="checked"' : ''} >
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
				   		<button class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</button>
				  	</div>
				</div>
			</form>
		</div>
	</section>
</div>

<!--底部-->
<%@include file="../include/footer.jsp" %>
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
	
	var place = '${activity.place}';
	if(place != ''){
		$("#mapDiv").show();
	}

	var map = new BMap.Map("allmap",{enableMapClick:false});
	map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
	map.enableScrollWheelZoom(true);
	// 添加带有定位的导航控件
	  var navigationControl = new BMap.NavigationControl({
	    // 靠左上角位置
	    anchor: BMAP_ANCHOR_TOP_LEFT,
	    // LARGE类型
	    type: BMAP_NAVIGATION_CONTROL_LARGE,
	    // 启用显示定位
	    enableGeolocation: true
	  });
	  map.addControl(navigationControl);
		
	
	  var cityName = null;
	  
    layui.use(['form', 'laydate'], function () {
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
            title: function (value) {
            	if (value == "") {
            		return '请填写活动标题';
            	} else if (value.length < 5) {
                    return '标题至少得5个字啊';
                }
            },
            pic : function (value) {
            	if (value == ""){
            		return "请上传活动海报";
            	}
            },
            startDate : function (value) {
            	if (value == ""){
            		return "请设置活动开始时间";
            	}
				var nowDate = new Date();
				var stdt = new Date(value.replace("-","/"));
				if(stdt < nowDate){
					return "活动开始时间不得早于当前时间";
				}
            },
	        endDate : function (value) {
	        	if (value == ""){
	        		return "请设置报名截止时间";
	        	}
	        	var startDate = $("[name=startDate]").val();
				var endDate = $("[name=endDate]").val();
				
				var nowDate = new Date();
				var eddt = new Date(endDate.replace("-","/"));
				if(eddt < nowDate){
					return "报名截止时间不得早于当前时间";
				}
				if(endDate >= startDate){
					return "报名截止时间应该早于活动开始时间";
				}
	        },
	        place : function (value) {
	        	if(value == ""){
	        		return "请设置活动场所";
	        	}
	        },
	        area : function (value) {
	        	if(value == ""){
	        		return "请设置活动区域";
	        	}
	        },
	        city : function(value){
	        	if(value == ""){
	        		return "请选择活动举办的城市";
	        	}
	        },
// 	       	lat : function(value){
// 	       		if(value == ""){
// 	       			return "请选择一个活动场所"
// 	       		}
// 	       	},
	        activityType : function (value) {
	        	if (value == ""){
	        		return "请设置活动类型";
	        	}
	        },
	        limitNum : function (value) {
	        	var reg = /^[0-9]*[1-9][0-9]*$/;
	        	if (value == ""){
	        		return "请设置活动人数上限";
	        	}else if(!reg.test(value)){
            		return "只能是数字";
            	}else if(parseInt(value) == 0){
            		return "请输入大于0的数字";
            	}
	        },
            price : function (value) {
            	var reg = /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
            	if (radioValue == null) {
            		var type = $("[name=isFree]:checked").val();
            		if (type == "noFree") {
            			if(value == ""){
	            			return "请设置活动报名金额";
            			}else if(!reg.test(value)){
                    		return "只能是数字";
                    	}
            		}
            	} else {
            		if (radioValue == "noFree") {
            			if(value == ""){
	            			return "请设置活动报名金额";
            			}else if(!reg.test(value)){
                    		return "只能是数字";
                    	}
                	}            		
            	}
            },
            content : function (value) {
            	$("#contentView").html(ue.getContent());
            	var content = $("#contentView").html();
            	if (content == "") {
            		return "请填写活动详情";
            	}
            }
        });

        //监听提交
        form.on('submit', function (data) {
            $("#content").val(ue.getContent().replace(/&quot;/gi, ""));
			var action = $("#myForm").attr("action");
			$.post(action,$('#myForm').serialize(),function(res) {
				if (res.success) {
				    location.href = "${ctx}/activity/activity/publishList.do";
				} else {
					alert('异常');
				}
			});
            return false;
        });

        //日期
        var start = {
            min: '2015-01-01 00:00:00',
            max: '2099-06-16 23:59',
            istoday: false,
            choose: function (datas) {
                
            },
            istime: true, //是否开启时间选择
            format: 'YYYY-MM-DD hh:mm'
        };

        var end = {
            min: '2015-01-01 00:00:00',
            max: '2099-06-16 23:59',
            istoday: false,
            choose: function (datas) {
            	start.min = datas; //开始日选好后，重置结束日的最小日期
            },
            istime: true, //是否开启时间选择
            format: 'YYYY-MM-DD hh:mm' //日期格式
        };

        document.getElementById('startTime').onclick = function () {
        	start.min = laydate.now();
        	var endDate = new Date('${endTime}'.replace("-","/"));
        	if('${endTime}' != ""){
        		if(endDate < new Date()){
        			start.min = '${endTime}';
            	}
        	}
            start.elem = this;
            laydate(start);
        }
        document.getElementById('endTime').onclick = function () {
        	end.min = laydate.now();
        	
        	var endDate = new Date('${endTime}'.replace("-","/"));
        	if('${endTime}' != ""){
        		if(endDate < new Date()){
        			end.min = '${endTime}';
            	}
        	}
            end.elem = this
            laydate(end);
        }
        
		form.on('select(city)', function(data) {
			var $select =  $("[name=cityId]").siblings(".layui-form-select");
			cityName = $select.find(".layui-this").text();
			$("#mapDiv").show();
			map.centerAndZoom(cityName, 12);
		});
	});

	function clickFunction(obj) {
		if (obj != undefined) {
			var jsonStr = obj.attr("data-json");
			var jsonObject = JSON.parse(jsonStr);
			$("#lng").val(jsonObject.location.lng);
			$("#lat").val(jsonObject.location.lat);
			$("#place").val(jsonObject.name);
		}
	}
	
	function containerHidden(){
		$('.field_list_wrap').animate({'width':'0px'}, 0,function(){
			$('.field_list_wrap_big').css('left','872px');
			$('.field_list_wrap_big .close span').css('background','url(http://cdn.hudongba.com/static_v4/images/other/field_IconSprite4.png) no-repeat -20px 0px');
			$('.field_btn a').css('right','10px');
		})
	}
	
	function containerVisible(){
		$('.field_list_wrap').animate({'width':'240px'}, 0,function(){
			$('.field_list_wrap_big').css('left','630px');
			$('.field_list_wrap_big .close span').css('background','url(http://cdn.hudongba.com/static_v4/images/other/field_IconSprite4.png) no-repeat 0px 0px');
			$('.field_btn a').css('right','250px');
		})
	}
	
	containerHidden()
	
	function width_auto(that){
		if (that.children('span').css('background-position') == '0px 0px') {
			containerHidden();
		}else{
			containerVisible();
		}
	}
	
	function gotoPage(obj){
		$("field_fy_wrap a").removeClass("current");
		$(obj).addClass("current");
		var page = parseInt($(obj).text());
		
		appendData(page);	
	}
	
	// 拼接列表数据
	function appendData(page){
		var areaName = $("#area").val() + $("#place").val();
		$.post("${ctx}/map/map/search.do", {region : cityName,query : areaName,pageNum : page}, function(data) {
			containerVisible();
			var dataResults = data.data.results;
			var array = new Array();
			$(".field_ul").html("");
			if(dataResults.length > 0){
				for(var i = 0; i < dataResults.length; i++){
					var jsonStr = JSON.stringify(dataResults[i]);
					array.push("<li class='field_li' data-json='"+jsonStr+"'>");
					array.push("<a href='javascript:void(0);'>");
					array.push("<div class='box'><h2>"+dataResults[i].name+"</h2>");
					if(dataResults[i].address != "null"){
						array.push("<p class='address'>地址:"+dataResults[i].address+"</p>");
					}
					array.push("</div>");
					array.push("<div class='number'>"+(i+1)+"</div></a></li>");
				}
				
				$(".field_ul").append(array.join(""));
				
				$(".field_fy_wrap").html("");
				var pageArray = new Array();
				for(var i = 0; i < data.data.totalPage; i++){
					if(data.data.pageNum == i){
						pageArray.push("<a class='current' onclick='gotoPage($(this))'>"+(i+1)+"</a>");
					} else {
						pageArray.push("<a class='' onclick='gotoPage($(this))'>"+(i+1)+"</a>");
					}
				}
				$(".field_fy_wrap").append(pageArray.join(""));
			} else {
				$(".field_fy_wrap").html("没有找到符合的数据");
			}
		});
	}

	$(function() {
		
		$(".field_ul").delegate('.field_li','click',function(e){
			map.clearOverlays();//清空原来的标注
			var $target = e.target;
			var parent = $target.closest(".field_li");
			$(parent).siblings(".field_li").removeClass("current_hover");
			$(parent).addClass("current_hover");
			var jsonStr = $(parent).attr("data-json");
			var jsonObject = JSON.parse(jsonStr);
			selectAddress(jsonObject);
		});
		
		ue.addListener('ready', function() {
			this.setContent($("#contentView").html());
		});

		ue.addListener('blur', function() {
			$("#contentView").html(ue.getContent());
		});

		$('#upload_single_img').change(function() {
			if (util.isValid(this.value)) {
				uploadFile.uploadSinglePhoto(this.files[0], function(ret) {
					console.log('回调：' + ret);
					$('#cover-img').css('background-image','url(' + ret.data.download_url + ')');
					$('#pic').val(ret.data.download_url);
					$('#upload_single_img').val('');
				});
			}
		});

   		$("#area").blur(function(){
   			var value = $(this).val();
   			if(value != ""){
        		map.centerAndZoom(value, 16);
   			}
   		});

   		$("#place").blur(function(){
   			var value = $(this).val();
   			if(value != ""){
   				appendData(null);
   			}
   		});

   		var place = '${activity.place}';
   		if(place != ''){
   			var $select =  $("[name=cityId]").siblings(".layui-form-select");
			cityName = $select.find(".layui-select-title .layui-input").val();
   			map.centerAndZoom(cityName, 16);
//    			sechar('${activity.place}','${activity.lat}','${activity.lng}');
   		}

		function sechar(value, lat, lng) {
			$("#mapDiv").show();
			map.clearOverlays();//清空原来的标注

			if (lat != '' && lng != '') {
				map.centerAndZoom(new BMap.Point(lng, lat), 18);
				$.post("${ctx}/map/map/getAddress.do", {
					x : lng,
					y : lat
				}, function(data) {
					var dataResults = data.data.result;
					var resultObject = {
						location : dataResults.location,
						name : value,
						address : dataResults.formatted_address
								+ dataResults.sematic_description
					};
					selectAddress(eval(resultObject));
				})
			} else {
				map.centerAndZoom(value, 18);
				var localSearch = new BMap.LocalSearch(map);
				localSearch.setSearchCompleteCallback(function(searchResult) {
					var poi = searchResult.getPoi(0);
					var location = poi.point.lat + "," + poi.point.lng;
					$.post("${ctx}/map/map/nearByQuery.do", {
						query : value,
						location : location
					}, function(data) {
						var dataResults = data.data;
						for (var i = 0; i < dataResults.results.length; i++) {
							selectAddress(eval(dataResults.results[i]));
						}
					})
				});
				localSearch.search($("#area").val());
			}
		}

		function selectAddress(obj) {
			var lng = obj.location.lng;
			var lat = obj.location.lat;
			var new_point = new BMap.Point(lng, lat);
			var marker = new BMap.Marker(new_point); // 创建标注，为要查询的地方对应的经纬度

			var array = new Array();
			array.push("<div class='activity-location'><div class='address-name'>"+ obj.name + "</div>");
			array.push("<table cellspacing='0' ><tbody>");
			if (obj.detail == 1) {
				array.push("<tr><td>地址：  </td><td>" + obj.address+ "</td></tr>");
			}
			array.push("<tr><td></td><td><a class='set-address' data-json='"+ JSON.stringify(obj)+ "' onclick='clickFunction($(this))'>选为我的活动地点</a></td></tr>");
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
				var point = new BMap.Point(lng,lat);
				var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
				map.openInfoWindow(infoWindow, point); //开启信息窗口

			}
		}
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