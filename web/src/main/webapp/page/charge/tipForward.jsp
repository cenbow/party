<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>服务升级</title>
</head>
<body>
    数量不够啦...请先去升级
</body>
<script type="text/javascript">
    setTimeout(function () {
        location.href="${ctx}/charge/package/packageList.do";
    }, 3000);
</script>
</html>
