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
            height: 320px;
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
            height: 60px;
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
            margin: 20px auto 0;
            color: #fff;
            cursor: pointer;
        }

        .update-btn {
            /*background-color: #ff9023;*/
            /*box-shadow: 0 0 8px #ff9023;*/
        }

        .styles-body .blue {
            background: #1c70ea
        }

        .styles-body .darkblue {
            background: #4e55ff
        }

        .styles-body .green {
            background: #15dcb5
        }

        .styles-body .orange {
            background: #F90
        }

        .styles-body .purple {
            background: #969
        }

        .styles-body .pink {
            background: #F99
        }

        .styles-body .red {
            background: #f94a4a
        }

        .styles-body .gray {
            background: #999
        }

        .styles-body .brown {
            background: #996
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
    </style>
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp"%>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="introduce">
                <ul>
                    <c:forEach var="productPackage" items="${packages}">
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
                                <c:if test="${productPackage.id == packageMember.levelId}">
                                    <div class="this-version ${productPackage.style}">当前版本</div>
                                </c:if>
                            </div>
                            <p>${productPackage.remarks}</p>
                            <c:if test="${productPackage.price > 0.0}">
                                <c:if test="${productPackage.id == packageMember.levelId}">
                                    <div class="update-btn ${productPackage.style}">续期</div>
                                </c:if>
                                <c:if test="${productPackage.id != packageMember.levelId}">
                                    <div class="update-btn ${productPackage.style}">开通</div>
                                </c:if>
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </section>
</div>
<script type="text/javascript">
    $(function () {
        $(".introduce").delegate(".update-btn", 'click', function (e) {
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
