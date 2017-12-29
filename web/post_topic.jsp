<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.inc.jsp"%>

<div class="am-g am-g-fixed bbs-g-fixed">
    <div class="am-u-md-8">


        <form class="am-form" action="topic?action=addTopic" method="post">
            <fieldset>
                <legend>编辑帖子</legend>
                <div class="am-form-group">
                    <label for="select-section">版块</label>
                    <select data-am-selected="{btnSize: 'sm'}" id="select-section" name="sectionID">
                        <c:forEach items="${sectionList}" var="section">
                            <option value="${section.id}">${section.name}</option>
                        </c:forEach>

                    </select>
                    <span class="am-form-caret"></span>
                </div>
                <div class="am-form-group">
                    <label for="text-title">标题</label>
                    <input type="text" class="am-form-field" id="text-title" rows="10" name="title">
                </div>
                <div class="am-form-group">
                    <label for="text-content">正文</label>
                    <textarea class="am-form-field" id="text-content" rows="10" name="content"></textarea>
                </div>
                <p><button type="submit" class="am-btn am-btn-success am-fr ">提交</button></p>
            </fieldset>
        </form>


    </div>
<%@include file="foot.inc.jsp"%>