<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>订单管理</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<%@include file="../include/commonFile.jsp"%>
<style type="text/css">
	.layui-table td, .layui-table th{
		padding-left: 10px!important;
		padding-right: 10px!important;
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
				<div class="ovh">
					<span class="title f20">订单管理</span> <span class="f12">共<b id="totalCount">${page.totalCount}</b>条记录
					</span>
				</div>
			</div>
			<form class="layui-form" action="${ctx}/order/order/orderList.do" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" value="1" />
				<input type="hidden" name="flag" id="flag" value="false" />
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">订单编号</label>
								<div class="layui-input-inline">
									<input type="text" name="id" class="layui-input" value="${orderForm.id}" placeholder="请输入查询订单编号" style="width: 212px">
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable" style="margin-left: -14px;">交易单号</label>
								<div class="layui-input-inline">
									<input type="text" name="transactionId" class="layui-input" value="${orderForm.transactionId}" placeholder="请输入查询交易单号"  style="width: 212px">
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">商户号</label>
								<div class="layui-input-inline">
									<input type="text" name="merchantId" class="layui-input" value="${orderForm.merchantId}" placeholder="请输入查询商户" style="width: 212px">
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">订单名称</label>
								<div class="layui-input-inline">
									<input type="text" name="title" class="layui-input" value="${orderForm.title}" placeholder="请输入查询订单名称" style="width: 212px">
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable" style="margin-left: -14px;">下单者</label>
								<div class="layui-input-inline">
									<input type="text" name="memberName" class="layui-input" value="${input.memberName}" placeholder="请输入查询订单下单者"  style="width: 212px">
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">金额</label>
								<div class="layui-input-inline">
									<input type="text" name="payment" class="layui-input" value="${orderForm.payment}" placeholder="请输入查询订单金额" style="width: 212px">
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item-inline">
								<label class="search-form-lable">支付类型</label>
								<div class="layui-input-inline">
									<select name="paymentWay">
										<option value="">全部</option>
										<option value="1" ${orderForm.paymentWay == '1' ? 'selected="selected"' : ''}>微信</option>
										<option value="0" ${orderForm.paymentWay == '0' ? 'selected="selected"' : ''}>支付宝</option>
									</select>
								</div>
							</li>
							<li class="form-item-inline">
								<label class="search-form-lable">交易状态</label>
								<div class="layui-input-inline">
									<select name="tradeStatus">
										<option value="">全部</option>
										<option value="1" ${tradeStatus == '1' ? 'selected="selected"' : ''}>交易成功</option>
										<option value="2" ${tradeStatus == '2' ? 'selected="selected"' : ''}>未支付</option>
										<option value="3" ${tradeStatus == '3' ? 'selected="selected"' : ''}>已退款</option>
										<option value="4" ${tradeStatus == '4' ? 'selected="selected"' : ''}>交易不存在</option>
									</select>
								</div>
							</li>
							<li class="form-item-inline">
								<label class="search-form-lable">订单状态</label>
								<div class="layui-input-inline">
									<select name="status">
										<option value="">全部</option>
										<c:forEach var="map" items="${orderStatus}">
											<c:if test="${orderForm.status == map.key}">
												<option value="${map.key}" selected="selected">${map.value}</option>
											</c:if>
											<c:if test="${orderForm.status != map.key}">
												<option value="${map.key}">${map.value}</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">下单时间</label>
								<div class="check-btn-inner" id="timeType">
									<a id="all" href="javascript:void(0);" onclick="setTimeType($(this),0,'#myForm')" ${empty input.timeType || input.timeType == 0 ? 'class="active"' : ''}>全部</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),4,'#myForm')" ${input.timeType == 4 ? 'class="active"' : ''}>昨天</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),1,'#myForm')" ${input.timeType == 1 ? 'class="active"' : ''}>今天</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),2,'#myForm')" ${input.timeType == 2 ? 'class="active"' : ''}>本周内</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),3,'#myForm')" ${input.timeType == 3 ? 'class="active"' : ''}>本月内</a>
									<input type="hidden" name="timeType" value="${input.timeType}" />
								</div>
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="createStart" value="${input.createStart}" placeholder="开始日" onclick="layui.laydate({elem: this})">
									</div>
									-
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="createEnd" value="${input.createEnd}" placeholder="截止日" onclick="layui.laydate({elem: this})">
									</div>
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">订单类型</label>
								<div class="check-btn-inner">
									<a id="all" href="javascript:void(0);" onclick="setTimeType($(this),'','#myForm')" ${empty orderForm.type ? 'class="active"' : ''}>全部</a>
									<c:forEach var="map" items="${orderTypes}">
										<a href="javascript:void(0);" onclick="setTimeType($(this),'${map.key}','#myForm')" ${orderForm.type == map.key ? 'class="active"' : ''}>${map.value}</a>
									</c:forEach>
									<input type="hidden" name="type" value="${orderForm.type}" />
								</div>
							</li>
							<li class="form-item-inline">
								<div class="sub-btns">
									<a class="layui-btn layui-btn-danger" href="javascript:submitClk()">确定</a>
									<a class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</form>
			<div class="my-act-list-content">
				<ul class="num">
					<div class="l">
						<li class="f16">订单总金额
							<span class="red">
								<c:if test="${orderTotal > 0}">
									<fmt:formatNumber pattern="#,###.##" value="${orderTotal}"/>
								</c:if>
								<c:if test="${orderTotal == 0.0}">
									${orderTotal}
								</c:if>
							</span> 元
						</li>
					</div>
					<div class="l">
						<li style="cursor: pointer;" class="r"><a class="layui-btn layui-btn-danger layui-btn-small" id="btnExport">导出EXCEL</a></li>
					</div>
					<p class="cl"></p>
				</ul>
				<div class="cl">
					<table class="layui-table">
						<colgroup>
							<col>
							<col>
							<col>
							<col>
							<col>
							<col>
							<col>
							<col width="150px">
							<col width="100px">
						</colgroup>
						<thead>
							<tr>
								<th>订单名称</th>
								<th>下单者</th>
								<th>商户</th>
								<th>类型</th>
								<th>金额</th>
								<th>支付方式</th>
								<th>状态</th>
								<th>下单时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="orderForm" items="${orderForms}">
							<tr>
								<td class="ellipsis-1 " title="${orderForm.title}" onclick="openDialogNoButton('订单详情','${ctx}/order/order/orderInfo.do?id=${orderForm.id}','450px','570px')">
									<a style="width: 250px;cursor: pointer;" class="blue">${orderForm.title}</a>
								</td>
								<td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${orderForm.member.id}','400px','470px')">
									<div class="member-cell">
									<div class="member-logo" style="background-image: url('${orderForm.member.logo}'),url(${ctx}/image/def_user_logo.png)"></div>
									<div class="member-name ellipsis-1"><a class="blue" title="${orderForm.member.realname}" style="cursor: pointer;">${orderForm.member.realname}</a></div>
									</div>
								</td>
								<td><div>${orderForm.merchantName}</div></td>
								<td width="12%">
									<c:choose>
										<c:when test="${orderForm.type == 0}"><span>标准商品订单</span></c:when>
										<c:when test="${orderForm.type == 1}"><span>定制商品订单</span></c:when>
										<c:when test="${orderForm.type == 2}"><span>活动订单</span></c:when>
										<c:when test="${orderForm.type == 3}"><span>众筹订单</span></c:when>
										<c:when test="${orderForm.type == 4}"><span>等级套餐订单</span></c:when>
									</c:choose>
								</td>
								<td>￥${orderForm.payment}</td>
								<td width="7%">
									<div>
										<c:choose>
											<c:when test="${orderForm.paymentWay == 1}">微信</c:when>
											<c:when test="${orderForm.paymentWay == 0}">支付宝</c:when>
										</c:choose>
									</div>
								</td>
								<td width="7%">
									<c:choose>
										<c:when test="${orderForm.status == 0}">
											<span>审核中</span>
										</c:when>
										<c:when test="${orderForm.status == 1}">
											<span>待支付</span>
										</c:when>
										<c:when test="${orderForm.status == 2}">
											<span style="color: red;">已支付</span>
										</c:when>
										<c:when test="${orderForm.status == 3}">
											<span>其它</span>
										</c:when>
										<c:when test="${orderForm.status == 4}">
											<span>已退款</span>
										</c:when>
									</c:choose>
								</td>
								<td width="15%"><fmt:formatDate value="${orderForm.updateDate}" pattern="yyyy-MM-dd HH:mm" /></td>
								<td class=" tb-opts">
									<div class="comm-opts">
 										<%--<c:if test="${orderForm.status == 2 && orderForm.payment != 0.0}">--%>
 											<%--<a class="red" href="javascript:refund('${orderForm.id}')" target="_self">退款</a>--%>
 										<%--</c:if>--%>
										<a class="red" href="javascript:check('${orderForm.id}')" target="_self">对账</a>
									</div>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div id="page_content" class="page-container"></div>
			</div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">

    showActive('${input.createStart}', '${input.createEnd}', '#timeType');


	
	//退款
	function refund(orderId) {
		layer.confirm('确认要退款吗?', {
			icon : 3,
			title : '系统提示'
		}, function(index) {
			$.post("${ctx}/refund/refund.do", {
				"orderId" : orderId
			}, function(data) {
				if (data.success == true) {
					top.layer.msg("退款成功！", {
						icon : 1
					}, function(index){
						window.location.reload();
					});
				} else {
					top.layer.msg(data.description, {
						icon : 2
					});
				}
				top.layer.close(index);
			});
		});
	}
	
	function check(orderId) {
        //loading层
        var loadIndex = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
		$.post("${ctx}/payquery/orderQuery.do", {
			"orderId" : orderId
		}, function(data) {
            top.layer.close(loadIndex);
            setTimeout(function() {
                if (data.success == true) {
                    top.layer.msg("对账成功！", {
                        icon: 1
                    }, function (index) {
                        window.location.reload();
                    });
                } else {
                    top.layer.msg(data.description, {
                        icon: 2
                    });
                }
            }, 500);
		});
	}
	
	function submitClk(){
		var paramsArray = $("#myForm").serializeArray();
        var params = new Array();
        $.each(paramsArray, function (i, param) {
        	if(param.value != ""){
            	params.push(param.value);
        	}
        });
        
        if(params.length > 2) {
        	$("#flag").val(true);
        } else if(params.length == 2) {
        	$("#flag").val(false);
        }
        $("#myForm").submit();
	}
	
	$(function(){
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm', submitClk);
		$("#btnExport").click(function() {
			layer.confirm('确认要导出Excel吗?', {
				icon : 3,
				title : '系统提示'
			}, function(index) {
				var url = "${ctx}/order/order/exportOrder.do";
				var action = $("#myForm").attr("action");
				$("#myForm").attr("action", url);
				$("#myForm").submit();
				$("#myForm").attr("action", action);
				top.layer.close(index);
			});
		});
	})
</script>
</body>
</html>