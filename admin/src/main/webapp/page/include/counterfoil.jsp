<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<style type="text/css">
.error_bd_red{border-color: #e8473f!important}
.post_pay_item {background-color: #fcfcfc;position: relative;}
.post_pay_item .post_pay_item_top {height: 40px;background-color: #edeeef;border: 1px solid #d9d9d9;}
.post_pay_item .post_pay_item_top span {display: inline-block;line-height: 14px;font-size: 14px;color: #aaa;text-align: center;float: left;border-left: 1px solid #cecece;margin: 13px 0;}
.post_pay_item .post_pay_item_top span b {font-size: 12px;color: #3e80ad;}
.post_pay_item .post_pay_item_top span.tit01 {width: 75px;border: none;}
.post_pay_item .post_pay_item_top span.tit02 {width: 209px;}
.post_pay_item .post_pay_item_top span.tit03 {width: 170px;}
.post_pay_item .cost_content {min-height: 48px;padding: 10px 0 0;border: 1px solid #d9d9d9;border-top: none;position: relative;}
.post_pay_item .cost_content .form_input_number {height: 38px;width: 75px;text-align: center;float: left;line-height: 38px;font-size: 14px;color: #000;}
.post_pay_item .cost_content input:focus {border-color: #C9C9C9!important}
.post_pay_item .cost_content .form_input_cost_type {height: 38px;width: 209px;text-align: center;float: left;}
.post_pay_item .cost_content .form_input_cost_type input {height: 36px;width: 190px;border: 1px solid #d9d9d9;text-align: center;font-size: 14px;color: #000;background-color: #FFF;}
.post_pay_item .cost_content .form_input_cost_money {height: 30px;width: 171px;float: left;text-align: center;}
.post_pay_item .cost_content .form_input_cost_money input {height: 36px;width: 150px;border: 1px solid #d9d9d9;text-align: center;font-size: 14px;color: #000;background-color: #FFF;}
.post_pay_item .cost_content .form_input_cost_money input.border_n {border: none;background: none;}
.post_pay_item .cost_content a {display: block;width: 18px;height: 18px;background:url('${ctx}/image/activity/post_party_icon1.png') no-repeat -1px -87px;float: left;margin: 10px 0 10px 55px;cursor: pointer;}
.post_pay_item .cost_content a:hover, .post_pay_item .cost_content.thisOver a{background-position: -1px -106px;}
.post_pay_item .cost_content a.controlClear {background-position: -1px -125px;margin: 10px 0 10px 25px;}
.post_pay_item .cost_content a.controlClear:hover {background-position: -1px -144px;}
.post_pay_item .cost_add {overflow: hidden;}
.post_pay_item .cost_add a {display: none;width: 120px;height: 30px;line-height: 30px;text-align: center;margin: 0 auto;background-color: #0099e9;color: #FFF;}
.post_pay_item .cost_add a:hover {background-color: #0082c6;}
.restrictControl {display: none;clear: both;background: #f4f9fe;margin-top: 49px;text-align: left;padding: 20px 0 30px 40px;padding-bottom: 10px;}
.restrictControl h4 {height: 18px;font-size: 14px;color: #444;font-weight: normal;}
.restrictControl .txt {font-size: 14px;color: #999;line-height: 38px;float: left;margin-right: 10px;}
.restrictControl .txt select {width: 64px;padding-left: 15px;height: 36px;border: 1px solid #d9d9d9;margin: 0 8px;background: #fff url(http://cdn.hudongba.com/static_v4/images/manage/manage_jiao.png) no-repeat 45px center;}
.restrictControl .txt i {padding: 0 10px;}
</style>
<!-- 票据 -->
<div class="post_main_r">
	<div id="payitemDiv" class="onlyone">
		<div class="post_pay_item">
			<div class="post_pay_item_top">
				<span class="tit01">序号</span><span class="tit02">费用名称</span> <span class="tit03">金额</span>
				<span class="tit03">名额限制</span> <span class="tit03">操作</span>
			</div>
			<div class="cost_container">
				<c:if test="${fn:length(counterfoils) == 0}">
					<div class="cost_content cost_item">
						<div class="form_input_number">1</div>
						<div class="form_input_cost form_input_cost_type">
							<input type="text" placeholder="输入费用名称" name="counterfoils[0].name" class="input cost_name" value="免费票">
						</div>
						<div class="form_input_cost form_input_cost_money">
							<input type="text" placeholder="金额，免费请填0" name="counterfoils[0].payment" class="input cost_payment" value="0">
						</div>
						<div class="form_input_cost form_input_cost_money">
							<input type="text" placeholder="人数，无限制请填0" name="counterfoils[0].limitNum" class="input cost_limitNum" value="0">
						</div>
						<c:if test="${isGoods}">
							<a title="设置" class="control" href="javascript:void(0)"></a>
						</c:if>
						<a title="删除" class="controlClear" href="javascript:void(0)" style="display: none;"></a>
						<div class="restrictControl">
							<h4>限购设置</h4>
							<div>
								<p class="txt">最多购买</p>
								<div class="layui-inline l" style="margin-right: 5px !important;">
									<div class="layui-input-inline" style="width: 80px; margin-right: 0px !important">
										<select name="counterfoils[0].maxBuyNum">
											<c:forEach var="x" begin="1" end="20" step="1">
												<option value="${x}">${x}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<p class="txt">张</p>
								<div class="cl"></div>
							</div>
							<div></div>
						</div>
					</div>
				</c:if>
				<c:if test="${fn:length(counterfoils) > 0}">
					<c:forEach var="counterfoil" varStatus="status" items="${counterfoils}">
						<div class="cost_content cost_item">
							<div class="form_input_number">${status.count}</div>
							<input type="hidden" class="cost_id" name="counterfoils[${status.index}].id" value="${counterfoil.id}">
							<input type="hidden" class="cost_delFlag" name="counterfoils[${status.index}].delFlag" value="0">
							<div class="form_input_cost form_input_cost_type">
								<input type="text" placeholder="输入费用名称" name="counterfoils[${status.index}].name" class="input cost_name" value="${counterfoil.name}">
							</div>
							<div class="form_input_cost form_input_cost_money">
								<input type="text" placeholder="金额，免费请填0" name="counterfoils[${status.index}].payment" class="input cost_payment" value="${counterfoil.payment}" 
									${counterfoil.hasBuy == true ? 'readonly="readonly"' : ''} >
							</div>
							<div class="form_input_cost form_input_cost_money">
								<input type="text" placeholder="人数，无限制请填0" name="counterfoils[${status.index}].limitNum" class="input cost_limitNum" value="${counterfoil.limitNum}">
							</div>
							<c:if test="${isGoods}">
								<a title="设置" class="control" href="javascript:void(0)"></a>
							</c:if>
							<c:if test="${counterfoil.hasBuy == false}">
								<a title="删除" class="controlClear" href="javascript:void(0)"></a>
							</c:if>
							<div class="restrictControl">
								<h4>限购设置</h4>
								<div>
									<p class="txt">最多购买</p>
									<div class="layui-inline l" style="margin-right: 5px !important;">
										<div class="layui-input-inline" style="width: 80px; margin-right: 0px !important">
											<select name="counterfoils[${status.index}].maxBuyNum">
												<c:forEach var="x" begin="1" end="20" step="1">
													<option value="${x}">${x}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<p class="txt">张</p>
									<div class="cl"></div>
								</div>
								<div></div>
							</div>
						</div>
					</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
	<div class="post_main_fee2 mt20">
		<a href="javascript:addItem()" class="layui-btn layui-btn-danger"> <i class="iconfont icon-add btn-icon"></i> 添加费用项</a>
	</div>
</div>
<script type="text/javascript" charset="utf-8">
	var form = null;
	layui.use([ 'form', 'laydate' ], function() {
		form = layui.form(), laydate = layui.laydate;
	});

	function addItem(){
		var length = $(".cost_container").find(".cost_content").length;
		var array = new Array();
		array.push('<div class="cost_content cost_item">');
		array.push('<div class="form_input_number">'+(length + 1)+'</div>');
		array.push('<div class="form_input_cost form_input_cost_type"><input type="text" name="counterfoils['+(length)+'].name" placeholder="输入费用名称" class="input cost_name" value=""></div>');
		array.push('<div class="form_input_cost form_input_cost_money"><input type="text" name="counterfoils['+(length)+'].payment" placeholder="金额，免费请填0" class="input cost_payment" value=""></div>');
		array.push('<div class="form_input_cost form_input_cost_money"><input type="text" name="counterfoils['+(length)+'].limitNum" placeholder="人数，无限制请填0" class="input cost_limitNum" value=""></div>');
		var isGoods = ${isGoods};
		if(isGoods){
			array.push('<a title="设置" class="control"></a>');
		}
		array.push('<a title="删除" class="controlClear"></a>');
		array.push('<div class="restrictControl">');
		array.push('<h4>限购设置</h4>');
		array.push('<div><p class="txt">最多购买</p>');
		array.push('<div class="layui-inline l" style="margin-right: 5px !important;">');
		array.push('<div class="layui-input-inline" style="width: 80px; margin-right: 0px !important">');
		array.push('<select name="counterfoils['+(length)+'].maxBuyNum" >');
		for(var i = 1; i < 21 ; i++){
			array.push('<option value='+i+'>' + i + '</option>');
		}
		array.push('</select></div></div>');
		array.push('<p class="txt">张</p><div class="cl"></div></div>');
		array.push('<div></div></div></div>');
		
		$(".cost_container").append(array.join(""));
		
		$(".cost_container").find(".cost_content").find(".controlClear").show();
		
		form.render('select');
		
		var display = $("#areaInput").css("display");
		if (util.isValid(display)){
			if (display == "block") {
				$("#areaInput").siblings(".layui-form-select").hide();
			}
		}
	}
	
	// 1.名称不能重名
	// 2.金额只能为数字且小于等于0
	// 3.名额限制只能为数字且大于等于0
	function checkCost(){
		var _payNameArr = new Array();
		var _flagArr = new Array();
		var flag = true;		
		$(".cost_container").find(".cost_item").each(function(index, element) {
			var $cost = $(element);
			var cost_name = $cost.find(".cost_name").val();
			cost_name = cost_name.replace(/\'/g, '');
			cost_name = cost_name.replace(/\"/g, '');
			var cost_payment = $cost.find(".cost_payment").val();
			var cost_limitNum = $cost.find(".cost_limitNum").val();
			
			// 检查空
			if(!util.isValid(cost_name)){
				$cost.find(".cost_name").addClass("error_bd_red");
				top.layer.msg('请填写费用名称', {
					icon : 5,
					time : 2000
				});
				flag = false;
				return flag;
			}
			
			if(!util.isValid(cost_payment)){
				$cost.find(".cost_payment").addClass("error_bd_red");
				top.layer.msg('请填写金额', {
					icon : 5,
					time : 2000
				});
				flag = false;
				return flag;
			}
			
			if(!util.isValid(cost_limitNum)){
				$cost.find(".cost_limitNum").addClass("error_bd_red");
				top.layer.msg('请填写人数上限', {
					icon : 5,
					time : 2000
				});
				flag = false;
				return flag;
			}
			
			// cost_name
			for (var m = 0; m < _payNameArr.length; m++) {
				if (_payNameArr[m] == cost_name) {
					$cost.find(".cost_name").addClass("error_bd_red");
					top.layer.msg('费用名称不能重复', {
						icon : 5,
						time : 2000
					});
					flag = false;
					return flag;
				}
			}
			_payNameArr.push(cost_name);
			
			// cost_payment
			var isError = false;
			var desc = "";
			var reg = /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
			if (isNaN(cost_payment) || !reg.test(cost_payment)){
				desc = "费用格式不正确";
				isError = true;
			} else if (parseFloat(cost_payment) < 0) {
				desc = "费用必须大于等于0";
				isError = true;
			}
			if (isError) {
				$cost.find(".cost_payment").addClass("error_bd_red");
				top.layer.msg(desc, {
					icon : 5,
					time : 2000
				});
				flag = false;
				return flag;
			}
			
			// cost_limitNum
			isError = false;
			desc = "";
			if (!util.checkNumber(cost_limitNum)) {
				desc = "人数上限必须是数字";
				isError = true;
			} else if (parseInt(cost_limitNum) < 0) {
				desc = "人数上限必须大于等于0";
				isError = true;
			}
			
			if (isError) {
				$cost.find(".cost_limitNum").addClass("error_bd_red");
				top.layer.msg(desc, {
					icon : 5,
					time : 2000
				});
				flag = false;
				return flag;
			}
			flag = true;
			$cost.find("input").removeClass("error_bd_red");
		});
		return flag;
	}

	$(function() {
		function hideDel() {
			var length = $(".cost_container").find(".cost_content").length;
			if (length == 1) {
				$(".cost_container").find(".cost_content").find(".controlClear").hide();
			}
		}

		hideDel();

		function updateName(ele, index) {
			var name = $(ele).attr("name");
			var start = name.substring(0, name.indexOf("[") + 1);
			var end = name.substring(name.indexOf("]"), name.length);
			var newName = start + index + end;
			$(ele).attr("name", newName);
		}
		
		$('#payitemDiv').delegate('input', 'blur', function(e){
			checkCost();
		});

		$('#payitemDiv').delegate('a,input', 'click', function(e) {
			var $target = $(e.target);
			if ($target.hasClass("control")) {
				var restrictControl = $target.closest(".cost_content").find(".restrictControl");
				var display = $(restrictControl).css("display");
				if (display == "none") {
					$(restrictControl).show();
				} else {
					$(restrictControl).hide();
				}
				e.stopPropagation();
			} else if ($target.hasClass("controlClear")) {
				// length = 1表示更新；length=表示新增
				var length = $target.closest(".cost_content").find(".cost_id").length;
				if (length == 0) {
					$target.closest(".cost_content").remove();
				} else {
					$target.closest(".cost_content").hide();
					$target.closest(".cost_content").find(".cost_delFlag").val("1");
					$target.closest(".cost_content").removeClass("cost_content");
				}
				// 重新编号 
				$(".cost_container").find(".cost_content").each(function(index, element) {
					var $cost = $(element);
					$cost.find(".form_input_number").text(index + 1);
				});
				// 重新标索引
				$(".cost_container").find(".cost_item").each(function(index, element) {
					var $cost = $(element);

					var $costId = $cost.find(".cost_id");
					if ($costId.length == 1) {
						updateName($costId, index);
					}
					var $costDelFlag = $cost.find(".cost_delFlag");
					if ($costDelFlag.length == 1) {
						updateName($costDelFlag, index);
					}

					$cost.find(".form_input_cost").each(function(i, ele) {
						var $input = $(ele).find("input");
						updateName($input, index);
					});
					var $select = $cost.find(".restrictControl").find("select");
					updateName($select, index);
				});
				// 如果只有一种票了，则不能删除
				length = $(".cost_container").find(".cost_content").length;
				if (length == 1) {
					$(".cost_container").find(".cost_content").find(".controlClear").hide();
				}
				e.stopPropagation();
			} else if ($target.hasClass("cost_payment")) {
				var readonly = $target.attr("readonly");
				if (util.isValid(readonly)) {
					top.layer.msg('已经有报名的收费项不能修改金额', {
						icon : 5,
						time : 2000
					});
				}
				e.stopPropagation();
			}
		});
	})
</script>