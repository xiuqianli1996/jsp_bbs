<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/admin/header.inc.jsp" %>

<div class="am-g am-g-fixed bbs-g-fixed">
    <div class="am-u-md-10">

        <hr>

        <div class="am-g">
            <div class="am-u-sm-12 am-u-md-6">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <a href="admin/section?action=edit" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增
                        </a>
                    </div>
                </div>
            </div>
            <form class="am-form" action="admin/section" method="post">

                <div class="am-u-sm-12 am-u-md-3">
                    <div class="am-input-group am-input-group-sm">
                        <input type="text" name="kw" class="am-form-field" value="${kw == null ? "" : kw}">
                        <span class="am-input-group-btn">
            <button class="am-btn am-btn-default" type="submit">搜索</button>
          </span>
                    </div>
                </div>
            </form>

        </div>

        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form">
                    <table class="am-table am-table-striped am-table-hover table-main">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>名称</th>
                            <th>版主</th>
                            <th>添加时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${sections}" var="section">
                            <tr>
                                <td>${section.id}</td>
                                <td class="am-hide-sm-only">${section.name}</td>
                                <td><a href="#">${section.master.username}</a></td>
                                <td class="am-hide-sm-only"><fmt:formatDate value="${section.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>
                                    <div class="am-btn-toolbar">
                                        <div class="am-btn-group am-btn-group-xs">
                                            <a href="admin/section?action=edit&id=${section.id}"
                                               class="am-btn am-btn-default am-btn-xs am-text-secondary"><span
                                                    class="am-icon-pencil-square-o"></span> 编辑
                                            </a>
                                            <a href="admin/section?action=delete&id=${section.id}" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
                                                <span class="am-icon-trash-o"></span> 删除
                                            </a>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                    <div class="am-cf">
                        共 ${fn:length(sections)} 条记录
                        <%--<div class="am-fr">--%>
                            <%--<ul class="am-pagination">--%>
                                <%--<li class="am-disabled"><a href="#">«</a></li>--%>
                                <%--<li class="am-active"><a href="#">1</a></li>--%>
                                <%--<li><a href="#">2</a></li>--%>
                                <%--<li><a href="#">3</a></li>--%>
                                <%--<li><a href="#">4</a></li>--%>
                                <%--<li><a href="#">5</a></li>--%>
                                <%--<li><a href="#">»</a></li>--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    </div>
                    <hr/>
                </form>
            </div>

        </div>

    </div>

</div>

<%@ include file="/admin/foot.inc.jsp" %>
