<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>频道详情</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/channel/channel_detail.css">
</head>
<%@include file="../include/header.jsp"%>
<body>
	<div class="index-outside">
		<%@include file="../include/sidebar.jsp"%>
		<section>
			<div class="section-main">
				<div class="act-detail-head">
					<div class="act-detail-content">
						<div class="detail-logo" style="background-image: url('${channel.picture}'),url(${ctx}/image/img_bg.png)"></div>
					</div>
					<div class="content-text">
						<p class="f24 text-titel">${channel.name}</p>
						<p class="f16 text-element">
							<i class="iconfont icon-remind btn-icon"></i> 发布时间：
							<fmt:formatDate value="${channel.createDate}" pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p class="f16 text-element">描述：${channel.remarks}</p>
					</div>
				</div>
				<div class="detail-head mt20">
					<span class="head-title f20">文章列表<span id="articleTotal" class="pl5"></span></span>
				</div>
				<div class="act-main-body" id="article_content">
					<form class="layui-form" id="searchForm" method="post">
						<input type="hidden" name="pageNo" id="pageNo" /> <input type="hidden" name="channelId" value="${channel.id}" />
					</form>
					<ul class="article-list-content"></ul>
					<div id="page_content" class="page-container"></div>
				</div>
			</div>
		</section>
	</div>
	<%@include file="../include/footer.jsp"%>
	<script id="article_template" type="text/x-dot-template">
    	{{#  layui.each(d, function(index, item){ }}
		<div class="article-detail-content">
			<div class="article-logo" style="background-image: url('{{ item.pic }}'),url(${ctx}/image/img_bg.png)"></div>
			<div class="article-detail">
				<div class="article-title">
					<a href="${ctx}/article/article/articleDetail.do?id={{ item.id }}" class="title f18">{{ item.title }}</a>
				</div>
				<div>
					<p class="start-time">
						发布时间：{{ getDateStr(item.createDate).Format('yyyy-MM-dd hh:mm') }}
					</p>
					{{# if(util.isValid(item.subTitle)){ }}
						<p class="start-time">
							简介：{{ item.subTitle }}
						</p>
						<p class="start-time">
							<span style="margin-right: 20px;">阅读量：<b class='active-red'>{{ item.readNum }}</b></span>
							<span>文章类型：<b class='active-red'>{{ item.articleType }}</b><span>
						</p>
					{{# } else { }}
						<p class="start-time">
							阅读量：<b class='active-red'>{{ item.readNum }}</b>
						</p>
						<p class="start-time">
							文章类型：{{ item.articleType }}
						</p>
					{{# } }}
				</div>
			</div>
		</div>
		{{#  }); }}
	</script>
	<script type="text/javascript">
		var laytpl = null;
		var laypage = null;
		$(function () {
            layui.use([ 'laytpl', 'laypage' ], function() {
                laytpl = layui.laytpl;
                laypage = layui.laypage;

                getArticleData();
            });
        })


		function loadPage(page) {
			laypage({
				cont : 'page_content',
				pages : page.totalPages,
				curr : function() {
					return page.page;
				}(),
				skip : true,
				skin : '#ea5952',
				jump : function(obj, first) {
					if (!first) {
						$("#searchForm").find("#pageNo").val(obj.curr);

						$.post("${ctx}/article/article/getArticleByChanne.do", $('#searchForm').serialize(), function(data) {
							// 填充数据
							appendHtml(data.articles);
						});
					}
				}
			});
		}

		function appendHtml(articles) {
			var getTpl = $("#article_template").html();
			var content = $('#article_content ul');
			$(content).html("");
			laytpl(getTpl).render(articles, function(html) {
				content.append(html);
			});
		}

		// 专题下的文章
		function getArticleData() {
			$.post("${ctx}/article/article/getArticleByChanne.do", {
				pageNo : 1,
				channelId : '${channel.id}'
			}, function(data) {
				$("#articleTotal").text(data.page.totalCount);
				// 加载分页
				loadPage(data.page);

				// 填充数据
				appendHtml(data.articles);
			});
		}
	</script>
</body>
</html>
