<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<head>
    <title>会员套餐</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <%@include file="../include/commonFile.jsp" %>
    <style type="text/css">
        .introduce {
            /*width: 960px;*/
            /*height: 430px;*/
            margin: 0 auto;
            /*text-align: justify;*/
        }

        .introduce > ul > li:hover {
            transform: scale(1.12, 1.12);
            -webkit-transform: scale(1.12, 1.12);
            -moz-transform: scale(1.12, 1.12);
            -ms-transform: scale(1.12, 1.12);
            -o-transform: scale(1.12, 1.12);
        }

        .introduce > ul > li {
            position: relative;
            vertical-align: top;
            width: 220px;
            /*height: 320px;*/
            margin-top: 50px;
            background-color: #fff;
            display: inline-block;
            border: 0.5px solid #efefef;
        }

        .introduce li:not(:last-child) {
            margin-right: 50px;
        }

        .introduce > ul > li .header {
            height: 140px;
            color: #fff;
            text-align: center;
            overflow: hidden;
            position: relative;
        }

        .header h3 {
            margin-top: 30px;
            font-size: 24px;
        }

        .header h4 {
            font-size: 36px;
            margin-top: 16px;
        }

        .header h4 span {
            font-size: 12px;
        }

        .introduce li p {
            /*height: 60px;*/
            padding: 20px;
            font-size: 12px;
            line-height: 20px;
            color: #76838f;
        }

        .introduce > ul > li p {
            text-indent: 2em;
            word-break: break-all;
        }

        .update-btn {
            width: 180px;
            height: 40px;
            line-height: 40px;
            font-size: 14px;
            text-align: center;
            margin: 20px auto 20px;
            color: #fff!important;
            cursor: pointer;
        }

        .update-btn {
            /*background-color: #ff9023;*/
            /*box-shadow: 0 0 8px #ff9023;*/
        }

        .styles-body .blue {
            background: #1c70ea;
            box-shadow: 0 0 8px #1c70ea;
            color: #1c70ea;
        }

        .styles-body .darkblue {
            background: #4e55ff;
            box-shadow: 0 0 8px #4e55ff;
            color: #4e55ff;
        }

        .styles-body .green {
            background: #15dcb5;
            box-shadow: 0 0 8px #15dcb5;
            color: #15dcb5;
        }

        .styles-body .orange {
            background: #FF9900;
            box-shadow: 0 0 8px #FF9900;
            color: #FF9900;
        }

        .styles-body .purple {
            background: #996699;
            box-shadow: 0 0 8px #996699;
            color: #996699;
        }

        .styles-body .pink {
            background: #FF9999;
            box-shadow: 0 0 8px #FF9999;
            color: #FF9999;
        }

        .styles-body .red {
            background: #f94a4a;
            box-shadow: 0 0 8px #f94a4a;
            color: #f94a4a;
        }

        .styles-body .gray {
            background: #999999;
            box-shadow: 0 0 8px #999999;
            color: #999999;
        }

        .styles-body .brown {
            background: #999966;
            box-shadow: 0 0 8px #999966;
            color: #999966;
        }

        .header .this-version {
            position: absolute;
            top: 16px;
            left: -40px;
            transform: rotate(-45deg);
            background-color: #fff;
            width: 139px;
            height: 30px;
            line-height: 30px;
            font-weight: 600;
        }

        .privilege-table {
            width: 1200px;
            border: 1px solid #efefef;
            margin: 20px auto 0px;
        }

        .proFuncHead, .privilege-main .proFuncContent {
            width: 100%;
            display: flex;
            border-bottom: 1px solid #efefef;
        }


        .proFuncHead .tableHead {
            flex: 1;
            padding: 15px;
            font-size: 18px;
            text-align: center;
        }


        .privilege-main > .proFuncContent:last-child  {
            border-bottom: 0px;
        }

        .privilege-main .tableContent {
            flex: 1;
            height: 60px;
            line-height: 60px;
            text-align: center;
        }

        .layui-table{
            width: 1200px;
            margin: 20px auto 0px;
        }

        .layui-table td, .layui-table th{
            text-align: center!important;
            padding: 18px 15px!important;
            font-size: 16px!important;
        }

        .layui-table tr:first-child td div{
            font-size: 18px!important;
        }
        .layui-table tr:first-child td div:first-child{
            margin-bottom: 5px!important;
        }

        .layui-table .btn-icon{
            font-size: 25px!important;
            font-weight: bold!important;
        }

        .layui-table tr:nth-child(even) {
            background-color: rgb(255, 255, 255);
        }

        .layui-table tr:nth-child(odd) {
            background-color: rgb(237, 246, 255);
        }

        .layui-table tr:hover td{
            background-color: transparent!important;
        }

    </style>
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp"%>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/system/member/memberIndex.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
                </div>
                <div class="ovh">
                    <span class="title f20">账户中心&nbsp;&gt;&nbsp;服务升级</span>
                </div>
            </div>
            <div class="introduce">
                <ul class="tc">
                    <c:forEach var="productPackage" items="${packages}">
                        <c:set var="isHas" value="false" />
                        <c:set var="thisVersion" value="false" />
                        <c:forEach var="role" items="${sysRoles}">
                            <c:if test="${productPackage.sysRoleId == role.id}">
                                <c:set var="isHas" value="true" />
                            </c:if>
                            <c:if test="${packageMember.sysRoleId == productPackage.sysRoleId}">
                                <c:set var="thisVersion" value="true" />
                            </c:if>
                        </c:forEach>
                        <li data-id="${productPackage.id}" class="styles-body">
                                <%--<div class="header" style="background: url('${level.picture}') no-repeat; background-size: 100% auto">--%>
                            <div class="header ${productPackage.style}">
                                <h3>${productPackage.name}</h3>
                                <c:if test="${productPackage.price == 0.0}">
                                    <h4 style="font-size: 32px">免费</h4>
                                </c:if>
                                <c:if test="${productPackage.price > 0.0}">
                                    <h4>${productPackage.price}<span>元/${productPackage.unit}</span></h4>
                                </c:if>
                                <c:if test="${thisVersion == true}">
                                    <div class="this-version ${productPackage.style}">当前版本</div>
                                </c:if>
                            </div>
                            <p>${productPackage.remarks}</p>
                            <c:if test="${productPackage.price > 0.0}">
                                <c:choose>
                                    <c:when test="${isHas == false}">
                                        <div class="update-btn buy-btn ${productPackage.style}">开通</div>
                                    </c:when>
                                    <c:when test="${isHas == true && thisVersion == false}">
                                        <div class="update-btn ${productPackage.style}">已开通</div>
                                    </c:when>
                                    <c:when test="${isHas == true && thisVersion == true}">
                                        <div class="update-btn buy-btn ${productPackage.style}">续期</div>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>
                <%--<div class="privilege-table">
                    <div class="proFuncHead">
                        <div class="tableHead"></div>
                        <c:forEach var="dbPackage" items="${packages}">
                            <div class="tableHead">
                                <div>${dbPackage.name}</div>
                                <div>${dbPackage.price}</div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="privilege-main">
                        <c:forEach var="privilege" items="${privileges}">
                            <div class="proFuncContent">
                                <div class="tableContent">${privilege.privilege.name}</div>
                                <c:forEach var="result" items="${privilege.results}">
                                    <div class="tableContent">${result.paramValue}</div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </div>
                </div>--%>

                <table class="layui-table">
                    <tr>
                        <td></td>
                        <c:forEach var="dbPackage" items="${packages}">
                            <td>
                                <div>${dbPackage.name}</div>
                                <c:choose>
                                    <c:when test="${dbPackage.price == 0.0}">
                                        <div>免费</div>
                                    </c:when>
                                    <c:when test="${dbPackage.price > 0.0}">
                                        <div>${dbPackage.price}/${dbPackage.unit}</span></div>
                                    </c:when>
                                </c:choose>
                            </td>
                        </c:forEach>
                    </tr>
                    <c:forEach var="privilege" items="${privileges}">
                        <tr>
                            <td>
                                <div>${privilege.privilege.name}</div>
                            </td>
                            <c:forEach var="result" items="${privilege.results}">
                                <td>
                                    <c:choose>
                                        <c:when test="${result.paramValue == null || result.paramValue == 'false'}">
                                            <i class="iconfont icon-close btn-icon red"></i>
                                        </c:when>
                                        <c:when test="${result.paramValue == 'true'}">
                                            <i class="iconfont icon-check btn-icon green"></i>
                                        </c:when>
                                        <c:when test="${result.paramValue != null}">
                                            <div>${result.paramValue}</div>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </section>
</div>
<script type="text/javascript">
    $(function () {
        $(".introduce").delegate(".buy-btn", 'click', function (e) {
            var $target = $(e.target);
            var levelId = $target.closest("li").data("id");
            layer.open({
                type: 2,
                area: ["670px", "420px"],
                title: "订单支付",
                maxmin: false,
                content: "${ctx}/charge/package/" + levelId + "/buyOrder.do",
                btn: ['关闭'],
                yes: function (index) {
                    top.layer.close(index);
                }
            });
        });
    })
</script>
</html>
