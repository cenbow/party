<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>消息管理</title>
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
                    <a href="${ctx}/notify/wechat/view.do" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-add btn-icon"></i>添加微信消息
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">微信模板消息</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/notify/wechat/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" />
                <div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">消息事件</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="eventName" autocomplete="off" class="layui-input" value="${wechatTemplateMessageWithEvent.eventName}" placeholder="消息事件">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">模板编号</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="templateId" autocomplete="off" class="layui-input" value="${wechatTemplateMessageWithEvent.templateId}" placeholder="模板编号">
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
                            <col>
                            <col>
                            <col>
                            <col>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>消息事件</th>
                            <th>模板编号</th>
                            <th>链接</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="message" items="${list}">
                            <tr>
                                <td class="">
                                    <div class="dib ellipsis-1"  title="${message.eventName}">${message.eventName}</div>
                                </td>
                                <td>
                                        ${message.templateId}
                                </td>
                                <td>
                                        ${message.url}
                                </td>
                                <td  class="tb-opts">
                                    <div class="comm-opts">
                                        <a href="${ctx}/notify/wechat/view.do?id=${message.id}" target="_self">
                                            编辑
                                        </a>
                                        <a href="javascript:deleteMessage('${message.id}');" target="_self">
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
    function deleteMessage(id){
        layer.confirm('确定删除事件', {
            icon : 3,
            title : '系统提示'
        }, function(index) {
            $.post("${ctx}/notify/wechat/delete.do", {
                id : id
            }, function(data) {
                if (data.success == true) {
                    layer.alert("删除成功", {
                        icon : 6
                    }, function(index) {
                        window.location.reload();
                    });
                } else {
                    layer.alert("删除失败", {
                        icon : 6
                    });
                }
            });
        });
    }
</script>
</body>
</html>