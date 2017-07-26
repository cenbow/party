<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>众筹事项</title>
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
                    <a href="${ctx}/crowdfund/event/view.do" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-add btn-icon"></i>添加事项
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">众筹事项</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/crowdfund/event/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" />
            </form>
            <div class="list-content">
                <div class="cl">
                    <table class="layui-table">
                        <colgroup>
                            <col width="45%">
                            <col width="150px">
                            <col width="">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="event" items="${list}">
                            <tr>
                                <td class="ellipsis-1"  title="${event.name}">
                                        ${event.name}
                                </td>
                                <td>
                                    <fmt:formatDate value="${event.createDate}" pattern="yyyy-MM-dd HH:mm" />
                                </td>

                                <td  class="tb-opts">
                                    <div class="comm-opts">
                                        <a href="${ctx}/crowdfund/event/view.do?id=${event.id}" target="_self">
                                            编辑
                                        </a>
                                        <a href="${ctx}/crowdfund/represen/listForEvent.do?eventId=${event.id}" target="_self">
                                            数据查看
                                        </a>
                                        <a href="${ctx}/crowdfund/analyze/list.do?eventId=${event.id}" target="_self">
                                            数据分析
                                        </a>
                                        <a href="${ctx}/activity/activity/zcActivityList.do?eventId=${event.id}" target="_self">
                                            众筹项目
                                        </a>
                                        <a href="javascript:deleteEvent('${event.id}');" target="_self">
                                            删除
                                        </a>

                                        <a href="javascript:createCircle('${event.id}')"> <i class="iconfont icon-check btn-icon"></i>生成圈子</a>
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
	function createCircle(actId) {
		var content = "确定要生成吗？请选择生成<br/><label for='all1' class='mr10'><input type='radio' id='all1' name='type' value='2' checked='checked' >全部报名人员</label>"+
					  "<label for='success'><input type='radio' id='success' name='type' value='1' >已成功报名人员</label>";
// 		content = "确定要生成吗？";
  		layer.confirm(content, {
            icon: 3,
            title: '系统提示'
        }, function (index) {
        	var type = $("[name=type]:checked").val() || '2';
            layer.close(index);
          	//loading层
			var loadIndex = layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
            $.post("${ctx}/crowdfund/event/createCircle.do", {
                eventId: actId,
                type : type
            }, function (data) {
            	layer.close(loadIndex);
            	setTimeout(function() {
	            	if (data.success == true) {
	                	layer.msg('生成成功', {icon : 1});
	                } else {
	                	layer.msg('生成失败', {icon : 2});
	                }
	            });
            })
        });
  	}

    function deleteEvent(id){
        layer.confirm('确定删除事件', {
            icon : 3,
            title : '系统提示'
        }, function(index) {
            $.post("${ctx}/crowdfund/event/delete.do", {
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