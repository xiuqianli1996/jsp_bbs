package com.jspbbs.web.controller;

import com.jspbbs.core.annotation.Around;
import com.jspbbs.core.db.Page;
import com.jspbbs.core.web.Controller;
import com.jspbbs.core.web.HttpContext;
import com.jspbbs.web.bean.Reply;
import com.jspbbs.web.bean.Topic;
import com.jspbbs.web.bean.User;
import com.jspbbs.web.interceptor.SectionInfoInterceptor;
import com.jspbbs.web.interceptor.UserAuthInterceptor;
import com.jspbbs.web.service.ReplyService;
import com.jspbbs.web.service.TopicService;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "TopicController", urlPatterns = "/topic")
@Around(SectionInfoInterceptor.class)
public class TopicController extends Controller {

    private TopicService topicService = TopicService.me;
    private ReplyService replyService = ReplyService.me;

    public void index(HttpContext context) throws IOException {

        Topic topic = null;

        if (context.containsParam("id")){
            topic = topicService.get(context.getParamInt("id"));
            topicService.addViewCount(topic);
        }
        if(null == topic){
            context.getResponse().sendError(404);
            return;
        }

        context.setAttr("topic", topic);
        context.renderJSP("topic.jsp");
    }

    public void search(HttpContext context){
        int pageNumber = context.getParamInt("pageNumber", 1);
        int pageSize = context.getParamInt("pageSize", 15);
        Page<Topic> page = new Page<>(pageNumber,pageSize);
        String kw = context.getParam("kw");
        context.setAttr("page", topicService.paginateBySearch(page, kw))
                .keepParam("kw")
                .renderJSP("topic_list.jsp");
    }

    /**发帖页
     * @param context
     * @throws IOException
     */
    @Around(UserAuthInterceptor.class)
    public void addTopicPage(HttpContext context){
        context.renderJSP("post_topic.jsp");
    }

    /**发帖
     * @param context
     * @throws IOException
     */
    @Around(UserAuthInterceptor.class)
    public void addTopic(HttpContext context) {
        User user = (User) context.getSessionAttr("user");

        Topic topic = context.getBean(Topic.class);
        topic.setAuthorID(user.getId());

        String msg ;
        if (topicService.save(topic)){
            msg = "发帖成功";
            user.getTopicList().add(topic);
            context.setSessionAttr("user", user);
        } else {
            msg = "发帖失败";
        }

        context.setAttr("msg", msg)
                .setAttr("url", "#")
                .setAttr("linkName", "首页")
                .renderJSP("show_msg.jsp");

    }

    /**删除帖子
     * @param context
     * @throws IOException
     */
    @Around(UserAuthInterceptor.class)
    public void delTopic(HttpContext context) throws IOException {
        User user = (User) context.getSessionAttr("user");

        if (!context.containsParam("id")){
            context.sendError(404);
        }
        int id = context.getParamInt("id");

        int userID = user.getId();
        Topic topic = topicService.get(id);

        if (topic.getAuthorID().equals(userID) || topic.getSection().getMasterID().equals(userID)){
            String msg = "";
            if (topicService.deleteByID(id)){
                msg = "删帖成功";
            } else {
                msg = "删帖失败";
            }

            context.setAttr("msg", msg)
                    .setAttr("url", "#")
                    .setAttr("linkName", "首页")
                    .renderJSP("show_msg.jsp");
        } else {
            context.sendError(403);//没有权限删帖
        }

    }

    /**删除回帖
     * @param context
     * @throws IOException
     */
    @Around(UserAuthInterceptor.class)
    public void delReply(HttpContext context) throws IOException {
        User user = (User) context.getSessionAttr("user");

        if (!context.containsParam("id")){
            context.sendError(404);
        }
        int id = context.getParamInt("id");
        Reply reply = replyService.get(id);

        int userID = user.getId();
        Topic topic = reply.getTopic();

        if (reply.getUserID().equals(userID) || topic.getSection().getMasterID().equals(userID)){
            String msg = "";
            if (replyService.deleteByID(id)){
                msg = "删除回帖成功";
            } else {
                msg = "删除回帖失败";
            }

            context.setAttr("msg", msg)
                    .setAttr("url", "topic?id=" + topic.getId())
                    .setAttr("linkName", "帖子详情")
                    .renderJSP("show_msg.jsp");
        } else {
            context.sendError(403);//没有权限删帖
        }

    }

    /**回帖
     * @param context
     */
    @Around(UserAuthInterceptor.class)
    public void addReply(HttpContext context){
        User user = (User) context.getSessionAttr("user");

        Reply reply = context.getBean(Reply.class);
        reply.setUserID(user.getId());//设置回帖人ID

        if (replyService.save(reply)){
            context.setAttr("msg", "回帖成功");
        } else {
            context.setAttr("msg", "回帖失败");
        }

        context.setAttr("url", "topic?id=" + context.getParam("topicID"))
                .setAttr("linkName", "帖子详情")
                .renderJSP("show_msg.jsp");

    }

    /**我的帖子
     * @param context
     */
    @Around(UserAuthInterceptor.class)
    public void my(HttpContext context){
        User user = (User)context.getSessionAttr("user");

        int pageNumber = context.getParamInt("pageNumber", 1);
        int pageSize = context.getParamInt("pageSize", 15);
        Page<Topic> page = new Page<>(pageNumber,pageSize);

        page = topicService.getTopicPageByAuthorID(user.getId(), page);

        context.setAttr("page", page)
                .renderJSP("topic_list.jsp");
    }

}
