<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<html>
<head>
    <title>分配角色</title>
    <link rel="stylesheet" href="${ctxStatic}/layui-v1.0.7/css/layui.css">
    <script type="text/javascript" charset="utf-8" src='${ctxStatic}/jquery/jquery-2.1.4.min.js'></script>
    <style type="text/css">
        div {
            padding: 10px;
        }

        li {
            border: 1px solid #ececec;
            float: left;
            padding-left: 20px;
            padding-right: 20px;
            margin-right: 10px;
            margin-bottom: 10px;
            height: 35px;
            line-height: 35px;
            height: 35px;
        }

        li:hover {
            cursor: pointer;
        }

        li.checked {
            background-color: #cfb31b;
            color: white;
        }
    </style>
</head>
<body>
<div>
    <ul class="tag-wrap">
        <c:forEach var="role" items="${sysRoles}">
            <li class="tag-item" data-id="${role.id}">${role.name}</li>
        </c:forEach>
    </ul>
</div>
</body>
<script type="text/javascript">

    $(function () {
        var userTag = '${sysRoleId}';

        $('[data-id="' + userTag + '"]').addClass('checked');
        $('.tag-wrap').delegate('.tag-item', 'click', function () {
            if($(this).hasClass('checked')){
                $(this).removeClass('checked');
            } else {
                $(this).addClass('checked');
            }
            $(this).siblings('.tag-item').removeClass('checked');
        })
    });

    function doSubmit() {
        if ($("li.checked").length == 0) {
            top.layer.msg("请选择一个角色");
            return false;
        }
        var $check = $("li.checked");
        var result = {
            "id": $check.attr("data-id"),
            "name": $check.text()
        };
        return result;
    }
</script>
</html>