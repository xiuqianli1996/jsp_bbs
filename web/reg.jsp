<%--
  Created by IntelliJ IDEA.
  User: moli
  Date: 2017/11/4
  Time: 0:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String ctx = request.getContextPath();%>
<html>
<head>
    <title>注册</title>
</head>
<body>

<form action="<%=ctx%>/user?action=register" method="post">

    用户名：<input type="text" name="username">
    密码：<input type="password" name="password">
    <button>注册</button>
</form>

</body>
</html>
