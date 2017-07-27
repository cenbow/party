<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>众筹分析状态</title>
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
                <div class="r">
                    <a href="${ctx}/label/label/view.do" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-add btn-icon"></i>新增分析状态
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">分析状态</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/label/label/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" />
            </form>
            <div class="list-content">
                <div class="cl">
                    <table class="layui-table">
                        <colgroup>
                            <col>
                            <col width="150px">
                            <col>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>颜色</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="label" items="${list}">
                            <tr>
                                <td class="">
                                    <div class="dib ellipsis-1"  title="${label.name}">${label.name}</div>
                                </td>
                                <td class="">
                                        ${label.style}
                                </td>
                                <td>
                                    <fmt:formatDate value="${label.createDate}" pattern="yyyy-MM-dd HH:mm" />
                                </td>
                                <td  class="tb-opts">
                                    <div class="comm-opts">
                                        <a href="${ctx}/label/label/view.do?id=${label.id}" target="_self">
                                            编辑
                                        </a>
                                        <a href="javascript:deleteLabel('${label.id}');" target="_self">
                                            删除
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
    </section>
</div>


<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">
    $(function () {
//加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })

    function deleteLabel(id){
        layer.confirm('确定删除？', {
            icon : 3,
            title : '系统提示'
        }, function(index) {
            $.post("${ctx}/label/label/delete.do", {
                id : id
            }, function(data) {
                if (data.success == true) {
                    layer.alert("删除成功", {
                        icon : 6
                    }, function(index) {
                        window.location.reload();
                    });
                } else {
                    layer.alert(data.description, {
                        icon : 6
                    });
                }
            });
        });
    }
</script>
</body>
</html>