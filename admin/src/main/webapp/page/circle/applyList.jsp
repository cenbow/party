<%@page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>会员审核</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">

    <%@include file="../include/commonFile.jsp" %>
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
                    <a href="javascript:history.back();" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-refresh btn-icon"></i> 返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f18"><a href="${ctx}/circle/list.do" class="">${circle.name}</a> > 会员审核</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/circle/apply/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo"/>
                <input type="hidden" name="circle" id="circle" value="${apply.circle }"/>
                <div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="realname" autocomplete="off" class="layui-input" value="${member.realname}" placeholder="请输入">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">电话号码</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="mobile" autocomplete="off" class="layui-input" value="${member.mobile}" placeholder="请输入">
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
                            <li class="form-item">
                                <label class="search-form-lable">审核状态</label>
                                <div class="check-btn-inner" id="check_status_tab">
                                    <a href="javascript:void(0);" data-status="0" ${ apply.checkStatus == 0 ? 'class="active"' : ''}>待审核</a>
                                    <a href="javascript:void(0);" data-status="1" onclick="setStatus(1)" ${apply.checkStatus == 1 ? 'class="active"' : ''}>已拒绝</a>
                                    <input type="hidden" name="checkStatus" value="${apply.checkStatus}"/>
                                </div>
                                <div class="cl"></div>
                            </li>
                        </ul>
                        <ul class="search-form-content">
							<li class="form-item"><label class="search-form-lable">申请时间</label>
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
                            <col width="150">
                            <col width="170">
                            <col width="280">
                            <col width="180">
                            <col width="200">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>电话号码</th>
                            <th>申请理由</th>
                            <th>申请时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach var="item" items="${list}">
                            <tr>
                                <td class="table-member" title="${item.mName}" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${item.mId}','400px','470px')">
                                    <div class="member-cell">
                                    <div class="member-logo" style="background-image: url('${item.mLogo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                    <div class="member-name ellipsis-1"><a class="blue" title="${item.mName}" href="javascript:void(0);">${item.mName}</a></div>
                                    </div>
                                </td>
                                <td>
                                    <div>${item.mMobile}</div>
                                </td>
                                <td>
                                    <div>${item.remarks}</div>
                                </td>
                                <td><div><fmt:formatDate value="${item.update_date}" pattern="yyyy-MM-dd HH:mm"/></div></td>
                                <td class="tb-opts">
                                    <div class="comm-opts">
                                        <a target="_self"  class="green" href="javascript:passApply('${item.id}')">
                                            通过
                                        </a>
                                        <c:if test="${item.check_status == 0}">
                                            <a target="_self" class="red" href="javascript:rejectApply('${item.id}')">
                                                拒绝
                                            </a>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${page.totalCount == 0}">
                        <div class="f16 tc mt15">还没有申请</div>
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


	
    $(function () {
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
        $('#check_status_tab a').click(function () {
            if(!$(this).hasClass('active')){
                $('input[name=checkStatus]').val($(this).data('status'));
                $('#myForm').submit();
            }
        })
    });

    function passApply(id) {
        layer.confirm('确定通过么？', {icon: 3, title: '提示'}, function (index) {
            $.post('${ctx}/circle/apply/pass.do', {id: id}, function (res) {
                if (res.success) {
                    layer.msg('审核成功', {icon: 1}, function (index) {
                        layer.close(index);
                        location.reload();
                    });
                } else {
                    layer.msg('审核失败', {icon: 2}, function (index) {
                        layer.close(index);
                        location.reload();
                    });
                }
            });
        });
    }
    function rejectApply(id) {
        layer.confirm('确定拒绝么？', {icon: 3, title: '提示'}, function (index) {
            $.post('${ctx}/circle/apply/reject.do', {id: id}, function (res) {
                if (res.success) {
                    layer.msg('设置成功', {icon: 1}, function (index) {
                        layer.close(index);
                        location.reload();
                    });
                } else {
                    layer.msg('设置失败', {icon: 2}, function (index) {
                        layer.close(index);
                        location.reload();
                    });
                }
            });
        });
    }


</script>
</body>
</html>