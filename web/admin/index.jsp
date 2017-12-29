<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.inc.jsp"%>

<div class="am-g am-g-fixed bbs-g-fixed">
    <div class="am-u-md-8">

        <ul class="am-avg-sm-1 am-avg-md-4 am-margin am-padding am-text-center admin-content-list ">
            <li><a href="admin/topic" class="am-text-success"><span class="am-icon-btn am-icon-file-text"></span><br>帖子数量<br>${topicCount}</a></li>
            <li><a href="admin/user" class="am-text-warning"><span class="am-icon-btn am-icon-briefcase"></span><br>用户数量<br>${userCount}</a></li>
            <li><a href="admin/section" class="am-text-danger"><span class="am-icon-btn am-icon-recycle"></span><br>版块数量<br>${sectionCount}</a></li>
        </ul>
    </div>

</div>

<%@ include file="foot.inc.jsp" %>

