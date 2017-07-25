<%@page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>我创建的圈子</title>
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
                    <a href="${ctx}/circle/form.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-add btn-icon"></i> 创建圈子 </a>
                </div>
                <div class="ovh">
                    <span class="title f18"><a href="${ctx}/circle/list.do" class="">我创建的圈子</a></span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/circle/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo"/>
                <input type="hidden" name="memberId" id="memberId" value="${contact.memberId }"/>
                <div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="name" autocomplete="off" class="layui-input" value="${circle.name}" placeholder="请输入">
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
							<li class="form-item"><label class="search-form-lable">创建时间</label>
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
                            <col width="10%">
                            <col width="10%">
                            <col width="25%">
                            <col width="15%">
                            <col width="15%">
                            <col width="25%">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>圈主</th>
                            <th>会员数</th>
                            <th>话题数</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="circle" items="${list}">
                            <tr>
                                <td class="table-member" title="${circle.name}">
                                    <div class="member-cell" style="max-width: 500px">
                                    <div class="member-logo" style="background-image: url('${circle.logo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                    <div class="member-name ellipsis-1">${circle.name}</div>
                                    </div>
                                </td>
                                <td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${circle.mgrId}','400px','470px')">
                                    <div class="member-cell">
                                    <div class="member-logo" style="background-image: url('${circle.mgrLogo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                    <div class="member-name ellipsis-1"><a class="blue" title="${circle.mgrRealname}" href="javascript:void(0);">${circle.mgrRealname}</a></div>
                                    </div>
                                </td>
                                <td>
                                    <div>${circle.member_num == null ? 0 : circle.member_num}</div>
                                </td>
                                <td>
                                    <div>${circle.topic_num == null ? 0 : circle.topic_num}</div>
                                </td>
                                <td><div><fmt:formatDate value="${circle.update_date}" pattern="yyyy-MM-dd HH:mm"/></div></td>
                                    <%--<td>--%>
                                    <%--<div class="ellipsis-1">${circle.memo}</div>--%>
                                    <%--</td>--%>
                                <td class="tb-opts">
                                    <div class="comm-opts">
                                        <a class="qr-btn" target="_self" class="" href="javascript:openQr('${circle.qrCodeUrl}');">
                                            二维码
                                        </a>
                                        <a target="_self" class="" href="${ctx}/circle/member/list.do?id=${circle.id}">
                                            会员管理
                                        </a>
                                        <a target="_self" class="" href="${ctx}/circle/apply/list.do?circle=${circle.id}">
                                            会员审核
                                        </a>
                                        <a target="_self" class="" href="${ctx}/circle/topic/list.do?circle=${circle.id}">
                                            话题管理
                                        </a>
                                        <a target="_self" class="" href="${ctx}/circle/tag/list.do?circle=${circle.id}">
                                            用户类型管理
                                        </a>
                                        <a target="_self" class="" href="${ctx}/circle/topicTag/list.do?circle=${circle.id}">
                                            话题类型管理
                                        </a>
                                        <a target="_self" class="green" href="${ctx}/circle/form.do?id=${circle.id}">
                                            编辑
                                        </a>
                                        <a target="_self" class="red" href="javascript:delCircle('${circle.id}')">
                                            删除
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${page.totalCount == 0}">
						<div class="f16 tc mt15">还没有圈子</div>
					</c:if>
                </div>
            </div>
            <div id="page_content" class="page-container"></div>
        </div>
    </section>
    <div class="f-def-dialog" id="circleQr" data-url="">
        <div class="f-dialog-shadow"></div>
        <div class="f-dialog-content">
            <span class="close-icon"><i class="iconfont icon-close"></i></span>
            <div class="dialog-detail">
                <p class="f16 gray">扫码二维码可预览分享</p>
                <img class="download-img" src="" />
                <div>
                    <a class="download-qrcode" href="javascript:void(0);" data-url="">下载二维码</a>
                </div>
            </div>
        </div>
    </div>
</div>
<!--底部-->
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">
    function delCircle(id) {
        layer.confirm('确定删除该圈子么？', {icon: 3, title: '提示'}, function (index) {
            $.post('${ctx}/circle/del.do', {id: id}, function (res) {
                if (res.success) {
                    layer.msg('删除成功', {icon: 1, time: 1000}, function (index) {
                        layer.close(index);
                        location.reload();
                    });
                } else {
                    layer.msg('删除失败', {icon: 2, time: 1000}, function (index) {
                        layer.close(index);
                        location.reload();
                    });
                }
            });
        })
    }
    function openQr(url) {
        $('#circleQr').data('url',url);
        $('#circleQr .download-img').prop('src','${qr_code}' + '/' + url);
        $('#circleQr .download-qrcode').attr('data-url',url);
        $('#circleQr').fadeIn();
        $('#circleQr .f-dialog-shadow').click(function () {
            $('#circleQr').fadeOut();
        })
    }
    
    showActive('${input.createStart}', '${input.createEnd}', '#timeType');

    
    $(function () {
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
        $('.f-def-dialog .download-qrcode').click(function (e) {
            var url = $(this).attr("data-url")
            download('${ctx}',url);
        });
    });

</script>
</body>
</html>