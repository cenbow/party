<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>我发布的文章列表</title>
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
					<a href="${ctx}/article/article/publishArticle.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-add btn-icon"></i>
						发布文章
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">文章管理</span> <span class="f12">共<b id="totalCount">${page.totalCount}</b>条记录
					</span>
				</div>
			</div>
			<form class="layui-form" action="${ctx}/article/article/articleList.do" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" />
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">文章标题</label>
								<div class="layui-input-inline">
									<input type="text" name="title" autocomplete="off" class="layui-input" value="${article.title}" placeholder="请输入查询文章标题">
								</div></li>
							<li class="form-item-inline"><label class="search-form-lable">文章类型</label>
								<div class="layui-input-inline">
									<select name="articleType">
										<option value="">全部</option>
										<c:forEach var="type" items="${articleTypes}">
											<option value="${type.value}" ${article.articleType == type.value ? 'selected="selected"' : ''}>${type.label}</option>
										</c:forEach>
									</select>
								</div>
							</li>
							<li class="form-item-inline">
								<div class="sub-btns">
									<a class="layui-btn layui-btn-danger" id="submitBtn">确定</a> <a class="layui-btn layui-btn-normal" id="resetBtn">重置</a>
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">发布时间</label>
								<div class="check-btn-inner">
									<a id="all" href="javascript:void(0);" onclick="setTimeType($(this),0)" ${empty timeType || timeType == 0 ? 'class="active"' : ''}>全部</a> <a
										href="javascript:void(0);" onclick="setTimeType($(this),1)" ${timeType == 1 ? 'class="active"' : ''}
									>今天</a> <a href="javascript:void(0);" onclick="setTimeType($(this),2)" ${timeType == 2 ? 'class="active"' : ''}>本周内</a> <a
										href="javascript:void(0);" onclick="setTimeType($(this),3)" ${timeType == 3 ? 'class="active"' : ''}
									>本月内</a> <input type="hidden" name="timeType" value="${timeType}" />
								</div>
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="c_start" value="${c_start}" placeholder="开始日" onclick="layui.laydate({elem: this})">
									</div>
									-
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="c_end" value="${c_end}" placeholder="截止日" onclick="layui.laydate({elem: this})">
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
						<div class="f16 tc mt15">暂时还没有数据</div>
					</c:if>
					<c:forEach var="article" items="${articles}">
						<li>
							<div class="detail-content">
								<a href="${ctx}/channel/channel/channelDetail.do?id=${article.id}" class="act-logo"
									style="background-image: url('${article.pic}'),url(${ctx}/image/img_bg.png)"
								></a>
								<div class="detail">
									<div class="act-title">
										<a title="${ article.title }" href="${ctx}/article/article/articleDetail.do?id=${ article.id }" class="title f18 ell db">${ article.title }</a>
									</div>
									<div class="">
										<c:if test="${not empty article.subTitle}">
											<p class="act-price ell" title="${ article.subTitle }">简介：${ article.subTitle }</p>
										</c:if>
										<p class="act-price">
											<span style="margin-right: 20px;">频道：<b class='active-red'>${ article.channel.name }</b></span> <span>发布者：<b class="active-red">${article.member}</b><span>
										</p>
										<c:if test="${not empty article.subTitle}">
											<p class="act-price">
												<span style="margin-right: 20px;">阅读量：<b class='active-red'>${ article.readNum }</b></span> <span style="margin-right: 20px;">文章类型：<b
													class='active-red'
												>${ article.articleType }</b></span> <span>发布时间：<fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd HH:mm" /></span>
											</p>
										</c:if>
										<c:if test="${empty article.subTitle}">
											<p class="act-price">
												阅读量：<b class='active-red'>${ article.readNum }</b>
											</p>
											<p class="act-price">文章类型：${ article.articleType }</p>
											<p class="act-price">
												发布时间：
												<fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd HH:mm" />
											</p>
										</c:if>
									</div>
									<div class="opts-btns tb-opts" style="width:475px;">
										<div class="comm-opts">
											<a class="qr-btn" href="javascript:void(0);"><i class="iconfont icon-share btn-icon"></i> 二维码</a>
											<a href="${ctx}/article/article/publishArticle.do?id=${article.id}" ><i class="iconfont icon-edit btn-icon"></i> 编辑</a>
											<a href="javascript:deleteObj('确定要删除吗？','${article.id}')" ><i class="iconfont icon-delete btn-icon"></i> 删除</a>
										</div>
									</div>
								</div>
							</div>
							<div class="f-def-dialog">
								<div class="f-dialog-shadow"></div>
								<div class="f-dialog-content">
									<span class="close-icon"><i class="iconfont icon-close"></i></span>
									<div class="dialog-detail">
										<p class="f16 gray">扫码二维码可预览分享</p>
										<img class="download-img" src="${qr_code}/${ article.qrCodeUrl }" />
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
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">

	function setTimeType(obj, type) {
		$(obj).closest(".check-btn-inner").find("a").removeClass("active");
		$(obj).addClass("active");
		$(obj).closest(".check-btn-inner").find("[name=timeType]").val(type);

		$("#myForm").submit();
	}
	//删除
	function deleteObj(content, targetId) {
		layer.confirm(content, {
			icon : 3,
			title : '系统提示'
		}, function(index) {
			$.post("${ctx}/article/article/delete.do", {
				id : targetId
			}, function(data) {
				if (data.success == true) {
					layer.alert("删除成功", {
						icon : 6
					}, function(index) {
						window.location.reload();
					});
				} else {
					layer.alert("删除失败", {
						icon : 6
					});
				}
			});
		});
	}
	$(function() {
        layui.use([ 'form', 'laydate', 'laypage' ], function() {
            var form = layui.form(), laydate = layui.laydate, laypage = layui.laypage;
            var $ = layui.jquery; //重点处
            laypage({
                cont : 'page_content',
                pages : '${page.totalPages}',
                curr : function() {
                    return '${page.page}';
                }(),
                skip : true,
                skin : '#ea5952',
                jump : function(obj, first) {
                    if (!first) {
                        $("#myForm").find("#pageNo").val(obj.curr);
                        $("#myForm").submit();
                    }
                }
            });
        });

        $('.c-time-list-content').delegate('.detail-content', 'click', function(e) {
			var $target = $(e.target);

			if ($target.hasClass('qr-btn')) {
				var dialog = $target.closest(".detail-content").siblings("div");
				$(dialog).fadeIn();
			}
		});
		$('.c-time-list-content').delegate('.f-def-dialog .close-icon', 'click', function(e) {
			$(this).closest('.f-def-dialog').fadeOut();
		});

		$("#submitBtn").click(function() {
			$("#myForm").submit();
		});

		$("#resetBtn").click(function() {
			$("#myForm input[type=text]").val("");
			$("#myForm select").find("option:eq(0)").attr("selected", true);
			$(".check-btn-inner").find("a").removeClass("active");
			$(".check-btn-inner").find("a:eq(0)").addClass("active")
			$(".check-btn-inner [name=timeType]").val("");
		});
	});
</script>
</body>
</html>