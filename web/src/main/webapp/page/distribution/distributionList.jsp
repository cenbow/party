<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>我的分销</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/distribution/distribution.css">
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
					<a href="${ctx}/activity/activity/activityList.do" class="layui-btn layui-btn-radius layui-btn-danger">
						<i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
                <div class="ovh">
                    <span class="title f20">我发布的活动 &gt; ${activity.title}</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/distribution/relation/targetList/${targetId}.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" />
                <input type="hidden" name="targetId" id="targetId" value="${targetId}"/>
                <div class="f-search-bar">
                    <div class="search-container">
                        <%-- <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">发起人</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="title" autocomplete="off" class="layui-input" value="${input.title}"
                                           placeholder="标题">
                                </div>
                            </li>

                        </ul>
                        <ul class="search-form-content">
                            <li class="form-item"><label class="search-form-lable">项目类型</label>
                                <div class="check-btn-inner">
                                    <a id="all" href="javascript:void(0);" onclick="setTimeType($(this),'','#myForm')"${empty input.type ? 'class="active"' : ''}>全部</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),0,'#myForm')" ${input.type == 0 ? 'class="active"' : ''}>商品</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),1,'#myForm')" ${input.type == 1 ? 'class="active"' : ''}>活动</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),2,'#myForm')" ${input.type == 2 ? 'class="active"' : ''}>文章</a>
                                    <input type="hidden" name="type" value="${input.type}" />
                                </div>
                            </li>
                        </ul> --%>
                        <ul class="search-form-content">
                            <li class="form-item"><label class="search-form-lable">注册时间</label>
                                <div class="check-btn-inner" id="timeType">
                                    <a id="all" href="javascript:void(0);" onclick="setTimeType($(this),0,'#myForm')" ${empty input.timeType || input.timeType == 0 ? 'class="active"' : ''}>全部</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),1,'#myForm')" ${input.timeType == 1 ? 'class="active"' : ''}>今天</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),2,'#myForm')" ${input.timeType == 2 ? 'class="active"' : ''}>本周内</a>
									<a href="javascript:void(0);" onclick="setTimeType($(this),3,'#myForm')" ${input.timeType == 3 ? 'class="active"' : ''}>本月内</a>
                                    <input type="hidden" name="timeType" value="${input.timeType}" />
                                </div>
                                <div class="layui-inline">
                                    <div class="layui-input-inline">
                                        <input class="layui-input" type="text" name="startDate" value="${input.startDate}" placeholder="开始日" onclick="layui.laydate({elem: this})">
                                    </div>
                                    -
                                    <div class="layui-input-inline">
                                        <input class="layui-input" type="text" name="endDate" value="${input.endDate}" placeholder="截止日" onclick="layui.laydate({elem: this})">
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
            <form class="layui-form" action="${ctx}/distribution/relation/targetListExport.do" id="exportForm" method="post">
                <input type="hidden" name="targetId" value="${targetId}"/>
            </form>
            <div class="list-content">
                <ul class="num">
                    <div class="r">
                        <li style="cursor: pointer;" class="r">
                            <c:if test="${not empty targetId}">
                                <c:if test="${empty zipUrl}">
                                    <a class="layui-btn layui-btn-danger layui-btn-small" id="btnExport">导出EXCEL</a>
                                </c:if>
                                <c:if test="${not empty zipUrl}">
                                    <a class="layui-btn layui-btn-danger layui-btn-small" href="/upload/${zipUrl}">点击下载</a>
                                </c:if>
                            </c:if>
                        </li>
                    </div>
                    <p class="cl"></p>
                </ul>
                <div class="cl">
                    <table class="layui-table">
                        <colgroup>
                            <col width="10%">
                            <col width="10%">
                            <col width="10%">
                            <col width="10%">
                            <col width="10%">
                            <col width="10%">
                            <col width="10%">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>分销商</th>
                            <th>项目名称</th>
                            <th>类型</th>
                            <th>浏览量</th>
                            <th>分享量</th>
                            <th>报名数</th>
                            <th>下单量</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="distribution" items="${list}">
                            <tr>
                                <td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${distribution.distributorId}','400px','470px')">
                                    <div class="member-cell">
                                    <div class="member-logo" style="background-image: url('${distribution.distributorLogo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                    <div class="member-name ellipsis-1"><a class="blue" title="${distribution.distributorName}" href="javascript:void(0);" >${distribution.distributorName}</a></div>
                                    </div>
                                </td>
                                <td>
                                    <div class="dib ellipsis-1" style="width: 200px;" title="${distribution.title}">${distribution.title}</div>
                                </td>
                                <td>
                                    <c:if test="${distribution.type == 0 || distribution.type == 1}">
                                        商品
                                    </c:if>
                                    <c:if test="${distribution.type == 2}">
                                        活动
                                    </c:if>
                                    <c:if test="${distribution.type == 3}">
                                        文章
                                    </c:if>
                                </td>
                                <td>
                                        ${distribution.viewNum}
                                </td>
                                <td>
                                        ${distribution.shareNum}
                                </td>
                                <td>
                                    <a class="blue applyNum count-num" data-id= "${distribution.id}" data-targetid="${distribution.targetId}">${distribution.applyNum}</a>
                                </td>
                                <td>
                                    <a class="blue salesNum count-num" data-id= "${distribution.id}" data-targetid="${distribution.targetId}">${distribution.salesNum}</a>
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
<script type="text/javascript">

    showActive('${input.startDate}', '${input.endDate}', '#timeType');



    $(function() {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
        $('.applyNum').click(function () {
            var id = this.dataset.id;
            var targetId = this.dataset.targetid;
            window.location.href = '${ctx}/distribution/apply/listView.do?distributionId='+ id +"&targetId=" + targetId;
        });

        $('.salesNum').click(function () {
            var id = this.dataset.id;
            var targetId = this.dataset.targetid;
            window.location.href = '${ctx}/distribution/order/listView.do?distributionId='+ id +"&targetId=" + targetId;
        });

        $("#btnExport").click(function () {
            layer.confirm('确认要导出Excel吗?', {
                icon : 3,
                title : '系统提示'
            }, function(index) {
                var index = layer.load(1);
                $.post("${ctx}/distribution/relation/targetListExport.do", $("#exportForm").serialize(), function(data) {
                    if (data.success == true) {
                        layer.alert("操作成功,请稍后刷新下载", {
                            icon : 6
                        });
                    } else {
                        layer.alert("操作失败", {
                            icon : 6
                        });
                    }
                    layer.close(index);
                    top.layer.close(index);
                });
            });
        });
    })
</script>
</body>
</html>