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
    <c:forEach var="tag" items="${groups}">
        <div class="tag-item" data-id="${tag.id}">
            <span class="tag-name">${tag.groupName}</span>
        </div>
    </c:forEach>
    <c:if test="${groups== null || fn:length(groups) == 0}">
        <div class="tc">
            <div class="mt10 mb10">还没有小组</div>
            <div onclick="javascript:parent.window.location.href='${ctx}/competition/group/list.do?projectId=${memberInfo.projectId}';" class="layui-btn layui-btn-radius layui-btn-danger">
                <i class="iconfont icon-add btn-icon"></i>添加小组
            </div>
        </div>
    </c:if>
</div>
</body>
<script type="text/javascript">
    $(function () {
        var userTag = '${memberInfo.groupId}';
        
     	$('[data-id="' + userTag + '"]').addClass('active');
        $('.tag-wrap').delegate('.tag-item', 'click', function () {
        	if($(this).hasClass('active')){
        		$(this).removeClass('active');
        	} else {
        		$(this).addClass('active');
        	}
        	$(this).siblings('.tag-item').removeClass('active');
        })
    })
    function doSubmit() {
        var ids = '';
        $.each($('.tag-item.active'), function () {
            ids += $(this).data('id');
        });
        var flag = false;
        $.ajax({
            url: '${ctx}/competition/group/saveMemberGroup.do',
            type: "POST",
            dataType: "JSON",
            async: false,
            data: {
            	memberInfoId: '${memberInfo.id}',
            	groupId: ids
            },
            success: function (res) {
                if (res.success) {
                	top.layer.msg('分配成功', {icon: 6}, function(index){
                    	parent.submitFunction('#myForm');
                    });
                }
                flag = res.success;
            }
        });
        return flag;
    }
</script>
</html>