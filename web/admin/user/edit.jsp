<%--
  Created by IntelliJ IDEA.
  User: lxq
  Date: 2017/11/18
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/admin/header.inc.jsp" %>

<div class="am-g am-g-fixed bbs-g-fixed">
    <div class="am-u-md-10">
        <h3>编辑</h3>
        <hr>
        <form class="am-form" method="post" action="admin/user?action=save">
            <input type="hidden" class="am-form-field" name="id" value="${bean.id}">
            <div class="am-form-group">
                <label class="am-form-label">用户名</label>
                <input type="text" class="am-form-field" name="username" value="${bean.username}">
            </div>
            <div class="am-form-group">
                <label class="am-form-label">密码</label>
                <input type="text" class="am-form-field" name="password" value="${bean.password}">
            </div>

            <div class="am-form-group">
                <label class="am-form-label">注册时间</label>
                <input type="text" class="am-form-field" placeholder="请选择" name="regTime"
                       value="<fmt:formatDate value='${bean.regTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" data-am-datepicker readonly required/>
            </div>
            <div class="am-form-group">
                <label class="am-form-label">角色</label>

                <c:forEach items="${roles}" var="role">
                    <c:set var="isContains" value="false"/>
                    <c:forEach items="${bean.roles}" var="userRole">
                        <c:if test="${role.id eq userRole.id}">
                            <c:set var="isContains" value="true"/>
                        </c:if>
                    </c:forEach>
                    <label class="am-checkbox-inline">
                        <input type="checkbox" name="roleID" value="${role.id}" data-am-ucheck
                        <c:if test="${isContains}">checked</c:if>> ${role.name}
                    </label>
                </c:forEach>
            </div>
            <button class="am-btn am-btn-success am-btn-block am-radius">提交</button>
        </form>

    </div>

</div>

<%@ include file="/admin/foot.inc.jsp" %>
