<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>帮助教程</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <%@include file="../include/commonFile.jsp" %>
    <style type="text/css">
        .help-item {
            margin-bottom: 30px;
        }

        .help-parent {
            font-size: 16px;
            margin-bottom: 10px;
        }

        ul.help-children {
            text-align: justify;
        }

        ul.help-children li {
            list-style: none;
            display: inline-block;
            width: 33%;
            height: 35px;
            line-height: 35px;
            border-bottom: 1px dashed #ddd;
            vertical-align: middle;
        }
    </style>
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="my-act-list-content">
                <div class="help-content">
                    <c:forEach var="help" items="${helpList}">
                        <div class="help-item">
                            <div class="help-parent">
                                <a href="${ctx}/help/help/${help.id}/getDetail.do"><fmt:formatNumber value="${help.serialNumber}" pattern="#.#"/>、${help.title}</a>
                            </div>
                            <ul class="help-children">
                                <c:forEach var="cHelp" items="${help.childrens}">
                                    <li><a href="${ctx}/help/help/${cHelp.id}/getDetail.do"><span class="gray"><fmt:formatNumber value="${cHelp.serialNumber}" pattern="#.#"/></span>&emsp;${cHelp.title}</a></li>
                                </c:forEach>
                                <c:if test="${fn:length(help.childrens) % 3 != 0}">
                                    <c:forEach var="cc" begin="1" end="${3 - (fn:length(help.childrens) % 3)}">
                                        <li></li>
                                    </c:forEach>
                                </c:if>
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">

</script>
</body>
</html>