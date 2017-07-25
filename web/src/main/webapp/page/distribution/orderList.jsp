<%@page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>活动报名管理</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/member_list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/member_act_list.css">
    <%@include file="../include/commonFile.jsp" %>
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp" %>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="my-act-list-header"><span class="title f16">
				<c:if test="${not empty targetId}">
                    <a href="${ctx}/distribution/relation/targetList/${targetId}.do">分销管理 &gt;</a>
                </c:if>
				<c:if test="${not empty hrefId }">
                    <a href="${ctx}/distribution/relation/childList/${hrefId}.do">下级分销管理 &gt;</a>
                </c:if>
				订单管理</span>
            </div>
            <div class="my-act-list-title">
                <span class="f16">订单管理</span>
            </div>
            <form class="layui-form" action="${ctx}/distribution/apply/listView.do?distributionId=${distributionId}" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo"/>
                <input type="hidden" name="targetId" id="targetId" value="${targetId}"/>
                <input type="hidden" name="hrefId" id="hrefId" value="${hrefId}"/>
            </form>

            <div class="my-act-list-content">
                <label class="cl">
                    <ul class="header">
                        <li style="width: 20%">项目名称</li>
                        <li style="width: 20%">下单人</li>
                        <li style="width: 10%">类型</li>
                        <li style="width: 10%">份数</li>
                        <li style="width: 10%">金额</li>
                        <li style="width: 10%">状态</li>
                        <li style="width: 4%"></li>
                    </ul>
                    <label id="view" class="cl content-body">
                        <c:forEach var="order" items="${list}">
                            <label class="info">
                                <ul class="content">
                                    <li style="width: 20%">
                                        <div class="dib ellipsis-1" style="width: 200px;" title="${order.title}">${order.title}</div>
                                    </li>
                                    <li style="width: 20%" class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${order.buyerId}','400px','470px')">
                                        <div class="member-cell">
                                        <div class="member-logo"
                                             style="background-image: url('${order.buyerLogo}'),url(${ctx}/image/def_user_logo.png)"
                                        ></div>
                                        <div class="member-name ellipsis-1">
                                            <a class="blue" title="${order.buyer}" href="javascript:void(0);">${order.buyer}</a>
                                        </div>
                                        </div>
                                    </li>
                                    <li style="width: 17%"><c:choose>
                                        <c:when test="${order.type == 0}">
                                            <span>标准商品</span>
                                        </c:when>
                                        <c:when test="${order.type == 1}">
                                            <span>定制商品</span>
                                        </c:when>
                                        <c:when test="${order.type == 2}">
                                            <span>活动订单</span>
                                        </c:when>
                                        <c:when test="${order.type == 3}">
                                            <span>众筹订单</span>
                                        </c:when>
                                    </c:choose></li>
                                    <li style="width: 6%">${order.unit}</li>
                                    <li style="width: 10%">
                                            ${order.payment}
                                    </li>
                                    <li style="width: 10%">
                                        <c:choose>
                                            <c:when test="${order.status == 0}">
                                                <span>审核中</span>
                                            </c:when>
                                            <c:when test="${order.status == 1}">
                                                <span>待支付</span>
                                            </c:when>
                                            <c:when test="${order.status == 2}">
                                                <span>支付成功</span>
                                            </c:when>
                                            <c:when test="${order.status == 3}">
                                                <span>其它</span>
                                            </c:when>
                                        </c:choose>
                                    </li>
                                    <li style="width: 4%; padding-left: 0" class="option"><i class="iconfont icon-unfold"></i>
                                        <i class="iconfont icon-fold"></i></li>
                                    <div class="cl"></div>
                                </ul>
                                <ul class="tr-extra-content">
                                    <c:if test="${order.linkman != ''}">
                                        <li>
                                            <label class="ext-label">联系人</label>
                                            <div class="ext-value">${order.linkman}</div>
                                        </li>
                                    </c:if>
                                    <c:if test="${order.phone != ''}">
                                        <li>
                                            <label class="ext-label">联系电话</label>
                                            <div class="ext-value">${order.phone}</div>
                                        </li>
                                    </c:if>
                                    <c:if test="${order.paymentWay != ''}">
                                        <li>
                                            <label class="ext-label">支付方式</label>
                                            <div class="ext-value">${order.paymentWay}</div>
                                        </li>
                                    </c:if>
                                    <c:if test="${fn:length(order.goodsCouponsList) > 0}">
                                        <c:forEach items="${order.goodsCouponsList}" var="item">
                                            <li>
                                                <label class="ext-label">核销码</label>
                                                <div class="ext-value">${item.verifyCode}</div>
                                            </li>
                                        </c:forEach>
                                    </c:if>
                                </ul>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div id="page_content" class="page-container"></div>
        </div>
    </section>
</div>

<!--底部-->
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">


    function check(content, memberActId, checkStatus) {
        layer.confirm(content, {icon: 3, title: '系统提示'}, function (index) {
            layer.close(index);
            $.post("${ctx}/activity/memberAct/verify.do", {id: memberActId, checkStatus: checkStatus}, function (data) {
                if (data.success == true) {
                    layer.alert(data.description, {
                        icon: 6
                    }, function (index) {
                        location.reload();
                    });
                }
            })
        });
    }

    $(function () {

        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
        $('.content-body').delegate('.option', 'click', function (e) {
            var info = $(this).closest('.info');
            if (!info.hasClass('active')) {//打开
                $('.info').removeClass('active');
                info.toggleClass('active');
            } else {
                info.toggleClass('active');
            }
        });
    })

    function openDialogShow(title, url, width, height, target) {
        layer.open({
            type: 2,
            area: [width, height],
            title: title,
            maxmin: true, //开启最大化最小化按钮
            content: url,
            btn: ['关闭'],
            yes: function (index, layero) {
                setTimeout(function () {
                    top.layer.close(index);
                }, 100);//延时0.1秒，对应360 7.1版本bug
            }
        });
    }
</script>
</body>
</html>