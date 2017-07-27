<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h5>正在跳转...</h5>
<form action="${authorizeUrl}"></form>
</body>
<script type="text/javascript">
    $(function () {
        $(form).submit();
    })
</script>
</html>
