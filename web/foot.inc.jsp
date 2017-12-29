<%--
  Created by IntelliJ IDEA.
  User: lxq
  Date: 2017/11/8
  Time: 8:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="am-u-md-4 bbs-sidebar" >
    <div class="am-panel-group">
        <c:if test="${null == sessionScope.user}">
            <section id="loginSection" class="am-panel am-panel-default" >
                <div class="am-panel-hd">登录 <a href="#" id="changeRegister" class="am-btn am-btn-link am-btn-sm">注册</a></div>
                <div class="am-panel-bd">
                    <form class="am-form" action="user?action=login" method="post">
                        <fieldset>
                            <div class="am-form-group">
                                <p><input type="text" name="username" class="am-form-field am-round" placeholder="用户名"/></p>
                            </div>
                            <div class="am-form-group">
                                <p><input type="password" name="password" class="am-form-field am-round" placeholder="密码"/></p>
                            </div>
                            <p><button type="submit" class="am-btn am-btn-secondary am-btn-block am-round">登录</button></p>
                        </fieldset>
                    </form>

                </div>
            </section>
            <section id="registerSection" class="am-panel am-panel-default" style="display:none">
                <div class="am-panel-hd">注册 <a href="#" id="changeLogin" class="am-btn am-btn-link am-btn-sm">登录</a></div>
                <div class="am-panel-bd">
                    <form class="am-form" action="user?action=register" method="post">
                        <fieldset>
                            <div class="am-form-group">
                                <p><input type="text" name="username" class="am-form-field am-round" placeholder="用户名"/></p>
                            </div>
                            <div class="am-form-group">
                                <p><input type="password" name="password" class="am-form-field am-round" placeholder="密码"/></p>
                            </div>
                            <p><button type="submit" class="am-btn am-btn-success am-btn-block am-round">注册</button></p>
                        </fieldset>
                    </form>

                </div>
            </section>
        </c:if>
        <c:if test="${null != sessionScope.user}">
            <section id="userSection" class="am-panel am-panel-default"  >
                <div class="am-panel-hd">欢迎您，${user.username}</div>
                <div class="am-panel-bd">
                    <p>我的帖子：<a href="topic?action=my">${fn:length(user.topicList)}</a>篇</p>
                    <a href="topic?action=addTopicPage" class="am-btn am-btn-primary am-btn-sm">我要发帖</a>
                    <c:if test="${fn:length(sessionScope.user.permissions) > 0}">
                        <a href="admin" class="am-btn am-btn-success am-btn-sm">后台管理</a>
                    </c:if>

                    <a href="user?action=logout" class="am-btn am-btn-danger am-btn-sm">退出登录</a>
                </div>
            </section>
        </c:if>

    </div>
</div>

</div>

<%@ include file="footer.inc.jsp" %>

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="static/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<script src="static/js/amazeui.min.js"></script>

<script type="text/javascript">
    $('#changeRegister').click(function(){
        $('#loginSection').hide();
    $('#registerSection').fadeIn();
    });
    $('#changeLogin').click(function(){
        $('#registerSection').hide();
    $('#loginSection').fadeIn();
    });
</script>

</body>
</html>
