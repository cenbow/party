<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>角色管理</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/role_list.css">
    <%@include file="../../include/commonFile.jsp"%>
</head>
<!--头部-->
<%@include file="../../include/header.jsp"%>
<div class="index-outside">
    <%@include file="../../include/sidebar.jsp"%>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/system/privilege/view.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i
                            class="iconfont icon-add btn-icon"
                    ></i>新增权限
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">权限管理</span> <span class="f12">共<b>${page.totalCount}</b>条记录
					</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/system/privilege/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" />
                <div class="f-search-bar">
                    <div class="search-container">

                        <ul class="search-form-content">
                            <li class="form-item-inline">
                                <label class="search-form-lable">名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="name" autocomplete="off" class="layui-input" value="${sysPrivilege.name}" placeholder="请输入查询名称">
                                </div>
                            </li>
                            <li class="form-item-inline">
                                <label class="search-form-lable">编码</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="code" autocomplete="off" class="layui-input" value="${sysPrivilege.code}" placeholder="请输入查询编码">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">类型</label>
                                <div class="layui-input-inline">
                                    <select name="type">
                                        <option value="">全部</option>
                                        <c:forEach items="${types}" var="type">
                                            <option value="${type.key}" <c:if test="${sysPrivilege.type == type.key}">selected</c:if>>${type.value}</option>
                                        </c:forEach>
                                    </select>
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
            <div class="my-act-list-content">
                <ul id="view">
                    <table class="layui-table">
                        <colgroup>
                            <col width="150">
                            <col width="200">
                            <col width="150">
                            <col width="150">
                            <col width="150">
                            <col>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>编码</th>
                            <th>类型</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="privilege" items="${list}">
                            <tr>
                                <td>${privilege.name}</td>
                                <td>${privilege.permission}</td>
                                <td>
                                    <c:if test="${privilege.type == 0}">菜单权限</c:if>
                                    <c:if test="${privilege.type == 1}">其他权限</c:if>
                                </td>
                                <td><fmt:formatDate value="${privilege.createDate}" pattern="yyyy-MM-dd HH:mm" /></td>
                                <td  class="tb-opts">
                                    <div class="comm-opts">
                                        <a href="${ctx}/system/privilege/children/view.do?parentId=${privilege.id}" target="_self">
                                            新增下级权限
                                        </a>
                                        <a href="${ctx}/system/privilege/view.do?id=${privilege.id}" target="_self">
                                            编辑
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </ul>
            </div>
            <div id="page_content" class="page-container"></div>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">
    $(function () {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })


</script>
</body>
</html>