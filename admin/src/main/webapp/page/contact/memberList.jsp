<%@page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>用户管理</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/member_list.css">
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
                <div class="ovh">
                    <span class="title f20">通讯录</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/contact/memberList.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo"/>
                <div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">昵称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="realname" autocomplete="off" class="layui-input" value="${member.realname}" placeholder="请输入查询昵称">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">手机号</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="mobile" autocomplete="off" class="layui-input" value="${member.mobile}" placeholder="请输入查询手机号">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">状态</label>
                                <div class="layui-input-inline">
                                    <select name="status">
                                        <option value="">全部</option>
                                        <option value="1" ${status == "1" ? 'selected="selected"' : ''}>已认证</option>
                                        <option value="0,2,3" ${status == "0,2,3" ? 'selected="selected"' : ''}>未认证</option>
                                    </select>
                                </div>
                            </li>
                        </ul>
                        <ul class="search-form-content">
                            <li class="form-item"><label class="search-form-lable">注册时间</label>
                                <div class="check-btn-inner">
                                    <a id="all" href="javascript:void(0);" onclick="setTimeType($(this),0,'#myForm')" ${empty timeType || timeType == 0 ? 'class="active"' : ''}>全部</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),1,'#myForm')" ${timeType == 1 ? 'class="active"' : ''}>今天</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),2,'#myForm')" ${timeType == 2 ? 'class="active"' : ''}>本周内</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),3,'#myForm')" ${timeType == 3 ? 'class="active"' : ''}>本月内</a>
                                    <input type="hidden" name="timeType" value="${timeType}"/>
                                </div>
                                <div class="layui-inline">
                                    <div class="layui-input-inline">
                                        <input class="layui-input" type="text" name="startDate" value="${startDate}" placeholder="开始日" onclick="layui.laydate({elem: this})">
                                    </div>
                                    -
                                    <div class="layui-input-inline">
                                        <input class="layui-input" type="text" name="endDate" value="${endDate}" placeholder="截止日" onclick="layui.laydate({elem: this})">
                                    </div>
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
            <div class="list-content">
                <div class="cl">
                    <table class="layui-table">
                        <colgroup>
                            <col width="10%">
                            <col width="15%">
                            <col width="10%">
                            <col width="10%">
                            <col width="25%">
                            <col width="15%">
                            <col width="15%">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>昵称</th>
                            <th>公司</th>
                            <th>职位</th>
                            <th>手机号</th>
                            <th>注册时间</th>
                            <th>状态</th>
                            <th>通讯录人数</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="gmember" items="${groupMember}">
                            <tr>
                                <td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${gmember.id}','400px','470px')">
                                    <div class="member-cell">
                                        <div class="member-logo" style="background-image: url('${gmember.logo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                        <div class="member-name ellipsis-1"><a class="blue" title="${gmember.realname}" href="javascript:void(0);">${gmember.realname}</a></div>
                                        <div class="member-cell">
                                </td>
                                <td><a style="width:20%" class="ellipsis-1" title="${gmember.company}">${gmember.company}</a></td>
                                <td><a style="width:20%" class="ellipsis-1" title="${gmember.job_title}">${gmember.job_title}</a></td>
                                <td>
                                        ${gmember.mobile}
                                </td>
                                <td>
                                    <fmt:formatDate value="${gmember.create_date}" pattern="yyyy-MM-dd HH:mm"/>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${gmember.user_status == 0 || gmember.user_status == 2 || gmember.user_status == 3}">
                                            未认证
                                        </c:when>
                                        <c:when test="${gmember.user_status == 1}">
                                            已认证
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td onclick="javascript:window.location.href='${ctx}/contact/contactList.do?memberId=${gmember.id}';">
                                    <a href="javascript:void(0);" class="blue">${gmember.countNum }</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
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


    $(function () {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    });

    function openDialog(title, url, width, height, target) {
        layer.open({
            type: 2,
            area: [width, height],
            title: title,
            maxmin: true, //开启最大化最小化按钮
            content: url,
            btn: ['确定', '关闭'],
            yes: function (index, layero) {
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
                    setTimeout(function () {
                        top.layer.close(index);
                    }, 100);//延时0.1秒，对应360 7.1版本bug

                    setTimeout(function () {
                        window.location.reload();
                    }, 200);
                }

            },
            cancel: function (index) {
            }
        });
    }

</script>
</body>
</html>