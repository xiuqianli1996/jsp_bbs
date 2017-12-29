package com.jspbbs.web.controller.admin;

import com.jspbbs.core.web.Controller;
import com.jspbbs.core.web.HttpContext;
import com.jspbbs.web.service.SectionService;
import com.jspbbs.web.service.TopicService;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/admin/topic")
public class SysTopicController extends Controller {
    
    private TopicService topicService = TopicService.me;
    private SectionService sectionService = SectionService.me;

    @Override
    public String getBaseViewPath() {
        return "/admin/topic";
    }

    public void index(HttpContext context){
        String kw = context.getParam("kw");
        Integer sectionID = context.getParamInteger("sectionID");
        context.setAttr("topics", topicService.getTopicsBySearch(sectionID, kw))
                .setAttr("sections", sectionService.list())
                .keepParam("kw")
                .keepParam("sectionID")
                .renderJSP("list.jsp");
    }

    public void delete(HttpContext context){
        topicService.deleteByID(context.getParamInt("id"));
        context.setAttr("topics", topicService.list())
                .renderJSP("list.jsp");
    }

    
}
