<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>广告管理</title>
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
					<a href="${ctx}/ad/form.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i
							class="iconfont icon-add btn-icon"
					></i>添加广告
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">广告管理</span> <span class="f12">共<b id="totalCount">${page.totalCount}</b>条记录
					</span>
				</div>
			</div>
			<form class="layui-form" action="${ctx}/ad/list.do" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" />
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">标题</label>
								<div class="layui-input-inline">
									<input type="text" name="title" autocomplete="off" class="layui-input" value="${ad.title}"
										   placeholder="请输入查询标题"
									>
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">类型</label>
								<div class="layui-input-inline">
									<select name="adPos">
										<option value="">全部</option>
										<c:forEach var="type" items="${adTypes}">
											<option value="${type.value}" ${ad.adPos == type.value ? 'selected="selected"' : ''}>${type.label}</option>
										</c:forEach>
									</select>
								</div>
							</li>
							<li class="form-item-inline">
								<div class="sub-btns">
									<a class="layui-btn layui-btn-danger" href="javascript:submitFunction('#myForm')">确定</a>
									<a class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</form>
			<div class="c-time-list-content">
				<ul id="view">
					<c:if test="${page.totalCount == 0}">
						<div class="f16 tc mt15">还没有数据，请点击右上角的“添加广告”，添加广告</div>
					</c:if>
					<c:forEach var="advertise" items="${list}">
						<li>
							<div class="detail-content">
								<a href="${ctx}/ad/adDetail.do?id=${advertise.id}"  class="act-logo"
								   style="background-image: url('${advertise.pic}'),url(${ctx}/image/img_bg.png)"
								></a>
								<div class="detail">
									<div class="act-title">
										<a title="${advertise.title}"  class="title f18 ell db">${advertise.title}</a>
									</div>
									<div>
										<p class="act-price">
											来源：
											<c:choose>
												<c:when test="${advertise.origin == '0'}">
													<span class=" ">外部广告</span>
												</c:when>
												<c:when test="${advertise.origin == '1'}">
													<span class=" ">内部广告</span>
												</c:when>
											</c:choose>
										</p>
										<p class="act-price">
											位置：
											<c:choose>
												<c:when test="${advertise.adPos == 'partner'}">
													<span class=" red">首页条幅广告</span>
												</c:when>
												<c:when test="${advertise.adPos == '1'}">
													<span class=" red">活动广告</span>
												</c:when>
												<c:when test="${advertise.adPos == '2'}">
													<span class=" red">标准活动预订广告</span>
												</c:when>
												<c:when test="${advertise.adPos == '5'}">
													<span class=" red">app初始广告</span>
												</c:when>
												<c:when test="${advertise.adPos == '3'}">
													<span class=" red">社区广告</span>
												</c:when>
												<c:when test="${advertise.adPos == '4'}">
													<span class=" red">定制活动预定广告</span>
												</c:when>
											</c:choose>
										</p>
									</div>
									<div class="opts-btns tb-opts"  style="width:475px">
										<div class="comm-opts">
											<a href="${ctx}/ad/form.do?id=${advertise.id}" > <i class="iconfont icon-edit btn-icon"></i> 编辑</a>
											<a href="javascript:deleteObj('确定要删除吗？','${ctx}/ad/delete.do?id=${advertise.id}');" >
												<i class="iconfont icon-delete btn-icon"></i> 删除
											</a>
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
    $(function () {
//加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })



</script>
</body>
</html>