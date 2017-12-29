<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.inc.jsp"%>

		<div class="am-g am-g-fixed bbs-g-fixed">
			<div class="am-u-md-8">

				<article class="am-article">
					<div class="am-article-hd">
						<h1 class="am-article-title">${topic.title}</h1>
						<p class="am-article-meta">
							<i class="am-icon-tasks"></i>
							<a href="section?id=${topic.section.id}">${topic.section.name}</a>&nbsp;&nbsp;
							<i class="am-icon-user"></i>
							<a href="#">${topic.author.username}</a>&nbsp;&nbsp;
							<i class="am-icon-calendar-times-o"></i>
							<fmt:formatDate value="${topic.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;
							<c:if test="${null != sessionScope.user && (topic.authorID == sessionScope.user.id
							|| topic.section.masterID == sessionScope.user.id) }">
								<i class="am-icon-trash"></i> <a href="topic?action=delTopic&id=${topic.id}">删帖</a>
							</c:if>
						</p>
					</div>

					<div class="am-article-bd">
						<p class="am-article-lead">
							${topic.content}
						</p>

					</div>
				</article>

				<hr class="am-article-divider">

				<form class="am-form" id="commentForm" action="topic?action=addReply" method="post">
					<fieldset>
						<legend>回帖</legend>
						<input type="hidden" name="topicID" value="${topic.id}">
						<input type="hidden" name="pid" value="">
						<textarea class="am-form-field" rows="5" name="content"></textarea>
						<p><button type="submit" class="am-btn am-btn-success am-fr ">提交</button></p>
					</fieldset>
				</form>
				<c:forEach items="${topic.replyList}" var="reply">
					<article class="am-comment am-comment-flip">
						<div class="am-comment-main">
							<header class="am-comment-hd">
								<!--<h3 class="am-comment-title">评论标题</h3>-->
								<div class="am-comment-meta">
									#${reply.level}
									<a href="#link-to-user" class="am-comment-author">${reply.user.username}</a>
									于 <fmt:formatDate value="${reply.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/> 回帖
								</div>
							</header>

							<div class="am-comment-bd">
								${reply.content}
							</div>

							<footer class="am-comment-footer">
								<div class="am-comment-actions">
									<a href="#" class="replyFloor" data-id="${reply.id}" data-level="${reply.level}">
										<i class="am-icon-commenting"></i>
									</a>
									<c:if test="${null != sessionScope.user && (reply.userID == sessionScope.user.id
							|| topic.section.masterID == sessionScope.user.id) }">
										<a href="topic?action=delReply&id=${reply.id}" id="delReply"><i class="am-icon-trash"></i></a>
									</c:if>

								</div>
							</footer>

						</div>
					</article>
				</c:forEach>

			</div>
			<script>
                function getAnchor(maoid) {
                    var href = location.href;
                    var index = href.lastIndexOf("#", 0);
                    if (index > 0) {
                        href = href.substring(0, index) + maoid;
                    } else {
                        href = href + '#' + maoid;
                    }
                    return href;
                };
                $(".replyFloor").attr('href', getAnchor('commentForm'));
				$(".replyFloor").click(function () {
					var obj = $(this);
					$("input[name='pid']").val(obj.attr('data-id'));
                    $("textarea[name='content']").attr('placeholder', '回复 ' + obj.attr('data-level') + ' 楼：');
                    //goAnchor('commentForm');
                })
				$('#commentForm').submit(function () {
                    var contentArea = $("textarea[name='content']");
                    var reply = contentArea.attr("placeholder");
                    if (undefined !== reply){
                        var content = contentArea.val();
                        contentArea.val(reply + content);
					}

                    return true;
                })
			</script>
<%@include file="foot.inc.jsp"%>