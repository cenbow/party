<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>频道管理</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<%@include file="../include/commonFile.jsp"%>
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
					<a href="${ctx}/channel/channel/channelForm.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-add btn-icon"></i>
						发布频道
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">频道管理</span> <span class="f12">共<b id="totalCount">${page.totalCount}</b>条记录
					</span>
				</div>
			</div>
			<form class="layui-form" action="${ctx}/channel/channel/channelList.do" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" />
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">频道名称</label>
								<div class="layui-input-inline">
									<input type="text" name="name" autocomplete="off" class="layui-input" value="${channel.name}" placeholder="请输入查询频道名称">
								</div></li>
							<li class="form-item-inline"><label class="search-form-lable">发布者</label>
								<div class="layui-input-inline">
									<input type="text" name="memberName" autocomplete="off" class="layui-input" value="${input.memberName}" placeholder="请输入查询频道发布者">
								</div></li>
							<li class="form-item-inline">
								<div class="sub-btns">
									<a class="layui-btn layui-btn-danger" href="javascript:submitFunction('#myForm')">确定</a>
									<a class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">发布时间</label>
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
				<ul>
					<c:if test="${page.totalCount == 0}">
						<div class="f16 tc mt15">还没有数据，请点击右上角的“发布频道”，发布和编辑内容</div>
					</c:if>
					<c:forEach var="channel" items="${channels}">
						<li>
							<div class="detail-content">
								<a href="${ctx}/channel/channel/channelDetail.do?id=${channel.id}" class="act-logo"
									style="background-image: url('${channel.picture}'),url(${ctx}/image/img_bg.png)"
								></a>
								<div class="detail">
									<div class="act-title">
										<a href="${ctx}/channel/channel/channelDetail.do?id=${channel.id}" class="title f18 ell db">${channel.name}</a>
									</div>
									<div class="">
										<p class="act-price">
											<span style="margin-right: 20px;">文章数量：<b class='active-red'>${channel.articleNum}</b></span> <span>发布者：<b class="active-red">${channel.member}</b><span>
										</p>
										<p class="act-price">
											发布时间：
											<fmt:formatDate value="${channel.updateDate}" pattern="yyyy-MM-dd HH:mm" />
										</p>
										<p class="start-time ell" style="width: 90%" title="${ channel.remarks }">描述：${ channel.remarks }</p>
									</div>
									<div class="opts-btns tb-opts" >
										<div class="comm-opts">
											<a class="qr-btn" href="javascript:void(0);"><i class="iconfont icon-qrcode btn-icon"></i> 二维码</a>
											<a href="${ctx}/channel/channel/channelForm.do?id=${channel.id}"> <i class="iconfont icon-edit btn-icon"></i> 编辑</a>
											<a href="javascript:deleteObj('确定要删除頻道吗？','${ctx}/channel/channel/delete.do?id=${channel.id}','该频道下已有文章，不能删除')">
												<i class="iconfont icon-delete btn-icon"></i> 刪除
											</a>
											<a href="${ctx}/article/article/articleList.do?channel.id=${channel.id}"><i class="iconfont icon-peoplelist btn-icon"></i> 文章管理</a>
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
										<img class="download-img" src="${qr_code}/${channel.qrCodeUrl}" />
										<div>
											<a class="download-qrcode" href="javascript:download('${ctx}','${channel.qrCodeUrl}')">下载二维码</a>
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
</div>
</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">
    $(function () {
//加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })

	// 二维码弹窗显示/隐藏
	qrCodeDialog('.c-time-list-content', 'qr-btn', '.detail-content', '.bmDialog');

    showActive('${input.createStart}', '${input.createEnd}', '#timeType');
</script>
</body>
</html>