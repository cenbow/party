<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<html>
<head>
    <title>分配类型</title>
    <link rel="stylesheet" href="${ctxStatic}/layui-v1.0.7/css/layui.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/circle/set_tag.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/common.css">
    <script type="text/javascript" charset="utf-8" src='${ctxStatic}/jquery/jquery-2.1.4.min.js'></script>

</head>
<body>

<div class="tag-wrap">
    <c:forEach var="tag" items="${tagList}">
        <div class="tag-item" data-id="${tag.id}">
            <span class="tag-name">${tag.tagName}</span>
        </div>
    </c:forEach>
    <c:if test="${tagList== null || fn:length(tagList) == 0}">
        <div class="tc">
            <div class="mt10 mb10">还没有类型</div>
            <div onclick="javascript:parent.window.location.href='${ctx}/circle/tag/list.do?circle=${cmTag.circle}';" class="layui-btn layui-btn-radius layui-btn-danger">
                <i class="iconfont icon-add btn-icon"></i>添加类型
            </div>
        </div>
    </c:if>
</div>
</body>
<script type="text/javascript">
    $(function () {
        var userTag = '${cmTagIds}';
        userTag = userTag.split(',');
        userTag.forEach(function (item) {
            $('[data-id="' + item + '"]').addClass('active');
        })
        $('.tag-wrap').delegate('.tag-item', 'click', function () {
            $(this).toggleClass('active');
        })
    })
    function doSubmit() {
        var ids = '';
        $.each($('.tag-item.active'), function () {
            ids += $(this).data('id') + ',';
        });
        var flag = false;
        $.ajax({
            url: '${ctx}/circle/tag/saveMemberTag.do',
            type: "POST",
            dataType: "JSON",
            async: false,
            data: {
                circle: '${cmTag.circle}',
                member: '${cmTag.member}',
                ids: ids
            },
            success: function (res) {
                if (res.success) {
                    top.layer.msg('分配成功', {
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