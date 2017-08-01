<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>众筹项目管理</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<%@include file="../include/commonFile.jsp"%>
<style type="text/css">
	.f-def-dialog .f-dialog-content {
		width: 400px !important;
		height: 300px !important;
	}
	
	.f-dialog-content .dialog-detail {
		width: auto !important;
	}
	
	.dialog-detail .download-img {
		margin-top: 0px !important;
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
					<a href="${ctx}/activity/activity/zcActivityForm.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-add btn-icon"></i>发布众筹项目
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">众筹项目管理</span> <span class="f12">共<b id="totalCount">${page.totalCount}</b>条记录
					</span>
				</div>
			</div>
			<form class="layui-form" action="${ctx}/activity/activity/zcActivityList.do" id="myForm" method="get">
				<input type="hidden" name="pageNo" id="pageNo" />
				<input type="hidden" name="eventId" value="${activity.eventId}" />
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">项目标题</label>
								<div class="layui-input-inline">
									<input type="text" name="title" autocomplete="off" class="layui-input" value="${activity.title}" placeholder="请输入查询项目标题">
								</div></li>
							<li class="form-item-inline"><label class="search-form-lable">发布者</label>
								<div class="layui-input-inline">
									<input type="text" name="memberName" autocomplete="off" class="layui-input" value="${input.memberName}" placeholder="请输入查询项目发布者">
								</div></li>
							<li class="form-item-inline"><label class="search-form-lable">活动状态</label>
                                <div class="layui-input-inline">
                                    <select name="checkStatus">
                                        <option value="">全部</option>
                                        <c:forEach var="status" items="${checkStatus}">
                                            <option value="${status.value}" ${activity.checkStatus == status.value ? 'selected="selected"' : ''}>${status.label}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">截止时间</label>
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="e_start" value="${e_start}" placeholder="开始日" onclick="layui.laydate({elem: this})">
									</div>
									-
									<div class="layui-input-inline">
										<input class="layui-input" type="text" name="e_end" value="${e_end}" placeholder="截止日" onclick="layui.laydate({elem: this})">
									</div>
								</div>
								<div class="cl"></div></li>
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
				<ul id="view">
					<c:if test="${page.totalCount == 0}">
						<div class="f16 tc mt15">还没有数据，请点击右上角的“发布众筹项目”</div>
					</c:if>
					<c:forEach var="activity" items="${activities}">
						<li>
							<div class="detail-content">
								<a href="${ctx}/activity/activity/zcActivityDetail.do?actId=${activity.id}" target="_self" class="act-logo"
									style="background-image: url('${activity.pic}'),url(${ctx}/image/img_bg.png)"
								></a>
								<div class="detail">
									<div class="act-title">
										<c:choose>
											<c:when test="${activity.checkStatus == 0}">
												<span class="status f20 red">审核中</span>
											</c:when>
											<c:when test="${activity.checkStatus == 1}">
												<c:set var="nowDate" value="<%=new Date()%>"></c:set>
												<c:if test="${activity.endTime > nowDate}">
													<span class="status f20 red">进行中</span>
												</c:if>
												<c:if test="${activity.endTime < nowDate}">
													<span class="status f18 dark">已截止</span>
												</c:if>
											</c:when>
											<c:when test="${activity.checkStatus == 2}">
												<span class="status f18 dark">已拒绝</span>
											</c:when>
										</c:choose>
										<a title="${activity.title}" href="${ctx}/activity/activity/zcActivityDetail.do?actId=${activity.id}" target="_self" class="title f18 ell db">${activity.title}</a>
									</div>
									<div>
										<p class="act-price">
											截止时间：
											<fmt:formatDate value="${activity.endTime}" pattern="yyyy-MM-dd HH:mm" />
										</p>
										<p class="act-price">
											<span style="margin-right: 10px;">报名费用：<b class="active-red">${activity.price}</b>元/位
											</span> <span>报名人数：<b class="active-red">${activity.joinNum}</b>人<span> <span>发布者：<b class="active-red">${activity.member}</b><span>
										</p>
										<p class="start-time">活动地点：${activity.area} ${activity.place}</p>
										<p class="act-price">
											发布时间：
											<fmt:formatDate value="${activity.updateDate}" pattern="yyyy-MM-dd HH:mm" />
										</p>
									</div>
									<div class="opts-btns tb-opts" style="width:475px">
										<div class="comm-opts">
											<c:if test="${activity.checkStatus == 1}">
												<a class="qr-btn" href="javascript:void(0);"><i class="iconfont icon-qrcode btn-icon"></i> 二维码</a>
											</c:if>
											<a href="${ctx}/activity/activity/zcActivityForm.do?id=${activity.id}"> <i class="iconfont icon-edit btn-icon"></i> 编辑</a>
											<a href="javascript:deleteObj('确定要删除众筹项目吗？','${ctx}/activity/activity/deleteLogic.do?id=${activity.id}','该众筹项目已有人报名，不能删除')">
												<i class="iconfont icon-delete btn-icon"></i> 删除
											</a>
											<a href="javascript:createCircle('${activity.id}')"> <i class="iconfont icon-check btn-icon"></i>生成圈子</a>
											<c:if test="${activity.checkStatus == 0}">
												<a class="green" href="javascript:verify('确认要通过吗？','1','${activity.id}')"> <i class="iconfont icon-check btn-icon"></i>通过</a>
												<a class="red" href="javascript:verify('确认要拒绝吗？','2','${activity.id}')"> <i class="iconfont icon-close btn-icon"></i>拒绝</a>
											</c:if>
											<a href="${ctx}/activity/activity/zcRepresentList.do?id=${activity.id}"> <i class="iconfont icon-scan btn-icon"></i>
												数据查看
											</a>
											<a href="${ctx}/notify/message/list.do?targetId=${activity.id}"> <i class="iconfont icon-mobile1 btn-icon"></i>
												消息推送
											</a>
											<a target="_blank" href="${ctx}/crowdfund/analyze/list.do?targetId=${activity.id}"> <i class="iconfont icon-news btn-icon"></i>
												数据分析
											</a>
											<c:if test="${removeEvent}">
												<a href="javascript:deleteObj('确定要移除众筹事项？','${ctx}/crowdfund/project/removeEvent.do?id=${activity.id}','系统异常')">
													<i class="iconfont icon-delete btn-icon"></i> 移除事项
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
										<div style="width: 320px; margin: 0px auto; margin-top: 10px">
											<div class="l" style="margin-right: 20px;">
												<div class="f18">报名二维码</div>
												<img class="download-img" src="${qr_code}/${activity.bmQrCodeUrl }" />
												<div>
													<a class="download-qrcode" href="javascript:download('${ctx}','${activity.bmQrCodeUrl}')">下载二维码</a>
												</div>
											</div>
											<div class="l">
												<div class="f18">代言二维码</div>
												<img class="download-img" src="${qr_code}/${activity.qrCodeUrl }" />
												<div>
													<a class="download-qrcode" href="javascript:download('${ctx}','${activity.qrCodeUrl}')">下载二维码</a>
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
    $(function () {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
        // 二维码弹窗显示/隐藏
        qrCodeDialog('.c-time-list-content', 'qr-btn', '.detail-content', '.bmDialog');

    })

	// 审核
	function verify(content, checkStatus, actId) {
		layer.confirm(content, {
			icon : 3,
			title : '系统提示'
		}, function(index) {
			layer.close(index);
			$.post("${ctx}/activity/activity/verify.do", {
				checkStatus : checkStatus,
				actId : actId
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
	
	function createCircle(actId) {
		var content = "确定要生成吗？请选择生成<br/><label for='all1' class='mr10'><input type='radio' id='all1' name='type' value='2' checked='checked' >全部报名人员</label>"+
					  "<label for='success'><input type='radio' id='success' name='type' value='1' >已成功报名人员</label>";
// 		content = "确定要生成吗？";
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
            $.post("${ctx}/activity/activity/createCircle.do", {
                actId: actId,
                type : type
            }, function (data) {
            	layer.close(loadIndex);
            	setTimeout(function() {
	            	if (data.success == true) {
	                	layer.msg('生成成功', {icon : 1});
	                } else {
	                	layer.msg('生成失败', {icon : 2});
	                }
            	});
            })
        });
  	}

	function send(id) {
		var content = "请选择类型<br/><label for='all1' class='mr10'><input type='radio' id='all1' name='type' value='0' checked='checked' >所有众筹</label>"+
				"<label for='success' class='mr10'><input type='radio' id='success' name='type' value='1' >众筹成功</label>"+
				"<label for='success'><input type='radio' id='success' name='type' value='2' >众筹中</label>";
		layer.confirm(content, {
			icon: 3,
			title: '系统提示'
		}, function (index) {
			var type = $("[name=type]:checked").val() || '0';
			layer.close(index);
			$.post("${ctx}/crowdfund/event/createCircle.do", {
				id: id ,
				type : type
			}, function (data) {
				if (data.success == true) {
					layer.msg('生成成功', {icon : 1});
				} else {
					layer.msg('生成失败', {icon : 2});
				}
			})
		});
	}
</script>
</body>
</html>