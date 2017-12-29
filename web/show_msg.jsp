<%--
  Created by IntelliJ IDEA.
  User: lxq
  Date: 2017/11/9
  Time: 22:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.inc.jsp"%>
<meta http-equiv="refresh" content="2;url=${url}">
<div class="am-g am-g-fixed bbs-g-fixed">
    <div class="am-u-md-8">
        <h3>${msg}，2s后回到<a href="${url}">${linkName}</a></h3>
    </div>
<%@include file="foot.inc.jsp"%>