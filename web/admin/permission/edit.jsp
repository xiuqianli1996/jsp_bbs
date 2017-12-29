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
        <form class="am-form" method="post" action="admin/permission?action=save">
            <input type="hidden" class="am-form-field" name="id" value="${bean.id}">
            <div class="am-form-group">
                <label class="am-form-label">权限名</label>
                <input type="text" class="am-form-field" name="name" value="${bean.name}">
            </div>
            <div class="am-form-group">
                <label class="am-form-label">编码</label>
                <input type="text" class="am-form-field" name="code" value="${bean.code}">
            </div>
            <div class="am-form-group">
                <label class="am-form-label">URL</label>
                <input type="text" class="am-form-field" name="url" value="${bean.url}">
            </div>
            <button class="am-btn am-btn-success am-btn-block am-radius">提交</button>
        </form>

    </div>

</div>

<%@ include file="/admin/foot.inc.jsp" %>
