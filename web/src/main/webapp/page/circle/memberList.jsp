<%@page import="java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>会员管理</title>
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
				<%--<div class="r">
					<a href="${ctx}/circle/member/form.do?id=${circle.id}" class="layui-btn layui-btn-radius layui-btn-danger">
						<i class="iconfont icon-add btn-icon"></i>添加会员
					</a>
				</div>--%>
				<div class="r">
					<a href="javascript:history.back()" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
				</div>
				<div class="ovh">
					<span class="title f18"><a href="${ctx}/circle/list.do" class="">${circle.name}</a> > 会员管理</span>
					<span class="f12">共<b>${page.totalCount}</b>条记录</span>
				</div>
			</div>
			<form class="layui-form" action="${ctx}/circle/member/list.do?id=${circle.id}" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" />
<%-- 				<input type="hidden" name="id"  value="${circle.id}"/> --%>
				<div class="f-search-bar">
					<div class="search-container">						
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">名称</label>
								<div class="layui-input-inline">${member}
									<input type="text" name="realName" autocomplete="off" class="layui-input" value="${realName}" placeholder="请输入">
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">电话号码</label>
								<div class="layui-input-inline">
									<input type="text" name="mobile" autocomplete="off" class="layui-input" value="${mobile}" placeholder="请输入">
								</div>
							</li>
							<li class="form-item-inline">
								<label class="search-form-lable">类型</label>
								<div class="layui-input-inline">
									<select name="tagId">
										<option value="">全部</option>
										<c:forEach var="tag" items="${circleTags}">
											<option value="${tag.id}" ${tagId == tag.id ? 'selected="selected"' : ''}>${tag.tagName}</option>
										</c:forEach>
									</select>
								</div>
							</li>
						</ul>
						<ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">加入时间</label>
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
							<li  class="form-item-inline">
								<div class="sub-btns">
                                    <a class="layui-btn layui-btn-danger" href="javascript:submitFunction('#myForm')">确定</a>
                                    <a class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
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
							<col width="250">
							<col width="120">
							<col width="100">
							<col width="180">
							<col width="180">
							<col width="300">
						</colgroup>
						<thead>
							<tr>
								<th>名称</th>
								<th>电话号码</th>
								<th>角色</th>
								<th>类型</th>
								<th>加入时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${list}">
								<tr>
									<td class="table-member" title="${item.mName}"  onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${item.mId}','400px','470px')">
										<div class="member-cell">
										<div class="member-logo" style="background-image: url('${item.mLogo}'),url(${ctx}/image/def_user_logo.png)"></div>
										<div class="member-name ellipsis-1"><a class="blue" title="${item.mName}" href="javascript:void(0);">${item.mName}</a></div>
										</div>
									</td>
									<td>
										<div>${item.mMobile}</div>
									</td>
									<td>
										<div>${item.isAdmin == 1 || item.mId == circle.createBy ? '管理员':'会员'}</div>
									</td>
									<td>
										<div>${item.tagNames}</div>
									</td>
									<td>
										<div><fmt:formatDate value="${item.updateDate}" pattern="yyyy-MM-dd HH:mm" /></div>
									</td>
									<td  class="tb-opts">
										<div class="comm-opts">
											<a target="_self" class="" href="javascript:openDialog('分配类型','${ctx}/circle/tag/setTag.do?circle=${circle.id}&member=${item.mId}','400px','300px')">
												分配类型
											</a>
											<%--<a target="_self" class="" href="${ctx}/circle/topic/form.do?cId=${circle.id}&mId=${item.mId}">
												代发话题
											</a>--%>
											<c:if test="${(empty item.isAdmin || item.isAdmin == 0) && item.mId != circle.createBy}">
												<a target="_self" class="" href="javascript:setMgr('${item.id}')">
													设为管理员
												</a>
											</c:if>
											<c:if test="${item.isAdmin == 1 && item.mId != circle.createBy}">
												<a target="_self" class="" href="javascript:cancelMgr('${item.id}')">
													取消管理员
												</a>
											</c:if>
											<c:if test="${circle.createBy!=item.mId}">
												<a target="_self" class="" href="javascript:setCreator('${item.id}')">
													移交圈子
												</a>
											</c:if>
											<c:if test="${item.mId != circle.createBy}">
												<a target="_self" class="red" href="javascript:removeMember('${item.id}')">
													移除
												</a>
											</c:if>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<c:if test="${page.totalCount == 0}">
						<div class="f16 tc mt15">还没有会员</div>
					</c:if>
				</div>
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
    })

	function setCreator(id){
        layer.confirm('确定要移交圈主么？', {icon: 3, title:'提示'}, function(index){
            $.post('${ctx}/circle/member/setCreator.do',{id: id}, function (res) {
                if (res.success){
                    layer.msg('设置成功', {icon : 1,time: 2000}, function(index){
                        layer.close(index);
                        location.reload();
                    });
                } else {
                    layer.msg(res.description, {icon : 2,time: 2000}, function(index){
                        layer.close(index);
                        location.reload();
                    });
                }
            });
        });
	}
    function setMgr(id){
        layer.confirm('确定要设置为管理员么？', {icon: 3, title:'提示'}, function(index){
            $.post('${ctx}/circle/member/setMgr.do',{id: id}, function (res) {
                if (res.success){
                    layer.msg('设置成功', {icon : 1,time: 2000}, function(index){
                        layer.close(index);
                        location.reload();
                    });
                } else {
                    layer.msg(res.description, {icon : 2,time: 2000}, function(index){
                        layer.close(index);
                        location.reload();
                    });
                }
            });
        });
    }
    
    function cancelMgr(id){
    	layer.confirm('确定要取消管理员么？', {icon: 3, title:'提示'}, function(index){
            $.post('${ctx}/circle/member/cancelMgr.do',{id: id}, function (res) {
                if (res.success){
                    layer.msg('取消成功', {icon : 1,time: 2000}, function(index){
                        layer.close(index);
                        location.reload();
                    });
                } else {
                    layer.msg('取消失败', {icon : 2,time: 2000}, function(index){
                        layer.close(index);
                        location.reload();
                    });
                }
            });
        });
    }
    function removeMember(id){
        layer.confirm('确定移除该会员么？', {icon: 3, title:'提示'}, function(index){
            $.post('${ctx}/circle/member/del.do',{id: id}, function (res) {
                if (res.success){
                    layer.msg('移除成功', {icon : 1,time: 2000}, function(index){
                        layer.close(index);
                        location.reload();
                    });
                } else {
                    layer.msg('移除失败', {icon : 2,time: 2000}, function(index){
                        layer.close(index);
                        location.reload();
                    });
                }
            });
        });
    }
    function openDialog(title, url, width, height, target) {
        layer.open({
            type : 2,
            area : [ width, height ],
            title : title,
            maxmin : true, //开启最大化最小化按钮
            content : url,
            btn : [ '确定', '关闭' ],
            yes : function(index, layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var inputForm = body.find('#inputForm');
                var top_iframe;
                if (target) {
                    top_iframe = target;//如果指定了iframe，则在改frame中跳转
                } else {
                    top_iframe = '_parent';//获取当前active的tab的iframe
                }
                inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示

                if (iframeWin.contentWindow.doSubmit()) {
                    setTimeout(function() {
                        top.layer.close(index);
                    }, 100);//延时0.1秒，对应360 7.1版本bug

                    setTimeout(function() {
                        window.location.reload();
                    }, 200);
                }

            },
            cancel : function(index) {
            }
        });
    }

</script>
</body>
</html>