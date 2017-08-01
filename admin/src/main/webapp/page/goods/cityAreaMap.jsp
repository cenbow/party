<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<style type="text/css">
	/**地图列表**/
	.field_list_wrap_big {
		width: 260px;
		position: absolute;
		top: 0;
		bottom: 0;
		right: -242px;
		text-align: left;
		-webkit-transition: right 0.3s;
		-moz-transition: right 0.3s;
		transition: right 0.3s;
	}

	.field_list_wrap_big.open {
		right: 0;
	}

	.field_list_wrap {
		width: 240px;
		position: absolute;
		right: 0;
		top: 0;
		bottom: 0;
		border: 1px solid #cacaca;
		background: rgba(255, 255, 255, 0.8);
	}

	.field_list_wrap .top {
		height: 35px;
		overflow: hidden;
		line-height: 40px;
		padding-left: 10px;
		font-size: 14px;
		color: #444;
	}

	.field_list_wrap .field_box {
		height: 467px;
		overflow-y: auto;
	}

	.field_list_wrap .field_li a {
		display: block;
		position: relative;
		padding-top: 10px;
	}

	.field_list_wrap .field_li a.current, .field_list_wrap .field_li a:hover, .current_hover {
		background: #f2fbff;
	}

	.field_list_wrap .field_li .box {
		border-bottom: 1px solid #ddd;
		padding: 0 5px 10px 25px;
		overflow: hidden;
		margin-left: 10px;
	}

	.field_list_wrap .field_li .border_n {
		border: none;
	}

	.field_list_wrap .field_li .number {
		position: absolute;
		top: 8px;
		left: 10px;
		width: 17px;
		height: 24px;
		line-height: 20px;
		text-align: center;
		font-size: 12px;
		color: #fff;
		background: url('${ctx}/image/activity/address_bg.png') no-repeat;
	}

	.field_list_wrap .field_li h2 {
		line-height: 20px;
		font-size: 14px;
		color: #0099e9;
		margin-bottom: 5px;
	}

	.field_list_wrap .field_li .tel {
		font-size: 12px;
		color: #999;
		line-height: 20px;
	}

	.field_list_wrap .field_li .address {
		font-size: 12px;
		color: #999;
		line-height: 16px;
	}

	.field_list_wrap .field_fy {
		text-align: center;
	}

	.field_list_wrap .field_fy_wrap {
		overflow: hidden;
		margin: 10px auto;
		display: inline-block;
	}

	.field_list_wrap .field_fy a {
		height: 18px;
		line-height: 18px;
		padding: 0 5px;
		font-size: 12px;
		color: #0099e9;
		float: left;
		margin: 0 2px;
		border: 1px solid #ebebeb;
	}

	.field_list_wrap .field_fy a:hover {
		border-color: #0099e9;
		cursor: pointer;
	}

	.field_list_wrap .field_fy .dian {
		padding: 0 2px;
		text-align: center;
		color: #999;
		border: none;
	}

	.field_list_wrap .field_fy .current {
		background: #0099e9;
		border: 1px solid #0099e9;
		color: #fff;
	}

	.field_list_wrap_big .close {
		position: absolute;
		top: 50%;
		-webkit-transform: translate(0, -50%);
		-ms-transform: translate(0, -50%);
		-moz-transform: translate(0, -50%);
		transform: translate(0, -50%);
		width: 17px;
		height: 62px;
		border: 1px solid #cacaca;
		border-right: none;
		/* margin-top: 210px; */
		background: url('${ctx}/image/activity/field_IconSprite5.png');
		border-radius: 2px 0 0 2px;
		cursor: pointer;
	}

	.field_list_wrap_big .close span {
		width: 7px;
		height: 11px;
		background: url('${ctx}/image/activity/field_IconSprite4.png') no-repeat 0px 0;
		margin: 25px 5px;
		display: inline-block;
	}

	.post_map {
		width: 100%;
		height: 512px; /*float:left;*/
	}

	.activity-location .address-name {
		font-weight: normal;
		font-size: 16px;
		color: #000;
	}

	.activity-location .set-address {
		color: red
	}

	.activity-location a:hover {
		color: red
	}

	.activity-location table td {
		padding-top: 10px;
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
		background: url(http://cdn.hudongba.com/static_v4/images/detail/address_icon3.png) no-repeat 10px 1px;
	}
</style>
<!-- 城市_区域_地图 -->
<div class="layui-form-item">
	<div class="layui-inline">
		<label class="layui-form-label">地点<span class="f-verify-red">*</span></label>
		<div class="layui-input-inline">
			<select name="cityId" lay-verify="city" lay-filter="city">
				<option value="">选择城市</option>
				<c:forEach var="city" items="${citys}">
					<option value="${city.id}" ${goods.cityId == city.id ? 'selected="selected"' : ""}>${city.name}</option>
				</c:forEach>
			</select>
		</div>
		<div class="layui-input-inline">
			<select name="areaSelect" lay-verify="area_select" id="areaSelect" lay-filter="area">
				<option value="">选择区域</option>
			</select>
			<input name="areaInput" type="text" id="areaInput" lay-verify="area_input" placeholder="填写区域" class="layui-input"
				value="${goods.area}" style="display: none;" />
		</div>
		<div class="post_site_K">
			<input type="text" placeholder="集合地点" id="place" name="place" value="${goods.place}" readonly="readonly">
			<input type="hidden" name="lat" id="lat" value="${goods.lat}" />
			<input type="hidden" name="lng" id="lng" value="${goods.lng}" />
			<span id="fadeIn" onclick="" style="display: none;cursor: pointer;">已标记</span>
		</div>
	</div>
</div>
<div id="mapDiv" class="layui-form-item ovh" style="display: none;">
	<label class="layui-form-label"></label>
	<div class="layui-input-block">
		<div id="allmap" class="post_map"></div>
		<div class="field_list_wrap_big">
			<div class="close" onclick="width_auto($(this));">
				<span style="background: url('${ctx}/image/activity/field_IconSprite4.png') 0px 0px no-repeat scroll;"></span>
			</div>
			<div class="field_list_wrap">
				<div class="top">场地列表</div>
				<div class="field_box" id="panel">
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
<script type="text/javascript" src="http://api.map.baidu.com/getscript?v=2.0&ak=rov0CrEf1g7FMNIsZKtwZD9jAs31BqGz&services=&t=${empty currentDate ? '20170517145936' : currentDate}"></script>
<script type="text/javascript">
    var map;
    function initMap() {
        map = new BMap.Map("allmap", {
            enableMapClick: false
        });
        map.enableScrollWheelZoom(); //启用滚轮放大缩小，默认禁用
        map.enableContinuousZoom(); //启用地图惯性拖拽，默认禁用
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

        $("#fadeIn").click(function () {
            $("#mapDiv").toggle(1000);
        });

        $(".field_ul").delegate('.field_li', 'click', function (e) {
            map.clearOverlays();//清空原来的标注
            var $target = e.target;
            var parent = $target.closest(".field_li");
            $(parent).siblings(".field_li").removeClass("current_hover");
            $(parent).addClass("current_hover");
            var jsonStr = $(parent).attr("data-json");
            var jsonObject = JSON.parse(jsonStr);
            selectAddress(jsonObject, 'add');

        });
    }

    function initMapForm(formMap) {
        // 城市
        formMap.on('select(city)', function (data) {
            var $select = $("[name=cityId]").siblings(".layui-form-select");
            cityName = $select.find(".layui-this").text();
            loadCityData(cityName);
            map.centerAndZoom(cityName, 12);

            clearPlace();
        });

        // 区域
        formMap.on('select(area)', function (data) {
            var value = data.value;
            if (value != "") {
                map.centerAndZoom(value, 16);
            }
            clearPlace();
        });

        $("#areaInput").blur(function () {
            var value = $(this).val();
            if (value != "") {
                $("#mapDiv").show();
                map.centerAndZoom(value, 15);
                $("#place").removeAttr("readonly");
            } else {
                clearPlace();
            }
        });

        $("#place").blur(function () {
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

        if ('${goods.area}' != "") {
            $("#mapDiv").show();
            map.centerAndZoom('${goods.area}', 16);
            $("#place").removeAttr("readonly");
            appendListData(null);
        }

        if ('${goods.place}' != '') {
            var $select = $("[name=cityId]").siblings(".layui-form-select");
            var cityName = $select.find(".layui-select-title .layui-input").val();
            $("#mapDiv").show();
            map.centerAndZoom(cityName, 16);
            appendListData(null);
            $('.field_list_wrap_big').removeClass('open');
        }

        // 加载Select数据
        function loadCityData(cityName) {
            $("#areaSelect").html("");
            $.post("${ctx}/activity/activity/getAreaByCityName.do", {
                "cityName": cityName
            }, function (data) {
                var array = new Array();
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        if ('${goods.area}' == data[i].name) {
                            array.push("<option value = '" + data[i].name + "' selected='selected'>" + data[i].name + "</option>");
                        } else {
                            array.push("<option value = '" + data[i].name + "'>" + data[i].name + "</option>");
                        }
                    }
                    $("#areaSelect").append(array.join(""));
                    formMap.render('select');
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
                    formMap.render('select');
                    $("#areaInput").siblings(".layui-form-select").hide();
                }
            });
            initMap();
        }
    }

    function width_auto(that) {
        var listWrap = $(that).closest('.field_list_wrap_big');
        listWrap.toggleClass('open');
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
            url: '${ctx}/map/map/search.do',
            type: 'POST',
            async: false, // 使用同步的方法
            data: {
                region: cityName,
                query: areaName,
                pageNum: page
            },
            dataType: 'json',
            success: function (data) {
                var dataResults = data.data.results;
                var array = new Array();
                $(".field_ul").html("");
                if (dataResults.length > 0) {
                    for (var i = 0; i < dataResults.length; i++) {
                        var jsonStr = JSON.stringify(dataResults[i]);
                        jsonStr = jsonStr.replace(new RegExp("\"", "gm"), "&quot;");
                        jsonStr = jsonStr.replace(new RegExp("'", "gm"), "&#39;");
                        array.push("<li class='field_li' data-json='" + jsonStr + "'>");
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

                    $('.field_list_wrap_big').addClass('open');

                    var location = dataResults[0].location;
                    if (location != undefined) {
                        $("#lat").val(location.lat);
                        $("#lng").val(location.lng);
                        map.centerAndZoom(new BMap.Point(location.lng, location.lat), 18);
                        selectAddress(dataResults[0], "edit");
                    }
                } else {
                    $(".field_fy_wrap").html("没有找到符合的数据");

                    var lng = $("#lng").val();
                    var lat = $("#lat").val();
                    if (lng != '' && lat != '' && '${goods.id}' != "") {
                        map.centerAndZoom(new BMap.Point(lng, lat), 18);
                        $.ajax({
                            url: '${ctx}/map/map/getAddress.do',
                            type: 'POST',
                            async: false, // 使用同步的方法
                            data: {
                                x: lng,
                                y: lat
                            },
                            dataType: 'json',
                            success: function (data) {
                                var dataResults = data.data.result;
                                var resultObject = {
                                    location: dataResults.location,
                                    name: $("#place").val(),
                                    address: dataResults.formatted_address + dataResults.sematic_description
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

    function toggleFadeIn() {
        if ($("#lat").val() != "") {
            $("#fadeIn").css("display", "block");
        } else {
            $("#fadeIn").css("display", "none");
        }
    }

    function clearPlace() {
        $("#place").val("");
        $("#lat").val("");
        $("#lng").val("");
        $(".field_ul").html("");
        $(".field_fy_wrap").html("");
        $('.field_list_wrap_big').removeClass('open');
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
            marker.addEventListener("click", function (e) {
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
                icon: 6
            });

            toggleFadeIn();
        }
    }

</script>