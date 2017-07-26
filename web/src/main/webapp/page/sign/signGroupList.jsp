<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>小组管理</title>
    <%@include file="../include/commonFile.jsp"%>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">

</head>
<%@include file="../include/header.jsp"%>
<body>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp"%>
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/sign/project/list.do" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">打卡管理&nbsp;&gt;&nbsp;小组管理</span>
                </div>
            </div>
            <div class="layui-tab-item  layui-show">
                <form class="layui-form" action="${ctx}/sign/group/list.do" id="myForm" method="post">
                    <input type="hidden" name="pageNo" id="pageNo" />
                    <input type="hidden" name="projectId" value="${signGroupInput.projectId}" />
                    <div class="f-search-bar">
                        <div class="search-container">
                            <ul class="search-form-content">
                                <li class="form-item-inline"><label class="search-form-lable">队伍名称</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="name" autocomplete="off" class="layui-input" value="${signGroupInput.name}"
                                               placeholder="队伍名称"
                                        >
                                    </div>
                                </li>
                            </ul>
                            <ul class="search-form-content">
                                <li class="form-item"><label class="search-form-lable">报名时间</label>
                                    <div class="check-btn-inner">
                                        <a id="all" href="javascript:void(0);" onclick="setTimeType($(this),0,'#myForm')" ${empty signGroupInput.timeType || signGroupInput.timeType == 0 ? 'class="active"' : ''}>全部</a>
                                        <a href="javascript:void(0);" onclick="setTimeType($(this),1,'#myForm')" ${signGroupInput.timeType == 1 ? 'class="active"' : ''}>今天</a>
                                        <a href="javascript:void(0);" onclick="setTimeType($(this),2,'#myForm')" ${signGroupInput.timeType == 2 ? 'class="active"' : ''}>本周内</a>
                                        <a href="javascript:void(0);" onclick="setTimeType($(this),3,'#myForm')" ${signGroupInput.timeType == 3 ? 'class="active"' : ''}>本月内</a>
                                        <input type="hidden" name="timeType" value="${signGroupInput.timeType}" />
                                    </div>
                                    <div class="layui-inline">
                                        <div class="layui-input-inline">
                                            <input class="layui-input" type="text" name="startDate" value="${signGroupInput.startDate}" placeholder="开始日"
                                                   onclick="layui.laydate({elem: this})"
                                            >
                                        </div>
                                        -
                                        <div class="layui-input-inline">
                                            <input class="layui-input" type="text" name="endDate" value="${signGroupInput.endDate}" placeholder="截止日"
                                                   onclick="layui.laydate({elem: this})"
                                            >
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

                <div class="my-act-list-content">
                    <ul class="num">
                        <div class="r">
                            <li style="cursor: pointer;" class="r">
                                <a href="${ctx}/sign/group/view.do?projectId=${signGroupInput.projectId}" class="layui-btn layui-btn-danger layui-btn-small"><i
                                        class="iconfont icon-add btn-icon"></i>创建队伍</a>
                            </li>
                        </div>
                        <p class="cl"></p>
                    </ul>
                    <div class="cl">
                        <table class="layui-table">
                            <colgroup>
                                <col>
                                <col>
                                <col>
                                <col width="150px">
                                <col>
                            </colgroup>
                            <thead>
                            <tr>
                                <th>队伍名称</th>
                                <th>正式成员</th>
                                <th>待审核成员</th>
                                <th>创建者</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="group" items="${list}">
                                <tr>
                                    <td>
                                            ${group.name}
                                    </td>
                                    <td>
                                            ${group.passNum}
                                    </td>
                                    <td>
                                            ${group.notAuditNum}
                                    </td>
                                    <td>
                                            ${group.authorName}
                                    </td>
                                    <td>
                                           <fmt:formatDate value="${group.createDate}" pattern="yyyy-MM-dd HH:mm" />
                                    </td>
                                    <td  class="tb-opts">
                                        <div class="comm-opts">
                                            <a href="${ctx}/sign/group/view.do?id=${group.id}" target="_self">
                                                编辑
                                            </a>
                                            <a href="javascript:deleteObj('确定要删除吗？','${ctx}/sign/group/delete.do?id=${group.id}','该队伍已有人报名，不能删除');" target="_self">
                                                删除
                                            </a>
                                            <a href="${ctx}/sign/member/group/list.do?groupId=${group.id}">
                                                人员审核
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div id="page_content" class="page-container"></div>
            </div>
        </div>
    </section>
</div>
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">
    $(function () {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })

</script>
</body>
</html>
