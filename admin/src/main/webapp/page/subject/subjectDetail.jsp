<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>专题详情</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/subject/subject_detail.css">
</head>
<%@include file="../include/header.jsp"%>
<body>
	<div class="index-outside">
		<%@include file="../include/sidebar.jsp"%>
		<section>
			<div class="section-main">
				<div class="c-time-list-header">
					<div class="r">
						<a href="${ctx}/subject/subject/subjectList.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
						</a>
					</div>
					<div class="ovh">
						<span class="title f20">专题管理&nbsp;&gt;&nbsp;专题详情</span>
					</div>
				</div>
				<div class="act-detail-head mt20">
					<div class="act-detail-content">
						<div class="detail-logo" style="background-image: url('${subject.picture}')"></div>
					</div>
					<div class="content-text">
						<p class="f24 text-titel">${subject.name}</p>
						<p class="f16 text-element">
							<i class="iconfont icon-remind btn-icon"></i> 发布时间：
							<fmt:formatDate value="${subject.createDate}" pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p class="f16 text-element">描述：${subject.remarks}</p>
					</div>
				</div>
				<div class="detail-head mt20">
					<span class="head-title f20">栏目列表<span class="pl5">${page.totalCount}</span></span>
				</div>
				<div class="act-main-body" id="article_content">
					<form class="layui-form" id="searchForm" method="post">
						<input type="hidden" name="pageNo" id="pageNo" /> <input type="hidden" name="subjectId" value="${subject.id}" />
					</form>
					<ul class="article-list-content"></ul>
					<div class="cl"></div>
					<div id="page_content" class="page-container"></div>
				</div>
			</div>
		</section>
	</div>
	<%@include file="../include/footer.jsp"%>
	<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
	<script id="article_template" type="text/x-dot-template">
    	{{#  layui.each(d, function(index, item){ }}
		<div class="article-detail-content">
			<div class="article-logo" style="background-image: url('{{ item.picture }}')"></div>
			<div class="article-detail">
				<a href="javascript:openDialogShow('栏目查看','${ctx}/subject/apply/applyDetail.do?id={{ item.id }}','500px','470px')" class="title f18">{{ item.name }}</a>
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

						$.post("${ctx}/subject/apply/publishListJson.do", $('#searchForm').serialize(), function(data) {
							// 填充数据
							appendHtml(data.datas);
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
			$.post("${ctx}/subject/apply/publishListJson.do", {
				pageNo : 1,
				subjectId : '${subject.id}'
			}, function(data) {
				$("#articleTotal").text(data.page.totalCount);
				// 加载分页
				loadPage(data.page);

				// 填充数据
				appendHtml(data.datas);
			});
		}
	</script>
</body>
</html>
