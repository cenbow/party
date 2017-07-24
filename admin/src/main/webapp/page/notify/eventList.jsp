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
                    <a href="${ctx}/notify/event/view.do" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-add btn-icon"></i>添加事件
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">消息事件</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/notify/event/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" />
                <div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="name" autocomplete="off" class="layui-input" value="${event.name}" placeholder="名称模糊查询">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">代码</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="code" autocomplete="off" class="layui-input" value="${event.code}" placeholder="代码模糊查询">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">类型</label>
                                <div class="layui-input-inline">
                                    <select name="type">
                                        <option value="">全部</option>
                                        <c:forEach var="type" items="${types}">
                                            <option value="${type.key}"  <c:if test="${type.key == event.type}">selected</c:if>>${type.value}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </li>
                        </ul>
                        <ul class="search-form-content">
                            <li class="form-item">
                                <label class="search-form-lable">操作方式</label>
                                <div class="check-btn-inner">
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),'','#myForm')" ${empty event.way ? 'class="active"' : ''}>全部</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),'hand','#myForm')" ${event.way == 'hand' ? 'class="active"' : ''}>手动</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),'auto','#myForm')" ${event.way == 'auto' ? 'class="active"' : ''}>自动</a>
                                    <input type="hidden" name="way" value="${event.way}" />
                                </div>
                                <div class="cl"></div>
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
                            <col width="100">
                            <col width="80">
                            <col width="100">
                            <col width="80">
                            <col width="80">
                            <col width="250">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>事件名称</th>
                            <th>类型</th>
                            <th>代码</th>
                            <th>开关</th>
                            <th>操作方式</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="event" items="${list}">
                            <tr>
                                <td class="">
                                    <div class="dib ellipsis-1"  title="${event.name}">${event.name}</div>
                                </td>
                                <td>
                                    <c:if test="${event.type == 1}">
                                        赞
                                    </c:if>
                                    <c:if test="${event.type == 2}">
                                        评论
                                    </c:if>
                                    <c:if test="${event.type == 3}">
                                        关注
                                    </c:if>
                                    <c:if test="${event.type == 4}">
                                        系统通知
                                    </c:if>
                                    <c:if test="${event.type == 5}">
                                        活动
                                    </c:if>
                                    <c:if test="${event.type == 6}">
                                        玩法
                                    </c:if>
                                    <c:if test="${event.type == 7}">
                                        主动推送
                                    </c:if>
                                </td>
                                <td>
                                        ${event.code}
                                </td>

                                <td>
                                    <c:if test="${event.msgSwitch == 0}">
                                        关
                                    </c:if>
                                    <c:if test="${event.msgSwitch == 1}">
                                        开
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${event.way == 'auto'}">
                                        自动
                                    </c:if>
                                    <c:if test="${event.way == 'hand'}">
                                        手动
                                    </c:if>
                                </td>
                                <td  class="tb-opts">
                                    <div class="comm-opts">
                                        <a href="${ctx}/notify/event/view.do?id=${event.id}" target="_self">
                                            编辑
                                        </a>
                                        <a href="javascript:openDialog('分配通道','${ctx}/notify/event/channelList.do?eventId=${event.id}','400px','300px')" target="_self">
                                            分配通道
                                        </a>
                                        <c:if test="${event.msgSwitch == 0}">
                                            <a class="green" href="javascript:eventSwitch('${event.id}', '1')" target="_self">
                                                开启
                                            </a>
                                        </c:if>
                                        <c:if test="${event.msgSwitch == 1}">
                                            <a class="red" href="javascript:eventSwitch('${event.id}', '0')" target="_self">
                                                关闭
                                            </a>
                                        </c:if>
                                        <a href="javascript:deleteEvent('${event.id}');" target="_self">
                                            删除
                                        </a>
                                        <a href="${ctx}/notify/channel/listForEven.do?eventId=${event.id}" target="_self">
                                            通道配置
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


    function deleteEvent(id){
        layer.confirm('确定删除事件', {
            icon : 3,
            title : '系统提示'
        }, function(index) {
            $.post("${ctx}/notify/event/delete.do", {
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

    function push(id) {
        var index = layer.load(1);
        $.post("${ctx}/notify/event/push.do", {
            id : id
        }, function(data) {
            layer.close(index);
            if (data.success == true) {
                layer.alert("推送成功", {
                    icon : 6
                }, function(index) {
                    window.location.reload();
                });
            } else {
                layer.alert("推送失败", {
                    icon : 6
                });
            }
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

    function eventSwitch(id, msgSwitch) {
        var title;
        if (msgSwitch == 0){
            title = '关闭';
        }
        else if (msgSwitch == 1){
            title = '开启';
        }
        layer.confirm('确定'+ title +'事件', {
            icon : 3,
            title : '系统提示'
        }, function(index) {
            $.post("${ctx}/notify/event/eventSwitch.do", {
                id : id,
                msgSwitch: msgSwitch
            }, function(data) {
                if (data.success == true) {
                    window.location.reload();
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