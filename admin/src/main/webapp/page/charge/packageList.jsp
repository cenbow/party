<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>套餐管理</title>
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
                    <a href="${ctx}/charge/package/0/toForm.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-add btn-icon"></i>
                        创建套餐
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">套餐管理</span> <span class="f12">共<b id="totalCount">${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/charge/package/packageList.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" value="${page.page}"/>
                <div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">套餐名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="name" autocomplete="off" class="layui-input" value="${level.name}" placeholder="请输入查询套餐名称">
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
                                    <input type="hidden" name="timeType" value="${input.timeType}"/>
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
                            <col>
                            <col>
                            <col>
                            <col>
                            <col>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>金额/单位</th>
                            <th>角色</th>
                            <th>等级</th>
                            <th>风格</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="productPackage" items="${packages}">
                            <tr>
                                <td>
                                    <div>${productPackage.name}</div>
                                </td>
                                <td>
                                    <div>
                                        <c:if test="${productPackage.price == 0.0}">
                                            免费
                                        </c:if>
                                        <c:if test="${productPackage.price > 0.0}">
                                            ${productPackage.price}元/${productPackage.unit}
                                        </c:if>
                                    </div>
                                </td>
                                <td>
                                    <div>${productPackage.sysRoleName}</div>
                                </td>
                                <td>
                                    <div>${productPackage.level}</div>
                                </td>
                                <td>
                                    <div>${productPackage.style}</div>
                                </td>
                                <td>
                                    <div><fmt:formatDate value="${productPackage.updateDate}" pattern="yyyy-MM-dd HH:mm"/></div>
                                </td>
                                <td class="opts-btns tb-opts">
                                    <div class="comm-opts">
                                        <a class="green" href="${ctx}/charge/package/${productPackage.id}/toForm.do">编辑</a>
                                        <a class="red" href="javascript:deleteObj('确定要删除吗', '${ctx}/charge/package/${productPackage.id}/delete.do');">删除</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${page.totalCount == 0}">
                        <div class="f16 tc mt15">还没有套餐</div>
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
    $(function () {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    });
    showActive('${input.createStart}', '${input.createEnd}', '#timeType');
</script>
</body>
</html>