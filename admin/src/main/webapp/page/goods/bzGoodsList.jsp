<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>标准玩法管理</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<%@include file="../include/commonFile.jsp"%>
	<style type="text/css">
		.f-def-dialog .f-dialog-content {
			width: 420px !important;
			height: 300px !important;
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
					<a href="${ctx}/goods/goods/bzGoodsForm.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-add btn-icon"></i>
						发布标准玩法
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">标准玩法管理</span> <span class="f12">共<b id="totalCount">${page.totalCount}</b>条记录
					</span>
				</div>
			</div>
			<form class="layui-form" action="${ctx}/goods/goods/bzGoodsList.do" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" />
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">玩法标题</label>
								<div class="layui-input-inline">
									<input type="text" name="title" autocomplete="off" class="layui-input" value="${goods.title}" placeholder="请输入查询玩法标题">
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">发布者</label>
								<div class="layui-input-inline">
									<input type="text" name="memberName" autocomplete="off" class="layui-input" value="${input.memberName}" placeholder="请输入查询玩法发布者">
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">玩法类型</label>
								<div class="layui-input-inline">
									<select name="categoryId">
										<option value="">全部</option>
										<c:forEach var="category" items="${categories}">
											<option value="${category.id}" ${goods.categoryId == category.id ? 'selected="selected"' : ''}>${category.name}</option>
										</c:forEach>
									</select>
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">开始时间</label>
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="startDate" value="${startDate}" placeholder="开始时间" onclick="layui.laydate({elem: this})">
									</div>
									-
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="endDate" value="${endDate}" placeholder="结束时间" onclick="layui.laydate({elem: this})">
									</div>
								</div>
								<div class="cl"></div>
							</li>
							<li class="form-item-inline">
								<div class="sub-btns">
									<a class="layui-btn layui-btn-danger" href="javascript:submitFunction('#myForm')">确定</a>
									<a class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">评论时间</label>
								<div class="check-btn-inner" id="timeType">
									<a id="all" href="javascript:void(0);" onclick="setTimeType($(this),0,'#myForm')" ${empty input.timeType || input.timeType == 0 ? 'class="active"' : ''}>全部</a>
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
					</div>
				</div>
			</form>
			<div class="c-time-list-content">
				<ul id="view">
					<c:if test="${page.totalCount == 0}">
						<div class="f16 tc mt15">还没有数据，请点击右上角的“发布标准玩法”</div>
					</c:if>
					<c:forEach var="goods" items="${goodsOutputs}">
						<li>
							<div class="detail-content">
								<a href="${ctx}/goods/goods/goodsDetail.do?id=${goods.id}&type=0" class="act-logo"
									style="background-image: url('${goods.picsURL}'),url(${ctx}/image/img_bg.png)"
								></a>
								<div class="detail">
									<div class="act-title">
										<c:choose>
											<c:when test="${goods.checkStatus == 0}">
												<span class="status f18 dark">未审核</span>
											</c:when>
											<c:when test="${goods.checkStatus == 1}">
												<span class="status f18 red">审核通过</span>
											</c:when>
											<c:when test="${goods.checkStatus == 2}">
												<span class="status f18 dark">审核未通过</span>
											</c:when>
										</c:choose>
										<a title="${goods.title}" href="${ctx}/goods/goods/goodsDetail.do?id=${goods.id}&type=0" class="title f18 ell db">${goods.title}</a>
									</div>
									<div>
										<p class="act-price">
											<span style="margin-right: 10px;"> 开始时间：<fmt:formatDate value="${goods.startTime}" pattern="yyyy-MM-dd" />
											</span> <span> 结束时间：<fmt:formatDate value="${goods.endTime}" pattern="yyyy-MM-dd" /> <span>
										</p>
										<p class="act-price">
											<span style="margin-right: 10px;">价格：<b class="active-red">${goods.price}</b>元/位
											</span> <span style="margin-right: 10px;">预定份数：<b class="active-red">${goods.joinNum}</b>份
											</span> <span>发布者：<b class="active-red">${goods.memberName}</b></span>
										</p>
										<p class="start-time">集合地点：${goods.area} ${goods.place}</p>
										<p class="act-price">
											发布时间：
											<fmt:formatDate value="${goods.updateDate}" pattern="yyyy-MM-dd HH:mm" />
										</p>
									</div>
									<div class="opts-btns tb-opts"  style="width:475px">
										<div class="comm-opts">
											<c:if test="${goods.checkStatus == 1}">
												<a class="qr-btn" href="javascript:void(0);"><i class="iconfont icon-qrcode btn-icon"></i> 二维码</a>
											</c:if>
											<a href="javascript:void(0)" class="z-copy-btn" data-id="${goods.id}" data-msg="ID已复制到剪切板"> <i class="iconfont icon-copy btn-icon"></i> 获取ID</a>
											<a href="${ctx}/goods/goods/bzGoodsForm.do?id=${goods.id}"> <i class="iconfont icon-edit btn-icon"></i> 编辑</a>
											<a href="javascript:deleteObj('确定要删除吗？','${ctx}/goods/goods/delete.do?id=${goods.id}','该玩法已有人购买，不能删除');">
												<i class="iconfont icon-delete btn-icon"></i> 删除
											</a>
											<c:if test="${goods.checkStatus == 1}">
												<a href="${ctx}/order/order/goodsOrderList.do?type=0&goodsId=${goods.id}"> <i class="iconfont icon-peoplelist btn-icon"></i> 预定管理</a>
											</c:if>
											<c:if test="${goods.checkStatus == 0}">
												<a class="green" href="javascript:verify('确认要通过吗？','1','${goods.id}')"> <i class="iconfont icon-check btn-icon"></i>通过
												</a>
												<a class="red" href="javascript:verify('确认要拒绝吗？','2','${goods.id}')"> <i class="iconfont icon-close btn-icon"></i>拒绝
												</a>
											</c:if>
										</div>
									</div>
								</div>
							</div>
							<div class="f-def-dialog bmDialog">
								<div class="f-dialog-shadow"></div>
								<div class="f-dialog-content">
									<span class="close-icon"><i class="iconfont icon-close"></i></span>
									<div class="dialog-detail">
										<p class="f16 gray">扫码二维码可预览分享</p>
										<div class="dialog-content">
											<div class="l">
												<div class="f18">报名二维码</div>
												<img class="download-img" src="${qr_code}/${goods.qrCodeUrl }" />
												<div>
													<a class="download-qrcode" href="javascript:download('${ctx}','${goods.qrCodeUrl}')">下载二维码</a>
												</div>
											</div>
											<div class="l">
												<div class="f18">分销二维码</div>
												<img class="download-img" src="${qr_code}/${goods.disQrCode }" />
												<div>
													<a class="download-qrcode" href="javascript:download('${ctx}','${goods.disQrCode}')">下载二维码</a>
												</div>
											</div>
											<div class="cl"></div>
										</div>
									</div>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div id="page_content" class="page-container"></div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">

    showActive('${input.createStart}', '${input.createEnd}', '#timeType');


	// 二维码弹窗显示/隐藏
	qrCodeDialog('.c-time-list-content', 'qr-btn', '.detail-content', '.bmDialog');
    $(function () {
//加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })
	// 审核
	function verify(content, checkStatus, id) {
		layer.confirm(content, {
			icon : 3,
			title : '系统提示'
		}, function(index) {
			layer.close(index);
			$.post("${ctx}/goods/goods/verify.do", {
				checkStatus : checkStatus,
				id : id
			}, function(data) {
				if (data.success == true) {
					layer.alert("审核成功", {
						icon : 6
					}, function(index) {
						window.location.reload();
					});
				}
			})
		});
	}
</script>
</body>
</html>