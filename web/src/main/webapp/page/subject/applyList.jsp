<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>我发布的专题应用</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<%@include file="../include/commonFile.jsp"%>
<style type="text/css">
.content-left {
	width: 20%;
	background-color: #efefef;
	padding-left: 10px;
	padding-right: 10px;
	padding-bottom: 10px;
	min-height: 500px;
}

.content-left h3 {
	height: 30px;
	line-height: 30px;
	border-bottom: 1px solid #ccc;
}

.content-left .apply-list {
	margin-bottom: 20px;
}

.content-left .apply-list li {
	padding-bottom: 0px;
	padding-top: 10px;
	height: 25px;
}

.content-left .apply-list li a:hover {
	color: red;
}

.content-right {
	width: 75%;
	padding-left: 10px;
	padding-right: 10px;
}

.content-right .types {
	border: 1px solid #efefef;
	padding-left: 10px;
	padding-top: 10px;
	padding-bottom: 10px;
	margin-bottom: 10px;
	color: #aaa;
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
					<a href="${ctx}/subject/subject/subjectList.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">我发布的专题 &gt; ${subject.name}</span>
				</div>
			</div>
			<div style="padding-top: 20px;">
				<div class="content-left l">
					<h3>栏目管理</h3>
					<ul class="apply-list">
						<c:forEach var="apply" items="${applies}">
							<li>
								<div class="l" style="display: flex">
									<a title="${apply.name}" style="width:130px;" class="${apply.id} ell" href="javascript:loadData('${apply.id}')">${apply.name}</a>
								</div>
								<div class="r opts-btn" style="display: none;">
									<a href="${ctx}/subject/apply/publishApply.do?id=${apply.id}&subjectId=${subject.id}"><i class="iconfont icon-edit btn-icon"></i></a> <a
										href="javascript:deleteApply('${apply.id}', '${subject.id}')"
									><i class="iconfont icon-delete btn-icon"></i></a>
								</div>
								<div class="cl"></div>
							</li>
						</c:forEach>
					</ul>
					<div>
						<a href="${ctx}/subject/apply/publishApply.do?subjectId=${subject.id}" class="layui-btn layui-btn-radius layui-btn-danger layui-btn-small">
							<i class="iconfont icon-add btn-icon"></i> 添加栏目
						</a>
					</div>
				</div>
				<div class="content-right l">
					<c:if test="${fn:length(applies) == 0}">
						<div class="f16 tc mt15">可点击左侧“添加栏目”进行添加</div>
					</c:if>
					<div id="types" class="types" style="display: none;">
						<p>
							类型： <span id="typeName"></span>
						</p>
						<p>
							说明：<span id="typeDesc"></span>
						</p>
					</div>
					<div id="article_lists" style="display: none;">
						<form class="layui-form" action="${ctx}/subject/apply/applyList.do" id="searchForm" method="post">
							<input type="hidden" name="pageNo" id="pageNo" />
							<input type="hidden" name="subjectId" value="${subject.id}" />
							<input type="hidden" name="applyId" class="applyId" />
							<!-- 							<div class="f-search-bar"> -->
							<!-- 								<div class="search-container"> -->
							<!-- 									<ul class="search-form-content"> -->
							<!-- 										<li class="form-item-inline"><label class="search-form-lable">标题</label> -->
							<!-- 											<div class="layui-input-inline"> -->
							<!-- 												<input type="text" name="name" autocomplete="off" class="layui-input" placeholder="请输入查询标题"> -->
							<!-- 											</div></li> -->
							<!-- 										<li class="form-item-inline"> -->
							<!-- 											<div class="sub-btns"> -->
							<!-- 												<a class="layui-btn layui-btn-danger" id="submitBtn">确定</a> <a class="layui-btn layui-btn-normal" id="resetBtn">重置</a> -->
							<!-- 											</div> -->
							<!-- 										</li> -->
							<!-- 										<li class="form-item-inline"></li> -->
							<!-- 									</ul> -->
							<!-- 								</div> -->
							<!-- 							</div> -->
						</form>
						<div class="sub-btns tr">
							<a class="layui-btn layui-btn-danger" id="articleBtn">添加文章</a>
						</div>
						<table class="layui-table lay-skin="line"">
							<colgroup>
								<col width="200">
								<col width="100">
								<col width="180">
								<col width="150">
								<col>
							</colgroup>
							<thead>
								<tr>
									<th>标题</th>
									<th>链接</th>
									<th>时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<div id="page_content" class="page-container"></div>
					</div>
					<div id="url_content" style="display: none;">
						<form id="myForm" class="layui-form" method="post" action="${ctx}/subject/apply/save.do">
							<div class="layui-form-item">
								<label class="layui-form-label">链接：</label>
								<div class="layui-input-block">
									<input type="text" style="width: 85%" class="layui-input" id="url" name="url" />
									<input type="hidden" name="id" class="applyId" />
									<input type="hidden" name="subjectId" value="${subject.id}" />
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-input-block">
									<button class="layui-btn layui-btn-danger" lay-submit lay-filter="*">保存生效</button>
									<button class="layui-btn layui-btn" type="button" id="resetBtn">取消</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="cl"></div>
			</div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
</body>
<script type="text/html" id="article_template">
{{#  layui.each(d, function(index, item){ }}
<tr>
	<td title="{{ item.title }}"><a class="ell db" style="width:300px;" href="${ctx}/article/article/articleDetail.do?id={{item.id}}">{{ item.title }}</a></td>
	<td><a href="javascript:void(0);" class="red qr-btn">查看</a></td>
	<td>{{ getDateStr(item.updateDate).Format('yyyy-MM-dd hh:mm') }}</td>
	<td>
		<a href="${ctx}/article/article/publishArticle.do?id={{ item.id }}&subjectId=${subject.id}&applyId={{ item.applyId }}"><i class="iconfont icon-edit btn-icon"></i></a>
		<a href="javascript:deleteArticle('{{ item.id }}', '${subject.id}', '{{ item.applyId }}')"><i class="iconfont icon-delete btn-icon"></i></a>
		<div class="f-def-dialog">
			<div class="f-dialog-shadow"></div>
			<div class="f-dialog-content">
				<span class="close-icon"><i class="iconfont icon-close"></i></span>
				<div class="dialog-detail">
					<p class="f16 gray">扫码二维码可预览分享</p>
					<img class="download-img" src="${qr_code}/{{item.qrCodeUrl }}" />
				</div>
			</div>
		</div>
	</td>
</tr>
{{# }); }}
</script>
<script type="text/javascript">

    var laytpl = null;
    var laypage = null;

    $(function() {

        layui.use([ 'laypage', 'laytpl', 'form' ], function() {
            laytpl = layui.laytpl;
            laypage = layui.laypage;
            var form = layui.form();

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('保存成功', {icon : 1}, function(index){
                            location.href = "${ctx}/subject/apply/applyList.do?subjectId=" + res.data.subjectId + "&applyId="+res.data.applyId;
                        });
                    } else {
                        top.layer.msg('保存失败', {icon : 2});
                    }
                });
                return false;
            });

            if ('${firstId}' != "") {
                loadData('${firstId}');
            }
        });
		$("#resetBtn").click(function(){
			$("#myForm").find("input[type=text]").val("");
		});
		
		$(".content-left .apply-list li").hover(function() {
			$(this).find(".opts-btn").show();
		}, function() {
			$(this).find(".opts-btn").hide();
		});

		$('tbody').delegate('td', 'click', function(e) {
			var $target = $(e.target);

			if ($target.hasClass('qr-btn')) {
				var dialog = $target.closest("tr").find(".f-def-dialog");
				$(dialog).fadeIn();
			}
		});
		$('tbody').delegate('.f-def-dialog .close-icon', 'click', function(e) {
			$(this).closest('.f-def-dialog').fadeOut();
		});
	});


	function deleteArticle(articleId, subjectId, applyId) {
		layer.confirm("确定要删除吗？", {
			icon : 3,
			title : '系统提示'
		}, function(index) {
			$.post("${ctx}/article/article/delete.do", {
				articleId : articleId,
				subjectId : subjectId,
				applyId : applyId
			}, function(res) {
				if (res.data != undefined) {
					layer.alert("删除成功", {
						icon : 6
					}, function(index) {
						location.href = "${ctx}/subject/apply/applyList.do?subjectId=" + res.data.subjectId + "&applyId="+res.data.applyId;
					});
				}
			});
			layer.close(index);
		});
	}

	function deleteApply(applyId, subjectId) {
		layer.confirm("确定要删除吗？", {
			icon : 3,
			title : '系统提示'
		}, function(index) {
			$.post("${ctx}/subject/apply/delete.do", {
				applyId : applyId,
				subjectId : subjectId
			}, function(res) {
				if(res.success == false){
					if(res.description != ""){
						layer.alert("该栏目下已有文章，不能删除", {
							icon : 6
						});
					} else {
						layer.alert("删除失败", {
							icon : 6
						});
					}
				} else{
					if (res.data != undefined) {
						layer.alert("删除成功", {
							icon : 6
						}, function(index) {
							location.href = "${ctx}/subject/apply/applyList.do?subjectId=" + res.data;
						});
					}
				}
			});
		});
	}

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

					$.post("${ctx}/subject/apply/loadJsonData.do", $('#searchForm').serialize(), function(data) {
						// 填充数据
						appendHtml(data.articles);
					});
				}
			}
		});
	}

	function appendHtml(articles) {
		var getTpl = $("#article_template").html();
		var content = $("tbody");
		$(content).html("");
		laytpl(getTpl).render(articles, function(html) {
			content.append(html);
		});
	}

	function loadData(applyId) {
		$(".apply-list a").removeClass("red");
		$("." + applyId).addClass("red");
		$.post("${ctx}/subject/apply/loadJsonData.do", {
			applyId : applyId,
			pageNo : 1,
			title : ""
		}, function(data) {
			$("#types").show();
			var typename = "";
			var typedesc = "";
			if (data.subjectApply.type == "url") {
				typename = "单链接";
				typedesc = "可以连接到一个固定的网页链接地址";
				$("#url_content").show();
				$("#article_lists").hide();
				$("#url").val(data.subjectApply.url);
			} else if (data.subjectApply.type == "article") {
				typename = "单篇文章";
				typedesc = "可以指向一篇文章内容";
				$("#url_content").hide();
				$("#article_lists").show();
				$("#articleBtn").attr("href", '${ctx}/article/article/publishArticle.do?applyId=' + data.subjectApply.id);
				if (data.articles.length == 1) {
					$("#articleBtn").parent().hide();
				}
			} else if (data.subjectApply.type == "channel") {
				typename = "文章列表";
				typedesc = "可以添加多个文章内容";
				$("#url_content").hide();
				$("#article_lists").show();
				$("#articleBtn").attr("href",
						'${ctx}/article/article/publishArticle.do?applyId=' + data.subjectApply.id + "&channelId=" + data.subjectApply.targetId);
				$("#articleBtn").parent().show();
			}

			$(".applyId").val(data.subjectApply.id);

			$("#typeName").text(typename);
			$("#typeDesc").text(typedesc);

			// 加载分页
			loadPage(data.page);

			// 填充数据
			appendHtml(data.articles);
		});
	}
</script>
</html>