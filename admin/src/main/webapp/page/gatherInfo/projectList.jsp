<%@page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>人员信息收集分组</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <%@include file="../include/commonFile.jsp" %>
    <style type="text/css">

        .layui-table td, .layui-table th{
            padding-left: 10px!important;
            padding-right: 10px!important;
        }
    </style>
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp" %>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/gatherInfo/project/toForm.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-add btn-icon"></i> 发布项目 </a>
                </div>
                <div class="ovh">
                    <span class="title f18">项目管理</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/gatherInfo/project/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo"/>
                <div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">项目名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="title" autocomplete="off" class="layui-input" value="${project.title}" placeholder="请输入查询项目名称">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">发布者</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="memberName" autocomplete="off" class="layui-input" value="${input.memberName}" placeholder="请输入查询项目发布者">
                                </div>
                            </li>
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
            <div class="list-content">
                <div class="cl">
                    <table class="layui-table">
                        <colgroup>
                            <col>
                            <col>
                            <col width="90px">
                            <col width="90px">
                            <col width="150px">
                            <col>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>项目名称</th>
                            <th>发布者</th>
                            <th>小组数量</th>
                            <th>收集人数</th>
                            <th>发布时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="project" items="${projectList}">
                            <tr class="detail-content1">
                                <td class="table-member" title="${project.title}">
                                    <div class="member-name ellipsis-1">${project.title}</div>
                                </td>
                                <td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${project.member.id}','400px','470px')">
                                    <div class="member-cell">
                                    <div class="member-logo" style="background-image: url('${project.member.logo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                    <div class="member-name ellipsis-1"><a class="blue" title="${project.member.realname}" href="javascript:void(0);">${project.member.realname}</a></div>
                                    </div>
                                </td>
                                <td><div>${project.groupNum}</div></td>
                                <td><div>${project.totalNum}</div></td>
                                <td><div><fmt:formatDate value="${project.updateDate}" pattern="yyyy-MM-dd HH:mm"/></div></td>
                                <td class="opts-btns tb-opts" style="width:330px">
		                            <div class="f-def-dialog bmDialog">
										<div class="f-dialog-shadow"></div>
										<div class="f-dialog-content">
											<span class="close-icon"><i
												class="iconfont icon-close"></i></span>
											<div class="dialog-detail">
												<p class="f16 gray">扫码二维码可预览分享</p>
												<img class="download-img" src="${qr_code}/${project.baseQrCodeUrl }"/>
												<div>
													<a class="download-qrcode" href="javascript:download('${ctx}','${project.baseQrCodeUrl}')">下载二维码</a>
												</div>
											</div>
										</div>
									</div>
                                    <div class="comm-opts">
			                            <a class="qr-btn" href="javascript:void(0);">二维码</a>
                                        <a href="${ctx}/gatherInfo/member/list.do?projectId=${project.id}">人员管理</a>
                                        <a href="${ctx}/gatherInfo/group/list.do?projectId=${project.id}">小组管理</a>
                                        <a class="green" href="${ctx}/gatherInfo/project/toForm.do?id=${project.id}">编辑</a>
                                        <a class="red" href="javascript:deleteObj('${ctx}/gatherInfo/project/delete.do?id=${project.id}');">删除</a>
                                        <a href="javascript:createCircle('${project.id}')">生成赛事项目</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${page.totalCount == 0}">
						<div class="f16 tc mt15">还没有项目</div>
					</c:if>
                </div>
            </div>
            <div id="page_content" class="page-container"></div>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">
   
	showActive('${input.createStart}', '${input.createEnd}', '#timeType');

	// 二维码弹窗显示/隐藏
	qrCodeDialog('.layui-table', 'qr-btn', '.detail-content1', '.bmDialog', "3");
    $(function () {
//加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })
	function deleteObj(url) {
		layer.confirm('确定要删除吗？', {
			icon : 3,
			title : '系统提示'
		}, function(index) {
			$.post(url, {}, function(data) {
				if (data.success == true) {
					layer.msg("删除成功", {
						icon : 1
					}, function(index) {
						location.reload();
					});
				} else {
					if (data.description != null) {
						layer.msg(data.description, {
							icon : 6
						});
					} else {
						layer.msg("删除失败", {
							icon : 6
						});
					}
				}
			});
		});
	}
	
	function createCircle(id) {
		var content = "确定要生成吗？";
  		layer.confirm(content, {
            icon: 3,
            title: '系统提示'
        }, function (index) {
        	var type = $("[name=type]:checked").val() || '2';
            layer.close(index);
          	//loading层
			var loadIndex = layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
            $.post("${ctx}/gatherInfo/project/syncCompetition.do", {
                id : id,
                type : type
            }, function (data) {
            	layer.close(loadIndex);
            	setTimeout(function() {
	            	if (data.success == true) {
	                	layer.msg('生成成功', {icon : 1});
	                } else {
	                	layer.msg('生成失败', {icon : 2});
	                }
				}, 500);// 延时0.5秒
            })
        });
  	}

</script>
</body>
</html>