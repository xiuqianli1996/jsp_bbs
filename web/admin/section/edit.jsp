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
        <form class="am-form" method="post" action="admin/section?action=save">
            <input type="hidden" class="am-form-field" name="id" value="${bean.id}">
            <div class="am-form-group">
                <label class="am-form-label">版块名</label>
                <input type="text" class="am-form-field" name="name" value="${bean.name}">
            </div>
            <div class="am-form-group">
                <label class="am-form-label">版主</label>
                <select data-am-selected="{searchBox: 1}" name="masterID">
                    <option value="">请选择</option>
                    <c:forEach items="${users}" var="user">
                        <option value="${user.id}" ${(bean.masterID != null && bean.masterID == user.id) ? "selected" : ""}>${user.username}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="am-form-group">
                <label class="am-form-label">创建时间</label>
                <input type="text" class="am-form-field" placeholder="请选择" name="createTime"
                       value="<fmt:formatDate value='${bean.createTime}' pattern='yyyy-MM-dd'/>" data-am-datepicker readonly required/>
            </div>
            <button class="am-btn am-btn-success am-btn-block am-radius">提交</button>
        </form>

    </div>

</div>

<%@ include file="/admin/foot.inc.jsp" %>
