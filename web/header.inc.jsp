<%--
  Created by IntelliJ IDEA.
  User: lxq
  Date: 2017/11/8
  Time: 8:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jspbbs.web.bean.Section" %>

<%@include file="head.inc.jsp"%>
<body>
<header class="am-topbar">
    <h1 class="am-topbar-brand">
        <a href="#">JspBBS</a>
    </h1>

    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
            data-am-collapse="{target: '#doc-topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span
            class="am-icon-bars"></span></button>

    <div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse">
        <ul class="am-nav am-nav-pills am-topbar-nav">
            <li><a href="#">所有帖子</a></li>
            <%
                List<Section> sectionList = (List<Section>) request.getAttribute("sectionList");
                if(null != sectionList){
                    int sectionListLen = sectionList.size();
                    int len = sectionListLen > 3 ? 3 : sectionListLen;
                    for(int i = 0; i < len; i++){
                        Section section = sectionList.get(i);
            %>
            <li><a href="section?id=<%=section.getId()%>"><%=section.getName()%></a></li>
            <%
                }
                if(sectionListLen > len){
            %>
            <li class="am-dropdown" data-am-dropdown>
                <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                    更多板块 <span class="am-icon-caret-down"></span>
                </a>
                <ul class="am-dropdown-content">
                    <%
                        for (;len < sectionListLen; len++){
                            Section section = sectionList.get(len);
                    %>
                    <li><a href="section?id=<%=section.getId()%>"><%=section.getName()%></a></li>
                    <%
                        }
                    %>
                </ul>
            </li>
            <%
                    }
                }

            %>

            <li><a href="topic?action=my">我的帖子</a></li>
        </ul>

        <form class="am-topbar-form am-topbar-left am-form-inline am-topbar-right" method="post" action="topic?action=search">
            <div class="am-form-group">
                <input type="text" name="kw" value="${kw}" class="am-form-field am-input-sm" placeholder="搜索帖子">
            </div>
            <button type="submit" class="am-btn am-btn-default am-btn-sm">搜索</button>
        </form>

    </div>
</header>
