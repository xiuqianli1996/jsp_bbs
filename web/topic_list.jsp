<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.inc.jsp"%>
<div class="am-g am-g-fixed bbs-g-fixed">
  <div class="am-u-md-8">

  <ul class="am-list am-list-border">
      <c:forEach items="${page.list}" var="topic">
          <li><a href="topic?id=${topic.id}">${topic.title}<small class="am-fr">${topic.author.username}：
              <fmt:formatDate value="${topic.createTime}" pattern="yyyy-MM-dd"/></small></a> </li>
      </c:forEach>

  </ul>

    <hr class="am-article-divider bbs-hr">
    <ul class="am-pagination bbs-pagination">
      <li class="am-pagination-prev"><a href="section?id=${id == null ? "" : id}&pageNumber=${page.pageNumber == null
      ||  page.pageNumber < 2 ? "1" : pageNumber - 1}">&laquo; 上一页</a></li>
      <li class="am-pagination-next"><a href="section?id=${id == null ? "" : id}&pageNumber=${page.pageNumber == null
       ? "2" : (page.pageNumber >= page.totalPage ? page.totalPage : page.pageNumber + 1)}">下一页 &raquo;</a></li>
    </ul>
  </div>
    <%@include file="foot.inc.jsp"%>
