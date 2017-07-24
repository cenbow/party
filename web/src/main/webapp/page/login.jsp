<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/page/include/tag.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <div>登陆页面</div>
    <form id="my-from">
        <input name="loginName">
        <input name="password">
        <button type="button" id="login">登陆</button>

    </form>
</body>
<!-- cdn -->
<script type="text/javascript" charset="utf-8" src="//apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
	if (!window.jQuery) {
		var s = document.createElement('script');
		s.src = '${ctxStatic}/jquery/jquery-2.1.4.min.js';
		document.body.appendChild(s);
	}
</script>
<script>
    $('#login').on('click', function () {
       $.get('${ctx}/user/login/login.do',$('#my-from').serialize(), function (res) {
         if (res.success){
             location.href = "${ctx}/crowdfund/project/list.do";
         }
         else {
             alert(res.description);
         }
       });
    });
</script>
</html>
