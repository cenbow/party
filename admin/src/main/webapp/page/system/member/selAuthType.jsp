<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../include/tag.jsp" %>
<html>
<head>
    <title>分配类型</title>
    <link rel="stylesheet" href="${ctxStatic}/layui-v1.0.7/css/layui.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/setAuthType.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/common.css">
    <script type="text/javascript" charset="utf-8" src='${ctxStatic}/jquery/jquery-2.1.4.min.js'></script>

</head>
<body>
<div class="type-wrap ">
    <c:forEach var="auth" items="${authList}">
        <div class="type-item" data-id="${auth.id}">
            <c:choose>
                <c:when test="${auth.type == 1}">
                    <img src="${ctx}/image/qq-logo.png" alt="">
                </c:when>
                <c:otherwise>
                    <img src="${ctx}/image/wechat-logo.png" alt="">
                </c:otherwise>
            </c:choose>
            <div class="info-inner">
                <p>
                <c:choose>
                    <c:when test="${auth.type == 0}">
                        APP微信授权
                    </c:when>
                    <c:when test="${auth.type == 4}">
                        小程序授权
                    </c:when>
                    <c:when test="${auth.type == 2}">
                        微博授权
                    </c:when>
                    <c:when test="${auth.type == 1}">
                        QQ授权
                    </c:when>
                    <c:when test="${auth.type == 3}">
                        微信公众号授权
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>
                </p>
                <p>${auth.official_account_name == null || auth.official_account_name == ''?'CEO户外':auth.official_account_name}</p>
            </div>
        </div>
    </c:forEach>
</div>
</body>
<script type="text/javascript">
    $(function () {
        $('.type-wrap').delegate('.type-item', 'click', function () {
            $(this).toggleClass('active');
        })
    })
    function doSubmit() {
        var ids = '';
        $.each($('.type-item.active'), function () {
            ids += $(this).data('id') + ',';
        });
        if(!ids){
            parent.layer.msg('请至少选择一个', {icon: 5});
            return;
        }
        var flag = false;
        $.ajax({
            url: '${ctx}/system/member/removeAuth.do',
            type: "POST",
            dataType: "JSON",
            async: false,
            data: {
                ids: ids
            },
            success: function (res) {
                if (res.success) {
                    top.layer.msg('解绑成功', {
                        icon: 6
                    });
                }
                flag = res.success;
            }
        });
        return flag;
    }
</script>
</html>